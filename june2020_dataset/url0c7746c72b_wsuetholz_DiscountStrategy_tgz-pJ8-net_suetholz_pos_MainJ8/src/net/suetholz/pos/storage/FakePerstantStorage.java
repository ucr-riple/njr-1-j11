/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.storage;

import net.suetholz.pos.Product;
import net.suetholz.pos.api.CustomerStrategy;
import net.suetholz.pos.api.DiscountStrategy;
import net.suetholz.pos.api.PersistantStorageStrategy;
import net.suetholz.pos.customer.BasicCustomer;
import net.suetholz.pos.customer.CorporateCustomer;
import net.suetholz.pos.customer.PreferredCustomer;
import net.suetholz.pos.discounts.DiscountByFlatRate;
import net.suetholz.pos.discounts.DiscountByPercentage;
import net.suetholz.pos.discounts.DiscountByQuantity;
import net.suetholz.pos.discounts.NoDiscount;

/**
 *
 * @author wsuetholz
 */
public class FakePerstantStorage implements PersistantStorageStrategy {

    private static final int ARRAY_INCREMENT = 10;	// If array needs to grow, grow by this amount...

    private FakePersistantCustomer[] customers;
    private int numCustomers;
    private FakePersistantProduct[] products;
    private int numProducts;
    private FakePersistantDiscount[] discounts;
    private int numDiscounts;

    public FakePerstantStorage(FakePersistantCustomer[] customers, FakePersistantProduct[] products, FakePersistantDiscount[] discounts) {
	if (customers == null || products == null) {
	    throw new IllegalArgumentException();
	}

	this.customers = customers;
	this.numCustomers = this.customers.length;
	this.products = products;
	this.numProducts = this.products.length;
	this.discounts = discounts;
	this.numDiscounts = this.discounts.length;
    }

    private final DiscountStrategy allocateDiscount(FakePersistantDiscount fakeDiscount) {
	if (fakeDiscount == null) {
	    throw new IllegalArgumentException();
	}

	DiscountStrategy discount = null;

	switch (fakeDiscount.getDiscType()) {
	    case ByPercentage:
		discount = new DiscountByPercentage(fakeDiscount.getDescription(), fakeDiscount.getDiscDouble());
		break;
	    case ByFlatRate:
		discount = new DiscountByFlatRate(fakeDiscount.getDescription(), fakeDiscount.getDiscDouble());
		break;
	    case ByQuantity:
		discount = new DiscountByQuantity(fakeDiscount.getDescription(), fakeDiscount.getDiscNumber(), fakeDiscount.getDiscDouble());
		break;
	    default:
		discount = new NoDiscount(fakeDiscount.getDescription());
		break;
	}

	fakeDiscount.setDiscount(discount);
	
	return discount;
    }

    private final void addDiscount(FakePersistantDiscount discount) {
	if (discount == null) {
	    throw new IllegalArgumentException();
	}

	// See if there is enough room in the array and if not expand the array by INCREMENT slots.
	if (numDiscounts >= discounts.length) {
	    FakePersistantDiscount[] tempDiscounts = new FakePersistantDiscount[discounts.length + ARRAY_INCREMENT];
	    for (int i = 0; i < numDiscounts; i++) {
		tempDiscounts[i] = discounts[i];
	    }
	    discounts = tempDiscounts;
	}

	discounts[numDiscounts++] = discount;
    }

    public final DiscountStrategy lookupDiscount(String discId) {
	if (discId == null) {
	    throw new IllegalArgumentException();
	}

	DiscountStrategy discount = null;

	for (int i = 0; i < numDiscounts; i++) {
	    if (discounts[i].getId().equalsIgnoreCase(discId)) {
		discount = discounts[i].getDiscount();
		if (discount == null) {
		    discount = allocateDiscount(discounts[i]);
		}
		break;
	    }
	}

	if (discount == null) {
	    throw new IllegalArgumentException();   // This should be something else...
	}
	
	return discount;
    }

