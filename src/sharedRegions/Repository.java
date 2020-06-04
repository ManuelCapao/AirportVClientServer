/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;


//import airportvconc.AirportVConc;
import entities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @author manuel
 */
public class Repository implements SharedRegion{

    //------Bus Driver------
    //Fila de pessoas para usar o autocarro
  

    int passengersFinished = 0;
    int passengersRunning = 0;
    int bagsInPlaneHold = 0;
    int Totalbags = 0;
    private File f;
    private PrintWriter pw;

    //AirportVConc
    int[] passengersLuggage;
    boolean[] passengersFinalDestination;
    int flightNumber;
    int passengersCount;
    int passengersLanded;


    int luggageInPlaneHold;
    char[] passengersActions;
    int[] passengersLuggageCollected;
    int luggageInBelt;
    int luggageInStoreRoom;
    int[] waitingForBus;
    int[] inTheBus;
    
    

    //States
    BusDriverStates busDriverState;
    PorterStates porterState;
    PassengerStates[] passengersState;

    public Repository() throws FileNotFoundException {
        f = new File("log_" + new Date().toString().replace(' ', '_') + ".txt");
        pw = new PrintWriter(f);
        String header = header();
        header = header.concat("\n");
        System.out.println(header);
        pw.write(header);
        
        
    }
    
    /**
     * initializes the repository with the current flight number
     * @param flightNumber
     */
    public synchronized void init_rep(int flightNumber){
        this.flightNumber = flightNumber;
        this.luggageInStoreRoom = 0;
        this.luggageInBelt = 0;
        this.luggageInPlaneHold = 0;
        this.passengersCount = 0;
        this.porterState = PorterStates.WPTL;
        this.busDriverState = BusDriverStates.PKAT;
        this.passengersState = new PassengerStates[6];
        for (int i = 0; i < 6; i++) {
            this.passengersState[i] = PassengerStates.IP;
        }
        this.waitingForBus = new int[6];
        Arrays.fill(this.waitingForBus, -1);
        this.inTheBus = new int[3];
        Arrays.fill(this.inTheBus, -1);
        this.passengersActions = new char[6];
        Arrays.fill(this.passengersActions, '-');
        this.passengersLuggage = new int[6];
        Arrays.fill(this.passengersLuggage, -1);
        this.passengersLuggageCollected = new int[6];
        Arrays.fill(this.passengersLuggageCollected, -1);
    }
    

    //Sets of each State
    public synchronized void setBusDriverState(BusDriverStates state){
        this.busDriverState= state;
        log();
    }
    public synchronized void setPorterState(PorterStates state){
        this.porterState = state;
        log();
    }
    public synchronized void setPassengerState(int id, PassengerStates state){
        this.passengersState[id] = state;
        log();
    }
    



        //--- ARV LOUNGE--- //

    /**
     * updates the number of bags in the current flight and adds to the total number of bags that have been in the planes hold
     * @param luggageInPlane
     */
    public synchronized void newFlight(int luggageInPlane){
        this.luggageInPlaneHold = luggageInPlane;
        bagsInPlaneHold +=  luggageInPlane;
        log();
    }
    
    /**
     * Passenger enters the Arrival Lounge
     * @param id
     * @param destination
     * @param numbags
     */
    public synchronized void passengerEnterLounge(int id, boolean destination, int numbags){
        this.Totalbags += numbags;
        this.passengersCount++; //Adds to total number of passengers
        this.passengersLuggage[id] = numbags; //updates the number of bags the passenger has in the beggining of the journey
        this.passengersState[id] = PassengerStates.WSD; //state of passenger is "what should i do"
        //If the airport is the passengers final destination it adds to passengers finished
        if(destination){
            this.passengersActions[id] = 'F';
            passengersFinished++;
        } else{
            this.passengersActions[id] = 'T';
            passengersRunning++; //else it adds to passengers running, aka passengers in transit
        }
        log();
    }

    /**
     * Removes 1 bag to the number of bags in planes Hold, the porter then is "at the plane's hold" state
     */
    public synchronized void removeLuggagePlainHold() {
        this.porterState = PorterStates.APLH;
        this.luggageInPlaneHold--;
        log();
    }

    

        //--- BgCollectionPoint --- //

    /**
     * 
     * Add 1 bag to the number of bags in conveyor belt, porter states is "at the luggage conveyor belt"
     */
    public synchronized void enterLuggageBelt() {
        this.porterState = PorterStates.ALCB;
        this.luggageInBelt++;
        log();
    }



    /**
     * Add 1 bag to the number of bags collected by the passenger with the given 
     * @param id
     */
    public synchronized void addCollectedLuggage(int id) {
        if (this.passengersLuggageCollected[id] == -1) this.passengersLuggageCollected[id] = 0;
        this.passengersLuggageCollected[id] ++;
        this.luggageInBelt--;  //Removes 1 from the number of bags in the conveyor bel
        log();
    }




