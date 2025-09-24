package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import algorithms.Algorithm;
import algorithms.ApproximationAlgorithm;
import algorithms.SIM;
import algorithms.SPT;
import dataStructure.AdjList;
import dataStructure.PositionTable;
import dataStructure.Weight;

public class Graph {
	public static final double[] SPANNER_FACTORS = {1, 1.25, 1.5, 1.75, 2};
	public static final int FIRST_NET_SIZE = 60;
	public static final int FIRST_TERMINAL_SIZE = 10;
	public static final int INC_NET_SIZE = 40;
	public static final int INC_TERMINAL_SIZE = 10;
	public static final int NUMBER_OF_NET_SIZES = 5;
	public static final int NUMBER_OF_TERMINAL_SET_SIZES = 5;
	public static final int SOURCE_ID = 5;
	public static final int NUMBER_OF_RUNS_SIZES = 30;
	public static final int FIELD_X = 500;
	public static final int FIELD_Y = 500;
	
	
	public static int[] getNetSizes() {
		int size, sizes[] = new int[Graph.NUMBER_OF_NET_SIZES];
		size = Graph.FIRST_NET_SIZE;
		for (int i = 0; i < Graph.NUMBER_OF_NET_SIZES; i++) {
			sizes[i] = size;
			size += Graph.INC_NET_SIZE;
		}
		return sizes;
	}
	
	public static int[] getSetSizes(int qtSizes, int firstSize, int incSize) {
		int size, sizes[] = new int[qtSizes];
		size = firstSize;
		for (int i = 0; i < qtSizes; i++) {
			sizes[i] = size;
			size += incSize;
		}
		return sizes;
	}
	
