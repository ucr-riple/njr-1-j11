package controleurs;

import medias.Genre;
import medias.Media;
import scolarite.Cours;
import test.Bdd;

public class ControleurMedia {

	public Boolean addMedia(int idCours, int idGenre, int idMedia, String n, String a, String r, int d) {
		// on recherche le cours
		Cours cours = null;
		int i = 0;
		while (i < Bdd.listCours.size() && cours == null) {
			if (Bdd.listCours.get(i).getId() == idCours)
				cours = Bdd.listCours.get(i);
			else 
				i++;
		}
		// s'il n'existe pas : echec
		if (cours == null)
			return false;
		
		// idem pour le genre
		Genre genre = null;
		i = 0;
		while (i < Bdd.listGenres.size() && genre == null) {
			if (Bdd.listGenres.get(i).getId() == idGenre)
				genre = Bdd.listGenres.get(i);
			else
				i++;
		}
		
		if (genre == null)
			return false;
		
		// si le cours et le genre existe
		// on verifie que l'id n'est pas deja utilise
		i = 0;
		while (i < Bdd.listMedias.size()) {
			if (Bdd.listMedias.get(i).getId() == idMedia)
				return false;
			else
				i++;
		}
		
		
		// si l'id n'est pas utilise, on instancie un nouveau media
		Media media = new Media(cours, genre, idMedia, n, a, r, d);
		
		Bdd.listMedias.add(media);
		
		return true;
	}

	public Boolean setMedia(int idCours, int idGenre, int idMedia, String n, String a, String r, int d) {
		// on recherche le cours
		Cours cours = null;
		int i = 0;
		while (i < Bdd.listCours.size() && cours == null) {
			if (Bdd.listCours.get(i).getId() == idCours)
				cours = Bdd.listCours.get(i);
			else 
				i++;
		}
		// s'il n'existe pas : echec
		if (cours == null)
			return false;
		
		// idem pour le genre
		Genre genre = null;
		i = 0;
		while (i < Bdd.listGenres.size() && genre == null) {
			if (Bdd.listGenres.get(i).getId() == idGenre)
				genre = Bdd.listGenres.get(i);
			else
				i++;
		}
		
		if (genre == null)
			return false;
		
		// si le cours et le genre existe
		// on recupere l'index du media
		i = 0;
		Boolean found = false;
		while (i < Bdd.listMedias.size() && !found) {
			if (Bdd.listMedias.get(i).getId() == idMedia)
				found = true;
			else
				i++;
		}
		
		// si le media n'existe pas : echec
		if (!found)
			return false;
		
		
		// sinon on modifie le media
		Bdd.listMedias.set(i, new Media(cours, genre, idMedia, n, a, r, d));
				
		return true;
	}

	public Boolean removeMedia(int idMedia) {
		// on recupere l'index du media
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listMedias.size() && !found) {
			if (Bdd.listMedias.get(i).getId() == idMedia)
				found = true;
			else
				i++;
		}
		
		// si le media n'existe pas : echec
		if (!found)
			return false;
		
		// sinon on le supprime
		Bdd.listMedias.remove(i);
		return true;
	}

	public String getMedia() {
		String listMedias = "";
		
		int i = 0;
		while (i < Bdd.listMedias.size()) {
			listMedias += Bdd.listMedias.get(i) + "\n";
			i++;
		}
		return listMedias;
	}

	public String getMedia(int idMedia) {
		// on recupere l'index du media
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listMedias.size() && !found) {
			if (Bdd.listMedias.get(i).getId() == idMedia)
				found = true;
			else
				i++;
		}
		
		// si le media n'existe pas : echec
		if (!found)
			return "";
		
		return "" + Bdd.listMedias.get(i);
	}
	
}
