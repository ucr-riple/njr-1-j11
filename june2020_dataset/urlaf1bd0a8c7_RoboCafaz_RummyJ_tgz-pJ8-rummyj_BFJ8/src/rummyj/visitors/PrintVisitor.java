package rummyj.visitors;

import rummyj.nodes.*;
import rummyj.nodes.Message;
import rummyj.nodes.Number;
import rummyj.FilePrinter;
import rummyj.Visitor;

public class PrintVisitor implements Visitor
{
	String	fileName;

	public PrintVisitor(String fileName)
	{
		this.fileName = fileName;
	}

	public void visit(Left n)
	{
		FilePrinter.write('<');
	}

	public void visit(Right n)
	{
		FilePrinter.write('>');
	}

	public void visit(Increment n)
	{
		FilePrinter.write('+');
	}

	public void visit(Decrement n)
	{
		FilePrinter.write('-');
	}

	public void visit(Input n)
	{
		FilePrinter.write(',');
	}

	public void visit(Output n)
	{
		FilePrinter.write('.');
	}

	public void visit(Loop n)
	{
		FilePrinter.write('[');
		n.body.accept(this);
		FilePrinter.write(']');
	}

	public void visit(Program n)
	{
		FilePrinter.openBuffer(fileName, "bf");
		n.body.accept(this);
		FilePrinter.closeBuffer();
	}

	public void visit(Message n)
	{
		FilePrinter.write("\"" + n.character + "\"");
	}

	public void visit(Number n)
	{
		FilePrinter.write("" + n.iterations);
		n.command.accept(this);
	}

	public void visit(Procedure n)
	{
		FilePrinter.write("(");
		n.body.accept(this);
		FilePrinter.write(")");
	}

	public void visit(ProcedureCall n)
	{
		FilePrinter.write(":");
	}
}