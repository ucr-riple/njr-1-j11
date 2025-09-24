package net.woopa.dungeon.managers;

import java.util.Properties;

import net.woopa.dungeon.settings.Property;

public class SettingsManager {
	//TODO add some sort of save/load to and from files
	public static Properties properties = new Properties();

	public static void addProperties(Class<?> c) {
		if (!c.isEnum())
			return;
		if (!(c.getEnumConstants() instanceof Property[]))
			return;
		Property[] ps = (Property[]) c.getEnumConstants();
		for (Property p : ps) {
			properties.setProperty(p.getKey(), p.getValue().toString());
		}
	}

	public static int getInt(Property p) {
		return Integer.parseInt(properties.getProperty(p.getKey()));
	}

	public static boolean getBoolean(Property p) {
		return Boolean.parseBoolean(properties.getProperty(p.getKey()));
	}
}
