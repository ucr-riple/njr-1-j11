package controleurs;

import scolarite.Cycle;
import test.Bdd;

public class ControleurCycle {

	public Boolean addCycle(int idCycle, String n) {
		// on verifie que l'id n'est pas deja utilise
		int i = 0;
		while (i < Bdd.listCycles.size()) {
			if (Bdd.listCycles.get(i).getId() == idCycle)
				return false;
			else
				i++;
		}
		
		// si l'id n'est pas utilise, on instancie un nouveau cycle
		Cycle cycle = new Cycle(idCycle, n);
		
		Bdd.listCycles.add(cycle);
		
		return true;
	}

	public Boolean setCycle(int idCycle, String n) {
		// on recupere l'index du cycle
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listCycles.size() && !found) {
			if (Bdd.listCycles.get(i).getId() == idCycle)
				found = true;
			else
				i++;
		}
		
		// si le cycle n'existe pas : echec
		if (!found)
			return false;
		
		
		// sinon on modifie le cycle
		Bdd.listCycles.set(i, new Cycle(idCycle, n));
				
		return true;
	}

	public Boolean removeCycle(int idCycle) {
		// on recupere l'index du cycle
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listCycles.size() && !found) {
			if (Bdd.listCycles.get(i).getId() == idCycle)
				found = true;
			else
				i++;
		}
		
		// si le cycle n'existe pas : echec
		if (!found)
			return false;
		
		// sinon on le supprime
		Bdd.listCycles.remove(i);
		return true;
	}

	public String getCycle() {
		String listCycles = "";
		
		int i = 0;
		while (i < Bdd.listCycles.size()) {
			listCycles += Bdd.listCycles.get(i) + "\n";
			i++;
		}
		
		return listCycles;
	}

	public String getCycle(int idCycle) {
		// on recupere l'index du cycle
		int i = 0;
		Boolean found = false;
		while (i < Bdd.listCycles.size() && !found) {
			if (Bdd.listCycles.get(i).getId() == idCycle)
				found = true;
			else
				i++;
		}
		
		// si le cycle n'existe pas : echec
		if (!found)
			return "";
		
		return "" + Bdd.listCycles.get(i);
	}

}
