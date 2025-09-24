package org.swiftgantt.common;

import java.awt.Graphics;

/**
 * Helper class for paint
 * 
 * @author Yuxing Wang
 * 
 */
public class PaintHelper {
	
	public static void fillThwartFoggyRect(Graphics g, int x, int y, int width, int height) {
		for (int i = x; i < width + x; i += 2) {
			for (int j = y; j < height + y; j += 2) {
				g.fillRect(i, j, 1, 1);
			}
		}
	}

	public static void fillBevelFoggyRect(Graphics g, int x, int y, int width, int height) {
		for (int i = x; i < width + x; i++) {
			for (int j = y + i%3; j < height + y; j += 3) {
				g.fillRect(i, j, 1, 1);
			}
		}
	}
	
	public static void fillOppositeBevelFoggyRect(Graphics g, int x, int y, int width, int height) {
		for (int i = width + x; i > x; i--) {
			for (int j = y + (3 - i % 3); j < height + y; j += 3) {
				g.fillRect(i, j, 1, 1);
			}
		}
	}
	
	public static void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2){
		for (int i = x1; i <= x2; i+=2) {
			for (int j = y1; j <= y2; j+=2) {
				g.fillRect(i, j, 1, 1);
			}
		}
	}
}
