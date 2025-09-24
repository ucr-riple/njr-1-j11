package com.gesoftware.venta.db;

import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.utility.CompressionUtility;
import com.gesoftware.venta.utility.ValidationUtility;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCP;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

/**
 * DB connection class definition
 **/
public final class DBConnection {
    /* Connections pool */
    private BoneCP m_Pool;

    /**
     * DB Statement class definition
     **/
    public final class DBStatement {
        private final PreparedStatement m_Statement;
        private final Connection m_Connection;

        /* *
         * METHOD: Class constructor
         *  PARAM: [IN] connection - current connection
         *  PARAM: [IN] statement  - statement, created from connection
         * AUTHOR: Dmitry Eliseev
         * */
        private DBStatement(final Connection connection, final PreparedStatement statement) {
            m_Connection = connection;
            m_Statement  = statement;
        } /* End of 'DBStatement::DBStatement' class */

        /* *
         * METHOD: Integer parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] index - parameter position
         *  PARAM: [IN] value - parameter value
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setInteger(final int index, final int value) {
            try {
                m_Statement.setInt(index, value);
                return true;
            } catch (final SQLException e) {
                LoggingUtility.debug("Can't set integer value: " + value + " because of " + e.getMessage());
            }

            return false;
        } /* End of 'DBStatement::setInteger' class */

        /* *
         * METHOD: Long parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] index - parameter position
         *  PARAM: [IN] value - parameter value
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setLong(final int index, final long value) {
            try {
                m_Statement.setLong(index, value);
                return true;
            } catch (final SQLException e) {
                LoggingUtility.debug("Can't set long value: " + value + " because of " + e.getMessage());
            }

            return false;
        } /* End of 'DBStatement::setLong' class */

        /* *
         * METHOD: String parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] index - parameter position
         *  PARAM: [IN] value - parameter value
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setString(final int index, final String value) {
            try {
                m_Statement.setString(index, value);
            } catch (final SQLException e) {
                LoggingUtility.debug("Can't set string value: " + value + " because of " + e.getMessage());
            }

            return false;
        } /* End of 'DBStatement::setString' class */

        /* *
         * METHOD: Enum parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] index - parameter position
         *  PARAM: [IN] value - parameter value
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setEnum(final int index, final Enum value) {
            return setString(index, value.name());
        } /* End of 'DBStatement::setEnum' method */

        /* *
         * METHOD: Binary stream parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] index  - parameter position
         *  PARAM: [IN] stream - stream
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setBinaryStream(final int index, final InputStream stream) {
            try {
                m_Statement.setBinaryStream(index, stream);
                return true;
            } catch (final SQLException e) {
                LoggingUtility.debug("Can't set stream value: " + stream + " because of " + e.getMessage());
            }

            return false;
        } /* End of 'DBStatement::setBinaryStream' method */

        /* *
         * METHOD: Object parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] index  - parameter position
         *  PARAM: [IN] object - serializable object
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setObject(final int index, final Serializable object) {
            final byte[] serializedObject = CompressionUtility.compress(object);
            return serializedObject != null && setBinaryStream(index, new ByteArrayInputStream(serializedObject));
        } /* End of 'DBStatement::setObject' method */

        @Override
        public final String toString() {
            return m_Statement.toString();
        }
    } /* End of 'DBConnection::DBStatement' class */

    /**
     * DB Multiple statement class definition
     **/
    public final class DBMultipleStatement {
        private final PreparedStatement[] m_Statements;
        private final Connection m_Connection;

        /* *
         * METHOD: Class constructor
         *  PARAM: [IN] connection - current connection
         *  PARAM: [IN] statement  - statement, created from connection
         * AUTHOR: Dmitry Eliseev
         * */
        private DBMultipleStatement(final Connection connection, final PreparedStatement... statements) {
            m_Connection = connection;
            m_Statements = statements;
        } /* End of 'DBMultipleStatement::DBMultipleStatement' class */

