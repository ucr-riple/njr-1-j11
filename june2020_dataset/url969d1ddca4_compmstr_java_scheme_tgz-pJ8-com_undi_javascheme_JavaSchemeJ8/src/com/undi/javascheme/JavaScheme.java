package com.undi.javascheme;

//import java.io.ByteArrayInputStream;
//import java.io.InputStream;

public class JavaScheme {

  /**
   * @param args
   */
  public static void main(String[] args) {
    //SchemeObject obj = SchemeObject.makeString("Hello");
    //System.out.println(obj.getString());
    //System.out.println(obj.getCharacter());
    
    //InputStream in = new ByteArrayInputStream("   ;this is a comment\nfoo".getBytes());
    //reader.eatWhitespace();
    //System.out.println(reader.readToken());
    //System.out.println(Character.isWhitespace('\n'));
    System.out.println("Welcome to java-scheme, press Ctrl-C to exit");
    SchemeReader reader = new SchemeReader(System.in);
    SchemeEval evaluator = new SchemeEval();
    
    /*System.out.println("Empty list: " + SchemeObject.EmptyList.hashCode());
    System.out.println("OK Symbol: " + SchemeObject.OkSymbol.hashCode());
    System.out.println("Lambda Symbol: " + SchemeObject.LambdaSymbol.hashCode());
    SchemeObject num1 = SchemeObject.makeNumber(2);
    SchemeObject num2 = SchemeObject.makeNumber(2);
    System.out.println("2: " + SchemeObject.makeNumber(2).hashCode());
    System.out.println("2 (redux): " + SchemeObject.makeNumber(2).hashCode());
    System.out.println("num1.equals(num2): " + (num1.equals(num2)));*/
    //SchemeReader stringReader = SchemeReader.fromString("(define (double x) (* x 2))");
    //System.out.println(evaluator.eval(stringReader.read(), evaluator.getGlobalEnv()));
    //REPL loop
    while(true){
      System.out.print("> ");
			try{
				System.out.println(evaluator.eval(reader.read(), evaluator.getGlobalEnv()));
			}catch(SchemeException e){
					System.out.println("Scheme Error: " + e.getMessage());
					//e.printStackTrace();
					reader.clearInput();
			}
    }

  }

}
