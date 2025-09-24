package com.gesoftware.venta.db;

import com.gesoftware.venta.logging.LoggingUtility;

import java.util.*;

/**
 * DB controller class definition
 **/
public abstract class DBController<T> {
    /* Real DB connection */
    protected final DBConnection m_Connection;

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] connection - real DB connection
     * AUTHOR: Dmitry Eliseev
     * */
    protected DBController(final DBConnection connection) {
        m_Connection = connection;

        LoggingUtility.core(getClass().getSimpleName() + " controller initialized");
    } /* End of 'DBController::DBController' method */

    /* *
     * METHOD: Requests collection of T objects using select statement
     * RETURN: Collection of objects if success, empty collection otherwise
     *  PARAM: [IN] selectStatement - prepared select statement
     * AUTHOR: Dmitry Eliseev
     * */
    protected final Collection<T> getCollection(final DBConnection.DBStatement selectStatement) {
        if (selectStatement == null)
            return new LinkedList<T>();

        final AbstractList<Map<String, Object>> objectsCollection = m_Connection.select(selectStatement);
        if ((objectsCollection == null)||(objectsCollection.size() == 0))
            return new LinkedList<T>();

        final Collection<T> parsedObjectsCollection = new ArrayList<T>(objectsCollection.size());
        for (final Map<String, Object> object : objectsCollection)
            parsedObjectsCollection.add(parse(object));

        return parsedObjectsCollection;
    } /* End of 'DBController::getCollection' method */

    /* *
     * METHOD: Requests one T object using select statement
     * RETURN: Object if success, null otherwise
     *  PARAM: [IN] selectStatement - prepared select statement
     * AUTHOR: Dmitry Eliseev
     * */
    protected final T getObject(final DBConnection.DBStatement selectStatement) {
        if (selectStatement == null)
            return null;

        final AbstractList<Map<String, Object>> objectsCollection = m_Connection.select(selectStatement);
        if ((objectsCollection == null)||(objectsCollection.size() != 1))
            return null;

        return parse(objectsCollection.get(0));
    } /* End of 'DBController::getObject' method */

    /* *
     * METHOD: Parses object's map representation to real T object
     * RETURN: T object if success, null otherwise
     *  PARAM: [IN] objectMap - object map, obtained by selection from DB
     * AUTHOR: Dmitry Eliseev
     * */
    protected abstract T parse(final Map<String, Object> objectMap);
} /* End of 'DBController' class */