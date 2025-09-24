package kth.game.othello.tournament;

import java.io.PrintWriter;
import java.util.List;

/**
 * A simple printer that prints the results of a tournament in a table format
 */
public class TournamentPrinter {

	private PrintWriter out;

	/**
	 * Create a printer printing to the specified output stream
	 * 
	 * @param out The stream to print to
	 */
	public TournamentPrinter(PrintWriter out) {
		this.out = out;
	}

	/**
	 * Print the given tournament results to the output stream
	 * 
	 * @param results The results to print
	 */
	public void printResults(List<PlayerResult> results) {
		int place = 1;
		out.println("\tName\tW\tL\tD");
		for (int i = 0; i < results.size(); i++) {
			PlayerResult pr = results.get(i);
			if (i == 0 || !pr.isScoreEqual(results.get(i - 1))) {
				place = i + 1;
			}
			out.print(place + ".\t");
			out.print(pr.getName() + "\t");
			out.print(pr.getWins() + "\t");
			out.print(pr.getLosses() + "\t");
			out.println(pr.getDraws() + "\t");
		}
		out.flush();
	}

}
