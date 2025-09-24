package controleurs;

import test.Bdd;
import utilisateurs.Administrateur;

public class ControleurAdmin {

	public Boolean addAdmin(int idAdmin, String n, String p) {
		// on verifie que l'id n'est pas deja utilise
		int i = 0;
		while (i < Bdd.listAdmins.size()) {
			if (Bdd.listAdmins.get(i).getId() == idAdmin)
				return false;
			else
				i++;
		}
		
		// si l'id n'est pas utilise, on instancie un nouveau admin
		Administrateur admin = new Administrateur(idAdmin, n, p);
		
		Bdd.listAdmins.add(admin);
		
		return true;
	}

	public Boolean setAdmin(int idAdmin, String n, String p) {
		// on recupere l'index du admin
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listAdmins.size() && !found) {
			if (Bdd.listAdmins.get(i).getId() == idAdmin)
				found = true;
			else
				i++;
		}
		
		// si le admin n'existe pas : echec
		if (!found)
			return false;
		
		
		// sinon on modifie le admin
		Bdd.listAdmins.set(i, new Administrateur(idAdmin, n, p));
				
		return true;
	}

	public Boolean removeAdmin(int idAdmin) {
		// on recupere l'index du admin
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listAdmins.size() && !found) {
			if (Bdd.listAdmins.get(i).getId() == idAdmin)
				found = true;
			else
				i++;
		}
		
		// si le admin n'existe pas : echec
		if (!found)
			return false;
		
		// sinon on le supprime
		Bdd.listAdmins.remove(i);
		return true;
	}

	public String getAdmin() {
		String listAdmins = "";
		
		int i = 0;
		while (i < Bdd.listAdmins.size()) {
			listAdmins += Bdd.listAdmins.get(i) + "\n";
			i++;
		}
		
		return listAdmins;
	}

	public String getAdmin(int idAdmin) {
		// on recupere l'index du admin
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listAdmins.size() && !found) {
			if (Bdd.listAdmins.get(i).getId() == idAdmin)
				found = true;
			else
				i++;
		}
		
		// si le admin n'existe pas : echec
		if (!found)
			return "";
		
		// sinon on le retourne
		return "" + Bdd.listAdmins.get(i);
	}

}
