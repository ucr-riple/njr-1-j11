/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.jdeals.main.controller.JDealsController;

/**
 * The Class JFrameMain.
 *
 * @author giuseppe
 */
public class JFrameMain extends JFrame {

    /**
     * The ctrl.
     */
    private final JDealsController ctrl;

    /**
     * The panel.
     */
    private JPanelMain panel;

    /**
     * Instantiates a new j frame main.
     *
     * @param ctrl the ctrl
     */
    public JFrameMain(JDealsController ctrl) {
        this.ctrl = ctrl;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //CENTER
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        this.panel = new JPanelMain(ctrl);
        this.add(this.panel);
        this.setSize(400, 400);

        //pack();
        this.setVisible(true);
    }

    /* (non-Javadoc)
     * @see java.awt.Component#repaint()
     */
    @Override
    public void repaint() {
        super.repaint();
        panel.repaint();
    }
}
