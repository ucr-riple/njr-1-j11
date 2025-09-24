package tivoo.input.parserHandler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import org.xml.sax.Attributes;
import tivoo.Event;


public class TVParserHandler extends ParserHandler
{
    private List<Event> events;
    private Event currentEvent;
    private Calendar currentCalendar;
    private HashMap<String, String> channelMap;


    public TVParserHandler ()
    {
        addElementHandler("programme", new EventElementHandler());
        addElementHandler("title", new TitleElementHandler(this));
        addElementHandler("desc", new DescriptionElementHandler());
        addElementHandler("sub-title", new SubTitleElementHandler());
        addElementHandler("channel", new ChannelElementHandler());
        addElementHandler("display-name", new ProgrammeNameElementHandler());
        addElementHandler("director", new DirectorElementHandler());
        addElementHandler("actor", new ActorElementHandler());
        addElementHandler("category", new CategoryElementHandler());
        channelMap = new HashMap<String, String>();
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
            currentEvent.setLocation("NA");

            String startTime = attributes.getValue("start");
            currentCalendar = Calendar.getInstance();
            setTime(currentCalendar, startTime);
            currentEvent.setStartTime(currentCalendar);

            String endTime = attributes.getValue("stop");
            currentCalendar = Calendar.getInstance();
            setTime(currentCalendar, endTime);
            currentEvent.setEndTime(currentCalendar);

            String channelId = attributes.getValue("channel");
            currentEvent.setDescription("Channel: " +
                                        channelMap.get(channelId) + "<br>");
        }


        @Override
        public void endElement ()
        {
            events.add(currentEvent);
        }
    }

    public void setTitle(String title){
    	currentEvent.setTitle(title);
    }

    private class DirectorElementHandler extends ElementHandler
    {
        @Override
        public void characters (char[] ch, int start, int length)
        {
            currentEvent.put("director", new String(ch, start, length));
        }
    }

    private class ActorElementHandler extends ElementHandler
    {
        @Override
        public void characters (char[] ch, int start, int length)
        {
            String str = currentEvent.get("actor");
            if (str != null)
            {
                currentEvent.put("actor", str + " " +
                                          new String(ch, start, length));
            }
            else
            {
                currentEvent.put(str, new String(ch, start, length));
            }
        }
    }

    private class CategoryElementHandler extends ElementHandler
    {
        @Override
        public void characters (char[] ch, int start, int length)
        {
            currentEvent.put("category", new String(ch, start, length));
        }
    }

    private class SubTitleElementHandler extends ElementHandler
    {
        @Override
        public void characters (char[] ch, int start, int length)
        {
            String title = currentEvent.getTitle();
            String currentTitle = title + "--" + new String(ch, start, length);
            currentEvent.setTitle(currentTitle);
        }
    }

    private class DescriptionElementHandler extends ElementHandler
    {
        @Override
        public void characters (char[] ch, int start, int length)
        {
            currentEvent.setDescription(currentEvent.getDescription() +
                                        new String(ch, start, length));
        }
    }

    private String channelId = "";
    private int count = 0;

    private class ChannelElementHandler extends ElementHandler
    {
        @Override
        public void startElement (Attributes attributes)
        {
            channelId = attributes.getValue("id");
            count = 0;
        }


        @Override
        public void endElement ()
        {
            count = 0;
        }
    }

    private class ProgrammeNameElementHandler extends ElementHandler
    {
        @Override
        public void characters (char[] ch, int start, int length)
        {
            if (count == 4)
            {
                channelMap.put(channelId, new String(ch, start, length));
                count++;
            }
            else count++;
        }
    }


    private void setTime (Calendar cal, String gmtDate)
    {
        int year = Integer.parseInt(gmtDate.substring(0, 4));
        int month = Integer.parseInt(gmtDate.substring(4, 6));
        int day = Integer.parseInt(gmtDate.substring(6, 8));
        int hour = Integer.parseInt(gmtDate.substring(8, 10));
        int minute = Integer.parseInt(gmtDate.substring(10, 12));
        int second = Integer.parseInt(gmtDate.substring(12, 14));
        String sign = gmtDate.substring(15, 16);
        int timezone = Integer.parseInt(gmtDate.substring(16, 18));
        cal.set(year, month, day, hour, minute, second);
        cal.setTimeZone(TimeZone.getTimeZone("GMT" + sign +
                                             Integer.toString(timezone)));
    }
}
