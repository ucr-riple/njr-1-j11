/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.entity.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.jdeals.libs.Message;

/**
 * The Class Manager.
 */
public class Manager extends User {

    /**
     *
     */
    private static final long serialVersionUID = -1639888212380916319L;

    /**
     * The is admin.
     */
    private boolean isAdmin;

    /**
     * The score.
     */
    private int score;

    /**
     * list of users.
     */
    private Map<Integer, ArrayList<String>> polls;

    /**
     * Instantiates a new manager.
     *
     * @param isAdmin the is admin
     * @param username the username
     * @param password the password
     * @param city the city
     * @param email the email
     */
    public Manager(boolean isAdmin, String username, String password,
            String city, String email) {
        super(username, password, city, email);

        this.isAdmin = isAdmin;
        this.polls = new HashMap<>();
        this.score = 0;
    }

    /**
     * Just Vote.
     *
     * @see Message
     * @param val the val
     * @param id the id
     * @param username the username
     * @throws Message the message
     */
    public void vote(byte val, int id, String username) throws Message {
        if (username.equals(this.getUsername())) {
            throw new Message("You cannot vote your items!");
        }

        if (this.polls.containsKey(id)) {
            if (this.polls.get(id).contains(username)) {
                throw new Message("You cannot vote again this item!");
            } else {
                this.polls.get(id).add(username);
            }
        } else {
            ArrayList<String> usernames = new ArrayList<>();
            usernames.add(username);
            this.polls.put(id, usernames);
        }

        this.score += val;

    }

    /**
     * Gets the score average.
     *
     * @return the score average
     */
    public byte getScoreAverage() {
        int size = 0;
        for (ArrayList<String> users : polls.values()) {
            size += users.size();
        }

        return (byte) Math.round(((double) score) / ((double) size));
    }

    /**
     * Flag to determine if the Manager is an administrator or a supplier.
     *
     * @return true, if is admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Set the flag to determine if the Manager is an administrator or a
     * supplier.
     *
     * @param isAdmin the new admin
     */
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @see com.jdeals.main.entity.user.User#clone()
     */
    @Override
    public Manager clone() {
        return (Manager) super.clone();
    }
}
