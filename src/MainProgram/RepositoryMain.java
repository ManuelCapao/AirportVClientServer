/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProgram;
import commonInfra.ServerCom;
import commonInfra.ServiceProvider;
import proxies.RepositoryProxy;
import sharedRegions.Repository;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
/**
 *
 * @author manuel
 */
public class RepositoryMain {
    public static void main(String args[]) throws FileNotFoundException {
        ServerCom serverCom, serverConn;
        ServiceProvider serviceProvider;

        Repository repository = new Repository();
        RepositoryProxy repositoryProxy = new RepositoryProxy(repository);

        serverCom = new ServerCom(Parameters.SERVER_REPOSITORY_PORT,0);
        serverCom.start();

        while(!repositoryProxy.simulationFinished()) {
            try {
                serverConn = serverCom.accept();
                serviceProvider = new ServiceProvider(repositoryProxy, serverConn);
                serviceProvider.start();
            } catch (SocketTimeoutException e) {
            }
        }
    }
}
