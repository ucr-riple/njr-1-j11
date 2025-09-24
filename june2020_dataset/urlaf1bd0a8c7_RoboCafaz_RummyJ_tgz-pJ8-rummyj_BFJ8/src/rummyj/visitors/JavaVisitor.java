package rummyj.visitors;

import rummyj.nodes.*;
import rummyj.nodes.Message;
import rummyj.nodes.Number;
import rummyj.FilePrinter;
import rummyj.Visitor;

public class JavaVisitor implements Visitor
{
	String	className;

	public JavaVisitor(String className)
	{
		this.className = className;
	}

	public void visit(Message character)
	{
		FilePrinter.writeLine("message=\"" + character.character + "\";");
		FilePrinter.writeLine("for(int i = 0;i < message.length();i++)");
		FilePrinter.writeLine("{");
		FilePrinter.increaseIndent();
		FilePrinter.writeLine("char c = message.charAt(i);");
		FilePrinter.writeLine("array[pointer] = (byte)c;");
		FilePrinter.writeLine("pointer++;");
		FilePrinter.writeLine("array[pointer] = 0;");
		FilePrinter.decreaseIndent();
		FilePrinter.writeLine("}");
	}

	public void visit(Procedure n)
	{
		FilePrinter.writeLine("procedures[array[pointer]] = new Procedure()");
		FilePrinter.writeLine("{");
		FilePrinter.increaseIndent();
		FilePrinter.writeLine("public void execute()");
		FilePrinter.writeLine("{");
		FilePrinter.increaseIndent();
		n.body.accept(this);
		FilePrinter.decreaseIndent();
		FilePrinter.writeLine("}");
		FilePrinter.decreaseIndent();
		FilePrinter.writeLine("};");
	}

	public void visit(ProcedureCall n)
	{
		FilePrinter.writeLine("procedures[array[pointer]].execute();");
	}

	public void visit(Loop loop)
	{
		FilePrinter.writeLine("while(array[pointer] != 0)");
		FilePrinter.writeLine("{");
		FilePrinter.increaseIndent();
		loop.body.accept(this);
		FilePrinter.decreaseIndent();
		FilePrinter.writeLine("}");
	}

	public void visit(Left left)
	{
		FilePrinter.writeLine("pointer--;");
	}

	public void visit(Right right)
	{
		FilePrinter.writeLine("pointer++;");
	}

	public void visit(Increment increment)
	{
		FilePrinter.writeLine("array[pointer]++;");
	}

	public void visit(Decrement decrement)
	{
		FilePrinter.writeLine("array[pointer]--;");
	}

	public void visit(Input input)
	{
		FilePrinter.writeLine("array[pointer] = (byte) System.in.read();");
	}

	public void visit(Output output)
	{
		FilePrinter.writeLine("System.out.print(\"\"+(char)array[pointer]);");
	}

	public void visit(Program program)
	{
		FilePrinter.openBuffer(className, "java");
		FilePrinter.writeLine("class " + className);
		FilePrinter.writeLine("{");
		FilePrinter.increaseIndent();
		FilePrinter.writeLine("public interface Procedure");
		FilePrinter.writeLine("{");
		FilePrinter.increaseIndent();
		FilePrinter.writeLine("void execute();");
		FilePrinter.decreaseIndent();
		FilePrinter.writeLine("}");
		FilePrinter.writeLine("private static Procedure[] procedures;");
		FilePrinter.writeLine("private static byte[] array;");
		FilePrinter.writeLine("private static int pointer;");
		FilePrinter.writeLine("private static String message;");
		FilePrinter.writeLine("public static void main(String[] args)");
		FilePrinter.writeLine("{");
		FilePrinter.increaseIndent();
		FilePrinter.writeLine("array = new byte[30000];");
		FilePrinter.writeLine("pointer = 0;");
		FilePrinter.writeLine("procedures = new Procedure[256];");
		program.body.accept(this);
		FilePrinter.decreaseIndent();
		FilePrinter.writeLine("}");
		FilePrinter.decreaseIndent();
		FilePrinter.writeLine("}");
		FilePrinter.closeBuffer();
	}

	public void visit(Number number)
	{
		for (int i = 0; i < number.iterations; i++)
		{
			number.command.accept(this);
		}
	}
}