package rummyj.nodes;

import rummyj.Node;
import rummyj.Visitor;

public class Right implements Node
{
  public void accept(Visitor v)
  {
    v.visit(this);
  }
}