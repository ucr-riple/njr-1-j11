package net.woopa.dungeon.core;

import net.woopa.dungeon.managers.SettingsManager;
import net.woopa.dungeon.settings.Property;


public enum CoreSettings implements Property {
	RoomMax(".Room.Max", 10), RoomMin(".Room.Min", 3), SpecialPct(
			".SpecialPct", 50), CorridorPct(".CorridorPct", 30), CorridorMax(
			".Corridor.Max", 9), CorridorMin(".Corridor.Min", 3), CorridorW2Pct(
			".Corridor.Width2Pct", 40), CorridorW3Pct(".Corridor.Width3Pct", 10), HiddenPct(
			".Archway.Type.HiddenPct", 10), DoorPct(".Archway.Type.DoorPct", 30), WebDoorPct(
			".Archway.Type.WebDoorPct", 10), TrapPct(".Room.Clutter.TrapPct",
			10), SandPct(".Room.Clutter.SandPct", 10), ChestPct(
			".Room.Clutter.ChestPct", 35), SpawnerPct(
			".Room.Clutter.SpawnerPct", 50), PoolPct(
			".Room.Clutter.Pool.PoolPct", 15), FullPoolPct(
			".Room.Clutter.Pool.FullPoolPct", 40), LavaPct(
			".Room.Clutter.Pool.LavaPct", 30), ShroomPct(
			".Room.Clutter.ShroomPct", 10), BenchPct(".Room.Clutter.BenchPct",
			3), AnvilPct(".Room.Clutter.AnvilPct", 3), EnchantPct(
			".Room.Clutter.EnchantPct", 3), OvenPct(".Room.Clutter.OvenPct", 2), DoubleDoorPct(
			".Archway.DoubleWidthPct", 60), MedHalfEmpty(
			".Loot.Medium.HalfEmpty", false);
	
	static {
		SettingsManager.addProperties(CoreSettings.class);
	}
	
	private String key;
	private Object val;

	private CoreSettings(String str, Object def) {
		this.key = str;
		this.val = def;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public Object getValue() {
		return this.val;
	}

}
