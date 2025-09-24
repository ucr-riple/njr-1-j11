package rummyj.visitors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rummyj.nodes.*;
import rummyj.nodes.Message;
import rummyj.nodes.Number;
import rummyj.FilePrinter;
import rummyj.Node;
import rummyj.Visitor;

public class OptimizationVisitor implements Visitor
{
  String oldString;

  public void visit(Left n)
  {
    oldString += ('<');
  }

  public void visit(Right n)
  {
    oldString += ('>');
  }

  public void visit(Increment n)
  {
    oldString += ('+');
  }

  public void visit(Decrement n)
  {
    oldString += ('-');
  }

  public void visit(Input n)
  {
    oldString += (',');
  }

  public void visit(Output n)
  {
    oldString += ('.');
  }

  public void visit(Loop n)
  {
    oldString += ('[');
    n.body.accept(this);
    oldString += (']');
  }

  public void visit(Program n)
  {
    oldString = "";
    n.body.accept(this);
    optimize();
  }

  public void visit(Message n)
  {
    oldString += ("\"" + n.character + "\"");
  }

  public void visit(Number n)
  {
    oldString += ("" + n.iterations);
    n.command.accept(this);
  }

  public void visit(Procedure n)
  {
    oldString += ("(");
    n.body.accept(this);
    oldString += (")");
  }

  public void visit(ProcedureCall n)
  {
    oldString += (":");
  }

  public void optimize()
  {
    String optimized = "";
    int i = 0;
    while (i < oldString.length())
    {
      int repeats = 1;
      char c = oldString.charAt(i);
      i++;
      if (isLoopable(c))
      {
        while (i < oldString.length() && c == oldString.charAt(i))
        {
          repeats++;
          i++;
        }
      }

      if (repeats > 1)
      {
        optimized += repeats;
        optimized += c;
      }
      else
      {
        optimized += c;
      }
    }
    System.out.println();
    System.out.println(" - Unoptimized: - ");
    System.out.println(oldString);
    System.out.println(" - Optimized: - ");
    System.out.println(optimized);
    System.out.println();
  }

  private boolean isLoopable(char c)
  {

    if (c == '<' || c == '>' || c == '+' || c == '-' || c == '.' || c == ','
        || c == ':' || c == '"')
    {
      return true;
    }
    return false;
  }
}