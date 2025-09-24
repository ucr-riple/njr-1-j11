package patterns.gof.creational.builder;

public abstract class PieBuilder {
	private Pie pie;
	
	private boolean baked;
	
	protected abstract void buildDough();
	protected abstract void buildFilling();
	
	
	public void createNewPie() {
		pie = new Pie();
		baked = false;
	}
	
	public void bake() {
		if(pie.getDough().isEmpty()) {
			BuilderClient.addOutput("error - no dough found!");
			return;
		}
		if (pie.getFilling().isEmpty()) {
			BuilderClient.addOutput("error - no filling found!");
			return;
		}
		baked = true;
		BuilderClient.addOutput("*oven sound*");
	}

	public Pie getPie() {
		return pie;
	}

	public void setPie(Pie pie) {
		this.pie = pie;
	}
	
	public boolean isBaked() {
		return baked;
	}
}