import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sg.edu.nus.cs5344.spring14.twitter.CmdArguments;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Day;
import sg.edu.nus.cs5344.spring14.twitter.datastructure.Time;



public class LocalTests {

	public static void main(String[] args) throws FileNotFoundException, ParseException {

		CmdArguments.instantiate(new String[]{"-d","/datalol"});
		System.out.println(CmdArguments.getArgs().getWorkingDir());

		System.out.println((new SimpleDateFormat("yyyy-MM-dd")).parse("2012-04-01").getTime());

		Date date = (new SimpleDateFormat("yyyy-MM-dd")).parse("2012-04-01");
		System.out.println(date);
		System.out.println(new Day(new Time(date.getTime())));


		date = (new SimpleDateFormat("yyyy-MM-dd")).parse("2012-09-06");
		System.out.println(date);
		System.out.println(new Day(new Time(date.getTime())));
	}
}
