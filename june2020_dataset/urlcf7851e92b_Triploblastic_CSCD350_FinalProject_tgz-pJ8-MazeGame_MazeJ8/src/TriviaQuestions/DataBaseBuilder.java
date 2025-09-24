package TriviaQuestions;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/*For one-time use: creates new .db for MovieTrivia*/
public class DataBaseBuilder {
	private static Connection  c = null;
	private static Statement  s = null;
	private static int count = 0;
	private static ArrayList<String> movies = new ArrayList();
	
	public static void main(String[] args) {
		try {
			connectToDatabase();
			createTable();
			insertTuples();
		} catch (Exception e) {
			System.out.println(e.toString());		
		}
	}//main
	
	public static void connectToDatabase() throws Exception {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:MovieQuoteTrivia.db");
		s = c.createStatement();
		System.out.println("Connection successfull");		
	}
	
	public static void createTable() throws Exception {
		String sql = "CREATE TABLE MovieTrivia" +
					 "(id       INTEGER PRIMARY KEY AUTOINCREMENT," +
					 " quote    VARCHAR(150)," +
					 " answer   VARCHAR(25) ," +
					 " wrong1   VARCHAR(25) ," +
					 " wrong2   VARCHAR(25) ," +
					 " wrong3   VARCHAR(25) )" ;
		s.executeUpdate(sql);
		System.out.println("Table created");
	}
	
	public static void insertTuples() throws Exception {
		insertQuoteAndMovie();
		insertWrongOptions();
	}
	
	private static void insertQuoteAndMovie() throws Exception{
		String sql, quote, movie;
		File fin = new File("MovieQuoteTrivia.txt");
		Scanner sc = new Scanner(fin);
		while(sc.hasNextLine()) {
			quote = sc.nextLine();
			movie = sc.nextLine();
			sql = 	"INSERT INTO MovieTrivia (quote, answer)" +
					"VALUES('" + quote + "','" + movie + "')";
			s.executeUpdate(sql);
			movies.add(movie);
		}
		System.out.println("Quote & Movie inserted");
	}//insertQuoteAndMovie()
	
	
	private static void insertWrongOptions() throws Exception{
		String sql, movie;
		ResultSet rsltSet = null;
		int size = movies.size(), tupleID = movies.size();
		Random random = new Random();
		int index = 0;
		while (tupleID > 0) {
			Collections.shuffle(movies);
			for (int i = 1; i < 4; i++) {
				rsltSet = s.executeQuery("SELECT answer FROM MovieTrivia WHERE id =" + tupleID);
				movie = rsltSet.getString(1);
				index = random.nextInt(size); 
				while (movie.equals(movies.get(index))) { //ensuring correct answer is not inserted as a wrong choice
					index = random.nextInt(size);
				} 
				sql = "UPDATE MovieTrivia " +
					  "SET wrong" + i + " = '" + movies.get(index) + "'" +
					  "WHERE id =" + tupleID + ";";
				s.executeUpdate(sql);
			}
			tupleID--;
		}
	}//insertWrongOptions()
	
	/*this method not used since extra apostrophe's were manually inserted into the text file*/
	private static String insertApostrophe(String str) {
		String curr;
		int j = str.length();
		for(int i = 0; i < j; i++) {
			curr = str.substring(i, i+1);
			if (curr.equals("'")) {
				str = str.substring(0, i) + "'" + str.substring(i);
				i++;
			}	
		}
		return str;
	}//insertApostrophe()
}
