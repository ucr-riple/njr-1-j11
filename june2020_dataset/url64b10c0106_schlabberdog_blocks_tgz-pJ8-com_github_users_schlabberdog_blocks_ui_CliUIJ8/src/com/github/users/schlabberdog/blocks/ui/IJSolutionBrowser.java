package com.github.users.schlabberdog.blocks.ui;

import com.github.users.schlabberdog.blocks.board.Board;
import com.github.users.schlabberdog.blocks.board.BoardSave;
import com.github.users.schlabberdog.blocks.board.moves.IMove;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class IJSolutionBrowser {
	private JButton firstStepButton;
	private JButton backButton;
	private JButton nextButton;
	private JButton lastStepButton;
	private BoardView boardView;
	private JLabel curStepLabel;
	private JLabel stepCountLabel;
	private JPanel formRoot;

	private int step = -1;

	private final Board board;
	private final BoardSave initialState;
	private final List<IMove> steps;

	public static IJSolutionBrowser Create(Board board,BoardSave initialState,List<IMove> steps) {
		IJSolutionBrowser browser = new IJSolutionBrowser(board,initialState,steps);

		JFrame frame = new JFrame("IJSolutionBrowser");
		frame.setContentPane(browser.formRoot);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		browser.validateUI();

		frame.setVisible(true);

		return browser;
	}

	private IJSolutionBrowser(Board board,BoardSave initialState,List<IMove> steps) {
		this.board = board;
		this.initialState = initialState;
		this.steps = steps;

		lastStepButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				gotoLastStep();
			}
		});
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				gotoNextStep();
			}
		});
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				gotoPreviousStep();
			}
		});
		firstStepButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				gotoFirstStep();
			}
		});
	}

	private void validateUI() {
		boolean canGoBack    = (step >= 0);
		boolean canGoForward = (step < steps.size()-1);

		firstStepButton.setEnabled(canGoBack);
		backButton.setEnabled(canGoBack);
		nextButton.setEnabled(canGoForward);
		lastStepButton.setEnabled(canGoForward);

		stepCountLabel.setText(String.valueOf(steps.size()));
		curStepLabel.setText(String.valueOf(step+1));

		boardView.repaint();
	}

	private void updateBoard(int newStep) {
		//keine änderung
		if(newStep == step)
			return;

		int startFrom = step+1;
		//wenn der neue step vor dem aktuellen liegt müssen wir das board bis dort hin neu abspielen
		if(newStep < step) {
			board.applySave(initialState);
			startFrom = 0;
		}

		//zwischenschritte anwenden
		for (int i = startFrom; i <= newStep; i++) {
			board.applyMove(steps.get(i));
		}

		step = newStep;
	}

	private void gotoFirstStep() {
		updateBoard(-1);
		validateUI();
	}

	private void gotoPreviousStep() {
		updateBoard(step-1);
		validateUI();
	}

	private void gotoNextStep() {
		updateBoard(step+1);
		validateUI();
	}

	private void gotoLastStep() {
		updateBoard((steps.size()-1));
		validateUI();
	}

	private void createUIComponents() {
		//zu aller erst müssen wir das board zurückspulen...weiß ja niemand wie das grade aussieht
		board.applySave(initialState);

		boardView = new BoardView(board);
	}


}
