package edu.sandbox.peoplecooperation;

import edu.clients.SearchableCitizen;

/**
 * Created by yurij.pyvovarenko on 05.05.14.
 */
public abstract class SearchEngine {
    public abstract SearchResults findByHobbie(SearchableCitizen citizen);
}
