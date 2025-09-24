/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.customer;

import net.suetholz.pos.api.CustomerStrategy;
import net.suetholz.pos.api.StoreStrategy;

/**
 * This is a custom Customer Strategy defining a Corporate Customer
 * 
 * @author wsuetholz
 * @version 1.00
 */
public class CorporateCustomer implements CustomerStrategy {

    private String id;

    private String companyName;
    private String creditCardLastFour;
    
    public CorporateCustomer(String id, String companyName) {
	this.id = id;
	this.companyName = companyName;
	this.creditCardLastFour = "";
    }
    
    /**
     * Get the customer's name
     *
     * @return Customer Name
     */
    @Override
    public final String getName() {
	return companyName;
    }

    /** 
     * Get the last 4 of the credit card number
     * 
     * @return last 4 of the credit card number
     */
    @Override
    public final String getCreditCardLastFour() {
	return creditCardLastFour;
    }

    /**
     * Get the customer greeting message
     * 
     * @param store
     * @return customer greeting
     */
    @Override
    public final String getGreeting(StoreStrategy store) {
	StringBuilder str = new StringBuilder("");
	if (store != null) {
	    str.append(store.getGreeting());
	    str.append(" ");
	}
	if (this.companyName.length() > 0) {
	    str.append(this.companyName);
	}
	return str.toString();
    }

    /**
     * get the customer thank you message
     * @param store
     * @return customer thank you message
     */
    @Override
    public final String getThankYou(StoreStrategy store) {
	StringBuilder str = new StringBuilder("");
	if (store != null) {
	    str.append(store.getThankYou());
	    str.append(" ");
	}
	return str.toString();
    }
    
}
