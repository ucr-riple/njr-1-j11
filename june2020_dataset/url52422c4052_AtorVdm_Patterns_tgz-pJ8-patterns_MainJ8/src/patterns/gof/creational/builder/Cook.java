package patterns.gof.creational.builder;

public class Cook {
	private PieBuilder pb;
	
	private boolean constructed = false;
	
	public void constructPieWithPieBuilder(PieBuilder pb) {
		this.pb = pb;
		constructed = false;
		if (this.pb != null) {
			this.pb.createNewPie();
			this.pb.buildDough();
			this.pb.buildFilling();
			constructed = true;
		} else {
			BuilderClient.addOutput("error - no pie builder found!");
		}
	}
	
	public void bakePie() {
		if (constructed) {
			pb.bake();
		} else {
			BuilderClient.addOutput("error - your pie is not constructed yet!"); 
		}
	}
	
	public Pie getPie() {
		if (pb.isBaked()) {
			return pb.getPie();
		} else {
			BuilderClient.addOutput("error - your pie is not ready yet");
			return null;
		}
	}
}