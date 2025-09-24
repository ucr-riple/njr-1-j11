package patterns.gof.structural.facade;

public class CPU {
	public void freeze() {
		FacadeClient.addOutput("freezing CPU");
	}
	
    public void jump(long position) {
    	FacadeClient.addOutput("jumping on " + position + " position in CPU");
    }
    
    public void execute() {
    	FacadeClient.addOutput("executing CPU");
    }
}
