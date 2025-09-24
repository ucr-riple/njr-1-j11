package algorithm.string;

public class LCS {

	private String strX;
	private String strY;
	private int[][] table; 
	
	public LCS(String strx, String stry){
		this.strX = strx;
		this.strY = stry;
		this.table = new int[this.strX.length()+1][this.strY.length()+1];
	}
	
	public void computeLCSTable(){
		for (int i = 0; i <= this.strX.length(); i++) {
			this.table[i][0] = 0;
		}
		
		for (int j = 0; j <= this.strY.length(); ++j){
			this.table[0][j] = 0;
		}
		
		for (int i = 1; i <= this.strX.length(); ++i){
			for(int j = 1; j <= this.strY.length(); ++j){
				if(this.strX.charAt(i-1) == this.strY.charAt(j-1)){
					this.table[i][j] = this.table[i-1][j-1] + 1;
				}
				else{
					if(this.table[i-1][j] > this.table[i][j-1])
						this.table[i][j] = this.table[i-1][j];
					else {
						this.table[i][j] = this.table[i][j-1];
					}
				}
			}
		}
		return;
	}
	
	public String findLCS(String lcs, int i, int j){
		if(this.table[i][j] == 0)
			return "";
		else{
			if(this.strX.charAt(i-1) == this.strY.charAt(j-1)){
				
				lcs += findLCS(lcs, i-1, j-1);
				lcs += this.strX.charAt(i-1);
			}
			else{
				if(this.table[i][j-1] > this.table[i-1][j]){
					lcs += findLCS(lcs, i, j-1);
				}
				else{
					lcs += findLCS(lcs, i-1, j);
				}
			}
			return lcs;
		}
	}
}
