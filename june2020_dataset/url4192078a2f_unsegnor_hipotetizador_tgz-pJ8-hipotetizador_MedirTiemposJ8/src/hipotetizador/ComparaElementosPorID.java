/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hipotetizador;

import java.util.Comparator;

/**
 *
 * @author Victor
 */
public class ComparaElementosPorID implements Comparator<Elemento> {

    @Override
    public int compare(Elemento t, Elemento t1) {
        return t.getID() - t1.getID();
    }
    
}
