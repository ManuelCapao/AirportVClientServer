/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxies;
import commonInfra.Message;
import commonInfra.MessageType;
import sharedRegions.StoreRoom;
/**
 *
 * @author manuel
 */
public class StoreRoomProxy implements ProxyInterface {
    
    
    private final StoreRoom temporaryStorageArea;

    private boolean simulationFinished = false;

    public StoreRoomProxy(StoreRoom temporaryStorageArea) {
        this.temporaryStorageArea = temporaryStorageArea;
    }
    
    @Override
    public Message processAndReply(Message message) {

        Message response = new Message();

        switch(message.getMessageType()) {
            case STORE_ROOM_CARRY_TO_APPROPRIATE_STORE:
                temporaryStorageArea.carryToAppropriateStore(message.getBag());
                break;
            case STORE_ROOM_SIMULATION_FINISHED:
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
