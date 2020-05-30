/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;

import entities.Passenger;
import entities.PassengerStates;

/**
 *
 * @author manuel
 */
public class DepTerminalEntrance {
    private int countFinish;
    private ArvTerminalExit arv;
    private Repository rep;
    public DepTerminalEntrance(Repository rep){
        this.countFinish = 0;
        this.rep = rep;
        
    }
    
    /* Metodos do Passenger */
    /**
     * Method used by the passenger in its last stage when in transfer
     */
    public synchronized void prepareNextLeg(){
        Passenger passenger = (Passenger) Thread.currentThread();
        rep.setPassengerState(passenger.getID(), PassengerStates.EDT); //sets passenger state to "/entering the departure terminal" (in repository) 
        try{
            this.countFinish++;
            //if its the last passengers it must notify all passengers to either go home or proceed to check in for the next lef of the journey
            if((this.countFinish + this.arv.getCount()) % 6 == 0){ 
                this.arv.notifyPassengers();
                notifyAll();
            }
            else{
                wait();
                passenger.setState(PassengerStates.EDT);//sets passenger state to "/entering the departure terminal"
            }
            
            
            
        }catch (InterruptedException e) {}
    }
    
    public void setArvTerminalExit(ArvTerminalExit arv){
        this.arv = arv;
    }
    public synchronized int getCount(){
        return this.countFinish;
    }
    
    /**
     * notify all passengers to either go home or proceed to check in for the next lef of the journey
     */
    public synchronized void notifyPassengers(){
        notifyAll();
    }
}
