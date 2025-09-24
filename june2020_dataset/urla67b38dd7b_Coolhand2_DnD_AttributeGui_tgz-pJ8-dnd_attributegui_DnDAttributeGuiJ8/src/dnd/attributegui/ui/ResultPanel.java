/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd.attributegui.ui;


import dnd.attributegui.Character;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
/**
 *
 * @author Mike
 */
public class ResultPanel extends JPanel{
    private Character _character;
    private JLabel _strengthValue, _dexterityValue, _constitutionValue, _intelligenceValue, _wisdomValue, _charismaValue;
    private JLabel _strengthMod, _dexterityMod, _constitutionMod, _intelligenceMod, _wisdomMod, _charismaMod;
    public ResultPanel(Character c){
        _character = c;
        loadPanel();
        setupPanel();
        attachListeners();
    }

    private void loadPanel(){
        _strengthValue = new JLabel("10");
        _dexterityValue = new JLabel("10");
        _constitutionValue = new JLabel("10");
        _intelligenceValue = new JLabel("10");
        _wisdomValue = new JLabel("10");
        _charismaValue = new JLabel("10");
        _strengthMod = new JLabel("+0");
        _dexterityMod = new JLabel("+0");
        _constitutionMod = new JLabel("+0");
        _intelligenceMod = new JLabel("+0");
        _wisdomMod = new JLabel("+0");
        _charismaMod = new JLabel("+0");
    }

    private void setupPanel(){
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        JLabel strengthLabel = new JLabel("STR");
        JLabel dexterityLabel = new JLabel("DEX");
        JLabel constitutionLabel = new JLabel("CON");
        JLabel intelligenceLabel = new JLabel("INT");
        JLabel charismaLabel = new JLabel("CHA");
        JLabel wisdomLabel = new JLabel("WIS");

        JLabel attributeTitle = new JLabel("Attribute");
        JLabel attributeValue = new JLabel("Value");
        JLabel attributeMod = new JLabel("Modifier");

        JSeparator separatorOne = new JSeparator(JSeparator.HORIZONTAL);
        JSeparator separatorTwo = new JSeparator(JSeparator.HORIZONTAL);
        JSeparator separatorThree = new JSeparator(JSeparator.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(
                    layout.createParallelGroup()
                        .addComponent(attributeTitle)
                        .addComponent(attributeValue)
                        .addComponent(attributeMod))
                .addGroup(
                    layout.createParallelGroup()
                        .addComponent(separatorOne)
                        .addComponent(separatorTwo)
                        .addComponent(separatorThree))
                .addGroup(
                    layout.createParallelGroup()
                        .addComponent(strengthLabel)
                        .addComponent(_strengthValue)
                        .addComponent(_strengthMod))
                .addGroup(
                    layout.createParallelGroup()
                        .addComponent(dexterityLabel)
                        .addComponent(_dexterityValue)
                        .addComponent(_dexterityMod))
                .addGroup(
                    layout.createParallelGroup()
                        .addComponent(constitutionLabel)
                        .addComponent(_constitutionValue)
                        .addComponent(_constitutionMod))
                .addGroup(
                    layout.createParallelGroup()
                        .addComponent(intelligenceLabel)
                        .addComponent(_intelligenceValue)
                        .addComponent(_intelligenceMod))
                .addGroup(
                    layout.createParallelGroup()
                        .addComponent(wisdomLabel)
                        .addComponent(_wisdomValue)
                        .addComponent(_wisdomMod))
                .addGroup(
                    layout.createParallelGroup()
                        .addComponent(charismaLabel)
                        .addComponent(_charismaValue)
                        .addComponent(_charismaMod))
        );
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(
                    layout.createParallelGroup()
                        .addComponent(attributeTitle)
                        .addComponent(separatorOne)
                        .addComponent(strengthLabel)
                        .addComponent(dexterityLabel)
                        .addComponent(constitutionLabel)
                        .addComponent(intelligenceLabel)
                        .addComponent(wisdomLabel)
                        .addComponent(charismaLabel))
                .addGroup(
                    layout.createParallelGroup()
                        .addComponent(attributeValue)
                        .addComponent(separatorTwo)
                        .addComponent(_strengthValue)
                        .addComponent(_dexterityValue)
                        .addComponent(_constitutionValue)
                        .addComponent(_intelligenceValue)
                        .addComponent(_wisdomValue)
                        .addComponent(_charismaValue))
                .addGroup(
                    layout.createParallelGroup()
                        .addComponent(attributeMod)
                        .addComponent(separatorThree)
                        .addComponent(_strengthMod)
                        .addComponent(_dexterityMod)
                        .addComponent(_constitutionMod)
                        .addComponent(_intelligenceMod)
                        .addComponent(_wisdomMod)
                        .addComponent(_charismaMod))
        );}
    private void attachListeners(){
        _character.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                updateValues(_character.getAttributes());
            }
        });
    }

    private void updateValues(int[] attributes){
        _strengthValue.setText(String.valueOf(attributes[0]));
        _dexterityValue.setText(String.valueOf(attributes[1]));
        _constitutionValue.setText(String.valueOf(attributes[2]));
        _intelligenceValue.setText(String.valueOf(attributes[3]));
        _wisdomValue.setText(String.valueOf(attributes[4]));
        _charismaValue.setText(String.valueOf(attributes[5]));

        _strengthMod.setText(String.valueOf((attributes[0]-10)/2));
        _dexterityMod.setText(String.valueOf((attributes[1]-10)/2));
        _constitutionMod.setText(String.valueOf((attributes[2]-10)/2));
        _intelligenceMod.setText(String.valueOf((attributes[3]-10)/2));
        _wisdomMod.setText(String.valueOf((attributes[4]-10)/2));
        _charismaMod.setText(String.valueOf((attributes[5]-10)/2));
    }
}
