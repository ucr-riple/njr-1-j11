package Centralizador;

import Centralizador.Absyn.*;

/** BNFC-Generated All Visitor */
public interface AllVisitor<R,A> extends
  Centralizador.Absyn.Tarefa.Visitor<R,A>,
  Centralizador.Absyn.Acao.Visitor<R,A>,
  Centralizador.Absyn.Coletar.Visitor<R,A>,
  Centralizador.Absyn.Aplicar.Visitor<R,A>,
  Centralizador.Absyn.Dados.Visitor<R,A>,
  Centralizador.Absyn.Operador.Visitor<R,A>,
  Centralizador.Absyn.Medicacao.Visitor<R,A>,
  Centralizador.Absyn.Quantidade.Visitor<R,A>,
  Centralizador.Absyn.Remedio.Visitor<R,A>
{}
