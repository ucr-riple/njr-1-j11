/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/**
 * Interface of moveable Items
 * @author Irakiza Krak
 */
public interface Moveable extends Item{
    @Override
    public void setName(String named);
    /**
     *
     * @param description
     */
    @Override
    public void setDescription(String description);
    @Override
    public String toString();
}
