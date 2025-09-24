package tivoo.output;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import tivoo.Event;
import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.B;
import com.hp.gagawa.java.elements.Br;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Li;
import com.hp.gagawa.java.elements.P;
import com.hp.gagawa.java.elements.Ul;

public class CalendarViewHTMLBuilder extends HTMLBuilder
{
    
    private static final String TITLE = "Calendar View";
    private static final String UNIQUE_CSS = "../css/calendarStyle.css";    
    private static final long DAY_LENGTH = 86400000L;
    private static final long WEEK_LENGTH = 604800000L;
        
    private enum Type
    {
        EMPTY, DAY, WEEK, MONTH
    }
        
    public CalendarViewHTMLBuilder (String summaryPageFileName)
    {
        super(summaryPageFileName);
    }
    
    @Override
    protected Div buildView (List<Event> eventList)
    {
        Div calendarView = new Div().setId("calendarView");
        Type calendarType = determineCalendarType(eventList);
        Node calendar = null;
        switch (calendarType)
        {
            case DAY:
                calendar = buildDayCalendar(eventList);
            case WEEK:
                calendar = buildWeekCalendar(eventList);
            case MONTH:
                calendar = buildMonthCalendar(eventList);
        }
        calendarView.appendChild(calendar);
        return calendarView;
    }

    @Override
    protected String getTitle ()
    {
        return TITLE;
    }
    
    @Override
    protected String getUniqueCSS ()
    {
        return UNIQUE_CSS;
    }
    
    private Type determineCalendarType (List<Event> eventList)
    {
        if (eventList.size() == 0)
            return CalendarViewHTMLBuilder.Type.EMPTY;
        long timeframe = determineTimeframe(eventList);
        if (timeframe <= DAY_LENGTH)
            return CalendarViewHTMLBuilder.Type.DAY;
        if (timeframe <= WEEK_LENGTH)
            return CalendarViewHTMLBuilder.Type.WEEK;
        return CalendarViewHTMLBuilder.Type.MONTH;
    }

    private long determineTimeframe (List<Event> eventList)
    {
        Event first = eventList.get(0);
        Event last = eventList.get(0); 
        for (Event e : eventList)
        {
            Calendar start = e.getStartTime();
            first = (start.getTimeInMillis() < first.getStartTime().getTimeInMillis()) ? e : first;
            
            Calendar end = e.getEndTime();
            last = (end.getTimeInMillis() > last.getEndTime().getTimeInMillis()) ? e : last;
        }
        long timeframe = last.getEndTime().getTimeInMillis() - first.getStartTime().getTimeInMillis();
        return timeframe;
    }

    private Ul buildDayCalendar (List<Event> eventList)
    {
        Ul dayCalendar = new Ul().setId("dayCal");
        for (Event currentEvent : eventList)
        {
            Li eventInfo = constructEventListItem(currentEvent);
            dayCalendar.appendChild(eventInfo);
        }
        return dayCalendar;
    }
    
    private Ul buildWeekCalendar (List<Event> eventList)
    {
        Ul weekCalendar = new Ul().setId("weekCal");
        Map<String, List<Event>> sortedEvents = sortByDayOfWeek(eventList);
        for (String day : DAYS_LIST)
        {
            Ul dayInfo = constructUnorderedList(day, "dayInfo", sortedEvents);
            weekCalendar.appendChild(new Li().appendChild(dayInfo));
        }
        return weekCalendar;
    }
    
    private Ul buildMonthCalendar (List<Event> eventList)
    {
        Ul monthCalendar = new Ul().setId("monthCal");
        Map<String, List<Event>> sortedEvents = sortByDate(eventList);
        for (String date: sortedEvents.keySet())
        {
            Ul dateInfo = constructUnorderedList(date, "dateInfo", sortedEvents);
            monthCalendar.appendChild(new Li().appendChild(dateInfo));
        }
        return monthCalendar;
    }

    private Ul constructUnorderedList (String dayOrDate, String listID, Map<String, List<Event>> sortedEvents)
    {
        Ul list = new Ul().setId(listID).appendChild(new B().appendText(dayOrDate));
        List<Event> eventsOnThisDayOrDate = sortedEvents.get(dayOrDate);
        if (eventsOnThisDayOrDate != null)
        {
            for (Event currentEvent : eventsOnThisDayOrDate)
            {
                Li eventInfo = constructEventListItem(currentEvent);
                list.appendChild(eventInfo);
            }
        }
        else
        {
            list.appendChild(new Li().appendChild(new P().appendText("None")));
        }
        return list;
    }
    
    private Li constructEventListItem (Event currentEvent)
    {
        Li eventInfo = new Li().setId("event");
        
        P eventP = new P();
        eventP.appendChild(linkToDetailsPage(currentEvent));
        eventP.appendChild(new Br());
        eventP.appendText(formatClockTimespan(currentEvent));
        
        eventInfo.appendChild(eventP);
        return eventInfo;
    }
    
}
