/**
 * 
 */
package Math;

/**
 * @author chenqian
 *
 */
public class MathSphere {

	MathPoint c;
	double r;
	
	public MathSphere(){}
	
	public MathSphere(MathCircle mc){
		this.c = new MathPoint(mc.getCenter());
		this.r = mc.getRadius();
	}
	
	public MathSphere(MathSphere ms){
		this.c = new MathPoint(ms.getCenter());
		this.r = ms.getRadius();
	}
	
	public MathSphere(double x, double y, double z, double r){
		this.c = new MathPoint(x, y, z);
		this.r = r;
	}
	
	public MathSphere(MathPoint c, double r){
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
	
	public static double getDistByCenter(MathSphere a, MathSphere b){
		return MathUtility.getDistance(a.getCenter(), b.getCenter());
	}
	
	
	/**
	 * 1 inside 
	 * 2 inscribe
	 * 3 intersect
	 * 4 circumscribe
	 * 5 outside
	 * */
	public static int getRelation(MathSphere a, MathSphere b){
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
	 * 
	 * Judge a point inside or on the sphere
	 * **/
	public boolean isInside(MathPoint p){
		double d = MathUtility.getDistance(getCenter(), p);
		if(MathUtility.D(d - getRadius()) <= 0)return true;
		else return false;
	}
	
	/**
	 * Judge a point inside the sphere strictly
	 * */
	public boolean isInsideStrictly(MathPoint p){
		double d = MathUtility.getDistance(getCenter(), p);
		if(MathUtility.D(d - getRadius()) < 0)return true;
		else return false;
	}
	
	/**
	 * get the inscribed circle from intersection of circle A and B. 
	 * have to guarantee A and B are intersected or iside first.
	 * 
	 * */
	public static MathSphere getInscribedCircleFromIntersection(MathSphere A, MathSphere B){
		if(getRelation(A, B) <= 2){
			if(MathUtility.D(A.getRadius() - B.getRadius()) < 0)return A;
			else return B;
		}
		if(getRelation(A, B) >= 4){
			return new MathSphere(0, 0, 0, -1);
		}
		double d1 = getDistByCenter(A, B);
		double rn = 0.5 * (A.getRadius() + B.getRadius() - d1);
		return new MathSphere(MathPoint.getScorePoint(A.getCenter(), B.getCenter(), (A.getRadius() - rn) / d1), rn);
	}
	
	/**
	 * get the circumscribed circle from intersection of circle A and B. The new circle is inscribed with B, and circumscribed with A.
	 * have to guarantee A and B are intersected or outside first.
	 * */
	public static MathSphere getInscribedCircleInBOutA(MathSphere A, MathSphere B){
		if(getRelation(A, B) <= 2){
			return new MathSphere(0, 0, 0, -1);
		}
		if(getRelation(A, B) >= 4){
			return B;
		}
		double d1 = getDistByCenter(A, B);
		double rn = 0.5 * (d1 - A.getRadius() + B.getRadius());
		return new MathSphere(MathPoint.getScorePoint(A.getCenter(), B.getCenter(), (A.getRadius() + rn) / d1), rn);
	}
	
	
	public double getVolume(){
		return 4 * 3 / Math.PI * getRadius() * getRadius() * getRadius();
	}
	
	public double getSurfaceArea(){
		return 4 * Math.PI * getRadius() * getRadius();
	}
	
	public void show(){
		System.out.println("Sphere center at (" + getCenter().getX() + ", " + getCenter().getY() + ", " + getCenter().getZ() + ") with r = " + getRadius() );
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MathSphere c1 = new MathSphere(0, 0, 0, 4),  c2 = new MathSphere(0, 4, 0, 4), c3 = new MathSphere(4, 0, 0, 4), c4 = new MathSphere(0, 1, 0, 2);
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
