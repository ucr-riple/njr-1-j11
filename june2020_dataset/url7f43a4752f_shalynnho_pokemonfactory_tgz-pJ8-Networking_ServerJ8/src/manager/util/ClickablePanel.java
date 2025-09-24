package manager.util;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;


public class ClickablePanel extends OverlayPanel {
	private ClickablePanelClickHandler handler;
	private Color color = new Color(255, 255, 255);
	
	public ClickablePanel(final ClickablePanelClickHandler handler) {
		//transparent white background
		setBackground(new Color(255,255,255,20));
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.handler = handler;
		
		addMouseListener(new MouseListener() {
			@Override public void mouseReleased(MouseEvent arg0) { }
			@Override public void mousePressed(MouseEvent arg0) { }
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 40));
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				handler.mouseClicked();
			}
		});
	}
	
	public Color getColor() {
		return color;
	}
	
	public void restoreColor() {
		color = new Color(255, 255, 255);
		setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 20));
	}
	
	public void setColor(Color c) {
		color = c;
		setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 40));
	}

	public ClickablePanelClickHandler getHandler() {
		return handler;
	}
}
