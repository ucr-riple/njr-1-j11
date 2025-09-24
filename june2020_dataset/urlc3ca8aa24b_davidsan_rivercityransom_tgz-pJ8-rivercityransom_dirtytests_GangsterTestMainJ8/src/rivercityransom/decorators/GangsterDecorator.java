package rivercityransom.decorators;

import rivercityransom.services.BlocService;
import rivercityransom.services.ObjetService;
import rivercityransom.services.GangsterService;

public class GangsterDecorator implements GangsterService {
	private GangsterService delegate;

	public GangsterDecorator(GangsterService delegate) {
		super();
		this.delegate = delegate;
	}

	public void init(String nom, int largeur, int hauteur, int profondeur,
			int force, int pointsDeVie, int x, int y, int z, ObjetService obj) {
		delegate.init(nom, largeur, hauteur, profondeur, force, pointsDeVie, x,
				y, z, obj);
	}

	public String nom() {
		return delegate.nom();
	}

	public int largeur() {
		return delegate.largeur();
	}

	public int hauteur() {
		return delegate.hauteur();
	}

	public int profondeur() {
		return delegate.profondeur();
	}

	public int force() {
		return delegate.force();
	}

	public int pointsDeVie() {
		return delegate.pointsDeVie();
	}

	public boolean estVaincu() {
		return delegate.estVaincu();
	}

	public boolean estEquipe() {
		return delegate.estEquipe();
	}

	public ObjetService laChoseEquipee() {
		return delegate.laChoseEquipee();
	}

	@Override
	public int getX() {
		return delegate.getX();
	}

	@Override
	public int getY() {
		return delegate.getY();
	}

	@Override
	public int getZ() {
		return delegate.getZ();
	}

	public void depot(int pointsDeVie) {
		delegate.depot(pointsDeVie);
	}

	public void retrait(int pointsDeVie) {
		delegate.retrait(pointsDeVie);
	}

	public void jeter(BlocService b) {
		delegate.jeter(b);
	}

	@Override
	public void setX(int x) {
		delegate.setX(x);
	}

	@Override
	public void setY(int y) {
		delegate.setY(y);
	}

	@Override
	public void setZ(int z) {
		delegate.setZ(z);
	}

}