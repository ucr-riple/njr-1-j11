package controleurs;

import scolarite.Cours;
import test.Bdd;
import utilisateurs.Utilisateur;

public class ControleurUser {
	
	ControleurEtudiant controleurEtudiant = new ControleurEtudiant();
	ControleurEnseignant controleurEnseignant = new ControleurEnseignant();

	public Boolean addUser(int idUser, String n, String p, String t, int nbEM) {
		if (t == "Etudiant")
			return controleurEtudiant.addEtudiant(idUser, n, p, t, nbEM);
		
		if (t == "Enseignant")
			return controleurEnseignant.addEnseignant(idUser, n, p, t, nbEM);
		
		// si le type n'est ni Etudiant, ni Enseignant : echec
		return false;
	}

	public Boolean setUser(int idUser, String n, String p, String t, int nbEM) {
		if (t == "Etudiant")
			return controleurEtudiant.setEtudiant(idUser, n, p, t, nbEM);
		
		if (t == "Enseignant")
			return controleurEnseignant.setEnseignant(idUser, n, p, t, nbEM);
		
		// si le type n'est ni Etudiant, ni Enseignant : echec
		return false;
	}

	public Boolean removeUser(int idUser) {
		// on cherche dans la liste des Etudiants si idUser existe
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listEtudiants.size() && !found) {
			if (Bdd.listEtudiants.get(i).getId() == idUser)
				found = true;
			else
				i++;
		}
		
		// si oui, on supprime l'etudiant
		if (found) {
			desinscrireUserAllCours(Bdd.listEtudiants.get(i));
			Bdd.listEtudiants.remove(i);
			return true;

		// sinon on cherche dans la liste des enseignants si idUser existe 
		} else {
			i = 0;
			while (i < Bdd.listEnseignants.size() && !found) {
				if (Bdd.listEnseignants.get(i).getId() == idUser)
					found = true;
				else
					i++;
			}
			
			// si oui on supprime l'enseignant
			if (found) {
				System.out.println(Bdd.listEnseignants.get(i));
				desinscrireUserAllCours(Bdd.listEnseignants.get(i));
				Bdd.listEnseignants.remove(i);
				return true;
			}

			// sinon : echec
			else
				return false;
		}
	}

	private void desinscrireUserAllCours(Utilisateur user) {
		// pour desinscrire un utilisateur de tous ses cours
		// il faut recuperer chacun de ses cours
		// le supprimer dans le cours
		// puis supprimer tous ses cours
		
		// pour chacun de ses cours
		int indexCoursDeUser = 0;
		while (indexCoursDeUser < user.getCours().size()) {
			// on recupere le cours
			Cours cours = user.getCours().get(indexCoursDeUser);
			
			if (user.getType() == "Etudiant")
				desinscrireEtudiantCours(user, cours);
			else
				desinscrireEnseignantCours(user, cours);
		}
		
		// on supprime tous ses cours
		user.getCours().clear();
		
	}

	private void desinscrireEnseignantCours(Utilisateur user, Cours cours) {
		cours.setProf(null);		
	}

	private void desinscrireEtudiantCours(Utilisateur user, Cours cours) {
		// on cherche l'index de l'utilisateur dans le cours
		int indexUserDuCours = 0;
		Boolean found = false;
		while (indexUserDuCours < cours.getEtudiants().size() && !found) {
			if (cours.getEtudiants().get(indexUserDuCours).getId() == user.getId())
				found = true;
			else
				indexUserDuCours++;
		}
		
		// si on le trouve, on le supprime du cours
		if (found)
			cours.getEtudiants().remove(indexUserDuCours);
	}

	public String getUser() {
		String listUsers = "";
		
		int i = 0;
		while (i < Bdd.listEnseignants.size()) {
			listUsers += Bdd.listEnseignants.get(i) + "\n";
			i++;
		}
		
		i = 0;
		while (i < Bdd.listEtudiants.size()) {
			listUsers += Bdd.listEtudiants.get(i) + "\n";
			i++;
		}
		
		return listUsers;
	}

	public String getUser(int idUser) {
		return "" + getUtilisateur(idUser);
	}
	
	public Utilisateur getUtilisateur(int idUtilisateur) {
		// on cherche dans la liste des Etudiants si idUser existe
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listEtudiants.size() && !found) {
			if (Bdd.listEtudiants.get(i).getId() == idUtilisateur)
				found = true;
			else
				i++;
		}
		
		// si oui, on renvoi l'etudiant
		if (found)
			return Bdd.listEtudiants.get(i);

		// sinon on cherche dans la liste des enseignants si idUser existe 
		else {
			i = 0;
			while (i < Bdd.listEnseignants.size() && !found) {
				if (Bdd.listEnseignants.get(i).getId() == idUtilisateur)
					found = true;
				else
					i++;
			}
			
			// si oui on renvoi l'enseignant
			if (found == true)
				return Bdd.listEnseignants.get(i);

			// sinon : echec
			else
				return null;
		}
	}
}
