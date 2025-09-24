/**
 * 
 */
package Math;

import java.awt.Polygon;
import java.util.ArrayList;

/**
 * @author chenqian
 *
 */
public class MathPolygon {

	ArrayList<MathPoint> points;
	
	public MathPolygon(MathPoint[] points){
		this.points = new ArrayList<MathPoint>();
		for(int i = 0; i < points.length; i ++){
			this.points.add(new MathPoint(points[i]));
		}
	}
	
	public MathPolygon(ArrayList<MathPoint> points){
		this.points = new ArrayList<MathPoint>();
		for(int i = 0; i < points.size(); i ++){
			this.points.add(new MathPoint(points.get(i)));
		}
	}
	
	public MathPolygon(){
		points = new ArrayList<MathPoint>();
	}
	
	public int getVerticesNumber(){
		return points.size();
	}
	
	public MathPoint getVertice(int i){
		if(points.size() == 0){
			System.out.println("The polygon is empty!");
			return null;
		}
		return points.get(i % points.size());
	}
	
	public boolean removeVertice(int i){
		return points.remove(points.get(i % points.size()));
	}
	
	public void addVertice(MathPoint p, int i){
		points.add(i % points.size(), p);
	}
	
	public void addVertice(MathPoint p){
		points.add(p);
	}
	
	public void addVertice(double[] p){
		points.add(new MathPoint(p));
	}
	
	public void addVertice(double x, double y){
		points.add(new MathPoint(x, y));
	}
	
	
	/**
	 * Judge a point in or on the polygon
	 * */
	public boolean inPolygon(MathPoint p){
		boolean[] s = new boolean[]{true, true, true};
		for (int i = 0; i < getVerticesNumber() && s[0] | s[2]; i++)
			s[MathUtility.D(MathUtility.Det(getVertice(i + 1), p, getVertice(i))) + 1] = false;
		return s[0] | s[2];
	}
	
	/**
	 * 
	 * judge a point in the polygon
	 * */
	public boolean inPolygon2(MathPoint p){
		boolean[] s = new boolean[]{true, true, true};
		for (int i = 0; i < getVerticesNumber() && s[0] | s[2]; i++)
			s[MathUtility.D(MathUtility.Det(getVertice(i + 1), p, getVertice(i))) + 1] = false;
		return s[1] && s[0] | s[2];
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MathPolygon poly = new MathPolygon();
		poly.addVertice(0, 2);
		poly.addVertice(2, 0);
		poly.addVertice(4, 2);
		poly.addVertice(2, 4);
		MathPoint[] queries = new MathPoint[]{
				new MathPoint(1, 1),
				new MathPoint(0, 2),
				new MathPoint(1, 2),
				new MathPoint(1, 3),
				new MathPoint(-1, 2)
				};
		for(int i = 0; i < queries.length; i++){			
			if(poly.inPolygon(queries[i])){
				System.out.println(queries[i].getX() + ", " + queries[i].getY() + " in the polygon");
			}else{
				System.out.println(queries[i].getX() + ", " + queries[i].getY() + " out the polygon");
			}
		}
	}

}
