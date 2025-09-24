package agentes.jade.Centralizador;



import gramatica.Centralizador.Yylex;
import gramatica.Centralizador.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;




public class GrammarParserCentralizador {
	
	public static TarefaCentralizador getCentralizadorMessageObject(String message) throws Exception{
		TarefaCentralizador tarefaObject = null;
		
		InputStream is = new ByteArrayInputStream(message.getBytes());

		Yylex linguagem = new Yylex(is);

		parser p = new parser(linguagem);
		try {
			VisitanteCentralizador vis = new VisitanteCentralizador();
			p.pTarefa().accept(vis.new TarefaVisitor<>(), null);
			tarefaObject = vis.getTarefa();
		} catch (Error e) {
			throw new Exception("String '" + message + "' nao pertence a linguagem. Erro: " + e.getMessage());
		}	
		
		return tarefaObject;
	}

}
