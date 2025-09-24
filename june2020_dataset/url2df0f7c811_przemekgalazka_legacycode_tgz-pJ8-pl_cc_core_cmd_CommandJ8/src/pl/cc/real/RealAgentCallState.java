package pl.cc.real;

/**
 * Stany w jakim może znajdować się agent pod kontem prowadzenia rozmowy telefonicznej
 * 
 * @since Nov 10, 2008
 */
public enum RealAgentCallState {
	NEW_CALL, 	// telefon dzwoni 
	CONNECT,  	// agent rozmawia onAgentConnect
	HANGEUP		// tel nie dzwoni, agent nie rozmawia
}
