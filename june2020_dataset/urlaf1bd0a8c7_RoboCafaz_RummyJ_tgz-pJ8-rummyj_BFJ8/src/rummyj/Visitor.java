package rummyj;

import rummyj.nodes.*;
import rummyj.nodes.Message;
import rummyj.nodes.Number;
import rummyj.Visitor;

public interface Visitor
{
  void visit(Message n);

  void visit(Procedure n);

  void visit(ProcedureCall n);

  void visit(Loop n);

  void visit(Left n);

  void visit(Right n);

  void visit(Increment n);

  void visit(Decrement n);

  void visit(Number n);

  void visit(Input n);

  void visit(Output n);

  void visit(Program n);
}