/**
 * 
 */
package mesh;

import quickhull3d.QuickHull3D;
import utility.FormatData2Norm;

/**
 * @author chenqian
 * This is Power Delaunay, derived from Delaynay.java.
 */
public class PDelaunay {
	float[][] edges;
	LinkedArray mesh;
	int[][] links;
	int linkCount;
	
	public PDelaunay(float points[][]){
		if( points.length < 1 ){
			edges = new float[0][4];
			mesh = new LinkedArray(0);
			links = new int[0][2];
			linkCount = 0;
			return;
		}

		// build points array for qhull
		double qPoints[] = new double[ points.length*3 + 9 ];
		for(int i=0; i<points.length; i++){
			qPoints[i*3] = points[i][0];
			qPoints[i*3+1] = points[i][1];
			qPoints[i*3+2] = -(points[i][0]*points[i][0] + points[i][1]*points[i][1]) - points[i][2]; // standard half-squared eucledian distance
		}
		// 1
		qPoints[ qPoints.length-9 ] = -2 * FormatData2Norm.scale;
		qPoints[ qPoints.length-8 ] = 0;
		qPoints[ qPoints.length-7 ] =  -(qPoints[ qPoints.length-9 ] * qPoints[ qPoints.length-9 ] + 
				qPoints[ qPoints.length-8 ] * qPoints[ qPoints.length-8 ]);

		// 2
		qPoints[ qPoints.length-6 ] = 2 * FormatData2Norm.scale;
		qPoints[ qPoints.length-5 ] = 2 * FormatData2Norm.scale;
		qPoints[ qPoints.length-4 ] =  -(qPoints[ qPoints.length-6 ] * qPoints[ qPoints.length-6 ] + 
				qPoints[ qPoints.length-5 ] * qPoints[ qPoints.length-5 ]);
		// 3
		qPoints[ qPoints.length-3 ] = 2 * FormatData2Norm.scale;
		qPoints[ qPoints.length-2 ] = -2 * FormatData2Norm.scale;
		qPoints[ qPoints.length-1 ] =  -(qPoints[ qPoints.length-3 ] * qPoints[ qPoints.length-3 ] + 
				qPoints[ qPoints.length-2 ] * qPoints[ qPoints.length-2 ]);

		// prepare quickhull
		QuickHull3D quickHull = new QuickHull3D(qPoints);
		int[][] faces = quickHull.getFaces(QuickHull3D.POINT_RELATIVE + QuickHull3D.CLOCKWISE + QuickHull3D.INDEXED_FROM_ONE);

		// turn faces into links
		mesh = new LinkedArray(points.length+3);
		links = new int[1][2];
		linkCount = 0;
		for(int i=0; i<faces.length; i++){
			for(int j=0; j<faces[i].length; j++){
				int p = faces[i][j];
				int q = faces[i][(j+1)%faces[i].length];
				if( p<=points.length && q<=points.length && !mesh.linked(p,q) ){
					mesh.link(p,q);
					if(linkCount==links.length){
						int[][] tmplinks = new int[links.length*2][2];
						System.arraycopy(links, 0, tmplinks, 0, links.length);
						links = tmplinks;
					}
					links[linkCount][0] = p;
					links[linkCount][1] = q;
					linkCount++;
				}
			}
		}		
		
		// turn links into edges
		edges = new float[linkCount][4];
		for(int i=0; i<linkCount; i++){
			edges[i][0] = points[links[i][0] - 1][0];
			edges[i][1] = points[links[i][0] - 1][1];
			edges[i][2] = points[links[i][1] - 1][0];
			edges[i][3] = points[links[i][1] - 1][1];
		}
	}
	
	public float[][] getEdges(){
		return edges;
	}

	public int[][] getLinks(){
		return links;
	}

	public int[] getLinked( int i ){
		return mesh.get(i).links;
	}

	public int edgeCount(){
		return linkCount;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//for test
		float [][] points = {{(float) -2.5,0,1}, {(float) 2.5,0,31}};
		PDelaunay pd = new PDelaunay(points);
		System.out.println("0," + (pd.getLinked(1)[0] - 1));
	}

}
