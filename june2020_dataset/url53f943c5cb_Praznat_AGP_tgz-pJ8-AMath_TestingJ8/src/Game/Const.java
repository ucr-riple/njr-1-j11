package Game;

import Defs.*;
import Sentiens.Clan;

public class Const implements Misc {
	private static int[] defaultT = {11,0,1,6,8,10};
	private static int[] mod = {8,4,2,1};
	private static int Ssize = 2;
	
	private int[] T = new int[6];
	private int[][] MX = new int[11][9];
	private int count;
	
	public Const(Clan doer) {
		load(doer);
	}
	private void load(Clan doer) {
		T[5] = doer.useBeh(M_.CONST6);
		T[4] = T[5] * doer.useBeh(M_.CONST5) / 15;
		T[3] = T[4] * doer.useBeh(M_.CONST4) / 15;
		T[2] = T[3] * doer.useBeh(M_.CONST3) / 15;
		T[1] = T[2] * doer.useBeh(M_.CONST2) / 15;
		T[0] = doer.useBeh(M_.CONST1); //center
		makeMatrix(doer);
	}
	private int[][] makeMatrix(Clan doer) {
		load(doer);
		count = 0;
		//T = defaultT;
		for(int c = 1; c < 5; c++) { //row 0
			MX[0][c] = C((T[1] / mod[c-1]) % 2);
		}   MX[0][4] += S(MX[0][3]);
		for(int r = 2; r < 10; r+= 2) {
			for(int c = 0; c < 4; c++) {
				MX[r][c] = C((T[r/2+1] / mod[c]) % 2);
			}
		}
		for(int i = 1; i < 10; i++) {
			if(i % 2 == 1) {
				if(over(i)) {MX[i][4] = S();}
			}
			else {if(over(i) || (T[0]/mod[i/2-1])%2 == 1 || MX[i][3] == 1) {MX[i][4] = S();}}
		}
		for(int c = 0; c < 5; c++) {MX[10][c] = 1;}
		for(int r = 1; r < 11; r+= 2) {
			for(int c = 3; c >= 0; c--) {
				if ((c==0 && r != 9) || r == 5) {MX[r][c] = (combo(r,c)||sonly(r,c) ? C() : 0);}
				else {MX[r][c] = (combo(r,c)||bonly(r,c) ? C() : 0);}
			}
		}
		for(int r = 0; r < 11; r++) {
			for(int c = 5; c < 9; c++) {
				MX[r][c] = C(MX[r][8 - c]);
			}
		}
		return MX;
	}
	
	private int C() {count++; return 1;}
	private int S() {count+=Ssize; return 1;}
	private int C(int x) {count+=x; return x;}
	private int S(int x) {count+=x*Ssize; return x;}
	
	
	private boolean over(int i) {
		int k = 0; for(k=i;k>=0; k--){if (MX[k][4] != 0) {return true;}}
		return false;
	}
	private boolean combo(int r, int c) {
		return (vert(r,c) || rndr(r,c) || allupleft(r,c));
	}
	private boolean vert(int r, int c) {
		return (MX[r-1][c] * MX[r+1][c] == 1);
	}
	private boolean rndr(int r, int c) {
		return (MX[r][c+1] == 1 && MX[r+1][c+1] == 0);
	}
	private boolean sonly(int r, int c) {
		return (MX[r-1][c] * (1-MX[r-1][c+1]) * (1-MX[r-1][Math.max(c-1,0)]) == 1);
	}
	private boolean bonly(int r, int c) {
		return (MX[r-1][c] == 1 && (MX[r-1][c+1] == 0 || c == 3));
	}
	private boolean allupleft(int r, int c) {
		return (upleft(r,c) && !downleft(r,c) && (MX[r-1][c] == 0));
	}
	private boolean upleft(int r, int c) {
		int k = 0; for(k=0;k<c; k++){if (MX[r-1][k] != 0) {return true;}}
		return false;
	}
	private boolean downleft(int r, int c) {
		int k = 0; for(k=0;k<c; k++){if (MX[r+1][k] != 0) {return true;}}
		return false;
	}
	
}
