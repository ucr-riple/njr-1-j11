package rummyj.nodes;

import rummyj.Node;
import rummyj.Visitor;

public class Message implements Node
{
  public String character;

  public Message(String character)
  {
    this.character = character;
  }

  public void accept(Visitor v)
  {
    v.visit(this);
  }
}
