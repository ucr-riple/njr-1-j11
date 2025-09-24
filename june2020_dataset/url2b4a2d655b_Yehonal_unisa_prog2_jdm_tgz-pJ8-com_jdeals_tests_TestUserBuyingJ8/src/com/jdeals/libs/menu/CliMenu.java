/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.libs.menu;

import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.Callable;

/**
 * The Class CliMenu.
 */
public class CliMenu {

    /**
     * The prev.
     */
    protected CliMenu prev;

    /**
     * The items.
     */
    private TreeMap<String, MenuItem> items;

    /**
     * The menu index.
     */
    private int menuIndex;

    /**
     * The scan.
     */
    private Scanner scan;

    /**
     * The menu name.
     */
    private String menuName;

    /**
     * Instantiates a new cli menu.
     *
     * @param menuName the menu name
     * @param prev the prev
     * @param scan the scan
     */
    public CliMenu(String menuName, final CliMenu prev, Scanner scan) {
        this(menuName, scan);
        this.setPrevMenu(prev);
    }

    /**
     * Instantiates a new cli menu.
     *
     * @param menuName the menu name
     * @param scan the scan
     */
    public CliMenu(String menuName, Scanner scan) {
        this.scan = scan;
        this.items = new TreeMap<>();
        this.menuName = menuName;
        menuIndex = 0;

        Callable<Boolean> exit = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                System.out.println("GoodBye!");
                System.exit(0);
                return true;
            }
        };
        this.addItem("Exit", exit, "x");
    }

    /**
     * Adds the item.
     *
     * @param text the text
     * @param action the action
     * @param option the option
     */
    public void addItem(String text, Callable action, String option) {
        items.put(option, new MenuItem(text, action));
    }

    /**
     * Adds the item.
     *
     * @param text the text
     * @param action the action
     */
    public void addItem(String text, Callable action) {
        this.menuIndex++;
        this.addItem(text, action, new Integer(this.menuIndex).toString());
    }

    /**
     * Removes the item.
     *
     * @param option the option
     */
    public void removeItem(String option) {
        items.remove(option);
    }

    /**
     * Run menu.
     *
     * @return true, if successful
     */
    public boolean runMenu() {
        MenuItem opt;

        do {
            opt = items.get(this.render());
            if (opt != null) {
                opt.doAction();
            }
        } while (true); // infinite loop until exit is called
    }

    /**
     * Sets the prev menu.
     *
     * @param prev the new prev menu
     */
    protected void setPrevMenu(final CliMenu prev) {
        this.prev = prev;
        if (this.prev != null) {
            Callable<Boolean> back = new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return prev.runMenu();
                }
            };
            this.addItem("Back to previous menu", back, "b");
        }
    }

    /**
     * Render.
     *
     * @return the string
     */
    private String render() {
        System.out.println("===== " + this.menuName + " =====");
        for (Entry<String, MenuItem> entry : items.entrySet()) {
            String opt = entry.getKey();
            MenuItem item = entry.getValue();

            System.out.println(opt + ") " + item.getText());
        }

        System.out.println("Choose an option: ");

        String opt = scan.nextLine();

        return opt;
    }
}
