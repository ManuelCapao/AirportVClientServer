/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxies;
import commonInfra.Message;
import commonInfra.MessageType;
import sharedRegions.BgCollectionPoint;
/**
 *
 * @author manuel
 */
public class BgCollectionPointProxy implements ProxyInterface{
    
    private final BgCollectionPoint baggageCollectionPoint;

    private boolean simulationFinished = false;

    public BgCollectionPointProxy(BgCollectionPoint baggageCollectionPoint) {
        this.baggageCollectionPoint = baggageCollectionPoint;

    }

    @Override
    public Message processAndReply(Message message) {

        Message response = new Message();

        switch(message.getMessageType()) {
            case WARNING_NO_MORE_BAGS_TO_COLLECT:
                 baggageCollectionPoint.warningNoMoreBagsToCollect();
                 break;
            case BG_COLLECTION_POINT_CARRY_TO_APPROPRIATE_STORE:
                 baggageCollectionPoint.carryToAppropriateStore(message.getBag());
                 break;
            case GO_COLLECT_BAG:
                response.setBag(baggageCollectionPoint.goCollectABag(message.getIdentifier()));
                break;
            case SET_NO_MORE_BAGS:
                baggageCollectionPoint.setNoMoreBags();
                break;
            case BG_COLLECTION_POINT_SIMULATION_FINISHED:
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
