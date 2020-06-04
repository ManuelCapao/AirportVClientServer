/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;

import entities.Passenger;
import entities.PassengerStates;
import stubs.*;
/**
 *
 * @author manuel
 */
public class DepTerminalEntrance implements SharedRegion{
    private int countFinish;
    private ArvTerminalExitStub arv;
    private RepositoryStub rep;
    public DepTerminalEntrance(RepositoryStub rep,ArvTerminalExitStub arv){
        this.countFinish = 0;
        this.rep = rep;
        this.arv = arv;
        
    }
    
    /* Metodos do Passenger */
    /**
     * Method used by the passenger in its last stage when in transfer
     */
    public synchronized void prepareNextLeg(int id){
        System.out.println("prepare next leg: "+ id);
        rep.setPassengerState(id, PassengerStates.EDT); //sets passenger state to "/entering the departure terminal" (in repository) 
        try{
            this.countFinish++;
            //if its the last passengers it must notify all passengers to either go home or proceed to check in for the next lef of the journey
            if((this.countFinish + this.arv.getCount()) % 6 == 0){ 
                this.arv.notifyPassengers();
                notifyAll();
            }
            else{
                wait();
                
            }
            
            
            
        }catch (InterruptedException e) {}
    }
    
    public int getCount(){
        synchronized (this){
            return this.countFinish;
        }

    }
    
    /**
     * notify all passengers to either go home or proceed to check in for the next leg of the journey
     */
    public synchronized void notifyPassengers(){
        System.out.println("notify: ");
        notifyAll();
    }
}
