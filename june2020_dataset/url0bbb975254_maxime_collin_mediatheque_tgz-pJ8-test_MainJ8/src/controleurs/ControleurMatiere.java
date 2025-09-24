package controleurs;

import scolarite.Matiere;
import test.Bdd;

public class ControleurMatiere {

	public Boolean addMatiere(int idMatiere, String n) {
		// on verifie que l'id n'est pas deja utilise
		int i = 0;
		while (i < Bdd.listMatieres.size()) {
			if (Bdd.listMatieres.get(i).getId() == idMatiere)
				return false;
			else
				i++;
		}
		
		// si l'id n'est pas utilise, on instancie une nouvelle matiere
		Matiere matiere = new Matiere(idMatiere, n);
		
		Bdd.listMatieres.add(matiere);
		
		return true;
	}

	public Boolean setMatiere(int idMatiere, String n) {
		// on recupere l'index de la matiere
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listMatieres.size() && !found) {
			if (Bdd.listMatieres.get(i).getId() == idMatiere)
				found = true;
			else
				i++;
		}
		
		// si la matiere n'existe pas : echec
		if (!found)
			return false;
		
		
		// sinon on modifie la matiere
		Bdd.listMatieres.set(i, new Matiere(idMatiere, n));
				
		return true;
	}

	public Boolean removeMatiere(int idMatiere) {
		// on recupere l'index de la matiere
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listMatieres.size() && !found) {
			if (Bdd.listMatieres.get(i).getId() == idMatiere)
				found = true;
			else
				i++;
		}
		
		// si la matiere n'existe pas : echec
		if (!found)
			return false;
		
		// sinon on la supprime
		Bdd.listMatieres.remove(i);
		return true;
	}

	public String getMatiere() {
		String listMatieres = "";
		
		int i = 0;
		while (i < Bdd.listMatieres.size()) {
			listMatieres += Bdd.listMatieres.get(i) + "\n";
			i++;
		}
		
		return listMatieres;
	}

	public String getMatiere(int idMatiere) {
		// on recupere l'index de la matiere
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listMatieres.size() && !found) {
			if (Bdd.listMatieres.get(i).getId() == idMatiere)
				found = true;
			else
				i++;
		}
		
		// si la matiere n'existe pas : echec
		if (!found)
			return "";
		
		return "" + Bdd.listMatieres.get(i);
	}

}
