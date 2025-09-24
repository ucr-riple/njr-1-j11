package rummyj.nodes;

import rummyj.Node;
import rummyj.Visitor;

public class Number implements Node
{
  public int  iterations;
  public Node command;

  public Number(int number, Node command)
  {
    iterations = number;
    this.command = command;
  }

  public void accept(Visitor v)
  {
    v.visit(this);
  }
}