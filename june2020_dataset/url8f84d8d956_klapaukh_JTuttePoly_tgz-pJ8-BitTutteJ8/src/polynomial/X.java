package polynomial;

public class X extends XYTerm {
	public X(int p) {
		super(p, 0);
	}

	public XYTerm times(Y yt) {
		return new XYTerm(xpower, yt.ypower);
	}

	public X times(X xt) {
		return new X(xpower * xt.xpower);
	}
}
