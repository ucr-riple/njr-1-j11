package jatrace.gui.body;

import jatrace.*;
import jatrace.gui.*;
import jatrace.gui.builders.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BodyBuilder
		extends JFrame
		implements ActionListener, FocusListener
{
	
	private final String [] bodyNames = { "Sphere", "Plane", "CheckPlane" };
	
	private JComboBox combobox;
	private JPanel activePanel;
	private SphereBuilder sphereBuilder;
	private PlaneBuilder planeBuilder;
	private CheckPlaneBuilder checkPlaneBuilder;
	
	private BodyPasser parent = null;
	private JPanel body, header, bodyNamer, buildpanel;
	
	private JTextField textInput;
	private JButton textButton = new JButton("set name");
	
	public BodyBuilder(BodyPasser p)
	{
		super();
		
		//let body button listening to this frame handle close operations
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//register which bodypasser this frame points to
		parent = p;
		
		body = new JPanel();
		body.setLayout( new BorderLayout() );
		body.setPreferredSize(new Dimension(400,600));
		
		
		
		header = new JPanel(new GridLayout(0,1,0,5));
		header.setPreferredSize( new Dimension(0,55) );
		body.add(header, BorderLayout.PAGE_START);
		
		//set up body namer panel
		setupBodyNamer();
		
		//setup combobox
		combobox = new JComboBox(bodyNames);
		combobox.setSelectedIndex(0);
		combobox.addActionListener(this);
		header.add(combobox);
		
		//set up body builder panel
		setupBuildPanels();
		
		//set active panel
		updateActivePanel();
		
		getContentPane().add(body);
		pack();
		
	}
	
	private void updateActivePanel()
	{
		int index = combobox.getSelectedIndex();
		String type = bodyNames[index];
		
		System.out.println("Selected " + type + " in combobox.");
		
		if ( type.equals("Sphere") )
		{
			updateByPanel(sphereBuilder);
		}
		
		if (type.equals("Plane"))
		{
			updateByPanel(planeBuilder);
		}
		
		if (type.equals("CheckPlane"))
		{
			updateByPanel(checkPlaneBuilder);
		}
	}
	
	private void updateByPanel(JPanel p)
	{
		if (activePanel != null)
		{
			System.out.println("Removing the active panel.");
			body.remove(activePanel);
			body.repaint();
		}
		activePanel = p;
		System.out.println("Adding a new panel.");
		body.add(activePanel, BorderLayout.CENTER);
		body.repaint();
		revalidate();
	}
	
	
	/** As of now, this method is a stub. Eventually, this will implement a body
	 *  featuring a card layout, each card containing a custom scrollable panel
	 *  containing the object builders needed to build a body. The card layout
	 *  will be controlled by a combobox, probably. */
	private void setupBuildPanels()
	{

		sphereBuilder = new SphereBuilder();
		planeBuilder = new PlaneBuilder();
		checkPlaneBuilder = new CheckPlaneBuilder();
		
	}
	
	private void setupBodyNamer()
	{
		
		//setup label maker title
		bodyNamer = new JPanel(new BorderLayout());
		bodyNamer.setPreferredSize( new Dimension(200,25) );
		JLabel l = new JLabel("Body Label",JLabel.CENTER);
		l.setPreferredSize(new Dimension(100,25));
		bodyNamer.add(l, BorderLayout.LINE_START);
		
		
		//setup up text input field
		String t = parent.getText();
		textInput = new JTextField(20);
		textInput.setText(t);
		textInput.setPreferredSize(new Dimension(0,25));
		textInput.setBorder(BorderFactory.createEmptyBorder(0,5,0,5) );
		textInput.addActionListener(this);
		textInput.addFocusListener(this);
		bodyNamer.add(textInput, BorderLayout.CENTER);
		
		//setup text input button
		textButton.setPreferredSize(new Dimension(100,25));
		textButton.addActionListener(this);
		bodyNamer.add(textButton, BorderLayout.LINE_END);
		
		header.add(bodyNamer);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object s = e.getSource();
		
		if (s == textInput || s == textButton)
		{
			String t = textInput.getText();
			parent.setText(t);
		}
		
		else if (s == combobox)
		{
			updateActivePanel();
		}
		
		//System.out.println("we're supposed to build a body now");
	}
	
	//Focus listener stuff below
	@Override public void focusLost(FocusEvent e) { }
	@Override public void focusGained(FocusEvent e)
	{
		Component c = e.getComponent();
		if (c instanceof JTextField)
		{
			((JTextField)c).selectAll();
		}
	}
	
	
	
}
