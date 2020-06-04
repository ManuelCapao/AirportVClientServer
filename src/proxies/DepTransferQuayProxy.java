/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxies;
import commonInfra.Message;
import commonInfra.MessageType;
import sharedRegions.DepTransferQuay;
/**
 *
 * @author manuel
 */
public class DepTransferQuayProxy implements ProxyInterface{
    
    
    private final DepTransferQuay depTransferQuay;
    private boolean simulationFinished = false;
    public DepTransferQuayProxy(DepTransferQuay depTransferQuay) {
        this.depTransferQuay = depTransferQuay;
    }
    
    @Override
    public Message processAndReply(Message message) {
        Message response = new Message();
        switch(message.getMessageType()) {
            case PARK_THE_BUS_AND_LET_PASS_OFF:
                depTransferQuay.parkTheBusAndLetPassOff(message.getIntValue());
                break;
            case LEAVE_THE_BUS:
                depTransferQuay.leaveTheBus(message.getIdentifier());
                break;
            case GO_TO_ARRIVAL_TERMINAL:
                depTransferQuay.goToArrivalTerminal();
                break;
            case DEP_TRANSFER_QUAY_SIMULATION_FINISHED:
                this.simulationFinished = true;
                break;
        }
        response.setMessageType(MessageType.OK);
        return response;
    }

    @Override
    public boolean simulationFinished() {
        return this.simulationFinished;
    }

}
