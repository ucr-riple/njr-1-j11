package util;


import gramatica.Centralizador.PrettyPrinter;
import gramatica.Centralizador.Yylex;
import gramatica.Centralizador.parser;
import gramatica.Centralizador.Absyn.Tarefa;

import java.io.ByteArrayInputStream;
import java.io.InputStream;



public class ParseGrammar {

	public static boolean validated;

	public static boolean validateSentence(String sentence) {
		
		validated = true;

		// convert String into InputStream
		InputStream is = new ByteArrayInputStream(sentence.getBytes());

		Yylex language = new Yylex(is);

		parser p = new parser(language);

		Tarefa parse_tree = null;
		try {
			parse_tree = p.pTarefa();

		} catch (Exception e) {
	
			System.out.println("String '" + sentence + "' nao pertence a linguagem.");
//			e.printStackTrace();
			validated = false;
		}
		System.out.println(PrettyPrinter.print(parse_tree));
		
		return validated;

	}
}
