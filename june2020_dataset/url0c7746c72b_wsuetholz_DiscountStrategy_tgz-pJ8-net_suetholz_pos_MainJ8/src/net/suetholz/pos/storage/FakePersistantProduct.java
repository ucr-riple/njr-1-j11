/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.storage;

import net.suetholz.pos.Product;

/**
 *
 * @author wsuetholz
 */
public class FakePersistantProduct {

    private String id;
    private String description;
    private String department;
    private double unitPrice;
    private String discId;
    private Product product;

    public FakePersistantProduct(String id, String description, String department, double unitPrice, String discId ) {
	this.id = id;
	this.description = description;
	this.department = department;
	this.unitPrice = unitPrice;
	this.discId = discId;
	this.product = null;
    }

    public final String getId() {
	return id;
    }

    public final String getDescription() {
	return description;
    }

    public final String getDepartment() {
	return department;
    }

    public final double getUnitPrice() {
	return unitPrice;
    }

    public final String getDiscId() {
	return discId;
    }

    public final void setProduct(Product product) {
	this.product = product;
    }

    public final Product getProduct() {
	return product;
    }
    
}
