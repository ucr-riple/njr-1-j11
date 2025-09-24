/**
 * 
 */
package utility;
import java.util.Arrays;
import java.util.Scanner;


/**
 * @author chenqian
 *
 */
public class DirectedMinSpanTree {

	int N, M;// 0,1,...,N-1
	Edge[] edge;
	int[] d, p, v, n, m, o, w;
	int edgeId;
	public int inf = 1 << 30;
	
	public DirectedMinSpanTree(){}
	
	public DirectedMinSpanTree(int _N, int _M){
		N = _N;
		M = _M;
		edge = new Edge[M];
		d = new int[N];
		p = new int[N];
		v = new int[N];
		n = new int[N];
		m = new int[N];
		o = new int[N];
		w = new int[N];
		edgeId = 0;
	}
	
	public void addEdge(int a, int b, int c){
		edge[edgeId] = new Edge(a, b, c);
		edgeId ++;
	}
	
	public int calcDirectedMinSpanTreeWeight(int r){
		o[r] = -1;
		w[r] = 0;
		Arrays.fill(m, 0);
		int w1 = 0, w2 = 0;
		while(true){
			Arrays.fill(d, inf);
			Arrays.fill(p, -1);
			for(int i = 0; i < M; i++){
				int a = edge[i].a;
				int b = edge[i].b;
				int c = edge[i].c;
				if(a != b && b != r && c < d[b]){
					d[b] = c;
					p[b] = a;
					o[edge[i].b2] = edge[i].a2;
					w[edge[i].b2] = edge[i].c2;
				}
			}
			Arrays.fill(v, -1);
			Arrays.fill(n, -1);
			w1 = 0;
			boolean jf = false;
			for(int i = 0; i < N; i++){
				if(m[i] != 0)continue;
				if(p[i] == -1 && i != r)return -1;
				if(p[i] >= 0) w1 += d[i];
				int s;
				for(s = i; s != -1 && v[s] == -1; s = p[s]) v[s] = i;
				if(s != -1 && v[s] == i){
					jf = true;
					int j = s;
					do{
						n[j] = s; m[j] = 1;
						w2 += d[j]; j = p[j];
					}while(j != s);
					m[s] = 0;
				}
			}
			if(!jf)break;
			for(int i = 0; i < M; i ++){
				int a = edge[i].a;
				int b = edge[i].b;
				if(n[b] >= 0)edge[i].c -= d[b];
				if(n[a] >= 0)edge[i].a = n[a];
				if(n[b] >= 0)edge[i].b = n[b];
				if(edge[i].a == edge[i].b)edge[i --] = edge[-- M];
			}
		}
		return w1 + w2;
	}
	
	public void getPlan(){//calcDirectedMinTreeWeight first
		
	}
	
	public void printPlan(){
		for(int i = 0; i < N; i++){
			System.out.println(i + " <- " + o[i] + " : " + w[i]);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//test
		Scanner in = new Scanner(System.in);
		int cas = in.nextInt();
		for(int casid = 0; casid < cas; casid++){
			int N = in.nextInt(), M = in.nextInt();
			DirectedMinSpanTree tree = new DirectedMinSpanTree(N, M);
			for(int i = 0 ; i < M; i++){
				tree.addEdge(in.nextInt(), in.nextInt(), in.nextInt());
			}
			int ans = tree.calcDirectedMinSpanTreeWeight(0);
			if(ans != -1){
				System.out.println("Case #" + (casid + 1) + ": " + ans);				
			}else{
				System.out.println("Case #" + (casid + 1) + ": Possums!");
			}
			tree.printPlan();
		}
	}
}

class Edge{
	public Edge(){}
	public Edge(int _a, int _b, int _c){
		a = _a;
		b = _b;
		c = _c;
		a2 = a;
		b2 = b;
		c2 = c;
	}
	public int a, b, c, a2, b2, c2;
}
