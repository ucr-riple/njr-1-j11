package rivercityransom.impl;

import rivercityransom.contracts.GestionCombatContract;
import rivercityransom.enumerations.Commande;
import rivercityransom.enumerations.Resultat;
import rivercityransom.services.GestionCombatService;
import rivercityransom.services.MoteurJeuService;

public class MoteurJeuImpl implements MoteurJeuService {

	private int pasJeuCourant;
	private GestionCombatService combat;

	@Override
	public int pasJeuCourant() {
		return pasJeuCourant;
	}

	@Override
	public boolean estFini() {
		return (combat().alex().estVaincu() && combat().ryan().estVaincu())
				|| combat().slick().estVaincu();
	}

	@Override
	public Resultat resulatFinal() {
		if (combat().slick().estVaincu()
				&& (!combat().alex().estVaincu() || !combat().ryan()
						.estVaincu())) {
			return Resultat.GAGNEE;
		}
		if (combat().alex().estVaincu() && combat().ryan().estVaincu()) {
			return Resultat.PERDUE;
		}
		return Resultat.NULLE;
	}

	@Override
	public GestionCombatService combat() {
		return combat;
	}

	@Override
	public void init(int largeur, int hauteur, int profondeur) {
		combat = new GestionCombatContract(new GestionCombatImpl());
		combat.init(largeur, hauteur, profondeur);
	}

	@Override
	public void pasDeJeu(Commande cR, Commande cA) {
		combat.gerer(cR, cA);
		pasJeuCourant++;
	}

}