/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.libs;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

import javax.swing.text.MaskFormatter;

/**
 * Various methods to implement generic features .
 *
 * @author Giuseppe Ronca
 */
public class Tools {

    /**
     * Prints the var using newline
     *
     * @param value the value
     * @param varName the var name
     * @param className the name of class to print, can be null or empty to
     * avoid classname prefix
     * @param raw the raw
     * @return the string
     */
    public static String printVar(Object value, String varName, String className, boolean raw) {
        return printVar(value, varName, className, raw, true);
    }

    /**
     * Prints the var.
     *
     * @param value the value
     * @param varName the var name
     * @param className the name of class to print, can be null or empty to
     * avoid classname prefix
     * @param raw the raw
     * @param newLine the new line
     * @return the string
     */
    public static String printVar(Object value, String varName, String className, boolean raw, boolean newLine) {
        return printVars(className, raw, newLine, new MyVar(value, varName));
    }

    /**
     * Prints the vars.
     *
     * @param className the name of class to print, can be null or empty to
     * avoid classname prefix
     * @param raw the raw
     * @param newLine the new line
     * @param varList the var list
     * @return the string
     */
    public static String printVars(String className, boolean raw, boolean newLine, MyVar... varList) {
        String res = className == null ? "" : className + (newLine ? "\n" : " ");

        for (MyVar v : varList) {
            res += raw ? "" : "[";
            if (v.name != null && !raw) {
                res += v.name + ": ";
            }

            res += v.value + (raw ? " " : "] ") + (newLine ? "\n" : "");
        }

        return res;
    }

    /**
     * Fix min size.
     *
     * @param frame the frame
     * @param restoreDim the restore dim
     */
    public static void fixMinSize(final Window frame, boolean restoreDim) {
        Dimension dim = frame.getSize();
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        if (restoreDim) {
            frame.setSize(dim);
        }

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension d = frame.getSize();
                Dimension minD = frame.getMinimumSize();
                if (d.width < minD.width) {
                    d.width = minD.width;
                }
                if (d.height < minD.height) {
                    d.height = minD.height;
                }
                frame.setSize(d);
            }
        });
    }

    /**
     * Formatted mask.
     *
     * @param strMask the str mask
     * @return the mask formatter
     */
    public static MaskFormatter formattedMask(String strMask) {
        MaskFormatter mask = null;
        try {
            //
            // Create a MaskFormatter for accepting phone number, the # symbol accept
            // only a number. We can also set the empty value with a place holder
            // character.
            //
            mask = new MaskFormatter(strMask);
            mask.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return mask;
    }

    /**
     * Round decimal pos.
     *
     * @param val the val
     * @param pos the pos
     * @return the double
     */
    public static double RoundDecimalPos(double val, int pos) {
        return new BigDecimal(val).setScale(pos, RoundingMode.HALF_UP).doubleValue();
    }
}
