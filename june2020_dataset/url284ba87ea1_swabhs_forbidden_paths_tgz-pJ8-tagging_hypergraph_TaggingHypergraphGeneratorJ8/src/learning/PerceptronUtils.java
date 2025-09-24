package learning;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PerceptronUtils {

	/** required in the update step of perceptron 
	 * 
	 * @param tokens
	 * @param tags
	 * @return
	 */
	static Map<String, Double> getFeatureVector(List<String> tokens, List<String> tags) {
		Map<String, Double> globalFeatures = new TreeMap<String, Double>();
		for (String token : tokens) {
			int pos = tokens.indexOf(token);
			
			History history;
			if (pos == 0) {
				history = new History("*", "*", tokens, pos);
			} else if (pos == 1) {
				history = new History("*", tags.get(pos - 1), tokens, pos);
			} else {
				history = new History(tags.get(pos - 2), tags.get(pos - 1), tokens, pos);
			}
			List<String> localFeatures = getLocalFeatures(history, tags.get(pos));
			for (String feature : localFeatures) {
				if (globalFeatures.containsKey(feature)) {
					double count  = globalFeatures.get(feature);
					globalFeatures.put(feature, count + 1.0);
				}
			}
			
		}
		return globalFeatures;
	}
	
	/**
	 * Only thing that needs to be changed when you add new features
	 * @param history
	 * @param tag
	 * @return
	 */
	static List<String> getLocalFeatures(History history, String tag) {
		List<String> localFeatures = new ArrayList<String>();
		// Trigram feature
		localFeatures.add(
				"TRIGRAM:" + history.getLastButOneTag() + ":" + history.getLastTag() + ":" + tag);
		//Tag feature
		localFeatures.add("TAG:" + history.getTokens().get(history.getPosition()) + ":" + tag);
		return localFeatures;
	}
	
	static Map<String, Double> mapAddition(Map<String, Double> map1, Map<String, Double> map2) {
		Map<String, Double> result = new TreeMap<String, Double>();
		//TODO: check if they have the same keys
		for (String feature : map1.keySet()) {
			result.put(feature, map1.get(feature) + map2.get(feature));
		}
		return result;
	}
	
	static Map<String, Double> mapSubtraction(Map<String, Double>map1, Map<String, Double>map2) {
		Map<String, Double> result = new TreeMap<String, Double>();
		//TODO: check if they have the same keys
		for (String feature : map1.keySet()) {
			result.put(feature, map1.get(feature) - map2.get(feature));
		}
		return result;
	}
}
