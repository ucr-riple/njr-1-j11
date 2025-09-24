package regexpToNFA;

/**
 * 语法中的一些关键字
 * @author Administrator
 *
 */
public class Syntax {
	/**
	 * 闭包
	 */
	public static char closure='*';
	/**
	 * 正闭包
	 */
	public static char positiveClosure='+';
	/**
	 * 或
	 */
	public static char or='|';
	public static char transMean='\\';
	public static char[] keyChar=new char[]{'*','+','(',')','|','(',')','?'};  //关键字
	public static char includeLeft='[';
	public static char includeRight=']';
	
	public static char lastChar;
	public static char nextChar;
	public static String noPlace[]=new String[]{"\\b","\\c"};   //不占位转移符号  \\b单词开始  \\d单词结束
	public static char keyCharForS[]=new char[]{'-'};  //中括号里面的关键字
	
	public static boolean isNoPlace(String tran){
		for(String str:noPlace){
			if(tran.equals(str)){
				return true;
			}
		}
		return false;
	}
	public static boolean isIncludeLeft(char ch){
		return includeLeft==ch;
	}
	public static boolean isIncludeRight(char ch){
		return includeRight==ch;
	}
	/**
	 * 判断是否是 '*','+','(',')','|','(',')' 中的一个
	 * @param str
	 * @return
	 */
	public static boolean isKeyWord(char str){
		for(int i=0;i<keyChar.length;i++){
			if(keyChar[i]==str){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是否是转义字符\
	 * @param str
	 * @return
	 */
	public static boolean isThransMean(char str){
		if(transMean==str){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否匹配
	 * @param tran 转换字符 可以是char或者是\w关键字
	 * @param in 输入的char
	 * @return
	 */
	public static boolean isMatch(String tran,char in){
		if(tran.startsWith("\\")){
			char ch[]=tran.toCharArray();
			if(isKeyWord(ch[1]) || ch[1]=='\\' || ch[1]=='.'){
				return in==ch[1];
			} else {
				if(tran.equals("\\d")){
					return isMatchScope('0','9',in);
				} else if(tran.equals("\\D")){
					return !isMatchScope('0','9',in);
				} else if(tran.equals("\\w")){
					return isMatchScope('0','9',in) || isMatchScope('a','z',in) || 
						isMatchScope('A','Z',in) || in=='_';
				} else if(tran.equals("\\W")){
					return !(isMatchScope('0','9',in) || isMatchScope('a','z',in) || 
					isMatchScope('A','Z',in) || in=='_');
				} else if(tran.equals("\\b")){
					if(lastChar==0){
						return true;
					}
					return !(isMatchScope('0','9',lastChar) || isMatchScope('a','z',lastChar) || 
							isMatchScope('A','Z',lastChar) || lastChar=='_');
				}
				else if(tran.equals("\\c")){
					return !(isMatchScope('0','9',in) || isMatchScope('a','z',in) || 
							isMatchScope('A','Z',in) || in=='_');
				}
				return false;
			}
		} else if(tran.equals(".")){
			return true;
		} else if(tran.startsWith("[")){
			tran=tran.substring(1, tran.length()-1);
			if(tran.startsWith("^")){
				
			} else {
				
			}
			return true;
		} else {
			return tran.toCharArray()[0]==in;
		}
	}
	public static void setLastNext(char last,char next){
		lastChar = last;
		nextChar = next;
	}
	public static boolean isMatchScope(char cha[],char ch){
		for(char c:cha){
			if(c==ch){
				return true;
			}
		}
		return false;
	}
	public static boolean isMatchScope(char start,char end,char ch){
		if(ch>=start && ch<=end){
			return true;
		}
		return false;
	}
}
