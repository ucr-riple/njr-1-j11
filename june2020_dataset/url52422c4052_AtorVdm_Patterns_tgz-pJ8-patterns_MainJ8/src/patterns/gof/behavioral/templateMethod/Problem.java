package patterns.gof.behavioral.templateMethod;

public abstract class Problem {
	private InitialData initialData;
	
	public Problem(InitialData data) {
		initialData = data;
	}
	
	protected abstract boolean checkSolvability();
	protected abstract void solve();
	protected abstract Answer getAnswer();
	
	public InitialData getInitialData() {
		return initialData;
	}
	
	public void solveProblem() {
		if (checkSolvability()) {
			solve();
			TemplateMethodClient.addOutput("answer [" + getAnswer().getClass().getName() + "]");
		} else {
			TemplateMethodClient.addOutput("problem is not solvable");
		}
	}
}