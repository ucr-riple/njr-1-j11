package testbed;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import hex.Hex;
import terrain.TerrainEnum;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class bbmsGUI {
	
	private static final int GUI_XSIZE = 1024;
	private static final int GUI_YSIZE = 800;
	
	static JFrame MainGUIframe;
	
	public static void main(String[] args) {		
        JWindowLook();				// Creates a "Windows" look and feel to the windows            
        GuiMainWindow();
        
		System.out.println("Welcome to the Bare Bones Military Simulator (BBMS) GUI\n");			
	}
	
	static ActionListener FileNewListener = new ActionListener( ) {
		public void actionPerformed(ActionEvent event) {
			System.out.println("File - New");
		}
	};
	
	static ActionListener FileExitListener = new ActionListener( ) {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	};
	
	static ActionListener Test7Listener = new ActionListener( ) {
		public void actionPerformed(ActionEvent event) {
			test7();
		}
	};
	
	static ActionListener Test8Listener = new ActionListener( ) {
		public void actionPerformed(ActionEvent event) {
			test8();
		}
	};
	
	static ActionListener HelpAboutListener = new ActionListener( ) {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Bare Bones Military Simulator v0.0");
		}
	};	

	public static void GuiMainWindow() {
		JMenuItem menuItem;
		JMenu menu;
		
		MainGUIframe = new JFrame ("Bare Bones Military Simulator (BBMS)");
		JMenuBar MainGUIMenu = new JMenuBar();
		
		// File Menu
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);			
		MainGUIMenu.add(menu);
		
		menuItem = new JMenuItem("New", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(FileNewListener);
		menu.add(menuItem);
		
		menu.addSeparator();
		
		menuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(FileExitListener);
		menu.add(menuItem);
		
		
		// Test Menu
		menu = new JMenu("Test");
		menu.setMnemonic(KeyEvent.VK_T);			
		MainGUIMenu.add(menu);
		
		menuItem = new JMenuItem("Run Test 7 (Map Init)", KeyEvent.VK_7);		
		menuItem.addActionListener(Test7Listener);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Run Test 8 (Mouse Debug Info)", KeyEvent.VK_8);		
		menuItem.addActionListener(Test8Listener);
		menu.add(menuItem); 

		
		// Help Menu
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);			
		MainGUIMenu.add(menu);
		
		menuItem = new JMenuItem("About", KeyEvent.VK_A);		
		menuItem.addActionListener(HelpAboutListener);
		menu.add(menuItem);
		
		
		MainGUIframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		MainGUIframe.setSize(GUI_XSIZE, GUI_YSIZE);
		MainGUIframe.add(MainGUIMenu);
		MainGUIframe.setJMenuBar(MainGUIMenu);
		MainGUIframe.setResizable(false);
		MainGUIframe.setVisible(true);
	}

	
    public static void JWindowLook() {
        // Sets the windows to have a "Windows" look and feel to them.
        try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        
        }
        catch (Exception e) {
            System.err.println("ERROR: Cannot load Windows-like displays.");
            e.printStackTrace();
        }
    }
    
	public static void test7() {
		int xDim = 4;
		int yDim = 4;
		
		for (int y = 0; y < yDim; y++) {
			for (int x = 0; x < xDim; x++) {
				TerrainEnum tType = TerrainEnum.T_GRASS;
				Hex tHex = new Hex(x, y, tType, 0);
				tHex.DisplayInfo();
			}
		}
	}
	
	// Tests mouse listener events
	public static void test8() {
		MouseListener mListener = new MouseListener() {
			int mouseX = 0;
			int mouseY = 0;
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Click at " + mouseX + ", " + mouseY);
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		};
	
	}
}