    /**
     * @param args
     */
    public static void main (String[] args) {
    	Date start = new Date();
    	int netSizes[] = Graph.getSetSizes(Graph.NUMBER_OF_NET_SIZES, Graph.FIRST_NET_SIZE, Graph.INC_NET_SIZE);
		int terminalSizes[] = Graph.getSetSizes(Graph.NUMBER_OF_TERMINAL_SET_SIZES, Graph.FIRST_TERMINAL_SIZE, Graph.INC_TERMINAL_SIZE);
		String dir = "";
		String outDegreeFile = "grau.dat";
		String violatedTerminalsCVRFile = "newViolatedTerminalsCVR.dat";
		String violatedNodesRatioFile = "newViolatedNodesRatio.dat";
		String violatedRunsRatioFile = "violatedRunsRatio.dat";
		String percCovTerFile = "percCovTer.dat";
		String algoName = "ApproxAlgorithm-";
		//String algoName = "SIM-";
		
		Date d = new Date();
		String tempFile = "tempFile-"+d.getTime()+".dat";
		Random random = new Random();    	
    	
    	
    	int sourceId = Graph.SOURCE_ID;
    	
    	int terminalsId[];// = new int[Graph.NUMBER_OF_TERMINALS];
    	double delaysTerm[];// = new double[Graph.NUMBER_OF_TERMINALS];
    	
    	double degree[][][] = new double[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	double violatedTerminalsCVR[][][] = new double[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	double minOfMeanViolatedTerminalsCVR[][][] = new double[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	double maxOfMeanViolatedTerminalsCVR[][][] = new double[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	double maxOfMaximumViolatedTerminalsCVR[][][] = new double[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	double meanOfMaximumViolatedTerminalsCVR[][][] = new double[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	int violatedTerminalsCVRRUNSVector[][][] = new int[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	double spCosts[][][] = new double[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	double terCoveredRatio[][][] = new double[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	int terCoveredRatioRUNSVector[][][] = new int[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	double violatedNodesRatio[][][] = new double[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	int violatedNodesRatioRUNSVector[][][] = new int[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	double violatedRunsRatio[][][] = new double[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	double violatedRunsRatioRUNSVector[][][] = new double[netSizes.length][terminalSizes.length][Graph.SPANNER_FACTORS.length];
    	
    	double degreeSPT[][] = new double[netSizes.length][terminalSizes.length];
    	double costsSPT[][] = new double[netSizes.length][terminalSizes.length];    	
		
		try {
			
			PrintWriter degreeOut = new PrintWriter(new BufferedWriter(new FileWriter(dir+algoName+outDegreeFile)));
			PrintWriter violatedTerminalsCVRFileOut = new PrintWriter(new BufferedWriter(new FileWriter(dir+algoName+violatedTerminalsCVRFile)));
			PrintWriter violatedNodesRatioOut = new PrintWriter(new BufferedWriter(new FileWriter(dir+algoName+violatedNodesRatioFile)));
			PrintWriter violatedRunsRatioOut = new PrintWriter(new BufferedWriter(new FileWriter(dir+algoName+violatedRunsRatioFile)));
			PrintWriter percCovTerOut = new PrintWriter(new BufferedWriter(new FileWriter(dir+algoName+percCovTerFile)));			
			
			for (int i = 0; i < netSizes.length; i++) {
				for (int t = 0; t < terminalSizes.length; t++) {
					terminalsId = new int[terminalSizes[t]];
					delaysTerm = new double[terminalSizes[t]];
					int qtTerm = 0;
					ArrayList<Integer> listTerm = new ArrayList<Integer>();
					for (int id = 0; qtTerm < terminalSizes[t]; id++) {
						random.setSeed(id);
						int terId = random.nextInt(netSizes[i]);
						if ((terId != sourceId) && (!listTerm.contains(terId))) {
							terminalsId[qtTerm] = terId;
							delaysTerm[qtTerm] = 300 + random.nextInt(100);
							qtTerm++;
							listTerm.add(terId);
						}
					}
					
					for (int p = 0; p < Graph.SPANNER_FACTORS.length; p++) {
						degree[i][t][p] = 0;
						violatedTerminalsCVR[i][t][p] = 0;
						minOfMeanViolatedTerminalsCVR[i][t][p] = 0;
						maxOfMeanViolatedTerminalsCVR[i][t][p] = 0;
						maxOfMaximumViolatedTerminalsCVR[i][t][p] = 0;
						meanOfMaximumViolatedTerminalsCVR[i][t][p] = 0;
						spCosts[i][t][p] = 0;
						terCoveredRatio[i][t][p] = 0;
						violatedNodesRatio[i][t][p] = 0;
						violatedRunsRatio[i][t][p] = 0;
						
						violatedRunsRatioRUNSVector[i][t][p] = 0;
						violatedTerminalsCVRRUNSVector[i][t][p] = 0;
						violatedNodesRatioRUNSVector[i][t][p] = 0;
						terCoveredRatioRUNSVector[i][t][p] = 0;
					}
					
					degreeSPT[i][t] = 0;
					costsSPT[i][t] = 0;					
					
				int seed = 0;
				for (int j = 0; j < Graph.NUMBER_OF_RUNS_SIZES; j++, seed++) {
					System.out.println("size: "+netSizes[i]+", ter: "+terminalSizes[t]+", run: "+j);
					random.setSeed(seed);
					///*
					PrintWriter topo = null;

							topo = new PrintWriter(new BufferedWriter(new FileWriter(dir+tempFile)));
						for (int a = 0; a < netSizes[i]; a++) {
							double x = random.nextDouble()*Graph.FIELD_X;
							topo.println(a+" set X_ "+x);
							double y = random.nextDouble()*Graph.FIELD_Y;
							topo.println(a+" set Y_ "+y);
							topo.println("--");
							topo.println("--");
							topo.println();
						}
						topo.close();

												
						PositionTable pt = new PositionTable(netSizes[i]);
						pt.readFromTopologyFile(dir+tempFile);
						Weight w = new Weight (50, 50, 0.1, 2, pt);
						Algorithm algo = new ApproximationAlgorithm(netSizes[i], 125.6, sourceId, terminalsId, delaysTerm, pt, w);
						//Algorithm algo = new SIM(netSizes[i], 125.6, sourceId, terminalsId, delaysTerm, pt, w);
						if (algo.pseudoConstructor()) {
							
							for (int p = 0; p < Graph.SPANNER_FACTORS.length; p++) {
								
								algo.execute(Graph.SPANNER_FACTORS[p]);
								
								double minCVR[] = new double[2];
								double maxCVR[] = new double[2];
								
								degree[i][t][p] += algo.getArbMaxOutDegree();
								
								double val = algo.getViolatedTerminalsCVR(minCVR, maxCVR);
								if (val > 0) {
									violatedTerminalsCVR[i][t][p] += val;
									violatedTerminalsCVRRUNSVector[i][t][p]++;

									if (val > maxOfMeanViolatedTerminalsCVR[i][t][p])
										maxOfMeanViolatedTerminalsCVR[i][t][p] = val;
									if ((val < minOfMeanViolatedTerminalsCVR[i][t][p]) || (minOfMeanViolatedTerminalsCVR[i][t][p] == 0))
										minOfMeanViolatedTerminalsCVR[i][t][p] = val;

									if (maxCVR[0] > maxOfMaximumViolatedTerminalsCVR[i][t][p])
										maxOfMaximumViolatedTerminalsCVR[i][t][p] = maxCVR[0];
									meanOfMaximumViolatedTerminalsCVR[i][t][p] += maxCVR[0];
									
								}
								
								if (val > 0) {
									violatedRunsRatioRUNSVector[i][t][p]++;
								}
								
								val = algo.getTermCoverFstPhRatio();
								if (val > 0) {
									terCoveredRatio[i][t][p] += val;
									terCoveredRatioRUNSVector[i][t][p]++;
								}
								
								val = algo.getViolatedNodesRatio();
								if (val > 0) {
									violatedNodesRatio[i][t][p] += val;
									violatedNodesRatioRUNSVector[i][t][p]++;
								}
							}						
						} else
							j--;

				}
				
				for (int p = 0; p < Graph.SPANNER_FACTORS.length; p++) {
					degree[i][t][p] = degree[i][t][p]/Graph.NUMBER_OF_RUNS_SIZES;
					
					if (violatedTerminalsCVRRUNSVector[i][t][p] == 0) {
						violatedTerminalsCVR[i][t][p] = 0;
						meanOfMaximumViolatedTerminalsCVR[i][t][p] = 0;
					} else {
						violatedTerminalsCVR[i][t][p] = (violatedTerminalsCVR[i][t][p]+(Graph.NUMBER_OF_RUNS_SIZES-violatedTerminalsCVRRUNSVector[i][t][p]))/Graph.NUMBER_OF_RUNS_SIZES;
						meanOfMaximumViolatedTerminalsCVR[i][t][p] = (meanOfMaximumViolatedTerminalsCVR[i][t][p]+(Graph.NUMBER_OF_RUNS_SIZES-violatedTerminalsCVRRUNSVector[i][t][p]))/Graph.NUMBER_OF_RUNS_SIZES;						
					}

					if (terCoveredRatioRUNSVector[i][t][p] == 0)
						terCoveredRatio[i][t][p] = 0;
					else
						terCoveredRatio[i][t][p] = (terCoveredRatio[i][t][p]/terCoveredRatioRUNSVector[i][t][p])*100;
					if (violatedNodesRatioRUNSVector[i][t][p] == 0)
						violatedNodesRatio[i][t][p] = 0;
					else
						violatedNodesRatio[i][t][p] = (violatedNodesRatio[i][t][p]/Graph.NUMBER_OF_RUNS_SIZES)*100;
					
					violatedRunsRatio[i][t][p] = (violatedRunsRatioRUNSVector[i][t][p]/Graph.NUMBER_OF_RUNS_SIZES)*100;
					
					degreeOut.println(netSizes[i]+" "+terminalSizes[t]+" "+Graph.SPANNER_FACTORS[p]+" "+degree[i][t][p]);
					violatedTerminalsCVRFileOut.println(netSizes[i]+" "+terminalSizes[t]+" "+Graph.SPANNER_FACTORS[p]+" "+violatedTerminalsCVR[i][t][p]+" "+maxOfMeanViolatedTerminalsCVR[i][t][p]+" "+maxOfMaximumViolatedTerminalsCVR[i][t][p]+" "+meanOfMaximumViolatedTerminalsCVR[i][t][p]);
					percCovTerOut.println(netSizes[i]+" "+terminalSizes[t]+" "+Graph.SPANNER_FACTORS[p]+" "+terCoveredRatio[i][t][p]);
					violatedNodesRatioOut.println(netSizes[i]+" "+terminalSizes[t]+" "+Graph.SPANNER_FACTORS[p]+" "+violatedNodesRatio[i][t][p]);
					violatedRunsRatioOut.println(netSizes[i]+" "+terminalSizes[t]+" "+Graph.SPANNER_FACTORS[p]+" "+violatedRunsRatio[i][t][p]);
					
					System.out.println(netSizes[i]+" "+terminalSizes[t]+" "+Graph.SPANNER_FACTORS[p]+" "+degree[i][t][p]);
					System.out.println(netSizes[i]+" "+terminalSizes[t]+" "+Graph.SPANNER_FACTORS[p]+" "+violatedTerminalsCVR[i][t][p]);
					System.out.println(netSizes[i]+" "+terminalSizes[t]+" "+Graph.SPANNER_FACTORS[p]+" "+terCoveredRatio[i][t][p]);					
					System.out.println(netSizes[i]+" "+terminalSizes[t]+" "+Graph.SPANNER_FACTORS[p]+" "+violatedNodesRatio[i][t][p]);
					System.out.println(netSizes[i]+" "+terminalSizes[t]+" "+Graph.SPANNER_FACTORS[p]+" "+violatedRunsRatio[i][t][p]);
				}
				
				degreeOut.println();
				violatedTerminalsCVRFileOut.println();
				percCovTerOut.println();
				violatedNodesRatioOut.println();
				violatedRunsRatioOut.println();
			}
			}
			
			degreeOut.close();
			violatedTerminalsCVRFileOut.close();
			percCovTerOut.close();
			violatedNodesRatioOut.close();
			violatedRunsRatioOut.close();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (int p = 0; p < Graph.SPANNER_FACTORS.length; p++) {
				System.out.println("Spanning factor of "+Graph.SPANNER_FACTORS[p]);
				for (int i = 0; i < netSizes.length; i++)
					for (int j = 0; j < terminalSizes.length; j++) {
						System.out.println("size "+netSizes[i]+" ter "+terminalSizes[j]+" degree "+degree[i][j][p]);
					}
				
				for (int i = 0; i < netSizes.length; i++)
					for (int j = 0; j < terminalSizes.length; j++) {
						System.out.println("size "+netSizes[i]+" ter "+terminalSizes[j]+" violatedTerminalsCVR "+violatedTerminalsCVR[i][j][p]);
					}					
				
				for (int i = 0; i < netSizes.length; i++)
					for (int j = 0; j < terminalSizes.length; j++) {
						System.out.println("size "+netSizes[i]+" ter "+terminalSizes[j]+" terCoveredRatio "+terCoveredRatio[i][j][p]);
					}
				
				for (int i = 0; i < netSizes.length; i++)
					for (int j = 0; j < terminalSizes.length; j++) {
						System.out.println("size "+netSizes[i]+" ter "+terminalSizes[j]+" violatedNodesRatio "+violatedNodesRatio[i][j][p]);
					}			
				
				for (int i = 0; i < netSizes.length; i++)
					for (int j = 0; j < terminalSizes.length; j++) {
						System.out.println("tam "+netSizes[i]+" ter "+terminalSizes[j]+" violatedRunsRatio "+violatedRunsRatio[i][j][p]);
					}				
			}
		
			Date stop = new Date();
			long l1 = start.getTime();
			long l2 = stop.getTime();
			long diff = l2 - l1;

			long secondInMillis = 1000;
			long minuteInMillis = secondInMillis * 60;
			long hourInMillis = minuteInMillis * 60;
			long dayInMillis = hourInMillis * 24;
			long yearInMillis = dayInMillis * 365;

			long elapsedYears = diff / yearInMillis;
			diff = diff % yearInMillis;
			long elapsedDays = diff / dayInMillis;
			diff = diff % dayInMillis;
			long elapsedHours = diff / hourInMillis;
			diff = diff % hourInMillis;
			long elapsedMinutes = diff / minuteInMillis;
			diff = diff % minuteInMillis;
			long elapsedSeconds = diff / secondInMillis;
			
			System.out.println(elapsedHours+"h:"+elapsedMinutes+"m:"+elapsedSeconds+"s");


    }
}
