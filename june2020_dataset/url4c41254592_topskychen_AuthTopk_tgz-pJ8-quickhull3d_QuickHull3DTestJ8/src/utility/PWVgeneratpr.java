/**
 * 
 */
package utility;

import java.io.ObjectInputStream.GetField;

import Math.MathPoint;
import Math.MathUtility;
import mesh.MPolygon;
import mesh.PVoronoi;

/**
 * @author chenqian
 *
 */
public class PWVgeneratpr {

	public static float lx = -10, rx = 18, ly = -10, ry = 18;
	public static MathPoint p0 = new MathPoint(lx, ly);
	public static MathPoint p1 = new MathPoint(lx, ry);
	public static MathPoint p2 = new MathPoint(rx, ly);
	public static MathPoint p3 = new MathPoint(rx, ry);
	
	
	public static boolean isInRnage(float x, float y){  
		return (x >= lx && x <= rx && y >= ly &&y <= ry);
	}
	
	public static boolean isInRnage(MathPoint u){
		float x = (float) u.getX(), y = (float) u.getY();
		return (x >= lx && x <= rx && y >= ly && y <= ry);
	}
	
	public static MathPoint getCrossPoint(MathPoint u, MathPoint v){
		if(MathUtility.is2SegmentsCrossed(u, v, p0, p1))return MathUtility.getCross2Lines(u, v, p0, p1);
		if(MathUtility.is2SegmentsCrossed(u, v, p0, p2))return MathUtility.getCross2Lines(u, v, p0, p2);
		if(MathUtility.is2SegmentsCrossed(u, v, p3, p1))return MathUtility.getCross2Lines(u, v, p3, p1);
		if(MathUtility.is2SegmentsCrossed(u, v, p3, p2))return MathUtility.getCross2Lines(u, v, p3, p2);
		//u.show(); v.show();
		return null;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		float [][] points_paper ={
//				{3, 4, 3}, 	//1
//				{1, 2, 6}, 	//2
//				{3, 1, 4}, 	//3
//				{6, 1, 5}, 	//4
//				{7, 2, 10}, //5
//				{8, 1, 10}, //6
//				{6, 6, 13}, //7
//				{3, 7, 15}, // 8
//				{7, 7, 13} //9
//				};
			float [][] points_paper ={
					{4	,6,	4},
					{1	,7,	9},
					{3	,8,	7},
					{6	,10,	3},
					{8	,8	,12},
					{10,	10	,16},
					{1	,2,	8},
					{4	,3,	4},
					{4	,0,	1},
					{10,	0,	2},
					{8	,3,	6},
					};
			System.out.println("size(200);");
			System.out.println("draw(" + "(" + p0.getX() + "," + p0.getY() + ") -- "
			+ "(" + p0.getX() + "," + p0.getY() + ") -- "
			+ "(" + p1.getX() + "," + p1.getY() + ") -- "
			+ "(" + p3.getX() + "," + p3.getY() + ") -- "
			+ "(" + p2.getX() + "," + p2.getY() + ") -- "
					+ "cycle);");
//			System.out.println("dot((-100,-100));");
//			System.out.println("dot((200,-100));");
//			System.out.println("dot((-100,200));");
//			System.out.println("dot((200,200));");
			PVoronoi pvd_paper = new PVoronoi(points_paper);
			MPolygon[] poly_paper = pvd_paper.getRegions();
			for(int i = 0; i < points_paper.length; i++){
				System.out.println("dot((" + points_paper[i][0] + ", " + points_paper[i][1] + "));");
			}
			for(int i = 0; i < poly_paper.length; i++){
				String line = "draw(";
				boolean is_cycle = true;
				for(int j = 0; j < poly_paper[i].count(); j++){
					MathPoint u = new MathPoint(poly_paper[i].getCoords(j));
					MathPoint v2 = new MathPoint(poly_paper[i].getCoords((j + 1) % poly_paper[i].count()));
					MathPoint v1 = new MathPoint(poly_paper[i].getCoords((j - 1 + poly_paper[i].count()) % poly_paper[i].count()));
					
					if(!isInRnage(u) && !isInRnage(v1) && !isInRnage(v2)){
						is_cycle = false;
						//u.show(); v.show();
						continue;
					}
					if(!line.equals("draw("))line += " -- ";
					if(!isInRnage(u)){
						//u.show();
						//System.out.println("==>");
						MathPoint u1 = getCrossPoint(u, v1);
						MathPoint u2 = getCrossPoint(u, v2);
						//u.show();
						if(u1 != null)line += "(" + u1.getX() + "," + u1.getY() + ")";						
						if(u2 != null){
							if(u1 != null) line += " -- ";
							line += "(" + u2.getX() + "," + u2.getY() + ")";
						}
					}else{
						line += "(" + u.getX() + "," + u.getY() + ")";						
					}
					
				}
				if(is_cycle)line += " -- cycle";
				if(poly_paper[i].count() != 0)System.out.println(line + ");");
			}
	}

}
