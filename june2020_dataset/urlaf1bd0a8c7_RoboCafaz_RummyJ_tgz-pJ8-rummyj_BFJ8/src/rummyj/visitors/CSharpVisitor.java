package rummyj.visitors;

import rummyj.nodes.*;
import rummyj.nodes.Message;
import rummyj.nodes.Number;
import rummyj.FilePrinter;
import rummyj.Visitor;

public class CSharpVisitor implements Visitor
{
  String className;

  public CSharpVisitor(String className)
  {
    this.className = className;
  }

  public void visit(Message n)
  {
    FilePrinter.writeLine("message=\"" + n.character + "\";");
    FilePrinter.writeLine("for(int i = 0;i < message();i++)");
    FilePrinter.writeLine("{");
    FilePrinter.increaseIndent();
    FilePrinter.writeLine("char c = message(i);");
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

  public void visit(Loop n)
  {
    FilePrinter.writeLine("while(array[pointer] != 0)");
    FilePrinter.writeLine("{");
    FilePrinter.increaseIndent();
    n.body.accept(this);
    FilePrinter.decreaseIndent();
    FilePrinter.writeLine("}");
  }

  public void visit(Left n)
  {
    FilePrinter.writeLine("pointer--;");
  }

  public void visit(Right n)
  {
    FilePrinter.writeLine("pointer++;");
  }

  public void visit(Increment n)
  {
    FilePrinter.writeLine("array[pointer]++;");
  }

  public void visit(Decrement n)
  {
    FilePrinter.writeLine("array[pointer]--;");
  }

  public void visit(Input n)
  {
    FilePrinter.writeLine("array[pointer] = (byte) System.in.read();");
  }

  public void visit(Output n)
  {
    FilePrinter.writeLine("Console.Out.Write(\"\"+(char)array[pointer]);");
  }

  public void visit(Program n)
  {
    FilePrinter.openBuffer(className, "cs");
    FilePrinter.writeLine("using System;");
    FilePrinter.writeLine("using System.Text;");
    FilePrinter.writeLine();
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
    FilePrinter.writeLine("public static void Main()");
    FilePrinter.writeLine("{");
    FilePrinter.increaseIndent();
    FilePrinter.writeLine("array = new byte[30000];");
    FilePrinter.writeLine("pointer = 0;");
    FilePrinter.writeLine("procedures = new Procedure[256];");
    n.body.accept(this);
    FilePrinter.decreaseIndent();
    FilePrinter.writeLine("}");
    FilePrinter.decreaseIndent();
    FilePrinter.writeLine("}");
    FilePrinter.closeBuffer();
  }

  public void visit(Number n)
  {
    for (int i = 0; i < n.iterations; i++)
    {
      n.command.accept(this);
    }
  }
}