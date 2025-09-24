package com.sdm.phr.gui;

import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel cardPanel;

    private CardLayout cardLayout = new CardLayout();

    public FirstScreen firstScreen;
    public PatientRegister patientRegisterPanel;
    public PatientLoginPanel patientLoginPanel;
    public AddUser addUserPanel;
    public LoginPanel loginPanel;
    public HomePanel homePanel;
    public PatientHome patientHomePanel;
    public UserHomePanel userHomePanel;

    public Main() {
        setTitle("Patient Health Record System");
        setSize(800, 500);
        setLocationRelativeTo(null);
        JDialog.setDefaultLookAndFeelDecorated(true);

        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        firstScreen = new FirstScreen(this);
        patientRegisterPanel = new PatientRegister(this);
        patientLoginPanel = new PatientLoginPanel(this);
        addUserPanel = new AddUser(this);
        loginPanel = new LoginPanel(this);
        homePanel = new HomePanel(this);
        patientHomePanel = new PatientHome(this);
        userHomePanel = new UserHomePanel(this);

        cardPanel.add(firstScreen, Main.FIRST_SCREEN);
        cardPanel.add(patientRegisterPanel, Main.PATIENT_REGISTER);
        cardPanel.add(patientLoginPanel, Main.PATIENT_LOGIN);
        cardPanel.add(addUserPanel, Main.USER_ADDD);
        cardPanel.add(loginPanel, Main.USER_LOGIN);
        cardPanel.add(userHomePanel, Main.USER_HOME);
        cardPanel.add(patientHomePanel, Main.PATIENT_HOME);

        add(cardPanel, BorderLayout.NORTH);
    }

    public void nextPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        }

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main frame = new Main();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

    public static String FIRST_SCREEN = "firstScreenPanel";
    public static String PATIENT_REGISTER = "patientRegisterPanel";
    public static String PATIENT_LOGIN = "patientLoginPanel";
    public static String USER_ADDD = "userAddPanel";
    public static String USER_LOGIN = "userLoginPanel";
    public static String PATIENT_HOME = "patientHomePanel";
    public static String USER_HOME = "userHomePanel";
}
