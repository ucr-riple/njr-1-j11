package patterns.gof.behavioral.templateMethod;

public class ProblemOne extends Problem {
	public ProblemOne(InitialData data) {
		super(data);
	}

	@Override
	protected boolean checkSolvability() {
		TemplateMethodClient.addOutput("Problem is solvable!(One)");
		return true;
	}

	@Override
	protected void solve() {
		TemplateMethodClient.addOutput("Problem solved!(One)");
	}

	@Override
	protected Answer getAnswer() {
		return new Answer() {};
	}
}