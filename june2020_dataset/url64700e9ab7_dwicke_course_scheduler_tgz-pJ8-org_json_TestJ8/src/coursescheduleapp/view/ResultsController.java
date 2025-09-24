package coursescheduleapp.view;

import java.awt.Point;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import coursescheduleapp.model.Course;
import coursescheduleapp.model.CoursePref;
import coursescheduleapp.model.CourseSelection;
import coursescheduleapp.model.EquivCourseSelection;
import coursescheduleapp.model.Model;

public class ResultsController {


	public void removeCourse(Course course, Model model)
	{
		Point indices = getCourseLoc(course, model.getPrefSet().getCoursePref());

		model.getPrefSet().getCoursePref().getCourseSelection(indices.x, indices.y).removeCourse(course);


	}

	private Point getCourseLoc(Course c, CoursePref coursePref)
	{
		int index = 0;
		Point p = new Point();
		Iterator<EquivCourseSelection> listI = coursePref.getAllCourses().iterator();

		while(listI.hasNext())
		{
			EquivCourseSelection curr = listI.next();
			Iterator<CourseSelection> currI = curr.getCourseSelectionList().iterator();

			boolean addedCourse = false;
			int courseSelectIndex = 0;
			while(currI.hasNext())
			{
				CourseSelection csels = currI.next();
				if (csels.hasCourse(c) == true)
				{
					System.out.println("The index of the course " + index);
					p = new Point(index, courseSelectIndex);
					return p;
				}
				courseSelectIndex++;
			}
			index++;
		}

		return p;
	}
	public boolean checkIfLast(Course course, Model model)
	{
		Point index = getCourseLoc(course, model.getPrefSet().getCoursePref());


		int numSelection = model.getPrefSet().getCoursePref().getEquivCourses(index.x).getNumCourseSelection();
		int numCourses = 0; 
		for (int i = 0; i < numSelection; i++)
		{
			numCourses += model.getPrefSet().getCoursePref().getCourseSelection(index.x, i).getNumCourses();

			//System.out.println(model.getPrefSet().getCoursePref().getCourseSelection(index, i).removeCourse(course));
		}
		System.out.println(numCourses);
		// No courses left
		if (numCourses <= 1)
		{
			return true;
		}
		// courses left
		return false;
	}
	/*
	public void getResults(Course curr, StyledDocument doc)
	{ 


		// Load the default style and add it as the "regular" text
		Style def = StyleContext.getDefaultStyleContext().getStyle( StyleContext.DEFAULT_STYLE );

		Style regular = doc.addStyle( "regular", def );

		// Create a bold style
		Style bold = doc.addStyle( "bold", regular );
		StyleConstants.setBold( bold, true );
<<<<<<< .mine





=======
>>>>>>> .r125



		Map<String,Object> data = curr.getCourseInfo();

		Set<String> keys = data.keySet();
		Iterator<String> kIter = keys.iterator();

		while(kIter.hasNext())
		{
			String key = kIter.next();

			try {


				if (key == CourseData.INSTRUCTOR.toString())
				{
<<<<<<< .mine
					doc.insertString(doc.getLength(), ": ",regular);

					ArrayList<String> names = parseNames(val);

					for (int i = 0; i < names.size(); i++)
					{
						String text = names.get(i);
						
						
						
						String url = "http://google.com/search?btnI=1&q=site%3Ahttp%3A//www.ratemyprofessors.com/ShowRatings.jsp%3Ftid%3D%20%22York%20College%20of%20Pennsylvania%22" + text;
						
						text = text.replace("%20", " ");

=======
				}
				else
				{
					doc.insertString( doc.getLength(), key, bold);
					doc.insertString(doc.getLength(), ": ",regular);

					String val = curr.getValue(key).toString().replace("[", "");
					val = val.replace("]", "");
					
					
					doc.insertString(doc.getLength(), val + "  ", regular);
				}
>>>>>>> .r125

<<<<<<< .mine
						try {
							// First, setup the href attribute for <A> tag.
							SimpleAttributeSet hrefAttr = new SimpleAttributeSet();
							
							
							
							hrefAttr.addAttribute(HTML.Attribute.HREF, url.toString().trim());
							
							// Second, setup the <A> tag
							SimpleAttributeSet attrs = new SimpleAttributeSet();
							attrs.addAttribute(HTML.Tag.A, hrefAttr);
							
							
=======
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
>>>>>>> .r125

<<<<<<< .mine
							// Add the text along with its attributes.
							doc.insertString(doc.getLength(), text.trim(), attrs);
						}
						catch (BadLocationException e) {
							e.printStackTrace(System.err);
						}        
=======
>>>>>>> .r125

<<<<<<< .mine
					}

					doc.insertString(doc.getLength(), "  ", regular);


				}
				else
				{
=======
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
>>>>>>> .r125

<<<<<<< .mine
					doc.insertString(doc.getLength(), ": " + val + "  ", regular);
=======
				//ArrayList<String> names = parseNames(val);

				//String text = names.get(i);
				String text = parseNames(names.get(i));


				String url = "http://google.com/search?btnI=1&q=site%3Ahttp%3A//www.ratemyprofessors.com/ShowRatings.jsp%3Ftid%3D%20%22York%20College%20of%20Pennsylvania%22" + text;

				text = text.replace("%20", " ").trim();


				try {

					SimpleAttributeSet attr = new SimpleAttributeSet();
					attr.addAttribute(StyleConstants.CharacterConstants.Underline, Boolean.TRUE);
					attr.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.BLUE);


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


>>>>>>> .r125
				}
<<<<<<< .mine

=======
				catch (BadLocationException e) {
					e.printStackTrace(System.err);
				}        

			}

			try {
				doc.insertString(doc.getLength(), "  ", regular);
>>>>>>> .r125
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	private String parseNames(String val) {
		// TODO Auto-generated method stub

		String name = "%20";
		//name = name.replace("(P)", "");



		StringTokenizer s = new StringTokenizer(val.replace("(P)", "").trim());
		if (s.countTokens() == 3)
		{
			name += s.nextToken();
			s.nextToken(); // the middle initial skip...
			name += "%20" + s.nextToken();
		}

		return name;
	}
<<<<<<< .mine
	private ArrayList<String> parseNames(String val) {
		// TODO Auto-generated method stub

		ArrayList<String> names = new ArrayList<String>();
		names.add(new String());

		StringTokenizer s = new StringTokenizer(val);
		while(s.hasMoreTokens())
		{
			String curr = s.nextToken();
			if (curr.contains("(") && s.hasMoreTokens());
			{
				String next = s.nextToken();
				if (next.contains(",") && s.hasMoreTokens())
				{
					names.add(new String());
				}
			}

			if (curr.contains("."))
			{

			}
			else
			{
				names.set((names.size() - 1), names.get((names.size() - 1)) + "%20" + curr);
			}
		}


		return names;
	}

=======

*/

	public void print(JTextPane printTxtPane) {
		// TODO Auto-generated method stub
		try {
			printTxtPane.print();

		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to print.", "Error",JOptionPane.ERROR_MESSAGE);
		}

	}

	public void save(File f, String text)
	{
		try {
			java.io.FileWriter wr = new java.io.FileWriter(f);

			wr.write(text);
			wr.flush();
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{

		}

	}
/*
	public ArrayList<Course> orderCoursesByDay(Schedule sched)
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
	*/

}
