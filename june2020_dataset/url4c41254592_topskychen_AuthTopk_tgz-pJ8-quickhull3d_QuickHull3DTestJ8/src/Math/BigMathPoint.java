/**
 * 
 */
package Math;

import java.math.BigDecimal;

/**
 * @author chenqian
 *
 */
public class BigMathPoint {

	private BigDecimal[] coords;
	
	public BigMathPoint(BigMathPoint p){
		coords = new BigDecimal[p.getCoordsLen()];
		for(int i = 0; i < p.getCoordsLen(); i++){
			coords[i] = p.getCoord(i);
		}
	}
	
	public BigMathPoint(long _x, long _y){
		coords = new BigDecimal[2];
		setX(new BigDecimal(_x)); setY(new BigDecimal(_y));
	}
	
	public BigMathPoint(){
		coords = new BigDecimal[2];
	}
	
	public static BigMathPoint add(BigMathPoint p, BigMathPoint q){
		BigMathPoint res = new BigMathPoint();
		res.coords[0] = p.getX().add(q.getX());
		res.coords[1] = p.getY().add(q.getY());
		return res;
	}
	public BigMathPoint(BigDecimal _x, BigDecimal _y){
		coords = new BigDecimal[2];
		setX(_x); setY(_y);
	}
	public BigMathPoint(BigDecimal[] coords){
		this.coords = new BigDecimal[coords.length];
		for(int i = 0; i < coords.length; i++){
			this.coords[i] = coords[i];
		}
	}
	
	public BigMathPoint(long[] coords){
		this.coords = new BigDecimal[coords.length];
		for(int i = 0; i < coords.length; i++){
			this.coords[i] = new BigDecimal(coords[i]);
		}
	}
	
	public BigMathPoint(float[] coords){
		this.coords = new BigDecimal[coords.length];
		for(int i = 0; i < coords.length; i++){
			this.coords[i] = new BigDecimal(coords[i]);
		}
	}
	
	public void show(){
		System.out.println(getX() + " " + getY());
	}
	
	public BigDecimal getX() {
		return coords[0];
	}
	public void setX(BigDecimal x) {
		this.coords[0] = x;
	}
	public BigDecimal getY() {
		return coords[1];
	}
	public void setY(BigDecimal y) {
		this.coords[1] = y;
	}
	
	public BigDecimal getW(){
		return coords[2];
	}

	public BigDecimal[] getCoords(){
		return coords;
	}
	
	public int getCoordsLen(){
		return coords.length;
	}
	
	public BigDecimal getCoord(int i){
		if(i >= coords.length)System.err.println("i is out of range");
		return coords[i];
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
