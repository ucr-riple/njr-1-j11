package atuador;

import atuador.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  atuador.Absyn.Tarefa.Visitor<R,A>,
  atuador.Absyn.Acao.Visitor<R,A>,
  atuador.Absyn.Remedio.Visitor<R,A>
{}
