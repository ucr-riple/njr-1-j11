package patterns.gof.behavioral.interpreter;

public interface Expression {
	String interpret(InterpreterContext ic);
}
