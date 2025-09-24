package nextgen.view;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import nextgen.model.Project;

public class DPackages extends javax.swing.JDialog {

    private DefaultListModel<nextgen.model.Package> listModel;
    private FProject project;

    public DPackages(FProject project) {
        super(project, true);
        this.project = project;
        listModel = new DefaultListModel<>();
        initComponents();
        setPackages();
        setLocationRelativeTo(project);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listPackage = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        bAdd = new javax.swing.JButton();
        bEdit = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Package Management");

        listPackage.setModel(listModel);
        jScrollPane1.setViewportView(listPackage);

        jLabel1.setText("Packages");

        jPanel2.setLayout(new java.awt.GridLayout());

        bAdd.setText("Add");
        bAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddActionPerformed(evt);
            }
        });
        jPanel2.add(bAdd);

        bEdit.setText("Edit");
        bEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditActionPerformed(evt);
            }
        });
        jPanel2.add(bEdit);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddActionPerformed
        String p = "";
        while (p.isEmpty()) {
            p = JOptionPane.showInputDialog(this, "Input the new Package name");
        }
        if (!project.getPackages().containsKey(p)) {
            project.getPackages().put(p, new nextgen.model.Package(p, ""));
        }
        setPackages();
        project.updatePackages();
    }//GEN-LAST:event_bAddActionPerformed

    private void bEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditActionPerformed
        nextgen.model.Package pack = listModel.get(listPackage.getSelectedIndex());
        project.getPackages().remove(listModel.get(listPackage.getSelectedIndex()).getName());

        String p = "";
        while (p.isEmpty()) {
            p = JOptionPane.showInputDialog(this, "Input the new Package name");
        }
        pack.setName(p);
        if (!project.getPackages().containsKey(p)) {
            project.getPackages().put(p, pack);
        }
        setPackages();
        project.updatePackages();
    }//GEN-LAST:event_bEditActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAdd;
    private javax.swing.JButton bEdit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList listPackage;
    // End of variables declaration//GEN-END:variables

    private void setPackages() {
        listModel.removeAllElements();
        for (nextgen.model.Package p : project.getPackages().values()) {
            listModel.addElement(p);
        }
    }
}
