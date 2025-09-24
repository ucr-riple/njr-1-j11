package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.BagLocation;
import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import be.demmel.fun.jgwcaconstants.bll.Rarity;
import java.io.IOException;

public class InventoryPanel {
    private final GWCAOperations gwcaOperations;
    private final Bag backPack, beltPouch, bag1, bag2, equipmentPack;

    public InventoryPanel(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
        backPack = new Bag(gwcaOperations, BagLocation.BACKPACK);
        beltPouch = new Bag(gwcaOperations, BagLocation.BELT_POUCH);
        bag1 = new Bag(gwcaOperations, BagLocation.BAG1);
        bag2 = new Bag(gwcaOperations, BagLocation.BAG2);
        equipmentPack = new Bag(gwcaOperations, BagLocation.EQUIPMENT_PACK);
    }
    
    /**
     * @return gold on your character and gold in your storage.
     * @throws IOException 
     */
    public int[] getGold() throws IOException {
        return this.gwcaOperations.getGold();
    }
    
    /**
     * drops the gold on the ground
     * @param goldAmount
     * @throws IOException 
     */
    public void dropGold(int goldAmount) throws IOException {
       this.gwcaOperations.dropGold(goldAmount);
    }

    public Bag getBackPack() {
        return backPack;
    }

    public Bag getBag1() {
        return bag1;
    }

    public Bag getBag2() {
        return bag2;
    }

    public Bag getBeltPouch() {
        return beltPouch;
    }

    public Bag getEquipmentPack() {
        return equipmentPack;
    }
    
    /**
     * @param bag the bag to work with
     * @throws IOException 
     */
    //FIXME: does this have any use for us??
    public void setBag(BagLocation bag) throws IOException {
        this.gwcaOperations.setBag(bag);
    }   
    
    /**
     * @return item id of the first ID kit it finds. If return is non-zero then you have an ID kit in your inventory.
     * @throws IOException 
     */
    public int getIdKit() throws IOException {
        return this.gwcaOperations.getIdKit();
    }
    
    /**
     *  If you've returned the item id from one of the prior commands you can use this command instead of CA_IdentifyItem.
     * @param itemId
     * @throws IOException 
     */
    public void identifyItemById(int itemId) throws IOException {
        this.gwcaOperations.identifyItemById(itemId);
    }
    
    /**
     * @param itemModelId
     * @return the first occurence of the specified model id and returns integer with the item id or 0 if item wasn't found.
     * @throws IOException 
     */
    public int findItemByModelId(int itemModelId) throws IOException {
        return this.gwcaOperations.findItemByModelId(itemModelId);
    }
    
    /**
     * @param itemId
     * @return the position of the item; bag and slot-wise. If any of the returns are 0, the item was not found.
     * @throws IOException 
     */
    public int[] getItemPositionByItemId(int itemId) throws IOException {
        return this.gwcaOperations.getItemPositionByItemId(itemId);
    }
    
    /**
     * @param modelId
     * @return the position of the item; bag and slot-wise. If any of the returns are 0, the item was not found.
     * @throws IOException 
     */
    public int[] getItemPositionByModelId(int modelId) throws IOException {
        return this.gwcaOperations.getItemPositionByModelId(modelId);
    }
    
    /**
     * @param rarity
     * @return the position of the item; bag and slot-wise. If any of the returns are 0, the item was not found.
     * @throws IOException 
     */
    public int[] getItemPositionByRarity(Rarity rarity) throws IOException {
        return this.gwcaOperations.getItemPositionByRarity(rarity);
    }
    
    /**
     * @param itemId
     * @return the model id of the specified item.
     * @throws IOException 
     */
    public int getItemModelIdById(int itemId) throws IOException {
        return this.gwcaOperations.getItemModelIdById(itemId);
    }
    
    /**
     * @param itemId
     * @return the rarity and quantity of the specified item.
     * @throws IOException 
     */
    public int[] getItemInfoById(int itemId) throws IOException {
        return this.gwcaOperations.getItemInfoById(itemId);
    }
    
    /**
     * Only works for offhands/shields!
     * @param itemId
     * @return  the damage mod of the item
     * @throws IOException 
     */
    public int getItemDmgModById(int itemId) throws IOException {
        return this.gwcaOperations.getItemDmgModById(itemId);
    }
    
    /**
     * Uses the item specified.
     * @param itemId
     * @throws IOException 
     */
    public void useItemById(int itemId) throws IOException {
        this.gwcaOperations.useItemById(itemId);
    }
    
    /**
     * Drops the specified item by the specified amount. Setting amount to -1 will drop it all.
     * @param itemId
     * @throws IOException 
     */
    public void dropItemById(int itemId) throws IOException {
        this.gwcaOperations.dropItemById(itemId);
    }
    
    /**
     * @param itemId
     * @throws IOException 
     */
    public void equipItemById(int itemId) throws IOException {
        this.gwcaOperations.equipItemById(itemId);
    }
    
    /**
     * @param itemId
     * @return  the attribute requirement of the item.
     * @throws IOException 
     */
    public int getItemReqById(int itemId) throws IOException {
        return this.gwcaOperations.getItemReqById(itemId);
    }
    
    /**
     * @param itemId
     * @return the extra id of the item - used to determine dye color etc.
     * @throws IOException 
     */
    public int getItemExtraIdById(int itemId) throws IOException {
        return this.gwcaOperations.getItemExtraIdById(itemId);
    }
    
    /**
     * @return the item id if it finds one 
     * @throws IOException 
     */
    public int getSalvageKit() throws IOException {
        return this.gwcaOperations.getSalvageKit();
    }
    
    /**
     * @param itemId
     * @throws IOException 
     */
    public void prepareMoveItem(int itemId) throws IOException {
        this.gwcaOperations.prepareMoveItem(itemId);
    }
    
    /**
     * @param targetBag
     * @param targetItemSlot
     * @throws IOException 
     */
    public void moveItem(BagLocation targetBag, int targetItemSlot) throws IOException {
        this.gwcaOperations.moveItem(targetBag, targetItemSlot);
    }
    
    /**
     * Moves an item from one location to another
     * @param currentBag
     * @param currentItemSlot
     * @param targetBag
     * @param targetItemSlot
     * @throws IOException 
     */
    public void moveItem(BagLocation currentBag, int currentItemSlot, BagLocation targetBag, int targetItemSlot) throws IOException {
        this.gwcaOperations.moveItem(currentBag, currentItemSlot, targetBag, targetItemSlot);
    }
    
    /**
     * Moves an item from one location to another
     * @param itemId
     * @param targetBag
     * @param targetItemSlot
     * @throws IOException 
     */
    public void moveItemById(int itemId, BagLocation targetBag, int targetItemSlot) throws IOException {
        this.gwcaOperations.moveItemById(itemId, targetBag, targetItemSlot);
    }
    
    /**
     * @param itemId
     * @return the last modifier of the item and wchar_t* with customize text. Last modifier can be used to check which dye (model id 146) it is. If customize text = 0 then item is not customized.
     * @throws IOException 
     */
    public int[] getItemLastModifierById(int itemId) throws IOException {
        return this.gwcaOperations.getItemLastModifierById(itemId);
    }
}
