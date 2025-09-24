package tivoo.input.parserHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import org.xml.sax.Attributes;
import tivoo.Event;


public class GoogleCalParserHandler extends ParserHandler
{
    private List<Event> events;
    private Event currentEvent;
    private Calendar startCalendar;
    private Calendar endCalendar;


    public GoogleCalParserHandler ()
    {
        addElementHandler("entry", new EventElementHandler());
        addElementHandler("title", new TitleElementHandler(this));
        addElementHandler("content", new ContentElementHandler());
        addElementHandler("name", new AuthorElementHanlder());
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
            if (currentEvent.getDescription() == null)
            {
                currentEvent.setDescription("");
            }
            if (currentEvent.getLocation() == null)
            {
                currentEvent.setLocation("");
            }
            events.add(currentEvent);
        }
    }


    public void setTitle (String title)
    {
        if (currentEvent != null) currentEvent.setTitle(title);
    }

    private String recurringTime;

    private class AuthorElementHanlder extends ElementHandler
    {
        @Override
        public void characters (char[] ch, int start, int length)
        {
            if (currentEvent != null) currentEvent.put("author",
                                                       new String(ch,
                                                                  start,
                                                                  length));
        }
    }

    private class ContentElementHandler extends ElementHandler
    {
        @Override
        public void startElement (Attributes attributes)
        {
            startCalendar = Calendar.getInstance();
            endCalendar = Calendar.getInstance();
            recurringTime = "";
        }


        @Override
        public void characters (char[] ch, int start, int length)
        {
            String content = new String(ch, start, length);
            if (content.startsWith("When"))
            {
                setTime(startCalendar, endCalendar, content.substring(6));
                currentEvent.setStartTime(startCalendar);
                currentEvent.setEndTime(endCalendar);
            }
            else if (content.contains("First"))
            {
                recurringTime = content.substring(14);
            }
            else if (content.contains("Duration"))
            {
                setRecurringEventTime(startCalendar,
                                      endCalendar,
                                      recurringTime,
                                      content);
                currentEvent.setStartTime(startCalendar);
                currentEvent.setEndTime(endCalendar);
            }
            else if (content.contains("where"))
            {
                currentEvent.setLocation(content);
            }
            else if (content.startsWith("Event Description"))
            {
                currentEvent.setDescription(content);
            }

        }

    }


    public void setTime (Calendar startCal, Calendar endCal, String dateTime)
    {
        String[] dateTimes = dateTime.split("\\s+");
        try
        {
            int year = Integer.parseInt(dateTimes[3]);
            int day = Integer.parseInt(dateTimes[2].replace(",", ""));
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
            int month = monthFormat.parse(dateTimes[1]).getMonth();
            SimpleDateFormat sdf;
            if (dateTimes.length > 4)
            {
                if (dateTimes[4].contains(":")) sdf =
                    new SimpleDateFormat("hh:mmaa");
                else sdf = new SimpleDateFormat("hhaa");
                int startHour = sdf.parse(dateTimes[4]).getHours();
                int startMinute = sdf.parse(dateTimes[4]).getMinutes();

                if (dateTimes[6].contains(":")) sdf =
                    new SimpleDateFormat("hh:mmaa");
                else sdf = new SimpleDateFormat("hhaa");
                int endHour = sdf.parse(dateTimes[6]).getHours();
                int endMinute = sdf.parse(dateTimes[6]).getMinutes();
                startCal.set(year, month, day, startHour, startMinute);
                endCal.set(year, month, day, endHour, endMinute);
            }
            else
            {
                startCal.set(year, month, day);
                endCal.set(year, month, day);
            }

            startCal.setTimeZone(TimeZone.getTimeZone("UTC"));
            endCal.setTimeZone(TimeZone.getTimeZone("UTC"));

        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public void setRecurringEventTime (Calendar startCal,
                                       Calendar endCal,
                                       String time,
                                       String eventDuration)
    {
        //2011-08-29 10:05:00 EDT Duration: 5400
        int year = Integer.parseInt(time.substring(0, 4));
        int month = Integer.parseInt(time.substring(5, 7));
        int day = Integer.parseInt(time.substring(8, 10));
        int startHour = Integer.parseInt(time.substring(11, 13));
        int startMinute = Integer.parseInt(time.substring(14, 16));
        double duration = Integer.parseInt(eventDuration.substring(11, 15));
        double result = duration / 3600;
        int endMinute = (int) (startMinute + (result - (int) result) * 60);
        int endHour = 0;
        if (endMinute < 60)
        {
            endHour = startHour + (int) result;
        }
        else
        {
            endHour = startHour + (int) result + 1;
            endMinute = endMinute - 60;
        }
        Calendar firstCal = Calendar.getInstance();
        firstCal.set(year, month, day);
        int dayOfWeek = firstCal.get(Calendar.DAY_OF_WEEK);
        startCal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        startCal.set(Calendar.HOUR_OF_DAY, startHour);
        startCal.set(Calendar.MINUTE, startMinute);
        endCal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        endCal.set(Calendar.HOUR_OF_DAY, endHour);
        endCal.set(Calendar.MINUTE, endMinute);
    }
}
