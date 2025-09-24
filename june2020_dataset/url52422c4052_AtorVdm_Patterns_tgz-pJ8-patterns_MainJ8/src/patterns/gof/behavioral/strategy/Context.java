package patterns.gof.behavioral.strategy;

public class Context {
	private Strategy strategy;
	
	public Context(Strategy initialStrategy) {
		strategy = initialStrategy;
	}
    
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    
    public int useStrategy(int a, int b) {
        return strategy.execute(a, b);
    }
}