package logica;

import java.awt.Point;

public enum Direccion {
	/* Ă˘â â */ NORTH(-1, 0),
	/* Ă˘â â */ SOUTH(+1, 0),
	/* Ă˘â ďż˝ */ WEST(0, -1),
	/* Ă˘â â */ EAST(0, +1),
	/* Ă˘â â */ NORTHWEST(-1, -1),
	/* Ă˘â Ë */ SOUTHEAST(+1, +1),
	/* Ă˘â â˘ */ SOUTHWEST(+1, -1),
	/* Ă˘â â */ NORTHEAST(-1, +1);
	private int pasoEnFila;
	private int pasoEnColumna;

	private Direccion(int pasoEnFila, int pasoEnColumna) {
		this.pasoEnFila = pasoEnFila;
		this.pasoEnColumna = pasoEnColumna;
	}

	public Point siguiente(Point point) {
		/* */
		if(point == null){
			System.out.println("El punto es nulooooo!!!");
		}
		return new Point(point.x + pasoEnFila, point.y + pasoEnColumna);
	}
}
