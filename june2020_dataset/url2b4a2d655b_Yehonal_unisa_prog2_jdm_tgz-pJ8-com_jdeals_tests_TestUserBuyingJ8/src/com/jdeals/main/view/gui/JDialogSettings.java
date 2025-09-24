package com.jdeals.main.view.gui;

/*
 * Copyright (C) 2007 - 2014 Hyperweb2 All rights reserved.
 * GNU General Public License version 3; see www.hyperweb2.com/terms/
 */
import javax.swing.JDialog;

import com.jdeals.libs.Tools;
import com.jdeals.main.controller.JDealsController;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

/**
 * The Class JDialogSettings.
 */
public class JDialogSettings extends JDialog {

    /**
     * The ctrl.
     */
    private JDealsController ctrl;

    /**
     * Instantiates a new j dialog settings.
     *
     * @param ctrl the ctrl
     */
    public JDialogSettings(final JDealsController ctrl) {
        super();
        this.ctrl = ctrl;
        this.setSize(400, 300);
        this.setTitle("Settings");

        JPanel panel = new JPanel();
        panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        final JCheckBox chckbxEnableExtraFeatures = new JCheckBox("Enable extra features");
        chckbxEnableExtraFeatures.setToolTipText("Enable different features as:\n* Quantity spinner limitation in catalogue\n* Admin users panel in first frame");
        chckbxEnableExtraFeatures.setSelected(ctrl.getSettings().isExtrasEnabled());
        chckbxEnableExtraFeatures.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ctrl.getSettings().setExtrasEnabled(chckbxEnableExtraFeatures.isSelected());
                ctrl.getFrame().repaint();
                ctrl.getFrame().revalidate();
            }
        });
        panel.add(chckbxEnableExtraFeatures);

        Tools.fixMinSize(this, true);
        this.setVisible(true);
    }
}
