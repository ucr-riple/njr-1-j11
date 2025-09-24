package tivoo.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import tivoo.Event;
import tivoo.input.parserHandler.*;
import tivoo.input.typeChecker.*;


public class CalendarParser
{
    private static SAXParserFactory SAX_PARSER_FACTORY =
        SAXParserFactory.newInstance();

    private final static Class<? extends TypeCheckHandler>[] TYPE_CHECKERS =
        (Class<? extends TypeCheckHandler>[]) (new Class[] {
                DukeCalTypeCheckHandler.class,
                TVTypeCheckHandler.class,
                DukeBasketballTypeCheckHandler.class,
                NFLTypeCheckHandler.class,
                GoogleCalTypeCheckHandler.class });

    private final static Class<? extends ParserHandler>[] PARSERS =
        (Class<? extends ParserHandler>[]) (new Class[] {
                DukeCalParserHandler.class,
                TVParserHandler.class,
                DukeBasketballCalHandler.class,
                NFLCalParserHandler.class,
                GoogleCalParserHandler.class });


    private static InputSource getInputSource (String fileName)
        throws IOException
    {
        File fileHandle = new File(fileName);
        InputStream inputStream = new FileInputStream(fileHandle);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        InputSource inputSource = new InputSource(reader);
        inputSource.setEncoding("UTF-8");
        return inputSource;
    }


    public static List<Event> parse (String fileName)
        throws SAXException,
            IOException,
            ParserConfigurationException
    {

        SAXParser parser = SAX_PARSER_FACTORY.newSAXParser();
        for (int i = 0; i < TYPE_CHECKERS.length; i++)
        {
            TypeCheckHandler typeCheckHandler = null;
            ParserHandler parserHandler = null;
            try
            {
                typeCheckHandler = TYPE_CHECKERS[i].newInstance();
                parserHandler = PARSERS[i].newInstance();
                parser.reset();
                parser.parse(getInputSource(fileName), typeCheckHandler);
            }
            catch (TypeMatchedException e)
            {
                parser.reset();
                parser.parse(getInputSource(fileName), parserHandler);
                return parserHandler.getEvents();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        System.err.println("Invalid File Type");
        return new LinkedList<Event>();
    }
}
