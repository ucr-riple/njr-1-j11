package rivercityransom.decorators;

import rivercityransom.enumerations.Type;
import rivercityransom.services.BlocService;
import rivercityransom.services.ObjetService;

public abstract class BlocDecorator implements BlocService {
	private BlocService delegate;

	public BlocDecorator(BlocService delegate) {
		super();
		this.delegate = delegate;
	}

	public Type getType() {
		return delegate.getType();
	}

	public ObjetService getObjet() {
		return delegate.getObjet();
	}

	public void init(Type type, ObjetService tresor) {
		delegate.init(type, tresor);
	}

	@Override
	public void setObjet(ObjetService t) {
		delegate.setObjet(t);
	}

}
