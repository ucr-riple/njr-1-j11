package nextgen.view;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import nextgen.importer.MysqlImporter;
import nextgen.model.Project;

public class DImportMysql extends javax.swing.JDialog {

    private FProject project;

    public DImportMysql(FProject project) {
        super(project, true);
        this.project = project;
        initComponents();
        setLocationRelativeTo(project);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        bImport = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tHostname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tDatabase = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tPort = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tUser = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tPassword = new javax.swing.JTextField();

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

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        bImport.setText("Import");
        bImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImportActionPerformed(evt);
            }
        });
        jPanel2.add(bImport);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Mysql Credentials"));
        jPanel3.setLayout(new java.awt.GridLayout(5, 2, 0, 10));

        jLabel2.setText("Hostname:");
        jPanel3.add(jLabel2);

        tHostname.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tHostname.setText("127.0.0.1");
        jPanel3.add(tHostname);

        jLabel6.setText("Database:");
        jPanel3.add(jLabel6);

        tDatabase.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel3.add(tDatabase);

        jLabel5.setText("Port:");
        jPanel3.add(jLabel5);

        tPort.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tPort.setText("3306");
        jPanel3.add(tPort);

        jLabel3.setText("User:");
        jPanel3.add(jLabel3);

        tUser.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tUser.setText("root");
        jPanel3.add(tUser);

        jLabel4.setText("Password:");
        jPanel3.add(jLabel4);

        tPassword.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel3.add(tPassword);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImportActionPerformed
        try {
            MysqlImporter.importer(project.getProject(), tHostname.getText(), tUser.getText(), tPassword.getText(), (Integer.parseInt(tPort.getText())), tDatabase.getText());
            project.refreshElementList();
        } catch (SQLException ex) {
            Logger.getLogger(DImportMysql.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(project, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_bImportActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bImport;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField tDatabase;
    private javax.swing.JTextField tHostname;
    private javax.swing.JTextField tPassword;
    private javax.swing.JTextField tPort;
    private javax.swing.JTextField tUser;
    // End of variables declaration//GEN-END:variables

}