    private final CustomerStrategy allocateCustomer(FakePersistantCustomer fakeCustomer) {
	if (fakeCustomer == null) {
	    throw new IllegalArgumentException();
	}
	
	CustomerStrategy customer = null;
	
	switch (fakeCustomer.getCustType()) {
	    case Basic:
		BasicCustomer bCustomer = new BasicCustomer(fakeCustomer.getId());
		bCustomer.setName(fakeCustomer.getName());
		customer = bCustomer;
		break;
	    case Preferred:
		PreferredCustomer pCustomer = new PreferredCustomer(fakeCustomer.getId(), fakeCustomer.getName());
		pCustomer.setDiscount(lookupDiscount(fakeCustomer.getDiscId()));
		customer = pCustomer;
		break;
	    case Corporate:
		CorporateCustomer cCustomer = new CorporateCustomer (fakeCustomer.getId(), fakeCustomer.getName());
		customer = cCustomer;
		break;
	}

	if (customer == null) {
	    throw new IllegalArgumentException();
	}
	
	return customer;
    }

    public final void addCustomer(FakePersistantCustomer customer) {
	if (customer == null) {
	    throw new IllegalArgumentException();
	}

	// See if there is enough room in the array and if not expand the array by INCREMENT slots.
	if (numCustomers >= customers.length) {
	    FakePersistantCustomer[] tempCustomers = new FakePersistantCustomer[customers.length + ARRAY_INCREMENT];
	    for (int i = 0; i < numCustomers; i++) {
		tempCustomers[i] = customers[i];
	    }
	    customers = tempCustomers;
	}

	customers[numCustomers++] = customer;
    }

    @Override
    public final CustomerStrategy lookupCustomer(String customerId) {
	if (customerId == null) {
	    throw new IllegalArgumentException();
	}

	CustomerStrategy customer = null;

	for (int i = 0; i < numCustomers; i++) {
	    if (customers[i].getId().equalsIgnoreCase(customerId)) {
		customer = customers[i].getCustomer();
		if (customer == null) {
		    customer = allocateCustomer(customers[i]);
		    customers[i].setCustomer(customer);
		}
		break;
	    }
	}

	if (customer == null) {
	    throw new IllegalArgumentException();   // This should be something else...
	}

	return customer;
    }

    public final void addProduct(FakePersistantProduct product) {
	if (product == null) {
	    throw new IllegalArgumentException();
	}

	// See if there is enough room in the array and if not expand the array by INCREMENT slots.
	if (numProducts >= products.length) {
	    FakePersistantProduct[] tempProducts = new FakePersistantProduct[products.length + ARRAY_INCREMENT];
	    for (int i = 0; i < numProducts; i++) {
		tempProducts[i] = products[i];
	    }
	    products = tempProducts;
	}

	products[numProducts++] = product;
    }

    private final Product allocateProduct (FakePersistantProduct fakeProduct) {
	if (fakeProduct == null) {
	    throw new IllegalArgumentException();
	}

	Product product = new Product (fakeProduct.getId(), fakeProduct.getDescription(), fakeProduct.getDepartment(), fakeProduct.getUnitPrice());
	DiscountStrategy discount = lookupDiscount (fakeProduct.getDiscId());
	product.setDiscount(discount);
	
	return product;
    }
    
    @Override
    public final Product lookupProduct(String productId) {
	if (productId == null) {
	    throw new IllegalArgumentException();
	}
	
	Product product = null;
	
	for (int i = 0; i < numProducts; i++) {
	    if (products[i].getId().equalsIgnoreCase(productId)) {
		product = products[i].getProduct();
		if (product == null) {
		    product = allocateProduct(products[i]);
		    products[i].setProduct(product);
		}
		break;
	    }
	}
	
	if (product == null) {
	    throw new IllegalArgumentException();   // This should be something else...
	}
	
	return product;
    }

}
