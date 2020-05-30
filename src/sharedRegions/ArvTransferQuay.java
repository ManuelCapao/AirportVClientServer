/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;
import entities.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
/**
 *
 * @author manuel
 */
public class ArvTransferQuay {
    private Repository repository;
    private ArvTerminalExit arv;
    private DepTerminalEntrance dep;
    private Queue<Integer> passengersWaiting;
    private Queue<Integer> passengersInBus;
    private boolean busDoorsOpen;

    public ArvTransferQuay(Repository repository, ArvTerminalExit arv, DepTerminalEntrance dep){
        this.repository = repository;
        this.arv = arv;
        this.dep = dep;
        this.passengersWaiting = new LinkedList<>();
        this.passengersInBus = new LinkedList<>();
    }


    
    /* Metodos do Passenger */
    /**
     * adds the passenger to the list of passengers waiting to take a bus
     */
    public synchronized void takeABus(){
        Passenger passenger = (Passenger) Thread.currentThread();
        passenger.setState(PassengerStates.ATT);
        passengersWaiting.add(passenger.getID());
        repository.addPassengerWaitingList(passenger.getID());
        //if number of passengers waiting is 3, then it notifies the
        //bus driver to start the bus
        if(passengersWaiting.size() == 3){
            notifyAll();
        }
        
    }

    /**
     * 
     * Passenger wait to catch the bus
     */
    public synchronized void waitToCatch(){
        Passenger passenger = (Passenger) Thread.currentThread();
        waitingloop:
        while(true){
            try{
                wait(); //passenger waits for the porter to notify him when he can enter the bust
                if(this.busDoorsOpen){
                    Object[] temp = passengersWaiting.toArray();
                

                    for(int i = 0; i < (temp.length > 3 ? 3 : temp.length); i++){
                        if(passenger.getID() == (Integer) temp[i]){
                            break waitingloop;
                        }
                        
                    }
                }
            }catch(InterruptedException e){}
            
        }
    }
    /**
     * 
     * passenger enters the bus and notify BusDriver 
     */

    public synchronized void enterTheBus(){
        Passenger passenger = (Passenger) Thread.currentThread();
        passengersInBus.add(passenger.getID()); //adds the passenger ID to the bus
        repository.addPassengerToBus(passenger.getID()); //same in the repository
        notifyAll(); //notifies 
    }
    


    /* metodos do Driver */
    /**
     * Bus Driver Function
     * Update driver state to driving forward
     */
    public synchronized void goToDepartureTerminal(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        bd.setState(BusDriverStates.DRFW);
        try {
            repository.setBusDriverState(BusDriverStates.DRFW);
            Thread.currentThread().sleep((long) (new Random().nextInt(50 + 1) + 20)); // simulates a trip
        } catch (InterruptedException e) {}
    }
    
   /**
    * Park the bus at ATT
    */
    public synchronized void parkTheBus(){
        BusDriver bd = (BusDriver) Thread.currentThread();
        bd.setState(BusDriverStates.PKAT);
        repository.setBusDriverState(BusDriverStates.PKAT);
        }
    /**
     * Let passengers enter the bus
     */
    public synchronized void announcingBusBoarding(){
        
        BusDriver busDriver = (BusDriver) Thread.currentThread();
        this.passengersInBus.clear();
        this.busDoorsOpen = true;
        int numWaiting = (passengersWaiting.size() > 3 ? 3 : passengersWaiting.size());
        busDriver.sitPassengers(numWaiting);
        notifyAll();
        try{
            while(passengersInBus.size() != numWaiting) wait();
        }catch(InterruptedException e){}
        
        this.busDoorsOpen = false;
        for (int i = 0; i < numWaiting; i++) {
            passengersWaiting.remove();
        }
    }
    
    /**
     * waits 2s before check if there is any passenger
     * @return true if has any passengers waiting for bus
     */
    public synchronized boolean readyToGo(){
        try{
            wait(2000); // verificar scheduling 
        }catch(InterruptedException e){}
        if(this.passengersWaiting.size() > 0){
            return true;
        }else return false;
        
    }
    /**
     * check if his day of work as finished
     * 
     * @return 
     */
    public synchronized boolean hasDaysWorkEnded(){
        if((this.dep.getCount() + this.arv.getCount()) == 30){
            return true;
        }
        else{
            return false;
        }
        

    }
    
}
