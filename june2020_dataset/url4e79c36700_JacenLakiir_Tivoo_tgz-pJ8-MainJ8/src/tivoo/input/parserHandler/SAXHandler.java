package tivoo.input.parserHandler;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public abstract class SAXHandler extends DefaultHandler
{
    private LinkedList<ElementHandler> elementStack =
        new LinkedList<ElementHandler>();
    private HashMap<String, ElementHandler> elementHandlers =
        new HashMap<String, ElementHandler>();


    public void addElementHandler (String qualifiedName, ElementHandler handler)
    {
        elementHandlers.put(qualifiedName, handler);
    }


    @Override
    public void startElement (String namespace,
                              String localName,
                              String qualifiedName,
                              Attributes attributes) throws SAXException
    {
        ElementHandler currentHandler = elementHandlers.get(qualifiedName);
        if (currentHandler != null)
        {
            currentHandler.startElement(attributes);
        }
        elementStack.push(currentHandler);
    }


    @Override
    public void characters (char[] ch, int start, int length)
        throws SAXException
    {
        ElementHandler currentHandler = elementStack.peek();
        if (currentHandler != null)
        {
            currentHandler.characters(ch, start, length);
        }
    }


    @Override
    public void endElement (String namespace,
                            String localName,
                            String qualifiedName) throws SAXException
    {
        ElementHandler currentHandler = elementStack.poll();
        if (currentHandler != null)
        {
            currentHandler.endElement();
        }
    }


    @Override
    public InputSource resolveEntity (String publicId, String systemId)
        throws IOException,
            SAXException
    {
        return new InputSource(new StringReader(""));
    }
}
