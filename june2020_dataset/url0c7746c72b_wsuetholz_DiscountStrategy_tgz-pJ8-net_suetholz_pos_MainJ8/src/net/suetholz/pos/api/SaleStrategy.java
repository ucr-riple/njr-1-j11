/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.api;

/**
 *
 * @author wsuetholz
 */
public interface SaleStrategy {

    public abstract CustomerStrategy getCustomer();

    public abstract void setCustomer(CustomerStrategy customer);

    public abstract LineItemStrategy[] getLineItems();

    public abstract void addLineItem(LineItemStrategy lineItem);
    
    public abstract int getLineItemsLength ();
    
    public abstract String getLineItemId(int index);
    
    public abstract String getLineItemDesc(int index);
    
    public abstract double getLineItemUnitPrice (int index);
    
    public abstract int getLineItemQuantity (int index);
    
    public abstract double getLineItemExtendedAmount (int index);
    
    public abstract double getLineItemDiscountAmount (int index);
    
    public abstract String getGreeting (StoreStrategy store);
    
    public abstract String getThankYou (StoreStrategy store);

    public abstract double getSubTotalAmount();

    public abstract double getTotalTaxAmount(StoreStrategy store);

    public abstract double getTotalDiscountAmount();

    public abstract double getTotalSaleAmount();
    
    public abstract double getTotalAmountDue(StoreStrategy store);

    public abstract void setAmountTendered(double amountTendered);

    public abstract double getAmountTenderd();

    public abstract double getChangeAmount(StoreStrategy store);

    public abstract boolean isComplete();
    
    public abstract void clearSale();
}
