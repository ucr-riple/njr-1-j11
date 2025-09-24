package util;

public class Triple <A,B,C> {
	public A first;
	public B second;
	public C third;
	
	public Triple(A a, B b, C c){
		this.first = a;
		this.second = b;
		this.third = c;
	}
	
	public Triple(Triple<A,B,C> t){
		this.first = t.first;
		this.second = t.second;
		this.third = t.third;
	}
	
	public boolean equals(Object o){
		if(o instanceof Triple){
			Triple t = (Triple) o;
			return first.equals(t.first) && second.equals(t.second) && third.equals(t.third);
		}
		return false;
	}
	
	public String toString(){
		return "("+ this.first + ", " + this.second + ", " + this.third + ")";
	}
}
