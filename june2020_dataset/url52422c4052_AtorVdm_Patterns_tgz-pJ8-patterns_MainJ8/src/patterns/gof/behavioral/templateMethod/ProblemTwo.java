package patterns.gof.behavioral.templateMethod;

public class ProblemTwo extends Problem {
	public ProblemTwo(InitialData data) {
		super(data);
	}

	@Override
	protected boolean checkSolvability() {
		TemplateMethodClient.addOutput("Problem is solvable!(Two)");
		return true;
	}

	@Override
	protected void solve() {
		TemplateMethodClient.addOutput("Problem solved!(Two)");
	}

	@Override
	protected Answer getAnswer() {
		return new Answer() {};
	}
}