package rivercityransom.services;

import rivercityransom.enumerations.Tresor;

public interface ObjetService {

	/** Observateur : vrai si objet équipable */
	boolean isEquipable();

	/** Observateur : vrai si objet à valeur marchande ? */
	boolean isValeurMarchande();

	/** Observateur : rend le trésor */
	Tresor getTresor();

	// inv: isEquipable(O) = (getTresor(O) == CHAINEDEVELO) || (getTresor(O) ==
	// POUBELLEMETALLIQUE)
	// inv: isValeurMarchande(O) = (getTresor(O) == UNDOLLAR) || (getTresor(O)
	// == CINQUANTECENTIMES)

	/**
	 * Initialisation 
	 * post: getTresor() = tresor
	 */
	void init(Tresor tresor);

}
