package Centralizador;
import Centralizador.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  Centralizador.Absyn.Tarefa.Visitor<Centralizador.Absyn.Tarefa,A>,
  Centralizador.Absyn.Acao.Visitor<Centralizador.Absyn.Acao,A>,
  Centralizador.Absyn.Coletar.Visitor<Centralizador.Absyn.Coletar,A>,
  Centralizador.Absyn.Aplicar.Visitor<Centralizador.Absyn.Aplicar,A>,
  Centralizador.Absyn.Dados.Visitor<Centralizador.Absyn.Dados,A>,
  Centralizador.Absyn.Operador.Visitor<Centralizador.Absyn.Operador,A>,
  Centralizador.Absyn.Medicacao.Visitor<Centralizador.Absyn.Medicacao,A>,
  Centralizador.Absyn.Quantidade.Visitor<Centralizador.Absyn.Quantidade,A>,
  Centralizador.Absyn.Remedio.Visitor<Centralizador.Absyn.Remedio,A>
{
/* Tarefa */
    public Tarefa visit(Centralizador.Absyn.ETask p, A arg)
    {
      Acao acao_ = p.acao_.accept(this, arg);

      return new Centralizador.Absyn.ETask(acao_);
    }

/* Acao */
    public Acao visit(Centralizador.Absyn.EAction1 p, A arg)
    {
      Coletar coletar_ = p.coletar_.accept(this, arg);
      Dados dados_ = p.dados_.accept(this, arg);

      return new Centralizador.Absyn.EAction1(coletar_, dados_);
    }
    public Acao visit(Centralizador.Absyn.EAction2 p, A arg)
    {
      Aplicar aplicar_ = p.aplicar_.accept(this, arg);
      Medicacao medicacao_ = p.medicacao_.accept(this, arg);

      return new Centralizador.Absyn.EAction2(aplicar_, medicacao_);
    }
    public Acao visit(Centralizador.Absyn.EAction3 p, A arg)
    {

      return new Centralizador.Absyn.EAction3();
    }

/* Coletar */
    public Coletar visit(Centralizador.Absyn.ECollect1 p, A arg)
    {

      return new Centralizador.Absyn.ECollect1();
    }
    public Coletar visit(Centralizador.Absyn.ECollect2 p, A arg)
    {

      return new Centralizador.Absyn.ECollect2();
    }

/* Aplicar */
    public Aplicar visit(Centralizador.Absyn.EApply1 p, A arg)
    {

      return new Centralizador.Absyn.EApply1();
    }
    public Aplicar visit(Centralizador.Absyn.EApply2 p, A arg)
    {

      return new Centralizador.Absyn.EApply2();
    }

/* Dados */
    public Dados visit(Centralizador.Absyn.EData1 p, A arg)
    {

      return new Centralizador.Absyn.EData1();
    }
    public Dados visit(Centralizador.Absyn.EData2 p, A arg)
    {

      return new Centralizador.Absyn.EData2();
    }
    public Dados visit(Centralizador.Absyn.EData3 p, A arg)
    {

      return new Centralizador.Absyn.EData3();
    }
    public Dados visit(Centralizador.Absyn.EData4 p, A arg)
    {

      return new Centralizador.Absyn.EData4();
    }
    public Dados visit(Centralizador.Absyn.EData5 p, A arg)
    {
      Dados dados_1 = p.dados_1.accept(this, arg);
      Operador operador_ = p.operador_.accept(this, arg);
      Dados dados_2 = p.dados_2.accept(this, arg);

      return new Centralizador.Absyn.EData5(dados_1, operador_, dados_2);
    }

/* Operador */
    public Operador visit(Centralizador.Absyn.EOp p, A arg)
    {

      return new Centralizador.Absyn.EOp();
    }

/* Medicacao */
    public Medicacao visit(Centralizador.Absyn.EMedic1 p, A arg)
    {
      Quantidade quantidade_ = p.quantidade_.accept(this, arg);
      Remedio remedio_ = p.remedio_.accept(this, arg);

      return new Centralizador.Absyn.EMedic1(quantidade_, remedio_);
    }
    public Medicacao visit(Centralizador.Absyn.EMedic2 p, A arg)
    {
      Remedio remedio_ = p.remedio_.accept(this, arg);

      return new Centralizador.Absyn.EMedic2(remedio_);
    }

/* Quantidade */
    public Quantidade visit(Centralizador.Absyn.EQtde1 p, A arg)
    {
      Integer integer_ = p.integer_;

      return new Centralizador.Absyn.EQtde1(integer_);
    }

/* Remedio */
    public Remedio visit(Centralizador.Absyn.ERemedy1 p, A arg)
    {

      return new Centralizador.Absyn.ERemedy1();
    }
    public Remedio visit(Centralizador.Absyn.ERemedy2 p, A arg)
    {

      return new Centralizador.Absyn.ERemedy2();
    }

}