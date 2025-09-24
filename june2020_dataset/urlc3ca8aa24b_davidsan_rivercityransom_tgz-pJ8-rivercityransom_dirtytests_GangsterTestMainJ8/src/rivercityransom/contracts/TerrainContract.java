package rivercityransom.contracts;

import rivercityransom.decorators.TerrainDecorator;
import rivercityransom.enumerations.Type;
import rivercityransom.error.PostconditionError;
import rivercityransom.error.PreconditionError;
import rivercityransom.services.BlocService;
import rivercityransom.services.TerrainService;

public class TerrainContract extends TerrainDecorator {

	public TerrainContract(TerrainService delegate) {
		super(delegate);
	}

	protected void checkInvariant() {
		// pas d'invariant
	}

	@Override
	public void init(int largeur, int hauteur, int profondeur) {
		if ((!(largeur > 0) || !(hauteur > 0) || !(profondeur > 0))) {
			throw new PreconditionError("Wrong init value");
		}

		super.init(largeur, hauteur, profondeur);
		checkInvariant();

		if (!(nbBlocLargeur() == largeur)) {
			throw new PostconditionError("Error largeur");
		}
		if (!(nbBlocHauteur() == hauteur)) {
			throw new PostconditionError("Error hauteur");
		}
		if (!(nbBlocProfondeur() == profondeur)) {
			throw new PostconditionError("Error profondeur");
		}

		// TODO Des cases initialisés avec des fossés ou des trésors
		// Attention : dans ce cas là, cette post condition n'a plus de sens !
		for (int x = 0; x < nbBlocLargeur(); x++) {
			for (int y = 0; y < nbBlocHauteur(); y++) {
				for (int z = 0; z < nbBlocProfondeur(); z++) {
					if (!(super.getBloc(x, y, z).getType() == Type.VIDE)) {
						throw new PostconditionError("Error bloc type");
					}
					if (!(super.getBloc(x, y, z).getObjet() == null)) {
						throw new PostconditionError("Error bloc tresor");
					}
				}
			}
		}

	}

	@Override
	public void setBloc(int x, int y, int z, BlocService b) {
		if (!((x >= 0) && (x < nbBlocLargeur()))) {
			throw new PreconditionError("setBloc");
		}
		if (!((y >= 0) && (y < nbBlocHauteur()))) {
			throw new PreconditionError("setBloc");
		}
		if (!((z >= 0) && (z < nbBlocProfondeur()))) {
			throw new PreconditionError("setBloc");
		}

		checkInvariant();
		super.setBloc(x, y, z, b);
		checkInvariant();
		if (!(super.getBloc(x, y, z).getObjet() == b.getObjet())) {
			throw new PostconditionError("setBloc");
		}
		if (!(super.getBloc(x, y, z).getType() == b.getType())) {
			throw new PostconditionError("setBloc");
		}
	}

}