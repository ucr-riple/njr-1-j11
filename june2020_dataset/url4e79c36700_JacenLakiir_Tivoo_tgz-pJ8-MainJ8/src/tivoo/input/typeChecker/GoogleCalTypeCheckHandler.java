package tivoo.input.typeChecker;

import java.util.HashSet;
import tivoo.input.parserHandler.ElementHandler;


public class GoogleCalTypeCheckHandler extends TypeCheckHandler
{
    private enum Element
    {
        TITLE, CONTENT, AUTHOR;
    }

    private HashSet<Element> seen;


    public GoogleCalTypeCheckHandler ()
    {
        addElementHandler("entry", new EventElementHandler());
        addElementHandler("title", new TitleElementHandler());
        addElementHandler("content", new ContentElementHandler());
        addElementHandler("name", new AuthorElementHanlder());
        seen = new HashSet<Element>();
    }

    private class EventElementHandler extends ElementHandler
    {
        @Override
        public void endElement () throws TypeMatchedException
        {
            if (seen.contains(Element.TITLE) && seen.contains(Element.AUTHOR) &&
                seen.contains(Element.CONTENT))
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

    private class AuthorElementHanlder extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            seen.add(Element.AUTHOR);
        }
    }

    private class ContentElementHandler extends ElementHandler
    {
        @Override
        public void endElement ()
        {
            seen.add(Element.CONTENT);
        }
    }
}
