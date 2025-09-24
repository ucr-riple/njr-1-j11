/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ascensor;

/**
 *
 * @author eduardogasser
 */
public class Pasajero implements Cloneable{
    // Declaración de atributos 

    private int piso_actual;
    private int piso_destino;
    private int tiempo_reflexion;
    private int instante_llegada;

    // Constructor 

    public Pasajero() {

        this.piso_actual = 0;
        this.piso_destino = 0;
        tiempo_reflexion = 0;
    }

    public Pasajero(String d, int destino,int id) {

        this.piso_actual = 0;
        this.piso_destino = destino;
        this.instante_llegada = id;
    }


    public int getTiempoEntrada()
    {
        return instante_llegada;
    }
    public int getPisoActual() {
        return piso_actual;
    }

    public void setPisoActual(int p) {
        piso_actual = p;
    }

    public void setPisoDestinoPasajero(int p) {
        piso_destino = p;
    }

    public float getTiempoReflexion() {
        return tiempo_reflexion;
    }

    public void setTiempoReflexion(int t) {
        tiempo_reflexion = t;
    }

    public int getPisoDestino() {
        return piso_destino;
    }
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    @Override
    public String toString() {
        return (" Pasajero:  piso_actual: " + piso_actual + "; piso_destino: " + piso_destino + " t reflexion: " + tiempo_reflexion +"tiempo_llegada: "+ instante_llegada );
    }
}
