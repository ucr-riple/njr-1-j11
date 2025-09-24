package Demo;

import javax.swing.*;

import org.sgbd.Mysgbd.*;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.sgbd.Mysgbd.data.*;

@SuppressWarnings("serial")
class LoginDemo extends JFrame {
	JButton SUBMIT;
	JLabel label1, label2;
	JPanel LoginPanel; 
	final JTextField text1, text2;

	LoginDemo() {
		setTitle("Login Form");
	    
		
		setLayout(null);
		label1 = new JLabel();
		label1.setText("Username:");
		text1 = new JTextField(15);

		label2 = new JLabel();
		label2.setText("Password:");
		text2 = new JPasswordField(15);

		SUBMIT = new JButton("SUBMIT");
		label1.setBounds(350, 100, 100, 20);
		text1.setBounds(450, 100, 200, 20);
		label2.setBounds(350, 130, 100, 20);
		text2.setBounds(450, 130, 200, 20);
		SUBMIT.setBounds(450, 160, 100, 20);
		add(label1);
		add(text1);
		add(label2);
		add(text2);
		add(SUBMIT);

		setVisible(true);
		setSize(1024, 768);

		SUBMIT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String value1 = text1.getText();
				String value2 = text2.getText();
				String req = "select * from user where username='" + value1
						+ "' and password='" + value2 + "'";
				try {

					String uname = "lhoussaine";
					String pass = "lhoussaine";

					if (value1.equals("") && value2.equals("")) {
						JOptionPane.showMessageDialog(null,
								"Enter login name or password", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (value1.equals(uname) && value2.equals(pass)) {
						NextPage page = new NextPage(uname);
						page.setVisible(true);
						setVisible(false);
					} else if (!value1.equals(uname) && !value2.equals(pass)) {
						text1.setText("");
						text2.setText("");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

}
