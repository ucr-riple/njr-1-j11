import java.util.*;

public class Smoothing{
    public int one;  //one to two and three to four
    public int two;
    public int three;
    public int four;
    public boolean reorder;  //do we have to reorder the orientations?
    
    public Smoothing(){ //(a=b) and (x=y)
          
    }
    
    public Smoothing(int a, int b, int x, int y, boolean r){ //(a=b) and (x=y)
        this.one = a;
        this.two = b;
        this.three = x;
        this.four = y;
        reorder = r;   
    }
    
    public int getOne(){ return this.one;}
    public int getTwo(){ return this.two;}
    public int getThree(){ return this.three;}
    public int getFour(){ return this.four;}
    
    /*public ArrayList<SeifertCircle> detectCircles(){
        ArrayList<SeifertCircle> sc = new ArrayList<SeifertCircle>();
        if(a1==a2)
            sc.add(new SeifertCircle(a1));
        if(b1==b2)
            sc.add(new SeifertCircle(b1));
        if(a1==b2 && a2==b1)
            sc.add(new SeifertCircle(a1));
        return sc;
    }*/
    
    public boolean needOrientationReorder(){
        return this.reorder;
    }
    
    public String toString(){
        return one+"->"+two+", "+three+"->"+four+"   "+this.reorder;
    }
}