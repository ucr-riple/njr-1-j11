package GUI;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import DATA.DataStore;
import DATA.Filler;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

public class WelcomeFrame extends JFrame {

	private JFrame frame;
	private DataStore dataStore;
	/**
	 * Create the application.
	 */
	public WelcomeFrame() {
		super();
		System.out.println("new WelcomeFrame()");
		setTitle("EdT");
		initialize();
		this.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 250, 140);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JButton b1 = new JButton("Nouveau");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// On demande d'abord où l'utilisateur souhaite enregistrer sa base de données.
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Emploi du Temps", "EdT"));
				int res = fc.showSaveDialog(frame);
				File f = null;
				if (res == JFileChooser.APPROVE_OPTION)	{
					// On a le fichier.
					f = fc.getSelectedFile();
					// On sauvegarde sa localisation pour la prochaine ouverture du programme.
					File dataStoreLocation = new File(System.getProperty("user.dir") + "/dataStoreLocation.loc");
					BufferedWriter writer;
					try {
						writer = new BufferedWriter(new FileWriter(dataStoreLocation));
						writer.write (f.getAbsolutePath()); // On écrit le chemin du DataStore
						writer.close();
						new StartFrame();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Impossible de sauvegarder la localisation du fichier de sauvegarde.", "Erreur de sauvegarde", JOptionPane.ERROR_MESSAGE);
					}
					setVisible(false);
				}
			}
		});
		
		
		JButton b2 = new JButton("Ouvrir");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Emploi du Temps", "EdT"));
				int res = fc.showOpenDialog(frame);
				File f = null;
				if (res == JFileChooser.APPROVE_OPTION)	{
					// On a le fichier.
					f = fc.getSelectedFile();
					// On sauvegarde sa localisation pour la prochaine ouverture du programme.
					File dataStoreLocation = new File(System.getProperty("user.dir") + "/dataStoreLocation.loc");
					BufferedWriter writer;
					try {
						writer = new BufferedWriter(new FileWriter(dataStoreLocation));
						writer.write (f.getAbsolutePath()); // On écrit le chemin du DataStore
						writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Impossible de sauvegarder la localisation du fichier de sauvegarde.", "Erreur de sauvegarde", JOptionPane.ERROR_MESSAGE);
					}

					// On essaie maintenant de charger le DataStore
					try
					{
						FileInputStream inputFileStream = new FileInputStream(f.getAbsolutePath());
						ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
						dataStore = (DataStore)objectInputStream.readObject();
						objectInputStream.close();
						inputFileStream.close();
					}
					catch(ClassNotFoundException e)
					{
						JOptionPane.showMessageDialog(null, "Erreur. Le fichier de sauvegarde semble corrumpu. Veuillez réessayer ou recréer une nouvelle base de données.", "Fichier de sauvegarde corrompu", JOptionPane.ERROR_MESSAGE);
						new WelcomeFrame();
					}
					catch(IOException i)
					{
						JOptionPane.showMessageDialog(null, "Fichier de sauvegarde introuvable", "Le fichier de sauvegarde est introuvable ; merci de le localiser à nouveau.", JOptionPane.ERROR_MESSAGE);
						new WelcomeFrame();
					}

					new MainFrame(dataStore);
					setVisible(false);
				}
			}
		});
		JButton b3 = new JButton("À Propos");
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AboutFrame();
			}
		});
		JButton b4 = new JButton("Quitter");
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 23, 23));
		this.getContentPane().add(b1);
		this.getContentPane().add(b2);
		this.getContentPane().add(b3);
		this.getContentPane().add(b4);
	}

}

