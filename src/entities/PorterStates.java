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
public enum PorterStates implements StateInterface, Serializable{
    WPTL("WPTL"),  //waiting for a plane to land
    APLH("APLH"),    //at the plane's hold
    ALCB("ALCB"),  //at the luggage conveyor belt
    ASTR("ASTR"); //at the storeroom
    
    private String value;

    PorterStates(String value) {
        this.value = value;
    }
    @Override
    public String getValue() {
        return this.value;
    }
   
}
