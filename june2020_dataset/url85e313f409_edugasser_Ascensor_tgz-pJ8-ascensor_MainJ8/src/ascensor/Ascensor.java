/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ascensor;

import java.util.ArrayList;

/**
 *
 * @author eduardogasser
 */
public class Ascensor {

    private String direccion;
    private int proxima_parada; // proxima parada del de los pasajeros. No tiene encuenta peticiones externas.
    private boolean viajando;
    private ArrayList<Pasajero> suben;
    private ArrayList<Pasajero> bajan;
    // Constructor 

    public Ascensor() {
        direccion = "ASC";
        //this.piso_actual = 0;
        //this.piso_destino = 0;
        //num_pasajeros = 0;
        proxima_parada = 10;
        suben = new ArrayList<Pasajero>();
        bajan = new ArrayList<Pasajero>();

        viajando = false;

    }
    public void clear(){
        this.direccion  = "ASC";
        proxima_parada = 10;
        suben.clear();
        bajan.clear();
        viajando = false;
    }
    public void setViajando(boolean b) {
        viajando = b;
    }

    public boolean getViajando() {
        return viajando;
    }

    public ArrayList<Pasajero> getPasajeros() {
        /* devolvemos la lista de los pasajeros del ascensor dependiendo de su dirección */

        if ("ASC".equals(direccion)) {
            return suben;
        } else {
            return bajan;
        }
    }
 
    public void setPasajeroSube(Pasajero p) {
        suben.add(p);

    }

    public void setPasajeroBaja(Pasajero p) {
        bajan.add(p);
    }

    public void setProximaParada(int p) {
        proxima_parada = p;
    }

    public int getProximaParada() {
        return proxima_parada;
    }
    public int getNumPasajeros() {
        return Math.max(suben.size(), bajan.size());
    }

    public void setDireccion(String d) {
        this.direccion = d;
    }

    public String getDireccion() {
        return direccion;
    }
}
