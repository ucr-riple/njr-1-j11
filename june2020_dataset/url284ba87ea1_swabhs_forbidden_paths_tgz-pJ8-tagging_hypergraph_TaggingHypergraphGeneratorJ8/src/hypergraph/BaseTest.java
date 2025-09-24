package hypergraph;

import static org.junit.Assert.assertEquals;
import hypergraph.HypergraphProto.Hyperedge;
import hypergraph.HypergraphProto.Hypergraph;
import hypergraph.HypergraphProto.Vertex;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * Tests for HypGenerator that builds the lattice hypergraph for bigram HMMs.
 * @author swabha
 */
public class BaseTest {

	protected Hypergraph h;
	private File counts;
	
	public BaseTest() {
		counts = new File("ner_rare.counts");
		//HypGenerator gen = new HypGenerator(counts, new File("ner_dev.dat"));
		//h = gen.buildLattice(Arrays.asList("Britain", "by", "Richard", "Volkov", "."));
		/*h = gen.buildLattice(Arrays.asList("Former", "U.S.", "president", "Ronald", "Reagan",
		       "'s", "wife", ",", "Nancy", ",", "admitted", "in", "her", "autobiography", "My",
		       "Turn",	"that", "she", "regularly", "."));*/
		/*h = gen.buildBigramHyp(Arrays.asList("Hove", ":", "Sussex", "363", "(", "W.", "Athey", 
				"111", ",", "V.", "Drakes", "52", ";", "I.", "Austin", "4-37", ")", ",",
				 "Lancashire", "197-8", "(", "W.", "Hegg", "54", ")"));
		/*h = gen.buildLattice(Arrays.asList("South", "Africa", "-", "15", "-", "Andre", "Joubert", 
				",", "14", "-", "Justin", "Swart", ",", "13", "-", "Japie", "Mulder", "(", "Joel",
				"Stransky", ",", "48", "mins", ")", "12", "-", "Danie", "van", "Schalkwyk", ",",
				"11", "-", "Pieter", "Hendriks", ";", "10", "-", "Henry", "Honiball", ",", "9",
				"-", "Joost", "van", "der", "Westhuizen", ";", "8", "-", "Gary", "Teichmann", "(",
				"captain", ")", ",", "7", "-", "Andre", "Venter", "(", "Wayne", "Fyvie", ",", "75",
				")", ",", "6", "-", "Ruben", "Kruge", ",", "5", "-", "Mark", "Andrews", "(",
				"Fritz", "van", "Heerden", ",", "39", ")", ",", "4", "-", "Kobus", "Wiese", ",",
				"3", "-", "Marius", "Hurter", ",", "2", "-", "James", "Dalton", ",", "1", "-",
				"Dawie", "Theron", "(", "Garry", "Pagel", ",", "66", ")", "."));*/
		//h = gen.buildLattice(Arrays.asList("May"));
	}
	
	@Test
	public void testBuildLattice() {
		/*assertEquals(105, h.getEdgesCount());
		assertEquals(26, h.getVerticesCount());*/
		
		//TODO(swabha): Test a random vertex and a random edge
		/*for (Vertex v : h.getVerticesList()) {
			System.out.println("Vertex " + v.getId() + ": " + v.getName());
			System.out.println("Out edges: " + v.getOutEdgeList());
			System.out.println("In edges: " + v.getInEdgeList());
			System.out.println();
		}*/
		/*for (Hyperedge e : h.getEdgesList()) {
			System.out.println("Edge " + e.getId()  + " Parent: " + 
				h.getVertices(e.getParentId()).getName() + 	" Children: " + 
				h.getVertices(e.getChildrenIdsList().get(0)).getName() + " weighs " + e.getWeight());
			System.out.println();
		}*/
	}
	
	@Test
	public void testEdgeWeights() {
		
		double britainLocCount = 0.0;
		double locCount = 0.0;
		double locBigramCount = 0.0;
		double locOBigramCount = 0.0;
		double starLocBigramCount = 0.0;
		double starCount = 0.0;
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(counts);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				String tokens[] = strLine.split(" ");
				if (tokens.length < 4)
					continue;
				if (tokens[1].equals("WORDTAG") 
						&& tokens[2].equals("I-LOC") && tokens[3].equals("Britain")) {
					britainLocCount += Double.parseDouble(tokens[0]);
				}
				if (tokens[1].equals("WORDTAG") && tokens[2].equals("I-LOC")) {
					locCount += Double.parseDouble(tokens[0]);
				}
				if (tokens[1].equals("2-GRAM") 
						&& tokens[2].equals("I-LOC") && tokens[3].equals("O")) {
					locOBigramCount += Double.parseDouble(tokens[0]);
				}
				if (tokens[1].equals("2-GRAM") && tokens[2].equals("I-LOC")) {
					locBigramCount += Double.parseDouble(tokens[0]);
				}
				if (tokens[1].equals("2-GRAM") 
						&& tokens[2].equals("*") && tokens[3].equals("I-LOC")) {
					starLocBigramCount += Double.parseDouble(tokens[0]);
				}
				if (tokens[1].equals("2-GRAM") && tokens[2].equals("*")) {
					starCount += Double.parseDouble(tokens[0]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Hyperedge britainLocOEdge = h.getEdges(14);
		Double expectedWeight = (britainLocCount / locCount) * 
				(locOBigramCount / locBigramCount) * (starLocBigramCount / starCount);
		int comparison = expectedWeight.compareTo((Double)britainLocOEdge.getWeight());
		System.out.println(expectedWeight);
		System.out.println(britainLocOEdge.getWeight());
		//assertEquals(comparison, 0);
	}
	
	@Test
	public void testEdgeWeights_LastColumn() {
		List<String> neTags = Arrays.asList("I-ORG", "I-PER", "I-LOC", "I-MISC", "O");
		Double tagCounts[] = new Double[neTags.size()];
		Double tagAsPeriodCounts[] = new Double[neTags.size()];
		Double tagStopCounts[] = new Double[neTags.size()];
		
		for (int i = 0; i < neTags.size(); i++) {
			tagCounts[i] = 0.0;
			tagAsPeriodCounts[i] = 0.0;
			tagStopCounts[i] = 0.0;
		}
		
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(counts);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				String tokens[] = strLine.split(" ");
				if (tokens.length < 4)
					continue;
				if (tokens[1].equals("WORDTAG") && neTags.contains(tokens[2])) {
					tagCounts[neTags.indexOf(tokens[2])] += Double.parseDouble(tokens[0]);
				}
				if (tokens[1].equals("WORDTAG") 
						&& tokens[3].equals(".")  && neTags.contains(tokens[2])) {
					tagAsPeriodCounts[neTags.indexOf(tokens[2])] +=  Double.parseDouble(tokens[0]);
				}
				if (tokens[1].equals("2-GRAM") 
						&& tokens[3].equals("STOP") && neTags.contains(tokens[2])) {
					tagStopCounts[neTags.indexOf(tokens[2])] +=  Double.parseDouble(tokens[0]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int firstEndEdge = 100;
		for (int i = 0; i < neTags.size(); i++) {
			Hyperedge edge = h.getEdges(i + firstEndEdge);
			Double expectedWeight = (tagAsPeriodCounts[i] / tagCounts[i]) * 
					(tagStopCounts[i] / tagCounts[i]);
			int comparator = expectedWeight.compareTo((Double) edge.getWeight());
			/*System.out.println(expectedWeight + " expected");
			System.out.println(edge.getWeight() + " actual");
			System.out.println(i);
			System.out.println(comparator);
			System.out.println();*/
			//assertEquals(comparator, 0);
		}
	}
}
