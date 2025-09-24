package xpathParser.bench.pt;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;



public class DefaultParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			 
				
			     FileInputStream file = new FileInputStream(new File("benchmark.xml"));
	                
	 	            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	 	             
	 	            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
	 	             
	 	            Document xmlDocument = builder.parse(file);
	 	 
	 	            XPath xPath =  XPathFactory.newInstance().newXPath();
	 	 
				for (Map.Entry<String, String> entry : TestPT.getMap().entrySet()) {
					System.out.print(entry.getKey()+";");
					long start = System.currentTimeMillis();
				
	 	             xPath.compile(entry.getValue()).evaluate(xmlDocument);
					long end = System.currentTimeMillis();
					System.out.println((end-start)+"");
					 System.gc();
					 System.gc();
//					writeNode(document,"out/FT_alphabet_"+entry.getKey()+".out");
				}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
