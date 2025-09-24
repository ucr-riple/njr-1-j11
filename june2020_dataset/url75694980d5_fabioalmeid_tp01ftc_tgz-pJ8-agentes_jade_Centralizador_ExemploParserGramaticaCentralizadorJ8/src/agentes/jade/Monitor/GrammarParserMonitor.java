package agentes.jade.Monitor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import gramatica.Monitor.Yylex;
import gramatica.Monitor.parser;



public class GrammarParserMonitor {
	public static TarefaMonitor getMonitorMessageObject(String message) throws Exception{
		TarefaMonitor tarefaObject = null;
		
		InputStream is = new ByteArrayInputStream(message.getBytes());

		Yylex linguagem = new Yylex(is);

		parser p = new parser(linguagem);
		try {
			VisitanteMonitor vis = new VisitanteMonitor();
			p.pTarefa().accept(vis.new TarefaVisitor<>(), null);
			tarefaObject = new TarefaMonitor();
			tarefaObject.setAfericoes(vis.getAfericoes());
		} catch (Error e) {
			throw new Exception("String '" + message + "' nao pertence a linguagem. Erro: " + e.getMessage());
		}	
		
		return tarefaObject;
	}
}
