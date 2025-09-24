package tivoo.input.typeChecker;

import java.util.HashSet;
import org.xml.sax.SAXException;
import tivoo.input.parserHandler.ElementHandler;


public class DukeCalTypeCheckHandler extends TypeCheckHandler
{
    private enum Element
    {
        TITLE, START, END, TIME, LOCATION, DESCRIPTION;
    }

    private HashSet<Element> seen;


    public DukeCalTypeCheckHandler ()
    {
        addElementHandler("event", new EventElementHandler());
        addElementHandler("summary", new TitleElementHandler());
        addElementHandler("start", new StartElementHandler());
        addElementHandler("end", new EndElementHandler());
        addElementHandler("utcdate", new TimeElementHandler());
        addElementHandler("address", new LocationElementHandler());
        addElementHandler("description", new DescriptionElementHandler());
        seen = new HashSet<Element>();
    }

    private class EventElementHandler extends ElementHandler
    {
        @Override
        public void endElement () throws SAXException
        {
            if (seen.contains(Element.TITLE) && seen.contains(Element.START) &&
                seen.contains(Element.END) && seen.contains(Element.LOCATION) &&
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

    private class StartElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            if (seen.contains(Element.TIME))
            {
                seen.add(Element.START);
                seen.remove(Element.TIME);
            }
        }
    }

    private class EndElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            if (seen.contains(Element.TIME))
            {
                seen.add(Element.END);
                seen.remove(Element.TIME);
            }
        }
    }

    private class TimeElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            seen.add(Element.TIME);
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
