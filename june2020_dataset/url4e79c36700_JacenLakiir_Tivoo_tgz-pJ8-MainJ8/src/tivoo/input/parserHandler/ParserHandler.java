package tivoo.input.parserHandler;

import java.util.List;
import tivoo.Event;


public abstract class ParserHandler extends SAXHandler
{
    public abstract List<Event> getEvents ();
    public abstract void setTitle (String title);
}
