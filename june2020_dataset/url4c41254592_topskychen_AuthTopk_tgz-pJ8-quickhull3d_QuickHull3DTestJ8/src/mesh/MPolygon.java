package mesh;

public class MPolygon {

	float[][] coords;
	int count = 0;
	
	public MPolygon(){
		this(0);
	}

	public MPolygon(int points){
		coords = new float[points][2];
		count = 0;
	}

	public void add(float x, float y){
		coords[count][0] = x;
		coords[count++][1] = y;
	}

	public int count(){
		return count;
	}

	public float[][] getCoords(){
		return coords;
	}
	
	public float[] getCoords(int i){
		if(i >= count)System.err.println("out of range!");
		return coords[i];
	}

}