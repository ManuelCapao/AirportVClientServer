/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxies;

import commonInfra.Message;
import commonInfra.MessageType;
import sharedRegions.DepTerminalEntrance;
/**
 *
 * @author manuel
 */
public class DepTerminalEntranceProxy implements ProxyInterface{
    private final DepTerminalEntrance departureTerminalEntrance;

    private boolean simulationFinished = false;


    public DepTerminalEntranceProxy(DepTerminalEntrance departureTerminalEntrance) {
        this.departureTerminalEntrance = departureTerminalEntrance;
    }

    @Override
    public Message processAndReply(Message message) {

        Message response = new Message();

        switch(message.getMessageType()) {
            case PREPARE_NEXT_LEG:
                departureTerminalEntrance.prepareNextLeg(message.getIdentifier());
                break;
            case DEP_TERMINAL_ENTRANCE_GET_COUNT:
                response.setIntValue(departureTerminalEntrance.getCount());
                break;
            case DEP_TERMINAL_ENTRANCE_NOTIFY_PASSENGERS:
                departureTerminalEntrance.notifyPassengers();
                break;
            case DEP_TERMINAL_ENTRANCE_SIMULATION_FINISHED:
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
