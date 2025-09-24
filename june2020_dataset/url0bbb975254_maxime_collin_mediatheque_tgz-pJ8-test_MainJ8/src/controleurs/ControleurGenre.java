package controleurs;

import medias.Genre;
import test.Bdd;

public class ControleurGenre {

	public Boolean addGenre(int idGenre, String n, int de) {
		// on verifie que l'id n'est pas deja utilise
		int i = 0;
		while (i < Bdd.listGenres.size()) {
			if (Bdd.listGenres.get(i).getId() == idGenre)
				return false;
			else
				i++;
		}
		
		// si l'id n'est pas utilise, on instancie un nouveau genre
		Genre genre = new Genre(idGenre, n, de);
		
		Bdd.listGenres.add(genre);
		
		return true;
	}

	public Boolean setGenre(int idGenre, String n, int de) {
		// on recupere l'index du genre
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listGenres.size() && !found) {
			if (Bdd.listGenres.get(i).getId() == idGenre)
				found = true;
			else
				i++;
		}
		
		// si le genre n'existe pas : echec
		if (!found)
			return false;
		
		
		// sinon on modifie le genre
		Bdd.listGenres.set(i, new Genre(idGenre, n, de));
				
		return true;
	}

	public Boolean removeGenre(int idGenre) {
		// on recupere l'index du genre
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listGenres.size() && !found) {
			if (Bdd.listGenres.get(i).getId() == idGenre)
				found = true;
			else
				i++;
		}
		
		// si le genre n'existe pas : echec
		if (!found)
			return false;
		
		// sinon on le supprime
		Bdd.listGenres.remove(i);
		return true;
	}

	public String getGenre() {
		String listGenres = "";
		
		int i = 0;
		while (i < Bdd.listGenres.size()) {
			listGenres += Bdd.listGenres.get(i) + "\n";
			i++;
		}
		
		return listGenres;
	}

	public String getGenre(int idGenre) {
		// on recupere l'index du genre
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listGenres.size() && !found) {
			if (Bdd.listGenres.get(i).getId() == idGenre)
				found = true;
			else
				i++;
		}
		
		// si le genre n'existe pas : echec
		if (!found)
			return "";
		
		return "" + Bdd.listGenres.get(i);
	}

}
