package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import java.io.IOException;

public class TradePanel {
    private final GWCAOperations gwcaOperations;

    public TradePanel(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
    }
    
    /**
     * Like pressing the "Submit Offer" button, but also including the amount of gold offered.
     * @param goldAmount
     * @throws IOException 
     */
    public void submitOffer(int goldAmount) throws IOException {
        this.gwcaOperations.submitOffer(goldAmount);
    }
    
    /**
     * Like pressing the "Change Offer" button.
     * @throws IOException 
     */
    public void changeOffer() throws IOException {
        this.gwcaOperations.changeOffer();
    }
    
    /**
     * Like pressing the "Cancel" button in a trade.
     * @throws IOException 
     */
    public void cancelTrade() throws IOException {
        this.gwcaOperations.cancelTrade();
    }
    
    /**
     * Like pressing the "Accept" button in a trade. Can only be used after both players have submitted their offer.
     * @throws IOException 
     */
    public void acceptTrade() throws IOException {
        this.gwcaOperations.acceptTrade();
    }
    
    /**
     * Offers the specified item in the trade window.
     * @param itemId
     * @throws IOException 
     */
    public void offerItem(int itemId) throws IOException {
       this.gwcaOperations.offerItem(itemId);
    }
}
