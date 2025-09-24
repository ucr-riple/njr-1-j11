package net.woopa.dungeon.managers;

import java.util.HashMap;
import java.util.Map;

import net.woopa.dungeon.core.CoreMaterial;
import net.woopa.dungeon.core.Main2;
import net.woopa.dungeon.datatypes.Material;

public class MaterialManager {
	private final static Map<Character, Material> materials = new HashMap<Character, Material>();
	@SuppressWarnings("unused")
	private final Main2 main;

	public MaterialManager(Main2 m) {
		this.main = m;
	}

	public void addMaterials(Class<?> c) {
		if (!c.isEnum())
			return;
		if (!(c.getEnumConstants() instanceof Material[]))
			return;
		Material[] ms = (Material[]) c.getEnumConstants();
		for (Material m : ms) {
			materials.put(m.getSymbol(), m);
		}
	}

	public static Material getMaterial(char c) {
		Material s = materials.get(c);
		if (s == null) {
			s = CoreMaterial.UNDEF;
		}
		return s;
	}

	public void addMaterial(Material m) {
		materials.put(m.getSymbol(), m);
	}
}
