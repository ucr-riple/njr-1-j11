/**
 * 
 */
package Math;

import java.util.Scanner;

/**
 * @author chenqian
 *
 */
public class MathUtility {

	static double eps = 1e-8;
	
	public static double getEps(){
		return eps;
	}
	
	/**
	 * set eps, the default is 1e-8;
	 * */
	public static void setEps(double _eps){
		eps = _eps;
	}
	
	/**
	 * Calculate distance given lat, lng.
	 * */
	public static double calculateLatLngDist( double startLat, double startLng, double endLat, double endLng ){
		/*double pi = atan2(1.0, 1.0) * 4;
		//scale back to radians
		double startLatInRadians = startLat*pi/180.0;
		double endLatInRadians = endLat*pi/180.0;
		double startLngInRadians = startLng*pi/180.0;
		double endLngInRadians = endLng*pi/180.0;
		double radians = acos(cos(startLatInRadians)*cos(startLngInRadians)*cos(endLatInRadians)*
			cos(endLngInRadians) + cos(startLatInRadians)*sin(startLngInRadians)*
			cos(endLatInRadians)*sin(endLngInRadians)+sin(startLatInRadians)*sin(endLatInRadians));
	
		//assume the radius of earth is 6372.8 km
		double dist = 6372800.0 * radians;
		gettimeofday(&eucEnd, 0);
		tcalD += (double)(eucEnd.tv_sec - eucStart.tv_sec + (double)(eucEnd.tv_usec - eucStart.tv_usec) / CLOCKS_PER_SEC);
	
		return dist<0 ? -dist : dist;*/
	
		double ratio = Math.PI / 180.0; // pi/180 
		double x = (endLng * ratio - startLng * ratio) * Math.cos((startLat * ratio + endLat * ratio) / 2);
		double y = (endLat * ratio - startLat * ratio);
		double circleDist = 6372800.0 * Math.sqrt( x * x + y * y );
		//circleDist = circleDist<0 ? -circleDist : circleDist;
		return circleDist;	
	}

	/**
	 * 
	 * get square
	 * 
	 * */

	public static double Square(double x){
		return x * x;
	}
	
	/**
	 * D(x) < 0, x < 0
	 * D(x) == 0, x == 0
	 * D(x) > 0, x > 0
	 * */
	public static int D(double x){
		if(x < -eps)return -1;
		if(x > eps)return 1;
		return 0;
	}
	
	/**
	 * get Distance between points low and high.
	 * */
	public static double getDistance(double[] low, double[] high){
		if(low.length != high.length){
			throw new IllegalArgumentException("Point has different dimensions.");
		}
		double ans = 0;
		for(int i = 0 ; i < low.length; i++){
			ans += (high[i] - low[i]) * (high[i] - low[i]);
		}
		return Math.sqrt(ans);
	}
	
	/**
	 * get distance of point a, b;
	 * */
	public static double getDistance(MathPoint a, MathPoint b){
		return MathPoint.getDistance(a, b);
	}
	
	/**
	 * get Distance between points low and high.
	 * */
	public static double getDistance2(double[] low, double[] high){
		if(low.length != high.length){
			throw new IllegalArgumentException("Point has different dimensions.");
		}
		double ans = 0;
		for(int i = 0 ; i < low.length; i++){
			ans += (high[i] - low[i]) * (high[i] - low[i]);
		}
		return ans;
	}
	
