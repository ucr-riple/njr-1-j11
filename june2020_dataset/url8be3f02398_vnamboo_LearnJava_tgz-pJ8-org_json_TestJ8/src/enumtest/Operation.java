package enumtest;

public enum Operation {

	ADD("+"){
		public double apply(double val1,double val2){
			return val1+val2;
		}
	}, 
	SUBTRACT("-"){
		public double apply(double val1,double val2){
			return val1-val2;
		}
	},
	DIVIDE("/"){
		public double apply(double val1,double val2){
			return val1/val2;
		}
	},
	MULTIPLY("*"){
		public double apply(double val1,double val2){
			return val1*val2;
		}
	};
	private final String symbol;
	
	private Operation(String symbol) {
		this.symbol = symbol;
	}

	public String toString(){
		return symbol;
	}
	
	public abstract double apply(double val1,double val2);

}
