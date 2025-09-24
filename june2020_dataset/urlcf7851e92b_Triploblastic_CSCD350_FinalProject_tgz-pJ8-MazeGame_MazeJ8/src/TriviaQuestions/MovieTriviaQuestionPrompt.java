package TriviaQuestions;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.TextArea;

import javax.swing.JRadioButton;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import MazeGame.Maze;
import java.awt.Color;
import java.awt.Font;

public class MovieTriviaQuestionPrompt extends JDialog {
	protected JPanel contentPane;
	private JLabel quote;
	private JButton submit; 
	private JRadioButton option1, option2, option3, option4;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private ActionListener radioListener = new RadioButtonListener();
	private ActionListener submitListener = new SubmitListener();
	private String selection = null;
	private boolean submitted = false;
	

	public MovieTriviaQuestionPrompt() {
		super(Maze.mainWindow);
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		
		setBounds(Maze.mainWindow.getBounds());
		contentPane = new JPanel();
		contentPane.setBackground(new Color(95, 158, 160));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		add(contentPane);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setFocusable(true);
		
		quote = new JLabel("New label");
		quote.setForeground(Color.BLACK);
		quote.setFont(new Font("Tekton Pro", Font.PLAIN, 16));
		quote.setBounds(48, 13, 335, 60);
		contentPane.add(quote);
		
		option1 = new JRadioButton("New radio button");
		option1.setFont(new Font("Tekton Pro", Font.PLAIN, 16));
		option1.setForeground(Color.BLACK);
		option1.setBackground(new Color(95, 158, 160));
		buttonGroup.add(option1);
		option1.setBounds(27, 94, 356, 25);
		option1.addActionListener(radioListener);
		contentPane.add(option1);
		
		option2 = new JRadioButton("New radio button");
		option2.setForeground(Color.BLACK);
		option2.setFont(new Font("Tekton Pro", Font.PLAIN, 16));
		option2.setBackground(new Color(95, 158, 160));
		buttonGroup.add(option2);
		option2.setBounds(27, 124, 356, 25);
		option2.addActionListener(radioListener);
		contentPane.add(option2);
		
		option3 = new JRadioButton("New radio button");
		option3.setForeground(Color.BLACK);
		option3.setFont(new Font("Tekton Pro", Font.PLAIN, 16));
		option3.setBackground(new Color(95, 158, 160));
		buttonGroup.add(option3);
		option3.setBounds(27, 154, 356, 25);
		option3.addActionListener(radioListener);
		contentPane.add(option3);
		
		option4 = new JRadioButton("New radio button");
		option4.setForeground(Color.BLACK);
		option4.setFont(new Font("Tekton Pro", Font.PLAIN, 16));
		option4.setBackground(new Color(95, 158, 160));
		buttonGroup.add(option4);
		option4.setBounds(27, 184, 356, 25);
		option4.addActionListener(radioListener);
		contentPane.add(option4);
		
		submit = new JButton("Submit");
		submit.setFont(new Font("Tekton Pro", Font.PLAIN, 16));
		submit.setBackground(Color.PINK);
		submit.setBounds(163, 249, 97, 25);
		submit.addActionListener(submitListener);
		contentPane.add(submit);
		
		new MovingTogether(Maze.mainWindow, this);
	}

	/*getters*/
	public JButton getSubmit() {return this.submit;}
	
	/*setters*/
	public void setPrompt(String prompt) {
		this.quote.setText("<html>" + prompt + "</html>");
	}
	public void setRadioButtons(String[] answerSet) {
		option1.setText(answerSet[0]);
		option2.setText(answerSet[1]);
		option3.setText(answerSet[2]);
		option4.setText(answerSet[3]);
	}
	
	public JPanel getContentPane(){
		return contentPane;
	}
	
	public String getSelection() {return this.selection;}
	
	public boolean isSubmitted() {return this.submitted;}
	
	class SubmitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			submitted = true;
		}
	}
	

	//new MovingTogether(jFrame1, jDialog1); ("jFrame1" is a JFrame
	//and "jDialog1" is a non-modal JDialog with jFrame1 as parent)
	class MovingTogether extends ComponentAdapter{
	    public MovingTogether(JFrame winA, JDialog winB){
	        this.winA = winA;
	        this.winB = winB;
	        winA.addComponentListener(this);
	        winB.addComponentListener(this);
	    }
	    public void componentMoved(ComponentEvent e) {
	        Window win = (Window) e.getComponent();
	        if(win==winA){
	            winB.removeComponentListener(this);
	            winB.setLocationRelativeTo(winA);
	            winB.addComponentListener(this);
	        } else if(winB.isVisible())  winA.setLocationRelativeTo(winB);
	    }
	    private Window winA, winB;
	}

	
	class RadioButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == option1) 
				selection = option1.getText();
			if (e.getSource() == option2) 
				selection = option2.getText();
			if (e.getSource() == option3) 
				selection = option3.getText();
			if (e.getSource() == option4) 
				selection = option4.getText();
			System.out.print(selection);
		}	
	}
}
