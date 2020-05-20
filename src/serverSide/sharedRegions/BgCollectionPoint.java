/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;
import commonInfra.*;
import entities.*;
import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * @author manuel
 */
public class BgCollectionPoint {
    private boolean checkBelt;
    private Repository repository;
    private Queue<Bag> bagsInBelt;
    public BgCollectionPoint(Repository repository){
        this.repository= repository;
        this.checkBelt = false;
        this.bagsInBelt = new LinkedList<>();
        
    }
    /* Metodos do Porter */
    /**
     * Porter carries bag to conveyor belt
     */
    public synchronized void carryToAppropriateStore(Bag bag){
        Porter porter = (Porter) Thread.currentThread();
        porter.setState(PorterStates.ALCB); //sets porter state to "at the luggage conveyor belt"
        this.bagsInBelt.add(bag); //adds the bag to the conveyor belt
        repository.enterLuggageBelt();
        
        notifyAll();
    }
    
    /**
     * Synchronization point where the porter gives the signal to the passengers that there are no more bags to collect
     * 
     */
    public synchronized void warningNoMoreBagsToCollect(){
        this.checkBelt = true;
        repository.setPorterState(PorterStates.WPTL);
        notifyAll();
    }
            
    /* Metodos do Passenger */
    
    /**
     * porter tries to collect a bag
     * 
     */
    public synchronized Bag goCollectABag(int id){
        
        repository.setPassengerState(id, PassengerStates.LCP); //sets passenger state to "at the luggage collection point"
        Bag bag = null;
        try{
            wait(200); //waits 200s or until the porter gives the signal that the bag is in the conveyor belt
           
            //if the bag of the passenger is in the conveyor belt it is removed 
            if(!this.bagsInBelt.isEmpty()){
                if(this.bagsInBelt.element().getPassenger() == id){
                    repository.addCollectedLuggage(id);
                    bag = this.bagsInBelt.remove();
                                        
                }
            }
  
        }catch(InterruptedException e){}
        return bag;
        
        
    }
    
    public synchronized boolean checkNoMoreBags(){
        return checkBelt;
    }
    public synchronized void setNoMoreBags(){
        this.checkBelt = false;
    }
    
    
}
