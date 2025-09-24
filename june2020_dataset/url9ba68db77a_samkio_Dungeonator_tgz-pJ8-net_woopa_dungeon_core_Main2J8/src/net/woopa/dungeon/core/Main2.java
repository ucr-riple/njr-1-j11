package net.woopa.dungeon.core;

import net.woopa.dungeon.datatypes.Direction;
import net.woopa.dungeon.datatypes.Level;
import net.woopa.dungeon.datatypes.Vector2D;
import net.woopa.dungeon.managers.MaterialManager;
import net.woopa.dungeon.managers.SchematicManager;
import net.woopa.dungeon.managers.SettingsManager;

public class Main2 {
	public final MaterialManager mm = new MaterialManager(this);
	public final SettingsManager sm = new SettingsManager();
	public final SchematicManager scm = new SchematicManager(this);

	public static void main(String[] args) {
		Main2 m = new Main2();
		m.begin();
	}

	private void begin() {
		System.err.println("Take2");
		this.mm.addMaterials(CoreMaterial.class);
		this.scm.addCustomRooms(CoreSchematic.class);
		this.scm.addCustomDowns(CoreDownSchematic.class);
		CoreLevelCreator clc = new CoreLevelCreator();
		Vector2D size = new Vector2D(100,30);
		Vector2D start = new Vector2D(50,15);
		Direction d = Direction.randomDirection();
		Level l = null;
		Dbg.tic();
		for (int I=0;I <1; I++){
		l = new Level(clc,size,start,d);
		Dbg.toc("Level"+(I+1));
		}
		Dbg.toc("Main");
		l.show();

	}

	public static void log(String s) {
		System.out.print(s);
	}
}
