/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.main.helper.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Class FileSerialization.
 *
 * @param <T> the generic type
 */
public class FileSerialization<T extends Serializable> implements
        DataBaseInterface<T> {

    /* (non-Javadoc)
     * @see com.jdeals.main.helper.storage.DataBaseInterface#storeList(java.util.ArrayList, java.lang.String)
     */
    @Override
    public void storeList(ArrayList<T> list, String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (Serializable elem : list) {
                out.writeObject(elem);
            }
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see com.jdeals.main.helper.storage.DataBaseInterface#loadList(java.lang.String)
     */
    @Override
    public ArrayList<T> loadList(String filePath) {

        ArrayList<T> list = new ArrayList<>();
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        try {
            fileIn = new FileInputStream(filePath);
            in = new ObjectInputStream(fileIn);
            while (true) {
                list.add((T) in.readObject());
            }
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fileIn != null) {
                    fileIn.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        return list;
    }

}
