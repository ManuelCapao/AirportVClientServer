/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxies;
import commonInfra.Message;
/**
 *
 * @author manuel
 */
public interface ProxyInterface {
    
    
    public Message processAndReply(Message message);

    public boolean simulationFinished();
}
