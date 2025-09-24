package patterns.gof.creational.singleton;

public class SingletonClient extends patterns.gof.helpers.Client {
	@Override
	public void main() {
		cleanOutput();
		
		Singleton sin = Singleton.getInstance();
		
	    sin.setData("1");
	    addOutput(sin.getData());
	    
	    dataChanger1();
	    addOutput(sin.getData());
	    
	    dataChanger2();
	    addOutput(sin.getData());
	    
	    super.main("Singleton");
	}

	private void dataChanger1() {
	    Singleton sin = Singleton.getInstance();
	    sin.setData(sin.getData() + "2");
	}

	private void dataChanger2() {
		Singleton sin = Singleton.getInstance();
	    sin.setData(sin.getData() + "3");
	}
}