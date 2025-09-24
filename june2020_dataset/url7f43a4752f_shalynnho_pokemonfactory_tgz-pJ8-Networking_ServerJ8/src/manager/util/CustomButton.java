package manager.util;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class CustomButton extends ClickablePanel {
    private Color borderColor = new Color(35, 35, 35);
    private Color borderHoverColor = new Color(150, 150, 150);
    private String name;

    private WhiteLabel buttonLabel;

    Border border;
    Border padding;
    Border compoundBorder;

    public CustomButton(String name, ClickablePanelClickHandler handler) {
	super(handler);
	this.name = name;
	setAlignmentX(CENTER_ALIGNMENT);
	
	buttonLabel = new WhiteLabel(name);
	add(buttonLabel);

	border = BorderFactory.createLineBorder(borderColor, 1);
	padding = BorderFactory.createEmptyBorder(4, 20, 4, 20);
	compoundBorder = new CompoundBorder(border, padding);
	setBorder(compoundBorder);

	addMouseListener(new MouseListener() {
	    @Override public void mouseClicked(MouseEvent e) { }
	    @Override public void mousePressed(MouseEvent e) { }
	    @Override public void mouseReleased(MouseEvent e) { }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		border = BorderFactory.createLineBorder(borderHoverColor, 1);
		compoundBorder = new CompoundBorder(border, padding);
		setBorder(compoundBorder);
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		border = BorderFactory.createLineBorder(borderColor, 1);
		compoundBorder = new CompoundBorder(border, padding);
		setBorder(compoundBorder);
	    }
	});
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
