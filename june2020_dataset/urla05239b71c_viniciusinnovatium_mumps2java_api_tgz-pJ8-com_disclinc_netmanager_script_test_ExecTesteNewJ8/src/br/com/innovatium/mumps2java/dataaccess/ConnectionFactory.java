package br.com.innovatium.mumps2java.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public final class ConnectionFactory {
	private static Properties properties;
	private static InitialContext context;

	static {
		properties = new Properties();
		properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		try {
			context = new InitialContext(properties);
		} catch (NamingException e) {
			throw new IllegalStateException(
					"Fail to initialize de contexto to do lookups of the datasource.",
					e);
		}
	}

	private ConnectionFactory() {

	}

	public static Connection getConnection(ConnectionType type)
			throws SQLException {

		if (context == null) {
			try {
				context = new InitialContext();
			} catch (NamingException e) {
				throw new IllegalStateException(
						"The context to looking for data sources was not initialized");
			}
		}

		if (ConnectionType.JDBC.equals(type)) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return DriverManager.getConnection(
					"jdbc:oracle:thin:@mokona:1521:ora11db1", "metauser",
					"metauser");

			/*
			 * return DriverManager.getConnection(
			 * "jdbc:postgresql://localhost:5432/metadata", "postgres",
			 * "@postgresql15");
			 */

		} else {

			try {
				return ((DataSource) ConnectionFactory.context
						.lookup("java:jboss/datasources/oracle"))
						.getConnection();
			} catch (NamingException e) {
				throw new IllegalStateException(
						"Fail on look up datasource to open a new conection.",
						e);
			}
		}

	}
}