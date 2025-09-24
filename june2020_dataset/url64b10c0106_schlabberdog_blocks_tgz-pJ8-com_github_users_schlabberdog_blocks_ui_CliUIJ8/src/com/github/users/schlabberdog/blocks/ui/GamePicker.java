package com.github.users.schlabberdog.blocks.ui;

import com.github.users.schlabberdog.blocks.board.Board;
import com.github.users.schlabberdog.blocks.r010.R010Game;
import com.github.users.schlabberdog.blocks.solver.Solver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ServiceLoader;

public class GamePicker {
    private JPanel rootPanel;
    private JComboBox gameDropdown;
    private JButton newGameButton;

    private static class GameDropdownItem {
        final IGame game;

        GameDropdownItem(IGame game) {
            this.game = game;
        }

        @Override
        public String toString() {
            return game.getTitle();
        }
    }


    public GamePicker() {
        ServiceLoader<IGame> sl = ServiceLoader.load(IGame.class);
        for (IGame game : sl) {
            gameDropdown.addItem(new GameDropdownItem(game));
        }

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createGame();
            }
        });
    }

    private void createGame() {
        GameDropdownItem item = (GameDropdownItem) gameDropdown.getSelectedItem();

		if(item == null)
			return;

        IGame game = item.game;
        Board board = game.getBoard();

        Solver solver = new Solver(board,game.getChecker());

        IJGUI.Create(board,solver);
    }

    public static void main(String[] args) {
        try {
			//macht auch nix wenn das fehlschlägt..
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame("GamePicker");
        frame.setContentPane(new GamePicker().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		frame.setResizable(false);
        frame.setVisible(true);
    }
}
