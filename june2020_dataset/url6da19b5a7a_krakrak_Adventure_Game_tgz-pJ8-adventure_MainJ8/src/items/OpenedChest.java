/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/**
 *
 * @author Irakiza Krak
 */
public class OpenedChest implements ChestStatelike{

    private String name;
    private String descr;
    private String shortC;
    /**
     * close an opened chest
     * @param STATE_CONTEXT
     * @param name
     * @param descr
     * @param shortC
     */
    
    @Override
    public void closeChest(ChestChangeState ccs, String name, String descr, String shortC) {
        ccs.setState(ccs.getClosedChest());
        this.name = name;
        this.descr = descr;
        this.shortC = shortC;
        
    }

    @Override
    public void openChest(ChestChangeState ccs, String name, String descr, String ShortC) {
        throw new UnsupportedOperationException("You can't open an opened chest"); //To change body of generated methods, choose Tools | Templates.
    }
    
}
