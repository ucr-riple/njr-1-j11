package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.BagLocation;
import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import java.io.IOException;

public class StoragePanel {
    private final GWCAOperations gwcaOperations;
    private final Bag storage1, storage2, storage3, storage4, storage5, storage6, storage7, storage8, storageAnniversary;

    public StoragePanel(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
        storage1 = new Bag(gwcaOperations, BagLocation.STORAGE_1);
        storage2 = new Bag(gwcaOperations, BagLocation.STORAGE_2);
        storage3 = new Bag(gwcaOperations, BagLocation.STORAGE_3);
        storage4 = new Bag(gwcaOperations, BagLocation.STORAGE_4);
        storage5 = new Bag(gwcaOperations, BagLocation.STORAGE_5);
        storage6 = new Bag(gwcaOperations, BagLocation.STORAGE_6);
        storage7 = new Bag(gwcaOperations, BagLocation.STORAGE_7);
        storage8 = new Bag(gwcaOperations, BagLocation.STORAGE_8);
        storageAnniversary = new Bag(gwcaOperations, BagLocation.STORAGE_ANNIVERSARY);
    }
    
    /**
     * Can be used anywhere in the outpost.
     * @param amount Specify -1 if you want to deposit the maximum amount. 
     * @throws IOException 
     */
    public void depositGold(int amount) throws IOException {
        this.gwcaOperations.depositGold(amount);
    }
    
    /**
     * Can be used anywhere in the outpost.
     * @param amount Specify -1 if you want to withdraw the maximum amount.
     * @throws IOException 
     */
    public void withdrawGold(int amount) throws IOException {
        this.gwcaOperations.withdrawGold(amount);
    }

    public Bag getStorage1() {
        return storage1;
    }

    public Bag getStorage2() {
        return storage2;
    }

    public Bag getStorage3() {
        return storage3;
    }

    public Bag getStorage4() {
        return storage4;
    }

    public Bag getStorage5() {
        return storage5;
    }

    public Bag getStorage6() {
        return storage6;
    }

    public Bag getStorage7() {
        return storage7;
    }

    public Bag getStorage8() {
        return storage8;
    }

    public Bag getStorageAnniversary() {
        return storageAnniversary;
    }
    
    /**
     * Experimental function which visually opens your storage. Note: only works after opening your storage once! Else GW will crash!
     * @throws IOException 
     */
    public void openStorage() throws IOException {
        this.gwcaOperations.openStorage();
    }
}
