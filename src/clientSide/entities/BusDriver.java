/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientSide.entities;
import clientSide.stubs.*;

public class BusDriver extends Thread {
    private boolean dayWorkEnded; //if true the BusDriver ends his day of work
    private int passengersInTheBus; //Number of passengers currently on the bus

    //Shared Entities
    private DepTranferQuayStub depTransferQuay;
    private ArvTranferQuayStub arvTransferQuay;
    private RepositoryStub repository;

    public BusDriver(ArvTranferQuayStub arvTransferQuay, DepTranferQuayStub depTransferQuay, RepositoryStub repository){
        super("BusDriver");
        this.arvTransferQuay=arvTransferQuay;
        this.depTransferQuay=depTransferQuay;
        this.repository = repository;
        
        this.dayWorkEnded = false;
    }

    @Override
    public void run()
    {
        while(!dayWorkEnded)
        {   
            if(arvTransferQuay.readyToGo()){
                arvTransferQuay.announcingBusBoarding();
                arvTransferQuay.goToDepartureTerminal();
                depTransferQuay.parkTheBusAndLetPassOff();
                depTransferQuay.goToArrivalTerminal();
                arvTransferQuay.parkTheBus();
            }
            
            dayWorkEnded = arvTransferQuay.hasDaysWorkEnded();

        }
        
    }
    public synchronized int numPassengersOnBus(){
        return passengersInTheBus;
    }
    
    //Changes number of passengers on the bus
    public void sitPassengers(int passengersInTheBus) {
        this.passengersInTheBus = passengersInTheBus;
    }
    public void setState(BusDriverStates state){repository.setBusDriverState(state);}
}
