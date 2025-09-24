/**
 * An example FileHandler to demonstrate how to process XML
 * documents that were passed using HTTP POST.
 *
 * The passed POST data must be form-data/multipart (Content-
 * Disposition/Content-Type) and have at least this element: 
 *  'my_file'.
 *  
 *
 * @author Ikaros Kappler
 * @date 2013-03-15
 * @version 1.0.0
 **/

import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import ikrs.httpd.AbstractFileHandler;
import ikrs.httpd.Constants;
import ikrs.httpd.DataFormatException;
import ikrs.httpd.DefaultPostDataWrapper;
import ikrs.httpd.FileHandler;
import ikrs.httpd.HeaderFormatException;
import ikrs.httpd.HTTPHandler;
import ikrs.httpd.HTTPHeaderLine;
import ikrs.httpd.HTTPHeaders;
import ikrs.httpd.PostDataWrapper;
import ikrs.httpd.Resource;
import ikrs.httpd.UnsupportedFormatException;
import ikrs.httpd.resource.ByteArrayResource;
import ikrs.httpd.datatype.FormData;
import ikrs.httpd.datatype.FormDataItem;
import ikrs.httpd.datatype.HeaderParams;
import ikrs.io.BytePositionInputStream;
import ikrs.util.CustomLogger;
import ikrs.util.Environment;
import ikrs.util.KeyValueStringPair;
import ikrs.util.MIMEType;
import ikrs.util.session.Session;
import ikrs.typesystem.BasicType;


// This are the classes we need to parse the XML document
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// import javax.xml.parsers.SAXParser;
// import javax.xml.parsers.SAXParserFactory;

