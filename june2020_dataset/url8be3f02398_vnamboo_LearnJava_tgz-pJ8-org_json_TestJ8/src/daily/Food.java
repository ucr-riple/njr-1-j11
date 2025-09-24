package daily;

public class Food {
	private int saltAmount;
	private int meatAmount;
	
	public Food(int saltAmount,int meatAmount){
		this.saltAmount=saltAmount;
		this.meatAmount=meatAmount;
	}

	public int getSaltAmount() {
		return saltAmount;
	}

	public int getMeatAmount() {
		return meatAmount;
	}
	
}
