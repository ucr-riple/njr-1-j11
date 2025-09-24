package yeah.cstriker1407.design_patterns.interpreter;

import java.util.HashMap;
import java.util.Stack;

public class InterpreterTest
{
	public static void test()
	{
		String expStr = "a+b-c+d";
		HashMap<String, Integer> vals = new HashMap<String, Integer>();
		vals.put("a", 10);
		vals.put("b", 20);
		vals.put("c", 30);
		vals.put("d", 40);
		
		System.out.println("val1 = " + Calculator.cal(expStr, vals));
		System.out.println("val2 = " + (10+20-30+40));
	}
}

class Calculator
{
	public static int cal(String expStr, HashMap<String, Integer> vals)
	{
		//用来暂存计算过程中的结果
		Stack<Expression> stack = new Stack<Expression>();
		
		//表达式拆分为字符数组
		char[] charArray = expStr.toCharArray();

		//运算
		Expression left = null;
		Expression right = null;
		for(int i=0;i<charArray.length;i++)
		{
			switch (charArray[i])
			{
				case '+':
				{
					left = stack.pop();
					right = new VarExpression(String.valueOf(charArray[++i]));
					stack.push(new AddExpression(left,right));
					break;
				}
				case '-':
				{
					left = stack.pop();
					right = new VarExpression(String.valueOf(charArray[++i]));
					stack.push(new SubExpression(left,right));
					break;
				}
				default:
				{
					stack.push(new VarExpression(String.valueOf(charArray[i])));
				}
			}
		}
		
		return stack.pop().interpreter(vals);
	}
}

abstract class Expression
{
	public abstract int interpreter(HashMap<String, Integer> var);
}
class VarExpression extends Expression
{
	private String key;
	public VarExpression(String key)
	{
		super();
		this.key = key;
	}

	@Override
	public int interpreter(HashMap<String, Integer> var)
	{
		return var.get(key);
	}
}

abstract class SymbolExpression extends Expression
{
	protected Expression left;
	protected Expression right;
	public SymbolExpression(Expression left, Expression right)
	{
		this.left = left;
		this.right = right;
	}
}
class AddExpression extends SymbolExpression
{
	public AddExpression(Expression left, Expression right)
	{
		super(left, right);
	}

	@Override
	public int interpreter(HashMap<String, Integer> var)
	{
		return left.interpreter(var) + right.interpreter(var);
	}
}
class SubExpression extends SymbolExpression
{
	public SubExpression(Expression left, Expression right)
	{
		super(left, right);
	}

	@Override
	public int interpreter(HashMap<String, Integer> var)
	{
		return left.interpreter(var) - right.interpreter(var);
	}
}
