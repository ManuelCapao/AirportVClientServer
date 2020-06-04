/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;
import entities.*;
import stubs.*;
/**
 *
 * @author manuel
 */
public class BgReclaimOffice implements SharedRegion{
    private RepositoryStub rep;
    public BgReclaimOffice(RepositoryStub rep){
        this.rep = rep;
    }
    
    /* Metodos do Passenger */
    
    /**
     * Method for the passenger to report its missing bags
     */
    public synchronized void reportMissingBags(int id){
        
        rep.setPassengerState(id, PassengerStates.BRO);//sets passenger state to "at the baggage reclaim office" (in repository)
        //rep.bagsLost++; 
        try {
            //passenger.sleep(3000) 
            Thread.sleep(3000); //Quick fix, simulates the process of reporting the bags, assuming it takes 3000s
        } catch (Exception e) { }
    }
}
