package ch.zhaw.regularLanguages.evolution.runners;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ch.zhaw.regularLanguages.evolution.initialisation.EvolvingGlobalProblemSetInitialisation;
import ch.zhaw.regularLanguages.helpers.Logger;

public class EvolvingGlobalDivisable3LanguageCSScaling {

	public static void main(String[] args) {
		EvolvingGlobalProblemSetInitialisation starter = new EvolvingGlobalProblemSetInitialisation();
		starter.initLanguage(new char[] { '0', '1' }, 10, "(1(01*0)*1|0)*");

		int solutionFoundCounter = 0;
		int noSolutionFound = 0;
		List<Long> cycleCount = new LinkedList<Long>();
		long tmpCycle;
		long timeStamp;

		int[] problemCount = new int[25];
		int[] candidatesCount = new int[1];
		int[] noCycles = new int[2];

		problemCount[0] = 3;
		problemCount[1] = 6;
		problemCount[2] = 9;
		problemCount[3] = 12;
		problemCount[4] = 15;
		problemCount[5] = 18;
		problemCount[6] = 21;
		problemCount[7] = 24;
		problemCount[8] = 27;
		problemCount[9] = 30;
		problemCount[10] = 33;
		problemCount[11] = 36;
		problemCount[12] = 39;
		problemCount[13] = 42;
		problemCount[14] = 45;
		problemCount[15] = 48;
		problemCount[16] = 51;
		problemCount[17] = 54;
		problemCount[18] = 57;
		problemCount[19] = 60;
		problemCount[20] = 63;
		problemCount[21] = 66;
		problemCount[22] = 69;
		problemCount[23] = 72;
		problemCount[24] = 75;

		candidatesCount[0] = 50;

		noCycles[0] = 250;
		noCycles[1] = 500;

		int pc = 0;
		int cc = 0;
		int nc = 0;

		for (int x = 0; x < 1; x++) {
			System.out.println("x:" + x);
			for (int n = 0; n < 25; n++) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");

				Logger l = new Logger("E_G_PS_" + df.format(new Date()) + ".log",
						true);

				pc = problemCount[n];
				cc = candidatesCount[0];
				nc = noCycles[1];

				l.log("Problem Count: " + pc);
				l.log("CandidatesCount: " + cc);
				l.log("Max Cycles: " + nc);

				solutionFoundCounter = 0;
				noSolutionFound = 0;
				cycleCount = new LinkedList<Long>();

				for (int i = 0; i < 100; i++) {
					timeStamp = System.currentTimeMillis();

					starter.initProblems(pc);
					starter.initCandidates(cc);
					tmpCycle = starter.startEvolution(nc);

					l.log(i + ": finished ("
							+ (System.currentTimeMillis() - timeStamp) + "ms, "
							+ tmpCycle + "cycles)");

					if (starter.getWinner() != null) {
						solutionFoundCounter++;
						cycleCount.add(tmpCycle);
						l.log(i + ": Solution found.");
						// GraphvizRenderer.renderGraph(starter.getWinner().getObj(),
						// "winner.svg");
					} else {
						noSolutionFound++;
						l.log(i + ": No solution found.");
					}
				}

				long max = 0;
				long min = 10000;
				long sum = 0;
				for (long no : cycleCount) {
					sum += no;
					max = (no > max ? no : max);
					min = (no < min ? no : min);
				}

				l.log("Solution Found: " + solutionFoundCounter);
				l.log("Avg cycles: "
						+ (cycleCount.size() > 0 ? sum / cycleCount.size()
								: '0'));
				l.log("Max cycles: " + max);
				l.log("Min cycles: " + min);
				l.log("No solution found: " + noSolutionFound);
				l.finish();
			}
		}
	}
}