/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxies;

import commonInfra.Message;
import commonInfra.MessageType;
import sharedRegions.ArvLounge;
/**
 *
 * @author manuel
 */
public class ArvLoungeProxy implements ProxyInterface{
    
    private final ArvLounge arrivalLounge;

    private boolean simulationFinished = false;


    public ArvLoungeProxy(ArvLounge arrivalLounge) {
        this.arrivalLounge = arrivalLounge;

    }
    
    @Override
    public Message processAndReply(Message message) {
        Message response = new Message();

        switch(message.getMessageType()) {
            case TAKE_A_REST:
                arrivalLounge.takeARest();
                break;
            case FILL_PLANE_HOLD:
                arrivalLounge.fillPlaneHold(message.getBooleanArray(),message.getIntArray());
                break;
            case SET_PORTER_END_OF_WORK:
                arrivalLounge.setEndOfWork();
                break;
            case NO_MORE_BAGS_TO_COLLECT:
                arrivalLounge.noMoreBagsToCollect();
                break;
            case TRY_TO_COLLECT_BAG:
                response.setBag(arrivalLounge.tryToCollectABag());
                break;
            case WHAT_SHOULD_I_DO:
                response.setCharValue(arrivalLounge.whatShouldIDo());
                break;
            case ARV_LOUNGE_SIMULATION_FINISHED:
                simulationFinished = true;
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
