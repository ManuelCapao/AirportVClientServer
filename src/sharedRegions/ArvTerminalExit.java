/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedRegions;
import stubs.*;
import entities.*;
/**
 *
 * @author manuel
 */
public class ArvTerminalExit implements SharedRegion{
    
    private DepTerminalEntranceStub dep;
    private RepositoryStub rep;
    private int countFinish;

    public ArvTerminalExit(RepositoryStub rep,DepTerminalEntranceStub dep){
        this.countFinish = 0;
        this.rep = rep;
        this.dep = dep;
        
    }
    /* Metodos do Passenger */

    /**
     * Passenger Goes Home
     */
    public synchronized void goHome(int id){
        
        rep.setPassengerState(id,PassengerStates.EAT); //set passenger state to "exiting the arrival terminal"
         //sets state of the passenger with ID in the repository to "exiting the arrival terminal"
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
   
    public int getCount(){
        synchronized (this){
            return this.countFinish;
        }

    }
    
    /**
     * acorda passageiros que estao a espera para sair do aeroporto
     */
    public synchronized void notifyPassengers(){
        notifyAll();
    }
}
