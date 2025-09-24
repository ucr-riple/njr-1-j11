package tivoo;

import java.io.IOException;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import tivoo.filtering.*;
import tivoo.input.CalendarParser;
import tivoo.output.*;


public class TivooSystem
{
    private List<Event> events = new LinkedList<Event>();


    public void loadFile (String fileName)
        throws SAXException,
            IOException,
            ParserConfigurationException
    {
        events.addAll(CalendarParser.parse(fileName));
    }


    public void clearEvents ()
    {
        events.clear();
    }


    public void filterByKeyword (String keyword, boolean inEvent)
    {
        Filter filter = new Filter();
        events = filter.filterByKeyword(keyword, events, inEvent);
    }


    public void filterByKeyword (String attribute, String keyword)
    {
        Filter filter = new Filter();
        events = filter.filterByKeyword(attribute, keyword, events);
    }


    public void filterByTimeFrame (Calendar startTime, Calendar endTime)
    {
        Filter filter = new Filter();
        events = filter.filterByTimeFrame(startTime, endTime, events);
    }


    public void sortByTitle (boolean reversed)
    {
        Filter filter = new Filter();
        Comparator<Event> comp = new TitleComparator(reversed);
        filter.sort(events, comp);
    }


    public void sortByStartTime (boolean reversed)
    {
        Filter filter = new Filter();
        Comparator<Event> comp = new StartTimeComparator(reversed);
        filter.sort(events, comp);
    }


    public void sortByEndTime (boolean reversed)
    {
        Filter filter = new Filter();
        Comparator<Event> comp = new EndTimeComparator(reversed);
        filter.sort(events, comp);
    }


    public void outputHorizontalView (String summaryPageFileName)
        throws IOException
    {
        HTMLBuilder output = new HorizontalViewHTMLBuilder(summaryPageFileName);
        output.buildHTML(events);
    }


    public void outputVerticalView (String summaryPageFileName)
        throws IOException
    {
        HTMLBuilder output = new VerticalViewHTMLBuilder(summaryPageFileName);
        output.buildHTML(events);
    }


    public void outputSortedView (String summaryPageFileName)
        throws IOException
    {
        HTMLBuilder output = new SortedViewHTMLBuilder(summaryPageFileName);
        output.buildHTML(events);
    }


    public void outputConflictView (String summaryPageFileName)
        throws IOException
    {
        HTMLBuilder output = new ConflictViewHTMLBuilder(summaryPageFileName);
        output.buildHTML(events);
    }


    public void outputCalendarView (String summaryPageFileName)
        throws IOException
    {
        HTMLBuilder output = new CalendarViewHTMLBuilder(summaryPageFileName);
        output.buildHTML(events);
    }
}
