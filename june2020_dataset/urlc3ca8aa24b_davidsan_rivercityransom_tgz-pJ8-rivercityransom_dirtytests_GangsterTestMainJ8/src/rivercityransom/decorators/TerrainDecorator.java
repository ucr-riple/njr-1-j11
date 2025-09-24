package rivercityransom.decorators;

import rivercityransom.services.BlocService;
import rivercityransom.services.TerrainService;

public class TerrainDecorator implements TerrainService {
	private TerrainService delegate;

	public TerrainDecorator(TerrainService delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public int nbBlocLargeur() {
		return delegate.nbBlocLargeur();
	}

	@Override
	public int nbBlocHauteur() {
		return delegate.nbBlocHauteur();
	}

	@Override
	public int nbBlocProfondeur() {
		return delegate.nbBlocProfondeur();
	}

	@Override
	public void init(int largeur, int hauteur, int profondeur) {
		delegate.init(largeur, hauteur, profondeur);
	}

	@Override
	public BlocService getBloc(int x, int y, int z) {
		return delegate.getBloc(x, y, z);
	}

	@Override
	public void setBloc(int x, int y, int z, BlocService b) {
		delegate.setBloc(x, y, z, b);
	}

}