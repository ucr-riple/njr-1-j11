package coursescheduleapp;

import coursescheduleapp.model.CourseTime;
import junit.framework.TestCase;


public class CourseTimeTest extends TestCase {
	
	private CourseTime onePM; 
	private CourseTime eightAM;
	private CourseTime nineAM;
	private CourseTime twelvePM;
    private CourseTime sixThirtyPM;
    private CourseTime fiveFourtyFivePM;
    
    private CourseTime eightFiftyThreeAM;
	
	
	@Override
	protected void setUp(){
		
		
		onePM=new CourseTime(780);
		eightAM= new CourseTime(480);
		nineAM= new CourseTime(540);
		twelvePM = new CourseTime(720);
		sixThirtyPM=new CourseTime(1110);
		fiveFourtyFivePM=new CourseTime(1065);
		
		eightFiftyThreeAM = new CourseTime(8,53);
		
	}
	
	public void testSubtract(){
		
		assertEquals(onePM.subtract(eightAM),300);
		assertEquals(sixThirtyPM.subtract(nineAM), 570);
		assertEquals(eightFiftyThreeAM.subtract(eightAM), 53);	
	}
	
	public void testGetTime()
	{
		assertEquals(780, onePM.getTime());
		assertEquals(480, eightAM.getTime());
		assertEquals(540, nineAM.getTime());
		assertEquals(720, twelvePM.getTime());
		assertEquals(1110, sixThirtyPM.getTime());
		assertEquals(533, eightFiftyThreeAM.getTime());
	}
	
	public void testToString(){
		
		assertEquals(eightAM.toString(),"8:00 am");
		assertEquals(nineAM.toString(),"9:00 am" );
		assertEquals(twelvePM.toString(),"12:00 pm");
		assertEquals(sixThirtyPM.toString(),"6:30 pm");
		assertEquals(fiveFourtyFivePM.toString(),"5:45 pm");	
		assertEquals(eightFiftyThreeAM.toString(), "8:53 am");
		
		
	}
	
	
	
	
	
}
