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
public class ArvTerminalExitStub extends GenericStub {
    
    
    public ArvTerminalExitStub(String hostname, int port){
        super(hostname,port);
    }
    
    public void goHome(int id){
        
        Message out;

        out= new Message();
        out.setMessageType(MessageType.GO_HOME);
        out.setIdentifier(id);

        this.process(out);
    }
    
    public void notifyPassengers(){
        Message out;

        out= new Message();
        out.setMessageType(MessageType.ARV_TERMINAL_EXIT_NOTIFY_PASSENGERS);

        this.process(out);
    }
    public int getCount(){
        Message out,in;
        
        out = new Message();
        out.setMessageType(MessageType.ARV_TERMINAL_EXIT_GET_COUNT);
        
        in = this.process(out);
        return in.getIntValue();
        
    }
    
    public void simulationFinished(){
        
        Message out;

        out= new Message();
        out.setMessageType(MessageType.ARV_TERMINAL_EXIT_SIMULATION_FINISHED);

        this.process(out);
    }
}
