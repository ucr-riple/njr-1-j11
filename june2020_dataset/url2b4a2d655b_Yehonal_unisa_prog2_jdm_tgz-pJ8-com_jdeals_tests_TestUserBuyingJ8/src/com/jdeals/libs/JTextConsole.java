/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
package com.jdeals.libs;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.text.JTextComponent;

/**
 * This class handle input and output using visual components .
 *
 * @author Giuseppe Ronca
 */
public class JTextConsole {

    /**
     * Console.
     *
     * @param output the output
     * @param input the input
     * @param out the out
     * @param in the in
     */
    public static void console(final JTextArea output, final JTextComponent input, final InputStream out, final PrintWriter in) {
        // handle "System.out"
        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                Scanner s = new Scanner(out);
                while (s.hasNextLine()) {
                    publish(s.nextLine() + "\n");
                }
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String line : chunks) {
                    output.append(line);
                }
            }
        }.execute();

        // handle "System.in"
        input.addKeyListener(new KeyAdapter() {
            private StringBuffer line = new StringBuffer();

            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == KeyEvent.VK_ENTER) {
                    in.println(line);
                    line.setLength(0);
                } else if (c == KeyEvent.VK_BACK_SPACE) {
                    line.setLength(line.length() - 1);
                } else if (!Character.isISOControl(c)) {
                    line.append(e.getKeyChar());
                }
            }
        });
    }
}
