package polynomial;

public class XYTerm {
	public int xpower;
	public int ypower;
	public int ypowerend;

	public XYTerm(int x, int y) {
		xpower = x;
		ypower = (y);
		ypowerend = (y);
	}

	public XYTerm(int x, int y, int yend) {
		xpower = (x);
		ypower = (y);
		ypowerend = yend;
		if (yend < y) {
			ypower = 0;
			ypowerend = 0;
		}
	}

	public boolean lessThan(XYTerm t) {
		return (xpower < t.xpower) || (xpower == t.xpower && ypower < t.ypower)
				|| (xpower == t.xpower && ypower == t.ypower && ypowerend < t.ypowerend);
	}

	public boolean equals(Object o) {
		if (o instanceof XYTerm) {
			XYTerm t = (XYTerm) o;
			return (xpower == t.xpower && t.ypower == t.ypower && t.ypowerend == t.ypowerend);
		}
		return false;
	}

	public String toString() {
		StringBuilder ss = new StringBuilder();
		if (xpower > 1) {
			ss = ss.append("x^");
			ss = ss.append(xpower);
		} else if (xpower == 1) {
			ss = ss.append("x");
		}

		if (ypower < ypowerend) {
			ss = ss.append("y^").append(ypower).append("-").append(ypowerend);
		} else if (ypower > 1) {
			ss = ss.append("y^").append(ypower);
		} else if (ypower == 1) {
			ss = ss.append("y");
		}

		return ss.toString();
	}

	public double substitute(double x, double y) {
		double r = 0;
		for (int i = ypower; i != ypowerend; ++i) {
			r += Math.pow(x, (double) xpower) * Math.pow(y, (double) i);
		}
		return r;
	}
}
