/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonInfra;



import java.io.*;
import java.net.*;

public class ClientCom extends GenericCom {

    private Socket socket = null;

    private String hostname = null;
    private int port;

    public ClientCom (String hostname, int port)
    {
        this.hostname = hostname;
        this.port = port;
    }

    public boolean open ()
    {
        String errorMessage = Thread.currentThread ().getName () + ": ";
        boolean keepAlive = false;
        SocketAddress serverAddress = new InetSocketAddress(hostname, port);

        try {
            socket = new Socket();
            socket.connect (serverAddress);
        }
        catch (Exception ex) {
            if (ex instanceof UnknownHostException ) {
                errorMessage = errorMessage.concat(String.format("The destination server is Unknown %s - %d", hostname,port));
            } else if (ex instanceof NoRouteToHostException ) {
                errorMessage = errorMessage.concat(String.format("No Route to Host %s - %d", hostname,port));
            } else if (ex instanceof ConnectException  ) {
                errorMessage = errorMessage.concat(String.format("The server is not replying %s - %d", hostname,port));
                if (ex.getMessage ().equals ("Connection refused")) keepAlive = true;
            } else if  (ex instanceof SocketTimeoutException  ) {
                errorMessage = errorMessage.concat(String.format("Socket Timeout to host %s - %d", hostname,port));
                keepAlive = true;
            } else if  (ex instanceof IOException  ) {
                errorMessage = errorMessage.concat(String.format("Unspecified error connecting to host %s - %d", hostname,port));
            }

            if (keepAlive) return true;

            System.out.println(errorMessage);
            ex.printStackTrace ();
            System.exit (1);
        }


        try {
            this.setOut(new ObjectOutputStream (socket.getOutputStream ()));
        }
        catch (IOException e)
        {
            errorMessage = errorMessage.concat(String.format("Could not open OUT channel on the socket %s - %d", hostname,port));
            System.out.println(errorMessage);
            e.printStackTrace ();
            System.exit (1);
        }

        try {
            this.setIn(new ObjectInputStream (socket.getInputStream ()));
        }
        catch (IOException e)
        {
            errorMessage = errorMessage.concat(String.format("Could not open IN channel on the socket %s - %d", hostname,port));
            System.out.println(errorMessage);
            e.printStackTrace ();
            System.exit (1);
        }

        return (keepAlive);
    }


    public void close () {
        String errorMessage = Thread.currentThread ().getName () + ": ";
        try {
            this.getIn().close();
        } catch (IOException e) {
            errorMessage.concat("Couldn't close IN channel of the socket!");
            System.out.println(errorMessage);
            e.printStackTrace ();
            System.exit (1);
        }

        try {
            this.getOut().close();
        }
        catch (IOException e) {
            errorMessage.concat("Couldn't close OUT channel of the socket!");
            System.out.println(errorMessage);
            e.printStackTrace ();
            System.exit (1);
        }

        try {
            socket.close();
        }
        catch (IOException e) {
            errorMessage.concat("Couldn't close socket!");
            System.out.println(errorMessage);
            e.printStackTrace ();
            System.exit (1);
        }
    }


}