        // --- StoreRoom --- //

    /**
     * Adds 1 bag to the number of bags in Store Room
     */
    public synchronized void enterLuggageStoreRoom() {
        this.porterState = PorterStates.ASTR; //Porter state is "at the storeroom"
        this.luggageInStoreRoom++;
        log();
    }
    


        // -- ArvTransferQuay -- //

    /**
     * Adds a passenger to the waiting list to get in the bus
     * @param id
     */
    public synchronized void addPassengerWaitingList(int id) {
        for (int i= 0; i < 6; i++) {
            if (waitingForBus[i] == -1) {
                waitingForBus[i] = id;
                passengersState[id] = PassengerStates.ATT; //The passengers state then is: at the arrival transfer terminal
                break;
            }
        }
        log();
    }

    /**
     * adds a passenger in the bus 
     * @param id
     */
    public synchronized void addPassengerToBus(int id) {
        for (int i= 0; i < 6; i++) { //Search for an empty spot on the bus
            if (inTheBus[i] == -1) {
                inTheBus[i] = id;  // Add to bus
                break;
            }
        }

        //removes the first passenger waiting for the bus and fills the empty spaces with a -1 (no passenger)
        for (int i= 0; i < 5; i++) {
            waitingForBus[i] = waitingForBus[i+1];
        }
        waitingForBus[5] = -1;

        this.setPassengerState(id,PassengerStates.TRT); //sets passenger state to: terminal transfer
    }


        // --- DepTransferQuay --//

    /**
     * Removes a passenger from the bus, if this passenger isnt in the bus it add the passenger to the bus
     * @param id
     */
    public void removePassengerFromTheBus(int id) {
        for (int i= 0; i < 3; i++) {
            if (inTheBus[i] == id) {
                inTheBus[i] = -1;  // Add to bus
                break;
            }
        }
        log();
    }







        //---- LOGS ---

    /**
     * Prints the header of the log
     */
    public static String header() {
        String str = "PLANE    PORTER                  DRIVER\n";
        str = str.concat("FN BN  Stat CB SR   Stat  Q1 Q2 Q3 Q4 Q5 Q6  S1 S2 S3\n");
        str = str.concat("St1 Si1 NR1 NA1 St2 Si2 NR2 NA2 St3 Si3 NR3 NA3 St4 Si4 NR4 NA4 St5 Si5 NR5 NA5 St6 Si6 NR6 NA6");
        return str;
    }

    /**
     * Logs
     */
    private void log() {
        String output = logInternalState();
        output = output.concat("\n");
        System.out.println(output);
        pw.write(output);
        pw.flush();
       
    }
    private String logInternalState() {
        String str = "";
        str = str.concat(String.format("%-3d%-4d%-5s%-3d%-5d%-6s",(flightNumber+1), luggageInPlaneHold, porterState, luggageInBelt, luggageInStoreRoom, busDriverState));
        for (int i = 0; i < 6; i++) {
            if (this.waitingForBus[i]!=-1) {
                str = str.concat(String.format("%-3d", this.waitingForBus[i]));
            } else {
                str = str.concat("-  ");
            }
        }
        str = str.concat(" ");
        for (int i = 0; i < 3; i++) {
            if (this.inTheBus[i]!=-1) {
                str = str.concat(String.format("%-3d", this.inTheBus[i]));
            } else {
                str = str.concat("-  ");
            }
        }
        str = str.concat("\n");

        for (int i = 0; i < 6; i++) {
            str = str.concat(String.format("%-4s", passengersState[i]));
            String situation = "-";
            if (passengersActions[i] == 'T') situation = "TRT";
            else if (passengersActions[i] == 'F') situation = "FDT";
            str = str.concat(String.format("%-4s", situation));
            if (passengersLuggage[i] == -1) {
                str = str.concat(String.format("%-4s", "-"));
            } else {
                str = str.concat(String.format("%-4d", passengersLuggage[i]));
            }
            if (passengersLuggageCollected[i] == -1) {
                str = str.concat(String.format("%-4s", "-"));
            } else {
                str = str.concat(String.format("%-4d", passengersLuggageCollected[i]));
            }
        }
        return str ;
    }

    /**
     * Print of the final report
     */
    private String report() {
        String str = "\n";
        str = str.concat("Final report\n");
        str = str.concat("N. of passengers which have this airport as their final destination = " + passengersFinished + "\n");
        str = str.concat("N. of passengers which are in transit = " + passengersRunning + "\n");
        str = str.concat("N. of bags that should have been transported in the planes hold = " + (Totalbags) + "\n");
        str = str.concat("N. of bags that were lost = " + (Totalbags - bagsInPlaneHold) + "\n");
        return str;
    }

    /**
     * Add the report at the end of the file
     *
     */
    public void addReport() {
        String output = report();
        System.out.println(output);
        pw.write(output);
        pw.flush();
    }

    
}