        /* *
         * METHOD: Integer parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] queryIndex - query index
         *  PARAM: [IN] index      - parameter position
         *  PARAM: [IN] value      - parameter value
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setInteger(final int queryIndex, final int index, final int value) {
            if ((queryIndex < 0)||(queryIndex >= m_Statements.length))
                return false;

            try {
                m_Statements[queryIndex].setInt(index, value);
                return true;
            } catch (final SQLException e) {
                LoggingUtility.debug("Can't set integer value: " + value + " because of " + e.getMessage());
            }

            return false;
        } /* End of 'DBMultipleStatement::setInteger' class */

        /* *
         * METHOD: Long parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] queryIndex - query index
         *  PARAM: [IN] index      - parameter position
         *  PARAM: [IN] value      - parameter value
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setLong(final int queryIndex, final int index, final long value) {
            if ((queryIndex < 0)||(queryIndex >= m_Statements.length))
                return false;

            try {
                m_Statements[queryIndex].setLong(index, value);
                return true;
            } catch (final SQLException e) {
                LoggingUtility.debug("Can't set long value: " + value + " because of " + e.getMessage());
            }

            return false;
        } /* End of 'DBMultipleStatement::setLong' class */

        /* *
         * METHOD: String parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] queryIndex - query index
         *  PARAM: [IN] index      - parameter position
         *  PARAM: [IN] value      - parameter value
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setString(final int queryIndex, final int index, final String value) {
            if ((queryIndex < 0)||(queryIndex >= m_Statements.length))
                return false;

            try {
                m_Statements[queryIndex].setString(index, value);
            } catch (final SQLException e) {
                LoggingUtility.debug("Can't set string value: " + value + " because of " + e.getMessage());
            }

            return false;
        } /* End of 'DBMultipleStatement::setString' class */

        /* *
         * METHOD: Enum parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] queryIndex - query index
         *  PARAM: [IN] index      - parameter position
         *  PARAM: [IN] value      - parameter value
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setEnum(final int queryIndex, final int index, final Enum value) {
            return setString(queryIndex, index, value.name());
        } /* End of 'DBMultipleStatement::setEnum' method */

        /* *
         * METHOD: Binary stream parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] queryIndex - query index
         *  PARAM: [IN] index      - parameter position
         *  PARAM: [IN] stream     - stream
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setBinaryStream(final int queryIndex, final int index, final InputStream stream) {
            if ((queryIndex < 0)||(queryIndex >= m_Statements.length))
                return false;

            try {
                m_Statements[queryIndex].setBinaryStream(index, stream);
                return true;
            } catch (final SQLException e) {
                LoggingUtility.debug("Can't set stream value: " + stream + " because of " + e.getMessage());
            }

            return false;
        } /* End of 'DBMultipleStatement::setBinaryStream' method */

        /* *
         * METHOD: Object parameter setter
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] queryIndex - query index
         *  PARAM: [IN] index  - parameter position
         *  PARAM: [IN] object - serializable object
         * AUTHOR: Dmitry Eliseev
         * */
        public final boolean setObject(final int queryIndex, final int index, final Serializable object) {
            final byte[] serializedObject = CompressionUtility.compress(object);
            return serializedObject != null && setBinaryStream(queryIndex, index, new ByteArrayInputStream(serializedObject));
        } /* End of 'DBMultipleStatement::setObject' method */

        /* *
         * METHOD: Returns the number of statements in batch
         * RETURN: The number of statements in batch
         * AUTHOR: Dmitry Eliseev
         * */
        public final int size() {
            return m_Statements.length;
        } /* End of 'DBMultipleStatement::size' method */
    } /* End of 'DBConnection::DBMultipleStatement' class */

    /* *
     * METHOD: Class constructor
     *  PARAM: [IN] host - Database service host
     *  PARAM: [IN] port - Database service port
     *  PARAM: [IN] name - Database name
     *  PARAM: [IN] user - Database user's name
     *  PARAM: [IN] pass - Database user's password
     * AUTHOR: Dmitry Eliseev
     * */
    public DBConnection(final String host, final int port, final String name, final String user, final String pass) {
        final BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + name + "?useUnicode=yes&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true");
        config.setUsername(user);
        config.setPassword(pass);
        config.setInitSQL("SET CHARACTER SET utf8, character_set_client = utf8, character_set_connection = utf8, " +
                          "character_set_results = utf8, NAMES utf8, collation_connection = utf8_general_ci, " +
                          "collation_database = utf8_general_ci");

