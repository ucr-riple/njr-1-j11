/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.view.cli;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Callable;

import com.jdeals.libs.MyDate;
import com.jdeals.main.controller.JDealsController;
import com.jdeals.main.entity.catalogue.GeneralGood;
import com.jdeals.main.entity.catalogue.Item;
import com.jdeals.main.entity.catalogue.Restourant;
import com.jdeals.main.entity.catalogue.Travel;
import com.jdeals.main.entity.user.Manager;
import com.jdeals.main.helper.sort.ItemDateSort;
import com.jdeals.main.helper.sort.ItemIndexSort;
import com.jdeals.main.helper.sort.SortingClass.SortingDirection;

/**
 * The Class AdminMenu.
 */
public class AdminMenu extends ViewCli {

    /**
     * The Enum ProductTypes.
     */
    public enum ProductTypes {

        /**
         * The restourant.
         */
        RESTOURANT,
        /**
         * The travel.
         */
        TRAVEL,
        /**
         * The goods.
         */
        GOODS
    };

    /**
     * Instantiates a new admin menu.
     *
     * @param sysCtrl the sys ctrl
     */
    public AdminMenu(JDealsController sysCtrl) {
        super("Select Menu", sysCtrl);

        this.addItem("Admin Panel", new Callable() {
            @Override
            public Boolean call() throws Exception {
                new AdminCommandList(getSysCtrl()).runMenu();
                return true;
            }
        });

        this.addItem("Default Menu", new Callable() {
            @Override
            public Boolean call() throws Exception {
                new UserMenu(getSysCtrl()).runMenu();
                return true;
            }
        });
    }

    /**
     * The Class AdminCommandList.
     */
    public class AdminCommandList extends ViewCli {

        /**
         * Instantiates a new admin command list.
         *
         * @param sysCtrl the sys ctrl
         */
        public AdminCommandList(final JDealsController sysCtrl) {
            super("Admin Menu", sysCtrl);

            this.addItem("Insert an item", new Callable() {
                @Override
                public Object call() throws Exception {
                    return new ItemMenu(sysCtrl).runMenu();
                }
            });
            this.addItem("Delete an item", new Callable() {
                @Override
                public Boolean call() throws Exception {
                    return delItem();
                }
            });
            this.addItem("List current active offers", new Callable() {
                @Override
                public Object call() throws Exception {
                    return new ListOrderMenu(sysCtrl, false).runMenu();
                }
            });
            this.addItem("List expired offers", new Callable() {
                @Override
                public Object call() throws Exception {
                    return new ListOrderMenu(sysCtrl, true).runMenu();
                }
            });
        }

        /**
         * Del item.
         *
         * @return true, if successful
         */
        public boolean delItem() {
            System.out.println("Insert id to delete:");
            int id = JDealsController.scan.nextInt();
            JDealsController.scan.nextLine();

            if (this.getSysCtrl().getStore().delItem(id)) {
                System.out.println("Item removed successfully");
                return true;
            }

            return false;
        }

        /**
         * The Class ListOrderMenu.
         */
        public class ListOrderMenu extends ViewCli {

            /**
             * The expired.
             */
            final private boolean expired;

            /**
             * Instantiates a new list order menu.
             *
             * @param sysCtrl the sys ctrl
             * @param expired the expired
             */
            public ListOrderMenu(JDealsController sysCtrl, final boolean expired) {
                super("Sort type", sysCtrl);
                this.expired = expired;

                this.addItem("Expiry Date", new Callable() {
                    @Override
                    public Object call() throws Exception {
                        listItems(expired, new ItemDateSort(SortingDirection.DECR));
                        return true;
                    }
                });
                this.addItem("Id", new Callable() {
                    @Override
                    public Object call() throws Exception {
                        listItems(expired, new ItemIndexSort(SortingDirection.DECR));
                        return true;
                    }
                });
            }

            /**
             * List items.
             *
             * @param expired the expired
             * @param sortPoliicy the sort poliicy
             */
            public void listItems(boolean expired, Comparator<Item> sortPoliicy) {
                ArrayList<Item> list = this.getSysCtrl().getStore()
                        .getFilteredItems(expired, sortPoliicy);
                for (Item item : list) {
                    System.out.println("\n===========\n" + item);
                }
            }

        }

        /**
         * The Class ItemMenu.
         */
        public class ItemMenu extends ViewCli {

            /**
             * Instantiates a new item menu.
             *
             * @param sysCtrl the sys ctrl
             */
            public ItemMenu(JDealsController sysCtrl) {
                super("Item Menu", sysCtrl);

                this.addItem("Add general good", new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return addItem(ProductTypes.GOODS);
                    }
                });
                this.addItem("Restourant Event", new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return addItem(ProductTypes.RESTOURANT);
                    }
                });
                this.addItem("Travel Event", new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return addItem(ProductTypes.TRAVEL);
                    }
                });
            }

            /**
             * Adds the item.
             *
             * @param type the type
             * @return true, if successful
             */
            public boolean addItem(ProductTypes type) {
                Item item;

                int id = this.getSysCtrl().getStore().getItems().size();

                System.out.println("Description:");
                String description = JDealsController.scan.nextLine();

                System.out.println("Insert price:");
                double price = JDealsController.scan.nextDouble();
                JDealsController.scan.nextLine();

                switch (type) {
                    case GOODS:
                        System.out.println("Insert quantity:");
                        int avaibleItems = JDealsController.scan.nextInt();
                        JDealsController.scan.nextLine();
                        item = new GeneralGood(description, price, 0, avaibleItems, ((Manager) this.getSysCtrl().getCurUser()));
                        break;
                    case RESTOURANT:
                    case TRAVEL:
                        MyDate date = new MyDate();
                        System.out.println("Location:");
                        String location = JDealsController.scan.nextLine();

                        System.out.println("Expiry date " + date.getFormat());
                        MyDate expiryDate;
                        try {
                            expiryDate = date.fromString(JDealsController.scan
                                    .nextLine());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }

                        if (type == ProductTypes.RESTOURANT) {
                            System.out.println("Insert event quantity:");
                            int avaibleEvents = JDealsController.scan.nextInt();
                            JDealsController.scan.nextLine();

                            System.out.println("Restourant name:");
                            String name = JDealsController.scan.nextLine();
                            item = new Restourant(name, description, price, 0,
                                    expiryDate, location, avaibleEvents);
                        } else {
                            System.out
                                    .println("Departure date " + date.getFormat());
                            try {
                                MyDate ddate = date.fromString(JDealsController.scan
                                        .nextLine());
                            } catch (Exception e) {
                                e.printStackTrace();
                                return false;
                            }
                            item = new Travel(price, 0,
                                    expiryDate, location, date);
                        }
                        break;
                    default:
                        return false;
                }

                try {
                    this.getSysCtrl().getStore().addItem(item);
                    System.out.println("Item successfully Added!");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                return true;
            }
        }
    }

}
