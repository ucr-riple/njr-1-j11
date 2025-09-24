package rivercityransom.services;

import rivercityransom.enumerations.Type;

public interface BlocService {
	/** Observateur : type du bloc */
	Type getType();

	/** Observateur : trésor du bloc */
	ObjetService getObjet();
	
	/**
	 * Initialisation 
	 * post: getType() = type 
	 * post: getObjet() = tresor
	 */
	void init(Type type, ObjetService tresor);
	
	/**
	 * Setter de Tresor
	 * post: getObjet() = t
	 */
	void setObjet(ObjetService t);

}
