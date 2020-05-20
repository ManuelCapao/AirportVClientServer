package commonInfra;
import java.io.Serializable;
/**
 * Structure of the message to be exchanged between
 * client and server
 */

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private MessageType msgType;
    public Message(MessageType msgType){
        this.msgType = msgType;
    }
    public MessageType getMessageType(){return msgType;}
    
}