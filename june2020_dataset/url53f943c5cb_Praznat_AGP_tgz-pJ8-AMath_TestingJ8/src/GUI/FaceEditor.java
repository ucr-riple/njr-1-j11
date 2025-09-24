package GUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import Game.AGPmain;

public class FaceEditor implements ChangeListener, ActionListener {
		static final int WIDTH = 600;
		static final int HEIGHT = 380;
		static final int CSIZE = 350;
		
		static final Color BGColor = new Color(32,32,32);
		static final Color FGColor = new Color(223,223,223);

		JFrame MainPane;
		EditableFace chart1;
		JPanel panel1, panel2, panel3, panel4;
		JLabel skinRL, skinGL, skinBL, hairlenL, haircurL, hairthiL, hairRL, hairGL, hairBL, hairchaoL, hairCCWL;
		JSlider skinRS, skinGS, skinBS, hairlenS, haircurS, hairthiS, hairRS, hairGS, hairBS, hairchaoS, hairCCWS;
		JButton maleB, femaleB, reshuffleB, recombB;
		
		public static final int skinR = 0;
		public static final int skinG = 1;
		public static final int skinB = 2;
		public static final int hairlen = 3;
		public static final int haircur = 4;
		public static final int hairthi = 5;
		public static final int hairR = 6;
		public static final int hairG = 7;
		public static final int hairB = 8;
		public static final int hairchao = 9;
		public static final int hairCCW = 10;
		JLabel[] labels;  ASlider[] sliders;
		boolean resettingSliders;
		
