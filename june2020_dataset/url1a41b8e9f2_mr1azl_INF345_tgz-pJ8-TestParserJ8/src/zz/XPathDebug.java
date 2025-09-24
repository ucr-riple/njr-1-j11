package zz;

/*
 *
 *  Artistic License
 *
 *  Preamble
 *
 *  The intent of this document is to state the conditions under which a
 *  Package may be copied, such that the Copyright Holder maintains some
 *  semblance of artistic control over the development of the package,
 *  while giving the users of the package the right to use and distribute
 *  the Package in a more-or-less customary fashion, plus the right to make
 *  reasonable modifications.
 *
 *  Definitions:
 *
 *  "Package" refers to the collection of files distributed by the
 *  Copyright Holder, and derivatives of that collection of files created
 *  through textual modification.
 *
 *  "Standard Version" refers to such a Package if it has not been
 *  modified, or has been modified in accordance with the wishes of the
 *  Copyright Holder.
 *
 *  "Copyright Holder" is whoever is named in the copyright or copyrights
 *  for the package.
 *
 *  "You" is you, if you're thinking about copying or distributing this Package.
 *
 *  "Reasonable copying fee" is whatever you can justify on the basis of
 *  media cost, duplication charges, time of people involved, and so
 *  on. (You will not be required to justify it to the Copyright Holder,
 *  but only to the computing community at large as a market that must bear
 *  the fee.)
 *
 *  "Freely Available" means that no fee is charged for the item itself,
 *  though there may be fees involved in handling the item. It also means
 *  that recipients of the item may redistribute it under the same
 *  conditions they received it.
 *
 *  1. You may make and give away verbatim copies of the source form of the
 *  Standard Version of this Package without restriction, provided that you
 *  duplicate all of the original copyright notices and associated
 *  disclaimers.
 *
 *  2. You may apply bug fixes, portability fixes and other modifications
 *  derived from the Public Domain or from the Copyright Holder. A Package
 *  modified in such a way shall still be considered the Standard Version.
 *
 *  3. You may otherwise modify your copy of this Package in any way,
 *  provided that you insert a prominent notice in each changed file
 *  stating how and when you changed that file, and provided that you do at
 *  least ONE of the following:
 *
 *    a) place your modifications in the Public Domain or otherwise make
 *    them Freely Available, such as by posting said modifications to
 *    Usenet or an equivalent medium, or placing the modifications on a
 *    major archive site such as ftp.uu.net, or by allowing the Copyright
 *    Holder to include your modifications in the Standard Version of the
 *    Package.
 *
 *    b) use the modified Package only within your corporation or
 *    organization.
 *
 *    c) rename any non-standard executables so the names do not conflict
 *    with standard executables, which must also be provided, and provide
 *    a separate manual page for each non-standard executable that
 *    clearly documents how it differs from the Standard Version.
 *
 *    d) make other distribution arrangements with the Copyright Holder.
 *
 *  4. You may distribute the programs of this Package in object code or
 *  executable form, provided that you do at least ONE of the following:
 *
 *    a) distribute a Standard Version of the executables and library
 *    files, together with instructions (in the manual page or
 *    equivalent) on where to get the Standard Version.
 *
 *    b) accompany the distribution with the machine-readable source of
 *    the Package with your modifications.
 *
 *    c) accompany any non-standard executables with their corresponding
 *    Standard Version executables, giving the non-standard executables
 *    non-standard names, and clearly documenting the differences in
 *    manual pages (or equivalent), together with instructions on where
 *    to get the Standard Version.
 *
 *    d) make other distribution arrangements with the Copyright Holder.
 *
 *  5. You may charge a reasonable copying fee for any distribution of this
 *  Package. You may charge any fee you choose for support of this
 *  Package. You may not charge a fee for this Package itself.  However,
 *  you may distribute this Package in aggregate with other (possibly
 *  commercial) programs as part of a larger (possibly commercial) software
 *  distribution provided that you do not advertise this Package as a
 *  product of your own.
 *
 *  6. The scripts and library files supplied as input to or produced as
 *  output from the programs of this Package do not automatically fall
 *  under the copyright of this Package, but belong to whomever generated
 *  them, and may be sold commercially, and may be aggregated with this
 *  Package.
 *
 *  7. C or perl subroutines supplied by you and linked into this Package
 *  shall not be considered part of this Package.
 *
 *  8. The name of the Copyright Holder may not be used to endorse or
 *  promote products derived from this software without specific prior
 *  written permission.
 *
 *  9. THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 *  MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 */

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xx.MonCompiler;
import xx.Parser;
import yyy.NodeTest;
import yyy.Step;

