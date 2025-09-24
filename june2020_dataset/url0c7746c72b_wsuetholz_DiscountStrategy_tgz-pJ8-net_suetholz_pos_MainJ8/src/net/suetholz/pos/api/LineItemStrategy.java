/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.api;

import net.suetholz.pos.Product;

/**
 *
 * @author wsuetholz
 */
public interface LineItemStrategy {

    public abstract Product getProduct();

    public abstract String getProductId();

    public abstract String getProductDesc();

    public abstract int getQuantity();

    public abstract double getUnitCost();

    public abstract double getDiscountAmount();

    public abstract double getExtendedAmount();
}
