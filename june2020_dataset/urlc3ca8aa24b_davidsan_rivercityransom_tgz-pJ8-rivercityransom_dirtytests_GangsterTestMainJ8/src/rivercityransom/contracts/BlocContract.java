package rivercityransom.contracts;

import rivercityransom.decorators.BlocDecorator;
import rivercityransom.enumerations.Type;
import rivercityransom.error.PostconditionError;
import rivercityransom.services.BlocService;
import rivercityransom.services.ObjetService;

public class BlocContract extends BlocDecorator {

	public BlocContract(BlocService delegate) {
		super(delegate);
	}

	public void checkInvariant() {
		// nothing
	}

	@Override
	public void init(Type type, ObjetService tresor) {
		checkInvariant();
		super.init(type, tresor);
		checkInvariant();
		if (!(this.getType() == type)) {
			throw new PostconditionError("Wrong type");
		}
		if (!(this.getObjet() == tresor)) {
			throw new PostconditionError("Wrong tresor");
		}
	}

	@Override
	public void setObjet(ObjetService t) {
		checkInvariant();
		super.setObjet(t);
		checkInvariant();
		if (!(getObjet() == t)) {
			throw new PostconditionError("Tresor was not set");
		}
	}

	@Override
	public String toString() {
		return "[BLOC type=" + getType() + ", tresor=" + getObjet() + " ]";

	}

}
