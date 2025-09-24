/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.libs.menu;

import java.util.concurrent.Callable;

/**
 * The Class MenuItem.
 */
public class MenuItem {

    /**
     * The text.
     */
    private String text;

    /**
     * The action.
     */
    private Callable action;

    /**
     * Instantiates a new menu item.
     *
     * @param text the text
     * @param action the action
     */
    public MenuItem(String text, Callable action) {
        this.text = text;
        this.action = action;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Do action.
     *
     * @return true, if successful
     */
    public boolean doAction() {
        boolean retVal;
        try {
            Object obj = action.call();
            retVal = obj instanceof Boolean ? (Boolean) obj : obj != null;
        } catch (Exception e) {
            retVal = false;
        }

        return retVal;
    }

}
