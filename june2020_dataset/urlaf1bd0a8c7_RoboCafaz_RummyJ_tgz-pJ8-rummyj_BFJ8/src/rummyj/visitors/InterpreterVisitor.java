package rummyj.visitors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rummyj.nodes.*;
import rummyj.nodes.Message;
import rummyj.nodes.Number;
import rummyj.Visitor;

public class InterpreterVisitor implements Visitor
{
  private byte[]               array;
  private int                  pointer;
  private Map<Byte, Procedure> procedures;

  public void visit(Left n)
  {
    pointer--;
  }

  public void visit(Right n)
  {
    pointer++;
  }

  public void visit(Increment n)
  {
    array[pointer]++;
  }

  public void visit(Decrement n)
  {
    array[pointer]--;
  }

  public void visit(Input n)
  {
    try
    {
      array[pointer] = (byte) System.in.read();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public void visit(Output n)
  {
    System.out.print((char) array[pointer]);
  }

  public void visit(Loop n)
  {
    while (array[pointer] != 0)
    {
      n.body.accept(this);
    }
  }

  public void visit(Program n)
  {
    array = new byte[30000];
    pointer = 0;
    procedures = new HashMap<Byte, Procedure>();
    n.body.accept(this);
  }

  public void visit(Message n)
  {
    for (int i = 0; i < n.character.length(); i++)
    {
      array[pointer] = (byte) n.character.charAt(i);
      pointer++;
      array[pointer] = 0;
    }
  }

  public void visit(Procedure n)
  {
    procedures.put(Byte.parseByte("" + (array[pointer])), n);
  }

  public void visit(ProcedureCall n)
  {
    Procedure procedure = procedures.get(Byte.parseByte("" + array[pointer]));
    if (procedure != null)
    {
      procedure.body.accept(this);
    }
  }

  public void visit(Number number)
  {
    for (int i = 0; i < number.iterations; i++)
    {
      number.command.accept(this);
    }
  }
}