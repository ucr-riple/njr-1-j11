
import jatrace.*;
import java.io.*;

public class MyImageTest
{
	
	
	private final static Color darkerThanBlack =  new Color(-1.00, -1.00, -1.00);
	private final static Color black =            new Color(0.001, 0.001, 0.001);
	
	private final static Color red =              new Color(0.999, 0.001, 0.001);
	private final static Color green =            new Color(0.001, 0.999, 0.001);
	private final static Color blue =             new Color(0.001, 0.001, 0.999);
	
	private final static Color white =            new Color(0.999, 0.999, 0.999);
	private final static Color lighterThanWhite = new Color(1.100, 1.100, 1.100);
	
	private final static String tempfile = "MyImageTest.tmp.png";
	private final static Color [] colors = new Color [] { darkerThanBlack, black, red, green, blue, white, lighterThanWhite };
	
	private static void deleteTemp()
	{
		try {
			String pathToTemp = new File(tempfile).getCanonicalPath();
			new File(pathToTemp).delete();
			System.out.println("Deleted '" + tempfile + "'.");
		}
		
		catch (Exception e) {
			System.out.println("Failed to delete temp file.");
		}
	}
	
	public static boolean ReadWriteTest(Color color)
	{
		myImage tmp;
		
		tmp = new myImage(1,1);
		tmp.setPixel(0,0,color);
		tmp.write(tempfile);
		
		Color read = myImage.read(tempfile).getPixel(0,0);
		
		deleteTemp();
		
		if ( color.equals(read) )
		{
			return true;
		}
		
		else
		{
			System.out.println(color + " doesn't equal " + read);
			return false;
		}
		
	}
	
	public static void main(String [] args)
	{
		int [] samples;
		
		for (Color color : colors)
		{
			ReadWriteTest(color);
			System.out.println("");
		}
		
		/*
		success = success && ReadWriteTest(darkerThanBlack);
		success = success && ReadWriteTest(black);
		success = success && ReadWriteTest(red);
		success = success && ReadWriteTest(green);
		success = success && ReadWriteTest(blue);
		success = success && ReadWriteTest(white);
		success = success && ReadWriteTest(lighterThanWhite);
		
		assert (success) : "something went wrong somewhere";
		*/
		
		
		
		
	}
	
}
