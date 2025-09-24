package coursescheduleapp.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTML;

import coursescheduleapp.model.Course;
import coursescheduleapp.model.CourseData;
import coursescheduleapp.model.Day;
import coursescheduleapp.model.Schedule;
import coursescheduleapp.model.TimeCollection;
import coursescheduleapp.model.TimeDayCollection;
import coursescheduleapp.model.TimePeriod;

public class OrderedSchedView implements SchedViewI {

	@Override
	public void getResults(Course curr, StyledDocument doc) {
		// TODO Auto-generated method stub

		// Load the default style and add it as the "regular" text
		Style def = StyleContext.getDefaultStyleContext().getStyle( StyleContext.DEFAULT_STYLE );

		Style regular = doc.addStyle( "regular", def );

		// Create a bold style
		Style bold = doc.addStyle( "bold", regular );
		StyleConstants.setBold( bold, true );
		
		

		Map<String,Object> data = curr.getCourseInfo();

		Set<String> keys = data.keySet();
		Iterator<String> kIter = keys.iterator();

		while(kIter.hasNext())
		{
			String key = kIter.next();

			try {
				

				if (key == CourseData.INSTRUCTOR.toString())
				{
				}
				else
				{
					doc.insertString( doc.getLength(), key, bold);
					doc.insertString(doc.getLength(), ": ",regular);

					String val = curr.getValue(key).toString().replace("[", "");
					val = val.replace("]", "");
					
					
					doc.insertString(doc.getLength(), val + "  ", regular);
				}

			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		Object ins = curr.getValue(CourseData.INSTRUCTOR.toString());
		
		if (ins != null)
		{
			
			try {
				doc.insertString( doc.getLength(), CourseData.INSTRUCTOR.toString(), bold);
				doc.insertString(doc.getLength(), ": ",regular);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String val = curr.getValue(CourseData.INSTRUCTOR.toString()).toString().replace("[", "");
			val = val.replace("]", "");
			
			ArrayList<String> names = (ArrayList<String>) ins;

			for (int i = 0; i < names.size(); i++)
			{

				//ArrayList<String> names = parseNames(val);

				//String text = names.get(i);
				String text = parseNames(names.get(i));


				String url = "http://google.com/search?btnI=1&q=site%3Ahttp%3A//www.ratemyprofessors.com/ShowRatings.jsp%3Ftid%3D%20%22York%20College%20of%20Pennsylvania%22" + text;

				text = text.replace("%20", " ").trim();


				try {

					SimpleAttributeSet attr = new SimpleAttributeSet();
					attr.addAttribute(StyleConstants.Underline, Boolean.TRUE);
					attr.addAttribute(StyleConstants.Foreground, Color.BLUE);


					SimpleAttributeSet hrefAttr = new SimpleAttributeSet();
					hrefAttr.addAttribute(HTML.Attribute.HREF, url.toString());



					SimpleAttributeSet attrs = new SimpleAttributeSet();
					attrs.addAttribute(HTML.Tag.A, hrefAttr);

					// Add the text along with its attributes.

					int offset = doc.getLength();
					int length = text.length();

					doc.insertString(doc.getLength(), text, attr);


					doc.setCharacterAttributes(offset, length, attrs, false);
					//doc.setCharacterAttributes(offset, length, attr, false);


				}
				catch (BadLocationException e) {
					e.printStackTrace(System.err);
				}        

			}

			try {
				doc.insertString(doc.getLength(), "  ", regular);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private String parseNames(String val) {
		// TODO Auto-generated method stub

		if (val.contains("Staff"))
		{
			return "%20Staff";
		}
		String name = "%20";
		//name = name.replace("(P)", "");



		StringTokenizer s = new StringTokenizer(val.replace("(P)", "").trim());
		
		
		if (s.countTokens() == 3)
		{
			name += s.nextToken();
			s.nextToken(); // the middle initial skip...
			name += "%20" + s.nextToken();
		}
		else if (s.countTokens() == 2)
		{
			name += s.nextToken();
			name += "%20" + s.nextToken();
		}

		return name;
	}
	
	
	
	private ArrayList<Course> orderCoursesByDay(Schedule sched)
	{
		ArrayList<Course> sortedSched = new ArrayList<Course>();

		TimeDayCollection schedColl = new TimeDayCollection();
		Iterator<Course> it = sched.getCourses().iterator();

		// Iterate over each course to create a time table for the days and times
		// of the courses.
		while(it.hasNext()) 
		{
			Course curr = it.next();
			TimePeriod p = (TimePeriod)curr.getValue(CourseData.TIME.toString());
			p.setPayload(curr);
			schedColl.addTimeCollection(p,(ArrayList<Day>)curr.getValue(CourseData.DAY.toString()));
		}

		for (Day d: Day.values())
		{
			TimeCollection timeColl = schedColl.getCollection(d);
			//Next Day
			sortedSched.add(null);
			for(TimePeriod t : timeColl.getCollection())
			{
				sortedSched.add((Course)t.getPayload());
			}

		}


		return sortedSched;

	}

	public List<Course> orderSched(Schedule sched) {
		
		return orderCoursesByDay(sched);
		// TODO Auto-generated method stub
		
	}

}
