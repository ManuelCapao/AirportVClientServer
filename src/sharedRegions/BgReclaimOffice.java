/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;
import entities.*;
/**
 *
 * @author manuel
 */
public class BgReclaimOffice {
    private Repository rep;
    public BgReclaimOffice(Repository rep){
        this.rep = rep;
    }
    
    /* Metodos do Passenger */
    
    /**
     * Method for the passenger to report its missing bags
     */
    public synchronized void reportMissingBags(){
        Passenger passenger = (Passenger) Thread.currentThread();
        passenger.setState(PassengerStates.BRO); //sets passenger state to "at the baggage reclaim office" 
        rep.setPassengerState(passenger.getID(), PassengerStates.BRO);//sets passenger state to "at the baggage reclaim office" (in repository)
        rep.bagsLost++; 
        try {
            //passenger.sleep(3000) 
            Thread.sleep(3000); //Quick fix, simulates the process of reporting the bags, assuming it takes 3000s
        } catch (Exception e) { }
    }
}
