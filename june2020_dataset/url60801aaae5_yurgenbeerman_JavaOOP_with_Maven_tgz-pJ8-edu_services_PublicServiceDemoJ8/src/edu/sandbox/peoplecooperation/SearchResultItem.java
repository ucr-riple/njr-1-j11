package edu.sandbox.peoplecooperation;

import edu.clients.SearchableCitizen;

/**
 * Created by yurij.pyvovarenko on 11.05.14.
 */
public class SearchResultItem {
    private SearchableCitizen foundCitizen;
    private int sameHobbiesCount;

    public SearchableCitizen getFoundCitizen() {
        return foundCitizen;
    }

    public void setFoundCitizen(SearchableCitizen foundCitizen) {
        this.foundCitizen = foundCitizen;
    }

    public int getSameHobbiesCount() {
        return sameHobbiesCount;
    }

    public void setSameHobbiesCount(int sameHobbiesCount) {
        this.sameHobbiesCount = sameHobbiesCount;
    }

    @Override
    public String toString() {
        return "SearchResultItem{" +
                "foundCitizen=" + foundCitizen +
                ", sameHobbiesCount=" + sameHobbiesCount +
                '}';
    }
}
