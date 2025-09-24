package manager.util;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

import Networking.Client;

public class WhiteLabel extends JLabel {
	public WhiteLabel(String text) {
		super(text);
		setForeground(Color.WHITE);
		setFont(Client.font);
	}
	
	public void setLabelSize(Dimension d) {
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
	}
	
	public void setLabelSize(int width, int height) {
		setLabelSize(new Dimension(width, height));
	}
}
