/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonInfra;

import java.io.*;
import java.net.*;

public class ServerCom extends GenericCom {

    private ServerSocket listeningSocket = null;
    private Socket socket = null;
    private int socketTimeout;
    private int port;


    public ServerCom (int port)
    {
        this.port = port;
        socketTimeout = 0;
    }

    public ServerCom (int port, ServerSocket listeningSocket)
    {
        this(port);
        this.listeningSocket = listeningSocket;
        socketTimeout = 0;
    }

    public ServerCom (int port, int socketTimeout)
    {
        this(port);
        this.socketTimeout = socketTimeout;
    }

    public void start ()
    {
        String errorMessage = Thread.currentThread ().getName () + ": ";
        try{
            listeningSocket = new ServerSocket (port);
            listeningSocket.setSoTimeout(socketTimeout);
        } catch (Exception ex) {
            if (ex instanceof BindException) {
                errorMessage = errorMessage.concat(String.format("Could not bind %d", port));
            } else if (ex instanceof IOException)  {
                errorMessage = errorMessage.concat(String.format("Unknown error %d", port));
            }
            System.out.println(errorMessage);
            ex.printStackTrace ();
            System.exit (1);
        }
    }

    public void end () {
        String errorMessage = Thread.currentThread ().getName () + ": ";
        try {
            listeningSocket.close ();
        }
        catch (IOException ex) {
            errorMessage = errorMessage.concat(String.format("Could not close socket %d", port));
            System.out.println(errorMessage);
            ex.printStackTrace ();
            System.exit (1);
        }
    }

    public ServerCom accept() throws SocketTimeoutException {
        ServerCom connection;                                      // canal de comunicação
        String errorMessage = Thread.currentThread ().getName () + ": ";
        connection = new ServerCom(port, listeningSocket);
        try {
            connection.socket = listeningSocket.accept();
        } catch (Exception ex) {
            if (ex instanceof SocketException) {
                errorMessage = errorMessage.concat("The Listening socket was closed");
            } else if (ex instanceof SocketTimeoutException) {
                throw (SocketTimeoutException)ex;
            }else if (ex instanceof IOException) {
                errorMessage = errorMessage.concat("Could not open a communication channel");
            }
            System.out.println(errorMessage);
            ex.printStackTrace ();
            System.exit (1);
        }

        try {
            connection.setIn(new ObjectInputStream (connection.socket.getInputStream ()));
        } catch (Exception ex) {
            errorMessage = errorMessage.concat("Could not open IN channel of the socket");
            System.out.println(errorMessage);
            ex.printStackTrace ();
            System.exit (1);
        }

        try{
            connection.setOut(new ObjectOutputStream (connection.socket.getOutputStream ()));
        }catch (Exception ex) {
            errorMessage = errorMessage.concat("Could not open OUT channel of the socket");
            System.out.println(errorMessage);
            ex.printStackTrace ();
            System.exit (1);
        }

        return connection;
    }

    public void close () {
        String errorMessage = Thread.currentThread ().getName () + ": ";
        try {
            this.getIn().close();
        } catch (Exception ex) {
            errorMessage = errorMessage.concat("Could not close IN channel!");
            System.out.println(errorMessage);
            ex.printStackTrace ();
            System.exit (1);
        }

        try {
            this.getOut().close();
        }catch (Exception ex){
            errorMessage = errorMessage.concat("Could not close OUT channel!");
            System.out.println(errorMessage);
            ex.printStackTrace ();
            System.exit (1);
        }

        try{
            socket.close();
        } catch (Exception ex){
            errorMessage = errorMessage.concat("Could not close communication socket!");
            System.out.println(errorMessage);
            ex.printStackTrace ();
            System.exit (1);
        }
    }



}
