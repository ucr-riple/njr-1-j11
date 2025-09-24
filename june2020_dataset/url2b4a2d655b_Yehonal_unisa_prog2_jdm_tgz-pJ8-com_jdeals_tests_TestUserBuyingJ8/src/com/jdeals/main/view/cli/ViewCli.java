/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.cli;

import com.jdeals.libs.menu.CliMenu;
import com.jdeals.main.controller.JDealsController;

/**
 * The Class ViewCli.
 */
abstract public class ViewCli extends CliMenu {

    /**
     * The sys ctrl.
     */
    private JDealsController sysCtrl;

    /**
     * Instantiates a new view cli.
     *
     * @param menuName the menu name
     * @param sysCtrl the sys ctrl
     */
    public ViewCli(String menuName, JDealsController sysCtrl) {
        super(menuName, JDealsController.scan);
        ViewCli view = sysCtrl.getCurMenu();
        ViewCli prev = view != null && !view.getClass().getName().equals(this.getClass().getName()) ? view : null;
        this.setPrevMenu(prev);
        this.sysCtrl = sysCtrl;
    }

    /**
     * Gets the sys ctrl.
     *
     * @return the sys ctrl
     */
    public JDealsController getSysCtrl() {
        return this.sysCtrl;
    }

    /* (non-Javadoc)
     * @see com.jdeals.libs.menu.CliMenu#runMenu()
     */
    @Override
    public boolean runMenu() {
        this.sysCtrl.setCurMenu(this);
        return super.runMenu();
    }
}
