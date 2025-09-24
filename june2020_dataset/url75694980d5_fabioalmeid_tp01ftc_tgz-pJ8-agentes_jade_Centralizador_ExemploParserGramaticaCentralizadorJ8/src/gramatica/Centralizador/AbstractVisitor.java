package gramatica.Centralizador;
import gramatica.Centralizador.Absyn.*;
/** BNFC-Generated Abstract Visitor */
public class AbstractVisitor<R,A> implements AllVisitor<R,A> {
/* Tarefa */
    public R visit(gramatica.Centralizador.Absyn.ETask p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Centralizador.Absyn.Tarefa p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Acao */
    public R visit(gramatica.Centralizador.Absyn.EAction1 p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Centralizador.Absyn.EAction2 p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Centralizador.Absyn.EAction3 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Centralizador.Absyn.Acao p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Coletar */
    public R visit(gramatica.Centralizador.Absyn.ECollect1 p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Centralizador.Absyn.ECollect2 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Centralizador.Absyn.Coletar p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Aplicar */
    public R visit(gramatica.Centralizador.Absyn.EApply1 p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Centralizador.Absyn.EApply2 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Centralizador.Absyn.Aplicar p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Dados */
    public R visit(gramatica.Centralizador.Absyn.EData1 p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Centralizador.Absyn.EData2 p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Centralizador.Absyn.EData3 p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Centralizador.Absyn.EData4 p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Centralizador.Absyn.EData5 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Centralizador.Absyn.Dados p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Operador */
    public R visit(gramatica.Centralizador.Absyn.EOp p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Centralizador.Absyn.Operador p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Medicacao */
    public R visit(gramatica.Centralizador.Absyn.EMedic1 p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Centralizador.Absyn.EMedic2 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Centralizador.Absyn.Medicacao p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Quantidade */
    public R visit(gramatica.Centralizador.Absyn.EQtde1 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Centralizador.Absyn.Quantidade p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }
/* Remedio */
    public R visit(gramatica.Centralizador.Absyn.ERemedy1 p, A arg) { return visitDefault(p, arg); }
    public R visit(gramatica.Centralizador.Absyn.ERemedy2 p, A arg) { return visitDefault(p, arg); }
    public R visitDefault(gramatica.Centralizador.Absyn.Remedio p, A arg) {
      throw new IllegalArgumentException(this.getClass().getName() + ": " + p);
    }

}
