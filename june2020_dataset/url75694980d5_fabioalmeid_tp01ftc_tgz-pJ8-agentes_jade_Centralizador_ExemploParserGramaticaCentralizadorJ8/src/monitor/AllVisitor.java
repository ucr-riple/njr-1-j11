package monitor;

import monitor.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  monitor.Absyn.Tarefa.Visitor<R,A>,
  monitor.Absyn.Acao.Visitor<R,A>,
  monitor.Absyn.Dados.Visitor<R,A>,
  monitor.Absyn.Quantidade.Visitor<R,A>,
  monitor.Absyn.Operador.Visitor<R,A>,
  monitor.Absyn.Hora.Visitor<R,A>
{}
