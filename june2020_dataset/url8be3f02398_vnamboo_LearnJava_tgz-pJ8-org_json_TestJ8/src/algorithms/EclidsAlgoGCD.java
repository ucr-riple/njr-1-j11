package algorithms;

public class EclidsAlgoGCD {
	public int euclid(int p, int q){
		System.out.println(String.format("%d,%d",p,q));
		if (q==0) return p;
		int r=p%q;
		return euclid(q,r);
	}
	
	
	public static void main(String[] args) {
		short number;
		System.out.println(new EclidsAlgoGCD().euclid(10,12));
		double doubledarray[][];
		doubledarray=new double[10][10];
		for (int i=0;i<10;i++){
			for (int j=0;j<10;j++){
				doubledarray[i][j]=0.0d;
			}
		}
	}
}
