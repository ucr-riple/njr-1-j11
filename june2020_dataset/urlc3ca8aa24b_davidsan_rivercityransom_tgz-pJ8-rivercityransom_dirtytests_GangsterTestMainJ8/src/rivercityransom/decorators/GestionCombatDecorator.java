package rivercityransom.decorators;

import java.util.List;

import rivercityransom.enumerations.Commande;
import rivercityransom.services.GangsterService;
import rivercityransom.services.GestionCombatService;
import rivercityransom.services.PersonnageService;
import rivercityransom.services.TerrainService;

public class GestionCombatDecorator implements GestionCombatService {
	public GestionCombatService delegate;

	public GestionCombatDecorator(GestionCombatService delegate) {
		super();
		this.delegate = delegate;
	}

	public TerrainService getTerrain() {
		return delegate.getTerrain();
	}

	public PersonnageService ryan() {
		return delegate.ryan();
	}

	public PersonnageService alex() {
		return delegate.alex();
	}

	public GangsterService slick() {
		return delegate.slick();
	}

	public List<GangsterService> gangster() {
		return delegate.gangster();
	}

	public boolean estFrappe(String nomPersonnage) {
		return delegate.estFrappe(nomPersonnage);
	}

	public boolean estGele(String nomPersonnage) {
		return delegate.estGele(nomPersonnage);
	}

	public List<GangsterService> collisionPerso(PersonnageService p) {
		return delegate.collisionPerso(p);
	}

	public List<PersonnageService> collisionGangster(GangsterService g) {
		return delegate.collisionGangster(g);
	}

	public void init(int largeurTerrain, int hauteurTerrain,
			int profondeurTerrain) {
		delegate.init(largeurTerrain, hauteurTerrain, profondeurTerrain);
	}

	public void gerer(Commande commandRyan, Commande commandAlex) {
		delegate.gerer(commandRyan, commandAlex);
	}

}