        /* Pool size configuration */
        config.setMaxConnectionsPerPartition(5);
        config.setMinConnectionsPerPartition(5);
        config.setPartitionCount(1);

        try {
            m_Pool = new BoneCP(config);
        } catch (final SQLException e) {
            LoggingUtility.error("Can't initialize connections pool: " + e.getMessage());
            m_Pool = null;
        }
    } /* End of 'DBConnection::DBConnection' method */

    @Override
    protected final void finalize() throws Throwable {
        super.finalize();

        if (m_Pool != null)
            m_Pool.shutdown();
    } /* End of 'DBConnection::finalize' method  */

    /* *
     * METHOD: Prepares statement using current connection
     * RETURN: Prepared statement
     *  PARAM: [IN] query - SQL query
     * AUTHOR: Dmitry Eliseev
     * */
    public final DBStatement createStatement(final String query) {
        if (ValidationUtility.isEmpty(query))
            return null;

        try {
            final Connection connection = m_Pool.getConnection();
            return new DBStatement(connection, connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS));
        } catch (final SQLException e) {
            LoggingUtility.error("Can't create prepared statement using query: " + e.getMessage());
        } catch (final Exception e) {
            LoggingUtility.error("Connection wasn't established: " + e.getMessage());
        }

        return null;
    } /* End of 'DBConnection::createStatement' method */

    /* *
     * METHOD: Prepares statement using current connection
     * RETURN: Prepared statement
     *  PARAM: [IN] queries - SQL queries
     * AUTHOR: Dmitry Eliseev
     * */
    public final DBMultipleStatement createMultipleStatement(final String... queries) {
        if ((queries == null)||(queries.length == 0))
            return null;

        try {
            final Connection connection = m_Pool.getConnection();

            /* Disable auto commit */
            connection.setAutoCommit(false);

            /* Prepare statements */
            final PreparedStatement[] statements = new PreparedStatement[queries.length];
            for (int i = 0; i < queries.length; i++)
                statements[i] = connection.prepareStatement(queries[i], Statement.RETURN_GENERATED_KEYS);

            return new DBConnection.DBMultipleStatement(connection, statements);
        } catch (final SQLException e) {
            LoggingUtility.error("Can't create prepared statement using query: " + e.getMessage());
        } catch (final Exception e) {
            LoggingUtility.error("Connection wasn't established: " + e.getMessage());
        }

        return null;
    } /* End of 'DBConnection::createStatement' method */

    /* *
     * METHOD: Closes prepared statement
     *  PARAM: [IN] sql - prepared statement
     * AUTHOR: Dmitry Eliseev
     * */
    public void closeStatement(final DBStatement query) {
        if (query == null)
            return;

        /* Close statement */
        try {
            if (query.m_Statement != null)
                query.m_Statement.close();
        } catch (final SQLException ignored) {}

        /* Return connection to pool */
        try {
            if (query.m_Connection != null)
                query.m_Connection.close();
        } catch (final SQLException ignored) {}
    } /* End of 'DBConnection::closeStatement' method */

    /* *
     * METHOD: Closes prepared statement
     *  PARAM: [IN] query - multiple query
     * AUTHOR: Dmitry Eliseev
     * */
    public void closeStatement(final DBMultipleStatement query) {
        if (query == null)
            return;

        /* Close all statements */
        for (final PreparedStatement statement : query.m_Statements)
            try {
                statement.close();
            } catch (final SQLException ignored) {}

        /* Enable auto commit */
        try {
            if (query.m_Connection != null)
                query.m_Connection.setAutoCommit(true);
        } catch (final Exception ignored) {}

        /* Return connection to pool*/
        try {
            if (query.m_Connection != null)
                query.m_Connection.close();
        } catch (final Exception ignored) {}
    } /* End of 'DBConnection::closeStatement' method */

    /* *
     * METHOD: Executes prepared statement like INSERT query
     * RETURN: Inserted item identifier if success, 0 otherwise
     *  PARAM: [IN] sql - prepared statement
     * AUTHOR: Dmitry Eliseev
     * */
    public final long insert(final DBStatement query) {
        try {
            /* Query execution */
            query.m_Statement.execute();

            /* Obtain last insert ID */
            final ResultSet resultSet = query.m_Statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (final SQLException e) {
            LoggingUtility.error("Can't execute insert query: " + query.toString() + " because of " + e.getMessage());
        } finally {
            closeStatement(query);
        }

        /* Insertion failed */
        return 0;
    } /* End of 'DBConnection::insert' method */

    /* *
     * METHOD: Executes prepared statement like INSERT query
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] sql - prepared statement
     * AUTHOR: Dmitry Eliseev
     * */
    public final boolean insert(final DBMultipleStatement query) {
        try {
            /* Execute all statements */
            for (final PreparedStatement statement : query.m_Statements)
                statement.execute();

            /* Commit transaction */
            query.m_Connection.commit();
            return true;
        } catch (final SQLException e) {

            /* Rollback transaction if there is an exception */
            try {
                query.m_Connection.rollback();
            } catch (final SQLException ignored) {}

            LoggingUtility.error("Can't execute multiple insert query");
        } finally {
            closeStatement(query);
        }

        /* Update failed */
        return false;
    } /* End of 'DBConnection::insert' method */

    /* *
     * METHOD: Executes prepared statement like UPDATE query
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] query - prepared statement
     * AUTHOR: Dmitry Eliseev
     * */
    public final boolean update(final DBStatement query) {
        try {
            query.m_Statement.execute();
            return true;
        } catch (final SQLException e) {
            LoggingUtility.error("Can't execute update query: " + query.toString() + " because of " + e.getMessage());
        } finally {
            closeStatement(query);
        }

        /* Update failed */
        return false;
    } /* End of 'DBConnection::update' method */

    /* *
     * METHOD: Executes prepared statement like UPDATE query
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] query - prepared statements
     * AUTHOR: Dmitry Eliseev
     * */
    public final boolean update(final DBMultipleStatement query) {
        try {
            /* Execute all statements */
            for (final PreparedStatement statement : query.m_Statements)
                statement.execute();

            /* Commit transaction */
            query.m_Connection.commit();
            return true;
        } catch (final SQLException e) {

            /* Rollback transaction if there is an exception */
            try {
                query.m_Connection.rollback();
            } catch (final SQLException ignored) {}

            LoggingUtility.error("Can't execute multiple update query");
        } finally {
            closeStatement(query);
        }

        /* Update failed */
        return false;
    } /* End of 'DBConnection::update' method */

    /* *
     * METHOD: Executes prepared statement like COUNT != 0 query
     * RETURN: True if exists, False otherwise
     *  PARAM: [IN] sql - prepared statement
     * AUTHOR: Dmitry Eliseev
     * */
    public final boolean exists(final DBStatement query) {
        final AbstractList<Map<String, Object>> results = select(query);
        return results != null && results.size() != 0;
    } /* End of 'DBConnection::DBConnection' method */

    /* *
     * METHOD: Executes prepared statement like SELECT query
     * RETURN: List of records (maps) if success, null otherwise
     *  PARAM: [IN] sql - prepared statement
     * AUTHOR: Dmitry Eliseev
     * */
    public final AbstractList<Map<String, Object>> select(final DBStatement query) {
        try {
            /* Container for result set */
            final AbstractList<Map<String, Object>> results = new LinkedList<Map<String, Object>>();

            /* Query execution */
            query.m_Statement.execute();

            /* Determine columns meta data */
            final ResultSetMetaData metaData = query.m_Statement.getMetaData();

            /* Obtain real data */
            final ResultSet resultSet = query.m_Statement.getResultSet();
            while (resultSet.next()) {
                final Map<String, Object> row = new HashMap<String, Object>();

                /* Copying fetched data */
                for (int columnID = 1; columnID <= metaData.getColumnCount(); columnID++)
                    row.put(metaData.getColumnName(columnID), resultSet.getObject(columnID));

                /* Add row to results */
                results.add(row);
            }

            /* That's it */
            return results;
        } catch (final SQLException e) {
            LoggingUtility.error("Can't execute select query: " + query.toString() + " because of " + e.getMessage());
        } finally {
            closeStatement(query);
        }

        /* Return empty result */
        return null;
    } /* End of 'DBConnection::select' method */
} /* End of 'DBConnection' class */
