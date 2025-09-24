package polynomial;

import java.util.Map;
import java.util.TreeMap;

public class SimplePoly {
	

		

			private Map<SimplePolyTerm, Integer> terms = new TreeMap<SimplePolyTerm, Integer>();
	
		  public SimplePoly() {}

		  public  SimplePoly(XYTerm t) {
		    if(t.xpower > 0 || t.ypower > 0) { terms.put(new SimplePolyTerm(t.xpower,t.ypower),1); }
		  }

		  public  SimplePoly(int n, int x, int y) {
		    if(x > 0 || y > 0) { terms.put(new SimplePolyTerm(x,y),n); }
		  }

		  public   SimplePoly(SimplePoly t) {
		    terms = new TreeMap<SimplePolyTerm, Integer>(t.terms);
		  }

		  public  SimplePoly equals(SimplePoly src) {
		    if(src != this) { 
		      terms = new TreeMap<SimplePolyTerm, Integer>(src.terms);
		    }
		    return this;
		  }

		  public   void insert(int n, XYTerm t) {
		    terms.put(new SimplePolyTerm(t.xpower,t.ypower),n); 
		  }

		  public   int nterms() { return terms.size(); }

		  public   Iterable<Map.Entry<SimplePolyTerm, Integer>> begin() { return terms.entrySet(); }

		  public   boolean equals(Object o )  {
			  if(o instanceof SimplePoly){
		    return terms == ((SimplePoly)o).terms;
			  }return false;
		  }

		  public 	  boolean notEqual(SimplePoly g) {
		    return terms != g.terms;
		  }

		  public 	  void add(SimplePoly p1) {    
		    for(Map.Entry<SimplePolyTerm, Integer> i : p1.terms.entrySet()){      
		      Integer j = terms.get(i.getKey());
		      if(j != null) {
			terms.put(i.getKey(), j += i.getValue());
		      } else {
			terms.put(i.getKey(),i.getValue());
		      }
		    }
		  }

		  // this is gary's shift operation
		  public   void times(XYTerm p2) {
		    if(p2.ypowerend == p2.ypower) {
		      // I don't think the STL strictly would allow this,
		      // but it doesn't hurt!      
		      for(Map.Entry<SimplePolyTerm, Integer> i :terms.entrySet()){      
		    	  SimplePolyTerm t = i.getKey();
			t.xpower += p2.xpower;
			t.ypower += p2.ypower;
		      }
		    } else {
		      // recursive case (this is an ugly hack)
		      SimplePoly p = new SimplePoly(this);

		      for(int i=p2.ypower;i!=p2.ypowerend-1;++i) {
			this.add(p);
			p.times(new XYTerm(0,1));
		      }
		      
		      this.add(p);
		    }
		  }

		  public 	 String toString()  {
		    StringBuilder ss = new StringBuilder();
		    // start with xs
		    boolean firstTime=true;
		    for(Map.Entry<SimplePolyTerm, Integer> i: terms.entrySet()) {      
		      if(!firstTime) { ss= ss .append( " + "); }
		      firstTime=false;
		      int count = i.getValue();
		      if(count > 1) { ss = ss .append( count); }
		      ss= ss.append( i.getKey().toString());
		    }
		    
		    return ss.toString(); 
		  }

		  public   double substitute(double x, double y)  {
		    double val=0;
		    for(Map.Entry<SimplePolyTerm, Integer> i : terms.entrySet()){   
		      val += i.getKey().substitute(x,y) * i.getValue();
		    }
		    return val;
		  }

}
