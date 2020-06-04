/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProgram;
import commonInfra.ServerCom;
import commonInfra.ServiceProvider;
import proxies.*;
import sharedRegions.*;
import stubs.*;


import java.lang.reflect.Constructor;
import java.net.SocketTimeoutException;
/**
 *
 * @author manuel
 */
public class SharedRegionMain {
    public static void main(String args[]){
        ServerCom serverA, serverB;
        ServiceProvider service;
        try{
            String regionName = args[0];

            System.out.println("Starting Region Name: " + regionName);
            //Load by reflection the shared memory
            Class<?> shRegClass = Class.forName("sharedRegions." + regionName);

            Constructor<?> shRegConstructor = null;
            SharedRegion sharedRegion = null;
            if (regionName.equalsIgnoreCase("ArvTerminalExit")) {
                shRegConstructor = shRegClass.getConstructor(RepositoryStub.class, DepTerminalEntranceStub.class);
                sharedRegion = (SharedRegion) shRegConstructor.newInstance(
                        new RepositoryStub(Parameters.SERVER_REPOSITORY_HOSTNAME, Parameters.SERVER_REPOSITORY_PORT),
                        new DepTerminalEntranceStub(Parameters.SERVER_DEP_TERMINAL_ENTRANCE_HOSTNAME, Parameters.SERVER_DEP_TERMINAL_ENTRANCE_PORT));
            } else if (regionName.equalsIgnoreCase("DepTerminalEntrance")) {
                shRegConstructor = shRegClass.getConstructor(RepositoryStub.class, ArvTerminalExitStub.class);
                sharedRegion = (SharedRegion) shRegConstructor.newInstance(
                        new RepositoryStub(Parameters.SERVER_REPOSITORY_HOSTNAME, Parameters.SERVER_REPOSITORY_PORT),
                        new ArvTerminalExitStub(Parameters.SERVER_ARV_TERMINAL_EXIT_HOSTNAME, Parameters.SERVER_ARV_TERMINAL_EXIT_PORT));
            }
            else if (regionName.equalsIgnoreCase("ArvTransferQuay")) {
                shRegConstructor = shRegClass.getConstructor(RepositoryStub.class, ArvTerminalExitStub.class, DepTerminalEntranceStub.class);
                sharedRegion = (SharedRegion) shRegConstructor.newInstance(
                        new RepositoryStub(Parameters.SERVER_REPOSITORY_HOSTNAME, Parameters.SERVER_REPOSITORY_PORT),
                        new ArvTerminalExitStub(Parameters.SERVER_ARV_TERMINAL_EXIT_HOSTNAME, Parameters.SERVER_ARV_TERMINAL_EXIT_PORT),
                        new DepTerminalEntranceStub(Parameters.SERVER_DEP_TERMINAL_ENTRANCE_HOSTNAME, Parameters.SERVER_DEP_TERMINAL_ENTRANCE_PORT));
            } else {
                shRegConstructor = shRegClass.getConstructor(RepositoryStub.class);
                sharedRegion = (SharedRegion) shRegConstructor.newInstance(
                        new RepositoryStub(Parameters.SERVER_REPOSITORY_HOSTNAME, Parameters.SERVER_REPOSITORY_PORT));
            }

            Class<?> proxyClass = Class.forName("proxies." + regionName + "Proxy");
            Constructor<?> proxyConstructor = proxyClass.getConstructor(shRegClass);
            ProxyInterface proxy = (ProxyInterface) proxyConstructor.newInstance(sharedRegion);

            serverA = new ServerCom(Parameters.getPortForSharedRegion(regionName),1000);
            serverA.start();

            while(!proxy.simulationFinished()) {
                try {
                    serverB = serverA.accept();
                    service = new ServiceProvider(proxy, serverB);
                    service.start();
                } catch (SocketTimeoutException e) {}
            }
            System.out.printf("%s: END!\n",regionName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    
    }
    
}
