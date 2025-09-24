/**
 * Copyright 2010 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Adapted from:
 * http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 */
public abstract class Generic_XMLDOMReader {

    protected File file;
    protected NodeList nodeList;
    protected String nodeName;
    protected DocumentBuilderFactory aDocumentBuilderFactory;
    protected DocumentBuilder aDocumentBuilder;
    protected Document aDocument;

    protected void init(
            File file,
            String nodeName) {
        this.file = file;
        this.nodeName = nodeName;
        initDocumentBuilderFactory();
        initDocumentBuilder();
        initDocument();
        initNodeList(nodeName);
    }

    protected void initDocumentBuilderFactory() {
        aDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
    }

    protected void initDocumentBuilder() {
        try {
            aDocumentBuilder = aDocumentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Generic_XMLDOMReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void initDocument() {
        try {
            aDocument = aDocumentBuilder.parse(file);
        } catch (SAXException ex) {
            Logger.getLogger(Generic_XMLDOMReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Generic_XMLDOMReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        aDocument.getDocumentElement().normalize();
        System.out.println("Root element :" + aDocument.getDocumentElement().getNodeName());
    }

    protected void initNodeList(String nodeName) {
        nodeList = aDocument.getElementsByTagName(nodeName);
    }

    protected void readNodeListElements() {
        int depth = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node aNode = nodeList.item(i);
            System.out.println("depth " + depth + ", node " + i);
            System.out.println(aNode.getNodeName());
            short aNodeType = aNode.getNodeType();
            if (aNodeType == Node.ELEMENT_NODE) {
                Element eElement = (Element) aNode;
                NodeList childnodes = eElement.getChildNodes();
                if (childnodes.getLength() == 0) {
                    String attributeName = aNode.getNodeValue();
                    String attributeValue = eElement.getElementsByTagName(attributeName).item(0).getTextContent();
                    System.out.println("depth " + depth + ", node " + i);
                    System.out.println("" + attributeName + " : " + attributeValue);
                } else {
                    readNodeListElements(childnodes, depth + 1);
                }
            }
//            if (aNodeType == Node.TEXT_NODE) {
//                String attributeName = aNode.getNodeValue();
//                String attributeValue = aNode.getTextContent();
//                System.out.println("" + attributeName + " : " + attributeValue);
//            }
        }
    }

    protected void readNodeListElements(NodeList aNodeList, int depth) {
        for (int i = 0; i < aNodeList.getLength(); i++) {
            Node aNode = aNodeList.item(i);
            System.out.println("depth " + depth + ", node " + i);
            System.out.println(aNode.getNodeName());
            short aNodeType = aNode.getNodeType();
            if (aNodeType == Node.ELEMENT_NODE) {
                Element eElement = (Element) aNode;
                NodeList childnodes = eElement.getChildNodes();
                if (childnodes.getLength() == 0) {
                    String attributeName = aNode.getNodeValue();
                    String attributeValue = eElement.getElementsByTagName(attributeName).item(0).getTextContent();
                    System.out.println("depth " + depth + ", node " + i);
                    System.out.println("" + attributeName + " : " + attributeValue);
                } else {
                    readNodeListElements(childnodes, depth + 1);
                }
            }
            if (aNodeType == Node.TEXT_NODE) {
                String attributeName = aNode.getNodeValue().trim();
                String attributeValue = aNode.getTextContent().trim();
                System.out.println("depth " + depth + ", node " + i);
                System.out.println("" + attributeName + " : " + attributeValue);
            }
        }
    }

    protected abstract void parseNodeList();
}