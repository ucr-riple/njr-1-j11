/**
 * 
 */
package Math;

/**
 * @author chenqian
 *
 */
public class MathCircle {

	MathPoint c;
	double r;
	
	public MathCircle(){}
	
	public MathCircle(MathCircle mc){
		this.c = new MathPoint(mc.getCenter());
		this.r = mc.getRadius();
	}
	
	public MathCircle(double x, double y, double r){
		this.c = new MathPoint(x, y);
		this.r = r;
	}
	
	public MathCircle(MathPoint c, double r){
		this.c = new MathPoint(c);
		this.r = r;
	}
	
	public MathPoint getCenter(){
		return c;
	}
	
	public double getRadius(){
		return r;
	}
	
	public double getDiameter(){
		return 2 * r;
	}
	
	/**
	 * Get distance of two circles by centers.
	 * */
	public static double getDistByCenter(MathCircle a, MathCircle b){
		return MathUtility.getDistance(a.getCenter(), b.getCenter());
	}
	
	/**
	 * 1 inside 
	 * 2 inscribe
	 * 3 intersect
	 * 4 circumscribe
	 * 5 outside
	 * */
	public static int getRelation(MathCircle a, MathCircle b){
		double d = getDistByCenter(a, b);
		double dr = Math.abs(a.getRadius() - b.getRadius());
		double sr = a.getRadius() + b.getRadius();
		if(MathUtility.D(d - dr) < 0){
			return 1;
		}
		if(MathUtility.D(d - dr) == 0){
			return 2;
		}
		if(MathUtility.D(d - sr) == 0){
			return 4;
		}
		if(MathUtility.D(d - sr) > 0){
			return 5;
		}
		return 3;
	}
	
	/**
	 * judge a point inside or on the circle
	 * 
	 * */
	public boolean isInside(MathPoint p){
		double d = MathUtility.getDistance(getCenter(), p);
		if(MathUtility.D(d - getRadius()) <= 0)return true;
		else return false;
	}
	
	
	/**
	 * judge a point strictly inside the circle
	 * 
	 * */
	public boolean isInsideStrictly(MathPoint p){
		double d = MathUtility.getDistance(getCenter(), p);
		if(MathUtility.D(d - getRadius()) < 0)return true;
		else return false;
	}
	
	
	/**
	 * get the inscribed circle from intersection of circle A and B. 
	 * have to guarantee A and B are intersected or inside.
	 * 
	 * */
	public static MathCircle getInscribedCircleFromIntersection(MathCircle A, MathCircle B){
		if(getRelation(A, B) <= 2){
			if(MathUtility.D(A.getRadius() - B.getRadius()) < 0)return A;
			else return B;
		}
		if(getRelation(A, B) >= 4){
			return new MathCircle(0, 0, -1);
		}
		double d1 = getDistByCenter(A, B);
		double rn = 0.5 * (A.getRadius() + B.getRadius() - d1);
		return new MathCircle(MathPoint.getScorePoint(A.getCenter(), B.getCenter(), (A.getRadius() - rn) / d1), rn);
	}
	
	/**
	 * get the circumscribed circle from intersection of circle A and B. The new circle is inscribed with B, and circumscribed with A.
	 * have to guarantee A and B are intersected or outside first.
	 * 
	 * */
	public static MathCircle getInscribedCircleInBOutA(MathCircle A, MathCircle B){
		if(getRelation(A, B) <= 2){
			return new MathCircle(0, 0, -1);
		}
		if(getRelation(A, B) >= 4){
			return B;
		}
		double d1 = getDistByCenter(A, B);
		double rn = 0.5 * (d1 - A.getRadius() + B.getRadius());
		return new MathCircle(MathPoint.getScorePoint(A.getCenter(), B.getCenter(), (A.getRadius() + rn) / d1), rn);
	}
	
	public double getArea(){
		return Math.PI * getRadius() * getRadius();
	}
	
	public double getPerimeter(){
		return 2 * Math.PI * getRadius(); 
	}
	
	public void show(){
		System.out.println("circle center at (" + getCenter().getX() + ", " + getCenter().getY() + ") with r = " + getRadius() );
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MathCircle c1 = new MathCircle(0, 0, 4),  c2 = new MathCircle(0, 4, 4), c3 = new MathCircle(4, 0, 4), c4 = new MathCircle(0, 1, 2);
		c1.show();
		c2.show();
		c3.show();
		getInscribedCircleFromIntersection(c1, c2).show();
		getInscribedCircleFromIntersection(c1, c3).show();
		getInscribedCircleFromIntersection(c2, c3).show();
		getInscribedCircleFromIntersection(c1, c4).show();
		getInscribedCircleInBOutA(c1, c2).show();
		getInscribedCircleInBOutA(c1, c3).show();
		getInscribedCircleInBOutA(c2, c3).show();
		getInscribedCircleInBOutA(c1, c4).show();
	}

}
