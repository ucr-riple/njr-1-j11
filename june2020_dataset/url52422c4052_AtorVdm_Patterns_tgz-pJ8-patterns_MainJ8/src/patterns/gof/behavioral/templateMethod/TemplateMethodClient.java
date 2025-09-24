package patterns.gof.behavioral.templateMethod;

import patterns.gof.helpers.Client;


public class TemplateMethodClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		Problem problem = new ProblemTwo(new InitialData() {});
		problem.solveProblem();
		
		super.main("Template Method");
	}
}