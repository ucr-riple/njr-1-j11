package tivoo.input.parserHandler;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import org.xml.sax.Attributes;
import tivoo.Event;


public class NFLCalParserHandler extends ParserHandler
{
    private Event currentEvent;
    private List<Event> events;
    private Calendar currentCalendar;


    public NFLCalParserHandler ()
    {
        addElementHandler("row", new EventElementHandler());
        addElementHandler("Col1", new TitleElementHandler(this));
        addElementHandler("Col8", new StartTimeElementHandler());
        addElementHandler("Col9", new EndTimeElementHandler());
        addElementHandler("Col15", new LocationElementHandler());
        events = new LinkedList<Event>();
    }


    @Override
    public List<Event> getEvents ()
    {
        return events;
    }

    private class EventElementHandler extends ElementHandler
    {
        @Override
        public void startElement (Attributes attributes)
        {
            currentEvent = new Event();
        }


        @Override
        public void endElement ()
        {
            currentEvent.setDescription("N/A");
            events.add(currentEvent);
        }
    }


    public void setTitle (String title)
    {
        currentEvent.setTitle(title);
    }

    private class StartTimeElementHandler extends ElementHandler
    {
        @Override
        public void startElement (Attributes attributes)
        {
            currentCalendar = Calendar.getInstance();
        }


        @Override
        public void endElement ()
        {
            currentEvent.setStartTime(currentCalendar);
        }


        public void characters (char[] ch, int start, int length)
        {
            setTime(currentCalendar, new String(ch, start, length));
        }
    }

    private class EndTimeElementHandler extends ElementHandler
    {
        @Override
        public void startElement (Attributes attributes)
        {
            currentCalendar = Calendar.getInstance();
        }


        @Override
        public void endElement ()
        {
            currentEvent.setEndTime(currentCalendar);
        }


        @Override
        public void characters (char[] ch, int start, int length)
        {
            setTime(currentCalendar, new String(ch, start, length));
        }
    }

    private class LocationElementHandler extends ElementHandler
    {
        @Override
        public void characters (char[] ch, int start, int length)
        {
            currentEvent.setLocation(new String(ch, start, length));
        }

    }


    private void setTime (Calendar cal, String date)
    {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        int hour = Integer.parseInt(date.substring(11, 13)) + 12;
        int minute = Integer.parseInt(date.substring(14, 16));
        int second = Integer.parseInt(date.substring(17, 19));

        cal.set(year, month, day, hour, minute, second);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
}
