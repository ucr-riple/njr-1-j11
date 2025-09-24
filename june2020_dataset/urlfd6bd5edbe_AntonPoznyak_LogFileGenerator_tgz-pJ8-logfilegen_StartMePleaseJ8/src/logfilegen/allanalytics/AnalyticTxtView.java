package logfilegen.allanalytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import logfilegen.allmodels.Log;

public class AnalyticTxtView implements AnalyticView {
	
	private Analyti analytic;
	private final String NEWLINE = System.getProperty("line.separator");
	
	public AnalyticTxtView(Log log){
		this.analytic = new Analyti(log);
	}

	public String getStatusFrequency() {
		Map<String, Integer> map = analytic.getStatusFrequency();
		StringBuilder sb = new StringBuilder();
		
		List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(map.entrySet());
		Collections.sort(entries, new Comparator<Entry<String, Integer>>() {
		    public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		     return o2.getValue() - o1.getValue();
		    }
		});
		
		for(Entry<String, Integer> status : entries){
			sb.append(status.getKey());
			sb.append(" - ");
			sb.append(status.getValue());
			sb.append(NEWLINE);
		}
		return sb.toString();
	}

	public String getStatusPercent() {
		Map<String, Double> map = analytic.getStatusPercent();
		StringBuilder sb = new StringBuilder();
		
		List<Entry<String, Double>> entries = new ArrayList<Entry<String, Double>>(map.entrySet());
		Collections.sort(entries, new Comparator<Entry<String, Double>>() {
		    public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
		     return (int) (o2.getValue() - o1.getValue());
		    }
		});
		
		for(Entry<String, Double> status : entries){
			sb.append(status.getKey());
			sb.append(" - ");
			sb.append(status.getValue());
			sb.append(NEWLINE);
		}
		return sb.toString();
	}

	public String getStatusFrequencyOverHours() {
		Map<Integer, Integer> map = analytic.getStatusFrequencyOverHours();
		StringBuilder sb = new StringBuilder();
		
		List<Entry<Integer, Integer>> entries = new ArrayList<Entry<Integer, Integer>>(map.entrySet());
		Collections.sort(entries, new Comparator<Entry<Integer, Integer>>() {
		    public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
		     return o2.getValue() - o1.getValue();
		    }
		});
		
		for(Entry<Integer, Integer> hour : entries){
			sb.append(hour.getKey());
			sb.append(" - ");
			sb.append(hour.getValue());
			sb.append(NEWLINE);
		}
		return sb.toString();
	}

	public String getStatusFrequencyOverPartOfDay() {
		Map<String, Integer> map = analytic.getStatusFrequencyOverPartOfDay();
		StringBuilder sb = new StringBuilder();
		
		List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(map.entrySet());
		Collections.sort(entries, new Comparator<Entry<String, Integer>>() {
		    public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		     return o2.getValue() - o1.getValue();
		    }
		});
		
		for(Entry<String, Integer> partOfDay : entries){
			sb.append(partOfDay.getKey());
			sb.append(" - ");
			sb.append(partOfDay.getValue());
			sb.append(NEWLINE);
		}
		return sb.toString();
	}

	public String getExtensionFrequency() {
		Map<String, Integer> map = analytic.getExtensionFrequency();
		StringBuilder sb = new StringBuilder();
		
		List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(map.entrySet());
		Collections.sort(entries, new Comparator<Entry<String, Integer>>() {
		    public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		     return o2.getValue() - o1.getValue();
		    }
		});
		
		for(Entry<String, Integer> extension : entries){
			sb.append(extension.getKey());
			sb.append(" - ");
			sb.append(extension.getValue());
			sb.append(NEWLINE);
		}
		return sb.toString();
	}

}
