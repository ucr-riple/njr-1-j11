/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.tests;

import com.jdeals.libs.Message;
import com.jdeals.libs.MyDate;
import com.jdeals.main.controller.JDealsController;
import com.jdeals.main.controller.Store;
import com.jdeals.main.entity.catalogue.GeneralGood;
import com.jdeals.main.entity.catalogue.Restourant;
import com.jdeals.main.entity.catalogue.Travel;
import com.jdeals.main.entity.user.Manager;
import com.jdeals.main.entity.user.User;
import com.jdeals.main.helper.discount.LastDaysDiscount;
import com.jdeals.main.helper.discount.QuantityDiscount;
import com.jdeals.main.helper.discount.RestourantDiscount;

/**
 * The Class TestDemoData.
 */
public class TestDemoData {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        JDealsController instance = new JDealsController();

        try {
            importExampleData(instance);
        } catch (Message m) {
            System.out.println(m);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        instance.render();
    }

    /**
     * Test method.
     *
     * @param ctrl the ctrl
     * @throws Message the message
     * @throws Exception the exception
     */
    public static void importExampleData(JDealsController ctrl) throws Message, Exception {

        if (ctrl.findUser("root", "admin@hw2.it") == null) {
            ctrl.getUsers().add(new Manager(true, "root", "root", "Salerno", "admin@hw2.it"));
        }

        if (ctrl.findUser("supplier", "supplier@hw2.it") == null) {
            ctrl.getUsers().add(new Manager(false, "supplier", "supplier", "Salerno", "supplier@hw2.it"));
        }

        if (ctrl.findUser("utente", "utente@hw2.it") == null) {
            ctrl.getUsers().add(new User("user", "user", "Salerno", "user@hw2.it"));
        }

        importStoreExampleData(ctrl.getStore());

        throw new Message("Example data loaded succefully!");
    }

    /**
     * Import store example data.
     *
     * @param store the store
     * @throws Exception the exception
     */
    public static void importStoreExampleData(Store store) throws Exception {
        try {
            store.addItem(new Travel(2000, 0, new MyDate().fromString("01/07/2010"), "California", new MyDate().fromString("10/08/2014")));
            store.addItem(new Travel(830.50, 0, new MyDate().fromString("01/07/2014"), "Amalfi", new MyDate().fromString("10/08/2014")));
            store.addItem(new Travel(0.33, 0, new MyDate().fromString("01/01/2020"), "Roma", new MyDate().fromString("01/01/2020")));
            store.addItem(new GeneralGood("Geox green shoes", 20, 0, 4, (Manager) store.getMainCtrl().findUser("root")));
            store.addItem(new GeneralGood("Samsung Galaxy 6", 600, 4, 10, (Manager) store.getMainCtrl().findUser("root")));
            store.addItem(new GeneralGood("IPhone 5d", 700, 1, 2, (Manager) store.getMainCtrl().findUser("supplier")));
            store.addItem(new Restourant("Le Castelle Pizzeria", "Great pizza, lovely meat and nice drinks", 30, 0, new MyDate().fromString("21/02/2014"), "Salerno", 6));
            store.addItem(new Restourant("Il Moro", "amazing foods", 39, 0, new MyDate().fromString("30/10/2014"), "Salerno", 10));
            store.addItem(new Restourant("La Bavarese", "original beer", 16, 0, new MyDate().fromString("01/02/2014"), "Napoli", 4));
            store.addItem(new Restourant("Made in Italy", "pizza and more", 20, 0, new MyDate().fromString("13/03/2012"), "Salerno", 8));

            store.addDiscount(new RestourantDiscount(50, 20, 7));
            store.addDiscount(new QuantityDiscount(5, 10));
            store.addDiscount(new LastDaysDiscount(30, 7));
        } catch (Exception e) {
            // do nothing
        }
    }

}
