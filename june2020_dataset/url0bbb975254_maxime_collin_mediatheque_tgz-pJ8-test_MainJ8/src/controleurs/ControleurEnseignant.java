package controleurs;

import test.Bdd;
import utilisateurs.Enseignant;

public class ControleurEnseignant {

	public Boolean addEnseignant(int idEnseignant, String n, String p, String t, int nbEM) {
		// on verifie que l'id n'est pas deja utilise
		int i = 0;
		while (i < Bdd.listEnseignants.size()) {
			if (Bdd.listEnseignants.get(i).getId() == idEnseignant)
				return false;
			else
				i++;
		}
				
		// si l'id n'est pas utilise, on instancie un nouvel enseignant
		Enseignant enseignant = new Enseignant(i, n, p, t, nbEM);
				
		Bdd.listEnseignants.add(enseignant);
				
		return true;
	}

	public Boolean setEnseignant(int idEnseignant, String n, String p, String t, int nbEM) {
		// on recupere l'index de l'enseignant
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listEnseignants.size() && !found) {
			if (Bdd.listEnseignants.get(i).getId() == idEnseignant)
				found = true;
			else
				i++;
		}
		
		// si l'enseignant n'existe pas : echec
		if (!found)
			return false;
				
		// sinon on modifie l'enseignant
		Enseignant enseignant = new Enseignant(i, n, p, t, nbEM);
				
		Bdd.listEnseignants.set(i, enseignant);
				
		return true;
	}
	
}
