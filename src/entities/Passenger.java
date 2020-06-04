/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import commonInfra.*;
import sharedRegions.*;


public class Passenger extends Thread{
    private int passengerID;


    private boolean isFinalDestination; //True if the airport is the passengers final destination
    private int numBags; //number of bags carried at the start of the journey by the passenger
    private int bagsCollected;   //number of bags collected by the passenger from the conveyor belt
    private PassengerStates passengerState;

    //Bags

    //Shared Entities
    private ArvLounge arvLounge;
    private ArvTransferQuay arvTransferQuay;
    private ArvTerminalExit arvTerminalExit;
    private DepTransferQuay depTransferQuay;
    private DepTerminalEntrance depTerminalEntrance;
    private BgCollectionPoint bgCollectionPoint;
    private BgReclaimOffice bgReclaimOffice;

    public Passenger(int M,
                    int numberOfLuggages,
                    boolean isFinalDestination,
                    ArvLounge arvLounge,
                    ArvTransferQuay arvTransferQuay,
                    ArvTerminalExit arvTerminalExit,
                    DepTransferQuay depTransferQuay,
                    DepTerminalEntrance depTerminalEntrance,
                    BgCollectionPoint bgCollectionPoint,
                    BgReclaimOffice bgReclaimOffice){
        super("Passenger"+ M);
        this.arvLounge = arvLounge;
        this.passengerID = M;
        this.arvTransferQuay=arvTransferQuay;
        this.arvTerminalExit=arvTerminalExit;
        this.depTransferQuay=depTransferQuay;
        this.depTerminalEntrance=depTerminalEntrance;
        this.bgCollectionPoint=bgCollectionPoint;
        this.bgReclaimOffice=bgReclaimOffice;
        this.numBags = numberOfLuggages;
        this.isFinalDestination = isFinalDestination;

        passengerState = PassengerStates.WSD;

    }


    public int getID(){
        return passengerID;
    }
    public int getBagsCollected(){return bagsCollected;}
    public int getNumBags(){return numBags;}

    public boolean getFinalDestination(){return isFinalDestination;}

    public void setState(PassengerStates passengerState)
    {
        this.passengerState = passengerState;
    }
    public PassengerStates getPassengerState(){return passengerState;}


    @Override
    public void run()
    {
        char toDo;
        toDo= arvLounge.whatShouldIDo(); 
        switch (toDo) {
            case 'T':
                
                arvTransferQuay.takeABus();
                arvTransferQuay.waitToCatch();
                arvTransferQuay.enterTheBus();
                depTransferQuay.leaveTheBus();
                depTerminalEntrance.prepareNextLeg();
                break;
            case 'C':
                while(bagsCollected != numBags){
                    Bag bag = bgCollectionPoint.goCollectABag(passengerID);
                    if(bag != null){
                       
                        bagsCollected++;
                    } 
                    else if (bgCollectionPoint.checkNoMoreBags()){
               
                        break;
                    }
                    
                }   
                if (bagsCollected != numBags){
                    bgReclaimOffice.reportMissingBags();
                }   
                arvTerminalExit.goHome();
                break;
            default:
               
                arvTerminalExit.goHome();
                break;
        }
        
        
        
    }



}
