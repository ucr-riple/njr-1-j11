/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper;

import java.io.Serializable;

/**
 * The Class Settings.
 */
public class Settings implements Serializable {

    /**
     * The extras enabled.
     */
    private boolean extrasEnabled;

    /**
     * Instantiates a new settings.
     *
     * @param enableExtras the enable extras
     */
    public Settings(boolean enableExtras) {
        super();
        this.extrasEnabled = enableExtras;
    }

    /**
     * Instantiates a new settings.
     */
    public Settings() {
        this(false);
    }

    /**
     * Checks if is extras enabled.
     *
     * @return true, if is extras enabled
     */
    public boolean isExtrasEnabled() {
        return extrasEnabled;
    }

    /**
     * Sets the extras enabled.
     *
     * @param extrasEnabled the new extras enabled
     */
    public void setExtrasEnabled(boolean extrasEnabled) {
        this.extrasEnabled = extrasEnabled;
    }
}
