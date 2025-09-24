package rivercityransom.services;

public interface TerrainService {

	/** Observateur : nombre de blocs en largeur */
	int nbBlocLargeur();
	
	/** Observateur : nombre de blocs en hauteur */
	int nbBlocHauteur();
	
	/** Observateur : nombre de blocs en profondeur */
	int nbBlocProfondeur();
	
	/** Observateur : Obtenir le bloc de coordonnées (x,y,z) */
	BlocService getBloc(int x, int y, int z);

	/**
	 * Initialisation
	 * pre:
				largeur > 0 
				&& hauteur > 0 
				&& profondeur > 0  
	 * post:
			nbBlocLargeur(init(l, h, p)) = l 
			nbBlocHauteur(init(l, h, p)) = h 
			nbBlocProfondeur(init(l, h, p)) = p
			getBloc(x, y, z) <> null
			Bloc::getType(getBloc(init(l, h, p), x, y, z)) = Bloc::TYPE.VIDE 
			Bloc::getTresor(getBloc(init(l, h, p), x, y, z)) = null
	 */
	void init(int largeur, int hauteur, int profondeur);


	/**
	 * Setter pour un bloc du terrain
	 * pre:
	 			0 <= x < nbBlocLargeur(T) 
				&& 0 <= y < nbBlocHauteur(T) 
				&& 0 <= z < nbBlocProfondeur(T)
	 * post:
	 		Bloc::getType(setBloc(init(l, h, p), x, y, z, b)) = Bloc::getType(b)
			Bloc::getTresor(setBloc(init(l, h, p), x, y, z, b)) = Bloc::getTresor(b)

	 */
	void setBloc(int x, int y, int z, BlocService b);


}