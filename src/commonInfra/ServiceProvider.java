/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonInfra;

import proxies.ProxyInterface;

public class ServiceProvider extends Thread  {

    private ProxyInterface sharedRegion;
    private ServerCom serverCom;

    public ServiceProvider(ProxyInterface sharedRegion, ServerCom serverCom) {
        this.sharedRegion = sharedRegion;
        this.serverCom = serverCom;
    }

    /**
     * Waits for message and sends it to shared region to be processed
     */
    @Override
    public void run() {
        Message msg = (Message) serverCom.readObject();
        msg = sharedRegion.processAndReply(msg);
        serverCom.writeObject(msg);
        serverCom.close();
    }



}
