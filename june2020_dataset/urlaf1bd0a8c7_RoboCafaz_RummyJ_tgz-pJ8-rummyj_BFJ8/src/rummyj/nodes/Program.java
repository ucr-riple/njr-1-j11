package rummyj.nodes;

import rummyj.Node;
import rummyj.Visitor;

public class Program implements Node
{
  public Node body;

  public Program(Node body)
  {
    this.body = body;
  }

  public void accept(Visitor v)
  {
    v.visit(this);
  }
}