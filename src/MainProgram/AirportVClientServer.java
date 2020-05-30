/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProgram;
import entities.BusDriver;
import entities.Passenger;
import entities.Porter;
import stubs.*;

import java.util.Random;
/**
 *
 * @author manuel
 */
public class AirportVClientServer {

    //Number of passengers per flight
    public static int N = 6;

    //Number of flights
    public static int K = 5;

    //Number of seats on the bus
    public static int T = 3;

    // Maximum number of bags per passenger
    public static int M = 2;

    //Current flight number
    public static int flightNumber; 

    public static void main(final String[] args) {
        try {
            //code application logic here

            // Shared Memory Regions
            final Repository repository = new Repository();
            final ArvLounge arvLounge = new ArvLounge(repository);
            final ArvTerminalExit arvTerminalExit = new ArvTerminalExit(repository);
            final BgCollectionPoint bgCollectionPoint = new BgCollectionPoint(repository);
            final BgReclaimOffice bgReclaimOffice = new BgReclaimOffice(repository);
            final DepTerminalEntrance depTerminalEntrance = new DepTerminalEntrance(repository);
            final DepTranferQuay depTransferQuay = new DepTranferQuay(repository);
            final StoreRoom tempStorage = new StoreRoom(repository);
            final ArvTranferQuay arvTransferQuay = new ArvTranferQuay(repository, arvTerminalExit, depTerminalEntrance);
            
            
            arvTerminalExit.setDepTerminalEntrance(depTerminalEntrance);
            depTerminalEntrance.setArvTerminalExit(arvTerminalExit);


            // Entities
            Passenger[] passengers = new Passenger[N];
            Porter porter = new Porter(arvLounge, tempStorage, bgCollectionPoint, repository);
           
            BusDriver busDriver = new BusDriver(arvTransferQuay, depTransferQuay, repository);
            busDriver.start();
            porter.start();
            
            // Flights
            flightNumber = 0;
            while(flightNumber < K)
            {
                busDriver.sitPassengers(0);
                repository.init_rep(flightNumber);
                
                //Vector with the number of bags per passenger on the flight
                int[] passengersLuggage = new int[N];
                //Vector with a boolean per passenger, if true the airport is the passengers final destination
                boolean[] passengersFinalDestination = new boolean[N];
                for (int j = 0; j < N; j++) {
                    passengersLuggage[j] = new Random().nextInt(M+1);
                    passengersFinalDestination[j] = new Random().nextBoolean();
                }
                
                // Losing the bags when added to plane's hold, with equal probabilities
                int[] plainHoldLuggage = new int[N];

                for (int j = 0; j < N; j++) {
                    plainHoldLuggage[j] = new Random().nextInt(passengersLuggage[j]+1);
                }


                arvLounge.fillPlaneHold(passengersFinalDestination,plainHoldLuggage);
                
                //Instantiate, start of passengers in the current flight
                for (int i = 0; i < N; i++) {
                    passengers[i] = new Passenger(i,
                                                passengersLuggage[i],
                                                passengersFinalDestination[i],
                                                arvLounge,
                                                arvTransferQuay,
                                                arvTerminalExit,
                                                depTransferQuay,
                                                depTerminalEntrance,
                                                bgCollectionPoint,
                                                bgReclaimOffice);
                    passengers[i].start();
                }
                //Passengers join
                for (int i = 0; i < N; i++) {
                    try {
                        passengers[i].join();
                    } catch (InterruptedException e) {}
                }
                flightNumber++;
            }
            // Joins
            busDriver.join();
            porter.join();
            repository.addReport();
        }catch (Exception ex) { System.out.println(ex); }
    
    }
}

    

