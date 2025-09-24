package atuador;

import atuador.Absyn.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/** BNFC-Generated Fold Visitor */
public abstract class FoldVisitor<R,A> implements AllVisitor<R,A> {
    public abstract R leaf(A arg);
    public abstract R combine(R x, R y, A arg);

/* Tarefa */
    public R visit(atuador.Absyn.ETarefa p, A arg) {
      R r = leaf(arg);
      r = combine(p.acao_.accept(this, arg), r, arg);
      return r;
    }

/* Acao */
    public R visit(atuador.Absyn.EAcao p, A arg) {
      R r = leaf(arg);
      r = combine(p.remedio_.accept(this, arg), r, arg);
      return r;
    }
    public R visit(atuador.Absyn.EAcao1 p, A arg) {
      R r = leaf(arg);
      r = combine(p.remedio_.accept(this, arg), r, arg);
      return r;
    }

/* Remedio */
    public R visit(atuador.Absyn.ERemedio p, A arg) {
      R r = leaf(arg);
      return r;
    }
    public R visit(atuador.Absyn.ERemedio1 p, A arg) {
      R r = leaf(arg);
      return r;
    }


}
