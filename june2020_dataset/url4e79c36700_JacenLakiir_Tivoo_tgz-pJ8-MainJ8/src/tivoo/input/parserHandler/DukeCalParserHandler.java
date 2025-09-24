package tivoo.input.parserHandler;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import org.xml.sax.Attributes;
import tivoo.Event;


public class DukeCalParserHandler extends ParserHandler
{
    private List<Event> events;
    private Event currentEvent;
    private Calendar currentCalendar;


    public DukeCalParserHandler ()
    {
        addElementHandler("event", new EventElementHandler());
        addElementHandler("summary", new TitleElementHandler(this));
        addElementHandler("start", new StartElementHandler());
        addElementHandler("end", new EndElementHandler());
        addElementHandler("utcdate", new TimeElementHandler());
        addElementHandler("address", new LocationElementHandler());
        addElementHandler("description", new DescriptionElementHandler());
        events = new LinkedList<Event>();
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
            events.add(currentEvent);
        }
    }


    public void setTitle (String title)
    {
        currentEvent.setTitle(title);
    }

    private class StartElementHandler extends ElementHandler
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
    }

    private class EndElementHandler extends ElementHandler
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
    }

    private class TimeElementHandler extends ElementHandler
    {
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

    private class DescriptionElementHandler extends ElementHandler
    {
        @Override
        public void characters (char[] ch, int start, int length)
        {
            currentEvent.setDescription(new String(ch, start, length));
        }
    }


    @Override
    public List<Event> getEvents ()
    {
        return events;
    }


    private void setTime (Calendar cal, String utcDate)
    {
        int year = Integer.parseInt(utcDate.substring(0, 4));
        int month = Integer.parseInt(utcDate.substring(4, 6));
        int day = Integer.parseInt(utcDate.substring(6, 8));
        int hour = Integer.parseInt(utcDate.substring(9, 11));
        int minute = Integer.parseInt(utcDate.substring(11, 13));
        int second = Integer.parseInt(utcDate.substring(13, 15));
        cal.set(year, month, day, hour, minute, second);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
}
