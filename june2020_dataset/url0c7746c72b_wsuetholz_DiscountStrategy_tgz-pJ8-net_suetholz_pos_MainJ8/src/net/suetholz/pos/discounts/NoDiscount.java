/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.discounts;

import net.suetholz.pos.api.DiscountStrategy;

/**
 *
 * @author wsuetholz
 */
public class NoDiscount implements DiscountStrategy {

    private String description;
    
    public NoDiscount (String description) {
	setDescription(description);
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
	return 0.0;
    }

}
