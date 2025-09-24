package effective.java.item2.builder;

public class NutritionalFacts {
	final int servingSize;
	final int servigs;
	final int calories;
	final int fat;
	final int sodium;
	final int carbohydrate;

	public static class Builder {
		// Mandatory arguments
		private int servigsSize;
		private int servigs;
		// Optional arguments
		int calories=0;
		int fat=0;
		int sodium=0;
		int carbohydrate=0;

		public Builder(int servingsSize, int servings) {
			this.servigsSize = servingsSize;
			this.servigs = servigs;
		}
		
		public Builder fat(int val){
			this.fat=val;
			return this;
		}
		
		public Builder sodium(int val){
			this.sodium=val;
			return this;
		}
		public Builder carbohydrate(int val){
			this.carbohydrate=val;
			return this;
		}
		public Builder calories(int val){
			this.calories=val;
			return this;
		}
		public NutritionalFacts build(){
			return new NutritionalFacts(this);
		}
	}
	
	private NutritionalFacts(Builder builder){
		servingSize=builder.servigsSize;
		servigs=builder.servigs;
		calories=builder.calories;
		fat=builder.fat;
		sodium=builder.sodium;
		carbohydrate=builder.carbohydrate;
	}
	
	public static void main(String[] args) {
		NutritionalFacts cola=new NutritionalFacts.Builder(200,10).calories(10).build();
	}
}
