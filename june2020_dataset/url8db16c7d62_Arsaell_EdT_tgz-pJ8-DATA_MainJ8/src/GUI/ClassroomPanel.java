package GUI;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import DATA.ClassType;
import DATA.Classroom;
import DATA.DataStore;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class ClassroomPanel extends JPanel {

	private DataStore dataStore;
	private ArrayList<Classroom> classrooms;
	private HashMap<ClassType, DefaultMutableTreeNode> treenodes;
	private JTextField tfName;
	private JComboBox cbType;
	private JButton btnPosition;
	private JButton btnAjouter;
	private Point position;
	private JSpinner spEff;
	private JTree tree;
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JButton btnPreview;
	private ClassroomPanel cp;
	
	private void initializeTree() {
		ArrayList<ClassType> types = dataStore.getTypes();
		tree = new JTree(new DefaultTreeModel(new DefaultMutableTreeNode()));
		
		for (ClassType ct : dataStore.getTypes())	{
			DefaultMutableTreeNode node = new DefaultMutableTreeNode("[" + ct.getShortName() + "] " + ct.getName());
			
			for (Classroom cr : dataStore.getClassrooms()) {
				if(cr.getType() == ct) {
					DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(cr.getName());
					node.add(node2);
				}
			}
			((DefaultMutableTreeNode)(tree.getModel().getRoot())).add(node);
			treenodes.put(ct, node);
		}
		
		for (int i = 0 ; i < tree.getRowCount() ; i++)
			tree.expandRow(i);
		
		tree.setRootVisible(false);
		
		scrollPane = new JScrollPane(tree);
		splitPane.setLeftComponent(scrollPane);
		splitPane.setDividerLocation(0.25);
		
		panel.remove(cbType);
		cbType = new JComboBox(types.toArray());
		panel.add(cbType, 5);
	}
	
	public ClassroomPanel(DataStore ds) {

		super();
		this.cp = this;
		this.dataStore = ds;
		this.classrooms = dataStore.getClassrooms();
		this.treenodes = new HashMap<ClassType, DefaultMutableTreeNode>();
		
		this.addComponentListener(new ComponentAdapter() {
			
			public void componentShown(ComponentEvent e) {
				initializeTree();
			}
			
			public void componentHidden(ComponentEvent e)	{
				dataStore.setClassrooms(classrooms);
			}
		});
		
		this.setLayout(new GridLayout(0, 1, 0, 0));
		
		this.splitPane = new JSplitPane();
		add(splitPane);
		
		this.tree = new JTree();
		this.scrollPane = new JScrollPane(tree);
		splitPane.setLeftComponent(this.scrollPane);
		
		this.panel = new JPanel();
		panel.setBorder(new EmptyBorder(23, 23, 23, 23));
		splitPane.setRightComponent(panel);
		
		panel.setLayout(new GridLayout(7, 2, 46, 16));
		
		JLabel label_1 = new JLabel("");
		panel.add(label_1);
		
		JLabel label = new JLabel("");
		panel.add(label);
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNom);
		
		this.tfName = new JTextField();
		tfName.setHorizontalAlignment(SwingConstants.LEFT);
		tfName.addInputMethodListener(new InputMethodListener() {
			
			@Override
			public void inputMethodTextChanged(InputMethodEvent event) {
				checkEnableBtn();
			}

			@Override
			public void caretPositionChanged(InputMethodEvent event) {}
		});
		panel.add(tfName);
		tfName.setColumns(10);
		
		JLabel lblType = new JLabel("Type");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblType);
		
		this.cbType = new JComboBox();
		panel.add(cbType);
		
		JLabel lblEffectif = new JLabel("Effectif");
		lblEffectif.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblEffectif);
		
		spEff = new JSpinner();
		spEff.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spEff.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				checkEnableBtn();
			}
		});
		panel.add(spEff);
		
		this.btnPosition = new JButton("Position");
		btnPosition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try	{
					new PositionFrame(ImageIO.read(new File("img/Plan Campus.jpg")), cp);
				} catch(Exception e){
					e.printStackTrace();
				}
				checkEnableBtn();
			}
		});
		panel.add(btnPosition);
		
		this.btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String name = tfName.getText();
				ClassType type = (ClassType)cbType.getSelectedItem();
				int eff = (Integer)spEff.getValue();
				//System.out.println("ClassroomPanel.btnAjouter() : " + name + " " + type + " " + eff + " " + position);
				Classroom cr = new Classroom(type, name, eff, position);
				classrooms.add(cr);
				
				DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
				root = (DefaultMutableTreeNode) root.getChildAt(root.getIndex(treenodes.get(type)));
				
				DefaultMutableTreeNode node = new DefaultMutableTreeNode(cr.getName());
				root.insert(node, root.getChildCount());
				
				tree = new JTree((DefaultMutableTreeNode)tree.getModel().getRoot());
				tree.setRootVisible(false);
				tree.makeVisible(new TreePath(node.getPath()));
				
				scrollPane = new JScrollPane(tree);
				splitPane.setLeftComponent(scrollPane);
				splitPane.setDividerLocation(0.25);
				//On redimensione la fenêtre, ça permet (va savoir pourquoi) d'actualiser 
				//l'affichage du JTree et de faire apparraître le nouveau node.
