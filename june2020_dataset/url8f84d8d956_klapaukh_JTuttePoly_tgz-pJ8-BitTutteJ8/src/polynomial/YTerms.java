package polynomial;

import java.math.BigInteger;

public class YTerms {

	public int ymin;
	public int ymax;

	private int fpadding;
	private int bpadding;
	BigInteger[] coefficients;

	/* =============================== */
	/* ========= CONSTRUCTORS ======== */
	/* =============================== */

	public YTerms() {
		ymin = (1);
		ymax = (0);
		fpadding = (0);
		bpadding = (0);
		coefficients = null;
	}

	public YTerms(int y_min, int y_max) {
		alloc(y_min, y_max);
	}

	public YTerms(YTerms src) {
		clone(src);
	}

	/* =============================== */
	/* ======== ASSIGNMENT OP ======== */
	/* =============================== */

	public YTerms copy(YTerms src) {
		if (src != this) {
			// now, we try to reuse memory where possible.
			int src_ncoeffs = src.size();
			int space = size() + fpadding + bpadding;
			if (space < src_ncoeffs) {

				clone(src);
			} else {
				ymin = src.ymin;
				ymax = src.ymax;
				fpadding = 0;
				bpadding = space - src_ncoeffs;
				// copy old stuff over
				for (int i = 0; i < src_ncoeffs; ++i) {
					coefficients[i] = src.coefficients[i];
				}
				// clear any old coefficients
				for (int i = src_ncoeffs; i < space; ++i) {
					coefficients[i] = BigInteger.ZERO;
				}
			}

		}
		return this;
	}

	public void swap(YTerms src) {
		int temp = this.ymin;
		this.ymin = src.ymin;
		src.ymin = temp;

		temp = this.ymax;
		this.ymax = src.ymax;
		src.ymax = temp;

		temp = fpadding;
		this.fpadding = src.fpadding;
		src.fpadding = temp;

		temp = bpadding;
		this.bpadding = src.bpadding;
		src.bpadding = temp;

		BigInteger[] t = this.coefficients;
		this.coefficients = src.coefficients;
		src.coefficients = t;

	}

	/* =============================== */
	/* ======== ARITHMETIC OPS ======= */
	/* =============================== */

	public void add(XYTerm p) {
		// make sure enough y terms
		resize(p.ypower, p.ypowerend);
		// now, do the addition
		for (int i = p.ypower; i <= p.ypowerend; ++i) {
			this.set(i, get(i).add(BigInteger.ONE));
		}
	}

	public void add(YTerms ys) {
		// make sure enough y terms
		resize(ys.ymin, ys.ymax);
		// now, do the addition
		int start = ys.ymin;
		int end = ys.ymax;
		for (int i = start; i <= end; ++i) {
			this.set(i, this.get(i).add(ys.get(i)));
		}
	}

	public void minus(XYTerm ys) {
		// make sure enough y terms
		resize(ys.ypower, ys.ypowerend);
		// now, do the subtraction
		for (int i = ys.ypower; i <= ys.ypowerend; ++i) {
			this.set(i, get(i).subtract(BigInteger.ONE));
		}
		// At this stage, we might have reduce the ytems to all zeros and
		// we could eliminate them ...
	}

	public void minus(YTerms ys) {
		// make sure enough y terms
		resize(ys.ymin, ys.ymax);
		// now, do the subtraction
		int start = ys.ymin;
		int end = ys.ymax;
		for (int i = start; i <= end; ++i) {
			this.set(i, this.get(i).subtract(ys.get(i)));
		}
		// At this stage, we might have reduce the ytems to all zeros and
		// we could eliminate them ...
	}

	void times(BigInteger coefficient) {
		int len = (ymax - ymin) + 1;
		for (int i = 0; i != len; ++i) {
			coefficients[i + fpadding] = coefficients[i + fpadding].multiply(coefficient);
		}
	}

