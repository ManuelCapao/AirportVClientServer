/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProgram;
import java.util.Date;
/**
 *
 * @author manuel
 */
public class Parameters {
    
    public static final boolean DEBUG_MODE = true;

    /**
     * Number of Passengers
     */
    public static final int PASSENGERS = 6;

    /**
     * Number of Plane Landings
     */
    public static final int LANDINGS = 5 ;

    /**
     * Maximum number of pieces of luggage by each passenger
     */
    public static final int LUGGAGE = 2;

    /**
     * Maximum Capacity of seating places in the bus
     */
    public static final int BUS_CAPACITY = 3;

    public static final int BUS_DRIVER_SCHEDULE_MILLIS = 1000;

    public static final int BUS_DRIVER_END_OF_DAY_DURATION_MILLIS = 5000;

    public static final int BUS_DRIVER_END_OF_DAY_SLEEP_MILLIS = 100;


    /**
     * Minimum milliseconds to wakeup.
     */
    public static final int MIN_SLEEP = 5;

    /**
     * Maximum milliseconds to wakeup.
     */
    public static final int MAX_SLEEP = 10;




    public static final int REPORT_MISSING_BAG_SLEEP = 100; //Millis

    /**
     * Repository log filename
     */
    public static final String FILENAME = "log_" + new Date().toString().replace(' ', '_') + ".txt";
    public static final String SERVER_REPOSITORY_HOSTNAME = "localhost";
    public static final int SERVER_REPOSITORY_PORT = 8080;

    public static final String SERVER_ARV_LOUNGE_HOSTNAME = "localhost";
    public static final int SERVER_ARV_LOUNGE_PORT = 8081;

    public static final String SERVER_ARV_TERMINAL_EXIT_HOSTNAME = "localhost";
    public static final int SERVER_ARV_TERMINAL_EXIT_PORT = 8082;

    public static final String SERVER_ARV_TERMINAL_TRANSFER_QUAY_HOSTNAME = "localhost";
    public static final int SERVER_ARV_TERMINAL_TRANSFER_QUAY_PORT = 8083;

    public static final String SERVER_BG_COLLECTION_POINT_HOSTNAME = "localhost";
    public static final int SERVER_BG_COLLECTION_POINT_PORT = 8084;

    public static final String SERVER_BG_RECLAIM_OFFICE_HOSTNAME = "localhost";
    public static final int SERVER_BG_RECLAIM_OFFICE_PORT = 8085;

    public static final String SERVER_DEP_TERMINAL_ENTRANCE_HOSTNAME = "localhost";
    public static final int SERVER_DEP_TERMINAL_ENTRANCE_PORT = 8086;

    public static final String SERVER_DEP_TERMINAL_TRANSFER_QUAY_HOSTNAME = "localhost";
    public static final int SERVER_DEP_TERMINAL_TRANSFER_QUAY_PORT = 8087;

    public static final String SERVER_STORE_ROOM_HOSTNAME = "localhost";
    public static final int SERVER_STORE_ROOM_PORT = 8088;

    public static String getHostName(String region) {
        String c;
        switch(region){
            case "ArvTerminalExit":
                c = SERVER_ARV_TERMINAL_EXIT_HOSTNAME;
                break;
            case "ArvLounge":
                c = SERVER_ARV_LOUNGE_HOSTNAME;
                break;
            case "ArvTerminalTransferQuay":
                c = SERVER_ARV_TERMINAL_TRANSFER_QUAY_HOSTNAME;
                break;
            case "BgCollectionPoint":
                c = SERVER_BG_COLLECTION_POINT_HOSTNAME;
                break;
            case "BgReclaimOffice":
                c = SERVER_BG_RECLAIM_OFFICE_HOSTNAME;
                break;
            case "DepTerminalEntrance":
                c = SERVER_DEP_TERMINAL_ENTRANCE_HOSTNAME;
                break;
            case "DepTerminalTransferQuay":
                c = SERVER_DEP_TERMINAL_TRANSFER_QUAY_HOSTNAME;
                break;
            case "StoreRoom":
                c = SERVER_STORE_ROOM_HOSTNAME;
                break;
            default:
                c = null;
                break;
        }
        return c;
    }

    public static int getPortForSharedRegion(String region) {
        int c;
        switch(region){
            case "ArvTerminalExit":
                c = SERVER_ARV_TERMINAL_EXIT_PORT;
                break;
            case "ArvLounge":
                c =  SERVER_ARV_LOUNGE_PORT;
                break;
            case "ArvTerminalTransferQuay":
                c =  SERVER_ARV_TERMINAL_TRANSFER_QUAY_PORT;
                break;
            case "BgCollectionPoint":
                c =  SERVER_BG_COLLECTION_POINT_PORT;
                break;
            case "BgReclaimOffice":
                c =  SERVER_BG_RECLAIM_OFFICE_PORT;
                break;
            case "DepTerminalEntrance":
                c =  SERVER_DEP_TERMINAL_ENTRANCE_PORT;
                break;
            case "DepTerminalTransferQuay":
                c =  SERVER_DEP_TERMINAL_TRANSFER_QUAY_PORT;
                break;
            case "StoreRoom":
                c =  SERVER_STORE_ROOM_PORT;
                break;
            default:
                c = -1;
        }
        return c;
    }
}
