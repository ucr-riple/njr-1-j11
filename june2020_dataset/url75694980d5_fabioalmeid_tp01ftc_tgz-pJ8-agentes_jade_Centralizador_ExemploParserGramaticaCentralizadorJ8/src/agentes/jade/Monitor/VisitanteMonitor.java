package agentes.jade.Monitor;

import gramatica.Monitor.Absyn.Acao;
import gramatica.Monitor.Absyn.Dados;
import gramatica.Monitor.Absyn.Hora;
import gramatica.Monitor.Absyn.Operador;
import gramatica.Monitor.Absyn.Quantidade;
import gramatica.Monitor.Absyn.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class VisitanteMonitor {
	List<Afericao> afericoes;

	public List<Afericao> getAfericoes() {
		return afericoes;
	}

	public class TarefaVisitor<R, A> implements Tarefa.Visitor<R, A> {
		public R visit(gramatica.Monitor.Absyn.ETarefa p, A arg) {
			/* Code For ETarefa Goes Here */
			afericoes = new ArrayList<Afericao>(); 

			p.acao_.accept(new AcaoVisitor<R, A>(), arg);
			
			return null;
		}

	}

	public class AcaoVisitor<R, A> implements Acao.Visitor<R, A> {
		public R visit(gramatica.Monitor.Absyn.EAcao p, A arg) {
			/* Code For EAcao Goes Here */

			Afericao afericao = new Afericao();

			afericao = p.dados_.accept(new DadosVisitor<R, A>(), arg);
			int[] hora = p.hora_.accept(new HoraVisitor<R, A>(), arg);
			
			afericao.setHora1(hora[0]);
			afericao.setHora2(hora[1]);
			afericoes.add(afericao);
			

			return null;
		}

		public R visit(gramatica.Monitor.Absyn.EAcao2 p, A arg) {
			/* Code For EAcao2 Goes Here */
			p.acao_1.accept(new AcaoVisitor<R, A>(), arg);
			p.acao_2.accept(new AcaoVisitor<R, A>(), arg);

			return null;
		}

	}

	public class DadosVisitor<R, A> implements Dados.Visitor<Afericao, A> {
		public Afericao visit(gramatica.Monitor.Absyn.EDados p, A arg) {
			/* Code For EDados Goes Here */
			Afericao afericao = new Afericao();

			Integer i = p.quantidade_.accept(new QuantidadeVisitor<R, A>(), arg);

			afericao.setDado(p);
			afericao.setQuantidade1(i);

			return afericao;
		}

		public Afericao visit(gramatica.Monitor.Absyn.EDados1 p, A arg) {
			/* Code For EDados1 Goes Here */
			Afericao afericao = new Afericao();

			Integer i = p.quantidade_
					.accept(new QuantidadeVisitor<R, A>(), arg);
			
			afericao.setDado(p);
			afericao.setQuantidade1(i);

			return afericao;
		}

		public Afericao visit(gramatica.Monitor.Absyn.EDados2 p, A arg) {
			/* Code For EDados2 Goes Here */
			Afericao afericao = new Afericao();

			Integer i = p.quantidade_
					.accept(new QuantidadeVisitor<R, A>(), arg);
			
			afericao.setDado(p);
			afericao.setQuantidade1(i);

			return afericao;
		}

		public Afericao visit(gramatica.Monitor.Absyn.EDados3 p, A arg) {
			/* Code For EDados3 Goes Here */
			Afericao afericao = new Afericao();

			Integer i = p.quantidade_1.accept(new QuantidadeVisitor<R, A>(), arg);
			Integer j = p.quantidade_2.accept(new QuantidadeVisitor<R, A>(), arg);
			
			afericao.setDado(p);
			afericao.setQuantidade1(i);
			afericao.setQuantidade2(j);

			return afericao;
		}

	}

	public class QuantidadeVisitor<R, A> implements
			Quantidade.Visitor<Integer, A> {
		public Integer visit(gramatica.Monitor.Absyn.EQuantidade p, A arg) {
			/* Code For EQuantidade Goes Here */

			Integer i = p.integer_;

			return i;
		}

	}

	public class OperadorVisitor<R, A> implements Operador.Visitor<R, A> {
		public R visit(gramatica.Monitor.Absyn.EOperador p, A arg) {
			/* Code For EOperador Goes Here */

			return null;
		}

	}

	public class HoraVisitor<R, A> implements Hora.Visitor<int[], A> {
		public int[] visit(gramatica.Monitor.Absyn.EHora p, A arg) {
			/* Code For EHora Goes Here */
			int[] hora = new int[2];

			hora[0] = p.integer_1;
			hora[1] = p.integer_2;

			return hora;
		}

	}
}
