/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import sharedRegions.*;
import commonInfra.Bag;
/**
 *
 * @author manuel
 */

public class Porter extends Thread {
    private boolean planeHoldEmpty; //true if the planes hold is empty
    private ArvLounge arvLounge;
    private StoreRoom tempStorage;
    private BgCollectionPoint bgCollectionPoint;
    private Repository repository;
    private boolean keepAlive; //variable to keep the porter working if true
    private StateInterface state;

    public Porter(ArvLounge arvLounge, StoreRoom tempStorage, BgCollectionPoint bgCollectionPoint, Repository repository){
        super("Porter");
        this.arvLounge=arvLounge;
        this.tempStorage = tempStorage;
        this.repository= repository;
        this.bgCollectionPoint = bgCollectionPoint;
        //repository.setPorterState(PorterStates.WPTL);
        this.keepAlive = true;
        
    }

    @Override
    public void run()
    {
        int count = 0;
        while(true){
            if (!this.keepAlive) break;
            
            arvLounge.takeARest();
            count++;
            planeHoldEmpty = false;
            bgCollectionPoint.setNoMoreBags();
            while(!planeHoldEmpty){

                Bag bag = arvLounge.tryToCollectABag();

                if (bag == null){
                    
                    planeHoldEmpty = true;
                    bgCollectionPoint.warningNoMoreBagsToCollect();
                    arvLounge.noMoreBagsToCollect();
                }
                else if(!bag.getDestination()){
                    
                    tempStorage.carryToAppropriateStore(bag);
                }
                else{ 
                    
                    bgCollectionPoint.carryToAppropriateStore(bag);
                }
            }
            if (count == 5){
                this.keepAlive = false;
            }
        }
            
    }
    
    public void setAlive(boolean keep){
        this.keepAlive = keep;
    }

    public void setPorterState(StateInterface state)
    {
        this.state = state; 
        //repository.setPorterState(state);
    }
    public StateInterface getPorterState(){
        return state;
    }
    
}
