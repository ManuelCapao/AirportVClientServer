/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;

import entities.BusDriver;
import entities.BusDriverStates;
import entities.Passenger;
import entities.PassengerStates;
import java.util.Random;
import stubs.RepositoryStub;

/**
 *
 * @author manuel
 */
public class DepTransferQuay implements SharedRegion{

    RepositoryStub repository;
    private int passengersInBus;
    
    public DepTransferQuay(RepositoryStub repository){
        this.repository = repository;  
        this.passengersInBus = 0;
    }
    /* Metodos do Passenger */ 
    /**
     * Passenger leaves the bus
     */
    public synchronized void leaveTheBus(int id){

        try{
            wait(); // waits for the bus driver to let the passengers out 
            this.passengersInBus--; 
            repository.setPassengerState(id,PassengerStates.DTT); //sets passenger state to "at the departure transfer terminal"
            //if all the passengers have left, it notifies the bus driver to drive back
            if(this.passengersInBus == 0){
                System.out.println("ja sairam todos: "+ id);
                notifyAll();
            }
            //In the repository, removes the passenger (with the specified id) from the bus
            repository.removePassengerFromTheBus(id);
        }catch(InterruptedException e){}
    }
    
    /* Metodos do Driver */

    /**
     * Method responsible for all the passengers leaving the bus
     */
    public synchronized void parkTheBusAndLetPassOff(int numPassengers){
        System.out.println("parkTheBusAndLetPassOff: ");
        repository.setBusDriverState(BusDriverStates.PKDT);  //sets driver state to"parking at the departure terminal" (in repository)
        this.passengersInBus = numPassengers;
        notifyAll(); // Notifies passengers they can leave the bus
        try {
            wait(); // Waits for everyone to leave, the last passenger wakes up the bus driver
            System.out.println("fui acordado bus driver");
        } catch (InterruptedException e ){}
    } 


    /**
     * Bus Driver goes back to arrival terminal
     */
    public synchronized void goToArrivalTerminal(){
        repository.setBusDriverState(BusDriverStates.DRBW); //update state in repository
        try {
            Thread.currentThread().sleep((long) (new Random().nextInt(50 + 1) + 20)); // Simulates a trip back
        } catch (InterruptedException e) {}
    }
}
