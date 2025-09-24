/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.discounts;

import net.suetholz.pos.api.DiscountStrategy;

/**
 * Provide a Flat Dollar Amount Discount
 * 
 * @author wsuetholz
 * @version 1.0
 */
public class DiscountByFlatRate implements DiscountStrategy {

    private double discountAmount;
    private String description;

    /**
     * Constructor for DiscountByFlatRate
     * 
     * @param description 
     * @param discountAmount 
     */
    public DiscountByFlatRate(String description, double discountAmount) {
	setDiscountAmount(discountAmount);
	setDescription(description);
    }

    /** 
     * Get the Flat Discount Amount
     * 
     * @return discount amount
     */
    public final double getDiscountAmount() {
	return discountAmount;
    }

    /**
     * Set the flat discount amount
     * 
     * Validate discount amount >= 0.0, set to 0.0 if invalid
     * 
     * @param discountAmount 
     */
    public final void setDiscountAmount(double discountAmount) {
	if (discountAmount < 0.0) {
	    discountAmount = 0.0;
	}
	this.discountAmount = discountAmount;
    }

    /**
     * Return the description of the discount
     * 
     * @return discount description
     */
    @Override 
    public final String getDescription() {
	return description;
    }
    
    /**
     * Set the description of the discount
     * 
     * @param description 
     * @throws IllegalArgumentException if description is null
     */
    public final void setDescription (String description) {
	if (description == null) {
	    throw new IllegalArgumentException();
	}
	this.description = description;
    }
    
    /**
     * Calculate and return the total discount amount for this discount
     * 
     * @param quantityPurchased
     * @param unitCost
     * @return total calculated discount amount
     */
    @Override
    public final double getDiscountAmount(int quantityPurchased, double unitCost) {
	if (quantityPurchased < 0) {
	    throw new IllegalArgumentException("QuantityPurchased must be greater then 0");
	}
	if (unitCost < 0.0) {
	    throw new IllegalArgumentException("UnitCost must be greater then 0.0");
	}

	return discountAmount;
    }

}
