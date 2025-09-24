package expression;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;

import expression.bean.IExpression;
import expression.bean.arithmetic.Addition;
import expression.bean.arithmetic.BitwiseXor;
import expression.bean.arithmetic.Division;
import expression.bean.arithmetic.Module;
import expression.bean.arithmetic.Multi;
import expression.bean.arithmetic.Subtract;
import expression.bean.func.FunctionExpression;
import expression.bean.relation.EqualsTo;
import expression.bean.relation.GreaterThan;
import expression.bean.relation.GreaterThanEquals;
import expression.bean.relation.MinorThan;
import expression.bean.relation.MinorThanEquals;
import expression.bean.relation.NotEqualsTo;
import expression.bean.value.BracketValue;
import expression.bean.value.FinalValue;
import expression.bean.value.IdentifierValue;
import expression.bean.value.ParamValue;

public class ExpressionHelper {
	
	public static IExpression createTree(CommonTree tree) throws Exception{
		switch (tree.getType()) {
		case DirkExpParser.STRING_LITERAL:
			return new FinalValue(tree.getText(),FinalValue.ValueType.String);

		case DirkExpParser.INTEGER_LITERAL:
			return new FinalValue(tree.getText(),FinalValue.ValueType.Integer);
			

		case DirkExpParser.IDENTIFIER:
			return new IdentifierValue(tree.getText());
			

		case DirkExpParser.DECIMAL_LITERAL:
			return new FinalValue(tree.getText(),FinalValue.ValueType.Decimal);
			

		case DirkExpParser.DATETIME_LITERAL:
			return new FinalValue(tree.getText(),FinalValue.ValueType.DateTime);
			

		case DirkExpParser.VARIABLE:
			return new ParamValue(tree.getChild(0).getText());
			
			
		case DirkExpParser.GT:
			return new GreaterThan(createTree((CommonTree) tree.getChild(0)),
					createTree((CommonTree) tree.getChild(1)));
			
			
		case DirkExpParser.GTEQ:
			return new GreaterThanEquals(createTree((CommonTree) tree.getChild(0)),
					createTree((CommonTree) tree.getChild(1)));
			
			
		case DirkExpParser.LT:
			return new MinorThan(createTree((CommonTree) tree.getChild(0)),
					createTree((CommonTree) tree.getChild(1)));
			
			
		case DirkExpParser.LTEQ:
			return new MinorThanEquals(createTree((CommonTree) tree.getChild(0)),
					createTree((CommonTree) tree.getChild(1)));
			
			
		case DirkExpParser.NOTEQUALS:
			return new NotEqualsTo(createTree((CommonTree) tree.getChild(0)),
					createTree((CommonTree) tree.getChild(1)));
			
			
		case DirkExpParser.EQUALS:
			return new EqualsTo(createTree((CommonTree) tree.getChild(0)),
					createTree((CommonTree) tree.getChild(1)));
			
			
		case DirkExpParser.POWER:
			return new BitwiseXor(createTree((CommonTree) tree.getChild(0)),
					createTree((CommonTree) tree.getChild(1)));
			
			
		case DirkExpParser.MOD:
			return new Module(createTree((CommonTree) tree.getChild(0)),
					createTree((CommonTree) tree.getChild(1)));
			
			
			
		case DirkExpParser.DIV:
			return new Division(createTree((CommonTree) tree.getChild(0)),
					createTree((CommonTree) tree.getChild(1)));
			
			
			
		case DirkExpParser.MUL:
			return new Multi(createTree((CommonTree) tree.getChild(0)),
					createTree((CommonTree) tree.getChild(1)));
			
			
			
		case DirkExpParser.MINUS:
			return new Subtract(createTree((CommonTree) tree.getChild(0)),
					createTree((CommonTree) tree.getChild(1)));
			
			
			
		case DirkExpParser.PLUS:
			return new Addition(createTree((CommonTree) tree.getChild(0)),
					createTree((CommonTree) tree.getChild(1)));
			
		case DirkExpParser.CALL:
			List<IExpression> exps = new LinkedList<IExpression>();
			int i=0;
			for (Object object : tree.getChildren()) {
				if(++i!=1)
					exps.add(createTree((CommonTree)object));
			}
			return new FunctionExpression(tree.getChild(0).getText(),exps);
			
		case DirkExpParser.BRACKETEXP:
			return new BracketValue(createTree((CommonTree)tree.getChild(0)));
			
		case DirkExpParser.LPAREN:
			return null;
		case DirkExpParser.RPAREN:
			return null;
			
		default:
			throw new Exception("unknown tree node");
		}
	}

}
