package GameOverLogic;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import java.awt.Color;

public class HighScoresWindow extends JDialog implements ActionListener{
	private JTextPane textPane;
	private GameOverObserver parent;
	
	public HighScoresWindow() {
		setTitle("High Scores");
		this.setUndecorated(true);
		textPane = new JTextPane();
		textPane.setBackground(Color.PINK);
		textPane.setEditable(false);
		textPane.setText(highScores);
		getContentPane().add(textPane, BorderLayout.CENTER);
		
		JButton btnNewButton = new JButton("Clear High Scores");
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.setToolTipText("Do it");
		btnNewButton.addActionListener(this);
		getContentPane().add(btnNewButton, BorderLayout.SOUTH);
	}
	private String highScores;
	
	public void setData(String highScores, GameOverObserver parent){
		this.highScores = highScores;
		this.parent = parent;
		textPane.setText(this.highScores);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		textPane.setText("");
		parent.ClearHighScores();
	}
		
	
}
