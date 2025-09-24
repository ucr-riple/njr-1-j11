package learn.dp;

public class ConcreteTemplate extends AbstractTemplate {

	@Override
	protected void doMethod() {
		System.out.println("performing do method");

	}

	public static void main(String[] args) {
		new ConcreteTemplate().doTask();
		synchronized (ConcreteTemplate.class) {
			
			
		}
	}
}
