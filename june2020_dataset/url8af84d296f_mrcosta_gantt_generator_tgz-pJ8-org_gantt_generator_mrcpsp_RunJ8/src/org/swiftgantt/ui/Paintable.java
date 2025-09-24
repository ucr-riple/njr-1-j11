package org.swiftgantt.ui;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * Paintable object in SwiftGantt UI.
 * @author Yuxing Wang
 * @version 1.0
 */
public interface Paintable {
	public void paint(Graphics g, JComponent c, Rectangle rec);
}
