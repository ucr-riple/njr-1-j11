package test;

public class Word {
	private String name;
	private String mark;//0-sure or 1-notsure 
	private String words[];
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getMark(){
		return this.mark;
	}
	public void setMark(String str){
		this.mark=str;
	}
	public String[] getWords(){
		return this.words;
	}
	public void setWords(String str[]){
		this.words=str;
	}
	public static void main(String[] args) {
	}
}
