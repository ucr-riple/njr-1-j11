/**
 * 
 */
package quickhull3d;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author chenqian This is for testing CA dataset.
 */
public class QhullTestCA {
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub

		// x y z coordinates of 6 points

		long start = System.currentTimeMillis();
		Scanner in = new Scanner(new FileInputStream(new File("source/CA.in")));
		in.nextInt(); int len = in.nextInt();
		Point3d[] points = new Point3d[len];
		for(int i = 0; i < len; i++){
			int x = in.nextInt(); // x
			int y = in.nextInt(); // y
			int z = in.nextInt(); // z
			//System.out.println(x + "\t" + y + "\t" + z);
			points[i] = new Point3d(x, y, z);
		}
		
		System.out.println("Load finished!");
//		Point3d[] points = new Point3d[] { new Point3d(0.0, 0.0, 0.0),
//				new Point3d(1.0, 0.5, 0.0), new Point3d(2.0, 0.0, 0.0),
//				new Point3d(0.5, 0.5, 0.5), new Point3d(0.0, 0.0, 2.0),
//				new Point3d(0.1, 0.2, 0.3), new Point3d(0.0, 2.0, 0.0), };

		QuickHull3D hull = new QuickHull3D();
		hull.build(points);
		System.out.println("Build finihed!");

		System.out.println("Vertices:");
		Point3d[] vertices = hull.getVertices();
		System.out.println("vertices size:\t" + vertices.length);
		// for (int i=0; i<vertices.length; i++)
		// { Point3d pnt = vertices[i];
		// System.out.println (pnt.x + " " + pnt.y + " " + pnt.z);
		// }

		System.out.println("Faces:");
		int[][] faceIndices = hull.getFaces();
		System.out.println("face size:\t" + faceIndices.length);
		// for (int i=0; i<vertices.length; i++)
		// { for (int k=0; k<faceIndices[i].length; k++)
		// { System.out.print (faceIndices[i][k] + " ");
		// }
		// System.out.println ("");
		// }
		long end = System.currentTimeMillis();
		System.out.println("Time elapse:\t" + (end - start) / 1000);
	}

}
