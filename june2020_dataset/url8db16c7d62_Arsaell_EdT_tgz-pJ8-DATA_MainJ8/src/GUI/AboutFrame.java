package GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AboutFrame extends JFrame {

	private JPanel contentPane;

	public AboutFrame() {
		super();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("About");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTextArea jta = new JTextArea("\n\nCe programme est le résultat d'un projet d'informatique réalisé par :\n\tRémi DUPOUY\n\tLéo MARTINEZ\n\tGatien PASCAUD\n\tTom RAVIX\n\ndans le cadre de l'enseignement d'informatique en seconde année\ndu premier cycle de l'INSA de Lyon.\n\n");
		jta.setEditable(false);
		contentPane.add(jta);
		
		JButton jb = new JButton("Fermer");
		jb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		
		contentPane.add(jb);
		
		this.setVisible(true);
	}

}
