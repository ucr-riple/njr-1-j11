package rivercityransom.contracts;

import rivercityransom.decorators.PersonnageDecorator;
import rivercityransom.error.InvariantError;
import rivercityransom.error.PostconditionError;
import rivercityransom.error.PreconditionError;
import rivercityransom.services.BlocService;
import rivercityransom.services.ObjetService;
import rivercityransom.services.PersonnageService;

import com.google.gson.Gson;

public class PersonnageContract extends PersonnageDecorator {

	public PersonnageContract(PersonnageService delegate) {
		super(delegate);

	}

	protected void checkInvariant() {
		if (estVaincu() && pointsDeVie() > 0) {
			throw new InvariantError("Wrong invariant");
		}
		if (sommeArgent() < 0) {
			throw new InvariantError("Wrong invariant");
		}
		if (estEquipe() && !laChoseEquipee().isEquipable()) {
			throw new InvariantError("Wrong invariant");
		}
	}

	@Override
	public void init(String nom, int largeur, int hauteur, int profondeur,
			int force, int pointsDeVie, int x, int y, int z) {
		if ((nom.isEmpty() || !(0 < force) || !(force < pointsDeVie))) {
			throw new PreconditionError("Wrong init value");
		}

		if (largeur < 0 || hauteur < 0 || profondeur < 0) {
			throw new PreconditionError("Wrong init value");
		}

		super.init(nom, largeur, hauteur, profondeur, force, pointsDeVie, x, y,
				z);
		checkInvariant();

		if (!(nom().compareTo(nom) == 0)) {
			throw new PostconditionError("Error nom");
		}

		if (!(largeur() == largeur)) {
			throw new PostconditionError("Error largeur");
		}
		if (!(hauteur() == hauteur)) {
			throw new PostconditionError("Error hauteur");
		}
		if (!(profondeur() == profondeur)) {
			throw new PostconditionError("Error profondeur");
		}
		if (!(force() == force)) {
			throw new PostconditionError("Error force");
		}
		if (!(sommeArgent() == 0)) {
			throw new PostconditionError("Error sommeArgent");
		}
		if (!(pointsDeVie() == pointsDeVie)) {
			throw new PostconditionError("Error pointDeVie");
		}
		if (!(laChoseEquipee() == null)) {
			throw new PostconditionError("Error laChoseEquipee");
		}
		if (!(getX() == x)) {
			throw new PostconditionError("Error X");
		}
		if (!(getY() == y)) {
			throw new PostconditionError("Error Y");
		}
		if (!(getZ() == z)) {
			throw new PostconditionError("Error Z");
		}

	}

	@Override
	public void retrait(int pointsDeVie) {

		if (!(!estVaincu() && pointsDeVie > 0)) {
			throw new PreconditionError("Wrong precondition retrait");
		}
		checkInvariant();

		int pointsDeVie_atPre = pointsDeVie();
		super.retrait(pointsDeVie);
		checkInvariant();

		if (!(pointsDeVie_atPre - pointsDeVie == pointsDeVie())) {
			throw new PostconditionError("Wrong postcondition retrait");
		}

	}

	@Override
	public void depot(int pointsDeVie) {

		if (!(!estVaincu() && pointsDeVie > 0)) {
			throw new PreconditionError("Wrong precondition depot");
		}
		checkInvariant();

		int pointsDeVie_atPre = pointsDeVie();
		super.depot(pointsDeVie);
		checkInvariant();

		if (!(pointsDeVie_atPre + pointsDeVie == pointsDeVie())) {
			throw new PostconditionError("Wrong postcondition depot");
		}

	}

	@Override
	public void depotArgent(int somme) {

		if (!(!estVaincu() && somme > 0)) {
			throw new PreconditionError("Wrong precondition depotArgent");
		}
		checkInvariant();
		int sommeArgent_atPre = sommeArgent();
		super.depotArgent(somme);
		checkInvariant();
		if (!(sommeArgent_atPre + somme == sommeArgent())) {
			throw new PostconditionError("Wrong postcondition depotArgent");
		}

	}

	@Override
	public void retraitArgent(int somme) {
		if (!(!estVaincu() && sommeArgent() >= somme && somme > 0)) {
			throw new PreconditionError("Wrong precondition retraitArgent");
		}
		checkInvariant();
		int sommeArgent_atPre = sommeArgent();
		super.retraitArgent(somme);
		checkInvariant();

		if (!(sommeArgent_atPre - somme == sommeArgent())) {
			throw new PostconditionError("Wrong postcondition retraitArgent");
		}
	}

	@Override
	public void ramasser(BlocService b) {
		if (!(!estVaincu() && !estEquipe() && b.getObjet().isEquipable())) {
			throw new PreconditionError("Wrong precondition ramasser");
		}
		checkInvariant();

		ObjetService o = b.getObjet();
		super.ramasser(b);
		checkInvariant();

		if (!(laChoseEquipee() == o && b.getObjet() == null)) {
			throw new PostconditionError("Wrong postcondition ramasser");
		}

	}

	@Override
	public void jeter(BlocService b) {
		if (!(estEquipe())) {
			throw new PreconditionError("Wrong precondition jeter");
		}
		checkInvariant();
		ObjetService o = laChoseEquipee();
		super.jeter(b);
		checkInvariant();
		if (!(laChoseEquipee() == null)) {
			throw new PostconditionError("Wrong postcondition jeter");
		}
		if (!estEquipe() == false) {
			throw new PostconditionError("Wrong postcondition jeter");
		}
		if (!(b.getObjet() == o)) {
			throw new PostconditionError("Wrong postcondition jeter");
		}
	}

	@Override
	public void setX(int x) {
		checkInvariant();
		super.setX(x);
		checkInvariant();
		if (!(getX() == x)) {
			throw new PostconditionError("Wrong postcondition setX");
		}
	}

	@Override
	public void setY(int y) {
		checkInvariant();
		super.setY(y);
		checkInvariant();
		if (!(getY() == y)) {
			throw new PostconditionError("Wrong postcondition setY");
		}
	}

	@Override
	public void setZ(int z) {
		checkInvariant();
		super.setZ(z);
		checkInvariant();
		if (!(getZ() == z)) {
			throw new PostconditionError("Wrong postcondition setZ");
		}
	}

	@Override
	public String toString() {
		Gson g = new Gson();
		return g.toJson(this);
	}

}