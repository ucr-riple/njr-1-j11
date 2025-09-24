package gramatica.Monitor;

import gramatica.Monitor.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  gramatica.Monitor.Absyn.Tarefa.Visitor<R,A>,
  gramatica.Monitor.Absyn.Acao.Visitor<R,A>,
  gramatica.Monitor.Absyn.Dados.Visitor<R,A>,
  gramatica.Monitor.Absyn.Quantidade.Visitor<R,A>,
  gramatica.Monitor.Absyn.Operador.Visitor<R,A>,
  gramatica.Monitor.Absyn.Hora.Visitor<R,A>
{}
