/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.discounts;

import net.suetholz.pos.api.DiscountStrategy;

/**
 * Provide a discount strategy which requires a specific quantity purchased, 
 * and then gives a flat discount amount.
 * 
 * @author wsuetholz
 * @version 1.00
 */
public class DiscountByQuantity implements DiscountStrategy {
    private int minQuantity;
    private double discountAmount;
    private String description;

    /**
     * Constructor for Discount by Quantity
     * 
     * @param minQuantity
     * @param discountAmount 
     */
    public DiscountByQuantity(String description, int minQuantity, double discountAmount) {
	setMinQuantity(minQuantity);
	setDiscountAmount(discountAmount);
	setDescription(description);
    }

    /**
     * Get the current minimum quantity required before discount is applied
     * 
     * @return minimum quantity required
     */
    public final int getMinQuantity() {
	return minQuantity;
    }

    /** 
     * Set the current minimum quantity required before discount is active
     * 
     * Validates minQuantity >= 0 and if not sets to 0.
     * 
     * @param minQuantity 
     */
    public final void setMinQuantity(int minQuantity) {
	if (minQuantity < 0) {
	    minQuantity = 0;
	}
	this.minQuantity = minQuantity;
    }

    /**
     * Gets the current discount amount
     * 
     * @return discount amount
     */
    public final double getDiscountAmount() {
	return discountAmount;
    }

    /**
     * Set the current discount amount
     * 
     * Validates discountAmount >= 0.0 and if not set to 0.0.
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

	double result = 0.0;
	if (quantityPurchased < minQuantity) {
	    return result;
	}

	result = discountAmount;

	return result;
    }
    
}
