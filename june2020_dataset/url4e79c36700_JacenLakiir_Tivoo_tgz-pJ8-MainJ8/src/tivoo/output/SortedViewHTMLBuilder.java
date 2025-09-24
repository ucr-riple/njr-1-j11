package tivoo.output;

import java.util.List;
import tivoo.Event;
import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Hr;

public class SortedViewHTMLBuilder extends HTMLBuilder
{
    
    private static final String TITLE = "Sorted View";
    private static final String UNIQUE_CSS = "../css/sortedStyle.css";
    
    public SortedViewHTMLBuilder (String summaryPageFileName)
    {
        super(summaryPageFileName);
    }

    @Override
    protected Div buildView (List<Event> eventList)
    {
        Div sortedView = new Div().setCSSClass("sortedView");
        for (Event currentEvent : eventList)
        {
            sortedView.appendChild(new Hr());
            String time = formatDateTimespan(currentEvent);
            Div eventInfo = constructEventDiv(currentEvent, time);
            sortedView.appendChild(eventInfo);
        }
        return sortedView;
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

}
