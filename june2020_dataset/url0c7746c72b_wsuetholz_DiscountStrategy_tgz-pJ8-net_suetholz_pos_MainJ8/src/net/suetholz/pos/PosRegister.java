/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 employee/clerk
 store id
 register id
 sale id
 total sales by store and by customer
 daily sales by store and customer
 buy 3 get 1 discount
 special discounts by customer
 special store discounts
 */
package net.suetholz.pos;

import net.suetholz.pos.api.PosEntryStrategy;
import net.suetholz.pos.api.SaleInputStrategy;
import net.suetholz.pos.api.SaleOutputStrategy;
import net.suetholz.pos.api.SaleStrategy;
import net.suetholz.pos.api.StoreStrategy;

/**
 * This class encapsulates the Point of Sale Register Information
 *
 * @author wsuetholz
 * @version 1.00
 */
public class PosRegister {

    private int registerId;
    private StoreStrategy store;
    private SaleStrategy currentSale;
    private SaleInputStrategy lineInput;
    private SaleOutputStrategy receiptOutput;
    private boolean keepRunning;

    public PosRegister(int registerId, StoreStrategy store, SaleInputStrategy lineInput, SaleOutputStrategy receiptOutput) {
	setRegisterId (registerId);
	setStore(store);
	setLineInput(lineInput);
	setReceiptOutput(receiptOutput);

	// The Store object knows the proper way to allocate a sale object.
	currentSale = store.allocateSale();
	
	keepRunning = true;
    }

    public final int getRegisterId() {
	return registerId;
    }

    public final void setRegisterId(int registerId) {
	if (registerId <= 0) {
	    throw new IllegalArgumentException();
	}
	this.registerId = registerId;
    }

    /**
     * Returns the current value of what store this register is assigned to
     *
     * @return store
     */
    public final StoreStrategy getStore() {
	return store;
    }

    /**
     * Sets the current store this register is assigned to.
     *
     * Validates store to be non-null
     *
     * @param store
     *
     * @throws IllegalArgumentException if store is null.
     */
    public final void setStore(StoreStrategy store) {
	if (store == null) {
	    throw new IllegalArgumentException();
	}
	this.store = store;
    }

    /**
     * Gets the sale being currently rung up.
     *
     * @return current sale
     */
    public final SaleStrategy getCurrentSale() {
	return currentSale;
    }

    /**
     * Get the current input strategy for a sale line item for this register.
     *
     * @return Current Line Item Input Strategy
     */
    public final SaleInputStrategy getLineInput() {
	return lineInput;
    }

    /**
     * Sets the input strategy for a sale line item for this register.
     *
     * Validates lineInput to be non-null
     *
     * @param lineInput
     * @throws IllegalArgumentException if lineInput is null.
     */
    public final void setLineInput(SaleInputStrategy lineInput) {
	if (lineInput == null) {
	    throw new IllegalArgumentException();
	}
	this.lineInput = lineInput;
    }

    /**
     * Gets the receipt output strategy
     *
     * @return current receipt output strategy
     */
    public final SaleOutputStrategy getReceiptOutput() {
	return receiptOutput;
    }

    /**
     * Sets the current receipt output strategy for this register
     *
     * @param receiptOutput
     */
    public final void setReceiptOutput(SaleOutputStrategy receiptOutput) {
	if (receiptOutput == null) {
	    throw new IllegalArgumentException();
	}
	this.receiptOutput = receiptOutput;
    }

    /**
     * Set the flag that indicates that the register should keep running or not
     * 
     * @param keepRunning 
     */
    public final void setKeepRunning(boolean keepRunning) {
	this.keepRunning = keepRunning;
    }

    /**
     * Drive the POS Register Terminal
     * 
     * This is ready to be run as a thread...
     */
    public final void run() {
	while (keepRunning) {
	    PosEntryStrategy posEntry = lineInput.getInput();
	    if (posEntry != null) {
		posEntry.processEntry(store, currentSale, receiptOutput);
	    }
	}
    }
}
