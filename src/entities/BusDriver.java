/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import sharedRegions.*;
import stubs.*;
public class BusDriver extends Thread {
    private boolean dayWorkEnded; //if true the BusDriver ends his day of work
    private int passengersInTheBus; //Number of passengers currently on the bus

    //Shared Entities
    private DepTransferQuayStub depTransferQuay;
    private ArvTransferQuayStub arvTransferQuay;
    private RepositoryStub repository;

    public BusDriver(ArvTransferQuayStub arvTransferQuay, DepTransferQuayStub depTransferQuay, RepositoryStub repository){
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

                this.sitPassengers(arvTransferQuay.announcingBusBoarding());
                System.out.println("announcing");

                arvTransferQuay.goToDepartureTerminal();
                System.out.println("go to dep ");
                depTransferQuay.parkTheBusAndLetPassOff(this.numPassengersOnBus());
                System.out.println("park and let");
                depTransferQuay.goToArrivalTerminal();
                System.out.println("go to arv terminal");
                arvTransferQuay.parkTheBus();
                System.out.println("park the bus");
            }
            System.out.println("entrei");
            dayWorkEnded = arvTransferQuay.hasDaysWorkEnded();
            System.out.println("sai");

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
