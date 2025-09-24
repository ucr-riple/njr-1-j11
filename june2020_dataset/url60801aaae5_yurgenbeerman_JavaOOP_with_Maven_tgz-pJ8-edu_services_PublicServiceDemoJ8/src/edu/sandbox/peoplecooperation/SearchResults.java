package edu.sandbox.peoplecooperation;

import edu.clients.SearchableCitizen;
import edu.services.execution.ServantsTasksDispatcher;

import java.util.*;

/**
 * Created by yurij.pyvovarenko on 11.05.14.
 */
public class SearchResults {
    private List<SearchResultItem> searchResultsItems;
    private boolean isFinalized = false;

    public SearchResults() {
        searchResultsItems = new ArrayList<SearchResultItem>();
    }

    public boolean containsCitizen(SearchableCitizen Citizen) {
        Iterator<SearchResultItem> itemIterator = searchResultsItems.listIterator();
        while (itemIterator.hasNext()) {
            if (itemIterator.next().getFoundCitizen().equals(Citizen)) return true;
        }
        return false;
    }

    public void add(SearchResultItem searchResultItem) {
        if (!isFinalized) {
            searchResultsItems.add(searchResultItem);
        }
    }

    public void setFinalized() {
        isFinalized = true;
    }

    public String toString() {
        return searchResultsItems.toString();
    }

    public SearchResultItem get(int index) {
        return searchResultsItems.get(index);
    }

    public void sortByHobbiesCount() {
        Collections.sort(searchResultsItems, new SortedByHobbiesCount());
    }

    public static class SortedByHobbiesCount implements Comparator<SearchResultItem> {
        public int compare(SearchResultItem searchResultItem1, SearchResultItem searchResultItem2) {
            if (searchResultItem1.getSameHobbiesCount() > searchResultItem2.getSameHobbiesCount()) {
                return -1;
            } else if (searchResultItem1.getSameHobbiesCount() < searchResultItem2.getSameHobbiesCount()) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
