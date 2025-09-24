/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/**
 *
 * @author Irakiza Krak
 */
public class ClosedChest implements ChestStatelike{
    private String name;
    private String descr;
    private String shortC;
    

    @Override
    public void openChest(ChestChangeState ccs, String name, String descr, String shortC) {
        ccs.setState(ccs.getOpenedChest());
        this.descr = descr;
        this.name = name;
        this.shortC = shortC;
    }

    @Override
    public void closeChest(ChestChangeState ccs, String name, String descr, String shortC) {
        throw new UnsupportedOperationException("You can't close a closed chest"); //To change body of generated methods, choose Tools | Templates.
    }
    
}
