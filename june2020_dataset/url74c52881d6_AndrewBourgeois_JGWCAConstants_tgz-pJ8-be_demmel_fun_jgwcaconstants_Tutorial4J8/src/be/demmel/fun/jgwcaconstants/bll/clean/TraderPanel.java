package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import java.io.IOException;

public class TraderPanel {
    private final GWCAOperations gwcaOperations;

    public TraderPanel(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
    }
    
    /**
     * For the Rune trader experiment around. Use this to setup CA_TraderBuy.
     * @param itemIndex the number on the list at the trader.
     * @throws IOException 
     */
    public void traderRequest(int itemIndex) throws IOException {
       this.gwcaOperations.traderRequest(itemIndex);
    }
    
    /**
     * Loop this until the item id or cost is non-zero to verify that the info has been received before calling CA_TraderBuy.
     * @return the trader item id and it's cost that is currently active.
     * @throws IOException 
     */
    public int[] traderCheck() throws IOException {
        return this.gwcaOperations.traderCheck();
    }
    
    /**
     * Requires a CA_TraderRequest and waiting until item id and cost has been received before it works!
     * @return whether the packet was sent or not as boolean.
     * @throws IOException 
     */
    public boolean traderBuy() throws IOException {
        return this.gwcaOperations.traderBuy();
    }
    
    /**
     * Same as CA_TraderRequestSell, but instead, simply takes an item id as input.
     * @param itemId
     * @throws IOException 
     */
    public void traderRequestSellById(int itemId) throws IOException {
       this.gwcaOperations.traderRequestSellById(itemId);
    }
    
    /**
     * Requires a CA_TraderRequestSell(ById) and waiting until item id and cost has been received before it works!
     * @return whether the packet was sent or not as boolean.
     * @throws IOException 
     */
    public boolean traderSell() throws IOException {
        return this.gwcaOperations.traderSell();
    }
    
    /**
     * Buys the specified item if you used the right gold cost.
     * @param merchantItemIndex (starting at 1)
     * @param goldCost
     * @throws IOException 
     */
    public void buyItem(int merchantItemIndex, int goldCost) throws IOException {
        this.gwcaOperations.buyItem(merchantItemIndex, goldCost);
    }
    
    /**
     * Requires an open dialog with the Merchant of the outpost!
     * @throws IOException 
     */
    public void buySuperiorIdKit() throws IOException {
        this.gwcaOperations.buySuperiorIdKit();
    }
    
    /**
     *  Requires an open dialog with the Merchant of the outpost!
     * @throws IOException 
     */
    public void buyIdKit() throws IOException {
        this.gwcaOperations.buyIdKit();
    }
    
    /**
     * Requires an open dialog with the Merchant of the outpost!
     * @param itemId
     * @throws IOException 
     */
    public void sellItemById(int itemId) throws IOException {
        this.gwcaOperations.sellItemById(itemId);
    }
}
