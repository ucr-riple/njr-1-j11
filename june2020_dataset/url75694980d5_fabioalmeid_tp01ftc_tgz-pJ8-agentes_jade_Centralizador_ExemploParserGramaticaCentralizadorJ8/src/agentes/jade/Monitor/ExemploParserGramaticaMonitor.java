package agentes.jade.Monitor;

import java.util.ArrayList;
import java.util.List;


public class ExemploParserGramaticaMonitor {
	
	public static void main(String args[]) {
		String mens = "Temperatura de 37 C as 05 h: 30 m";
		
		// exemplo de como realizar get nos dados
		TarefaMonitor tm = new TarefaMonitor();
		try {
			System.out.println("mensagem recebida do monitor:\n" + mens);
			tm = GrammarParserMonitor.getMonitorMessageObject(mens);
			System.out.println(tm.prettyPrinter());
			System.out.println("\n-------------------");
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		
		
		// agora testando com mais mensagens
		List<String> mensagens = new ArrayList<String>();
		mensagens.add("Temperatura de 39 C as 19 h: 59 m");
		mensagens.add("Bilirrubina 1000 g/dL as 16 h: 12 m");
		mensagens.add("Hemoglobina 900 mg/dL as 22 h: 36 m");
		mensagens.add("Pressao Arterial 15 : 9 mmHg as 00 h: 12 m");
		mensagens.add("Bilirrubina 1000 g/dL as 14 h: 01 m e Hemoglobina 900 mg/dL as 14 h: 01 m");
		mensagens.add("Temperatura de 39 C as 19 h: 59 m e Pressao Arterial 15 : 9 mmHg as 19 h: 18 m");
		
		for (String s : mensagens) {
			System.out.println("mensagem recebida do monitor:\n" + s);
			TarefaMonitor t = new TarefaMonitor();
			try {
				t = GrammarParserMonitor.getMonitorMessageObject(s);
				System.out.println(t.prettyPrinter());
				System.out.println("-------------------\n");
			} catch (Exception e){
				System.out.println(e.getMessage());
			}			
		}
		
	}
}
