/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;
import commonInfra.*;
import entities.Porter;
import entities.PorterStates;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author manuel
 */
public class StoreRoom {
    
    private Repository repository;
    private Queue<Bag> bagsInStore; //bags in the store room

    public StoreRoom(Repository repository){
        this.repository = repository;
        this.bagsInStore = new LinkedList<>();
        
    }

    /**
     * porter puts bag in the store room, and updates repository and the porter's state
     */
    public void carryToAppropriateStore(Bag bag)
    {
        Porter porter = (Porter) Thread.currentThread();
        //porter.setState(PorterStates.ASTR);
        this.repository.enterLuggageStoreRoom();
        this.bagsInStore.add(bag);
        
    }
}
