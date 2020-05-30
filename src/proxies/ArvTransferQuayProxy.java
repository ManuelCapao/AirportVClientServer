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
                arrivalTerminalTransferQuay.readyToGo();
                break;
            case ANNOUNCING_BUS_BOARDING:
                arrivalTerminalTransferQuay.announcingBusBoarding();
                break;
            case GO_TO_DEPARTURE_TERMINAL:
                arrivalTerminalTransferQuay.goToDepartureTerminal();
                break;
            case PARK_THE_BUS:
                arrivalTerminalTransferQuay.parkTheBus();
                break;
            case BUS_DRIVER_END_OF_WORK:
                arrivalTerminalTransferQuay.hasDaysWorkEnded();
                break;
            case TAKE_A_BUS:
                arrivalTerminalTransferQuay.takeABus();
                break;
            case WAIT_TO_CATCH:
                arrivalTerminalTransferQuay.waitToCatch();
                break;
            case ENTER_THE_BUS:
                arrivalTerminalTransferQuay.enterTheBus();
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
