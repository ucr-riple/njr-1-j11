/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd.attributegui.ui;

import dnd.attributegui.Character;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Mike
 */
public class GeneratePanel extends JPanel {

    private Character _character;
    private JButton _button;

    public GeneratePanel(Character c) {
        _character = c;
        loadPanel();
        setupPanel();
        attachListeners();
    }

    private void loadPanel() {
        _button = new JButton("Generate!");
    }

    private void setupPanel() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addComponent(_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addComponent(_button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
    }

    private void attachListeners() {
        _button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                _character.generateAttributes();
            }
        });
    }
}
