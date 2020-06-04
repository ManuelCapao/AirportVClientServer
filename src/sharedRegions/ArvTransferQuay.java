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
import stubs.*;
/**
 *
 * @author manuel
 */
public class ArvTransferQuay implements SharedRegion{
    private RepositoryStub repository;
    private ArvTerminalExitStub arv;
    private DepTerminalEntranceStub dep;
    private Queue<Integer> passengersWaiting;
    private Queue<Integer> passengersInBus;
    private boolean busDoorsOpen;

    public ArvTransferQuay(RepositoryStub repository, ArvTerminalExitStub arv, DepTerminalEntranceStub dep){
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
    public synchronized void takeABus(int id){
        System.out.println("take a bus");
       
        repository.setPassengerState(id,PassengerStates.ATT);
        this.passengersWaiting.add(id);
        repository.addPassengerWaitingList(id);
        //if number of passengers waiting is 3, then it notifies the
        //bus driver to start the bus
        if(this.passengersWaiting.size() == 3){
            notifyAll();
        }
        
    }

    /**
     * 
     * Passenger wait to catch the bus
     */
    public synchronized void waitToCatch(int id){
        System.out.println("wait to catch");
        
        waitingloop:
        while(true){
            try{
                wait(); //passenger waits for the porter to notify him when he can enter the bust
                if(this.busDoorsOpen){
                    Object[] temp = passengersWaiting.toArray();
                

                    for(int i = 0; i < (temp.length > 3 ? 3 : temp.length); i++){
                        if(id == (Integer) temp[i]){
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

    public synchronized void enterTheBus(int id){
        System.out.println("enter the bus");
        
        passengersInBus.add(id); //adds the passenger ID to the bus
        repository.addPassengerToBus(id); //same in the repository
        notifyAll(); //notifies 
    }
    


    /* metodos do Driver */
    /**
     * Bus Driver Function
     * Update driver state to driving forward
     */
    public synchronized void goToDepartureTerminal(){
        System.out.println("go to dep terminal");

        try {
            repository.setBusDriverState(BusDriverStates.DRFW);
            Thread.currentThread().sleep((long) (new Random().nextInt(50 + 1) + 20)); // simulates a trip
        } catch (InterruptedException e) {}
    }
    
   /**
    * Park the bus at ATT
    */
    public synchronized void parkTheBus(){
        System.out.println("park the bus");
        repository.setBusDriverState(BusDriverStates.PKAT);
        }
    /**
     * Let passengers enter the bus
     */
    public synchronized int announcingBusBoarding(){
        System.out.println("announcing");
        
        this.passengersInBus.clear();
        this.busDoorsOpen = true;
        int numWaiting = (passengersWaiting.size() > 3 ? 3 : passengersWaiting.size());

        notifyAll();
        try{
            while(passengersInBus.size() != numWaiting){
                System.out.println("waiting: " + numWaiting);
                System.out.println("in bus: " + passengersInBus.size());
                wait();
            }

        }catch(InterruptedException e){}
        
        this.busDoorsOpen = false;
        for (int i = 0; i < numWaiting; i++) {
            passengersWaiting.remove();
        }
        return numWaiting;
    }
    
    /**
     * waits 2s before check if there is any passenger
     * @return true if has any passengers waiting for bus
     */
    public synchronized boolean readyToGo(){
        System.out.println("ok ");
        try{
            wait(2000); // verificar scheduling 
        }catch(InterruptedException e){}
        if(this.passengersWaiting.size() > 0){
            System.out.println("ready to go ");
            return true;
        }else {
            System.out.println("not ready: " + this.passengersWaiting);
            return false;
        }
        
    }
    /**
     * check if his day of work as finished
     * 
     * @return 
     */
    public synchronized boolean hasDaysWorkEnded(){
        System.out.println("dep count: " + this.dep.getCount());
        System.out.println("arv count: " + this.arv.getCount());
        if((this.dep.getCount() + this.arv.getCount()) == 30){
            System.out.println("acabou");
            return true;
        }
        else{
            System.out.println("nao acabou");
            return false;
        }
        

    }
    
}
