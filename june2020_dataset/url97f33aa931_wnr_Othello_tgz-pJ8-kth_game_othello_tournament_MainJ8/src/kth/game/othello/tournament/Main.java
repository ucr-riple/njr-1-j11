package kth.game.othello.tournament;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import kth.game.othello.player.movestrategy.*;

/**
 * Start point for the Tournament Othello game module.
 */
public class Main {
	public static void main(String[] args) {
		// Simple reading of command line arguments.
		// Should probably be done with a third party tool if we want to extends it
		boolean showView = false;
		for (String arg : args) {
			if (arg.equals("-g") || arg.equals("-gui")) {
				showView = true;
			}
		}

		List<MoveStrategy> strategies = new ArrayList<>();
		strategies.add(new GreedyStrategy());
		strategies.add(new RandomStrategy());
		strategies.add(new LowestStrategy());
		strategies.add(new TopLeftStrategy());

		System.out.println("Starting tournament...");
		Tournament tournament = new Tournament(strategies);
		List<PlayerResult> results = tournament.play(showView);
		System.out.println("Tournament done. Results:");
		new TournamentPrinter(new PrintWriter(System.out)).printResults(results);
	}
}
