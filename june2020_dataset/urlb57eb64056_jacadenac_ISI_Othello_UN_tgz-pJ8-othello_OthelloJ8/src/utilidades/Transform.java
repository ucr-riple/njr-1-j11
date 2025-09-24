package utilidades;

import java.awt.Point;

import core.Tablero;

public class Transform {
	public static Point indexToPoint(int index) {
		return new Point(index / Tablero.TABLERO_LARGO, index % Tablero.TABLERO_LARGO);
	}

	public static Point indexToPoint(int index, int length) {
		return new Point(index / length, index % length);
	}

	public static int pointToIndex(Point point) {
		return Tablero.TABLERO_LARGO * point.x + point.y;
	}

	public static int pointToIndex(Point point, int length) {
		return length * point.x + point.y;
	}

	public static String toBoardNotation(Point coordinate) {
		return String.format("%d%c", coordinate.x + 1, coordinate.y + 65);
	}

	public static Point fromBoardNotation(String coordinate) {
		return new Point(coordinate.charAt(0) - 1, coordinate.charAt(1) - 65);
	}
}
