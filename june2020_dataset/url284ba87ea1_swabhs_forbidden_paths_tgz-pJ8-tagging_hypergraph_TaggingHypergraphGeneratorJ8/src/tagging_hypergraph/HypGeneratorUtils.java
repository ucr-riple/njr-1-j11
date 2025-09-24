package tagging_hypergraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for TaggingHypergraphGenerator
 * @author swabha
 *
 */
public class HypGeneratorUtils {
	
	static List<Integer> getConsecutiveIntegers(int start, int size) {
		List<Integer> consecutive = new ArrayList<Integer>();
		for (int i = start; i < start + size; i++) {
			consecutive.add(i);
		}
		return consecutive;
	}
}
