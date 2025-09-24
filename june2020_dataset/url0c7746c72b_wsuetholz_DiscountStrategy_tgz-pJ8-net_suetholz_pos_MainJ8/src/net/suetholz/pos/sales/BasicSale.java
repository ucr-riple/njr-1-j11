/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.sales;

import net.suetholz.pos.api.CustomerStrategy;
import net.suetholz.pos.api.LineItemStrategy;
import net.suetholz.pos.api.SaleStrategy;
import net.suetholz.pos.api.StoreStrategy;

/**
 *
 * @author wsuetholz
 */
public class BasicSale implements SaleStrategy {

    private static final int ARRAY_ELEMENTS = 10;
    private CustomerStrategy customer;
    private LineItemStrategy[] lineItems;
    private int numItems;
    private boolean complete;
    private double amountTendered;
    
    public BasicSale () {
	complete = false;
	lineItems = new LineItemStrategy[ARRAY_ELEMENTS];
	numItems = 0;
	customer = null;
	amountTendered = 0.0;
    }
    
    @Override
    public final CustomerStrategy getCustomer() {
	return customer;
    }

    @Override
    public final void setCustomer(CustomerStrategy customer) {
	if (customer == null) {
	    throw new IllegalArgumentException();
	}
	this.customer = customer;
    }

    @Override
    public final LineItemStrategy[] getLineItems() {
	return lineItems;
    }

    @Override
    public final void addLineItem(LineItemStrategy lineItem) {
	if (lineItem == null) {
	    throw new IllegalArgumentException();
	}
	if (numItems >= lineItems.length) {
	    LineItemStrategy[] tempLineItems = new LineItemStrategy[lineItems.length+ARRAY_ELEMENTS];
	    for (int i = 0; i < numItems; i++) {
		tempLineItems[i] = lineItems[i];
	    }
	    lineItems = tempLineItems;
	}
	lineItems[numItems++] = lineItem;
    }

    @Override
    public final String getGreeting(StoreStrategy store) {
	return customer.getGreeting(store);
    }

    @Override
    public final String getThankYou(StoreStrategy store) {
	return customer.getThankYou(store);
    }

    @Override
    public final int getLineItemsLength() {
	return numItems;
    }

    @Override
    public final String getLineItemId(int index) {
	if (index < 0 || index >= numItems) {
	    throw new IllegalArgumentException();
	}
	
	return lineItems[index].getProductId();
    }

    @Override
    public final String getLineItemDesc(int index) {
	if (index < 0 || index >= numItems) {
	    throw new IllegalArgumentException();
	}
	
	return lineItems[index].getProductDesc();
    }

    @Override
    public final double getLineItemUnitPrice(int index) {
	if (index < 0 || index >= numItems) {
	    throw new IllegalArgumentException();
	}
	
	return lineItems[index].getUnitCost();
    }

    @Override
    public final int getLineItemQuantity(int index) {
	if (index < 0 || index >= numItems) {
	    throw new IllegalArgumentException();
	}
	
	return lineItems[index].getQuantity();
    }

    @Override
    public final double getLineItemExtendedAmount(int index) {
	if (index < 0 || index >= numItems) {
	    throw new IllegalArgumentException();
	}
	
	return lineItems[index].getExtendedAmount();
    }

    @Override
    public final double getLineItemDiscountAmount(int index) {
	if (index < 0 || index >= numItems) {
	    throw new IllegalArgumentException();
	}
	
	return lineItems[index].getDiscountAmount();
    }

    @Override
    public final double getSubTotalAmount() {
	double subTotal = 0.0;
	
	for (int i = 0; i < numItems; i++) {
	    subTotal += lineItems[i].getExtendedAmount();
	}
	
	return subTotal;
    }

    @Override
    public final double getTotalTaxAmount(StoreStrategy store) {
	if (store == null) {
	    throw new IllegalArgumentException();
	}

	return ((getTotalSaleAmount() * store.getTaxRate()) / 100.0);
    }

    @Override
    public final double getTotalDiscountAmount() {
	double discountAmount = 0.0;
	
	for (int i = 0; i < numItems; i++) {
	    discountAmount += lineItems[i].getDiscountAmount();
	}
	
	return discountAmount;
    }

    @Override
    public final double getTotalSaleAmount() {
	return (getSubTotalAmount() - getTotalDiscountAmount());
    }

    @Override
    public final double getTotalAmountDue(StoreStrategy store) {
	if (store == null) {
	    throw new IllegalArgumentException();
	}

	return (getTotalSaleAmount() + getTotalTaxAmount(store)) ;
    }

    @Override
    public final void setAmountTendered(double amountTendered) {
	if (amountTendered < 0.0) {
	    throw new IllegalArgumentException();
	}
	
	this.amountTendered = amountTendered;
	this.complete = true;
    }

    @Override
    public final double getAmountTenderd() {
	return amountTendered;
    }

    @Override
    public final double getChangeAmount(StoreStrategy store) {
	if (store == null) {
	    throw new IllegalArgumentException();
	}

	double amountDue = getTotalAmountDue(store);
	if (amountTendered < amountDue) {
	    throw new IllegalArgumentException();
	}
	
	return (amountDue - amountTendered);
    }

    @Override
    public final boolean isComplete() {
	return complete;
    }
    
    @Override
    public final void clearSale() {
	complete = false;
	amountTendered = 0.0;
	for (int i = 0; i < numItems; i++) {
	    lineItems[i] = null;
	}
	numItems = 0;
    }
    
}
