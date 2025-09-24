package expression;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;

import expression.bean.IExpression;
import expression.visitor.SQLExpressionVisitor;

public class DirkExpTest {
	
	public static void main(String[] args) throws Exception {
//		String exp = "(1+2)>90";
//		String exp = "[idssss9090fewf]+[ilfeswfe898989]>[jfownf34242]*([fewfwef89798few]+1111222)";
		String exp = "(sum([idsfefw2321]+10))";
		DirkExpLexer lexer = new DirkExpLexer(new ANTLRStringStream(exp));
		DirkExpParser parser = new DirkExpParser(new CommonTokenStream(lexer));
		CommonTree tree = (CommonTree) parser.expr().getTree();
		IExpression expression = ExpressionHelper.createTree(tree);
		SQLExpressionVisitor v = new SQLExpressionVisitor();
		expression.accept(v);
		String str = v.getResult();
		System.out.println(str);
//		createTree(tree);
	}
	
//	private static void createTree(CommonTree tree){
//		switch (tree.getType()) {
//		case BoncExpParser.STRING_LITERAL:
//			System.out.println("wrong----String:"+tree.getText());
//			break;
//		case BoncExpParser.VARIABLE:
//			System.out.println("ok-------param:"+tree.getChild(0).getText());
////			createTree((CommonTree)tree.getChild(0).getChild(0));
//			break;
//		case BoncExpParser.INTEGER_LITERAL:
//			System.out.println("int:"+tree.getText());
//			break;
//		case BoncExpParser.DECIMAL_LITERAL:
//			System.out.println("decimal:"+tree.getText());
//			
//			break;
//		case BoncExpParser.DATETIME_LITERAL:
//			System.out.println("data:"+tree.getText());
//			
//			break;
//		case BoncExpParser.LT:
//		case BoncExpParser.LTEQ:
//		case BoncExpParser.GT:
//		case BoncExpParser.GTEQ:
//		case BoncExpParser.PLUS:
//		case BoncExpParser.MINUS:
//			createTree((CommonTree) tree.getChild(0));
//			createTree((CommonTree) tree.getChild(1));
//			break;
//		default:
//			break;
//		}
//	}

}
