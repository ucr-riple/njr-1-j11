package polynomial;

public class SimplePolyTerm implements Comparable<SimplePolyTerm> {

	public int xpower;
	public int ypower;

	public SimplePolyTerm(int x, int y) {
		xpower = (x);
		ypower = (y);
	}

	public boolean lessthan(SimplePolyTerm t) {
		return (xpower < t.xpower) || (xpower == t.xpower && ypower < t.ypower);
	}

	public int compareTo(SimplePolyTerm t) {
		if (lessthan(t))
			return -1;
		if (equals(t))
			return 0;
		return 1;
	}

	public boolean equals(Object o) {
		if (o instanceof SimplePolyTerm) {
			SimplePolyTerm t = (SimplePolyTerm) o;
			return (xpower == t.xpower && t.ypower == t.ypower);
		}
		return false;
	}

	public String toString() {
		StringBuilder ss = new StringBuilder();
		if (xpower > 1) {
			ss = ss.append("x^").append(xpower);
		} else if (xpower == 1) {
			ss = ss.append("x");
		}

		if (ypower > 1) {
			ss = ss.append("y^").append(ypower);
		} else if (ypower == 1) {
			ss = ss.append("y");
		}

		return ss.toString();
	}

	public double substitute(double x, double y) {
		return Math.pow(x, (double) xpower) * Math.pow(y, (double) ypower);
	}

}