/**
 * XPathCompiler
 * 
 * @author <a href="mailto:unl@users.sourceforge.net">Ulrich Nicolas
 *         Liss&eacute;</a>
 * @version $Id: XPathCompiler.java,v 1.2 2003/07/31 02:09:35 joernt Exp $
 */
public class XPathDebug {

	public XPathDebug() {
		// NOP
	}
private String docFile="";
	/**
	 * Method compile.
	 * @param xpath String
	 * @return Node
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
//			root.setAttribute("expression", xpath);
			Object result = Parser.parseExpression(xpath, new MonCompiler(
					document));
			if (result instanceof String)
				System.out.println("WRONG PARSING " + (String) result);
			 if (result instanceof NodeTest)
			{//TODO
				
			}else
			if (result instanceof Node){
				root.appendChild((Node) result);
			}
			if (result instanceof NodeList)
				for (int i = 0; i < ((NodeList) result).getLength(); i++)
					root.appendChild(((NodeList) result).item(i));
			if (result instanceof ArrayList)
				for (Node node : (ArrayList<Node>) result) {
					if (node instanceof Document) {
						System.out.println("the node is document");
						printNode(node, "\t");
//						root = ((Document) node).getDocumentElement();
					} else if (node instanceof Attr) {
						root.setAttribute(((Attr) node)
								.getName(),((Attr) node)
								.getValue());
//						root.appendChild(document.createElement(((Attr) node)
//								.getName()));
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
	 * Method main.
	 * @param args String[]
	 */
	public static void main(String[] args) {
		try {
			 
				XPathDebug compiler = new XPathDebug();
				
				//compiler.setDocFile(args[1]);
				compiler.setDocFile("out/alphabet.xml");
				// Node document = compiler.compile("/company/staff[position() < 2]");
//				writeNode(document,args[1]+".out.xml");
//				printNode(document, "\t");
//				Node document;
//				
//				System.out.println("O2"+"\t"+Test.getMap().get("O2"));
//				 document = compiler.compile(Test.getMap().get("O2"));
//				writeNode(document,"out/FT_alphabet_"+"O2"+".out");
//				printNode(document,"\t");
//				printNote(document.getChildNodes());
				Node document;
				for (Map.Entry<String, String> entry : TestFT.getMap().entrySet()) {
					System.out.println("out/FT_alphabet_"+entry.getKey()+".out");
					 document = compiler.compile(entry.getValue());
//					writeNode(document,"out/FT_alphabet_"+entry.getKey()+".out");
				}
			 
		} catch (Exception e) {
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
	
	 /**
	  * Method printNote.
	  * @param nodeList NodeList
	  */
	 private static void printNote(NodeList nodeList) {
		 
		    for (int count = 0; count < nodeList.getLength(); count++) {
		 
			Node tempNode = nodeList.item(count);
		 
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
		 
				// get node name and value
				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				System.out.println("Node Value =" + tempNode.getTextContent());
		 
				if (tempNode.hasAttributes()) {
		 
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
		 
					for (int i = 0; i < nodeMap.getLength(); i++) {
		 
						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : " + node.getNodeValue());
		 
					}
		 
				}
		 
				if (tempNode.hasChildNodes()) {
		 
					// loop again if has child nodes
					printNote(tempNode.getChildNodes());
		 
				}
		 
				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
		 
			}
		 
		    }
	 }
}
