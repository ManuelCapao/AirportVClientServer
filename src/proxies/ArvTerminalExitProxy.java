/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxies;
import commonInfra.Message;
import commonInfra.MessageType;
import sharedRegions.ArvTerminalExit;
/**
 *
 * @author manuel
 */
public class ArvTerminalExitProxy implements ProxyInterface{
    
    
    private final ArvTerminalExit arrivalTerminalExit;

    private boolean simulationFinished = false;


    public ArvTerminalExitProxy(ArvTerminalExit arrivalTerminalExit) {
        this.arrivalTerminalExit = arrivalTerminalExit;
    }
    
    @Override
    public Message processAndReply(Message message){
        Message response = new Message();
        switch(message.getMessageType()) {
            case GO_HOME:
                arrivalTerminalExit.goHome();
                break;
            case ARV_TERMINAL_EXIT_GET_COUNT:
                response.setIntValue(arrivalTerminalExit.getCount());
                break;
            case ARV_TERMINAL_EXIT_NOTIFY_PASSENGERS:
                arrivalTerminalExit.notifyPassengers();
                break;
            case ARV_TERMINAL_EXIT_SIMULATION_FINISHED:
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
