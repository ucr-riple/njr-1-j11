package agentes.jade.Atuador;

import gramatica.Atuador.Yylex;
import gramatica.Atuador.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class GrammarParserAtuador {
	public static TarefaAtuador getAtuadorMessageObject(String message) throws Exception{
		TarefaAtuador tarefaObject = null;
		
		InputStream is = new ByteArrayInputStream(message.getBytes());

		Yylex linguagem = new Yylex(is);

		parser p = new parser(linguagem);
		try {
			VisitanteAtuador vis = new VisitanteAtuador();
			p.pTarefa().accept(vis.new TarefaVisitor<>(), null);
			tarefaObject = vis.getTarefa();
		} catch (Error e) {
			throw new Exception("String '" + message + "' nao pertence a linguagem. Erro: " + e.getMessage());
		}	
		
		return tarefaObject;
	}
}
