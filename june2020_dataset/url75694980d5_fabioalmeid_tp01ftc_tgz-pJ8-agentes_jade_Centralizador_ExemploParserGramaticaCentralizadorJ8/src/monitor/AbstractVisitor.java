package monitor;
import monitor.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Tarefa */
    public R visit(monitor.Absyn.ETarefa p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(monitor.Absyn.Tarefa p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Acao */
    public R visit(monitor.Absyn.EAcao p, A arg) { return visitDefault(p, arg); }
    public R visit(monitor.Absyn.EAcao2 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(monitor.Absyn.Acao p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Dados */
    public R visit(monitor.Absyn.EDados p, A arg) { return visitDefault(p, arg); }
    public R visit(monitor.Absyn.EDados1 p, A arg) { return visitDefault(p, arg); }
    public R visit(monitor.Absyn.EDados2 p, A arg) { return visitDefault(p, arg); }
    public R visit(monitor.Absyn.EDados3 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(monitor.Absyn.Dados p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Quantidade */
    public R visit(monitor.Absyn.EQuantidade p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(monitor.Absyn.Quantidade p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Operador */
    public R visit(monitor.Absyn.EOperador p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(monitor.Absyn.Operador p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Hora */
    public R visit(monitor.Absyn.EHora p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(monitor.Absyn.Hora p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
