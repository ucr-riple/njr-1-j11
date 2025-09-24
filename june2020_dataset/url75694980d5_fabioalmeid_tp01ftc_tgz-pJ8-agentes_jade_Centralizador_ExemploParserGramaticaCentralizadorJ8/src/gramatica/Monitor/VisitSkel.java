package gramatica.Monitor;
import gramatica.Monitor.Absyn.*;
/*** BNFC-Generated Visitor Design Pattern Skeleton. ***/
/* This implements the common visitor design pattern.
   Tests show it to be slightly less efficient than the
   instanceof method, but easier to use. 
   Replace the R and A parameters with the desired return
   and context types.*/

public class VisitSkel
{
  public class TarefaVisitor<R,A> implements Tarefa.Visitor<R,A>
  {
    public R visit(gramatica.Monitor.Absyn.ETarefa p, A arg)
    {
      /* Code For ETarefa Goes Here */

      p.acao_.accept(new AcaoVisitor<R,A>(), arg);

      return null;
    }

  }
  public class AcaoVisitor<R,A> implements Acao.Visitor<R,A>
  {
    public R visit(gramatica.Monitor.Absyn.EAcao p, A arg)
    {
      /* Code For EAcao Goes Here */

      p.dados_.accept(new DadosVisitor<R,A>(), arg);
      p.hora_.accept(new HoraVisitor<R,A>(), arg);

      return null;
    }
    public R visit(gramatica.Monitor.Absyn.EAcao2 p, A arg)
    {
      /* Code For EAcao2 Goes Here */

      p.acao_1.accept(new AcaoVisitor<R,A>(), arg);
      p.operador_.accept(new OperadorVisitor<R,A>(), arg);
      p.acao_2.accept(new AcaoVisitor<R,A>(), arg);

      return null;
    }

  }
  public class DadosVisitor<R,A> implements Dados.Visitor<R,A>
  {
    public R visit(gramatica.Monitor.Absyn.EDados p, A arg)
    {
      /* Code For EDados Goes Here */

      p.quantidade_.accept(new QuantidadeVisitor<R,A>(), arg);

      return null;
    }
    public R visit(gramatica.Monitor.Absyn.EDados1 p, A arg)
    {
      /* Code For EDados1 Goes Here */

      p.quantidade_.accept(new QuantidadeVisitor<R,A>(), arg);

      return null;
    }
    public R visit(gramatica.Monitor.Absyn.EDados2 p, A arg)
    {
      /* Code For EDados2 Goes Here */

      p.quantidade_.accept(new QuantidadeVisitor<R,A>(), arg);

      return null;
    }
    public R visit(gramatica.Monitor.Absyn.EDados3 p, A arg)
    {
      /* Code For EDados3 Goes Here */

      p.quantidade_1.accept(new QuantidadeVisitor<R,A>(), arg);
      p.quantidade_2.accept(new QuantidadeVisitor<R,A>(), arg);

      return null;
    }

  }
  public class QuantidadeVisitor<R,A> implements Quantidade.Visitor<R,A>
  {
    public R visit(gramatica.Monitor.Absyn.EQuantidade p, A arg)
    {
      /* Code For EQuantidade Goes Here */

      //p.integer_;

      return null;
    }

  }
  public class OperadorVisitor<R,A> implements Operador.Visitor<R,A>
  {
    public R visit(gramatica.Monitor.Absyn.EOperador p, A arg)
    {
      /* Code For EOperador Goes Here */


      return null;
    }

  }
  public class HoraVisitor<R,A> implements Hora.Visitor<R,A>
  {
    public R visit(gramatica.Monitor.Absyn.EHora p, A arg)
    {
      /* Code For EHora Goes Here */

      //p.integer_1;
      //p.integer_2;

      return null;
    }

  }
}