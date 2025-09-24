/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.input;

import net.suetholz.pos.Product;
import net.suetholz.pos.api.CustomerStrategy;
import net.suetholz.pos.api.PersistantStorageStrategy;
import net.suetholz.pos.api.PosEntryStrategy;
import net.suetholz.pos.api.SaleInputStrategy;
import net.suetholz.pos.entries.PosEntryAmountTendered;
import net.suetholz.pos.entries.PosEntryClearKey;
import net.suetholz.pos.entries.PosEntryCustomer;
import net.suetholz.pos.entries.PosEntryProductNQuantity;
import net.suetholz.pos.lineitem.BasicLineItem;

/**
 *
 * @author wsuetholz
 */
public class FakeRegisterInput implements SaleInputStrategy {

    private static final int ENTRY_INCREMENT = 10;
    private PersistantStorageStrategy storage;
    private PosEntryStrategy[] posEntries;
    private int posNumEntries;
    private int posIndex;
    
    public FakeRegisterInput (PersistantStorageStrategy storage) {
	if (storage == null) {
	    throw new IllegalArgumentException();
	}
	this.storage = storage;
	posEntries = new PosEntryStrategy[ENTRY_INCREMENT];
	posNumEntries = 0;
	posIndex = 0;
    }
    
    public final void addEntry (PosEntryStrategy entry) {
	if (entry == null) {
	    throw new IllegalArgumentException();
	}
	
	// See if there is enough room in the array and if not expand the array by INCREMENT slots.
	if (posNumEntries >= posEntries.length) {
	    PosEntryStrategy[] posTempEntries = new PosEntryStrategy[posEntries.length+ENTRY_INCREMENT];
	    for (int i = 0; i < posNumEntries; i++) {
		posTempEntries[i] = posEntries[i];
	    }
	    posEntries = posTempEntries;
	}
	
	posEntries[posNumEntries++] = entry;
    }
    
    public final void addClearKey() {
	addEntry (new PosEntryClearKey());
    }
    
    public final void addAmountTendered (double amountTendered) {
	addEntry (new PosEntryAmountTendered(amountTendered));
    }
    
    public final void addCustomer (String customerId) {
	if (customerId == null) {
	    throw new IllegalArgumentException();
	}
	
	CustomerStrategy customer = storage.lookupCustomer(customerId);
	if (customer != null) {
	    addEntry (new PosEntryCustomer(customer));
	}
    }
    
    public final void addProductNQuantity(String productId, int quantity) {
	if (productId == null) {
	    throw new IllegalArgumentException();
	}
	if (quantity < 0) {
	    throw new IllegalArgumentException();
	}
	
	Product product = storage.lookupProduct(productId);
	if(product != null) {
	    addEntry (new PosEntryProductNQuantity (new BasicLineItem(product, quantity)));
	}
    }
    
    @Override
    public final PosEntryStrategy getInput() {
	if (posIndex >= posNumEntries) {
	    try {
		Thread.sleep(5000);
	    } catch (InterruptedException ex) {
		
	    }
	    return null;
	}
	return (posEntries[posIndex++]);
    }
    
}
