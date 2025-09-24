import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JOptionPane;

import DATA.DataStore;
import DATA.Filler;
import GUI.*;

public class Main {

	/**
	 * Entrée du programme
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException	{
		
		// On cherche le datastore enregistré précédemment.
		String workingDir = System.getProperty("user.dir");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(workingDir + "/dataStoreLocation.loc"));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				// Ceci est la localisation du DataStore
				// On essaie de le charger
			    DataStore dataStore = null;
			    try
			    {
			      FileInputStream inputFileStream = new FileInputStream(sCurrentLine);
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
			    	JOptionPane.showMessageDialog(null, "Le fichier de sauvegarde est introuvable ; merci de le localiser à nouveau.", "Fichier de sauvegarde introuvable", JOptionPane.ERROR_MESSAGE);
			    	new WelcomeFrame();
			    }
			    
			    //new MainFrame(dataStore);
			}
		} catch (FileNotFoundException e) {
			// On n'arrive pas à localiser le DataStore précédent, on affiche donc la welcome frame.
			new WelcomeFrame();
		}
	}
}
