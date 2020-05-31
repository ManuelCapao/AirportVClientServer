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
public class DepTerminalEntranceStub extends GenericStub {
    
    public DepTerminalEntranceStub(String hostname, int port){
        super(hostname, port);
    }
    
    public void prepareNextLeg(){
        
        Message out = new Message();
        out.setMessageType(MessageType.PREPARE_NEXT_LEG);
        this.process(out);
    }
    
    public int getCount(){
        
        Message out = new Message();
        Message in;
        
        out.setMessageType(MessageType.DEP_TERMINAL_ENTRANCE_GET_COUNT);
        in = this.process(out);
        return in.getIntValue();
    }
    
    public void notifyPassengers(){
        
        Message out = new Message();
        
        out.setMessageType(MessageType.DEP_TERMINAL_ENTRANCE_NOTIFY_PASSENGERS);
        
        this.process(out);
    }
    public void simualtionFinished(){
        
        Message out = new Message();
        
        out.setMessageType(MessageType.DEP_TERMINAL_ENTRANCE_SIMULATION_FINISHED);
        
        this.process(out);
    }
}
