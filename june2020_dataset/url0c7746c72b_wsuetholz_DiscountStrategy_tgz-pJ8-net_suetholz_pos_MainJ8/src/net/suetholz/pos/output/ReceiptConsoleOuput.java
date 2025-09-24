/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.output;

import net.suetholz.pos.api.SaleOutputStrategy;
import net.suetholz.pos.api.SaleStrategy;
import net.suetholz.pos.api.StoreStrategy;

/**
 *
 * @author wsuetholz
 */
public class ReceiptConsoleOuput implements SaleOutputStrategy {

    @Override
    public final void outputReceipt(StoreStrategy store, SaleStrategy sale) {
	String dashes = "----------------------------------------------------------------------------------------------------------";
	String dbdashes = "==========================================================================================================";
	String spaces = "                                                                                                          ";
	
	System.out.format("%87.87s%n", dbdashes);
	System.out.format("%12.12s %61.61s %12.12s%n",
			  dbdashes, sale.getGreeting(store), dbdashes);
	System.out.format("%87.87s%n", dbdashes);
	System.out.format("%10.10s %30.30s %5.5s %10.10s %10.10s %6.6s %10.10s%n",
			  "Prod ID", "Description", "Qty", "Unit $", "Ext Amt", "Disc", "Line Tot");
	System.out.format("%10.10s %-30.30s %5.5s %10.10s %10.10s %6.6s %10.10s%n",
			  dashes, dashes, dashes, dashes, dashes, dashes, dashes);
	int maxLineItems = sale.getLineItemsLength();
	for (int index = 0; index < maxLineItems; index++) {
	    System.out.format("%10.10s %30.30s %5d %10.2f %10.2f %6.2f %10.2f%n",
			      sale.getLineItemId(index),
			      sale.getLineItemDesc(index),
			      sale.getLineItemQuantity(index),
			      sale.getLineItemUnitPrice(index),
			      sale.getLineItemExtendedAmount(index),
			      sale.getLineItemDiscountAmount(index),
			      sale.getLineItemExtendedAmount(index) - sale.getLineItemDiscountAmount(index));
	}
	System.out.format("%87.87s%n", dashes);
	System.out.format("%43.43s %30.30s %12.2f%n", spaces, "Sub Total Amount", sale.getSubTotalAmount());
	System.out.format("%43.43s %30.30s %12.2f%n", spaces, "Total Discount Amount", sale.getTotalDiscountAmount());
	System.out.format("%43.43s %30.30s %12.2f%n", spaces, "Discounted Sub Total Amount", sale.getTotalSaleAmount());
	System.out.format("%43.43s %30.30s %12.2f%n", spaces, "Tax Amount", sale.getTotalTaxAmount(store));
	System.out.format("%43.43s %42.42s%n", spaces, dashes);
	System.out.format("%43.43s %30.30s %12.2f%n", spaces, "Total Amount Due", sale.getTotalAmountDue(store));
	System.out.println();
	System.out.format("%43.43s %30.30s %12.2f%n", spaces, "Amount Tendered", sale.getAmountTenderd());
	System.out.format("%43.43s %30.30s %12.2f%n", spaces, "Change Due", sale.getChangeAmount(store));
	System.out.println();
	System.out.format("%87.87s%n", dbdashes);
	System.out.println(sale.getThankYou(store));
	System.out.format("%87.87s%n", dbdashes);
	System.out.println();
    }

}
