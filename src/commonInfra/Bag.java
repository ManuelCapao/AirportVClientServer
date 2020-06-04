/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commonInfra;


import java.io.Serializable;

/**
 *
 * @author manuel
 */
public class Bag implements Serializable {
    private int passengerID; // "Passenger" a quem pertence a mala.
    private boolean Destination; // Variavel definida para saber se a mala chegou ao destino.
    
    public Bag(int passenger, boolean destination) {
        this.passengerID = passenger;
        this.Destination = destination;
    }

    
    public long getPassenger() {
        return passengerID;
    }
    public boolean getDestination() {
        return Destination;
    }

}
