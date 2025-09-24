package tivoo.input.typeChecker;

import java.util.HashSet;
import org.xml.sax.SAXException;
import tivoo.input.parserHandler.ElementHandler;


public class DukeBasketballTypeCheckHandler extends TypeCheckHandler
{
    private enum Element
    {
        TITLE,
        START_DATE,
        START_TIME,
        END_DATE,
        END_TIME,
        LOCATION,
        DESCRIPTION;
    }

    private HashSet<Element> seen;


    public DukeBasketballTypeCheckHandler ()
    {
        addElementHandler("Calendar", new EventElementHandler());
        addElementHandler("Subject", new TitleElementHandler());
        addElementHandler("StartDate", new StartDateElementHandler());
        addElementHandler("StartTime", new StartTimeElementHandler());
        addElementHandler("EndDate", new EndDateElementHandler());
        addElementHandler("EndTime", new EndTimeElementHandler());
        addElementHandler("Location", new LocationElementHandler());
        addElementHandler("Description", new DescriptionElementHandler());
        seen = new HashSet<Element>();
    }

    private class EventElementHandler extends ElementHandler
    {
        public void endElement () throws SAXException
        {
            if (seen.contains(Element.TITLE) &&
                seen.contains(Element.START_DATE) &&
                seen.contains(Element.START_TIME) &&
                seen.contains(Element.END_DATE) &&
                seen.contains(Element.END_TIME) &&
                seen.contains(Element.LOCATION) &&
                seen.contains(Element.DESCRIPTION))
            {
                throw new TypeMatchedException();
            }
        }
    }

    private class TitleElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            seen.add(Element.TITLE);
        }
    }

    private class StartDateElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            seen.add(Element.START_DATE);
        }
    }

    private class StartTimeElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            seen.add(Element.START_TIME);
        }
    }

    private class EndDateElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            seen.add(Element.END_DATE);
        }
    }

    private class EndTimeElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            seen.add(Element.END_TIME);
        }
    }

    private class LocationElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            seen.add(Element.LOCATION);
        }
    }

    private class DescriptionElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            seen.add(Element.DESCRIPTION);
        }
    }
}
