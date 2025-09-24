package tivoo.output;

import java.util.List;
import java.util.Map;
import tivoo.Event;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Table;
import com.hp.gagawa.java.elements.Td;
import com.hp.gagawa.java.elements.Th;
import com.hp.gagawa.java.elements.Tr;

public class HorizontalViewHTMLBuilder extends HTMLBuilder
{
    
    private static final String TITLE = "Horizontal Week View";
    private static final String UNIQUE_CSS = "../css/horizontalStyle.css";

    public HorizontalViewHTMLBuilder (String summaryPageFileName)
    {
        super(summaryPageFileName);
    }
    
    @Override
    protected Table buildView (List<Event> eventList)
    {
        Table weekView = new Table();
        weekView.setTitle("horizontalView");
        weekView.setCellspacing("0");
        
        Tr tableHeading = buildHorizontalTableHeading();
        weekView.appendChild(tableHeading);
        
        Map<String, List<Event>> sortedEvents = sortByDayOfWeek(eventList);
        Tr tableRow = buildHorizontalTableRow(sortedEvents);
        weekView.appendChild(tableRow);
        
        return weekView;
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

    private Tr buildHorizontalTableHeading ()
    {
        Tr headingRow = new Tr();
        for (String day : DAYS_LIST)
        {
            Th heading = new Th().setCSSClass("day").appendText(day);
            headingRow.appendChild(heading);
        }
        return headingRow;
    }
    
    private Tr buildHorizontalTableRow (Map<String, List<Event>> sortedEvents)
    {
        Tr tableRow = new Tr();
        for (String day : DAYS_LIST)
        {
            List<Event> eventsOnThisDay = sortedEvents.get(day);
            Td dayCal = buildHorizontalDayCalendar(day, eventsOnThisDay);
            tableRow.appendChild(dayCal);
        }
        return tableRow;
    }

    private Td buildHorizontalDayCalendar (String day, List<Event> eventsOnThisDay)
    {
        boolean columnClass = (DAYS_LIST.indexOf(day) % 2 == 0);
        Td dayCal = columnClass ? new Td().setCSSClass("gr1") : new Td().setCSSClass("gr1alt");
        Div dayDiv = constructHorizontalDayDiv(eventsOnThisDay);
        dayCal.appendChild(dayDiv);
        return dayCal;
    }

    private Div constructHorizontalDayDiv (List<Event> eventsOnThisDay)
    {
        Div dayEvents = new Div().setId("dayEvents");
        if (eventsOnThisDay != null)
        {
            for (Event currentEvent : eventsOnThisDay)
            {
                String time = formatClockTimespan(currentEvent);
                Div eventInfo = constructEventDiv(currentEvent, time);
                dayEvents.appendChild(eventInfo);
            }
        }
        return dayEvents;
    }
    
}
