package gramatica.Centralizador;

import gramatica.Centralizador.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  gramatica.Centralizador.Absyn.Tarefa.Visitor<R,A>,
  gramatica.Centralizador.Absyn.Acao.Visitor<R,A>,
  gramatica.Centralizador.Absyn.Coletar.Visitor<R,A>,
  gramatica.Centralizador.Absyn.Aplicar.Visitor<R,A>,
  gramatica.Centralizador.Absyn.Dados.Visitor<R,A>,
  gramatica.Centralizador.Absyn.Operador.Visitor<R,A>,
  gramatica.Centralizador.Absyn.Medicacao.Visitor<R,A>,
  gramatica.Centralizador.Absyn.Quantidade.Visitor<R,A>,
  gramatica.Centralizador.Absyn.Remedio.Visitor<R,A>
{}
