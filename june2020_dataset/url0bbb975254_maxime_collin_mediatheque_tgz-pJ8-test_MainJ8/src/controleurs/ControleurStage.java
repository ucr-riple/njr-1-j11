package controleurs;

import java.util.Date;

import scolarite.Projet;
import scolarite.Stage;
import test.Bdd;

public class ControleurStage {

	public Boolean addStage(int idProjet, int idStage, String n, Date dd, Date df) {
		// on recherche le projet
		Projet projet = null;
		int indexProjet = 0;
		while (indexProjet < Bdd.listProjets.size() && projet == null) {
			if (Bdd.listProjets.get(indexProjet).getId() == idProjet)
				projet = Bdd.listProjets.get(indexProjet);
			else 
				indexProjet++;
		}
		// s'il n'existe pas : echec
		if (projet == null)
			return false;
				
		// si le projet existe
		// on verifie que l'id n'est pas deja utilise
		int i = 0;
		while (i < Bdd.listStages.size()) {
			if (Bdd.listStages.get(i).getId() == idStage)
				return false;
			else
				i++;
		}
		
		
		// si l'id n'est pas utilise, on instancie un nouveau stage
		Stage stage = new Stage(projet, idStage, n, dd, df);
		
		Bdd.listStages.add(stage);
		Bdd.listProjets.get(indexProjet).setStage(stage);
		return true;
	}

	public Boolean setStage(int idProjet, int idStage, String n, Date dd, Date df) {
		// on recherche le projet
		Projet projet = null;
		int i = 0;
		while (i < Bdd.listProjets.size() && projet == null) {
			if (Bdd.listProjets.get(i).getId() == idProjet)
				projet = Bdd.listProjets.get(i);
			else 
				i++;
		}
		// s'il n'existe pas : echec
		if (projet == null)
			return false;
				
		// si le projet existe
		// on recupere l'index du stage
		i = 0;
		Boolean found = false;
		while (i < Bdd.listStages.size() && !found) {
			if (Bdd.listStages.get(i).getId() == idStage)
				found = true;
			else
				i++;
		}
		
		if (!found)
			return true;
		
		// si l'id n'est pas utilise, on instancie un nouveau stage
		Stage stage = new Stage(projet, idStage, n, dd, df);
		
		Bdd.listStages.set(i, stage);
		
		return true;
	}

	public Boolean removeStage(int idStage) {
		// on recupere l'index du stage
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listStages.size() && !found) {
			if (Bdd.listStages.get(i).getId() == idStage)
				found = true;
			else
				i++;
		}
		
		// si le stage n'existe pas : echec
		if (!found)
			return false;
		
		// sinon on le supprime
		Bdd.listStages.remove(i);
		return true;
	}

	public String getStage() {
		String listStages = "";
		
		int i = 0;
		while (i < Bdd.listStages.size()) {
			listStages += Bdd.listStages.get(i) + "\n";
			i++;
		}
		
		return listStages;
	}

	public String getStage(int idStage) {
		// on recupere l'index du stage
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listStages.size() && !found) {
			if (Bdd.listStages.get(i).getId() == idStage)
				found = true;
			else
				i++;
		}
		
		// si le stage n'existe pas : echec
		if (!found)
			return "";
		
		return "" + Bdd.listStages.get(i);
	}
	
}
