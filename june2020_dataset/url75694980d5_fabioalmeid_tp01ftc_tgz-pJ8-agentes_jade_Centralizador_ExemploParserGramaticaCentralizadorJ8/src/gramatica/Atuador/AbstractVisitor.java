package gramatica.Atuador;
import gramatica.Atuador.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Tarefa */
    public R visit(gramatica.Atuador.Absyn.ETarefa p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Atuador.Absyn.Tarefa p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Acao */
    public R visit(gramatica.Atuador.Absyn.EAcao p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Atuador.Absyn.EAcao1 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Atuador.Absyn.Acao p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Remedio */
    public R visit(gramatica.Atuador.Absyn.ERemedio p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Atuador.Absyn.ERemedio1 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Atuador.Absyn.Remedio p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
