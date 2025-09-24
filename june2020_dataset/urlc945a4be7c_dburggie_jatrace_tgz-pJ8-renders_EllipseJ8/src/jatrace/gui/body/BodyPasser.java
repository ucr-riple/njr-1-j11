package jatrace.gui.body;

import jatrace.*;
import jatrace.gui.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public interface BodyPasser
{
	public void setText(String t);
	public String getText();
	
	public void setBody(Body b);
	public Body getBody();
	
	public void setPrevBodyPasser(BodyPasser p);
	public void setNextBodyPasser(BodyPasser p);
	public void insertBefore(BodyPasser p);
	public void insertAfter(BodyPasser p);
	public void remove();
}


