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
public class ArvLoungeStub extends GenericStub{
    
    public ArvLoungeStub(String hostname, int port) {
        super(hostname, port);   
    }
    
    public void takeARest() {
        Message out;

        out= new Message();
        out.setMessageType(MessageType.TAKE_A_REST);

        this.process(out);
    }
    
    public void fillPlaneHold(boolean[] destinations, int[] bags){
        
        Message out;

        out= new Message();
        out.setMessageType(MessageType.FILL_PLANE_HOLD); 
        out.setIntArray(bags);
        out.setBooleanArray(destinations);
        
        this.process(out);
    }
    
    public void noMoreBagsToCollect(){
        Message out;

        out= new Message();
        out.setMessageType(MessageType.NO_MORE_BAGS_TO_COLLECT);
        
        this.process(out);
    }
    
    public Bag tryToCollectABag(){
        
        Message out, inMessage;

        out= new Message();
        out.setMessageType(MessageType.TRY_TO_COLLECT_BAG);

        inMessage = this.process(out);

        return inMessage.getBag();
    }
    
     public char whatShouldIDo(int id, boolean isFinalDestination, int numberOfLuggages) {
        Message out = new Message();

        Message in;
        out.setMessageType(MessageType.WHAT_SHOULD_I_DO);
        out.setIdentifier(id);
        out.setBooleanValue(isFinalDestination);
        out.setIntValue(numberOfLuggages);
        in = this.process(out);
        return in.getCharValue();
        
    }
     public void simulationFinished(){
        Message out;

        out= new Message();
        out.setMessageType(MessageType.ARV_LOUNGE_SIMULATION_FINISHED);

        this.process(out);
     }
}
