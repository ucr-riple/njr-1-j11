package rivercityransom.services;

public interface GangsterService {
	
	/** Observateur : nom du Gangster */
	String nom();

	/** Observateur : largeur du Gangster */
	int largeur();

	/** Observateur : hauteur du Gangster */
	int hauteur();

	/** Observateur : profondeur du Gangster */
	int profondeur();

	/** Observateur : force du Gangster */
	int force();

	/** Observateur : points de vie restants du Gangster */
	int pointsDeVie();
	
	/** Observateur : vrai si vaincu */
	boolean estVaincu();

	/** Observateur : vrai si équipé */
	boolean estEquipe();
	
	/** Observateur : chose que le gangster a (et jetera à sa mort) */
	ObjetService laChoseEquipee();
	
	/** Observateur : x, position x du Gangster sur le terrain */
	int getX();
	
	/** Observateur : y, position y du Gangster sur le terrain */
	int getY();
	
	/** Observateur : z, position z du Gangster sur le terrain */
	int getZ();


	/**
	 * 
	 * 	[invariants]
			estVaincu(G) = pointsDeVie(G) <= 0 
			estEquipe(G) = Objet::isEquipable(laChoseEquipee(G))
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
	  	nom(init(n,l,h,p,f,pv,x,y,z,o)) = n 
		largeur(init(n,l,h,p,f,pv,x,y,z,o)) = l 
		hauteur(init(n,l,h,p,f,pv,x,y,z,o)) = h
		profondeur(init(n,l,h,p,f,pv,x,y,z,o)) = p 
		force(init(n,l,h,p,f,pv,x,y,z,o)) = f 
		pointsDeVie(init(n,l,h,p,f,pv,x,y,z,o)) = pv
		laChoseEquipee(init(n,l,h,p,f,pv,x,y,z,o)) = o
		getX(init(n,l,h,p,f,pv,x,y,z,o)) = x
		getY(init(n,l,h,p,f,pv,x,y,z,o)) = y
		getZ(init(n,l,h,p,f,pv,x,y,z,o)) = z
	 */
	void init(String nom, int largeur, int hauteur, int profondeur, int force,
			int pointsDeVie, int x, int y, int z, ObjetService obj);

	/**
	 * Dépot de points de vie
	 * post:
	 * 		pointsDeVie(depot(G,s)) = pointsDeVie(G) + s
	 */
	void depot(int pointsDeVie);

	/**
	 * Retrait de points de vie
	 * post:
	 * 		pointsDeVie(depot(G,s)) = pointsDeVie(G) - s
	 */
	void retrait(int pointsDeVie);

	/**
	 * Jeter l'objet équipé
	 * post:
	 * 		laChoseEquipee(jeter(G,b)) = null
	 *		Bloc::getTresor(jeter(G,b)) = laChoseEquipee(G)
	 */
	void jeter(BlocService b);
	
	/**
	 * Setter pour x
	 * post:
	 * 		getX(setX(G,x)) = x
	 */
	void setX(int x);

	/**
	 * Setter pour y
	 * post:
	 * 		getY(setY(G,y)) = y
	 */
	void setY(int y);

	/**
	 * Setter pour z
	 * post:
	 * 		getZ(setZ(G,z)) = z
	 */
	void setZ(int z);

}