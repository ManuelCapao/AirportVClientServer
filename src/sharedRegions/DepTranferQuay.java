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

/**
 *
 * @author manuel
 */
public class DepTranferQuay {

    Repository repository;
    private int passengersInBus;
    
    public DepTranferQuay(Repository repository){
        this.repository = repository;  
        this.passengersInBus = 0;
    }
    /* Metodos do Passenger */ 
    /**
     * Passenger leaves the bus
     */
    public synchronized void leaveTheBus(){
        Passenger passenger = (Passenger) Thread.currentThread();
        try{
            wait(); // waits for the bus driver to let the passengers out 
            this.passengersInBus--; 
            passenger.setState(PassengerStates.DTT); //sets passenger state to "at the departure transfer terminal"
            //if all the passengers have left, it notifies the bus driver to drive back
            if(this.passengersInBus == 0){
                notifyAll();
            }
            //In the repository, removes the passenger (with the specified id) from the bus
            repository.removePassengerFromTheBus(passenger.getID());
        }catch(InterruptedException e){}
    }
    
    /* Metodos do Driver */

    /**
     * Method responsible for all the passengers leaving the bus
     */
    public synchronized void parkTheBusAndLetPassOff(){
        repository.setBusDriverState(BusDriverStates.PKDT);  //sets driver state to"parking at the departure terminal" (in repository)
        BusDriver busDriver = (BusDriver) Thread.currentThread();
        busDriver.setState(BusDriverStates.PKDT); //sets driver state to"parking at the departure terminal" 
        this.passengersInBus = busDriver.numPassengersOnBus();
        notifyAll(); // Notifies passengers they can leave the bus
        try {
            wait(); // Waits for everyone to leave, the last passenger wakes up the bus driver
        } catch (InterruptedException e ){}
    } 


    /**
     * Bus Driver goes back to arrival terminal
     */
    public synchronized void goToArrivalTerminal(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        bd.setState(BusDriverStates.DRBW);  //state is "driving backwars"
        repository.setBusDriverState(BusDriverStates.DRBW); //update state in repository
        try {
            Thread.currentThread().sleep((long) (new Random().nextInt(50 + 1) + 20)); // Simulates a trip back
        } catch (InterruptedException e) {}
    }
}
