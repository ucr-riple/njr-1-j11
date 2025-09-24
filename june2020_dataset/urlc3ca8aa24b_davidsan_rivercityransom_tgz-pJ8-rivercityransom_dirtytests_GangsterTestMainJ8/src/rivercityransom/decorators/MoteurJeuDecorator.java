package rivercityransom.decorators;

import rivercityransom.enumerations.Commande;
import rivercityransom.enumerations.Resultat;
import rivercityransom.services.GestionCombatService;
import rivercityransom.services.MoteurJeuService;

public class MoteurJeuDecorator implements MoteurJeuService {
	private MoteurJeuService delegate;

	public MoteurJeuDecorator(MoteurJeuService delegate) {
		super();
		this.delegate = delegate;
	}

	public int pasJeuCourant() {
		return delegate.pasJeuCourant();
	}

	public boolean estFini() {
		return delegate.estFini();
	}

	public Resultat resulatFinal() {
		return delegate.resulatFinal();
	}

	public GestionCombatService combat() {
		return delegate.combat();
	}

	public void init(int largeur, int hauteur, int profondeur) {
		delegate.init(largeur, hauteur, profondeur);
	}

	public void pasDeJeu(Commande cR, Commande cA) {
		delegate.pasDeJeu(cR, cA);
	}

}