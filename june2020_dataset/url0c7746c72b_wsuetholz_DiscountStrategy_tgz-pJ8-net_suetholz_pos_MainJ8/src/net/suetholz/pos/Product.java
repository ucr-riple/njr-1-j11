package net.suetholz.pos;

import net.suetholz.pos.api.DiscountStrategy;

/**
 * This is a product that can be sold via the point of sale system.
 *
 * @author wsuetholz
 * @version 1.00
 */
public class Product {

    private String id;
    private String description;
    private String department;
    private double unitPrice;
    private DiscountStrategy discount;

    /**
     * Create an instance of the Product.
     *
     * @param id
     * @param description
     * @param department
     * @param unitPrice
     */
    public Product(String id, String description, String department, double unitPrice) {
	setId(id);
	setDescription(description);
	setDepartment(department);
	setUnitPrice(unitPrice);
	this.discount = null;
    }

    /** 
     * Get the product identification string
     * 
     * @return product id 
     */
    public final String getId() {
	return id;
    }

    /**
     * Set the product identification string.
     * 
     * Validate not null
     * 
     * @param id 
     * @throws IllegalArgumentException if id string parameter is null
     */
    public final void setId(String id) {
	if (id == null) {
	    throw new IllegalArgumentException();
	}
	this.id = id;
    }

    /**
     * Get the product description
     * 
     * @return product description 
     */
    public final String getDescription() {
	return description;
    }

    /**
     * Set the product description
     * 
     * Validate not null
     * 
     * @param description 
     * @throws IllegalArgumentExecption if description parameter is null
     */
    public final void setDescription(String description) {
	if (description == null) {
	    throw new IllegalArgumentException();
	}
	this.description = description;
    }

    /**
     * Get the product department
     * 
     * @return the department the product is part of
     */
    public final String getDepartment() {
	return department;
    }

    /**
     * Set the department that the product is part of
     * 
     * Validate not null
     * 
     * @param department 
     * @throws IllegalArgumentException if department parameter is null
     */
    public final void setDepartment(String department) {
	if (department == null) {
	    throw new IllegalArgumentException();
	}
	this.department = department;
    }

    /**
     * Get the unit price of the product
     * 
     * @return unit price 
     */
    public final double getUnitPrice() {
	return unitPrice;
    }

    /**
     * Set the unit price of the product
     * 
     * Validate >= 0.0
     * 
     * @param unitPrice 
     * @throws IllegalArgumentException if unitPrice < 0.0
     */
    public final void setUnitPrice(double unitPrice) {
	if (unitPrice < 0.0) {
	    throw new IllegalArgumentException();
	}
	this.unitPrice = unitPrice;
    }

    /**
     * Return the current discount strategy in use for this product.
     *
     * @return current discount strategy. Can be null.
     */
    public final DiscountStrategy getDiscount() {
	return discount;
    }

    /**
     * Sets the current discount strategy used for this product.
     *
     * @param discount which can be null for no discount strategy.
     */
    public final void setDiscount(DiscountStrategy discount) {
	this.discount = discount;
    }

    /**
     * Get the total discount amount for this product.
     *
     * @param quantityPurchased
     * @return the current discount amount.
     */
    public final double getDiscountAmount(int quantityPurchased) {
	double discountAmount = 0.0;

	if (discount != null) {
	    discountAmount = discount.getDiscountAmount(quantityPurchased, unitPrice);
	}

	return discountAmount;
    }
}
