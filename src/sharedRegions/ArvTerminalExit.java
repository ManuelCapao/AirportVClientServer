/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;
import entities.*;
/**
 *
 * @author manuel
 */
public class ArvTerminalExit {
    
    private DepTerminalEntrance dep;
    private Repository rep;
    private int countFinish;

    public ArvTerminalExit(Repository rep){
        this.countFinish = 0;
        this.rep = rep;
        
    }
    /* Metodos do Passenger */

    /**
     * Passenger Goes Home
     */
    public synchronized void goHome(){
        Passenger passenger = (Passenger) Thread.currentThread();
        passenger.setState(PassengerStates.EAT); //set passenger state to "exiting the arrival terminal"
        rep.setPassengerState(passenger.getID(), PassengerStates.EAT); //sets state of the passenger with ID in the repository to "exiting the arrival terminal"
        //ponto de sincronizaçao
        //acorda passageiros que estao a espera par sair do aeroporto
        try{
            this.countFinish++;
            if((this.countFinish + this.dep.getCount()) % 6 == 0){
                this.dep.notifyPassengers();
                notifyAll();
            }
            else{
                wait();
            }
        } catch (InterruptedException e) {}
        
        
    }
    

    public void setDepTerminalEntrance(DepTerminalEntrance dep){
        this.dep = dep;
    }
    public synchronized int getCount(){
        return this.countFinish;
    }
    
    /**
     * acorda passageiros que estao a espera par sair do aeroporto
     */
    public synchronized void notifyPassengers(){
        notifyAll();
    }
}
