/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.entity.user;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.jdeals.libs.MyObject;
import com.jdeals.main.entity.Order;

/**
 * The Class User.
 */
public class User extends MyObject implements Serializable, Cloneable {

    /**
     *
     */
    private static final long serialVersionUID = 1498484911189114540L;

    /**
     * The username.
     */
    private String username;

    /**
     * The password.
     */
    private String password;

    /**
     * The email.
     */
    private String email;

    /**
     * The credit.
     */
    private double credit;

    /**
     * The city.
     */
    private String city;

    /**
     * The orders.
     */
    private ArrayList<Order> orders;

    /**
     * The cur order. It shouldn't be saved at app closed
     *
     */
    transient private Order curOrder;

    /**
     * Instantiates a new user.
     *
     * @param username the username
     * @param password the password
     * @param city the city
     * @param email the email
     */
    public User(String username, String password, String city, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.city = city;
        this.credit = 0;
        this.orders = new ArrayList<>();
        this.curOrder = new Order();
    }

    /**
     * Gets the city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     *
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the credit.
     *
     * @return the credit
     */
    public double getCredit() {
        return credit;
    }

    /**
     * Sets the credit.
     *
     * @param credit the new credit
     */
    public void setCredit(double credit) {
        this.credit = credit;
    }

    /**
     * Mod credit.
     *
     * @param credit the credit
     */
    public void modCredit(double credit) {
        this.credit += credit;
    }

    /**
     * Check credit.
     *
     * @param price the price
     * @return true, if successful
     */
    public boolean checkCredit(double price) {
        return this.getCredit() >= price;
    }

    /**
     * Gets the orders.
     *
     * @return the orders
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Gets the orders.
     *
     * @param sortPolicy the sort policy
     * @return the orders
     */
    public ArrayList<Order> getOrders(Comparator<Order> sortPolicy) {
        Collections.sort(this.orders, sortPolicy);
        return this.orders;
    }

    /**
     * Adds the order.
     *
     * @param order the order
     */
    public void addOrder(Order order) {
        this.orders.add(order);
    }

    /**
     * Gets the cur order.
     *
     * @return the cur order
     */
    public Order getCurOrder() {
        return curOrder;
    }

    /**
     * @see java.lang.Object#clone()
     */
    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        // check same username that means same user
        return ((User) obj).getUsername().equals(this.getUsername());
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        // transient var must be re-init at
        // object read
        curOrder = new Order();
    }

    /*
     @Override
     public String toString() {
     return Tools.printVars(this.getClass().getSimpleName(), false, false, new MyVar(this.username,"Username"),
     new MyVar(this.credit,"Credit"),new MyVar(this.email,"Email"), new MyVar(this.city,"City"));
     } */
}