	public void times(XYTerm p) {
		// if this poly is empty do nothing!
		if (is_empty()) {
			return;
		}
		// Ok, it's not empty ...
		int ystart = ymin;
		int yend = ymax;
		int ypadding = fpadding;
		int nystart = ymin + p.ypower;
		int nyend = ymax + p.ypowerend;

		if (p.ypower == p.ypowerend) {
			// easy case, only a shift required
			ymin = nystart;
			ymax = nyend;
		} else {
			// harder case

			// The following could use padding, since it currently doesn't!
			BigInteger[] o_coefficients = coefficients;
			alloc(nystart, nyend);
			int depth = (p.ypowerend - p.ypower) + 1;
			int width = (yend - ystart) + 1;

			// going up the triangle
			BigInteger acc = BigInteger.ZERO;
			for (int i = 0; i < Math.min(width, depth); ++i) {
				acc = acc.add(o_coefficients[i + ypadding]);
				coefficients[i + nystart + fpadding - ymin] = acc;
			}
			// free fall (if there is any)
			for (int i = width; i < depth; ++i) {
				coefficients[i + nystart + fpadding - ymin] = acc;
			}
			// going along the top (if there is one)
			BigInteger sub = BigInteger.ZERO;
			for (int i = depth; i < width; ++i) {
				sub = sub.add(o_coefficients[i + ypadding - depth]);
				acc = acc.add(o_coefficients[i + ypadding]);
				coefficients[i + fpadding] = acc.subtract(sub);
			}
			// going down the triangle
			for (int i = Math.max(depth, width); i < (nyend - nystart) + 1; ++i) {
				sub = sub.add(o_coefficients[i + ypadding - depth]);
				coefficients[i + fpadding] = acc.subtract(sub);
			}
		}
		// and we're done!
	}

	/*
	 * The more complicated general case. Big question as to whether this code can be optimised any more.
	 */
	public void times(YTerms p) {
		// if this poly is empty do nothing
		if (is_empty()) {
			return;
		}

		if (p.size() == 1) {
			// optimise simple case!
			ymin += p.ymin;
			ymax += p.ymax;
			BigInteger v = p.get(p.ymin);

			for (int i = ymin; i <= ymax; ++i) {
				this.set(i, this.get(i).multiply(v));
			}
		} else {
			YTerms r = new YTerms(p.ymin + ymin, p.ymax + ymax);

			for (int i = p.ymin; i <= p.ymax; ++i) {
				BigInteger v = p.get(i);
				for (int j = ymin; j <= ymax; ++j) {
					r.set(j + i, r.get(j + i).add(this.get(j).multiply(v)));
				}
			}

			swap(r); // normal swap trick optimisation
		}
	}

	/* ========================== */
	/* ======== OTHER FNS ======= */
	/* ========================== */

	public int size() {
		if (coefficients == null) {
			return 0;
		}
		return (ymax - ymin) + 1;
	}

	public boolean is_empty() {
		return coefficients == null;
	}

	public BigInteger get(int i) {
		return coefficients[(i + fpadding) - ymin];
	}

	public void set(int i, BigInteger v) {
		coefficients[(i + fpadding) - ymin] = v;
	}

	public BigInteger substitute(int y) {

		if (coefficients != null) {
			BigInteger r = BigInteger.ZERO;
			BigInteger p = new BigInteger("" + y);
			for (int i = ymin; i <= ymax; ++i) {
				r = r.add(this.get(i).multiply(p.pow(i)));
			}
			return r;
		} else {
			return BigInteger.ZERO;
		}
	}

	public String toString() {
		StringBuilder ss = new StringBuilder();
		if (coefficients == null) {
			return "";
		} else if (ymin != ymax) {
			ss = ss.append("y^{").append(ymin).append("..").append(ymax).append("}");
		} else if (ymin == 1) {
			ss = ss.append("y");
		} else if (ymin != 0) {
			ss = ss.append("y^").append(ymin);
		}
		ss = ss.append("(");
		for (int i = ymin; i <= ymax; ++i) {
			if (i != ymin) {
				ss = ss.append(" + ");
			}
			ss = ss.append(this.get(i));
		}
		ss = ss.append(")");
		return ss.toString();
	}

