package gramatica.Monitor;

import gramatica.Monitor.Absyn.*;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/** BNFC-Generated Fold Visitor */
public abstract class FoldVisitor<R,A> implements AllVisitor<R,A> {
    public abstract R leaf(A arg);
    public abstract R combine(R x, R y, A arg);

/* Tarefa */
    public R visit(gramatica.Monitor.Absyn.ETarefa p, A arg) {
      R r = leaf(arg);
      r = combine(p.acao_.accept(this, arg), r, arg);
      return r;
    }

/* Acao */
    public R visit(gramatica.Monitor.Absyn.EAcao p, A arg) {
      R r = leaf(arg);
      r = combine(p.dados_.accept(this, arg), r, arg);
      r = combine(p.hora_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(gramatica.Monitor.Absyn.EAcao2 p, A arg) {
      R r = leaf(arg);
      r = combine(p.acao_1.accept(this, arg), r, arg);
      r = combine(p.operador_.accept(this, arg), r, arg);
      r = combine(p.acao_2.accept(this, arg), r, arg);
      return r;
    }

/* Dados */
    public R visit(gramatica.Monitor.Absyn.EDados p, A arg) {
      R r = leaf(arg);
      r = combine(p.quantidade_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(gramatica.Monitor.Absyn.EDados1 p, A arg) {
      R r = leaf(arg);
      r = combine(p.quantidade_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(gramatica.Monitor.Absyn.EDados2 p, A arg) {
      R r = leaf(arg);
      r = combine(p.quantidade_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(gramatica.Monitor.Absyn.EDados3 p, A arg) {
      R r = leaf(arg);
      r = combine(p.quantidade_1.accept(this, arg), r, arg);
      r = combine(p.quantidade_2.accept(this, arg), r, arg);
      return r;
    }

/* Quantidade */
    public R visit(gramatica.Monitor.Absyn.EQuantidade p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Operador */
    public R visit(gramatica.Monitor.Absyn.EOperador p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Hora */
    public R visit(gramatica.Monitor.Absyn.EHora p, A arg) {
      R r = leaf(arg);
      return r;
    }


}
