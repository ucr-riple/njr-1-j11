/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

/**
 *
 * @author Drew
 */
public interface GAFitness<E> {

    //  This method will return the fitness of the individual
    public double getFitness(E individual);
}
