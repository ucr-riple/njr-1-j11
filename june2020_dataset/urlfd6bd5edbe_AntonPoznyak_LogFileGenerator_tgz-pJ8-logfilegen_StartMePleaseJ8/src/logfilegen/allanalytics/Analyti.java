package logfilegen.allanalytics;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import logfilegen.allmodels.Log;
import logfilegen.allmodels.Record;

public class Analyti {
private Log log;
	
	public Analyti(Log log){
		this.log = log;
	}
	
	public Map<String, Integer> getStatusFrequency(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for(Record record : log.getRecords()){
			String status = record.getStatus().getStatus();
			Integer number = map.get(status);
			
			if(number != null){
				map.remove(status);
				map.put(status, ++number);
			} else {
				map.put(status, 1);
			}
		}
		
		return map;
	}
	

	public Map<String, Integer> getExtensionFrequency(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for(Record record : log.getRecords()){
			String extension = record.getRequest().getUrl().getExtension().getExtension();
			Integer number = map.get(extension);
			if(number != null){
				map.remove(extension);
				map.put(extension, ++number);
			} else {
				map.put(extension, 1);
			}
		}
		
		return map;
	} 
	
	public Map<String, Double> getStatusPercent(){
		Map<String, Integer> countMap = getStatusFrequency();
		Map<String, Double> percentMap = new HashMap<String, Double>();
		
		Integer count = log.getRecords().size();
		for(java.util.Map.Entry<String, Integer> status : countMap.entrySet()){
			percentMap.put(status.getKey(), (double) status.getValue() / count  * 100);
		}
		
		return percentMap;
	}
	
	public Map<Integer, Integer> getStatusFrequencyOverHours(){
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for(Record record : log.getRecords()){
			Integer hour = record.getTime().getDate().get(Calendar.HOUR_OF_DAY);
			Integer number = map.get(hour);
			if(number != null){
				map.put(hour, ++number);
			} else {
				map.put(hour, 1);
			}
		}
		
		return map;
	}
	
	public Map<String, Integer> getStatusFrequencyOverPartOfDay(){
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		for(Record record : log.getRecords()){
			Integer hour = record.getTime().getDate().get(Calendar.HOUR_OF_DAY);
			String partOfDay = getPartOfDate(hour);
			Integer number = map.get(partOfDay);
			if(number != null){
				map.remove(partOfDay);
				map.put(partOfDay, ++number);
			} else {
				map.put(partOfDay, 1);
			}
		}
		
		return map;
	}
	
	private String getPartOfDate(Integer hourOfDay){
		if(hourOfDay >= 7 && hourOfDay <= 12){
			return "morning";
		} else if(hourOfDay > 12 && hourOfDay <= 18){
			return "afternoon";
		} else if(hourOfDay > 18 && hourOfDay <= 23 || hourOfDay == 0){
			return "evening";
		} else {
			return "night";
		}
	}
	
}