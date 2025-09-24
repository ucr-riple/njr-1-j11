package learning;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class InputReader {
	
	/**
	 * Reads the initial hyperedge weights from a file
	 * @param trigramWeights
	 * @param tagWeights
	 */
	public static Map<String, Double> readWeights(File weightsFile) {
		Map<String, Double> weights = new TreeMap<String, Double>();
		try {						
			FileInputStream fstream = new FileInputStream(weightsFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			while ((strLine = br.readLine()) != null) {
				
				String[] items = strLine.split(" ");
				weights.put(items[0], Double.parseDouble(items[1]));
				
			}				
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return weights;
	}
	
	/** 
	 * 
	 * @param trainingFile
	 * @param weightsMap
	 * @return
	 */
	public static List<LearningExample> readExample(
			File trainingFile, Map<String, Double> weightsMap) {
		
		List<LearningExample> examples = new ArrayList<LearningExample>();
		try {
			
			FileInputStream fstream = new FileInputStream(trainingFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			List<String> tokens = new ArrayList<String>();
			List<String> tags = new ArrayList<String>();
			//String tagSequence = "";
			while ((strLine = br.readLine()) != null) {
				String[] items = strLine.split(" ");
				if (items.length < 2) {
					examples.add(new LearningExample(tokens, tags));
					tokens = new ArrayList<String>();
					tags = new ArrayList<String>();
					//tagSequence = "";
				} else {
					
					tokens.add(items[0]);
					tags.add(items[1]);
					//tagSequence += " " + items[1];
				}				
			}				
		} catch(IOException e) {
			e.printStackTrace();
		}
		return examples;
	}

}