		public FaceEditor() {
			MainPane = new JFrame("1");
			panel1 = new JPanel();
			panel2 = new JPanel();
			panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS)); //new GridLayout(24, 1));
			panel3 = new JPanel(new GridLayout(1, 2));
			panel4 = new JPanel(new GridLayout(1, 2));
			chart1 = new EditableFace();    
			chart1.setPreferredSize(new Dimension(CSIZE, 0));
			panel1.setPreferredSize(new Dimension(4, 0));
			chart1.setBackground(BGColor); panel1.setBackground(BGColor); panel2.setBackground(BGColor);
			
			labels = new JLabel[] {new JLabel("SRed"),new JLabel("SGreen"),new JLabel("SBlue"),new JLabel("HRadius"),new JLabel("HCurl"),new JLabel("HBody"),new JLabel("HRed"),new JLabel("HGreen"),new JLabel("HBlue"),new JLabel("HChaos"),new JLabel("HSway")};
			resettingSliders = false;
			sliders = new ASlider[labels.length];
			for(int i = 0; i < sliders.length; i++) {
				sliders[i] = new ASlider(0, 100);
			}
			reshuffleB = new JButton("New Goblin"); reshuffleB.setActionCommand("reshuffle"); reshuffleB.addActionListener(this);
			recombB = new JButton("Ruffle Hair"); recombB.setActionCommand("recomb"); recombB.addActionListener(this);
			maleB = new JButton("Male"); maleB.setActionCommand("Male"); maleB.addActionListener(this);
			femaleB = new JButton("Female"); femaleB.setActionCommand("Female"); femaleB.addActionListener(this);
			
			MainPane.setSize(WIDTH,HEIGHT);
			//MainPane.getContentPane().add(chart1);
			MainPane.getContentPane().add(chart1, BorderLayout.WEST);
			MainPane.getContentPane().add(panel1, BorderLayout.CENTER);
			MainPane.getContentPane().add(panel2, BorderLayout.EAST);
			for (int i = 0; i < labels.length; i++) {
				JPanel tmp = new JPanel();
				tmp.setBackground(BGColor);
				labels[i].setForeground(FGColor);
				tmp.add(labels[i]);
				sliders[i].setPreferredSize(new Dimension(150, 15));
				sliders[i].setID(i);
				sliders[i].addChangeListener(this);
				sliders[i].setBackground(BGColor);
				tmp.add(sliders[i]);
				panel2.add(tmp);
			}
			panel2.add(panel3);
			panel2.add(panel4);
			panel3.add(maleB, BorderLayout.WEST);
			panel3.add(femaleB, BorderLayout.EAST);
			panel4.add(reshuffleB, BorderLayout.WEST);
			panel4.add(recombB, BorderLayout.EAST);
			MainPane.setVisible(true);

	    	chart1.redefine(AGPmain.mainGUI.AC.getAvatar());
	    	resetSliders();
		}
		
		public void resetSliders() {
			resettingSliders = true;
			sliders[skinR].setValue((chart1.getR()-chart1.getG())*2*99/(241-chart1.getG()));
			sliders[skinG].setValue(chart1.getG());
			sliders[skinB].setValue(chart1.getB());
			sliders[hairlen].setValue(chart1.headhair.getLength());
			sliders[haircur].setValue(chart1.headhair.getCurl());
			sliders[hairthi].setValue(chart1.headhair.getThickness());
			sliders[hairR].setValue(chart1.headhair.getR());
			sliders[hairG].setValue(chart1.headhair.getG());
			sliders[hairB].setValue(chart1.headhair.getB());
			sliders[hairchao].setValue(chart1.headhair.getChaos());
			sliders[hairCCW].setValue(chart1.headhair.getCCWN());
			resettingSliders = false;
		}
		public void stateChanged(ChangeEvent e) {
		    ASlider source = (ASlider)e.getSource();
		    if (!resettingSliders) {
		        int val = (int)source.getValue();
		        switch (source.getID()) {
			        case skinR: chart1.SKINR.setPctInRange(sliders[skinR].getValue()); chart1.setSkinColor(); break;
			        case skinG: chart1.SKING.setPctInRange(sliders[skinG].getValue()); chart1.setSkinColor(); break;
			        case skinB: chart1.SKINB.setPctInRange(sliders[skinB].getValue()); chart1.setSkinColor(); break;
			        case hairlen: chart1.HAIRL.setPctInRange(val); break;
			        case haircur: chart1.HAIRC.setPctInRange(val); break;
			        case hairthi: chart1.HAIRW.setPctInRange(val); break;
			        case hairR: chart1.HAIRR.setPctInRange(sliders[hairR].getValue()); chart1.setHairColor(); break;
			        case hairG: chart1.HAIRG.setPctInRange(sliders[hairG].getValue()); chart1.setHairColor(); break;
			        case hairB: chart1.HAIRB.setPctInRange(sliders[hairB].getValue()); chart1.setHairColor(); break;
			        case hairchao: chart1.HAIRX.setPctInRange(val); break;
			        case hairCCW: chart1.HAIRS.setPctInRange(val); break;
			        default: break;
		        }
		    }
		    chart1.paintFace();
		}
		
		public void actionPerformed(ActionEvent e) {
		    if ("reshuffle".equals(e.getActionCommand())) {
		    }
		    else if ("recomb".equals(e.getActionCommand())) {
		    	AGPmain.rand.nextBytes(chart1.rands);
		    }
		    else if ("Male".equals(e.getActionCommand())) {
		    	chart1.setFemale(false);
		    }
		    else if ("Female".equals(e.getActionCommand())) {
		    	chart1.setFemale(true);
		    }
		    chart1.paintFace();
		} 


		public Color setColor(Color C, int R, int G, int B) {
			int red = (R>=0? R : C.getRed());
			int green = (G>=0? G : C.getGreen());
			int blue = (B>=0? B : C.getBlue());
			//System.out.println("RED:"+red+" GREEN:"+green+" BLUE:"+blue);
			return new Color(red, green, blue);
		}

	}

	class ASlider extends JSlider {
		public int id;
		public ASlider(int a, int b) {super(a, b);}
		public void setID(int i) {id = i;}
		public int getID() {return id;}
	}

