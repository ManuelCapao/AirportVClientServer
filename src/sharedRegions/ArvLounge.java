/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;
import commonInfra.*;
import java.util.Stack;
import stubs.*;
import entities.*;
/**
 *
 * @author manuel
 */
public class ArvLounge implements SharedRegion{
    
    private int countPassengers; //variable that gives us the number of the current passnger
    private boolean[] passengersDestinations; //Vector with the final destination of each passenger, if true the airport is its final destination
    private RepositoryStub repository;
    private Stack<Bag> planeHoldBags; //Bags currently in the planes hold

    public ArvLounge(RepositoryStub repository){
        this.countPassengers = 0;
        this.repository = repository;
        this.passengersDestinations = new boolean[6];
        this.planeHoldBags = new Stack<>();
        
    }
    /* Metodos do Porter */

    /**
     * Porter waits to be notified, by the last passenger to reach the arrival lounge, to start unloading planes hold
     */
    
    public synchronized void takeARest(){
        System.out.println("take a rest");
        try {
           
            wait();
        } catch (InterruptedException e) {}
        
    }
    
    /**
     * Fills the planes hold with passengers bags
     * 
     */
    public synchronized void fillPlaneHold(boolean[] destinations, int[] bags){
        System.out.println("fill plane");
        this.countPassengers = 0; //puts passenger count at 0
        this.passengersDestinations = destinations;
        planeHoldBags.clear(); //clears previous plane hold
        for(int i=0; i < destinations.length;i++){
            for(int j = 0; j < bags[i]; j++){
                Bag bag = new Bag(i, destinations[i]);
                this.planeHoldBags.push(bag);
                
            }
        } 
        repository.newFlight(planeHoldBags.size()); //In the repository, it updates the number of bags in the current planes hold
        //and adds to the total number of bags that have been in the planes hold

    }


    /**
     * porter has no more bags to collect, so we change its current state to waiting for a plane to land
     * 
     */ 
    public void noMoreBagsToCollect(){
        repository.setPorterState(PorterStates.WPTL);
    }


    /**
     * 
     * Porter tries to collect a bag from the planes hold and returns bag collected, return null if there are no bags in planes hold
     */
    public synchronized Bag tryToCollectABag(){

        repository.setPorterState(PorterStates.APLH);
        if(this.planeHoldBags.empty()) return null;
        Bag bag = this.planeHoldBags.pop();
        repository.removeLuggagePlainHold(); //decreases the number of bags in planes hold (in the repository)
        return bag;
    }
    
    /* Metodos do Passenger */

    /**
     * 
     * Checks what the Passenger should do next, depending on if the airport is its final destination and if it has any bags to collect
     */
    public synchronized char whatShouldIDo(int id, boolean isFinalDestination, int numberOfLuggages)
    {
        System.out.println("what should i do ");

        repository.passengerEnterLounge(id, isFinalDestination, numberOfLuggages);
        this.countPassengers++;
        char toDo;
        
        if(isFinalDestination && numberOfLuggages != 0)
        {

            repository.setPassengerState(id, PassengerStates.LCP);
            toDo = 'C'; //the passenger has bags and the airport is its final destination, so it must collect its bags
        }
        else if(!isFinalDestination)
        {

            repository.setPassengerState(id, PassengerStates.ATT);
            toDo = 'T'; //Passenger is in transit
        } 
        else{

            repository.setPassengerState(id, PassengerStates.EAT);
            toDo = 'E'; //the passenger doenst have any bags and has arrived at its last destination
        } 
        
        //checks is its the last passenger, so it can notify the porter to start collecting bags from the planes hold
        if(this.countPassengers == 6){
            notifyAll();
        }
        return toDo;
    }
    
    public synchronized void setEndOfWork(){
        notifyAll();
    }
    
}
