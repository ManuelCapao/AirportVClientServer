/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stubs;


import commonInfra.*;

/**
 *
 * @author manuel
 */
public class GenericStub {
    
    private String hostname;
    private int port;


    public GenericStub(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public Message process(Message message) {
        ClientCom cc = new ClientCom(hostname,port);
        cc.open();
        cc.writeObject(message);

        return (Message) cc.readObject();
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
