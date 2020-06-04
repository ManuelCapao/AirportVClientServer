/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxies;
import commonInfra.Message;
import commonInfra.MessageType;
import sharedRegions.ArvTransferQuay;
/**
 *
 * @author manuel
 */
public class ArvTransferQuayProxy implements ProxyInterface{
    
    private final ArvTransferQuay arrivalTerminalTransferQuay;

    private boolean simulationFinished = false;

    public ArvTransferQuayProxy(ArvTransferQuay arrivalTerminalTransferQuay) {
        this.arrivalTerminalTransferQuay = arrivalTerminalTransferQuay;

    }
    
    @Override
    public Message processAndReply(Message message) {
        Message response = new Message();
        switch(message.getMessageType()) {
            case READY_TO_GO:
                response.setBooleanValue(arrivalTerminalTransferQuay.readyToGo());
                break;
            case ANNOUNCING_BUS_BOARDING:
                response.setIntValue(arrivalTerminalTransferQuay.announcingBusBoarding());
                break;
            case GO_TO_DEPARTURE_TERMINAL:
                arrivalTerminalTransferQuay.goToDepartureTerminal();
                break;
            case PARK_THE_BUS:
                arrivalTerminalTransferQuay.parkTheBus();
                break;
            case BUS_DRIVER_END_OF_WORK:
                response.setBooleanValue(arrivalTerminalTransferQuay.hasDaysWorkEnded());
                break;
            case TAKE_A_BUS:
                arrivalTerminalTransferQuay.takeABus(message.getIdentifier());
                break;
            case WAIT_TO_CATCH:
                arrivalTerminalTransferQuay.waitToCatch(message.getIdentifier());
                break;
            case ENTER_THE_BUS:
                arrivalTerminalTransferQuay.enterTheBus(message.getIdentifier());
                break;

            case ARV_TRANSFER_QUAY_SIMULATION_FINISHED:
                this.simulationFinished = true;
                break;
        }
        response.setMessageType(MessageType.OK);
        return response;
    }
    @Override
    public boolean simulationFinished() {
        return simulationFinished;
    }

}
