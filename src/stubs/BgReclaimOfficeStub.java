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
public class BgReclaimOfficeStub extends GenericStub{
    
    public BgReclaimOfficeStub(String hostname, int port){
        super(hostname, port);
    }
    
    public void reportMissingBags(int id){
        
        Message out = new Message();
        out.setMessageType(MessageType.REPORT_MISSING_BAGS);   
        out.setIdentifier(id);
        this.process(out);
    }
    public void simulationFinished(){
        
        Message out = new Message();
        out.setMessageType(MessageType.BG_RECLAIM_OFFICE_SIMULATION_FINISHED);      
        this.process(out);
    }
}
