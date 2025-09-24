package pl.cc;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localization {

	static ResourceBundle rb;
	
	public static void initLocalization(String locale){
		try {
			rb = ResourceBundle.getBundle("ccproxylib", new Locale(locale));
		} catch (MissingResourceException e) {
			System.out.println("No localization "+locale);
			rb=null;
		}
	}
	
	public static String getText(String text){
		if (rb==null) return text;
		try {
			return rb.getString(text);
		} catch (MissingResourceException e) {
			return text;
		}
	}
}