public class XMLExampleHandler
    extends AbstractFileHandler {


    /**
     * The constructor without arguments (!) MUST exist to allow the system
     * to instantiate the class by the use of the Class.newInstance() method.
     **/
    public XMLExampleHandler() {
	super();

	
    }


    //--- BEGIN ------------------------ FileHandler implementation ------------------------------
    /**
     * Most file handlers operate on existing files that are located inside the local file 
     * system (such as the default handler does for simple file delivery).
     *
     * Some file handlers operate on virtual file systems, which means that the request URI does
     * not necessarily address an existing file but a symbol only the handler may know. 
     *
     * The global HTTP handler needs to know if to throw a MissingResourceException (resulting
     * in a 404) if a requested file does not exists --- or if to ignore that fact and simply 
     * continue.
     *
     * This method tells how to proceed.
     *
     * If your implementation returns true this handler will not be called at all; the request
     * processing will directly stop raising an HTTP status 404.
     *
     * @return true if this file handler definitely requires existing files. The process(...) 
     *              method will never be called if the requested file does not exist in that case.
     **/
    public boolean requiresExistingFile() {

	// Requested files do not necessarily have to exist :)
	return false;
    }

    /**
     * The 'process' method is very generic. It depends on the underlying implementation how the passed
     * file should be processed.
     *
     * @param sessionID   The current session's ID.
     * @param headers     The HTTP request headers.
     * @param postData    The HTTP post data; if the method is not HTTP POST the 'postData' should be null
     *                    (or empty).
     * @param file        The requested file itself (inside the local file system).
     * @param requestURI  The requested URI (relative to DOCUMENT_ROOT).
     **/
    public Resource process( UUID sessionID,
			     HTTPHeaders headers,
			     PostDataWrapper postData,
			     File file,
			     URI requestURI )
	throws IOException,
	       HeaderFormatException,
	       DataFormatException,
               UnsupportedFormatException {
	    

	getLogger().log( Level.INFO,
			 getClass().getName() + ".process(...)",
			 "Processing. requestURI=" + requestURI + ". file=" + file.getAbsolutePath() );

	
	
	// Print POST data on stdout
	/*byte[] tmpBuf = new byte[ 256 ];
	int tmpLen;
	while( (tmpLen = postData.getInputStream().read(tmpBuf)) != -1 ) {
	    for( int i = 0; i < tmpLen; i++ )
		System.out.print( (char)tmpBuf[i] );
		}*/
	

	
	// The XML parser only works with POST data
	if( postData == null )  
	    throw new DataFormatException( "You MUST send your XML document via HTTP POST!" );
	    
	
	FormData formData        = postData.readFormData();
	if( formData.size() == 0 )
	    throw new HeaderFormatException( "No params passed (but required)." );


	StringBuffer buffer    = new StringBuffer();
	StringBuffer xmlBuffer = null;   // A buffer to store the XML data
	String xmlFileName     = null;
	buffer.append( "This is a test.\n" ).
	    append( "\n" ).
	    append( "The " + getClass().getName() + " class expects an XML document with the form item name 'my_file' passed via HTTP POST.\n" ).
	    append( "\n\n" ).
	    append( "Statistics:\n" ).
	    append( "-----------\n" );
	buffer.append( " =================== There are " + formData.size() + " form data items. ===============\n" );

	for( int i = 0; i < formData.size(); i++ ) {

	    FormDataItem item = formData.get(i);
	    buffer.append( " +++ Item["+i+"]=" + item + "\n" );
	    StringBuffer tmpXMLBuffer = new StringBuffer();
	    String tmpFileName = this.processFormDataItem( item, i, buffer, tmpXMLBuffer );
	    
	    if( tmpFileName != null && xmlFileName == null ) {
		xmlBuffer   = tmpXMLBuffer;
		xmlFileName = tmpFileName;
	    }

	}


	buffer.append( "\n\n\n" );
	if( xmlBuffer == null )
	    buffer.append( "No XML file sent.\n" );
	else {
	    buffer.append( "Received XML data from file '" ).
		append( xmlFileName ).
		append( "'.\n" ).
		append( "---BEGIN---------- XML data ------------------\n" ).
		append( xmlBuffer ).append( "\n" ).
		append( "---END------------ XML data ------------------\n\n\n" );

	    this.parseXMLData( buffer, xmlFileName, xmlBuffer.toString() );
	}




	buffer.append( "-EOI-" );
	

       


	Session<String,BasicType,ikrs.httpd.HTTPConnectionUserID> session = this.getHTTPHandler().getSessionManager().get( sessionID );
	

	String data = buffer.toString();
	buffer.delete( 0, buffer.length() ); // Clear buffer

	ByteArrayResource resource = new ByteArrayResource( this.getHTTPHandler(),
							    this.getLogger(),
							    data.getBytes(),
							    false   // no need to use fair locks
							    );

	// I want this output to be displayed as plain text.
	// One other possible way would be
	//   MIMEType mimeType = new MIMEType( "text/plain" );
	MIMEType mimeType = MIMEType.getByFileExtension( "txt" );
	resource.getMetaData().setMIMEType( mimeType );
	resource.getMetaData().setCharsetName( "UTF-8" );
		
	return resource;
    }
    //--- END -------------------------- FileHandler implementation ------------------------------


    /**
     * If the 'my_file' param is found in the passed item the method will
     * print the file data into the xmlBuffer and return the file's name
     * (otherwise it returns null).
     **/
    private String processFormDataItem( FormDataItem item,
					int itemIndex,
					StringBuffer buffer,
					StringBuffer xmlBuffer ) 
	throws IOException {

	String xmlFileName = null;
	    
	// Parse header values (deep parse)
	for( int h = 0; h < item.getHeaders().size(); h++ ) {

	    HTTPHeaderLine header = item.getHeaders().get( h );
	    HeaderParams params = new HeaderParams( header.getKey(), header.getValue() );
	    buffer.append( " +++ +++ (key=" + header.getKey() + ")\n" );
	    buffer.append( " +++ +++ params["+itemIndex+"]["+h+"]=" + params.getMainToken() + "\n" );
	    for( int l = 0; l < params.getSublevelCount(h); l++ )
		buffer.append( " +++ +++ +++ token["+itemIndex+"]["+h+"]["+l+"]=" + params.getToken(h,l) + "\n" );

	}

	String hdr_contentDisposition = item.getHeaders().getStringValue( HTTPHeaders.NAME_CONTENT_DISPOSITION );
	String hdr_contentType        = item.getHeaders().getStringValue( HTTPHeaders.NAME_CONTENT_TYPE );

	buffer.append( " ---------- Content-Disposition: " + hdr_contentDisposition + "\n" );
	buffer.append( " ---------- Content-Type:        " + hdr_contentType + "\n" );

	HeaderParams prm_contentDisposition = new HeaderParams( HTTPHeaders.NAME_CONTENT_DISPOSITION, hdr_contentDisposition );	    
	String value_contentDisposition_main     = prm_contentDisposition.getMainToken(); // This should equal 'form-data'
	String value_contentDisposition_name     = null;
	String value_contentDisposition_filename = null;
	if( value_contentDisposition_main != null 
	    && value_contentDisposition_main.equalsIgnoreCase("form-data") ) {

	    value_contentDisposition_name     = prm_contentDisposition.getTokenValue( "form-data", "name", true );
	    value_contentDisposition_filename = prm_contentDisposition.getTokenValue( "form-data", "filename", true );
		
	    buffer.append( " ---------- --- Content-Disposition // name:     " + value_contentDisposition_name + "\n" );
	    buffer.append( " ---------- --- Content-Disposition // filename: " + value_contentDisposition_filename + "\n" );

	}


	// The 'my_file' form field was defined in the PHP file.
	if( value_contentDisposition_name.equals("my_file") 
	    && value_contentDisposition_filename != null 
	    && value_contentDisposition_filename.length() != 0 ) {

	    // xmlBuffer   = new StringBuffer();
	    xmlFileName = value_contentDisposition_filename;
	}
		

	buffer.append( "Raw data, eventually binary:\n" );
	if( item.getInputStream() == null ) {
	    buffer.append( "NA\n" );
	} else {
	    // Print POST data on stdout or write into output buffer?
	    long binaryTokenLength = 0;
	    byte[] buf = new byte[ 256 ];
	    int len;
	    while( (len = item.getInputStream().read(buf)) != -1 ) {
		for( int b = 0; b < len; b++ ) { 
		    buffer.append( (char)buf[b] );
		    //System.out.print( (char)buf[b] );
			
		    // Data is part of the sent XML file?
		    if( value_contentDisposition_name.equals("my_file") 
			&& value_contentDisposition_filename != null 
			&& value_contentDisposition_filename.length() != 0 ) {

			xmlBuffer.append( (char)buf[b] );
		    }

		}
		binaryTokenLength += len;
	    }
	    buffer.append( "\n" ).append( "Token data read: " ).append( binaryTokenLength ).append( " bytes\n" );
	}
	    
	    
	buffer.append( "\n\n" );

	
	
	return xmlFileName;	
    }


    /**
     * This method does the actual job. It parses the XML document (hopefully valid XML)
     * by the use of Java's SAX parser and writes the parsed XML structure into the
     * output buffer.
     **/
    private void parseXMLData( StringBuffer buffer,
			       String fileName,
			       String xmlData ) {

	try {
	    buffer.append( "[parseXMLData] Parsing XML data ...\n" );
	    InputStream in                   = new ByteArrayInputStream( xmlData.getBytes("UTF-8") ); // might throw IOException
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db               = dbFactory.newDocumentBuilder();    // might throw ParserConfigurationException
	    Document doc                     = db.parse( in );                    // might throw SAXException
	    
	    doc.getDocumentElement().normalize();

	    buffer.append( "[parseXMLData] Document parsed: " + doc + "\n\n" );
	    this.printNodes( buffer,         
			     doc.getDocumentElement().getElementsByTagName("*"),   // wildcard: get all nodes
			     1                                                     // the initial indent
			     );
	    
	    
	} catch( IOException e ) {
	    buffer.append( "IOException during parse process: " + e.getMessage() + "\n\n" );
	} catch( ParserConfigurationException e ) {
	    buffer.append( "ParserConfigurationException during parse process: " + e.getMessage() + "\n\n" );   
	} catch( SAXException e ) {
	    buffer.append( "SAXException during parse process: " + e.getMessage() + ". Was the passed data really an XML document?\n\n" );
	}

    }

    /**
     * This is just a helper method that converts the XML structure into a nice
     * tree-like text view (writes the output into the passed buffer).
     **/
    private void printNodes( StringBuffer buffer, NodeList nodeList, int indent ) {
	
	for( int n = 0; n < nodeList.getLength(); n++ ) {
	    
	    Node node              = nodeList.item( n );
	    this.printIndent( buffer, indent );
	    buffer.append( "[Node: " ).append( node.getNodeName() );
	    NamedNodeMap attributes = node.getAttributes();
	    if( attributes != null ) {
		for( int a = 0; a < attributes.getLength(); a++ ) {
		    Node attr = attributes.item(a);
		    buffer.append( " " ).append( attr.getNodeName() ).append( "='" ).append( attr.getNodeValue() ).append( "'" );
		}
	    }
	    buffer.append( "]\n" );
	    
	    this.printNodes( buffer, node.getChildNodes(), indent + 4 );
	}
    }

    /**
     * This method just prints n-times the whitespace character (0x20).
     *
     * It is used for indentation when generating the XML tree view.
     **/
    private void printIndent( StringBuffer buffer, int indent ) {
	while( indent-- > 0 )
	    buffer.append( " " );
    }

}
