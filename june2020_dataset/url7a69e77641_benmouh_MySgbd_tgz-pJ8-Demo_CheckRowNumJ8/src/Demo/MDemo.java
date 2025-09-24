package Demo;

/*
 * This file is part of Mysgbd.
 *
 * Mysgbd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mysgbd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Mysgbd.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.sql.SQLException;
import java.util.Vector;
import java.io.*;

import org.sgbd.Mysgbd.*;
import org.sgbd.Mysgbd.data.MEval;
import org.sgbd.Mysgbd.data.MTuple;

/**
 * <pre>
 * MDemo is able to send SQL queries to simple CSV (comma-separated values)
 * files; the CSV syntax used here is very simple:
 *  The 1st line contains the column names
 *  Other lines contain column values (tuples)
 *  Values are separated by commas, so they can't contain commas (it's just
 *  for demo purposes).
 * Example:
 * Create a num.db text file that contains the following:
 *  a,b,c,d,e
 *  1,1,1,1,1
 *  2,2,2,2,2
 *  1,2,3,4,5
 *  5,4,3,2,1
 * You can then run MDemo, and query it; some legal queries follow:
 *  select * from num;
 *  select a, b from num;
 *  select a+b, c from num;
 *  select * from num where a = 1 or e = 1;
 *  select * from num where a = 1 and b = 1 or e = 1;
 *  select d, e from num where a + b + c <= 3;
 *  select * from num where 3 = a + b + c;
 *  select * from num where a = b or e = d - 1;
 *  select * from num where b ** a <= 2;
 * </pre>
 */
public class MDemo {

	public static void main(String args[]) {
		new LoginDemo();
		
	
		try {

			MqlParser p = null;

			if (args.length < 1) {
				System.out.println("Reading SQL from stdin (quit; or exit; to quit)");
				p = new MqlParser(System.in);
			} else {
				p = new MqlParser(new DataInputStream(new FileInputStream(args[0])));
			}

			// Read all SQL statements from input
			MStatement st;
			while ((st = p.readStatement()) != null) {

				System.out.println(st.toString()); // Display the statement

				if (st instanceof MQuery) { // An SQL query: query the DB
					queryDB((MQuery) st);
				} else if (st instanceof MInsert) { // An SQL insert
					insertDB((MInsert) st);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Query the database
	 */
	static void queryDB(MQuery q) throws Exception {

		Vector sel = q.getSelect(); // SELECT part of the query
		Vector from = q.getFrom(); // FROM part of the query
		MExpression where = (MExpression) q.getWhere(); // WHERE part of the
														// query

		if (from.size() > 1) {
			throw new SQLException("Joins are not supported");
		}

		// Retrieve the table name in the FROM clause
		MFromItem table = (MFromItem) from.elementAt(0);

		// We suppose the data is in a text file called <tableName>.db
		// <tableName> is the table name in the FROM clause
		BufferedReader db = new BufferedReader(new FileReader(table.getTable()
				+ ".db"));

		// Read the column names (the 1st line of the .db file)
		MTuple tuple = new MTuple(db.readLine());

		MEval evaluator = new MEval();

		// Now, each line in the .db file is a tuple
		String tpl;
		while ((tpl = db.readLine()) != null) {

			tuple.setRow(tpl);

			// Evaluate the WHERE expression for the current tuple
			// Display the tuple if the condition evaluates to true

			if (where == null || evaluator.eval(tuple, where)) {
				DisplayTuple(tuple, sel);
			}

		}

		db.close();
	}

	/**
	 * Display a tuple, according to a SELECT map
	 */
	static void DisplayTuple(MTuple tuple, Vector map) throws Exception {

		// If it is a "select *", display the whole tuple
		if (((MSelectItem) map.elementAt(0)).isWildcard()) {
			System.out.println(tuple.toString());
			return;
		}

		MEval evaluator = new MEval();

		// Evaluate the value of each select item
		for (int i = 0; i < map.size(); i++) {

			MSelectItem item = (MSelectItem) map.elementAt(i);
			System.out.print(evaluator
					.evalExpValue(tuple, item.getExpression()).toString());

			if (i == map.size() - 1)
				System.out.println("");
			else
				System.out.print(", ");
		}
	}

	static void insertDB(MInsert ins) throws Exception {
		System.out.println("Should implement INSERT here");
		System.out.println(ins.toString());
	}

};
