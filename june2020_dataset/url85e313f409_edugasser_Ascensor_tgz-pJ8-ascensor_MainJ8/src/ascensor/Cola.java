/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ascensor;

/**
 *
 * @author eduardogasser
 */
import java.util.ArrayList;

public class Cola extends ArrayList {
    private String direccion;
    public Cola(String d){
        direccion = d;
    }
    public String getDireccion(){
        return direccion;
    }
    //se añade un elemento a la cola. Se añade al final de esta.
    public void encolar(Pasajero dato) {
        if (dato != null) {
            this.add(dato);
        } else {
            System.out.println("Introduzca un dato no nulo");
        }
    }

    //se elimina el elemento frontal de la cola, es decir, el primer elemento que entró.
    public void desencolar() {
        if (this.size() > 0) {
            this.remove(0);
        }
    }
    
    
    //se devuelve el elemento frontal de la cola, es decir, el primer elemento que entró.
    public Pasajero frente() {
        Pasajero datoAuxiliar = null;
        if (this.size() > 0) {
            datoAuxiliar = (Pasajero) this.get(0);
        }
        return datoAuxiliar;
    }

    //devuelve cierto si la pila está vacía o falso en caso contrario (empty).
    public boolean vacia() {
        return this.isEmpty();
    }
}