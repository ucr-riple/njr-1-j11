package gramatica.Monitor;
import gramatica.Monitor.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  gramatica.Monitor.Absyn.Tarefa.Visitor<gramatica.Monitor.Absyn.Tarefa,A>,
  gramatica.Monitor.Absyn.Acao.Visitor<gramatica.Monitor.Absyn.Acao,A>,
  gramatica.Monitor.Absyn.Dados.Visitor<gramatica.Monitor.Absyn.Dados,A>,
  gramatica.Monitor.Absyn.Quantidade.Visitor<gramatica.Monitor.Absyn.Quantidade,A>,
  gramatica.Monitor.Absyn.Operador.Visitor<gramatica.Monitor.Absyn.Operador,A>,
  gramatica.Monitor.Absyn.Hora.Visitor<gramatica.Monitor.Absyn.Hora,A>
{
/* Tarefa */
    public Tarefa visit(gramatica.Monitor.Absyn.ETarefa p, A arg)
    {
      Acao acao_ = p.acao_.accept(this, arg);

      return new gramatica.Monitor.Absyn.ETarefa(acao_);
    }

/* Acao */
    public Acao visit(gramatica.Monitor.Absyn.EAcao p, A arg)
    {
      Dados dados_ = p.dados_.accept(this, arg);
      Hora hora_ = p.hora_.accept(this, arg);

      return new gramatica.Monitor.Absyn.EAcao(dados_, hora_);
    }
    public Acao visit(gramatica.Monitor.Absyn.EAcao2 p, A arg)
    {
      Acao acao_1 = p.acao_1.accept(this, arg);
      Operador operador_ = p.operador_.accept(this, arg);
      Acao acao_2 = p.acao_2.accept(this, arg);

      return new gramatica.Monitor.Absyn.EAcao2(acao_1, operador_, acao_2);
    }

/* Dados */
    public Dados visit(gramatica.Monitor.Absyn.EDados p, A arg)
    {
      Quantidade quantidade_ = p.quantidade_.accept(this, arg);

      return new gramatica.Monitor.Absyn.EDados(quantidade_);
    }
    public Dados visit(gramatica.Monitor.Absyn.EDados1 p, A arg)
    {
      Quantidade quantidade_ = p.quantidade_.accept(this, arg);

      return new gramatica.Monitor.Absyn.EDados1(quantidade_);
    }
    public Dados visit(gramatica.Monitor.Absyn.EDados2 p, A arg)
    {
      Quantidade quantidade_ = p.quantidade_.accept(this, arg);

      return new gramatica.Monitor.Absyn.EDados2(quantidade_);
    }
    public Dados visit(gramatica.Monitor.Absyn.EDados3 p, A arg)
    {
      Quantidade quantidade_1 = p.quantidade_1.accept(this, arg);
      Quantidade quantidade_2 = p.quantidade_2.accept(this, arg);

      return new gramatica.Monitor.Absyn.EDados3(quantidade_1, quantidade_2);
    }

/* Quantidade */
    public Quantidade visit(gramatica.Monitor.Absyn.EQuantidade p, A arg)
    {
      Integer integer_ = p.integer_;

      return new gramatica.Monitor.Absyn.EQuantidade(integer_);
    }

/* Operador */
    public Operador visit(gramatica.Monitor.Absyn.EOperador p, A arg)
    {

      return new gramatica.Monitor.Absyn.EOperador();
    }

/* Hora */
    public Hora visit(gramatica.Monitor.Absyn.EHora p, A arg)
    {
      Integer integer_1 = p.integer_1;
      Integer integer_2 = p.integer_2;

      return new gramatica.Monitor.Absyn.EHora(integer_1, integer_2);
    }

}