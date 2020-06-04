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
public class DepTransferQuayStub extends GenericStub{
    
    public DepTransferQuayStub(String hostname, int port){
        super(hostname, port);
    }
    
    public void leaveTheBus(int id){
        
        Message out = new Message();
        out.setMessageType(MessageType.LEAVE_THE_BUS); 
        out.setIdentifier(id);
        this.process(out);
    }
    
    public void parkTheBusAndLetPassOff(int passengers){
        
        Message out = new Message();  
        out.setMessageType(MessageType.PARK_THE_BUS_AND_LET_PASS_OFF);
        out.setIntValue(passengers);
        this.process(out);
    }
    public void goToArrivalTerminal(){
        
        Message out = new Message();    
        out.setMessageType(MessageType.GO_TO_ARRIVAL_TERMINAL);
        this.process(out);
    }
    
    public void simulationFinished(){
        
        Message out = new Message();    
        out.setMessageType(MessageType.DEP_TRANSFER_QUAY_SIMULATION_FINISHED);
        this.process(out);
    }
}
