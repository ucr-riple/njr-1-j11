package agentes.jade.Atuador;

import java.util.ArrayList;
import java.util.List;

public class ExemploParserGramaticaAtuador {
	public static void main(String args[]) {
		List<String> mensagens = new ArrayList<String>();
		
		// todas mensagens corretas - Simulacao de envio do atuador
		mensagens.add("Liberando Dipirona");
		mensagens.add("Liberando Paracetamol");
		mensagens.add("Cessando Liberacao de Dipirona");
		mensagens.add("Cessando Liberacao de Paracetamol");
		
		for (String s : mensagens) {
			System.out.println("mensagem:\n" + s);
			try { // simula o monitor recebendo e validando a mensagem
				TarefaAtuador ta = GrammarParserAtuador.getAtuadorMessageObject(s);
				System.out.println(ta.prettyPrinterAction());
				System.out.println("\n-------------------");	
			} catch (Exception e){
				System.out.println(e.getMessage());
			}						
		}
	}
}
