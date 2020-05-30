/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxies;
import commonInfra.Message;
import commonInfra.MessageType;
import sharedRegions.BgReclaimOffice;
/**
 *
 * @author manuel
 */
public class BgReclaimOfficeProxy implements ProxyInterface {
    
    private final BgReclaimOffice baggageReclaimOffice;

    private boolean simulationFinished = false;

    public BgReclaimOfficeProxy(BgReclaimOffice baggageReclaimOffice) {
        this.baggageReclaimOffice = baggageReclaimOffice;

    }
    @Override
    public Message processAndReply(Message message) {

        Message response = new Message();
        switch(message.getMessageType()) {
            case REPORT_MISSING_BAGS:
                baggageReclaimOffice.reportMissingBags();
                break;
            case BG_RECLAIM_OFFICE_SIMULATION_FINISHED:
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
