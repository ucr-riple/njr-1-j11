package atuador;
import atuador.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Tarefa */
    public R visit(atuador.Absyn.ETarefa p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(atuador.Absyn.Tarefa p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Acao */
    public R visit(atuador.Absyn.EAcao p, A arg) { return visitDefault(p, arg); }
    public R visit(atuador.Absyn.EAcao1 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(atuador.Absyn.Acao p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Remedio */
    public R visit(atuador.Absyn.ERemedio p, A arg) { return visitDefault(p, arg); }
    public R visit(atuador.Absyn.ERemedio1 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(atuador.Absyn.Remedio p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
