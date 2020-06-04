/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxies;
import commonInfra.Message;
import commonInfra.MessageType;
import entities.BusDriverStates;
import entities.PassengerStates;
import entities.PorterStates;
import sharedRegions.Repository;
/**
 *
 * @author manuel
 */
public class RepositoryProxy implements ProxyInterface {
    
    private boolean simulationFinished = false;
    private final Repository repository;
    
    public RepositoryProxy(Repository repository) {
        this.repository = repository;
    }
    
    @Override
    public Message processAndReply(Message message) {
        Message response = new Message();
        switch(message.getMessageType()) {
            case REPOSITORY_INIT: 
                repository.init_rep(message.getIntValue()); 
                break;
            case REPOSITORY_PASSENGER_ENTER_LOUNGE: 
                repository.passengerEnterLounge(message.getIdentifier(), message.getBooleanValue(), message.getIntValue()); 
                break;
            case REPOSITORY_REMOVE_PASSENGER_FROM_BUS: 
                repository.removePassengerFromTheBus(message.getIdentifier()); 
                break;
            case REPOSITORY_ADD_PASSENGER_TO_BUS: 
                repository.addPassengerToBus(message.getIdentifier()); 
                break;
            case REPOSITORY_ADD_PASSENGER_TO_WAITING_LIST: 
                repository.addPassengerWaitingList(message.getIdentifier()); 
                break;
            case REPOSITORY_ADD_COLLECTED_LUGGAGE: 
                repository.addCollectedLuggage(message.getIdentifier()); 
                break;
            case REPOSITORY_ENTER_LUGGAGE_STORE_ROOM: 
                repository.enterLuggageStoreRoom(); 
                break;
            case REPOSITORY_ENTER_LUGGAGE_BELT: 
                repository.enterLuggageBelt(); 
                break;
            case REPOSITORY_REMOVE_LUGGAGE_PLANE_HOLD: 
                repository.removeLuggagePlainHold(); 
                break;
            case REPOSITORY_NEW_FLIGHT: 
                repository.newFlight(message.getIntValue()); 
                break;
            case REPOSITORY_SET_PORTER_STATE: 
                repository.setPorterState((PorterStates)message.getEntityState()); 
                break;
            case REPOSITORY_SET_BUS_DRIVER_STATE: 
                repository.setBusDriverState((BusDriverStates)message.getEntityState());
                break;
            case REPOSITORY_SET_PASSENGER_STATE: 
                repository.setPassengerState(message.getIdentifier(), (PassengerStates)message.getEntityState()); 
                break;
            case REPOSITORY_ADD_REPORT: 
                repository.addReport(); 
                break;
            case REPOSITORY_SIMULATION_FINISHED: 
                this.simulationFinished = true;
                System.exit(0);
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
