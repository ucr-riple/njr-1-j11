package rivercityransom.impl;

import rivercityransom.enumerations.Type;
import rivercityransom.services.BlocService;
import rivercityransom.services.ObjetService;

public class BlocImpl implements BlocService {

	private Type type;
	private ObjetService objet;

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public ObjetService getObjet() {
		return objet;
	}

	@Override
	public void init(Type type, ObjetService objet) {
		this.type = type;
		this.objet = objet;
	}

	@Override
	public void setObjet(ObjetService t) {
		this.objet = t;
	}

}
