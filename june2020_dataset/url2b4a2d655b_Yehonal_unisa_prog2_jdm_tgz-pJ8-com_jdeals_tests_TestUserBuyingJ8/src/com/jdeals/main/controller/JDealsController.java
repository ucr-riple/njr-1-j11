/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.controller;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Scanner;

import com.jdeals.libs.EmailValidator;
import com.jdeals.main.entity.user.Manager;
import com.jdeals.main.entity.user.User;
import com.jdeals.main.helper.Settings;
import com.jdeals.main.helper.storage.DataBaseInterface;
import com.jdeals.main.helper.storage.FileSerialization;
import com.jdeals.main.view.cli.MainMenu;
import com.jdeals.main.view.cli.ViewCli;
import com.jdeals.main.view.gui.JFrameLogin;

/**
 * This is the core of JDeals Manager. It keeps instances dataset of registered
 * users and of the Store itself. It also provides methods to handle the user
 * list ( such as Login, Register, findUser etc.)
 *
 * @author Giuseppe Ronca
 *
 */
public class JDealsController {

    /**
     * The Enum RunType.
     */
    public enum RunType {

        /**
         * The applet.
         */
        APPLET,
        /**
         * The cli.
         */
        CLI,
        /**
         * The frame.
         */
        FRAME
    }

    /**
     * The scan.
     */
    public static Scanner scan;

    /**
     * The users.
     */
    private ArrayList<User> users;

    /**
     * The cur user.
     */
    private User curUser;

    /**
     * The cur menu.
     */
    private ViewCli curMenu;

    /**
     * The store.
     */
    private Store store;

    /**
     * The frame.
     */
    private Window frame;

    /**
     * The run type.
     */
    private RunType rType;

    /**
     * The settings.
     */
    private Settings settings;

    /**
     * Default constructor.
     */
    public JDealsController() {
        this(RunType.FRAME);
    }

    /**
     * Constructor with runtype specification.
     *
     * @param type APPLET/CLIENT/FRAME
     * @see Store
     */
    public JDealsController(RunType type) {
        this.store = new Store(this);
        this.users = new ArrayList<>();
        this.settings = new Settings(false);
        this.rType = type;

        this.loadData(new FileSerialization<>());

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                getCurUser().getCurOrder().reset(false);
                saveData(new FileSerialization<>());
            }
        });
    }

    /**
     * Renders CLI or Login Frame depending on runType.
     *
     * @see JFrameLogin
     * @see MainMenu
     */
    public void render() {
        switch (this.rType) {
            case CLI:
                new MainMenu(this).runMenu();
            default:
                new JFrameLogin(this);
                break;
        }
    }

    /**
     * Saves app data to file.
     *
     * @param manager the db manager that must implements DataBaseInterface
     * @see DataBaseInterface
     */
    public void saveData(DataBaseInterface manager) {
        manager.storeList(users, "database/users.db");
        this.store.saveData(manager);
    }

    /**
     * Load the app data from file.
     *
     * @param manager the db manager that must implements DataBaseInterface
     * @see User
     * @see DataBaseInterface
     */
    public void loadData(DataBaseInterface manager) {
        ArrayList<User> tmp = manager.loadList("database/users.db");
        if (!tmp.isEmpty()) {
            users = tmp;
        }

        this.store.loadData(manager);
    }

    /**
     * Gets the list of registered users.
     *
     * @return ArrayList<User>
     * @see User
     */
    public ArrayList<User> getUsers() {
        return this.users;
    }

    /**
     * Gets the runType.
     *
     * @return RunType
     */
    public RunType getrType() {
        return rType;
    }

    /**
     * Gets the app main frame.
     *
     * @return the frame
     */
    public Window getFrame() {
        return this.frame;
    }

    /**
     * Sets the app main frame.
     *
     * @param frame the new frame
     */
    public void setFrame(Window frame) {
        this.frame = frame;
    }

    /**
     * Gets the store instance.
     *
     * @return the store
     * @see Store
     */
    public Store getStore() {
        return this.store;
    }

    /**
     * Gets instance of current user.
     *
     * @return the cur user
     * @see User
     */
    public User getCurUser() {
        return this.curUser;
    }

    /**
     * Sets the current User
     *
     * @see User
     * @param curUser
     */
    public void setCurUser(User curUser) {
        this.curUser = curUser;
    }

    /**
     * Gets current menu for CLI.
     *
     * @return the cur menu
     */
    public ViewCli getCurMenu() {
        return this.curMenu;
    }

    /**
     * Sets current menu for CLI.
     *
     * @param curMenu ViewCli
     * @see ViewCli
     */
    public void setCurMenu(ViewCli curMenu) {
        this.curMenu = curMenu;
    }

    /**
     * Checks if curUser is Manager.
     *
     * @param adminOnly check if is an admin or a supplier
     * @return true if it's a manager, false otherwise
     * @see User
     * @see Manager
     */
    public boolean isManagerSession(boolean adminOnly) {
        return this.curUser instanceof Manager && (!adminOnly || ((Manager) this.curUser).isAdmin() == true);
    }

    /**
     * Handles the login process.
     *
     * @param id username or email
     * @param password the password of the user
     * @return User instance if found
     * @throws Exception the exception
     * @see User
     */
    public User Login(String id, String password) throws Exception {
        curUser = findUser(id, password);

        if (curUser != null) {
            return curUser;
        } else {
            throw new Exception("Invalid id or password!");
        }
    }

    /**
     * Registers an user inside user list.
     *
     * @param username the username
     * @param email the email
     * @param city the city
     * @param password the password
     * @return true if the registration has been completed, otherwise return
     * false
     * @throws Exception the exception
     * @see User
     * @see EmailValidator
     */
    public boolean Register(String username, String email, String city, String password) throws Exception {
        if (username.contains("@")) {
            throw new Exception("Invalid Username!");
        }

        EmailValidator validator = new EmailValidator();
        if (!validator.validate(email)) {
            throw new Exception("Invalid Email!");
        }

        if (this.findUser(username, email, null) == null) {
            return this.users.add(new User(username, password, city, email));
        }

        throw new Exception("Username or Email already exists");
    }

    /**
     * Finds an user inside the list using ( id or email ) and ( password if not
     * null ) .
     *
     * @param username the username
     * @param email the email
     * @param password the password
     * @return the user
     * @see User
     */
    public User findUser(String username, String email, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) || u.getEmail().equals(email)) {
                if (password == null || u.getPassword().equals(password)) {
                    return u;
                }
            }
        }

        return null;
    }

    /**
     * Overloading of findUser.
     *
     * @param id username or email
     * @return the user
     */
    public User findUser(String id) {
        return this.findUser(id, id, null);
    }

    /**
     * Overloading of findUser.
     *
     * @param id username or email
     * @param password the password
     * @return the user
     */
    public User findUser(String id, String password) {
        return this.findUser(id, id, password);
    }

    /**
     * Retrieves extra settings class instance ( only for dev testing ).
     *
     * @return the settings
     */
    public Settings getSettings() {
        return settings;
    }
}
