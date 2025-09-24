package agentes.jade.Atuador;

import gramatica.Atuador.Absyn.Acao;
import gramatica.Atuador.Absyn.Remedio;
import gramatica.Atuador.Absyn.Tarefa;

public class VisitanteAtuador {
	private TarefaAtuador tf;

	public TarefaAtuador getTarefa() {
		return tf;
	}

	public class TarefaVisitor<R, A> implements Tarefa.Visitor<R, A> {
		public R visit(gramatica.Atuador.Absyn.ETarefa p, A arg) {
			/* Code For ETarefa Goes Here */
			tf = new TarefaAtuador();

			p.acao_.accept(new AcaoVisitor<R, A>(), arg);

			return null;
		}

	}

	public class AcaoVisitor<R, A> implements Acao.Visitor<R, A> {
		public R visit(gramatica.Atuador.Absyn.EAcao p, A arg) {
			/* Code For EAcao Goes Here */
			p.remedio_.accept(new RemedioVisitor<R, A>(), arg);

			tf.setAcao(p);

			return null;
		}

		public R visit(gramatica.Atuador.Absyn.EAcao1 p, A arg) {
			/* Code For EAcao1 Goes Here */

			p.remedio_.accept(new RemedioVisitor<R, A>(), arg);

			tf.setAcao(p);

			return null;
		}

	}

	public class RemedioVisitor<R, A> implements Remedio.Visitor<R, A> {
		public R visit(gramatica.Atuador.Absyn.ERemedio p, A arg) {
			/* Code For ERemedio Goes Here */
			tf.setRemedio(p);

			return null;
		}

		public R visit(gramatica.Atuador.Absyn.ERemedio1 p, A arg) {
			/* Code For ERemedio1 Goes Here */
			tf.setRemedio(p);

			return null;
		}

	}
}
