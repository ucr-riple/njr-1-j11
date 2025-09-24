package TriviaQuestions;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class MovieTriviaDatabaseManager {
	private int tupleCount = 0;
	private int[] ids; //db primary keys are in random order
	private int index = 0; //ids[index] 
	private Connection c;
	private Statement s;
	
	public MovieTriviaDatabaseManager(){}
	
	public int getTupleCount() {return this.tupleCount;}
	
	public void connectToDatabase() throws Exception {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:MovieQuoteTrivia.db");
		s = c.createStatement();
		tupleCount = countTuples(s);
		ids = randomizeIDs(tupleCount);	
	}
	
	public ResultSet getRandomTuple() throws Exception {
		index++;
		if(index > tupleCount)
			index = 1;
		String sql = "SELECT * FROM MovieTrivia WHERE id = " + ids[index];
		ResultSet rs = s.executeQuery(sql);
		return rs;
	}
	
	private static int countTuples(Statement s) throws Exception {
		String sql = "SELECT count(*) FROM MovieTrivia;";
		ResultSet rs = s.executeQuery(sql);
		return rs.getInt(1);
	}
	
	private static int[] randomizeIDs(int size) {
		int[] ids = new int[size];
		for (int i = 0; i < size; i++)
			ids[i] = i+1; //lowest id == 1
		Random r = new Random();
		int index = 0;
		int temp = 0;
		for (int i = 1; i < size; i++) {
			index = r.nextInt(size);
			temp = ids[i];
			ids[i] = ids[index];
			ids[index] = temp;
		}
		return ids;
	}
	

}
