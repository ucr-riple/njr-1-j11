package tivoo.input.typeChecker;

import java.util.HashSet;
import org.xml.sax.SAXException;
import tivoo.input.parserHandler.ElementHandler;


public class TVTypeCheckHandler extends TypeCheckHandler
{
    private enum Element
    {
        TITLE, SUBTITLE, DESCRIPTION;
    }

    private HashSet<Element> seen;


    public TVTypeCheckHandler ()
    {
        addElementHandler("programme", new EventElementHandler());
        addElementHandler("title", new TitleElementHandler());
        addElementHandler("desc", new DescriptionElementHandler());
        addElementHandler("sub-title", new SubTitleElementHandler());
        seen = new HashSet<Element>();
    }

    private class EventElementHandler extends ElementHandler
    {
        @Override
        public void endElement () throws SAXException
        {
            if (seen.contains(Element.TITLE) &&
                seen.contains(Element.SUBTITLE) &&
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

    private class SubTitleElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            seen.add(Element.SUBTITLE);
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
