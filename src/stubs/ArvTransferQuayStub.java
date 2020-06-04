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
public class ArvTransferQuayStub extends GenericStub {
    
    
    public ArvTransferQuayStub(String hostname, int port){
        super(hostname, port);
    }
    
    public void takeABus(int id){
        
        Message out = new Message();
        
        out.setMessageType(MessageType.TAKE_A_BUS);
        out.setIdentifier(id);
        
        this.process(out);
    }
    
    public void waitToCatch(int id){
        
        Message out = new Message();
        
        out.setMessageType(MessageType.WAIT_TO_CATCH);
        out.setIdentifier(id);
        this.process(out);
    }
    
    public void enterTheBus(int id){
        
        Message out= new Message();
        out.setIdentifier(id);
        out.setMessageType(MessageType.ENTER_THE_BUS);
        
        this.process(out);
    }
    
    public void goToDepartureTerminal(){
        Message out = new Message();
        
        out.setMessageType(MessageType.GO_TO_DEPARTURE_TERMINAL);
        
        this.process(out);
    }
    
    public void parkTheBus(){
        Message out = new Message();
        
        out.setMessageType(MessageType.PARK_THE_BUS);
        this.process(out);
    }
    public int announcingBusBoarding(){
        Message out = new Message();
        Message in;
        out.setMessageType(MessageType.ANNOUNCING_BUS_BOARDING);
        in = this.process(out);
        return in.getIntValue();
        
    }
    public boolean readyToGo(){
        Message out = new Message();
        Message in;
        
        out.setMessageType(MessageType.READY_TO_GO);
        
        in = this.process(out);
        return in.getBooleanValue();
        
    }
    public boolean hasDaysWorkEnded(){
        
        Message out = new Message();
        Message in;
        
        out.setMessageType(MessageType.BUS_DRIVER_END_OF_WORK);
        
        in = this.process(out);
        return in.getBooleanValue();
    }
    
    public void simulationFinished() {
        Message out = new Message();

        out.setMessageType(MessageType.ARV_TRANSFER_QUAY_SIMULATION_FINISHED);

        this.process(out);
    }
}