//				setSize(getSize().width + 1, getSize().height);
//				setSize(getSize().width - 1, getSize().height);
				
				position = null;
				tfName.setText("");
				cbType.setSelectedIndex(-1);
				spEff.setValue(0);
				checkEnableBtn();
			}
		});
		
		
		
		panel.add(btnAjouter);
		
		btnPreview = new JButton("Preview");
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame fr = new JFrame();
				fr.add(new MapPanel(classrooms));
				fr.pack();
				fr.setVisible(true);
				checkEnableBtn();
			}
		});
		panel.add(btnPreview);
		this.initializeTree();
		this.checkEnableBtn();
	}

	private void checkEnableBtn()	{
		this.btnAjouter.setEnabled(this.tfName.getText().length() > 0 && this.cbType.getSelectedItem() != null && this.position != null);
		//this.btnPreview.setEnabled(this.tfName.getText().length() > 0 && this.cbType.getSelectedItem() != null && this.position != null);
	}
	
	protected void setPosition(Point p)	{
		this.position = p;
	}
	
	private class PositionFrame extends JFrame{
		
		Point p; //Attention : Rajouter +2 en X et Y
		ClassroomPanel cp;
		
		protected PositionFrame(final Image img, ClassroomPanel aCp)	{
			super();
			this.cp = aCp;
			JPanel pane = new JPanel();
			//pane.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JLabel lblImage = new JLabel(new ImageIcon(img));
			lblImage.setBorder(new EmptyBorder(23, 23, 23, 23));
			lblImage.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent e) {
					
				}
				
				public void mousePressed(MouseEvent e) {
					p = e.getPoint();
					Graphics g = getContentPane().getGraphics();
					g.drawImage(img, 28, 28, null);
					g.setColor(Color.RED);
					g.drawLine(31, p.y + 2, 31 + img.getWidth(null) - 13, p.y + 2);
					g.drawLine(p.x + 2, 35, p.x + 2, 22 + img.getHeight(null));
				}
				
				public void mouseExited(MouseEvent e) {
					
				}
				
				public void mouseEntered(MouseEvent e) {
					
				}
				
				public void mouseClicked(MouseEvent e) {
					
				}
			});
			
			pane.add(lblImage);
			
			JButton bt = new JButton("Valider");
			bt.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
					cp.setPosition(new Point(p.x + 2, p.y + 2));
					cp.checkEnableBtn();
					setVisible(false);
				}
			});
			
			pane.add(bt);
			getContentPane().add(pane);
			this.pack();
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.setVisible(true);
		}
	}
}
