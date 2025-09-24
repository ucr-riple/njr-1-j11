package rivercityransom.decorators;

import rivercityransom.enumerations.Tresor;
import rivercityransom.services.ObjetService;

public abstract class ObjetDecorator implements ObjetService {
	private ObjetService delegate;

	public ObjetDecorator(ObjetService delegate) {
		super();
		this.delegate = delegate;
	}

	public boolean isEquipable() {
		return delegate.isEquipable();
	}

	public boolean isValeurMarchande() {
		return delegate.isValeurMarchande();
	}

	public Tresor getTresor() {
		return delegate.getTresor();
	}

	public void init(Tresor tresor) {
		delegate.init(tresor);
	}

}
