package net.woopa.dungeon.core;
import net.woopa.dungeon.datatypes.Material;

public enum CoreMaterial implements Material {
	UNDEF(' '), WALL('#'), FIXEDWALL('X'), UPWALL('U'), DOWNWALL('D'), BOTHWALL(
			'B'), PRESSURE('x'), RED1('1'), RED2('2'), ARROW('>'), FLOOR('.'), FIXEDFLOOR(
			','), FIXEDFLOORUP(';'), FIXEDFLOORDOWN(':'), O_FLOOR('`'), WINDOW(
			'G'), BARS('I'), HIGH_BARS('b'), DOOR('+'), UP('^'), DOWN('V'), ARCH(
			'A'), HIDDEN('$'), WATER('W'), LAVA('L'), ANVIL('a'), FURNACE('f'), BOOKCASE(
			'k'), BOOKCASE2('K'), SIGNPOST('p'), ENCHANT('e'), TORCH('t'), O_TORCH(
			'~'), WEB('w'), SHROOM('m'), CAKE('='), SOULSAND('s'), EMPTYCHEST(
			'o'), CHEST('c'), MIDCHEST('C'), BIGCHEST('*'), WORKBENCH('T'), SPAWNER(
			'M'), BED_H('Z'), BED_F('z'), NONE('!');

	private final char ch;

	CoreMaterial(char ch) {
		this.ch = ch;
	}

	public Boolean isFloor() {
		return this == FLOOR || this == FIXEDFLOOR || this == FIXEDFLOORUP
				|| this == FIXEDFLOORDOWN;
	}

	public Boolean isWall() {
		return this == CoreMaterial.WALL || this == CoreMaterial.FIXEDWALL
				|| this == CoreMaterial.DOWNWALL
				|| this == CoreMaterial.BOTHWALL || this == CoreMaterial.UPWALL;
	}

	public Boolean isChest() {
		return this == CoreMaterial.CHEST || this == CoreMaterial.MIDCHEST
				|| this == CoreMaterial.BIGCHEST
				|| this == CoreMaterial.EMPTYCHEST;
	}

	public Boolean isDoor() {
		return this == CoreMaterial.DOOR || this == CoreMaterial.ARCH
				|| this == CoreMaterial.HIDDEN || this == CoreMaterial.WEB;
	}

	public Boolean isStair() {
		return this == CoreMaterial.UP || this == CoreMaterial.DOWN;
	}

	public Boolean isUndef() {
		return this == CoreMaterial.UNDEF;
	}

	public Boolean isBoundary() {
		return (this.isWall() || this.isStair() || this.isDoor());
	}

	@Override
	public char getSymbol() {
		return ch;
	}

	@Override
	public String getName() {
		return this.name();
	}
}