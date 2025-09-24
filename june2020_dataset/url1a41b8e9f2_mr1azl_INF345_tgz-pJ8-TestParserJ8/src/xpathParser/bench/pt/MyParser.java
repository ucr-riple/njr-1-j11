package xpathParser.bench.pt;

import java.util.Map;

import org.w3c.dom.Node;

import zz.TestFT;
import zz.XPathDebug;

public class MyParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			 
				XPathDebug compiler = new XPathDebug();
				
				compiler.setDocFile("benchmark.xml");
				for (Map.Entry<String, String> entry : TestPT.getMap().entrySet()) {
					long start = System.currentTimeMillis();
					System.out.print(entry.getKey()+";");
					compiler.compile(entry.getValue());
					long end = System.currentTimeMillis();
					System.out.println((end-start)+"");
					 System.gc();
					 System.gc();
//					writeNode(document,"out/FT_alphabet_"+entry.getKey()+".out");
				}
			 
		} catch (Exception e) {
			System.err.println("ko");
		}
	}

}
