/**
 * 
 */
package Math;

/**
 * @author chenqian
 *
 */
public class MathPoint {

	private double[] coords;
	
	public MathPoint(MathPoint p){
		coords = new double[p.getCoordsLen()];
		for(int i = 0; i < p.getCoordsLen(); i++){
			coords[i] = p.getCoord(i);
		}
	}
	
	public MathPoint(){
		coords = new double[2];
	}
	
	public MathPoint(double _x, double _y, double _z){
		coords = new double[3];
		setX(_x); setY(_y); setZ(_z);
	}
	
	public MathPoint(double _x, double _y){
		coords = new double[2];
		setX(_x); setY(_y);
	}
	
	public MathPoint(double[] coords){
		this.coords = new double[coords.length];
		for(int i = 0; i < coords.length; i++){
			this.coords[i] = coords[i];
		}
	}
	public MathPoint(float[] coords){
		this.coords = new double[coords.length];
		for(int i = 0; i < coords.length; i++){
			this.coords[i] = coords[i];
		}
	}
	
	public void show(){
		System.out.println(getX() + " " + getY());
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public double getX() {
		return coords[0];
	}
	public void setX(double x) {
		this.coords[0] = x;
	}
	public double getY() {
		return coords[1];
	}
	public void setY(double y) {
		this.coords[1] = y;
	}
	public double getZ() {
		return coords[2];
	}
	public void setZ(double z){
		this.coords[2] = z;
	}
	public double[] getCoords(){
		return coords;
	}
	
	public int getCoordsLen(){
		return coords.length;
	}
	
	public double getCoord(int i){
		if(i >= coords.length)System.err.println("i is out of range");
		return coords[i];
	}
	
	public MathPoint getNorm(double k){
		double[] zero = new double[getCoordsLen()];
		for(int i = 0; i < getCoordsLen(); i ++){
			zero[i] = 0;
		}
		double dist = MathUtility.getDistance(zero, getCoords());
		double[] ncoords = new double[getCoordsLen()];
		for(int i = 0; i < getCoordsLen(); i ++){
			ncoords[i] = getCoord(i) * k / dist;
		}
		return new MathPoint(ncoords);
	}
	
	public static double getDistance(MathPoint a, MathPoint b){
		return MathUtility.getDistance(a.getCoords(), b.getCoords());
	}
	
	public static MathPoint add(MathPoint a, MathPoint b){
		if(a.getCoordsLen() != b.getCoordsLen()){
			System.out.println("warning: a and b has different dimensions");
			return null;
		}
		double [] coords = new double[a.getCoordsLen()];
		for(int i = 0; i < a.getCoordsLen(); i ++){
			coords[i] = a.getCoord(i) + b.getCoord(i);
		}
		return new MathPoint(coords);
	}
	
	public MathPoint add(MathPoint b){
		if(getCoordsLen() != b.getCoordsLen()){
			System.out.println("warning: a and b has different dimensions");
			return null;
		}
		double [] coords = new double[getCoordsLen()];
		for(int i = 0; i < getCoordsLen(); i ++){
			coords[i] = getCoord(i) + b.getCoord(i);
		}
		return new MathPoint(coords);
	}
	
	public static MathPoint minus(MathPoint a, MathPoint b){
		if(a.getCoordsLen() != b.getCoordsLen()){
			System.out.println("warning: a and b has different dimensions");
			return null;
		}
		double [] coords = new double[a.getCoordsLen()];
		for(int i = 0; i < a.getCoordsLen(); i ++){
			coords[i] = a.getCoord(i) - b.getCoord(i);
		}
		return new MathPoint(coords);
	}
	
	public MathPoint minus(MathPoint b){
		if(getCoordsLen() != b.getCoordsLen()){
			System.out.println("warning: a and b has different dimensions");
			return null;
		}
		double [] coords = new double[getCoordsLen()];
		for(int i = 0; i < getCoordsLen(); i ++){
			coords[i] = getCoord(i) - b.getCoord(i);
		}
		return new MathPoint(coords);
	}
	
	public void multiplyToSelf(double k){
		for(int i = 0; i < getCoordsLen(); i ++){
			coords[i] *= k;
		}
	}
	
	public MathPoint multiply(double k){
		multiplyToSelf(k);
		return this;
	}
	
	public static MathPoint dotproduct(MathPoint a, MathPoint b){
		if(a.getCoordsLen() != b.getCoordsLen()){
			System.out.println("warning: a and b has different dimensions");
			return null;
		}
		double [] coords = new double[a.getCoordsLen()];
		for(int i = 0; i < a.getCoordsLen(); i ++){
			coords[i] = a.getCoord(i) * b.getCoord(i);
		}
		return new MathPoint(coords);
	}
	
	public MathPoint dotproduct(MathPoint b){
		if(getCoordsLen() != b.getCoordsLen()){
			System.out.println("warning: a and b has different dimensions");
			return null;
		}
		double [] coords = new double[getCoordsLen()];
		for(int i = 0; i < getCoordsLen(); i ++){
			coords[i] = getCoord(i) * b.getCoord(i);
		}
		return new MathPoint(coords);
	}
	
	public static MathPoint getScorePoint(MathPoint a, MathPoint b, double l){
		return b.minus(a).multiply(l).add(a);
	}
	
	public boolean equals(MathPoint b){
		if(getCoordsLen() != b.getCoordsLen())return false;
		for(int i = 0; i < getCoordsLen(); i++){
			if(MathUtility.D(getCoord(i) - b.getCoord(i)) != 0)return false;
		}
		return true;
	}
}
