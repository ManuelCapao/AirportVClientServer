/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stubs;
import commonInfra.*;
/**
 *
 * @author manuel
 */
public class BgCollectionPointStub extends GenericStub{
    
    public BgCollectionPointStub(String hostname, int port){
        super(hostname, port);
    }
    
    public void carryToAppropriateStore(Bag bag){
        Message out = new Message();
        
        out.setBag(bag);
        out.setMessageType(MessageType.BG_COLLECTION_POINT_CARRY_TO_APPROPRIATE_STORE);
        this.process(out);
    }
    
    
    public void warningNoMoreBagsToCollect(){
        Message out = new Message();
        
        out.setMessageType(MessageType.WARNING_NO_MORE_BAGS_TO_COLLECT);
        this.process(out);
    }
            
    
    public Bag goCollectABag(int id){
        
        Message out = new Message();
        Message in;
        out.setIdentifier(id);
        out.setMessageType(MessageType.GO_COLLECT_BAG);
        in = this.process(out);
        return in.getBag();    
    }
    
    public boolean checkNoMoreBags(){
       
        Message out = new Message();
        Message in;
        out.setMessageType(MessageType.CHECK_NO_MORE_BAGS);
        
        in = this.process(out);
        return in.getBooleanValue();
    }
    public void setNoMoreBags(){
        Message out = new Message();


         out.setMessageType(MessageType.SET_NO_MORE_BAGS);

         this.process(out);
    }
    
    public void simulationFinished() {
        Message out = new Message();

      
        out.setMessageType(MessageType.BG_COLLECTION_POINT_SIMULATION_FINISHED);

        this.process(out);
    }
    
}
