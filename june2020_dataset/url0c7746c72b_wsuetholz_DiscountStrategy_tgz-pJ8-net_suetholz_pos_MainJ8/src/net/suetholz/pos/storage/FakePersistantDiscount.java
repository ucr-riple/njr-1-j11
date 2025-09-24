/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.storage;

import net.suetholz.pos.api.DiscountStrategy;

/**
 *
 * @author wsuetholz
 */
public class FakePersistantDiscount {

    private String id;
    private FakePersistantDiscountType discType;
    private String description;
    private int discNumber;
    private double discDouble;
    private DiscountStrategy discount;
    

    public FakePersistantDiscount(String id, FakePersistantDiscountType discType, String description, int discNumber, double discDouble) {
	this.id = id;
	this.discType = discType;
	this.description = description;
	this.discNumber = discNumber;
	this.discDouble = discDouble;
	this.discount = null;
    }

    public final FakePersistantDiscountType getDiscType() {
	return discType;
    }

    public final String getId() {
	return id;
    }
    
    public final String getDescription() {
	return description;
    }
    
    public final int getDiscNumber() {
	return discNumber;
    }

    public final double getDiscDouble() {
	return discDouble;
    }

    public final DiscountStrategy getDiscount() {
	return discount;
    }
   
    public final void setDiscount(DiscountStrategy discount) {
	this.discount = discount;
    }
    
}
