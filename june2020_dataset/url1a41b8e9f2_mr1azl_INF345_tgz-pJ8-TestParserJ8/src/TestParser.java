import xx.MonCompiler;
import xx.Parser;
import xx.XpathValidator;


/**
 */
public class TestParser {

	
	/**
	 * Method main.
	 * @param args String[]
	 */
	public static void main(String[] args) {
		
		Object result = Parser.parseExpression("/*",new XpathValidator() );
		System.out.println(result);
	}
}
