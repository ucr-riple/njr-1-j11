package fr.noxx90.jflam;

import fr.noxx90.jflam.functions.Linear;
import fr.noxx90.jflam.functions.Sinusoidal;
import fr.noxx90.jflam.functions.Spherical;
import fr.noxx90.jflam.functions.Swirl;
import fr.noxx90.jflam.model.Function;

public class Functions
{
	public static final Function LINEAR = new Linear();
	public static final Function SINUSOIDAL = new Sinusoidal();
	public static final Function SPHERICAL = new Spherical();
	public static final Function SWIRL = new Swirl();
}
