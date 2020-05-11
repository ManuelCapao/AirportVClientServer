/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;
import commonInfra.*;
import entities.*;
import java.util.Stack;
/**
 *
 * @author manuel
 */
public class ArvLounge {
    
    private int countPassengers; //variable that gives us the number of the current passnger
    private boolean[] passengersDestinations; //Vector with the final destination of each passenger, if true the airport is its final destination
    private Repository repository;
    private Stack<Bag> planeHoldBags; //Bags currently in the planes hold

    public ArvLounge(Repository repository){
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
        try {
           
            wait();
        } catch (InterruptedException e) {}
        
    }
    
    /**
     * Fills the planes hold with passengers bags
     * 
     */
    public synchronized void fillPlaneHold(boolean[] destinations, int[] bags){
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
        Porter p = (Porter) Thread.currentThread();
        p.setState(PorterStates.WPTL);
    }


    /**
     * 
     * Porter tries to collect a bag from the planes hold and returns bag collected, return null if there are no bags in planes hold
     */
    public synchronized Bag tryToCollectABag(){
        
        Porter porter = (Porter) Thread.currentThread();
        porter.setState(PorterStates.APLH);
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
    public synchronized char whatShouldIDo() 
    {
        Passenger p = (Passenger) Thread.currentThread();
        repository.passengerEnterLounge(p.getID(), p.getFinalDestination(), p.getNumBags());
        this.countPassengers++;
        char toDo;
        
        if(p.getFinalDestination() && p.getNumBags() != 0)
        {
            p.setState(PassengerStates.LCP);
            toDo = 'C'; //the passenger has bags and the airport is its final destination, so it must collect its bags
        }
        else if(!p.getFinalDestination()) 
        {
            p.setState(PassengerStates.ATT);
            toDo = 'T'; //Passenger is in transit
        } 
        else{
            p.setState(PassengerStates.EAT);
            toDo = 'E'; //the passenger doenst have any bags and has arrived at its last destination
        } 
        
        //checks is its the last passenger, so it can notify the porter to start collecting bags from the planes hold
        if(this.countPassengers == 6){
            notifyAll();
        }
        return toDo;
    }
    
    public void setEndOfWork(){
        
    }
    
}
