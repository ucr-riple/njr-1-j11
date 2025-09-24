/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suetholz.pos.api;

/**
 * CustomerStrategy defines what has to be implemented to be a Customer
 * 
 * @author wsuetholz
 * @version 1.00
 */
public interface CustomerStrategy {

    /**
     * Get the customer's name
     *
     * @return Customer Name
     */
    public abstract String getName();

    /**
     * Get the last 4 of the credit card number
     * 
     * @return last 4 of credit card number
     */
    public abstract String getCreditCardLastFour();

    /**
     * Get the customer greeting message
     * 
     * @param store
     * @return customer greeting
     */
    public abstract String getGreeting(StoreStrategy store);

    /**
     * get the customer thank you message
     * 
     * @param store
     * @return customer thank you message
     */
    public abstract String getThankYou(StoreStrategy store);
    
}
