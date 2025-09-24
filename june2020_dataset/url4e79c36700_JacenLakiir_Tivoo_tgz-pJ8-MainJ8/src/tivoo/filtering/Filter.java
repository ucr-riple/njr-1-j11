package tivoo.filtering;

import tivoo.Event;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Filter {

	
	
	/**
	 * Finds all events without the given keyword.
	 */
	public List<Event> filterByKeyword(String keyword, List<Event> eventList, boolean inEvent) {
		List<Event> newEventList = new LinkedList<Event>();
		for (Event event : eventList) {
			boolean checkEvent = false;
			if (event.getDescription().contains(keyword)
					|| event.getTitle().contains(keyword)) {
				checkEvent = true;
			}
			for (String value : event.values()) {
				if (value.contains(keyword)) {
					checkEvent= true;
				}
			}
			if (checkEvent==inEvent) newEventList.add(event);
		}
		return newEventList;
	}

	/**
	 * Finds all events for a given timeframe.
	 */
	public List<Event> filterByTimeFrame(Calendar startTime, Calendar endTime,
			List<Event> eventList) {
		List<Event> newEventList = new LinkedList<Event>();
		for (Event event : eventList) {
			if (event.getStartTime().after(startTime)
					&& event.getEndTime().before(endTime)) {
				newEventList.add(event);
			}
		}
		return newEventList;
	}

	/**
	 * Finds all events with a given keyword in a specific attribute (Doesn't
	 * include title, description, or location).
	 */
	public List<Event> filterByKeyword(String attribute, String keyword,
			List<Event> eventList) {
		List<Event> newEventList = new LinkedList<Event>();
		for (Event event : eventList) {
			if (event.containsKey(attribute)) {
				if (event.get(attribute).contains(keyword)) {
					newEventList.add(event);
				}
			}
			else{
				newEventList.add(event);
			}
		}
		return newEventList;
	}

	/**
	 * Sorts events by name in ascending order
	 */
	public void sort(List<Event> eventList, Comparator<Event> comp) {
		Collections.sort(eventList,comp);
	}
		
}
