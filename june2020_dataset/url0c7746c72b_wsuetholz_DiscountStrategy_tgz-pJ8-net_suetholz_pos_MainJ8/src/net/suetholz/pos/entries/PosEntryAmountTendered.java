package net.suetholz.pos.entries;

import net.suetholz.pos.api.PosEntryStrategy;
import net.suetholz.pos.api.SaleOutputStrategy;
import net.suetholz.pos.api.SaleStrategy;
import net.suetholz.pos.api.StoreStrategy;

/**
 * Point of Sale Enter the Amount Tendered by Customer
 * 
 * @author wsuetholz
 * @version 1.00
 */
public class PosEntryAmountTendered implements PosEntryStrategy {

    private double amountTendered;

    public PosEntryAmountTendered ( double amountTendered ) {
	setAmountTendered(amountTendered);
    }
    
    public final double getAmountTendered() {
	return amountTendered;
    }

    public final void setAmountTendered(double amountTendered) {
	if (amountTendered < 0.0) {
	    throw new IllegalArgumentException();
	}
	this.amountTendered = amountTendered;
    }
    
    @Override
    public final void processEntry(StoreStrategy store, SaleStrategy sale,SaleOutputStrategy receiptOutput) {
	if (sale == null) {
	    throw new IllegalArgumentException();
	}
	sale.setAmountTendered (amountTendered);
	
	if (receiptOutput == null) {
	    throw new IllegalArgumentException();
	}
	
	receiptOutput.outputReceipt (store, sale);
    }
    
}