	/**
	 * get Distance between (x1, y1) and (x2, y2).
	 * */
	public static double getDistance(double x1, double y1, double x2, double y2){
		return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	
	/**
	 * get squared Distance between (x1, y1) and (x2, y2).
	 * */
	public static double getDistance2(double x1, double y1, double x2, double y2){
		return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
	}
	
	/**
	 * Determinant
	 * */
	public static double Det(MathPoint a, MathPoint b, MathPoint c){
		return -(b.getX() - a.getX()) * (c.getY() - a.getY()) + (c.getX() - a.getX()) * (b.getY() - a.getY());
	}
	
	
	/**
	 * Calculate the cross point of two lines. Before all, we need to judge two lines are not parallel.
	 * */
	public static MathPoint getCross2Lines(MathPoint u1, MathPoint u2, MathPoint v1, MathPoint v2){
		MathPoint ret=u1;
		double t=((u1.getX()-v1.getX())*(v1.getY()-v2.getY())-(u1.getY()-v1.getY())*(v1.getX()-v2.getX()))
			/((u1.getX()-u2.getX())*(v1.getY()-v2.getY())-(u1.getY()-u2.getY())*(v1.getX()-v2.getX()));
		ret.setX(ret.getX() + (u2.getX()-u1.getX())*t);
		ret.setY(ret.getY() + (u2.getY()-u1.getY())*t);
		return ret;
	}
	
	
	/**
	 * To judge whether two lines are parallel.
	 * */
	public static boolean isParallel(MathPoint u1, MathPoint u2, MathPoint v1, MathPoint v2){
		return D((u1.getX()-u2.getX())*(v1.getY()-v2.getY())-(v1.getX()-v2.getX())*(u1.getY()-u2.getY()))==0;
	}

	
	/**
	 * To judge whether two points locate two sides of a line strictly.
	 * 
	 * */
	public static boolean isOppoSide(MathPoint u1, MathPoint u2, MathPoint v1, MathPoint v2){
		return D(Det(u1, v1, v2) * Det(u2, v1, v2)) < 0;
	}
	
	public static boolean isInsideRect(MathPoint x, MathPoint[] rect){
		if(D(Det(x, rect[0], rect[1]) * Det(x, rect[3], rect[2])) > 0)return false;
		if(D(Det(x, rect[0], rect[3]) * Det(x, rect[1], rect[2])) > 0)return false;
		//System.err.println("inside");
		return true;
	}
	
	
	/**
	 * To judge whether two segments are intersected.
	 * */
	public static boolean is2SegmentsCrossed(MathPoint u1, MathPoint u2, MathPoint v1, MathPoint v2){
		return isOppoSide(u1, u2, v1, v2) && ( D(Det(v1, u1, u2) * Det(v2, u1, u2)) <= 0 ) || ( D(Det(u1, v1, v2) * Det(u2, v1, v2)) <= 0 ) && isOppoSide(v1, v2, u1, u2);
	}
	
	public static boolean isSegmentIntersectWithRect(MathLine l, MathPoint[] rect){
		for(int i = 0; i < 4; i++){
			if(is2SegmentsCrossed(l.u, l.v, rect[i], rect[(i + 1) % 4])){
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isSegmentInterOrInsideWithRect(MathLine l, MathPoint[] rect){
		if(isInsideRect(l.u, rect) || isInsideRect(l.v, rect) || isSegmentIntersectWithRect(l, rect)){
			return true;
		}
		return false;
	}
	
	public static boolean isSegmentInterOrInsideWithRect(MathPoint p1, MathPoint p2, MathPoint p3, MathPoint p4){
		MathLine seg = new MathLine(p1, p2);
		MathPoint[] rect = new MathPoint[4];
		rect[0] = new MathPoint(p3.getX(), p3.getY());
		rect[1] = new MathPoint(p3.getX(), p4.getY());
		rect[2] = new MathPoint(p4.getX(), p4.getY());
		rect[3] = new MathPoint(p4.getX(), p3.getY());
		if(isSegmentInterOrInsideWithRect(seg, rect)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Get approximate value of x.
	 * 
	 * */
	public static double getApproximate(double x){
		if(D(x - Math.ceil(x)) == 0)return Math.ceil(x);
		if(D(x - Math.floor(x)) == 0)return Math.floor(x);
		return x;
	}
	
	/**
	 * Judge a point on a line; 
	 * */
	public static boolean isPointOnLine(MathPoint p, MathLine l){
		return l.isPointOnLine(p);
	}
	
	/**
	 * Judge a point on a line; 
	 * */
	public static boolean isPointOnLine(MathPoint p, MathPoint l1, MathPoint l2){
		MathLine l = new MathLine(l1, l2);
		return l.isPointOnLine(p);
	}
	
	
	/**
	 * Judge a point on a segment; 
	 * */
	public static boolean isPointOnSegment(MathPoint p, MathLine l){
		return l.isPointOnSegment(p);
	}
	
	
	/**
	 * Judge a point on a segment; 
	 * */
	public static boolean isPointOnSegment(MathPoint p, MathPoint l1, MathPoint l2){
		MathLine l = new MathLine(l1, l2);
		return l.isPointOnSegment(p);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		while(true){
			double lat1 = in.nextDouble(), lng1 = in.nextDouble(), lat2 = in.nextDouble(), lng2 = in.nextDouble();
			System.out.println(calculateLatLngDist(lat1, lng1, lat2, lng2));
		}
	}

}
