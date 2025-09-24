package rivercityransom.contracts;

import rivercityransom.decorators.MoteurJeuDecorator;
import rivercityransom.enumerations.Commande;
import rivercityransom.enumerations.Resultat;
import rivercityransom.error.InvariantError;
import rivercityransom.error.PostconditionError;
import rivercityransom.error.PreconditionError;
import rivercityransom.services.MoteurJeuService;

public class MoteurJeuContract extends MoteurJeuDecorator {

	public MoteurJeuContract(MoteurJeuService delegate) {
		super(delegate);
	}

	protected void checkInvariant() {
		if (estFini()
				&& !((combat().alex().estVaincu() && combat().ryan()
						.estVaincu()) || combat().slick().estVaincu())) {
			throw new InvariantError("Wrong invariant");
		}
		if (resulatFinal() == Resultat.GAGNEE
				&& !(combat().slick().estVaincu() && (!combat().alex()
						.estVaincu() || !combat().ryan().estVaincu()))) {
			throw new InvariantError("Wrong invariant");
		}
		if (resulatFinal() == Resultat.PERDUE
				&& !(combat().alex().estVaincu() || combat().ryan().estVaincu())) {
			throw new InvariantError("Wrong invariant");
		}

	}

	@Override
	public void init(int largeur, int hauteur, int profondeur) {
		super.init(largeur, hauteur, profondeur);
		checkInvariant();

		if (!(combat().getTerrain().nbBlocLargeur() == largeur
				&& combat().getTerrain().nbBlocHauteur() == hauteur && combat()
				.getTerrain().nbBlocProfondeur() == profondeur)) {
			throw new PostconditionError("Error init combat");
		}

	}

	@Override
	public void pasDeJeu(Commande cR, Commande cA) {
		if (!(!estFini())) {
			throw new PreconditionError("Wrong precondition pasDeJeu");
		}
		int pasJeuCourant_atPre = pasJeuCourant();
		checkInvariant();
		super.pasDeJeu(cR, cA);
		checkInvariant();
		if (!(pasJeuCourant() == pasJeuCourant_atPre + 1)) {
			throw new PostconditionError("Wrong postcondtion pasDeJeu");
		}
	}

}