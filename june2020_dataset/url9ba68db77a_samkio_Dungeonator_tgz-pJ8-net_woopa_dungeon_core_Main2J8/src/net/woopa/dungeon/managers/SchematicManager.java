package net.woopa.dungeon.managers;

import java.util.ArrayList;
import java.util.List;

import net.woopa.dungeon.core.Main2;
import net.woopa.dungeon.core.RandomUtil;
import net.woopa.dungeon.datatypes.Schematic;

public class SchematicManager {
	public static List<Schematic> customRooms = new ArrayList<Schematic>();
	public static List<Schematic> customDown = new ArrayList<Schematic>();

	@SuppressWarnings("unused")
	private final Main2 main;

	public SchematicManager(Main2 main) {
		this.main = main;
	}

	public void addCustomRoom(Schematic s) {
		customRooms.add(s);
	}

	public void addCustomDown(Schematic s) {
		customDown.add(s);
	}

	public void addCustomRooms(Class<?> c) {
		if (!c.isEnum())
			return;
		if (!(c.getEnumConstants() instanceof Schematic[]))
			return;
		Schematic[] ss = (Schematic[]) c.getEnumConstants();
		for (Schematic s : ss) {
			customRooms.add(s);
		}
	}

	public void addCustomDowns(Class<?> c) {
		if (!c.isEnum())
			return;
		if (!(c.getEnumConstants() instanceof Schematic[]))
			return;
		Schematic[] ss = (Schematic[]) c.getEnumConstants();
		for (Schematic s : ss) {
			customDown.add(s);
		}
	}

	public static Schematic randomRoomSchematic() {
		return customRooms.get(RandomUtil.nextInt(customRooms.size()));
	}

	public static Schematic randomDownSchematic() {
		return customDown.get(RandomUtil.nextInt(customDown.size()));
	}
}
