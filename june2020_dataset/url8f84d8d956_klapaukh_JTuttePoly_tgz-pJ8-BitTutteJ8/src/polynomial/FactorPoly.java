package polynomial;

import java.math.BigInteger;

public class FactorPoly {

	public static final int FPOLY_PADDING_FACTOR = 1;

	private YTerms[] xterms;
	private int nxterms;

	/* =============================== */
	/* ========= CONSTRUCTORS ======== */
	/* =============================== */

	public FactorPoly() {
		nxterms = 5; // arbitrary ?
		// create the xterm array
		xterms = new YTerms[nxterms];
		for(int i =0;i<nxterms;i++){
			xterms[i] = new YTerms(); 
		}
	}

	public FactorPoly(XYTerm xyt) {
		nxterms = xyt.xpower + 1;
		// create the xterm array
		xterms = new YTerms[nxterms];
		for(int i =0;i<nxterms;i++){
			xterms[i] = new YTerms(); 
		}
		this.add(xyt);
	}

	public FactorPoly(FactorPoly fp) {
		clone(fp);
	}

	public FactorPoly(int nx, YTerms[] xts) {
		nxterms = nx;
		xterms = new YTerms[xts.length];
		for(int i=0;i<xts.length;i++){
			xterms[i] = new YTerms(xts[i]);
		}
	}

	/* =============================== */
	/* ======== ASSIGNMENT OP ======== */
	/* =============================== */

	public FactorPoly assign(FactorPoly src) {
		if (src != this) {
			clone(src);
		}
		return this;
	}

	public void swap(FactorPoly src) {
		int t = this.nxterms;
		this.nxterms = src.nxterms;
		src.nxterms = t;

		YTerms[] a = xterms;
		xterms = src.xterms;
		src.xterms = a;

	}

	/* =============================== */
	/* ======== ARITHMETIC OPS ======= */
	/* =============================== */

	public void add(XYTerm p) {
		// make sure enough x terms
		resize_xterms(p.xpower + 1);
		// now, do the addition
		xterms[p.xpower].add(p);
	}

	public void add(FactorPoly p) {
		// make sure enough x terms
		resize_xterms(p.nxterms);
		for (int i = 0; i < p.nxterms; ++i) {
			if (!p.xterms[i].is_empty()) {
				xterms[i].add(p.xterms[i]);
			}
		}
	}

	public void minus(XYTerm p) {
		// make sure enough x terms
		resize_xterms(p.xpower + 1);
		// now, do the subtraction
		xterms[p.xpower].minus(p);
	}

	public void minus(FactorPoly p) {
		// make sure enough x terms
		resize_xterms(p.nxterms);
		// now do the subtraction
		for (int i = 0; i < p.nxterms; ++i) {
			if (!p.xterms[i].is_empty()) {
				xterms[i].minus(p.xterms[i]);
			}
		}
	}

	public void times(BigInteger coefficient) {
		for (int i = 0; i < nxterms; ++i) {
			xterms[i].times(coefficient);
		}
	}

	public void times(XYTerm p) {
		if (p.xpower > 0) {
			// need to shift the x's
			resize_xterms(p.xpower + nxterms + 1);
			for (int i = nxterms; i > p.xpower; --i) {
				xterms[i - 1].swap(xterms[i - (p.xpower + 1)]);
			}
		}
		for (int i = 0; i < nxterms; ++i) {
			xterms[i].times(p);
		}
	}

	/*
	 * The more complicated general case
	 */
	public void times(FactorPoly p) {
		if (p.nxterms == 1) {
			// optimise simple case
			YTerms ps = new YTerms(p.xterms[0]);
			for (int j = 0; j < nxterms; ++j) {
				xterms[j].times(ps);
			}
		} else {
			FactorPoly r = new FactorPoly();

			for (int i = 0; i < p.nxterms; ++i) {
				if (p.xterms[i].is_empty()) {
					continue;
				}
				FactorPoly tmp = new FactorPoly(this);
				tmp.times(new XYTerm(i, 0));
				YTerms ps = new YTerms(p.xterms[i]);
				for (int j = 0; j < tmp.nxterms; ++j) {
					tmp.xterms[j].times(ps);
				}
				r.add(tmp);
			}
			this.swap(r);
		}
	}

