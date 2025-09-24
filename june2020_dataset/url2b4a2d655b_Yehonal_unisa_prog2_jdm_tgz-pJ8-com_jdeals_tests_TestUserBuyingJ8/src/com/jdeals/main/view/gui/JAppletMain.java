/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.gui;


import com.jdeals.main.controller.JDealsController;

import javax.swing.JApplet;

/**
 * The Class JAppletMain.
 *
 * @author giuseppe
 */
public class JAppletMain extends JApplet {

    /**
     * The instance.
     */
    private JDealsController instance;

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    public void init() {
        instance = new JDealsController(JDealsController.RunType.APPLET);
        instance.render();
        // JFrame login=new JFrameLogin(instance);
        //this.setSize(640, 480);
        this.setVisible(false);
    }
}
