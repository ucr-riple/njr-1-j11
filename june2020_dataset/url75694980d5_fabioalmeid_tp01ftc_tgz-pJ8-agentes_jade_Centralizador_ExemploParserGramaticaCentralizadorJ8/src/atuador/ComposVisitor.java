package atuador;
import atuador.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  atuador.Absyn.Tarefa.Visitor<atuador.Absyn.Tarefa,A>,
  atuador.Absyn.Acao.Visitor<atuador.Absyn.Acao,A>,
  atuador.Absyn.Remedio.Visitor<atuador.Absyn.Remedio,A>
{
/* Tarefa */
    public Tarefa visit(atuador.Absyn.ETarefa p, A arg)
    {
      Acao acao_ = p.acao_.accept(this, arg);

      return new atuador.Absyn.ETarefa(acao_);
    }

/* Acao */
    public Acao visit(atuador.Absyn.EAcao p, A arg)
    {
      Remedio remedio_ = p.remedio_.accept(this, arg);

      return new atuador.Absyn.EAcao(remedio_);
    }
    public Acao visit(atuador.Absyn.EAcao1 p, A arg)
    {
      Remedio remedio_ = p.remedio_.accept(this, arg);

      return new atuador.Absyn.EAcao1(remedio_);
    }

/* Remedio */
    public Remedio visit(atuador.Absyn.ERemedio p, A arg)
    {

      return new atuador.Absyn.ERemedio();
    }
    public Remedio visit(atuador.Absyn.ERemedio1 p, A arg)
    {

      return new atuador.Absyn.ERemedio1();
    }

}