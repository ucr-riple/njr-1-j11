package ikrs.util;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.text.Collator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


/**
 * This XML reader was inspired by
 * http://www.java-tips.org/java-se-tips/javax.xml.parsers/how-to-read-xml-file-in-java.html
 *
 * @author Ikaros Kappler
 * @date 2012-04-24
 * @version 1.0.0
 **/



import ikrs.typesystem.*;


public class XMLEnvironmentReader {
    
    public static final String TEXT_NODE_KEY = "#text";

    /**
     * A dummy constructor. This class is not meant to be instantiated.
     **/
    protected XMLEnvironmentReader() {
	
    }

    /**
     * Reads the whole XML file into an environment object.
     * Note: Text nodes will be dropped.
     **/
    public static Environment<String,BasicType> read( File file )
	throws IOException {
	
	return read( file, true );
    }
    
    /**
     * Reads the whole XML file into an environment object.
     * Note: if dropTextNodes is set to false, the text data will be added to
     *       the local environments map using the mapping key XMLEnvironmentReader.TEXT_NODE_KEY.
     *
     * @param file The input file.
     * @param dropTextNodes if set to true all text nodes will be dropped.
     *
     * @return The XML file contents inside a nested environment. Note that the environment has
     *         allowMultipleChildNames() set to true!
     **/
    public static Environment<String,BasicType> read( File file,
						      boolean dropTextNodes )
	throws IOException {
	
	Comparator<String> comp = CaseInsensitiveComparator.sharedInstance;
	Environment<String,BasicType> env = 
	    new DefaultEnvironment<String,BasicType>( new TreeMapFactory<String,BasicType>( comp ),
						      true   // allowMultipleChildNames
						      );

	try {
	    
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document doc = db.parse(file);
	    doc.getDocumentElement().normalize();

	    read( doc, env, dropTextNodes );

	    //System.out.println( env );

	    return env;

	} catch (Exception e) {

	    e.printStackTrace();
	    return null;
	    
	}

    }

    protected static void read( Document doc,
				Environment<String,BasicType> env,
				boolean dropTextNodes ) {

	/* Get root node */
	Node rootNode = doc.getDocumentElement();
	
	/* Start recursion */
	read( rootNode,
	      env,
	      dropTextNodes );
	
    }

    protected static void read( Node currentNode,
				Environment<String,BasicType> env,
				boolean dropTextNodes ) {
							 
	//System.out.println("Current node " + currentNode.getNodeName() );
	//NodeList nodeLst = doc.getElementsByTagName("server");

	// Add the current root's attributes to the environment
	NamedNodeMap attributes = currentNode.getAttributes();
	if( attributes != null ) {
	    for( int a = 0; a < attributes.getLength(); a++ ) {
		Node attr = attributes.item(a);
		/*
		System.out.print( "NodeName=" + attr.getNodeName() );
		System.out.print( ", LocalName=" + attr.getLocalName() );
		System.out.print( ", NodeValue=" + attr.getNodeValue() );
		System.out.print( ", isElementNode=" + (currentNode.getNodeType() == Node.ELEMENT_NODE) );
		System.out.println( ", Node=" + attr );
		*/
		env.put( attr.getNodeName(), new BasicStringType(attr.getNodeValue()) );
	    }
	}

	
	NodeList children = currentNode.getChildNodes();
	for (int s = 0; s < children.getLength(); s++) {
	    
	    // Get next node
	    Node childNode = children.item(s);
	    
	    // Drop those #text nodes
	    if( childNode.getNodeType() == Node.TEXT_NODE ) {
		
		String tmpText = childNode.getNodeValue(); // TextContent();
		if( !dropTextNodes && tmpText != null && !(tmpText = tmpText.trim()).equals("") ) {
		    
		    env.put( XMLEnvironmentReader.TEXT_NODE_KEY, new BasicStringType(tmpText) );
		    
		}
		continue;
		
	    } 
		
	    
	    /*
	    System.out.print( "---ChildNode: "+childNode.getNodeName() );
	    System.out.print( ", LocalName=" + childNode.getLocalName() );
	    System.out.print( ", isElementNode=" + (currentNode.getNodeType() == Node.ELEMENT_NODE) );
	    System.out.println( "" );
	    */
	    
	    Environment<String,BasicType> childEnv = env.createChild( childNode.getNodeName() );
	    
	    read( childNode,
		  childEnv,
		  dropTextNodes 
		  );
	}
    } // END method

    public static void main( String argv[] ) {
	try {
	    read( new File( argv[0] ) );
	} catch( IOException e ) {
	    e.printStackTrace();
	}
    }
}