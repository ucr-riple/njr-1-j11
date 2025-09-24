/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.store;

import net.suetholz.pos.api.SaleStrategy;
import net.suetholz.pos.api.StoreStrategy;
import net.suetholz.pos.sales.BasicSale;

/**
 *
 * @author wsuetholz
 */
public class BasicStore implements StoreStrategy {

    private String greeting;
    private String thankYou;
    private String name;
    private String location;
    private double taxRate;
    
    public BasicStore (String name, String location, String greeting, String thankYou, double taxRate) {
	setName(name);
	setLocation(location);
	setGreeting(greeting);
	setThankYou(thankYou);
	setTaxRate(taxRate);
    }
 
    public final void setGreeting(String greeting) {
	if (greeting == null) {
	    throw new IllegalArgumentException();
	}

	this.greeting = greeting;
    }

    public final void setThankYou(String thankYou) {
	if (thankYou == null) {
	    throw new IllegalArgumentException();
	}

	this.thankYou = thankYou;
    }

    public final void setName(String name) {
	if (name == null) {
	    throw new IllegalArgumentException();
	}

	this.name = name;
    }

    public final void setLocation(String location) {
	if (location == null) {
	    throw new IllegalArgumentException();
	}

	this.location = location;
    }

    public final void setTaxRate(double taxRate) {
	if (taxRate < 0.0) {
	    throw new IllegalArgumentException();
	}

	this.taxRate = taxRate;
    }

    @Override
    public final String getGreeting() {
	return greeting;
    }

    @Override
    public final String getThankYou() {
	return thankYou;
    }

    @Override
    public final String getName() {
	return name;
    }

    @Override
    public final String getLocation() {
	return location;
    }

    @Override
    public final double getTaxRate() {
	return taxRate;
    }

    @Override
    public final SaleStrategy allocateSale() {
	BasicSale newSale = new BasicSale();
	
	return newSale;
    }

    @Override
    public final void newSale(SaleStrategy sale) {
	if (sale == null) {
	    throw new IllegalArgumentException();
	}
	
	sale.clearSale();
    }

    @Override
    public final void saveSale(SaleStrategy sale) {
	if (sale == null) {
	    throw new IllegalArgumentException();	    
	}
    }

    @Override
    public final void voidSale(SaleStrategy sale) {
	if (sale == null) {
	    throw new IllegalArgumentException();	    
	}
    }
    
}
