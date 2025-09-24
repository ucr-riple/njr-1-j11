package nextgen.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import nextgen.model.Attribute;
import nextgen.model.Element;
import nextgen.model.Key;
import nextgen.model.enums.Cardinality;
import nextgen.model.enums.KeyType;

public class DKey extends javax.swing.JDialog {

    private Key key;
    private FProject project;
    private PElement element;

    public DKey(FProject project, PElement element, Key key) {
        super(project, true);
        this.project = project;
        this.element = element;
        this.key = key;
        initComponents();
        setUpData();
        setTitle(key.getName());
        setLocationRelativeTo(element);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tName = new javax.swing.JTextField();
        pAttributes = new javax.swing.JPanel();
        bUpdate = new javax.swing.JButton();
        cbKeyType = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Name:");

        pAttributes.setBorder(javax.swing.BorderFactory.createTitledBorder("Attributes"));

        javax.swing.GroupLayout pAttributesLayout = new javax.swing.GroupLayout(pAttributes);
        pAttributes.setLayout(pAttributesLayout);
        pAttributesLayout.setHorizontalGroup(
            pAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pAttributesLayout.setVerticalGroup(
            pAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        bUpdate.setText("Update");
        bUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbKeyType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tName))
                    .addComponent(pAttributes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbKeyType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bUpdate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pAttributes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdateActionPerformed
        key.setName(tName.getText());
        key.setType((KeyType)cbKeyType.getSelectedItem());
        element.refreshKeys();
    }//GEN-LAST:event_bUpdateActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bUpdate;
    private javax.swing.JComboBox cbKeyType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pAttributes;
    private javax.swing.JTextField tName;
    // End of variables declaration//GEN-END:variables

    private void setUpData() {
        cbKeyType.addItem(KeyType.Primary);
        cbKeyType.addItem(KeyType.Unique);
        cbKeyType.setSelectedItem(key.getType());
        tName.setText(key.getName());

        Element e = this.element.getElement();
        pAttributes.setLayout(new GridLayout(e.getAttributes().size(), 1));
        for (Attribute a : e.getAttributes()) {
            if (a.getCardinality() == Cardinality.Single) {
                JCheckBox cb = new JCheckBox(a.getName());
                if (key.getAttributes().contains(a)){
                    cb.setSelected(true);
                }
                cb.addActionListener(new ActionCbListener(a));
                pAttributes.add(cb);
            }
        }
        pack();
    }

    private class ActionCbListener implements ActionListener {
        private Attribute attribute;

        public ActionCbListener(Attribute attribute) {
            this.attribute = attribute;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox cb = (JCheckBox) e.getSource();
            if (cb.isSelected()) {
                key.getAttributes().add(attribute);
            } else {
                key.getAttributes().remove(attribute);
            }
        }
    }
}
