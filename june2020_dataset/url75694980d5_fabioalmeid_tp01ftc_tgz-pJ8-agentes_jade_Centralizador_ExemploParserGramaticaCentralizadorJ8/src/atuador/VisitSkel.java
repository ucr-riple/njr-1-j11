package atuador;
import atuador.Absyn.*;
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
    public R visit(atuador.Absyn.ETarefa p, A arg)
    {
      /* Code For ETarefa Goes Here */

      p.acao_.accept(new AcaoVisitor<R,A>(), arg);

      return null;
    }

  }
  public class AcaoVisitor<R,A> implements Acao.Visitor<R,A>
  {
    public R visit(atuador.Absyn.EAcao p, A arg)
    {
      /* Code For EAcao Goes Here */

      p.remedio_.accept(new RemedioVisitor<R,A>(), arg);

      return null;
    }
    public R visit(atuador.Absyn.EAcao1 p, A arg)
    {
      /* Code For EAcao1 Goes Here */

      p.remedio_.accept(new RemedioVisitor<R,A>(), arg);

      return null;
    }

  }
  public class RemedioVisitor<R,A> implements Remedio.Visitor<R,A>
  {
    public R visit(atuador.Absyn.ERemedio p, A arg)
    {
      /* Code For ERemedio Goes Here */


      return null;
    }
    public R visit(atuador.Absyn.ERemedio1 p, A arg)
    {
      /* Code For ERemedio1 Goes Here */


      return null;
    }

  }
}