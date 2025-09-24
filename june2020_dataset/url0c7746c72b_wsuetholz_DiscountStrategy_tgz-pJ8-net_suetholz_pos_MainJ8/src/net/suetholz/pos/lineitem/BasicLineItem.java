/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.lineitem;

import net.suetholz.pos.Product;
import net.suetholz.pos.api.LineItemStrategy;

/**
 *
 * @author wsuetholz
 */
public class BasicLineItem implements LineItemStrategy {

    private Product product;
    private int quantity;

    public BasicLineItem (Product product, int quantity) {
	setProduct(product);
	setQuantity(quantity);
    }
    
    public final void setProduct(Product product) {
	if (product == null) {
	    throw new IllegalArgumentException();
	}
	this.product = product;
    }
    
    @Override
    public final Product getProduct() {
	return product;
    }

    @Override
    public final String getProductId() {
	return product.getId();
    }

    @Override
    public final String getProductDesc() {
	return product.getDescription();
    }

    public final void setQuantity(int quantity) {
	if (quantity < 0) { 
	    throw new IllegalArgumentException();
	}
	this.quantity = quantity;
    }
    
    @Override
    public int getQuantity() {
	return quantity;
    }

    @Override
    public final double getUnitCost() {
	return product.getUnitPrice();
    }

    @Override
    public final double getDiscountAmount() {
	return product.getDiscountAmount(quantity);
    }

    @Override
    public final double getExtendedAmount() {
	return (product.getUnitPrice()*quantity);
    }
    
}
