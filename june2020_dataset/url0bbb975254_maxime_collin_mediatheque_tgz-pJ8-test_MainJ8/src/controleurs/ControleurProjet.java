package controleurs;

import java.util.Date;

import scolarite.Cours;
import scolarite.Projet;
import test.Bdd;
import utilisateurs.Etudiant;

public class ControleurProjet {

	public Boolean addProjet(int idCours, int idProjet, String n, String d, Date dd, Date df) {
		// on recherche le cours
		Cours cours = null;
		int indexCours = 0;
		while (indexCours < Bdd.listCours.size() && cours == null) {
			if (Bdd.listCours.get(indexCours).getId() == idCours)
				cours = Bdd.listCours.get(indexCours);
			else 
				indexCours++;
		}
		// s'il n'existe pas : echec
		if (cours == null)
			return false;
				
		// si le cours existe
		// on verifie que l'id n'est pas deja utilise
		int i = 0;
		while (i < Bdd.listProjets.size()) {
			if (Bdd.listProjets.get(i).getId() == idProjet)
				return false;
			else
				i++;
		}
		
		
		// si l'id n'est pas utilise, on instancie un nouveau projet
		Projet projet = new Projet(cours, null, idProjet, n, d, dd, df);
		
		Bdd.listProjets.add(projet);
		Bdd.listCours.get(indexCours).getProjets().add(projet);
		return true;
	}

	public Boolean setProjet(int idCours, int idProjet, String n, String d, Date dd, Date df) {
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
				
		// si le cours existe
		// on recupere l'index du projet
		i = 0;
		Projet projet = null;
		while (i < Bdd.listProjets.size() && projet == null) {
			if (Bdd.listProjets.get(i).getId() == idProjet)
				projet = Bdd.listProjets.get(i);
			else
				i++;
		}
		
		// si le projet n'existe pas : echec
		if (projet == null)
			return false;
		
		
		// sinon on modifie le projet, en gardant les memes etudiants
		Bdd.listProjets.set(i, new Projet(cours, projet.getEtudiants(), idProjet, n, d, dd, df));
				
		return true;
	}

	public Boolean removeProjet(int idProjet) {
		// on recupere l'index du projet
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listProjets.size() && !found) {
			if (Bdd.listProjets.get(i).getId() == idProjet)
				found = true;
			else
				i++;
		}
		
		// si le projet n'existe pas : echec
		if (!found)
			return false;
		
		// sinon on le supprime
		Bdd.listProjets.remove(i);
		return true;
	}

	public String getProjet() {
		String listProjets = "";
		
		int i = 0;
		while (i < Bdd.listProjets.size()) {
			listProjets += Bdd.listProjets.get(i) + "\n";
			i++;
		}
		
		return listProjets;
	}

	public String getProjet(int idProjet) {
		// on recupere l'index du projet
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listProjets.size() && !found) {
			if (Bdd.listProjets.get(i).getId() == idProjet)
				found = true;
			else
				i++;
		}
		
		// si le projet n'existe pas : echec
		if (!found)
			return "";
		
		return "" + Bdd.listProjets.get(i);
	}
	
	public Boolean addEtudiantProjet(int idEtudiant, int idProjet) {
		// on recupere l'index de l'etudiant
		int indexEtudiant = 0;
		Etudiant etudiant = null;
		while (indexEtudiant < Bdd.listEtudiants.size() && etudiant == null) {
			if (Bdd.listEtudiants.get(indexEtudiant).getId() == idEtudiant)
				etudiant = Bdd.listEtudiants.get(indexEtudiant);
			else
				indexEtudiant++;
		}
				
		// si l'etudiant n'existe pas : echec
		if (etudiant == null)
			return false;
		
		// on recupere l'index du projet
		int indexProjet = 0;
		Projet projet = null;
		while (indexProjet < Bdd.listProjets.size() && projet == null) {
			if (Bdd.listProjets.get(indexProjet).getId() == idProjet)
				projet = Bdd.listProjets.get(indexProjet);
			else
				indexProjet++;
		}
				
		// si le projet n'existe pas : echec
		if (projet == null)
			return false;
		
		// sinon on ajoute l'etudiant au projet
		Bdd.listProjets.get(indexProjet).getEtudiants().add(etudiant);
		return true;
	}

	public Boolean removeEtudiantProjet(int idEtudiant, int idProjet) {
		// on recupere l'index du projet
		int indexProjet = 0;
		Projet projet = null;
		while (indexProjet < Bdd.listProjets.size() && projet == null) {
			if (Bdd.listProjets.get(indexProjet).getId() == idProjet)
				projet = Bdd.listProjets.get(indexProjet);
			else
				indexProjet++;
		}
				
		// si le projet n'existe pas : echec
		if (projet == null)
			return false;
		
		// on recupere l'index de l'etudiant dans le projet
		int indexEtudiantProjet = 0;
		Etudiant etudiant = null;
		while (indexEtudiantProjet < projet.getEtudiants().size() && etudiant == null) {
			if (projet.getEtudiants().get(indexEtudiantProjet).getId() == idEtudiant)
				etudiant = projet.getEtudiants().get(indexEtudiantProjet);
			else
				indexEtudiantProjet++;
		}
				
		// si l'etudiant n'existe pas : echec
		if (etudiant == null)
			return false;
		
		// sinon on supprime l'etudiant du projet
		Bdd.listProjets.get(indexProjet).getEtudiants().remove(indexEtudiantProjet);
		return true;
	}
}
