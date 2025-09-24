package gramatica.Centralizador;
import gramatica.Centralizador.Absyn.*;
/** BNFC-Generated Composition Visitor
*/

public class ComposVisitor<A> implements
  gramatica.Centralizador.Absyn.Tarefa.Visitor<gramatica.Centralizador.Absyn.Tarefa,A>,
  gramatica.Centralizador.Absyn.Acao.Visitor<gramatica.Centralizador.Absyn.Acao,A>,
  gramatica.Centralizador.Absyn.Coletar.Visitor<gramatica.Centralizador.Absyn.Coletar,A>,
  gramatica.Centralizador.Absyn.Aplicar.Visitor<gramatica.Centralizador.Absyn.Aplicar,A>,
  gramatica.Centralizador.Absyn.Dados.Visitor<gramatica.Centralizador.Absyn.Dados,A>,
  gramatica.Centralizador.Absyn.Operador.Visitor<gramatica.Centralizador.Absyn.Operador,A>,
  gramatica.Centralizador.Absyn.Medicacao.Visitor<gramatica.Centralizador.Absyn.Medicacao,A>,
  gramatica.Centralizador.Absyn.Quantidade.Visitor<gramatica.Centralizador.Absyn.Quantidade,A>,
  gramatica.Centralizador.Absyn.Remedio.Visitor<gramatica.Centralizador.Absyn.Remedio,A>
{
/* Tarefa */
    public Tarefa visit(gramatica.Centralizador.Absyn.ETask p, A arg)
    {
      Acao acao_ = p.acao_.accept(this, arg);

      return new gramatica.Centralizador.Absyn.ETask(acao_);
    }

/* Acao */
    public Acao visit(gramatica.Centralizador.Absyn.EAction1 p, A arg)
    {
      Coletar coletar_ = p.coletar_.accept(this, arg);
      Dados dados_ = p.dados_.accept(this, arg);

      return new gramatica.Centralizador.Absyn.EAction1(coletar_, dados_);
    }
    public Acao visit(gramatica.Centralizador.Absyn.EAction2 p, A arg)
    {
      Aplicar aplicar_ = p.aplicar_.accept(this, arg);
      Medicacao medicacao_ = p.medicacao_.accept(this, arg);

      return new gramatica.Centralizador.Absyn.EAction2(aplicar_, medicacao_);
    }
    public Acao visit(gramatica.Centralizador.Absyn.EAction3 p, A arg)
    {

      return new gramatica.Centralizador.Absyn.EAction3();
    }

/* Coletar */
    public Coletar visit(gramatica.Centralizador.Absyn.ECollect1 p, A arg)
    {

      return new gramatica.Centralizador.Absyn.ECollect1();
    }
    public Coletar visit(gramatica.Centralizador.Absyn.ECollect2 p, A arg)
    {

      return new gramatica.Centralizador.Absyn.ECollect2();
    }

/* Aplicar */
    public Aplicar visit(gramatica.Centralizador.Absyn.EApply1 p, A arg)
    {

      return new gramatica.Centralizador.Absyn.EApply1();
    }
    public Aplicar visit(gramatica.Centralizador.Absyn.EApply2 p, A arg)
    {

      return new gramatica.Centralizador.Absyn.EApply2();
    }

/* Dados */
    public Dados visit(gramatica.Centralizador.Absyn.EData1 p, A arg)
    {

      return new gramatica.Centralizador.Absyn.EData1();
    }
    public Dados visit(gramatica.Centralizador.Absyn.EData2 p, A arg)
    {

      return new gramatica.Centralizador.Absyn.EData2();
    }
    public Dados visit(gramatica.Centralizador.Absyn.EData3 p, A arg)
    {

      return new gramatica.Centralizador.Absyn.EData3();
    }
    public Dados visit(gramatica.Centralizador.Absyn.EData4 p, A arg)
    {

      return new gramatica.Centralizador.Absyn.EData4();
    }
    public Dados visit(gramatica.Centralizador.Absyn.EData5 p, A arg)
    {
      Dados dados_1 = p.dados_1.accept(this, arg);
      Operador operador_ = p.operador_.accept(this, arg);
      Dados dados_2 = p.dados_2.accept(this, arg);

      return new gramatica.Centralizador.Absyn.EData5(dados_1, operador_, dados_2);
    }

/* Operador */
    public Operador visit(gramatica.Centralizador.Absyn.EOp p, A arg)
    {

      return new gramatica.Centralizador.Absyn.EOp();
    }

/* Medicacao */
    public Medicacao visit(gramatica.Centralizador.Absyn.EMedic1 p, A arg)
    {
      Quantidade quantidade_ = p.quantidade_.accept(this, arg);
      Remedio remedio_ = p.remedio_.accept(this, arg);

      return new gramatica.Centralizador.Absyn.EMedic1(quantidade_, remedio_);
    }
    public Medicacao visit(gramatica.Centralizador.Absyn.EMedic2 p, A arg)
    {
      Remedio remedio_ = p.remedio_.accept(this, arg);

      return new gramatica.Centralizador.Absyn.EMedic2(remedio_);
    }

/* Quantidade */
    public Quantidade visit(gramatica.Centralizador.Absyn.EQtde1 p, A arg)
    {
      Integer integer_ = p.integer_;

      return new gramatica.Centralizador.Absyn.EQtde1(integer_);
    }

/* Remedio */
    public Remedio visit(gramatica.Centralizador.Absyn.ERemedy1 p, A arg)
    {

      return new gramatica.Centralizador.Absyn.ERemedy1();
    }
    public Remedio visit(gramatica.Centralizador.Absyn.ERemedy2 p, A arg)
    {

      return new gramatica.Centralizador.Absyn.ERemedy2();
    }

}