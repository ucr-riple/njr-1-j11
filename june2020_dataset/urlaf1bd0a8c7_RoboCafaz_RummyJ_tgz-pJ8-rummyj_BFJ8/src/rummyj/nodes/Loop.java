package rummyj.nodes;

import rummyj.Node;
import rummyj.Visitor;

public class Loop implements Node
{
  public Node body;

  public Loop(Node body)
  {
    this.body = body;
  }

  public void accept(Visitor v)
  {
    v.visit(this);
  }
}