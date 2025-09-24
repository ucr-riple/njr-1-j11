package controleurs;

import test.Bdd;
import utilisateurs.Etudiant;

public class ControleurEtudiant {

	public Boolean addEtudiant(int idEtudiant, String n, String p, String t, int nbEM) {
		// on verifie que l'id n'est pas deja utilise
		int i = 0;
		while (i < Bdd.listEtudiants.size()) {
			if (Bdd.listEtudiants.get(i).getId() == idEtudiant)
				return false;
			else
				i++;
		}
				
		// si l'id n'est pas utilise, on instancie un nouvel etudiant
		Etudiant etudiant = new Etudiant(i, n, p, t, nbEM);
				
		Bdd.listEtudiants.add(etudiant);
				
		return true;
	}

	public Boolean setEtudiant(int idEtudiant, String n, String p, String t, int nbEM) {
		// on recupere l'index de l'etudiant
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listEtudiants.size() && !found) {
			if (Bdd.listEtudiants.get(i).getId() == idEtudiant)
				found = true;
			else
				i++;
		}
		
		// si l'etudiant n'existe pas : echec
		if (!found)
			return false;
				
		// sinon on modifie l'etudiant
		Etudiant etudiant = new Etudiant(i, n, p, t, nbEM);
				
		Bdd.listEtudiants.set(i, etudiant);
				
		return true;
	}
	
}
