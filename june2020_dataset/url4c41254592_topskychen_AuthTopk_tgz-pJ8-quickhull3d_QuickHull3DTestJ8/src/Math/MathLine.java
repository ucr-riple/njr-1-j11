/**
 * 
 */
package Math;

/**
 * @author chenqian
 *
 */
public class MathLine {

	MathPoint u, v;
	public MathLine(){}
	
	public MathLine(double x1, double y1, double x2, double y2){
		u = new MathPoint(x1, y1);
		v = new MathPoint(x2, y2);
	}
	
	public MathLine(MathPoint _u, MathPoint _v){
		u = _u; v = _v;
	}
	
	public boolean isPointOnLine(double x, double y){
		MathPoint p = new MathPoint(x, y);
		return isPointOnLine(p);
	}
	
	public boolean isPointOnLine(MathPoint p){
		return MathUtility.D(MathUtility.Det(p, u, v)) == 0;
	}
	
	public boolean isPointOnSegment(double x, double y){
		MathPoint p = new MathPoint(x, y);
		return isPointOnSegment(p);
	}
	public boolean isPointOnSegment(MathPoint p){
		return isPointOnLine(p) && 
				MathUtility.D((v.getX() - p.getX()) * (u.getX() - p.getX())) <= 0 && 
				MathUtility.D((v.getY() - p.getY()) * (u.getY() - p.getY())) <= 0;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MathLine l = new MathLine(-1, -1, 1, 1);
		System.out.println(l.isPointOnLine(0, 0));
		System.out.println(l.isPointOnSegment(0, 0));
		System.out.println(l.isPointOnLine(2, 2));
		System.out.println(l.isPointOnSegment(2, 2));
	}

}
