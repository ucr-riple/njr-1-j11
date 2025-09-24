package gramatica.Monitor;
import gramatica.Monitor.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Tarefa */
    public R visit(gramatica.Monitor.Absyn.ETarefa p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Monitor.Absyn.Tarefa p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Acao */
    public R visit(gramatica.Monitor.Absyn.EAcao p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Monitor.Absyn.EAcao2 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Monitor.Absyn.Acao p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Dados */
    public R visit(gramatica.Monitor.Absyn.EDados p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Monitor.Absyn.EDados1 p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Monitor.Absyn.EDados2 p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Monitor.Absyn.EDados3 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Monitor.Absyn.Dados p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Quantidade */
    public R visit(gramatica.Monitor.Absyn.EQuantidade p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Monitor.Absyn.Quantidade p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Operador */
    public R visit(gramatica.Monitor.Absyn.EOperador p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Monitor.Absyn.Operador p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Hora */
    public R visit(gramatica.Monitor.Absyn.EHora p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Monitor.Absyn.Hora p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
