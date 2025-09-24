package br.com.innovatium.mumps2java.dataaccess;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import br.com.innovatium.mumps2java.todo.TODO;

public class DAO {
	private final Connection con;

	public DAO() {
		this(ConnectionType.DATASOURCE);
	}

	public DAO(ConnectionType connectionType) {
		try {
			con = ConnectionFactory.getConnection(connectionType);
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to open connection to database access throught "
							+ connectionType + " strategy", e);
		}

	}

	// Check another return type different from the map.
	@TODO
	public Map<String, String> like(String tableName, String key) {
		if (key == null) {
			return null;
		}
		Map<String, String> map = null;
		PreparedStatement select = null;
		ResultSet result = null;
		try {

			String like = "select key_, value_ from " + tableName
					+ " where key_ like ? order by key_ asc";

			select = con.prepareStatement(like);
			select.setString(1, key + "%");
			result = select.executeQuery();
			map = new HashMap<String, String>();
			while (result.next()) {
				map.put(result.getString(1), result.getString(2));
			}

		} catch (java.sql.SQLSyntaxErrorException e) {
			if (!hasTable(tableName)) {
				return map;
			}
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to find data thought like clause from table "
							+ tableName + " and key " + key, e);
		} finally {
			closeResouce(select);
		}
		return map;
	}

	public void remove(String tableName, String key) {
		PreparedStatement delete = null;
		try {
			String string = "delete from " + tableName + " where key_ like ?";
			delete = con.prepareStatement(string);
			delete.setString(1, key + "%");
			delete.execute();

		} catch (java.sql.SQLSyntaxErrorException e) {
			if (!hasTable(tableName)) {

			}
		} catch (SQLException e) {
			throw new IllegalStateException("Fail to remove data from table "
					+ tableName + " and key " + key, e);
		} finally {
			closeResouce(delete);
		}
	}

	// Remove table name treatment.
	@TODO
	public void insert(String tableName, Object key, Object value) {
		PreparedStatement ps = null;
		ResultSet result = null;
		try {

			String selectOne = "select key_, value_ from " + tableName
					+ " where key_ = ?";
			ps = con.prepareStatement(selectOne);
			ps.setObject(1, key);
			result = ps.executeQuery();

			if (!result.next()) {
				closeResouce(ps);
				String insertQuery = "insert into " + tableName
						+ " values (?, ?)";
				ps = con.prepareStatement(insertQuery);
				ps.setString(1, String.valueOf(key));
				ps.setObject(2, value);
			} else {
				closeResouce(ps);
				String updateQuery = "update " + tableName
						+ " set value_ = ? where key_ = ?";
				ps = con.prepareStatement(updateQuery);
				ps.setObject(1, value);
				ps.setString(2, String.valueOf(key));
			}

			ps.execute();

		} catch (java.sql.SQLSyntaxErrorException e) {
			if (!hasTable(tableName)) {
				createTable(tableName);
				insert(tableName, key, value);
			}
		} catch (SQLException e) {

			throw new IllegalStateException("Fail to insert data into table "
					+ tableName + " and key " + key, e);
		} finally {
			closeResouce(ps);
		}
	}

	public boolean hasTable(String tableName) {
		boolean hasTable = false;
		ResultSet rs = null;
		try {
			DatabaseMetaData metaData = con.getMetaData();
			rs = metaData.getTables(null, null, tableName, null);
			if (rs.next()) {
				hasTable = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return hasTable;
	}

	// Remove table name treatment.
	@TODO
	public Object find(String tableName, String key) {
		Object objResult = null;
		PreparedStatement ps = null;
		ResultSet result = null;

		try {
			final StringBuilder selectOne = new StringBuilder(
					"select key_, value_ from ").append(tableName).append(
					" where key_ = ?");
			ps = con.prepareStatement(selectOne.toString());
			ps.setString(1, key);
			result = ps.executeQuery();
			objResult = result.next() ? result.getString(2) : null;
		} catch (java.sql.SQLSyntaxErrorException e) {
			if (!hasTable(tableName)) {
				objResult = null;
			} else {
				objResult = null;
			}
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to select data from the table " + tableName
							+ " and key " + key, e);
		} finally {
			closeResouce(ps);
		}
		return objResult;
	}

	public boolean createTable(String tableName) {
		PreparedStatement ps = null;
		try {
			final StringBuilder selectOne = new StringBuilder("CREATE TABLE "
					+ tableName
					+ "( \"KEY_\" VARCHAR2(4000 BYTE) NOT NULL ENABLE,"
					+ "\"VALUE_\" VARCHAR2(4000 BYTE))");
			ps = con.prepareStatement(selectOne.toString());
			return ps.execute();

		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to create table " + tableName, e);
		} finally {
			closeResouce(ps);
		}
	}

	private void closeResouce(PreparedStatement ps) {
		try {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Fail to close prepare statement",
					e);
		}
	}

}
