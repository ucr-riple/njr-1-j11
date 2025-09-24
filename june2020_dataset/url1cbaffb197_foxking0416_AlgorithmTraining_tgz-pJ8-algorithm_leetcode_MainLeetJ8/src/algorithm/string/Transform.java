package algorithm.string;


public class Transform {
	private String strX;
	private String strY;
	private int[][] tableCost;
	private int[][] tableOperation;
	
	private int deleteCost = 2;
	private int insertCost = 2;
	private int copyCost = -1;
	private int replaceCost = 1;
	
	
	public Transform(String strx, String stry){
		this.strX = strx;
		this.strY = stry;
		this.tableCost = new int[this.strX.length()+1][this.strY.length()+1];
		this.tableOperation = new int[this.strX.length()+1][this.strY.length()+1];
	}
	
	public int computeTransformTable(){
		this.tableCost[0][0] = 0;
		this.tableOperation[0][0] = -1;

		for(int i = 1; i <= this.strX.length(); ++i){
			this.tableCost[i][0] = this.tableCost[i-1][0] + deleteCost;
			this.tableOperation[i][0] = Operation.delete.ordinal();
		}
		
		for(int j = 1; j <= this.strY.length(); ++j){
			this.tableCost[0][j] = this.tableCost[0][j-1] + insertCost;
			this.tableOperation[0][j] = Operation.insert.ordinal();	
		}
		
		for(int i = 1; i <= this.strX.length(); ++i){
			for(int j = 1; j <= this.strY.length(); ++j){
				
				if(this.strX.charAt(i-1) == this.strY.charAt(j-1)){
					this.tableCost[i][j] = this.tableCost[i-1][j-1] + copyCost;
					this.tableOperation[i][j] = Operation.copy.ordinal();
				}
				else{
					this.tableCost[i][j] = this.tableCost[i-1][j-1] + replaceCost;
					this.tableOperation[i][j] = Operation.replace.ordinal();
					if(this.tableCost[i-1][j] + deleteCost < this.tableCost[i][j]){
						this.tableCost[i][j] = this.tableCost[i-1][j] + deleteCost;
						this.tableOperation[i][j] = Operation.delete.ordinal();
					}
					else if(this.tableCost[i][j-1] + insertCost < this.tableCost[i][j]){
						this.tableCost[i][j] = this.tableCost[i][j-1] +insertCost;
						this.tableOperation[i][j] = Operation.insert.ordinal();
					}	
				}
			}
		}
		return this.tableCost[this.strX.length()][this.strY.length()];
	}
	
	public String assembleTransformation(String str, int i, int j){
		if(i == 0 && j == 0)
			return "";
		else{
			switch (this.tableOperation[i][j]) {
			case 0://delete
				str += assembleTransformation(str, i-1, j);
				
				break;
			case 1://insert
				str += assembleTransformation(str, i, j-1);
				str += this.strY.charAt(j-1);
				break;
			case 2://replace
				str += assembleTransformation(str, i-1, j-1);
				str += this.strY.charAt(j-1);
				break;
			case 3://copy
				str += assembleTransformation(str, i-1, j-1);
				str += this.strY.charAt(j-1);
				break;
			default:
				break;
			}
		}
		
		return str;
	}
}
