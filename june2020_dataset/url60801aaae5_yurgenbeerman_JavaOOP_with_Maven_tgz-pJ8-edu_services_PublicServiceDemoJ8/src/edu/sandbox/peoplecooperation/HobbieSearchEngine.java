package edu.sandbox.peoplecooperation;

import edu.clients.SearchableCitizen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by yurij.pyvovarenko on 05.05.14.
 */
public class HobbieSearchEngine extends SearchEngine {
    private final String delimiters = " ,:;-.\n\t";
    List<SearchableCitizen> citizensToSearch;

    public HobbieSearchEngine(List<SearchableCitizen> citizensToSearch) {
        this.citizensToSearch = citizensToSearch;
    }

    public SearchResults findByHobbie(SearchableCitizen citizen) {
        //List<SearchableCitizen> result = new ArrayList<SearchableCitizen>();
        SearchResults searchResults = new SearchResults();

        StringTokenizer stringTokenizer = new StringTokenizer(citizen.getHobbies(), delimiters);
        int hobbiesCount = stringTokenizer.countTokens();
        String[] hobbiesNames = new String[hobbiesCount];
        for (int i = 0; i < hobbiesCount; i++) {
            hobbiesNames[i] = stringTokenizer.nextToken();
        }

        boolean hasSameHobby;
        SearchResultItem searchResultItem;
        for (SearchableCitizen aCitizen : citizensToSearch) {
            hasSameHobby = false;
            searchResultItem = new SearchResultItem();
            for (int i = 0; i < hobbiesCount; i++) {
                if (aCitizen.getHobbies().contains(hobbiesNames[i])) {
                    if (!aCitizen.equals(citizen)) {
                        if (!hasSameHobby) {
                            searchResultItem.setSameHobbiesCount(1);
                            searchResultItem.setFoundCitizen(aCitizen);
                            hasSameHobby = true;
                        } else {
                            searchResultItem.setSameHobbiesCount(searchResultItem.getSameHobbiesCount()+1);
                        }
                    }
                }
            }
            if (hasSameHobby) {
                searchResults.add(searchResultItem);
            }
        }
        searchResults.sortByHobbiesCount();
        searchResults.setFinalized();
        return searchResults;

        /*
        while (stringTokenizer.hasMoreTokens()) {
            String hobbie = stringTokenizer.nextToken();
            System.out.println(hobbie + ", ");
            for (SearchableCitizen aCitizen : citizensToSearch) {
                if (aCitizen.getHobbies().contains(hobbie)) {
                    searchResultItem.setFoundCitizen(aCitizen);
                    result.add(aCitizen);
                }
            }
        }
        return Collections.unmodifiableList(result);
        */
    }
}
