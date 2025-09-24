package pl.cc.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Obsługa plików ini.<BR>
 * 
 *
 * @author internet
 * @since 2007-01-31
 */
public class IniFile {

	Hashtable<String,Hashtable<String,String>> sections;

	/**
	 * Constructor for the IniFile object
	 */
	public IniFile() {
		sections = new Hashtable<String, Hashtable<String, String>>();
	}

	/**
	 * Constructor for the IniFile object
	 *
	 * @param filename
	 *            Description of Parameter
	 * @exception FileNotFoundException
	 *                Description of Exception
	 */
	public IniFile(String filename) throws FileNotFoundException {
		this();
		load(filename);
	}

	/**
	 * Constructor for the IniFile object
	 *
	 * @param url
	 *            Description of Parameter
	 * @exception IOException
	 *                Description of Exception
	 */
	public IniFile(URL url) throws IOException {
		this();
		load(url.openStream());
	}

	/**
	 * Constructor for the IniFile object
	 *
	 * @param input
	 *            Description of Parameter
	 * @exception IOException
	 *                Description of Exception
	 */
	public IniFile(InputStream input) throws IOException {
		this();
		load(input);
	}

	/**
	 * Sets the KeyValue attribute of the IniFile object
	 *
	 * @param section
	 *            The new KeyValue value
	 * @param key
	 *            The new KeyValue value
	 * @param value
	 *            The new KeyValue value
	 */
	public void setKeyValue(String section, String key, String value) {
		try {
			getSection(section).put(key.toLowerCase(), value);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the Sections attribute of the IniFile object
	 *
	 * @return The Sections value
	 */
	public Hashtable<String, Hashtable<String, String>> getSections() {
		return sections;
	}

	/**
	 * Gets the Section attribute of the IniFile object
	 *
	 * @param section
	 *            Description of Parameter
	 * @return The Section value
	 */
	public Hashtable<String, String> getSection(String section) {
		return  sections.get(section.toLowerCase());
	}

	/**
	 * Gets the NullOrEmpty attribute of the IniFile object
	 *
	 * @param section
	 *            Description of Parameter
	 * @param key
	 *            Description of Parameter
	 * @return The NullOrEmpty value
	 */
	public boolean isNullOrEmpty(String section, String key) {
		String value = getKeyValue(section, key);
		return (value == null || value.length() == 0);
	}

	/**
	 * Gets the KeyValue attribute of the IniFile object
	 *
	 * @param section
	 *            Description of Parameter
	 * @param key
	 *            Description of Parameter
	 * @return The KeyValue value
	 */
	public String getKeyValue(String section, String key) {
		try {
			return (String) getSection(section).get(key.toLowerCase());
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Gets the KeyIntValue attribute of the IniFile object
	 *
	 * @param section
	 *            Description of Parameter
	 * @param key
	 *            Description of Parameter
	 * @return The KeyIntValue value
	 */
	public int getKeyIntValue(String section, String key) {
		return getKeyIntValue(section, key, 0);
	}

	/**
	 * Gets the KeyIntValue attribute of the IniFile object
	 *
	 * @param section
	 *            Description of Parameter
	 * @param key
	 *            Description of Parameter
	 * @param defaultValue
	 *            Description of Parameter
	 * @return The KeyIntValue value
	 */
	public int getKeyIntValue(String section, String key, int defaultValue) {
		String value = getKeyValue(section, key.toLowerCase());
		if (value == null) {
			return defaultValue;
		} else {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
	}

	/**
	 * Gets the KeysAndValues attribute of the IniFile object
	 *
	 * @param aSection
	 *            Description of Parameter
	 * @return The KeysAndValues value
	 */
	public String[][] getKeysAndValues(String aSection) {
		Hashtable<String, String> section = getSection(aSection);
		if (section == null) {
			return null;
		}
		String[][] results = new String[section.size()][2];
		int i = 0;
		for (Enumeration<String> f = section.keys(), g = section.elements(); f
				.hasMoreElements(); i++) {
			results[i][0] = (String) f.nextElement();
			results[i][1] = (String) g.nextElement();
		}
		return results;
	}

	/**
	 * Description of the Method
	 *
	 * @param filename
	 *            Description of Parameter
	 * @exception FileNotFoundException
	 *                Description of Exception
	 */
	public void load(String filename) throws FileNotFoundException {
		load(new FileInputStream(filename));
	}

	/**
	 * Description of the Method
	 *
	 * @param filename
	 *            Description of Parameter
	 * @exception IOException
	 *                Description of Exception
	 */
	public void save(String filename) throws IOException {
		save(new FileOutputStream(filename));
	}

	/**
	 * Description of the Method
	 *
	 * @param in
	 *            Description of Parameter
	 */
	public void load(InputStream in) {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(in));
			String read;
			Hashtable<String, String> section = null;
			String section_name;
			while ((read = input.readLine()) != null) {
				if (read.startsWith(";") || read.startsWith("#")) {
					continue;
				} else if (read.startsWith("[")) {
					// new section
					section_name = read.substring(1, read.indexOf("]"))
							.toLowerCase();
					section = sections.get(section_name);
					if (section == null) {
						section = new Hashtable<String, String>();
						sections.put(section_name, section);
					}
				} else if (read.indexOf("=") != -1 && section != null) {
					// new key
					String key = read.substring(0, read.indexOf("=")).trim()
							.toLowerCase();
					String value = read.substring(read.indexOf("=") + 1).trim();
					// usunięcie z jiń
					value = value.replaceAll("\\s*;.*", "");
					section.put(key, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description of the Method
	 *
	 * @param out
	 *            Description of Parameter
	 */
	public void save(OutputStream out) {
		try {
			PrintWriter output = new PrintWriter(out);
			String section;
			for (Enumeration<String> e = sections.keys(); e.hasMoreElements();) {
				section = (String) e.nextElement();
				output.println("[" + section + "]");
				for (Enumeration<String> f = getSection(section).keys(), g = getSection(
						section).elements(); f.hasMoreElements();) {
					output.println(f.nextElement() + "=" + g.nextElement());
				}
			}
			output.flush();
			output.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a feature to the Section attribute of the IniFile object
	 *
	 * @param section
	 *            The feature to be added to the Section attribute
	 */
	public void addSection(String section) {
		sections.put(section.toLowerCase(), new Hashtable<String, String>());
	}

	/**
	 * Description of the Method
	 *
	 * @param section
	 *            Description of Parameter
	 */
	public void removeSection(String section) {
	}

	/**
	 * Simple test function
	 *
	 * @param args
	 *            The command line arguments
	 * @exception Exception
	 *                Description of Exception
	 */
	
	public static void main(String[] args) throws Exception {
		IniFile i = new IniFile(Resources.getInputStream("client.ini"));
		Hashtable<String, String> server = i.getSection("server");
		System.out.println(server.get("ip"));
		/*
			IniFile i = new IniFile();
			i.addSection("OPTIONS");
			i.setKeyValue("OPTIONS", "output_directory", "/tmp/");
			i.setKeyValue("OPTIONS", "dry_run", "0");
			i.save("/tmp/f.ini");
	
			IniFile io = new IniFile("/tmp/f.ini");
			System.out.println(io.getKeyValue("OPTIONS", "dry_run"));
		 */
	}
}
