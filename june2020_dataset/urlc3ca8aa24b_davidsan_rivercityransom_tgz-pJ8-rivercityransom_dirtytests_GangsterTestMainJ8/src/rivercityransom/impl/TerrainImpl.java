package rivercityransom.impl;

import rivercityransom.contracts.BlocContract;
import rivercityransom.enumerations.Type;
import rivercityransom.services.BlocService;
import rivercityransom.services.TerrainService;

public class TerrainImpl implements TerrainService {
	private int largeur;
	private int hauteur;
	private int profondeur;
	private BlocService[][][] plateau;

	@Override
	public int nbBlocLargeur() {
		return largeur;
	}

	@Override
	public int nbBlocHauteur() {
		return hauteur;
	}

	@Override
	public int nbBlocProfondeur() {
		return profondeur;
	}

	@Override
	public void init(int largeur, int hauteur, int profondeur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.profondeur = profondeur;
		this.plateau = new BlocService[largeur][hauteur][profondeur];
		for (int x = 0; x < largeur; x++) {
			for (int y = 0; y < hauteur; y++) {
				for (int z = 0; z < profondeur; z++) {
					BlocService b = new BlocContract(new BlocImpl());
					// TODO Des cases initialisés avec des fossés ou des trésors
					// Utiliser le RandomSingleton pour cela
					b.init(Type.VIDE, null);
					plateau[x][y][z] = b;
				}
			}
		}
	}

	@Override
	public BlocService getBloc(int x, int y, int z) {
		return plateau[x][y][z];
	}

	@Override
	public void setBloc(int x, int y, int z, BlocService b) {
		plateau[x][y][z] = b;
	}

}