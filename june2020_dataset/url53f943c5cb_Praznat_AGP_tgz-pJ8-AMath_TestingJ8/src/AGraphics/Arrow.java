package AGraphics;

class Arrow {
	int dx;
	int dy;
	int[] xy;
	public Arrow() {
		xy = new int[] {0,0}; dx = 0; dy = 0;
	}
	public Arrow(int[] XY, int dX, int dY) {
		xy = XY; dx = dX; dy = dY;
	}
	public int getDX() {return dx;}
	public int getDY() {return dy;}
	public int[] getXY() {return xy;}
	public int side(int[] ref) {
		double y = dy*(double)ref[0]/(double)dx;
		return (int) Math.signum(y - ref[0]);
	}
	public double slope() {return (double) dy / (double) dx;}
	public double hypotenuse() {
		return Math.sqrt(dx*dx + dy*dy);
	}
	public double acosine() {
		return Math.cos(Math.atan(slope()));
	}
	public double asine() {
		return Math.sin(Math.atan(slope()));
	}
	public double cosine() {
		return (double)dx / hypotenuse();
	}
	public double sine() {
		return (double)dy / hypotenuse();
	}
}
