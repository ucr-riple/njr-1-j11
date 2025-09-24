package Ledger;

public class Memo {
	private static final int MAXLEN = 20;
	private static final int END = Integer.MIN_VALUE;
	
	private static int[] Memo = new int[MAXLEN];
	
	public static int[] get() {
		int[] out = new int[end()]; for(int i = 0; i < out.length; i++) {out[i] = Memo[i];}
		return out;
	}
	public static int take(int pos) {return Memo[pos];}
	public static void copy(int[] in) {
		if(in.length>Memo.length) {int z = 1/0; return;}
		int i = 0; for(; i < in.length; i++) {Memo[i] = in[i];} Memo[i] = END;
	}
	public static void add(int val, int pos) {Memo[pos] = val;}
	
	private static int end() {int k = 0; for(;k<MAXLEN;k++) {if (Memo[k] == END) break;} return k;}
}
