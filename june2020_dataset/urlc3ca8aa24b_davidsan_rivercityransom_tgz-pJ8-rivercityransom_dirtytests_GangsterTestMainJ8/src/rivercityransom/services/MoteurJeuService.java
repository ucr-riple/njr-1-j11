package rivercityransom.services;

import rivercityransom.enumerations.Commande;
import rivercityransom.enumerations.Resultat;

public interface MoteurJeuService {

	/** Observateur : pas de jeu courant */
	int pasJeuCourant();

	/** Observateur : vrai si la partie est finie */
	boolean estFini();

	/** Observateur : retourne le résultat final */
	Resultat resulatFinal();

	/** Observateur : retourne le combat */
	GestionCombatService combat();

	/*
		[invariant]
			estFini(M) = 
					Personnage::estVaincu(GestionCombat::slick(combat(M)) 
					|| (Personnage::estVaincu(GestionCombat::ryan(combat(M))
						&& Personnage::estVaincu(GestionCombat::alex(combat(M))))
	
			resultatFinal(M) = RESULTAT.GAGNEE, si
					Personnage::estVaincu(GestionCombat::slick(combat(M)) 
					&& (!Personnage::estVaincu(GestionCombat::ryan(combat(M))) 
						|| !Personnage::estVaincu(GestionCombat::slick(combat(M))))
	
			resultatFinal(M) = RESULTAT.PERDUE, si				
					Personnage::estVaincu(GestionCombat::ryan(combat(M)) 
					&& Personnage::estVaincu(GestionCombat::alex(combat(M))
	
			resultatFinal(M) = RESULTAT.NULLE, sinon
	 */

	/**
	 * Initialisation 
	 * post: 
 			combat(init(x, y, z)) = GestionCombat::init(x, y, z)
	 */

	void init(int largeur, int hauteur, int profondeur);

	/**
	 * Effectue les actions a faire pendant un pas de jeu
	 * post:
		pasJeuCourant(pasDeJeu(M, cr, ca)) = pasJeuCourant(M) + 1;
		combat(pasJeu(M, cr, ca)) = GestionCombat::gerer(combat(M), cr, ca)
	 */
	void pasDeJeu(Commande cR, Commande cA);
}