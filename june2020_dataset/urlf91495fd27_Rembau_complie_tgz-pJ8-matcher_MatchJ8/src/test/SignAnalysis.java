package test;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SignAnalysis {
	/**
	 * 去掉注释/**\/
	 */
	public String removeNote(String str){
		Pattern p=Pattern.compile("/\\*.*\\*/",Pattern.CASE_INSENSITIVE);
		Matcher m=p.matcher(str);
		while(m.find()){
			str=str.replace(m.group(), "");
		}
		p=Pattern.compile("//.*$",Pattern.CASE_INSENSITIVE);
		m=p.matcher(str);
		while(m.find()){
			str=str.replace(m.group(), "");
		}
		return str;
	}
	public void search(){
		
	}
	public LinkedList<SonContent> searchSon(String str,String x,int i){
		LinkedList<SonContent> sonContentList =new LinkedList<SonContent>();
		Pattern p=Pattern.compile(x,Pattern.CASE_INSENSITIVE);
		Matcher m=p.matcher(str);
		while(m.find()){
			SonContent son=new SonContent();
			son.setLevel(i+1);
			son.setStr(m.group());
			System.out.println(m.group());
			sonContentList.add(son);
		}
		return sonContentList;
	}
	public static void main(String[] args) {
		new SignAnalysis().searchSon("ifw(sum==1) ", "\\bif\\b", 0);
	}
}
class SonContent{
	String str;
	int level;
	public String getStr(){
		return str;
	}
	public void setStr(String str){
		this.str=str;
	}
	public int getLevel(){
		return level;
	}
	public void setLevel(int i){
		this.level=i;
	}
}
