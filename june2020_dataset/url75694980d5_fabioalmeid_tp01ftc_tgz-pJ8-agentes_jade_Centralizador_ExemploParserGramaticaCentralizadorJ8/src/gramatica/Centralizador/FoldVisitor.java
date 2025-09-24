package gramatica.Centralizador;


import gramatica.Centralizador.Absyn.*;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


/** BNFC-Generated Fold Visitor */
public abstract class FoldVisitor<R,A> implements AllVisitor<R,A> {
    public abstract R leaf(A arg);
    public abstract R combine(R x, R y, A arg);

/* Tarefa */
    public R visit(gramatica.Centralizador.Absyn.ETask p, A arg) {
      R r = leaf(arg);
      r = combine(p.acao_.accept(this, arg), r, arg);
      return r;
    }

/* Acao */
    public R visit(gramatica.Centralizador.Absyn.EAction1 p, A arg) {
      R r = leaf(arg);
      r = combine(p.coletar_.accept(this, arg), r, arg);
      r = combine(p.dados_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(gramatica.Centralizador.Absyn.EAction2 p, A arg) {
      R r = leaf(arg);
      r = combine(p.aplicar_.accept(this, arg), r, arg);
      r = combine(p.medicacao_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(gramatica.Centralizador.Absyn.EAction3 p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Coletar */
    public R visit(gramatica.Centralizador.Absyn.ECollect1 p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(gramatica.Centralizador.Absyn.ECollect2 p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Aplicar */
    public R visit(gramatica.Centralizador.Absyn.EApply1 p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(gramatica.Centralizador.Absyn.EApply2 p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Dados */
    public R visit(gramatica.Centralizador.Absyn.EData1 p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(gramatica.Centralizador.Absyn.EData2 p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(gramatica.Centralizador.Absyn.EData3 p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(gramatica.Centralizador.Absyn.EData4 p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(gramatica.Centralizador.Absyn.EData5 p, A arg) {
      R r = leaf(arg);
      r = combine(p.dados_1.accept(this, arg), r, arg);
      r = combine(p.operador_.accept(this, arg), r, arg);
      r = combine(p.dados_2.accept(this, arg), r, arg);
      return r;
    }

/* Operador */
    public R visit(gramatica.Centralizador.Absyn.EOp p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Medicacao */
    public R visit(gramatica.Centralizador.Absyn.EMedic1 p, A arg) {
      R r = leaf(arg);
      r = combine(p.quantidade_.accept(this, arg), r, arg);
      r = combine(p.remedio_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(gramatica.Centralizador.Absyn.EMedic2 p, A arg) {
      R r = leaf(arg);
      r = combine(p.remedio_.accept(this, arg), r, arg);
      return r;
    }

/* Quantidade */
    public R visit(gramatica.Centralizador.Absyn.EQtde1 p, A arg) {
      R r = leaf(arg);
      return r;
    }

/* Remedio */
    public R visit(gramatica.Centralizador.Absyn.ERemedy1 p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(gramatica.Centralizador.Absyn.ERemedy2 p, A arg) {
      R r = leaf(arg);
      return r;
    }


}
