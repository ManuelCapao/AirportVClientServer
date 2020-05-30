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
public enum BusDriverStates  implements StateInterface, Serializable{
    PKAT("PKAT"), //parking at the arrival terminal
    DRFW("DRFW"), //driving forward
    PKDT("PKDT"), //parking at the departure terminal
    DRBW("DRBW"); //driving backwars
    
    private String value;

    BusDriverStates(String value) {
        this.value = value;
    }
    @Override
    public String getValue() {
        return this.value;
    }
    

}
