package gramatica.Atuador;

import gramatica.Atuador.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  gramatica.Atuador.Absyn.Tarefa.Visitor<R,A>,
  gramatica.Atuador.Absyn.Acao.Visitor<R,A>,
  gramatica.Atuador.Absyn.Remedio.Visitor<R,A>
{}
