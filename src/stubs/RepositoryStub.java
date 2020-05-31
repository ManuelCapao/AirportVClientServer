/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stubs;

import commonInfra.*;
import entities.*;
/**
 *
 * @author manuel
 */
public class RepositoryStub extends GenericStub{
    
    
    public RepositoryStub(String hostname, int port){
        super(hostname,port);
    }
    
    public void init_rep(int flightNumber){

        Message out = new Message();
        out.setIntValue(flightNumber);
        out.setMessageType(MessageType.REPOSITORY_INIT);
        this.process(out);
    }
    
    public void setBusDriverState(BusDriverStates state){
        
        Message out = new Message();
        out.setMessageType(MessageType.REPOSITORY_SET_BUS_DRIVER_STATE);
        out.setEntityState(state);
        this.process(out);
    }
    
    public void setPorterState(PorterStates state){

        Message out = new Message();
        out.setMessageType(MessageType.REPOSITORY_SET_PORTER_STATE);
        out.setEntityState(state);
        this.process(out);
        
    }
    public void setPassengerState(int id, PassengerStates state){

        Message out = new Message();
        out.setIdentifier(id);
        out.setMessageType(MessageType.REPOSITORY_SET_PASSENGER_STATE);
        out.setEntityState(state);
        this.process(out);
    }

    public void newFlight(int luggageInPlane){
        Message out = new Message();
        out.setIntValue(luggageInPlane);
        out.setMessageType(MessageType.REPOSITORY_NEW_FLIGHT);
        this.process(out);
    }
    
    public void passengerEnterLounge(int id, boolean destination, int numbags){
        Message out = new Message();
        out.setIntValue(numbags);
        out.setIdentifier(id);
        out.setBooleanValue(destination);
        out.setMessageType(MessageType.REPOSITORY_PASSENGER_ENTER_LOUNGE);
        this.process(out);
    }

    public void removeLuggagePlainHold() {
        Message out = new Message();
        out.setMessageType(MessageType.REPOSITORY_REMOVE_LUGGAGE_PLANE_HOLD);
        this.process(out);
    }

    public void enterLuggageBelt() {
        Message out = new Message();
        out.setMessageType(MessageType.REPOSITORY_ENTER_LUGGAGE_BELT);
        this.process(out);
    }

    public void addCollectedLuggage(int id) {
        Message out = new Message();
        out.setIdentifier(id);
        out.setMessageType(MessageType.REPOSITORY_ADD_COLLECTED_LUGGAGE);
        this.process(out);
    }

    public void enterLuggageStoreRoom() {
        Message out = new Message();
        out.setMessageType(MessageType.REPOSITORY_ENTER_LUGGAGE_STORE_ROOM);
        this.process(out);
    }
 
    public void addPassengerWaitingList(int id) {
        Message out = new Message();
        out.setIdentifier(id);
        out.setMessageType(MessageType.REPOSITORY_ADD_PASSENGER_WAITING_LIST);
        this.process(out);
    }

    public void addPassengerToBus(int id) {
        Message out = new Message();
        out.setIdentifier(id);
        out.setMessageType(MessageType.REPOSITORY_ADD_PASSENGER_TO_BUS);
        this.process(out);
    }

    public void removePassengerFromTheBus(int id) {
        Message out = new Message();
        out.setIdentifier(id);
        out.setMessageType(MessageType.REPOSITORY_REMOVE_PASSENGER_FROM_BUS);
        this.process(out);
    }

    public void addReport() {
        Message out = new Message();
        out.setMessageType(MessageType.REPOSITORY_ADD_REPORT);
        this.process(out);
    }
    
    public void simulationFinished() {
        Message out = new Message();
        out.setMessageType(MessageType.REPOSITORY_SIMULATION_FINISHED);
        this.process(out);
    }
}