	public FactorPoly addnew(FactorPoly p) {
		FactorPoly r = new FactorPoly(this);
		r.add(p);
		return r;
	}

	public FactorPoly minusnew(FactorPoly p) {
		FactorPoly r = new FactorPoly(this);
		r.minus(p);
		return r;
	}

	public FactorPoly timesnew(XYTerm p) {
		FactorPoly r = new FactorPoly(this);
		r.times(p);
		return r;
	}

	public FactorPoly timesnew(FactorPoly p) {
		FactorPoly r = new FactorPoly(this);
		r.times(p);
		return r;
	}

	/* ========================== */
	/* ======== OTHER OPS ======= */
	/* ========================== */

	public void insert(int n, XYTerm p) {
		// make sure enough x terms
		resize_xterms(p.xpower + 1);
		// now, do the addition
		xterms[p.xpower].insert(n, p);
	}

	public String compact_str() {
		String r = "";
		boolean first_time = true;
		for (int i = 0; i < nxterms; ++i) {
			if (!xterms[i].is_empty()) {
				if (!first_time) {
					r += " + ";
				}
				first_time = false;
				if (i > 0) {
					String ss = "";
					ss += i;
					r += "x^" + ss;
				}
				r += xterms[i].toString();
			}
		}
		return r;
	}

	public String toString() {
		String r = "";
		boolean first_time = true;
		for (int i = 0; i < nxterms; ++i) {
			if (!xterms[i].is_empty()) {
				String xs = "";
				if (i > 1) {
					xs = "*x^" + i;
				} else if (i == 1) {
					xs = "*x";
				}

				for (int j = xterms[i].ymin; j <= xterms[i].ymax; ++j) {
					if (!first_time) {
						r += " + ";
					}
					first_time = false;
					String ys = "";
					if (j > 1) {
						ys = "*y^" + j;
					} else if (j == 1) {
						ys = "*y";
					}
					r += xterms[i].get(j).toString() + xs + ys;
				}
			}
		}
		return r;
	}

	public BigInteger substitute(int x, int y) {
		BigInteger r = BigInteger.ZERO;
		BigInteger p = new BigInteger("" + x);
		for (int i = 0; i < nxterms; ++i) {
			BigInteger bx = xterms[i].substitute(y);
			r = r.add(p.pow(i).multiply(bx));
		}
		return r;
	}

	public int nterms() {
		int r = 0;
		for (int i = 0; i < nxterms; ++i) {
			r += xterms[i].size();
		}
		return r;
	}

	private void clone(FactorPoly p) {
		nxterms = p.nxterms;
		xterms = new YTerms[nxterms];
		for (int i = 0; i < nxterms; ++i) {
			xterms[i] = new YTerms(p.xterms[i]);
		}
	}

	private void resize_xterms(int ns) {
		if (ns < nxterms) {
			return;
		}
		YTerms[] xs = new YTerms[ns];
		for(int i=0;i<ns;i++){
			xs[i] = new YTerms();
		}
		// need to use swap here, because
		// I don't want the following delete []
		// call to call destructors on my YTerms
		for (int i = 0; i < nxterms; ++i) {
			xs[i].swap(xterms[i]);
		}
		xterms = xs;
		nxterms = ns;
	}

	public static void main(String args[]){
		System.out.println("Initialising Testing protocol for polynomials");
		FactorPoly p1 = new FactorPoly(new Y(0));
		FactorPoly py = new FactorPoly(new Y(1));
		FactorPoly px = new FactorPoly(new X(1));
		p1 = new FactorPoly(py);
//		FactorPoly pyx = py.timesnew(px);
		System.out.println("--" + py);
		System.out.println("--" + p1);
		System.out.println("--" + px);
		p1.addnew(px);
//		System.out.println(pyx);
//		p1.times(px);
		System.out.println("--" +py);
		System.out.println("--" + p1);
		System.out.println("--" + px);
//		System.out.println(py);
	}
}
