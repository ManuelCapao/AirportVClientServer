/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import java.io.Serializable;
/**
 *
 * @author manuel
 */
public enum PassengerStates implements StateInterface, Serializable {
    IP("IP"),
    WSD("WSD"), //what should I do
    ATT("ATT"), // at the arrival transfer terminal
    TRT("TRT"), // terminal transfer
    DTT("DTT"), //at the departure transfer terminal
    EDT("EDT"), //entering the departure terminal
    LCP("LCP"),  //at the luggage collection point
    BRO("BRO"),  //at the baggage reclaim office
    EAT("EAT"); //exiting the arrival terminal
    
    private final String value;

    PassengerStates(String value) {
        this.value = value;
    }
    @Override
    public String getValue() {
        return this.value;
    }
}
