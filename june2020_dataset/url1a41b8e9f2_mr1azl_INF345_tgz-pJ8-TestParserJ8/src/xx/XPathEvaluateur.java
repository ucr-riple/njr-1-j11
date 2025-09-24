package xx;


import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import yyy.NodeTest;
import yyy.Step;
import zz.TestPT;

 
public class XPathEvaluateur {

	public XPathEvaluateur() {
	}
private String docFile="";

/**
 * 
 * @param xpath
 * @param document
 * @return
 */
public Node compile(String xpath,Document document) {
	try {
		List<Node> dedup=new ArrayList<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory
				.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();

		Element root = document.createElement("answer");
//		root.setAttribute("expression", xpath);
		Object result = Parser.parseExpression(xpath, new MonCompiler(
				document));
		if (result instanceof String)
			System.out.println("WRONG PARSING " + (String) result);
		
		 if (result instanceof NodeTest)
			{//TODO
				
			}else
			if (result instanceof Node){
				 
			//root.appendChild((Node) result);
			Node node = (Node) result;
			if(!dedup.contains(node) && ! (node instanceof DocumentType)){
				root.appendChild(node.cloneNode(true));
				dedup.add(node);
				}
		}
		if (result instanceof NodeList)
			for (int i = 0; i < ((NodeList) result).getLength(); i++)
				root.appendChild(((NodeList) result).item(i));
		if (result instanceof ArrayList)
			for (Node node : (ArrayList<Node>) result) {
				if (node instanceof Document) {
					System.out.println("the node is document");
					printNode(node, "\t");
//					root = ((Document) node).getDocumentElement();
				} else if (node instanceof Attr) {
					root.setAttribute(((Attr) node)
							.getName(),((Attr) node)
							.getValue());
//					root.appendChild(document.createElement(((Attr) node)
//							.getName()));
				}else if (node instanceof Step)
				{
					
				}
				else {
					if(!dedup.contains(node) && ! (node instanceof DocumentType)){
						root.appendChild(node.cloneNode(true));
						dedup.add(node);
						}
				}
			}

		return root;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}

/**
 * 
 * @param xpath
 * @return
 */
public Node compile(String xpath) {
	try {
		List<Node> dedup=new ArrayList<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory
				.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();

		Document document = dBuilder.parse(docFile);
		Element root = document.createElement("answer");
//		root.setAttribute("expression", xpath);
		Object result = Parser.parseExpression(xpath, new MonCompiler(
				document));
		if (result instanceof String)
			System.out.println("WRONG PARSING " + (String) result);
		
		 if (result instanceof NodeTest)
			{//TODO
				
			}else
			if (result instanceof Node){
				 
			//root.appendChild((Node) result);
			Node node = (Node) result;
			if(!dedup.contains(node) && ! (node instanceof DocumentType)){
				root.appendChild(node.cloneNode(true));
				dedup.add(node);
				}
		}
		if (result instanceof NodeList)
			for (int i = 0; i < ((NodeList) result).getLength(); i++)
				root.appendChild(((NodeList) result).item(i));
		if (result instanceof ArrayList)
			for (Node node : (ArrayList<Node>) result) {
				if (node instanceof Document) {
					System.out.println("the node is document");
					printNode(node, "\t");
//					root = ((Document) node).getDocumentElement();
				} else if (node instanceof Attr) {
					root.setAttribute(((Attr) node)
							.getName(),((Attr) node)
							.getValue());
//					root.appendChild(document.createElement(((Attr) node)
//							.getName()));
				}else if (node instanceof Step)
				{
					
				}
				else {
					if(!dedup.contains(node) && ! (node instanceof DocumentType)){
						root.appendChild(node.cloneNode(true));
						dedup.add(node);
						}
				}
			}

		return root;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}


/**
 * 
 * @param xpath
 * @param xml
 * @return
 * @throws ParserConfigurationException
 * @throws SAXException
 * @throws IOException
 */
public String parseString(String xpath,String xml) throws ParserConfigurationException, SAXException, IOException{
	XPathEvaluateur compiler = new XPathEvaluateur();
	
	DocumentBuilderFactory factory = DocumentBuilderFactory
			.newInstance();
	factory.setNamespaceAware(true);
	factory.setValidating(false);
	DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
			.newDocumentBuilder();
	Document document = dBuilder.parse(new InputSource(new StringReader(xml)));
	Node root = compiler.compile(xpath,document);
//		writeNode(document,"out/FT_alphabet_"+entry.getKey()+".out");
	return printNode(root);
}
/**
 * Method readFile.
 * @param path String
 * @return String
 * @throws ParserConfigurationException
 * @throws SAXException
 * @throws IOException
 */
public String readFile(String path) throws ParserConfigurationException, SAXException, IOException{
	XPathEvaluateur compiler = new XPathEvaluateur();
	
	DocumentBuilderFactory factory = DocumentBuilderFactory
			.newInstance();
	factory.setNamespaceAware(true);
	factory.setValidating(false);
	DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
			.newDocumentBuilder();
	Document document = dBuilder.parse(new File(path));
//		writeNode(document,"out/FT_alphabet_"+entry.getKey()+".out");
	return printNode(document.getDocumentElement());
}

	/**
	 * Method main.
	 * @param args String[]
	 */
	public static void main(String[] args) {
		try {
			 if(args.length == 2){
				XPathEvaluateur compiler = new XPathEvaluateur();
				String xpath=args[0];
				String path =args[1];
				compiler.setDocFile(args[1]);
				 Node document = compiler.compile(xpath);
				 String outfile="";
				 if(path.toLowerCase().contains(".xml")){
					 outfile= path.substring(0,path.indexOf(".xml"))+".out.xml";
				 }
				 else {
					 outfile= path+".out.xml";
				 }
				writeNode(document,outfile);
				
			 }
			 else if (args.length==1)
			 { String xpath=args[0];
				 
				  Object result = Parser.parseExpression(xpath,new XpathValidator() );

				    // Determine if input is too short or too long
				   if(!(result instanceof Boolean ))
				   {
					System.out.println("XPath non valide");
				   }
				   else {
					   System.out.println("XPath valide");   
				   }
			 }
		} catch (Exception e) {
			System.err.println("Error !!");
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
	/**
	 * Method getDocFile.
	 * @return String
	 */
	public String getDocFile() {
		return docFile;
	}

	/**
	 * Method setDocFile.
	 * @param docFile String
	 */
	public void setDocFile(String docFile) {
		this.docFile = docFile;
	}
}
