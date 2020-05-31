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
public class StoreRoomStub extends GenericStub{
    
    public StoreRoomStub(String hostname, int port){
        super(hostname,port);
    }
    
    public void carryToAppropriateStore(Bag bag){
        Message out = new Message();
        
        out.setMessageType(MessageType.STORE_ROOM_CARRY_TO_APPROPRIATE_STORE);
        out.setBag(bag);
        this.process(out);
    }
    
    public void simulationFinished(){
        Message out = new Message();
        
        out.setMessageType(MessageType.STORE_ROOM_SIMULATION_FINISHED);
        this.process(out);
    }
}
