package rummyj.nodes;

import java.util.ArrayList;

import rummyj.Node;
import rummyj.Visitor;

public class Sequence implements Node
{
  private ArrayList<Node> children;

  public Sequence()
  {
    children = new ArrayList<Node>();
  }

  public void accept(Visitor v)
  {
    for (Node child : children)
    {
      child.accept(v);
    }
  }

  public void add(Node instruction)
  {
    children.add(instruction);
  }
}