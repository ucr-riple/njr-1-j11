package rivercityransom.contracts;

import rivercityransom.decorators.ObjetDecorator;
import rivercityransom.enumerations.Tresor;
import rivercityransom.error.InvariantError;
import rivercityransom.error.PostconditionError;
import rivercityransom.services.ObjetService;

public class ObjetContract extends ObjetDecorator {

	public ObjetContract(ObjetService delegate) {
		super(delegate);
	}

	public void checkInvariant() {
		if (this.isEquipable()) {
			if (!(this.getTresor() == Tresor.CHAINEDEVELO || this.getTresor() == Tresor.POUBELLEMETALLIQUE)) {
				throw new InvariantError("Wrong invariant");
			}
		}

		if (this.isValeurMarchande()) {
			if (!(this.getTresor() == Tresor.CINQUANTECENTIMES || this
					.getTresor() == Tresor.UNDOLLAR)) {
				throw new InvariantError("Wrong invariant");
			}
		}

	}

	public void init(Tresor tresor) {
		super.init(tresor);
		checkInvariant();
		if (!(this.getTresor() == tresor)) {
			throw new PostconditionError("Error tresor");
		}
	}

	@Override
	public String toString() {
		return "[TRESOR " + getTresor() + " ]";
	}
}
