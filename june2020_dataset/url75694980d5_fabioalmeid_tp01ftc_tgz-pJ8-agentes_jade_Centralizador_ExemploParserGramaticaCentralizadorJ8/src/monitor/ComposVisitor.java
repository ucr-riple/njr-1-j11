package monitor;
import monitor.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  monitor.Absyn.Tarefa.Visitor<monitor.Absyn.Tarefa,A>,
  monitor.Absyn.Acao.Visitor<monitor.Absyn.Acao,A>,
  monitor.Absyn.Dados.Visitor<monitor.Absyn.Dados,A>,
  monitor.Absyn.Quantidade.Visitor<monitor.Absyn.Quantidade,A>,
  monitor.Absyn.Operador.Visitor<monitor.Absyn.Operador,A>,
  monitor.Absyn.Hora.Visitor<monitor.Absyn.Hora,A>
{
/* Tarefa */
    public Tarefa visit(monitor.Absyn.ETarefa p, A arg)
    {
      Acao acao_ = p.acao_.accept(this, arg);

      return new monitor.Absyn.ETarefa(acao_);
    }

/* Acao */
    public Acao visit(monitor.Absyn.EAcao p, A arg)
    {
      Dados dados_ = p.dados_.accept(this, arg);
      Hora hora_ = p.hora_.accept(this, arg);

      return new monitor.Absyn.EAcao(dados_, hora_);
    }
    public Acao visit(monitor.Absyn.EAcao2 p, A arg)
    {
      Acao acao_1 = p.acao_1.accept(this, arg);
      Operador operador_ = p.operador_.accept(this, arg);
      Acao acao_2 = p.acao_2.accept(this, arg);

      return new monitor.Absyn.EAcao2(acao_1, operador_, acao_2);
    }

/* Dados */
    public Dados visit(monitor.Absyn.EDados p, A arg)
    {
      Quantidade quantidade_ = p.quantidade_.accept(this, arg);

      return new monitor.Absyn.EDados(quantidade_);
    }
    public Dados visit(monitor.Absyn.EDados1 p, A arg)
    {
      Quantidade quantidade_ = p.quantidade_.accept(this, arg);

      return new monitor.Absyn.EDados1(quantidade_);
    }
    public Dados visit(monitor.Absyn.EDados2 p, A arg)
    {
      Quantidade quantidade_ = p.quantidade_.accept(this, arg);

      return new monitor.Absyn.EDados2(quantidade_);
    }
    public Dados visit(monitor.Absyn.EDados3 p, A arg)
    {
      Quantidade quantidade_1 = p.quantidade_1.accept(this, arg);
      Quantidade quantidade_2 = p.quantidade_2.accept(this, arg);

      return new monitor.Absyn.EDados3(quantidade_1, quantidade_2);
    }

/* Quantidade */
    public Quantidade visit(monitor.Absyn.EQuantidade p, A arg)
    {
      Integer integer_ = p.integer_;

      return new monitor.Absyn.EQuantidade(integer_);
    }

/* Operador */
    public Operador visit(monitor.Absyn.EOperador p, A arg)
    {

      return new monitor.Absyn.EOperador();
    }

/* Hora */
    public Hora visit(monitor.Absyn.EHora p, A arg)
    {
      Integer integer_1 = p.integer_1;
      Integer integer_2 = p.integer_2;

      return new monitor.Absyn.EHora(integer_1, integer_2);
    }

}