/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.api;

/**
 * DiscountStrategy defines the methods that are required to be implemented
 * by any Discount child classes.
 * 
 * @author wsuetholz
 * @version 1.00
 */
public interface DiscountStrategy {

    /**
     * Calculate and return the total discount amount for this discount
     * 
     * @param quantityPurchased
     * @param unitCost
     * @return total calculated discount amount
     */
    public abstract double getDiscountAmount(int quantityPurchased, double unitCost);

    /**
     * Return the description of the discount
     * 
     * @return discount description
     */
    public abstract String getDescription();
}
