package controleurs;

import java.util.Date;

import medias.Media;
import test.Bdd;
import utilisateurs.Utilisateur;

public class ControleurEmprunt {
	
	ControleurUser controleurUser = new ControleurUser();
	
	public Boolean emprunter(int idUser, int idMedia, Date de, Date dr) {
		Utilisateur user = controleurUser.getUtilisateur(idUser);
		
		if (user == null)
			return false;
		
		// on recupere l'index du media
		int i = 0;
		Media media = null;
		while (i < Bdd.listMedias.size() && media == null) {
			if (Bdd.listMedias.get(i).getId() == idMedia)
				media = Bdd.listMedias.get(i);
			else
				i++;
		}
	
		// si le media n'existe pas : echec
		if (media == null)
			return false;
		
		// si l'emprunt est effectue correctement
		if (user.emprunter(media) == 1)
			return true;
		else
			return false;
	}
	
	public Boolean emprunter(int idUser, int idMedia, Date de, Date dr, int nbJours) {
		Utilisateur user = controleurUser.getUtilisateur(idUser);
		
		if (user == null)
			return false;
		
		// on recupere l'index du media
		int i = 0;
		Media media = null;
		while (i < Bdd.listMedias.size() && media == null) {
			if (Bdd.listMedias.get(i).getId() == idMedia)
				media = Bdd.listMedias.get(i);
			else
				i++;
		}
	
		// si le media n'existe pas : echec
		if (media == null)
			return false;
		
		// si l'emprunt est effectue correctement
		if (user.emprunter(media, nbJours) == 1)
			return true;
		else
			return false;
	}

	public Boolean commenter(int idUser, int idMedia, int n, String c) {
		Utilisateur user = controleurUser.getUtilisateur(idUser);
		
		if (user == null)
			return false;
		
		// on recupere l'index de l'emprunt
		int indexEmpruntUser = 0;
		Boolean found = false;
		while (indexEmpruntUser < user.getEmprunts().size() && !found) {
			if (user.getEmprunts().get(indexEmpruntUser).getMedia().getId() == idMedia)
				found = true;
			else
				indexEmpruntUser++;
		}
	
		// si le media n'existe pas : echec
		if (!found)
			return false;
		
		// si le commentaire est effectue correctement
		if (user.commenter(indexEmpruntUser, n, c) == 1)
			return true;
		else
			return false;
	}
}
