package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.BagLocation;
import be.demmel.fun.jgwcaconstants.bll.Dye;
import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import java.io.IOException;

public class Bag {
    private final BagLocation position;
    private final GWCAOperations gwcaOperations;

    public Bag(GWCAOperations gwcaOperations, BagLocation position) {
        this.gwcaOperations = gwcaOperations;
        this.position = position;
    }
    
    /**
     * @return the number of slots and the number of items currently in the bag.
     * @throws IOException 
     */
    public int[] getBagSize() throws IOException {
        return this.gwcaOperations.getBagSize(position);
    }
    
    /**
     * @param itemSlot
     * @return item id and item model id.
     * @throws IOException 
     */
    public int[] getItemId(int itemSlot) throws IOException {
        return this.gwcaOperations.getItemId(itemSlot, position);
    }
    
    /**
     * @param itemSlot
     * @throws IOException 
     */
    public void identifyItem(int itemSlot) throws IOException {
        this.gwcaOperations.identifyItem(position, itemSlot);
    }
    
    /**
     * @param itemSlot
     * @return  the attribute requirement of the item.
     * @throws IOException 
     */
    public int getItemReq(int itemSlot) throws IOException {
        return this.gwcaOperations.getItemReq(position, itemSlot);
    }
    
    /**
     * @param itemSlot
     * @return the extra id of the item - used to determine dye color etc.
     * @throws IOException 
     */
    public int getItemExtraId(int itemSlot) throws IOException {
       return this.gwcaOperations.getItemExtraId(position, itemSlot);
    }
    
    /**
     * Requires an open dialog with the Merchant of the outpost!
     * @param itemSlot
     * @throws IOException 
     */
    public void sellItem(int itemSlot) throws IOException {
        this.gwcaOperations.sellItem(position, itemSlot);
    }
    
    /**
     * @param itemSlot
     * @return item rarity and quantity.
     * @throws IOException 
     */
    public int[] getItemInfo(int itemSlot) throws IOException {
        return this.gwcaOperations.getItemInfo(itemSlot, position);
    }
    
    /**
     * Uses the item specified.
     * @param itemSlot
     * @throws IOException 
     */
    public void useItem(int itemSlot) throws IOException {
        this.gwcaOperations.useItem(position, itemSlot);
    }
    
    /**
     * Drops the specified item. If the quantity is above 1 then this command will drop it all.
     * @param itemSlot
     * @throws IOException 
     */
    public void dropItem(int itemSlot) throws IOException {
        this.gwcaOperations.dropItem(position, itemSlot);
    }
    
    /**
     * @return bag index and item slot
     * @throws IOException 
     */
    public int[] findEmptySlot() throws IOException {
        return this.gwcaOperations.findEmptySlot(position);
    }
    
    /**
     * Searches from the backpack until (and including) the bag index supplied.
     * @return  item id of the gold item found and the item's model id. If return value is 0 then no gold items were found.
     * @throws IOException 
     */
    public int[] findGoldItem() throws IOException {
        return this.gwcaOperations.findGoldItem(position);
    }
    
    /**
     * @param itemSlot
     * @throws IOException 
     */
    public void equipItem(int itemSlot) throws IOException {
        this.gwcaOperations.equipItem(position, itemSlot);
    }
    
    /**
     * @param dye
     * @return the position, if found, of the specified dye as bag,slot.
     * @throws IOException 
     */
    public int getDyePositionByColor(Dye dye) throws IOException {
        return this.gwcaOperations.getDyePositionByColor(dye, position);
    }
    
    /**
     * @param itemSlot
     * @return the damage mod of the item, with the search starting at this bag
     * @throws IOException 
     */
    public int getItemDmgMod(int itemSlot) throws IOException {
        return this.gwcaOperations.getItemDmgMod(itemSlot, position);
    }
    
    /**
     * Offers the specified item in the trade window.
     * @param itemSlot
     * @throws IOException 
     */
    public void offerItem(int itemSlot) throws IOException {
       this.gwcaOperations.offerItem(position, itemSlot);
    }
    
    /**
     * Sends a Request Quote on the specified item (remember to use the right trader for the item). Wait using CA_TraderCheck until the info is received.
     * @param itemSlot
     * @throws IOException 
     */
    public void traderRequestSell(int itemSlot) throws IOException {
       this.gwcaOperations.traderRequestSell(position, itemSlot);
    }
    
    /**
     * @param itemSlot
     * @throws IOException 
     */
    public void prepareMoveItem(int itemSlot) throws IOException {
        this.gwcaOperations.prepareMoveItem(position, itemSlot);
    }
    
    /**
     * @param itemSlot
     * @return the last modifier of the item and wchar_t* with customize text. Last modifier can be used to check which dye (model id 146) it is. If customize text = 0 then item is not customized.
     * @throws IOException 
     */
    public int[] getItemLastModifier(int itemSlot) throws IOException {
        return this.gwcaOperations.getItemLastModifier(itemSlot, position);
    }
    
}