	public void insert(int n, XYTerm p) { // needs a better name?
		// make sure enough y terms
		resize(p.ypower, p.ypowerend);
		// now, do the addition
		for (int i = p.ypower; i <= p.ypowerend; ++i) {
			this.set(i, this.get(i).add(new BigInteger("" + n)));
		}
	}

	/* =============================== */
	/* ======== HELPERS ======= */
	/* =============================== */

	private void resize(int n_ymin, int n_ymax) {
		if (is_empty()) {
			alloc(n_ymin, n_ymax);
		} else {
			int d_end = n_ymax - ymax;
			int d_beg = ymin - n_ymin;

			if (d_beg <= (int) fpadding && d_end <= (int) bpadding) {
				// in this case, there is enough padding to cover it
				if (d_end > 0) {
					ymax = n_ymax;
					bpadding -= d_end;
				}
				if (d_beg > 0) {
					ymin = n_ymin;
					fpadding -= d_beg;
				}
			} else {
				// no, there definitely aren't enough y-terms
				int o_ymin = ymin;
				int o_ymax = ymax;
				int o_fpadding = fpadding;
				BigInteger[] o_coefficients = coefficients;
				alloc(Math.min(o_ymin, n_ymin), Math.max(o_ymax, n_ymax));
				// copy old stuff over
				for (int i = o_ymin; i <= o_ymax; ++i) {
					this.set(i, o_coefficients[(i + o_fpadding) - o_ymin]);
				}

			}
		}
	}

	private void clone(YTerms src) {
		if (src.is_empty()) {
			coefficients = null;
			ymin = 1;
			ymax = 0;
			fpadding = 0;
			bpadding = 0;
		} else {
			ymin = src.ymin;
			ymax = src.ymax;
			fpadding = src.fpadding;
			bpadding = src.bpadding;

			int ncoeffs = src.size() + src.fpadding + src.bpadding;

			coefficients = new BigInteger[ncoeffs];

			// copy old stuff over
			for (int i = 0; i < ncoeffs; ++i) {
				coefficients[i] = src.coefficients[i];
			}
		}
	}

	private void alloc(int _ymin, int _ymax) {
		int nyterms = (_ymax - _ymin) + 1;
		bpadding = nyterms * FactorPoly.FPOLY_PADDING_FACTOR;
		fpadding = Math.min(_ymin, nyterms * FactorPoly.FPOLY_PADDING_FACTOR);
		ymin = _ymin;
		ymax = _ymax;
//		System.out.println("RAWR " + nyterms + bpadding + fpadding);
		coefficients = new BigInteger[nyterms + bpadding + fpadding];
		// why the following line is needed is just plain
		// wierd.
		for (int i = 0; i < nyterms + fpadding + bpadding; ++i) {
			coefficients[i] = BigInteger.ZERO;
		}
	}

	/*
	 * bstreambuf &operator<<(bstreambuf &bout, yterms &yt) { bout << yt.ymin << yt.ymax;
	 * 
	 * for(unsigned int i=yt.ymin;i<=yt.ymax;++i) { bout << yt[i]; } }
	 * 
	 * 
	 * bistream &operator>>(bistream &bin, yterms<T> &yt) { unsigned int ymin, ymax; bin >> ymin >> ymax; if(ymin > ymax) { yt = yterms<T>(); } else {
	 * yterms<T> tmp(ymin,ymax); for(unsigned int i=tmp.ymin;i<=tmp.ymax;++i) { bin >> tmp[i]; } // again, the following trick saves on // assignment
	 * yt.swap(tmp); } }
	 */

}
