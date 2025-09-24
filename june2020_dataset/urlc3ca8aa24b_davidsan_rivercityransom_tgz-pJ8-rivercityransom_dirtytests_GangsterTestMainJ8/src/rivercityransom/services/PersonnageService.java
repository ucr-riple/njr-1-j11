package rivercityransom.services;

public interface PersonnageService {

	/** Observateur : nom du personnage */
	String nom();

	/** Observateur : largeur du personnage */
	int largeur();

	/** Observateur : hauteur du personnage */
	int hauteur();

	/** Observateur : profondeur du personnage */
	int profondeur();

	/** Observateur : force du personnage */
	int force();

	/** Observateur : points de vie restants du personnage */
	int pointsDeVie();

	/** Observateur : somme d'argent amassé par le personnage */
	int sommeArgent();
	
	/** Observateur : x, position x du Personnage sur le terrain */
	int getX();
	
	/** Observateur : y, position y du Personnage sur le terrain */
	int getY();
	
	/** Observateur : z, position z du Personnage sur le terrain */
	int getZ();	
	
	/** Observateur : vrai si vaincu */
	boolean estVaincu();

	/** Observateur : vrai si équipé */
	boolean estEquipe();

	/** Observateur : l'objet équipé */
	ObjetService laChoseEquipee();

	/**
	 * 	[invariants]
	 *		estVaincu(P) = pointsDeVie(P) <= 0 
	 *		sommeArgent(P) >= 0
	 *		estEquipe(P) = Objet::isEquipable(laChoseEquipee(P))
	 *
	 */
		
	/**
	 * Initialisation 
	 * pre:
		nom <> "" 
		&& largeur > 0 
		&& hauteur > 0 
		&& profondeur > 0 
		&& 0 < force < vie 
	 * post:
		nom(init(n,l,h,p,f,pv,x,y,z)) = n 
		largeur(init(n,l,h,p,f,pv,x,y,z)) = l 
		hauteur(init(n,l,h,p,f,pv,x,y,z)) = h
		profondeur(init(n,l,h,p,f,pv,x,y,z)) = p 
		force(init(n,l,h,p,f,pv,x,y,z)) = f 
		pointsDeVie(init(n,l,h,p,f,pv,x,y,z)) = pv
		laChoseEquipee(init(n,l,h,p,f,pv,x,y,z)) = null
		sommeArgent(init(n,l,h,p,f,pv,x,y,z)) = 0
		getX(init(n,l,h,p,f,pv,x,y,z)) = x
		getY(init(n,l,h,p,f,pv,x,y,z)) = y
		getZ(init(n,l,h,p,f,pv,x,y,z)) = z
	 */
	void init(String nom, int largeur, int hauteur, int profondeur, int force,
			int pointsDeVie, int x, int y, int z);

	/**
	 * Dépot de points de vie
	 * post:
	 * 		pointsDeVie(depot(P,s)) = pointsDeVie(P) + s
	 */
	void depot(int pointsDeVie);

	/**
	 * Retrait de points de vie
	 * post:
	 * 		pointsDeVie(retrait(P,s)) = pointsDeVie(P) - s
	 */
	void retrait(int pointsDeVie);

	/**
	 * Dépot d'argent
	 * post:
	 * 		sommeArgent(depotArgent(P,s)) = sommeArgent(P) + s
	 */
	void depotArgent(int somme);

	/**
	 * Retrait d'argent
	 * post:
	 * 		sommeArgent(retraitArgent(P,s)) = sommeArgent(P) - s
	 */
	void retraitArgent(int somme);

	/**
	 * Ramasser un objet
	 * post:
	 *  	laChoseEquipee(ramasser(P,b)) = Bloc::getTresor(b)
	 *  	Bloc::getTresor(ramasser(P,b)) = null
	 */
	void ramasser(BlocService b);

	/**
	 * Jeter l'objet équipé
	 * post:
	 *		laChoseEquipee(jeter(P)) = null
	 * 		estEquipee(jeter(P)) = false
	 *		Bloc::getTresor(jeter(P,b)) = laChoseEquipee(P)
	 */
	void jeter(BlocService b);

	/**
	 * Setter pour x
	 * post:
	 * 		getX(setX(P,x)) = x
	 */
	void setX(int x);

	/**
	 * Setter pour y
	 * post:
	 * 		getY(setY(P,y)) = y
	 */
	void setY(int y);

	/**
	 * Setter pour z
	 * post:
	 *  	getZ(setZ(P,z)) = z
	 */
	void setZ(int z);

}