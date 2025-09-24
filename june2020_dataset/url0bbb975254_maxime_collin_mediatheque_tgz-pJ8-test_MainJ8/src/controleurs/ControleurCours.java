package controleurs;

import test.Bdd;
import utilisateurs.Enseignant;
import utilisateurs.Etudiant;
import scolarite.Cours;
import scolarite.Cycle;
import scolarite.Matiere;

public class ControleurCours {

	public Boolean addCours(int idCycle, int idMatiere, int idEnseignant, int idCours, String n) {
		// on verifie que le Cycle existe, sinon : echec		
		Cycle cycle = null;
		int i = 0;
		while (i < Bdd.listCycles.size() && cycle == null) {
			if (Bdd.listCycles.get(i).getId() == idCycle)
				cycle = Bdd.listCycles.get(i);
			else
				i++;
		}
		
		if (cycle == null)
			return false;
		
		
		// on verifie que la Matiere existe, sinon : echec
		Matiere matiere = null;
		i = 0;
		while(i < Bdd.listMatieres.size() && matiere == null) {
			if (Bdd.listMatieres.get(i).getId() == idMatiere)
				matiere = Bdd.listMatieres.get(i);
			else
				i++;
		}
		
		if (matiere == null)
			return false;
		
		
		// on verifie que l'Enseignant existe, sinon : echec
		Enseignant enseignant = null;
		i = 0;
		while (i < Bdd.listEnseignants.size() && enseignant == null) {
			if (Bdd.listEnseignants.get(i).getId() == idEnseignant)
				enseignant = Bdd.listEnseignants.get(i);
			else
				i++;
		}
		
		if (enseignant == null)
			return false;
		
		
		// on verifie que l'id n'est pas deja utilise
		i = 0;
		while (i < Bdd.listCours.size()) {
			if (Bdd.listCours.get(i).getId() == idCours)
				return false;
			else
				i++;
		}
		
		// si l'id n'est pas utilise, on instancie un nouveau Cours
		Cours Cours = new Cours(cycle, matiere, enseignant, idCours, n);
		
		Bdd.listCours.add(Cours);
		
		return true;
	}

	public Boolean setCours(int idCycle, int idMatiere, int idEnseignant, int idCours, String n) {
		// on verifie que le Cycle existe, sinon : echec		
		Cycle cycle = null;
		int i = 0;
		while (i < Bdd.listCycles.size() && cycle == null) {
			if (Bdd.listCycles.get(i).getId() == idCycle)
				cycle = Bdd.listCycles.get(i);
			else
				i++;
		}
		
		if (cycle == null)
			return false;
		
		
		// on verifie que la Matiere existe, sinon : echec
		Matiere matiere = null;
		i = 0;
		while(i < Bdd.listMatieres.size() && matiere == null) {
			if (Bdd.listMatieres.get(i).getId() == idMatiere)
				matiere = Bdd.listMatieres.get(i);
			else
				i++;
		}
		
		if (matiere == null)
			return false;
		
		
		// on verifie que l'Enseignant existe, sinon : echec
		Enseignant enseignant = null;
		i = 0;
		while (i < Bdd.listEnseignants.size() && enseignant == null) {
			if (Bdd.listEnseignants.get(i).getId() == idEnseignant)
				enseignant = Bdd.listEnseignants.get(i);
			else
				i++;
		}
		
		if (enseignant == null)
			return false;
		
		
		// on recupere l'index du cours
		i = 0;
		Boolean found = false;
		while (i < Bdd.listCours.size() && !found) {
			if (Bdd.listCours.get(i).getId() == idCours)
				found = true;
			else
				i++;
		}
		
		if (!found) 
			return false;

		// si l'id n'est pas utilise, on instancie un nouveau Cours
		Cours Cours = new Cours(cycle, matiere, enseignant, idCours, n);
		
		Bdd.listCours.set(i, Cours);
		
		return true;
	}

