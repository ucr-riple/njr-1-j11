package tivoo.input.parserHandler;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import org.xml.sax.Attributes;
import tivoo.Event;


public class DukeBasketballCalHandler extends ParserHandler
{
    private Event currentEvent;
    private List<Event> events;
    private String currentDate;
    private String currentTime;
    private Calendar currentCalendar;


    public DukeBasketballCalHandler ()
    {
        addElementHandler("Calendar", new EventElementHandler());
        addElementHandler("Subject", new TitleElementHandler(this));
        addElementHandler("StartTime", new StartTimeElementHandler());
        addElementHandler("StartDate", new StartDateElementHandler());
        addElementHandler("EndTime", new EndTimeElementHandler());
        addElementHandler("EndDate", new EndDateElementHandler());
        addElementHandler("Location", new LocationElementHandler());
        addElementHandler("Description", new DescriptionElementHandler());
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
        public void startElement (Attributes attribute)
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

    private class DescriptionElementHandler extends ElementHandler
    {
        @Override
        public void characters (char[] ch, int start, int length)
        {
            currentEvent.setDescription(new String(ch, start, length));
        }
    }

    private class StartDateElementHandler extends ElementHandler
    {
        @Override
        public void startElement (Attributes attributes)
        {
            currentCalendar = Calendar.getInstance();
        }


        @Override
        public void characters (char[] ch, int start, int length)
        {

            currentDate = new String(ch, start, length);
        }
    }

    private class StartTimeElementHandler extends ElementHandler
    {
        @Override
        public void characters (char[] ch, int start, int length)
        {
            currentTime = new String(ch, start, length);
        }


        @Override
        public void endElement ()
        {
            setTime(currentCalendar, currentTime, currentDate);
            currentEvent.setStartTime(currentCalendar);
        }
    }

    private class EndDateElementHandler extends ElementHandler
    {
        @Override
        public void startElement (Attributes attributes)
        {
            currentCalendar = Calendar.getInstance();
        }


        @Override
        public void characters (char[] ch, int start, int length)
        {
            currentTime = new String(ch, start, length);
        }
    }

    private class EndTimeElementHandler extends ElementHandler
    {
        @Override
        public void characters (char[] ch, int start, int length)
        {
            currentTime = new String(ch, start, length);
        }


        @Override
        public void endElement ()
        {
            setTime(currentCalendar, currentTime, currentDate);
            currentEvent.setEndTime(currentCalendar);
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


    private void setTime (Calendar cal, String time, String date)
    {
        String[] myDate = date.split("/");
        String[] myTime = time.split(":");
        int year = Integer.parseInt(myDate[2]);
        int month = Integer.parseInt(myDate[0]);
        int day = Integer.parseInt(myDate[1]);
        int hour = Integer.parseInt(myTime[0]);
        int minuete = Integer.parseInt(myTime[1]);
        int second = Integer.parseInt(myTime[2].substring(0, 2));
        String convertTwelve = myTime[2].substring(3, 5);
        if (convertTwelve.equals("PM"))
        {
            hour += 12;
        }
        cal.set(year, month, day, hour, minuete, second);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
}
