package Trading_Engine;

import java.io.*;
import java.sql.*;
import java.util.LinkedList;

public class myDatabase {
	private static Connection connection;
	private int totalMatch;

	public myDatabase(){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			System.out.println("Could not find JDBCdriver : " + e);
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:test.db");
		} catch (Exception e) {
			System.out.println("Could not establish connection : " + e);
		}
		deleteAllTables();
	}
	public static void insertAllDatabase (Connection con, BufferedReader br, String newHead, String dbName, LinkedList<String> overviewResult){
		try {
			String st = "";
			String preInsertQuery = "insert into " + dbName +"(" + newHead + ")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement orderBookQuery = con.prepareStatement(preInsertQuery);
			String tradeQuery = "insert into old_trade_list(" + newHead + ")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement tradePre = con.prepareStatement(tradeQuery);
			con.setAutoCommit(false);
			int count = 0;
			int enter = 0;
			int ask = 0;
			int bid =0;
			int amend = 0;
			int delete = 0;
			int trade = 0;
			int other = 0;
			String checkLastTime = "";
			while ((st=br.readLine())!=null){
				String[] insertElement = st.split(",");
				if(insertElement[3].equalsIgnoreCase("ENTER") ){
					enter++;
					if(insertElement[12].equalsIgnoreCase("A")){
						ask++;
					}else{
						bid++;
					}
				}else if(insertElement[3].equalsIgnoreCase("AMEND") ){
					amend++;
				}else if(insertElement[3].equalsIgnoreCase("DELETE") ){
					delete++;
				}else if(insertElement[3].equalsIgnoreCase("TRADE") ){
					insertQuery(tradePre,insertElement);
					trade++;
				}else{
					other++;
				}
				count++;
				insertQuery(orderBookQuery,insertElement);
				if(count%1000 == 0){
					orderBookQuery.executeBatch();
				}
				if(trade%1000 == 0){
					tradePre.executeBatch();
				}
				checkLastTime = insertElement[2];
			}
			//System.out.println("checkLastTime: " + checkLastTime);
			orderBookQuery.executeBatch();
			tradePre.executeBatch();
			con.commit();
			String stockName = "default name";
			Date currentDate = null;
			Time startTime = null;
			Time endTime = null;
			ResultSet firstLine = getResultSet("select * from " + dbName + " order by Entry_Time asc limit 1");
			if(firstLine.next()){
				stockName = firstLine.getString(1);
				currentDate = firstLine.getDate(2);
				startTime = firstLine.getTime(3);
			}
			firstLine.close();
			
			overviewResult.add(stockName);
			overviewResult.add(currentDate.toString());
			overviewResult.add(startTime.toString() + " to " + checkLastTime.substring(0, 8));
			overviewResult.add(Integer.toString( count));
			overviewResult.add(Integer.toString(enter));
			overviewResult.add(Integer.toString(ask));
			overviewResult.add(Integer.toString(bid));
			overviewResult.add(Integer.toString(trade));
			overviewResult.add(Integer.toString(amend));
			overviewResult.add(Integer.toString(delete));

			System.out.println("Result of table " + dbName + " :");
			System.out.println("i1 length: " + count + " == " + (enter + amend + delete + trade + other));
			System.out.println("enter: " + enter + " ask: " + ask + " bid " + bid);
			System.out.println("amend: " + amend);
			System.out.println("delete: " + delete);
			System.out.println("trade: " + trade);

			orderBookQuery.close();
		}catch(Exception e){
			System.out.println("In insertAllDatabase: " + e);
		}
	}
	public LinkedList<String> insertAll(File f, String dbName) {
		LinkedList<String> overviewResult = new LinkedList<String>();
		deleteAllTables();
		try {
			Statement s = connection.createStatement();
			BufferedReader br = new BufferedReader(new FileReader(f));
			String head = "";
			if((head=br.readLine())!=null){
				//System.out.println("first character of head: " + head.charAt(0));
				if(head.charAt(0)== '#'){
					String[] tableElement = head.split(",");
					tableElement[3] = "Record_Type";
					tableElement[6] = "Undisclosed_Volume";
					tableElement[9] = "Trans_ID";
					tableElement[10] = "Bid_ID";
					tableElement[11] = "Ask_ID";
					tableElement[12] = "Bid_Ask";
					tableElement[13] = "Entry_Time";
					tableElement[14] = "Old_Price";
					tableElement[15] = "Old_Volume";
					tableElement[16] = "Buyer_Broker_ID";
					tableElement[17] = "Seller_Broker_ID";
					String tableQuery = tableElement[0].substring(1) + " varchar(3), " +
							tableElement[1] + " date, " +
							tableElement[2] + " time, " +
							"millisecond" + " integer, "+
							tableElement[3] + " text, " +
							tableElement[4] + " double, " +
							tableElement[5] + " integer, " +
							tableElement[6] + " integer, " +
							tableElement[7] + " float, " +
							tableElement[8] + " text, " +
							tableElement[9] + " integer, " +
							tableElement[10] + " bigint, " +
							tableElement[11] + " bigint, " +
							tableElement[12] + " varchar(1), " +
							tableElement[13] + " time, " +
							tableElement[14] + " double, " +
							tableElement[15] + " integer, " +
							tableElement[16] + " integer, " +
							tableElement[17] + " integer ";
					String newHead = tableElement[0].substring(1);
					for(int i = 1; i < tableElement.length; i++){
						newHead += " , " + tableElement[i];
						if(i == 2){
							newHead += " , " + "millisecond";
						}
					}
					//System.out.println(tableQuery);
					s.execute("create table " + dbName + "(" + tableQuery + ")");
					s.execute("create table old_trade_list(" + tableQuery + ")");
					insertAllDatabase(connection,br,newHead,dbName,overviewResult);
				} else {
					System.out.println("Error: file content not csv format");
				}
			}else {
				System.out.println("Error: file is empty");
			}
			br.close();
			s.close();
		}catch (Exception e) {
			System.out.println("In insertAll:  " + e);
		}
		return overviewResult;
	}
	public void deleteAllTables(){
		try {
			Statement statement = connection.createStatement();
			DatabaseMetaData dbm = connection.getMetaData();
			String[] types = {"TABLE"};
			ResultSet allTables = dbm.getTables(null,null,"%",types);
			if(allTables!=null){
				while(allTables.next()){
					String table = allTables.getString("TABLE_NAME");
					statement.addBatch("drop table " + table);
					System.out.println("delete table: " + table);
				};
				statement.executeBatch();
			}
			allTables.close();
			statement.close();
		}catch(Exception e) {
			System.out.println("in deleteAllTables: " + e);
		}
	}
	public static void insertQuery(PreparedStatement pstmt, String[] insertElement){
		try {
			int lengthOfArray = insertElement.length;
			//System.out.println(insertElement.length);
			pstmt.setString(1, insertElement[0]);

			String tempDate = insertElement[1].substring(0, 4) + "-" 
					+ insertElement[1].substring(4, 6) + "-" + insertElement[1].substring(6, 8);
			pstmt.setDate(2, Date.valueOf(tempDate));
			pstmt.setTime(3, Time.valueOf(insertElement[2].substring(0, 8)));
			pstmt.setInt(4, Integer.parseInt(insertElement[2].substring(9)));
			pstmt.setString(5, insertElement[3]);
			if(insertElement[4].isEmpty()){
				pstmt.setNull(6, java.sql.Types.DOUBLE);
			}else {
				pstmt.setDouble(6, Double.parseDouble(insertElement[4]));
			}
			if(insertElement[5].isEmpty()){
				pstmt.setNull(7, java.sql.Types.INTEGER);
			}else {
				pstmt.setInt(7, Integer.parseInt(insertElement[5]));
			}
			if(insertElement[6].isEmpty()){
				pstmt.setNull(8, java.sql.Types.INTEGER);
			}else {
				pstmt.setInt(8, Integer.parseInt(insertElement[6]));
			}
			if(lengthOfArray > 7){
				if(insertElement[7].isEmpty()){
					pstmt.setNull(9, java.sql.Types.FLOAT);
				}else {
					pstmt.setFloat(9, Float.parseFloat(insertElement[7]));
				}
			}
			if(lengthOfArray > 8){
				if(insertElement[8].isEmpty()){
					pstmt.setNull(10, java.sql.Types.VARCHAR);
				}else {
					pstmt.setString(10, insertElement[8]);
				}
			}
			if(lengthOfArray > 9){
				if(insertElement[9].isEmpty()){
					pstmt.setNull(11, java.sql.Types.INTEGER);
				}else{
					pstmt.setInt(11, Integer.parseInt(insertElement[9]));
				}
			}else{
				pstmt.setNull(11, java.sql.Types.INTEGER);
			}
			if(lengthOfArray > 10){
				if(insertElement[10].isEmpty()){
					pstmt.setNull(12, java.sql.Types.BIGINT);
				}else {
					pstmt.setLong(12, Long.parseLong(insertElement[10]));
				}
			}else {
				pstmt.setNull(12, java.sql.Types.BIGINT);
			}
			if(lengthOfArray > 11){
				if(insertElement[11].isEmpty()){
					pstmt.setNull(13, java.sql.Types.BIGINT);
				}else {
					pstmt.setLong(13, Long.parseLong(insertElement[11]));
				}
			}else {
				pstmt.setNull(13, java.sql.Types.BIGINT);
			}

			if(lengthOfArray > 12){
				if(insertElement[12].isEmpty()){
					pstmt.setNull(14, java.sql.Types.VARCHAR);
				}else {
					pstmt.setString(14, insertElement[12]);
				}
			}else {
				pstmt.setNull(14, java.sql.Types.VARCHAR);
			}
			if(lengthOfArray > 13){
				if(insertElement[13].isEmpty()){
					pstmt.setNull(15, java.sql.Types.TIME);
				}else {
					pstmt.setTime(15, Time.valueOf(insertElement[13]));
				}
			}else {
				pstmt.setNull(15, java.sql.Types.TIME);
			}
			if(lengthOfArray > 14){
				if(insertElement[14].isEmpty()){
					pstmt.setNull(16, java.sql.Types.DOUBLE);
				}else {
					pstmt.setDouble(16, Double.parseDouble(insertElement[14]));
				}
			}else {
				pstmt.setNull(16, java.sql.Types.DOUBLE);
			}
			if(lengthOfArray > 15){
				if(insertElement[15].isEmpty()){
					pstmt.setNull(17, java.sql.Types.INTEGER);
				}else {
					pstmt.setInt(17, Integer.parseInt(insertElement[15]));
				}
			}else {
				pstmt.setNull(17, java.sql.Types.INTEGER);
			}
			if(lengthOfArray > 16){
				if(insertElement[16].isEmpty()){
					pstmt.setNull(18, java.sql.Types.INTEGER);
				}else {
					pstmt.setInt(18, Integer.parseInt(insertElement[16]));
				}
			}else {
				pstmt.setNull(18, java.sql.Types.INTEGER);
			}
			if(lengthOfArray > 17){
				if(insertElement[17].isEmpty()){
					pstmt.setNull(19, java.sql.Types.INTEGER);
				}else {
					pstmt.setInt(19, Integer.parseInt(insertElement[17]));
				}
			}else {
				pstmt.setNull(19, java.sql.Types.INTEGER);
			}
			pstmt.addBatch();
		}catch(Exception e) {
			System.out.println("In insertQuery:  " + e);
		}
	}

	public String getRowCount() {
		String reply = null;
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT count(*) FROM orderbook;");
			if(rs!=null){
				if(rs.next()){
					reply ="orderbook contains " + rs.getString(1) + " lines.\n";
				}
			}
			rs = statement.executeQuery("SELECT count(*) FROM bid_list;");
			if(rs!=null){
				if(rs.next()){
					reply = reply + "bid_list contains " + rs.getString(1) + " lines.\n";
				}
			}
			rs = statement.executeQuery("SELECT count(*) FROM ask_list;");
			if(rs!=null){
				if(rs.next()){
					reply = reply + "ask_list contains " + rs.getString(1) + " lines.\n";
				}
			}
			rs = statement.executeQuery("SELECT count(*) FROM trade_list;");
			if(rs!=null){
				if(rs.next()){
					reply = reply + "trade_list contains " + rs.getString(1) + " lines.\n";
				}
			}
			rs.close();
			statement.close();
		}catch(Exception e){
			System.out.println("In getRowCount:  " + e);
		}
		return reply;
	}

	public myTrade getTrade(String query) {
		ResultSet rs = null;
		myTrade trade = new myTrade();
		System.out.println("trade length: " + trade.getLength());
		try{
			Statement statement = connection.createStatement();
			rs = statement.executeQuery(query);
			Double tmp;
			while (rs.next()){
				tmp = rs.getDouble(6);
				//System.out.println("tmp: " + tmp);
				trade.addPice(tmp);
			};
			rs.close();
			statement.close();
		}catch(Exception e){
			System.out.println("In getTrade:  " + e);
		}
		return trade;
	}
	public void closeDatabase(){
		try {
			deleteAllTables();
			connection.close();
		} catch (Exception e) {
			System.out.println("Error closing database : " + e);
		}
	}
	public static ResultSet getResultSet(String query) {
		ResultSet set = null;
		try{
			Statement statement = connection.createStatement();
			set = statement.executeQuery(query);
		}catch(Exception e){
			System.out.println("In getResultSet:  " + e);
		}
		return set;
	}
	public void initTwoList() {
		String tableQuery = "ID" + " bigint, " + "Price" + " float, " + "Volume" + " integer ";
		totalMatch = 0;
		try{
			Statement s = connection.createStatement();
			s.execute("create table bid_list(" + tableQuery + ")");
			s.execute("create table ask_list(" + tableQuery + ")");

			s.close();
			connection.setAutoCommit(false);
		}catch (Exception e){
			System.out.println("In initTwoList:  " + e);
		}
	}
	public void closeTwoList() {
		try{
			connection.commit();
		}catch (Exception e){
			System.out.println("In closeTwoList:  " + e);
		}
	}
	public void insertBidList(long tmpID, double tmpPrice, int tmpVol) {
		String preInsertQuery = "insert into bid_list values(?,?,?)";
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from ask_list order by Price asc limit 1");
			if(rs!=null){
				if(rs.next()){
					Long firstAskListID = rs.getLong(1);
					double firstAskListPrice = rs.getDouble(2);
					int firstAskListVol = rs.getInt(3);
					if(tmpPrice >= firstAskListPrice){
						totalMatch++;
						//System.out.println("A trade is performed!!");
						if(tmpVol == firstAskListVol){
							deleteOneFromList(firstAskListID,"ask_list");
						}else if(tmpVol > firstAskListVol){
							deleteOneFromList(firstAskListID,"ask_list");
							tmpVol -= firstAskListVol;
							insertBidList(tmpID,tmpPrice,tmpVol);
						}else{
							firstAskListVol -= tmpVol;
							updateAskList(firstAskListID,firstAskListPrice,firstAskListVol);
						}
					}else{
						PreparedStatement bidListQuery = connection.prepareStatement(preInsertQuery);
						bidListQuery.setLong(1, tmpID);
						bidListQuery.setDouble(2, tmpPrice);
						bidListQuery.setInt(3, tmpVol);
						bidListQuery.executeUpdate();
						bidListQuery.close();
					}
				}else{
					PreparedStatement bidListQuery = connection.prepareStatement(preInsertQuery);
					bidListQuery.setLong(1, tmpID);
					bidListQuery.setDouble(2, tmpPrice);
					bidListQuery.setInt(3, tmpVol);
					bidListQuery.executeUpdate();
					bidListQuery.close();
				}
			}
			rs.close();
			statement.close();
		}catch (Exception e){
			System.out.println("In insertBidList:  " + e);
		}
	}
	public void insertAskList(long tmpID, double tmpPrice, int tmpVol) {
		String preInsertQuery = "insert into ask_list values(?,?,?)";
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from bid_list order by Price desc limit 1");
			if(rs!=null){
				if(rs.next()){
					Long firstAskListID = rs.getLong(1);
					double firstAskListPrice = rs.getDouble(2);
					int firstAskListVol = rs.getInt(3);
					if(tmpPrice <= firstAskListPrice){
						totalMatch++;
						//System.out.println("A trade is performed!!");
						if(tmpVol == firstAskListVol){
							deleteOneFromList(firstAskListID,"bid_list");
						}else if(tmpVol > firstAskListVol){
							deleteOneFromList(firstAskListID,"bid_list");
							tmpVol -= firstAskListVol;
							insertAskList(tmpID,tmpPrice,tmpVol);
						}else{
							firstAskListVol -= tmpVol;
							updateAskList(firstAskListID,firstAskListPrice,firstAskListVol);
						}
					}else{
						PreparedStatement askListQuery = connection.prepareStatement(preInsertQuery);
						askListQuery.setLong(1, tmpID);
						askListQuery.setDouble(2, tmpPrice);
						askListQuery.setInt(3, tmpVol);
						askListQuery.executeUpdate();
						askListQuery.close();
					}
				}else{
					PreparedStatement askListQuery = connection.prepareStatement(preInsertQuery);
					askListQuery.setLong(1, tmpID);
					askListQuery.setDouble(2, tmpPrice);
					askListQuery.setInt(3, tmpVol);
					askListQuery.executeUpdate();
					askListQuery.close();
				}
			}
			rs.close();
			statement.close();
		}catch (Exception e){
			System.out.println("In insertAskList:  " + e);
		}
	}
	public void updateBidList(long tmpID, double tmpPrice, int tmpVol) {
		String preUpdateQuery = "update ask_list set Price = ? , Volume = ? where ID = ?";
		try{
			PreparedStatement updateBid = connection.prepareStatement(preUpdateQuery);
			updateBid.setDouble(1, tmpPrice);
			updateBid.setInt(2, tmpVol);
			updateBid.setLong(3, tmpID);
			updateBid.executeUpdate();

			updateBid.close();
		}catch (Exception e){
			System.out.println("In updateBidList:  " + e);
		}
	}
	public void updateAskList(long tmpID, double tmpPrice, int tmpVol) {
		String preUpdateQuery = "update ask_list set Price = ? , Volume = ? where ID = ?";
		try{
			PreparedStatement updateAsk = connection.prepareStatement(preUpdateQuery);
			updateAsk.setDouble(1, tmpPrice);
			updateAsk.setInt(2, tmpVol);
			updateAsk.setLong(3, tmpID);
			updateAsk.executeUpdate();

			updateAsk.close();
		}catch (Exception e){
			System.out.println("In updateAskList:  " + e);
		}
	}
	public void deleteOneFromList(long tmpID, String type) {
		String deleteQuery = "delete from " + type + " where ID = ?";
		try{
			PreparedStatement deleteListQuery = connection.prepareStatement(deleteQuery);
			deleteListQuery.setLong(1, tmpID);
			deleteListQuery.executeUpdate();
			deleteListQuery.close();
		}catch (Exception e){
			System.out.println("In deleteOneFromList:  " + e);
		}
	}
	public void printTwoList() {
		try{
			Statement s = connection.createStatement();
			System.out.println("Number of Match occur: " + totalMatch);
			ResultSet rs = s.executeQuery("SELECT count(*) FROM bid_list;");
			if(rs!=null){
				if(rs.next()){
					System.out.println("bid_list contains " + rs.getString(1) + " lines.");
				}
			}
			rs = s.executeQuery("SELECT count(*) FROM ask_list;");
			if(rs!=null){
				if(rs.next()){
					System.out.println("ask_list contains " + rs.getString(1) + " lines.");
				}
			}
			rs.close();
			s.close();
		}catch (Exception e){
			System.out.println("In printTwoList:  " + e);
		}
	}
}
