package org.swiftgantt.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.LabelUI;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.swiftgantt.LogoView;
import org.swiftgantt.common.LogHelper;

/**
 * paint logo and version for <code>LogoView.java</code>
 * @author Yuxing Wang
 * 
 */
public class LogoViewUI extends LabelUI {

	protected LogoView logoView = null;
	protected Logger logger = null;
	private int fontSize = 28;

	public LogoViewUI() {
		logger = LogManager.getLogger(LogoViewUI.class);
	}

	public static ComponentUI createUI(JComponent c) {
		return new LogoViewUI();
	}

	@Override
	public void installUI(JComponent c) {
		logoView = (LogoView) c;
		super.installUI(c);
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		LogHelper.title(logger, "Start to Paint Logo View UI");
		int width = c.getSize().width;
		int height = c.getSize().height;
		logger.debug(width);
		logger.debug(height);
		//        Rectangle rec = new Rectangle(0, 0, width, height);

		//        g.setColor(this.logoView.getGanttChart().getConfig().getGanttChartBackColor());
		//        g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		// Draw Brand "SwiftGantt"
		g.setFont(new Font("Colonna MT", Font.BOLD, fontSize));

		String brand = logoView.getText();
		if (brand != null) {
			logger.debug("Text: " + brand);
			g.setColor(Color.GRAY);
			g.drawChars(brand.toCharArray(), 0, brand.length(), 20, 32);
		}
		//
		g.setColor(Color.black);
		g.drawRect(0, 0, width, height);
	}

	@Override
	public void uninstallUI(JComponent c) {
		super.uninstallUI(c);
	}
}
