package securitygame;

import java.util.ArrayList;

/**
 * Pits Attacker and Defender agents against one another in the name of Science!
 * 
 * STUDENTS: add your defenders and attackers to the sections in main that say
 * "add defenders here" and "add attackers here" Also add your defender to the
 * method getDefenderByName() and your attacker to getAttackerByName() You may
 * also edit the rates in the Parameters class. Trust that these rates will be
 * changed when the full tournament is run.
 * 
 * @author Oscar Veliz, Porag Chowdhury, Anjon Basak, Marcus Gutierrez
 * @version 2014/11/14
 */
public class GameMaster {
	/**
	 * Runs the tournament
	 * 
	 * @param args not using any command line arguments
	 */
	public static void main(String[] args) {
		int numGames = 20;
		generateGraphs(numGames);

		// add Defenders here
		ArrayList<Defender> defenders = new ArrayList<Defender>();
		defenders.add(new WhatDoesThisButtonDoDefender("0"));
		defenders.add(new Strengthener("0"));
		defenders.add(new NumbDefender("0"));
		defenders.add(new RationalDefender("0"));

		// get names of defenders
		String[] defenderNames = new String[defenders.size()];
		for (int i = 0; i < defenders.size(); i++)
			defenderNames[i] = defenders.get(i).getName();
		int numDefenders = defenderNames.length;
		// execute defenders
		for (int d = 0; d < numDefenders; d++) {
			for (int g = 0; g < numGames; g++) {
				Defender defender = getDefender(defenderNames[d], g + "");
				new Thread(defender).start();
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				defender.kill();
				new DefenderHelper(defender.getName(), defender.getGraph());
			}
		}

		// add Attackers here
		ArrayList<Attacker> attackers = new ArrayList<Attacker>();
		attackers.add(new Blitzkrieg());
		attackers.add(new GamblingAttacker());
		attackers.add(new CautiousAttacker());
		attackers.add(new GreedyAttacker());

		// get names of attackers
		String[] attackerNames = new String[attackers.size()];
		for (int i = 0; i < attackers.size(); i++)
			attackerNames[i] = attackers.get(i).getName();
		int numAttackers = attackerNames.length;
		// initialize point matrix
		int[][] points = new int[numDefenders][numAttackers];

		// execute attackers
		for (int d = 0; d < numDefenders; d++) {
			String defenderName = defenderNames[d];
			for (int a = 0; a < numAttackers; a++) {
				String attackerName = attackerNames[a];
				for (int g = 0; g < numGames; g++) {
					String graphName = g + "";
					AttackerMonitor am = new AttackerMonitor(attackerName,
							defenderName, graphName);
					while (am.getBudget() > 0) {
						Attacker attacker = getAttacker(defenderName,
								attackerName, graphName);
						new Thread(attacker).start();
						try {
							Thread.sleep(500);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						attacker.kill();
						am.readMove();
						System.out.println("Budget after move: "+ am.getBudget());
						System.out.println();
					}
					am.close();
					points[d][a] += am.getPoints();
				}
			}
		}
		// perform analysis
		new Analyzer(points, attackerNames, defenderNames);
	}

	/**
	 * Generates graphs
	 * 
	 * @param numGraphs
	 *            the number of graphs to generate
	 */
	public static void generateGraphs(int numGraphs) {
		for (int i = 0; i < numGraphs; i++) {
			Network n = new Network(i);
			n.printNetwork();
		}
	}

	/**
	 * You should edit this method to include your defender
	 * 
	 * @param name
	 *            name of defender
	 * @param file
	 *            graph defender will read
	 * @return your defender
	 */
	public static Defender getDefender(String name, String file) {
		if (name.equalsIgnoreCase("WDTBD"))
			return new WhatDoesThisButtonDoDefender(file);
		if (name.equalsIgnoreCase("Strengthener"))
			return new Strengthener(file);
		if (name.equalsIgnoreCase("NumbDefender"))
			return new NumbDefender(file);
		if (name.equalsIgnoreCase("RationalDefender"))
			return new RationalDefender(file);
		// add your defender

		// invalid defender if name could not be found
		return new Defender("", "") {
			@Override
			public void makeMoves() {
				System.out.print("check name");
			}
		};
	}

	/**
	 * You should edit this method to include your attacker
	 * 
	 * @param defName
	 *            name of defender attacker will be pit against
	 * @param atName
	 *            name of defender
	 * @param file
	 *            graph defender will attack
	 * @return your attacker
	 */
	public static Attacker getAttacker(String defName, String atName,
			String file) {
		if (atName.equalsIgnoreCase("Blitzkrieg"))
			return new Blitzkrieg(defName, file);
		if (atName.equalsIgnoreCase("GamblingAttacker"))
			return new GamblingAttacker(defName, file);
		if (atName.equalsIgnoreCase("CautiousAttacker"))
			return new CautiousAttacker(defName, file);
		if (atName.equalsIgnoreCase("GreedyAttacker"))
			return new GreedyAttacker(defName, file);

		// add your attacker here

		// in case your name was not added
		return new Attacker("", "", "") {
			@Override
			public AttackerAction makeSingleAction() {
				System.out.println("check attacker name");
				return null;
			}

			@Override
			protected void initialize() {
			}
		};

	}
}
