package rummyj.nodes;

import rummyj.Node;
import rummyj.Visitor;

public class Procedure implements Node
{
  public Node body;

  public Procedure(Node body)
  {
    this.body = body;
  }

  public void accept(Visitor v)
  {
    v.visit(this);
  }
}