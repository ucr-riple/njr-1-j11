package algorithm.string;

import java.util.ArrayList;

public class Matching {

	private String strText;
	private String strPattern;
	private int nextStateTable[][];
	private ArrayList<Character> array = new ArrayList<Character>();
	
	
	public Matching(String text, String pattern){
		this.strText = text;
		this.strPattern = pattern;
		for(int i = 0; i < this.strText.length(); ++i){
			boolean flag = true;
			for(int j = 0; j < this.array.size(); ++j){
				if(this.strText.charAt(i) == this.array.get(j)){
					flag = false;
					break;
				}
			}
			if(flag == true)
				this.array.add(this.strText.charAt(i));
		}
		
		this.nextStateTable = new int[this.strPattern.length() + 1][this.array.size()];
	}
	
	
	public void createNextStateTable(){
		for(int i = 0; i < this.strPattern.length() + 1; ++i){
			for(int j = 0; j < this.array.size(); ++j){
				int step = 0;
				for(int k = i; k >= 0; --k){
					if(k == this.strPattern.length())
						continue;
					String prefix = this.strPattern.substring(0, k+1);
					String concatenation = this.strPattern.substring(0, i).concat(this.array.get(j).toString());
					
					if(concatenation.endsWith(prefix)){
						step = k+1;
						break;
					}
				}
				this.nextStateTable[i][j] = step;
			}
		}
		return;
	}
	
	
	public void findMatch(){
		
		boolean find = false;
		int state = 0;
		for(int i = 0; i < this.strText.length(); ++i){
			
			char c = this.strText.charAt(i);
			int a;
			for(a = 0; a < this.array.size(); ++a){
				if(c == this.array.get(a)){
					break;
				}				
			}
			
			state = this.nextStateTable[state][a];
			
			if(state == this.strPattern.length()){
				System.out.print("The pattern occurs with shift " + (i+1-this.strPattern.length()) + "\n");
				find = true;
			}
		}
		if(find == false)
			System.out.print("Do not find any match!" + "\n");
	}
}
