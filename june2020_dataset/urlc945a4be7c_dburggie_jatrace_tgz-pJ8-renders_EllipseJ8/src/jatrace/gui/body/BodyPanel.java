package jatrace.gui.body;

import jatrace.*;
import jatrace.gui.*;
import jatrace.gui.builders.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BodyPanel extends JPanel implements ActionListener
{
	
	private BodyButton head, tail;
	
	private JScrollPane scroller;
	private ButtonPanel buttons;
	private JButton adder;

	
	public BodyPanel()
	{

		super(new BorderLayout());
		setPreferredSize( new Dimension(200,400) );
		
		//create the body adder
		adder = new JButton("New Body");
		adder.setPreferredSize( new Dimension(200,50) );
		adder.addActionListener(this);
		add(adder, BorderLayout.PAGE_END);
		
		//initialize buttons in the Scroll Pane
		head = tail = new BodyButton();
		buttons = new ButtonPanel(head);
		
		//create the scroller
		/*
		scroller = new JScrollPane(
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		*/
		scroller = new JScrollPane(buttons);
		scroller.setBorder( BorderFactory.createEmptyBorder(0,0,5,0) );
		add(scroller, BorderLayout.CENTER);
		
		updateScroller();
		
	}
	
	private void updateScroller()
	{
		scroller.revalidate();
	}
	
	private void addNewBodyButton()
	{
		System.out.println("Adding a new Body Button!");
		BodyButton b = new BodyButton();
		
		if (tail != null)
		{
			tail.insertAfter(b);
			tail = b;
		}
		
		else
		{
			head = tail = b;
		}
		
		buttons.add(b);
		updateScroller();
	}
	
	private void updateButtons()
	{
		buttons = new ButtonPanel(head);
		updateScroller();
	}
	
	public void removeButton(BodyButton b)
	{
		BodyButton temp = head;
		
		while (temp != null)
		{
			if ( b == temp )
			{
				temp.remove();
				updateButtons();
				break;
			}
			temp = temp.getNext();
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		Object s = e.getSource();
		
		if (s == adder)
		{
			addNewBodyButton();
		}
	}
}
