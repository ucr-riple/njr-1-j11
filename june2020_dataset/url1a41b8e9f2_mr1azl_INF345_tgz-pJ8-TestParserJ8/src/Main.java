import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
 
public class Main {
    public static void main(String[] args) {
 
        try {
        	
			 if(args.length == 2){
				 
 				String xpath=args[0];
 				String path =args[1];
 				
 		        FileInputStream file = new FileInputStream(new File(path));
                
 	            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
 	             
 	            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
 	             
 	            Document xmlDocument = builder.parse(file);
 	 
 	            XPath xPath =  XPathFactory.newInstance().newXPath();
 	 
 	            String expression = xpath;
 	            String xml = xPath.compile(expression).evaluate(xmlDocument);
 	            
 				 String outfile="";
 				 if(path.toLowerCase().contains(".xml")){
 					 outfile= path.substring(0,path.indexOf(".xml"))+".out.xml";
 				 }
 				 else {
 					 outfile= path+".out.xml";
 				 }
 				 System.out.println(xml);
// 				Document document = builder.parse(new InputSource(new StringReader(xml)));
// 				writeNode(document,outfile);
 				try {
 					 
 		 
 					File out = new File(outfile);
 		 
 					// if file doesnt exists, then create it
 					if (!out.exists()) {
 						out.createNewFile();
 					}
 		 
 					FileWriter fw = new FileWriter(out.getAbsoluteFile());
 					BufferedWriter bw = new BufferedWriter(fw);
 					bw.write(xml);
 					bw.close();
 		 
 		 
 				} catch (IOException e) {
 					e.printStackTrace();
 				}
 				
 			 }
			 
    
 


 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }       
    }
    
    
    




    	/**
    	 * Method writeNode.
    	 * @param node Node
    	 * @param file String
    	 */
    	private static void writeNode(Node node, String file){
    	
    		try {TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		
    	Transformer transformer = transformerFactory.newTransformer();
    	transformer.setOutputProperty(OutputKeys.INDENT, "no");
    	transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
    	DOMSource source = new DOMSource(node);
    	StreamResult result = new StreamResult(new File(file));

    	// Output to console for testing
    	// StreamResult result = new StreamResult(System.out);

    	
    		transformer.transform(source, result);
    	} catch (TransformerException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
    	/**
    	 * Method printNode.
    	 * @param node Node
    	 * @param spacer String
    	 */
    	private static void printNode(Node node, String spacer) {
    		StringWriter buffer = new StringWriter();
    		try {
    			TransformerFactory transFactory = TransformerFactory.newInstance();
    			Transformer transformer = transFactory.newTransformer();

    			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
    					"yes");

    			transformer
    					.transform(new DOMSource(node), new StreamResult(buffer));
    		} catch (TransformerConfigurationException e) {

    		} catch (TransformerException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		String str = buffer.toString();
    		System.out.println(str);
    	}

    	/**
    	 * Method printNode.
    	 * @param node Node
    	 * @return String
    	 */
    	private static String  printNode(Node node) {
    		StringWriter buffer = new StringWriter();
    		try {
    			TransformerFactory transFactory = TransformerFactory.newInstance();
    			Transformer transformer = transFactory.newTransformer();
    			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
    					"yes");

    			transformer
    					.transform(new DOMSource(node), new StreamResult(buffer));
    		} catch (TransformerConfigurationException e) {

    		} catch (TransformerException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		String str = buffer.toString();
    		System.out.println(str);
    		return str;
    	}

}