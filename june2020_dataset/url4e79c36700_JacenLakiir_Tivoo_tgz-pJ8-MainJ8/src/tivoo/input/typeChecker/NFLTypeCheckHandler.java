package tivoo.input.typeChecker;

import java.util.HashSet;
import org.xml.sax.SAXException;
import tivoo.input.parserHandler.ElementHandler;


public class NFLTypeCheckHandler extends TypeCheckHandler
{
    private enum Element
    {
        TITLE, START, END, LOCATION;
    }

    private HashSet<Element> seen;


    public NFLTypeCheckHandler ()
    {
        addElementHandler("row", new EventElementHandler());
        addElementHandler("Col1", new TitleElementHandler());
        addElementHandler("Col8", new StartElementHandler());
        addElementHandler("Col9", new EndElementHandler());
        addElementHandler("Col15", new LocationElementHandler());
        seen = new HashSet<Element>();
    }

    private class EventElementHandler extends ElementHandler
    {
        @Override
        public void endElement () throws SAXException
        {
            if (seen.contains(Element.TITLE) && seen.contains(Element.START) &&
                seen.contains(Element.END) && seen.contains(Element.LOCATION))
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
            seen.add(Element.START);
        }
    }

    private class EndElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            seen.add(Element.END);
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
}
