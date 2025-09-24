package org.swiftgantt.common;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * @author Yuxing Wang
 * 
 */
public class ResourceManager {

	private static final String BUNDLE_NAME = "org.swiftgantt.resource.gantt";
//	private Map<String, String> resource = null;
	private ResourceBundle resBundle;

	private static ResourceManager manager = null;

	Locale locale = Locale.ENGLISH;
	
	private ResourceManager() {
//		resource = new HashMap<String, String>();
//		resource.put("Head.Date.Monday", "M");
//		resource.put("Head.Date.Tuesday", "T");
//		resource.put("Head.Date.Wednesday", "W");
//		resource.put("Head.Date.Thursday", "T");
//		resource.put("Head.Date.Friday", "F");
//		resource.put("Head.Date.Saturday", "S");
//		resource.put("Head.Date.Sunday", "S");
		
//		locale = Locale.JAPAN;
//		locale = Locale.KOREA;
//		System.out.println(Locale.getDefault());
		try {
			// Trey system default.
			resBundle = ResourceBundle.getBundle(BUNDLE_NAME);
		} catch (RuntimeException e) {
			e.printStackTrace();
			// If failed to find system default resource bundle, use english as default.
			resBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
		}
	}

	public static ResourceManager getInstance() {
		if (manager == null) {
			manager = new ResourceManager();
		}
		return manager;
	}

	public String getString(String key) {
		String value = resBundle.getString(key);
		if (value == null) {
			value = "[Default Value]";
		}
		return value;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