	public Boolean removeCours(int idCours) {
		// on recupere l'index du Cours
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listCours.size() && !found) {
			if (Bdd.listCours.get(i).getId() == idCours)
				found = true;
			else
				i++;
		}
		
		// si le Cours n'existe pas : echec
		if (!found)
			return false;
		
		// sinon on le supprime
		Bdd.listCours.remove(i);
		return true;
	}

	public String getCours() {
		String listCours = "";
		
		int i = 0;
		while (i < Bdd.listCours.size()) {
			listCours += Bdd.listCours.get(i) + "\n";
			i++;
		}
		
		return listCours;
	}

	public String getCours(int idCours) {
		// on recupere l'index du Cours
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listCours.size() && !found) {
			if (Bdd.listCours.get(i).getId() == idCours)
				found = true;
			else
				i++;
		}
		
		// si le cours n'existe pas : echec
		if (!found)
			return "";
		
		// sinon on le retourne
		return "" + Bdd.listCours.get(i);
	}

	public Boolean inscrireUserCours(int idUser, int idCours) {
		// on verifie que l'etudiant existe, sinon : echec
		Etudiant etudiant = null;
		int indexEtudiant = 0;
		while (indexEtudiant < Bdd.listEtudiants.size() && etudiant == null) {
			if (Bdd.listEtudiants.get(indexEtudiant).getId() == idUser)
				etudiant = Bdd.listEtudiants.get(indexEtudiant);
			else
				indexEtudiant++;
		}
		
		if (etudiant == null)
			return false;
		
		
		// on verifie que le cours existe sinon : echec
		Cours cours = null;
		int indexCours = 0;
		while (indexCours < Bdd.listCours.size() && cours == null) {
			if (Bdd.listCours.get(indexCours).getId() == idCours)
				cours = Bdd.listCours.get(indexCours);
			else
				indexCours++;
		}
		
		if (cours == null)
			return false;
		
		// on inscrit l'etudiant dans le cours
		Bdd.listCours.get(indexCours).getEtudiants().add(etudiant);
		// on inscrit le cours dans la liste des cours de l'etudiant
		Bdd.listEtudiants.get(indexEtudiant).getCours().add(cours);
		return true;
	}

	public Boolean desinscrireUserCours(int idUser, int idCours) {
		// on verifie que le cours existe sinon : echec
		Cours cours = null;
		int indexCours = 0;
		while (indexCours < Bdd.listCours.size() && cours == null) {
			if (Bdd.listCours.get(indexCours).getId() == idCours)
				cours = Bdd.listCours.get(indexCours);
			else
				indexCours++;
		}
		
		if (cours == null)
			return false;
		
		// on verifie que l'etudiant existe dans le cours, sinon : echec
		Etudiant etudiant = null;
		int indexEtudiantCours = 0;
		while (indexEtudiantCours < cours.getEtudiants().size() && etudiant == null) {
			if (cours.getEtudiants().get(indexEtudiantCours).getId() == idUser)
				etudiant = cours.getEtudiants().get(indexEtudiantCours);
			else
				indexEtudiantCours++;
		}
		
		if (etudiant == null)
			return false;
		
		// on recupere son index dans la BDD
		int indexEtudiantBdd = 0;
		Boolean found = false;
		while (indexEtudiantBdd < Bdd.listEtudiants.size() && !found) {
			if (Bdd.listEtudiants.get(indexEtudiantBdd).getId() == idUser)
				found = true;
			else
				indexEtudiantBdd++;
		}
		
		// ne doit jamais arriver : 
		// si l'etudiant est enregistrer dans le cours, il doit aussi l'etre en BDD
		if (!found)
			return false;
		
		// on desinscrit l'etudiant du cours
		Bdd.listCours.get(indexCours).getEtudiants().remove(indexEtudiantCours);
		// on desinscrit le cours dans la liste des cours de l'etudiant
		Bdd.listEtudiants.get(indexEtudiantBdd).getCours().add(cours);
		return true;
	}
	
}
