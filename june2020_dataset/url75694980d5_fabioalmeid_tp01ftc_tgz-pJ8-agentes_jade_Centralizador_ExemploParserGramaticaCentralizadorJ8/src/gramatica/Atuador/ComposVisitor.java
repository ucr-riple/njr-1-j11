package gramatica.Atuador;
import gramatica.Atuador.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  gramatica.Atuador.Absyn.Tarefa.Visitor<gramatica.Atuador.Absyn.Tarefa,A>,
  gramatica.Atuador.Absyn.Acao.Visitor<gramatica.Atuador.Absyn.Acao,A>,
  gramatica.Atuador.Absyn.Remedio.Visitor<gramatica.Atuador.Absyn.Remedio,A>
{
/* Tarefa */
    public Tarefa visit(gramatica.Atuador.Absyn.ETarefa p, A arg)
    {
      Acao acao_ = p.acao_.accept(this, arg);

      return new gramatica.Atuador.Absyn.ETarefa(acao_);
    }

/* Acao */
    public Acao visit(gramatica.Atuador.Absyn.EAcao p, A arg)
    {
      Remedio remedio_ = p.remedio_.accept(this, arg);

      return new gramatica.Atuador.Absyn.EAcao(remedio_);
    }
    public Acao visit(gramatica.Atuador.Absyn.EAcao1 p, A arg)
    {
      Remedio remedio_ = p.remedio_.accept(this, arg);

      return new gramatica.Atuador.Absyn.EAcao1(remedio_);
    }

/* Remedio */
    public Remedio visit(gramatica.Atuador.Absyn.ERemedio p, A arg)
    {

      return new gramatica.Atuador.Absyn.ERemedio();
    }
    public Remedio visit(gramatica.Atuador.Absyn.ERemedio1 p, A arg)
    {

      return new gramatica.Atuador.Absyn.ERemedio1();
    }

}