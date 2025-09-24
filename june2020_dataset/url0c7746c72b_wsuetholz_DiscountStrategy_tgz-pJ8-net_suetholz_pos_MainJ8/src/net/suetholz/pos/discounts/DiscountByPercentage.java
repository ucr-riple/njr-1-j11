/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.discounts;

import net.suetholz.pos.api.DiscountStrategy;

/**
 * By Item Percentage Discount
 *
 * @author wsuetholz
 * @version 1.00
 */
public class DiscountByPercentage implements DiscountStrategy {

    private double pctgDiscountPerItem;
    private String description;

    /**
     * Constructor for DiscountByPercentage
     *
     * @param pctgDiscountPerItem
     */
    public DiscountByPercentage(String description, double pctgDiscountPerItem) {
	setPctgDiscountPerItem(pctgDiscountPerItem);
	setDescription(description);
    }

    /**
     * Get the per item discount percentage
     *
     * @return per item discount percentage
     */
    public final double getPctgDiscountPerItem() {
	return pctgDiscountPerItem;
    }

    /**
     * Set the per item discount percentage
     *
     * Validate discount percent >= 0.0, set to 0.0 if invalid
     *
     * @param pctgDiscountPerItem
     */
    public final void setPctgDiscountPerItem(double pctgDiscountPerItem) {
	if (pctgDiscountPerItem < 0.0) {
	    pctgDiscountPerItem = 0.0;
	}
	this.pctgDiscountPerItem = pctgDiscountPerItem;
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
    public final void setDescription(String description) {
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

	return (((unitCost * pctgDiscountPerItem) * quantityPurchased) / 100);
    }

}
