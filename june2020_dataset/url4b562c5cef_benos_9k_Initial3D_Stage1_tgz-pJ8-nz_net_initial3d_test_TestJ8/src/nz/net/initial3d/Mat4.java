package nz.net.initial3d;

import java.util.Arrays;

/**
 * <p>
 * A mutable 4 x 4 matrix. The Cloneable interface is implemented. Internally, the elements are stored row-major.
 * </p>
 * <p>
 * Unless specified otherwise, all methods will throw <code>NullPointerException</code> if called with any argument
 * equal to <code>null</code>.
 * </p>
 * 
 * @author Ben Allen
 */
public final class Mat4 implements Cloneable {

	// row-major
	private final double[] elements = new double[16];

	/**
	 * Construct a Mat4, initialised to the identity.
	 */
	public Mat4() {
		// init to identity (array already zeroed)
		elements[0] = 1;
		elements[5] = 1;
		elements[10] = 1;
		elements[15] = 1;
	}

	/**
	 * Construct a Mat4, initialised to a scalar multiple of the identity. Obviously, if the scale factor is 0, the
	 * result will be the zero matrix.
	 * 
	 * @param f
	 *            Scale factor.
	 */
	public Mat4(double f) {
		elements[0] = f;
		elements[5] = f;
		elements[10] = f;
		elements[15] = f;
	}

	/**
	 * @return An independent copy of this Mat4.
	 */
	@Override
	public Mat4 clone() {
		return new Mat4().set(this);
	}

	/**
	 * Get an element of this Mat4.
	 * 
	 * @param row
	 *            Row index.
	 * @param col
	 *            Column index.
	 * @return The element at (row, col).
	 * @throws IndexOufOfBoundsException
	 *             If <code>row < 0 || row > 3 || col < 0 || col > 3</code>.
	 */
	public double get(int row, int col) {
		if (row < 0 || row > 3 || col < 0 || col > 3) throw new IndexOutOfBoundsException();
		return elements[row * 4 + col];
	}

	/**
	 * Set an element of this Mat4.
	 * 
	 * @param row
	 *            Row index.
	 * @param col
	 *            Column index.
	 * @param val
	 *            Value to set.
	 * @return <code>this</code>
	 * @throws IndexOufOfBoundsException
	 *             If <code>row < 0 || row > 3 || col < 0 || col > 3</code>.
	 */
	public Mat4 set(int row, int col, double val) {
		if (row < 0 || row > 3 || col < 0 || col > 3) throw new IndexOutOfBoundsException();
		elements[row * 4 + col] = val;
		return this;
	}

	/**
	 * Set all the elements of this Mat4 from the corresponding elements in another Mat4.
	 * 
	 * @param m
	 *            Mat4 to copy from.
	 * @return <code>this</code>
	 */
	public Mat4 set(Mat4 m) {
		if (m == null) throw new NullPointerException();
		System.arraycopy(m.elements, 0, this.elements, 0, 16);
		return this;
	}

	/**
	 * Set this Mat4 to the zero matrix, ie set all its elements to 0.
	 * 
	 * @return <code>this</code>
	 */
	public Mat4 setZero() {
		Arrays.fill(elements, 0);
		return this;
	}

	/**
	 * Set this Mat4 to the identity matrix.
	 * 
	 * @return <code>this</code>
	 */
	public Mat4 setIden() {
		Arrays.fill(elements, 0);
		elements[0] = 1;
		elements[5] = 1;
		elements[10] = 1;
		elements[15] = 1;
		return this;
	}

	/**
	 * Swap two rows of this Mat4.
	 * 
	 * @param r0
	 *            Row index.
	 * @param r1
	 *            Row index.
	 * @return <code>this</code>
	 * @throws IndexOutOfBoundsException
	 *             If either row index is invalid.
	 */
	public Mat4 rowSwap(int r0, int r1) {
		// all indices valid if first ones are, don't need explicit bounds checking
		int i0 = r0 * 4, i1 = r1 * 4;
		double t;
		t = elements[i0];
		elements[i0++] = elements[i1];
		elements[i1++] = t;
		t = elements[i0];
		elements[i0++] = elements[i1];
		elements[i1++] = t;
		t = elements[i0];
		elements[i0++] = elements[i1];
		elements[i1++] = t;
		t = elements[i0];
		elements[i0++] = elements[i1];
		elements[i1++] = t;
		return this;
	}

	/**
	 * Add a scalar multiple of one row to another.
	 * 
	 * @param tr
	 *            Index of row to modify.
	 * @param sr
	 *            Index of source row.
	 * @param f
	 *            Scale factor.
	 * @return <code>this</code>
	 * @throws IndexOutOfBoundsException
	 *             If either row index is invalid.
	 */
	public Mat4 rowAdd(int tr, int sr, double f) {
		// all indices valid if first ones are, don't need explicit bounds checking
		int ti = tr * 4, si = sr * 4;
		elements[ti++] += elements[si++] * f;
		elements[ti++] += elements[si++] * f;
		elements[ti++] += elements[si++] * f;
		elements[ti++] += elements[si++] * f;
		return this;
	}

	/**
	 * Subtract a scalar multiple of one row from another.
	 * 
	 * @param tr
	 *            Index of row to modify.
	 * @param sr
	 *            Index of source row.
	 * @param f
	 *            Scale factor.
	 * @return <code>this</code>
	 * @throws IndexOutOfBoundsException
	 *             If either row index is invalid.
	 */
	public Mat4 rowSub(int tr, int sr, double f) {
		// all indices valid if first ones are, don't need explicit bounds checking
		int ti = tr * 4, si = sr * 4;
		elements[ti++] -= elements[si++] * f;
		elements[ti++] -= elements[si++] * f;
		elements[ti++] -= elements[si++] * f;
		elements[ti++] -= elements[si++] * f;
		return this;
	}

	/**
	 * Multiply a row by a scalar.
	 * 
	 * @param r
	 *            Index of row to modify.
	 * @param f
	 *            Scale factor.
	 * @return <code>this</code>
	 * @throws IndexOutOfBoundsException
	 *             If the row index is invalid.
	 */
	public Mat4 rowMul(int r, double f) {
		// all indices valid if first one is, don't need explicit bounds checking
		int i = r * 4;
		elements[i++] *= f;
		elements[i++] *= f;
		elements[i++] *= f;
		elements[i++] *= f;
		return this;
	}

	/**
	 * Divide a row by a scalar.
	 * 
	 * @param r
	 *            Index of row to modify.
	 * @param f
	 *            Divisor.
	 * @return <code>this</code>
	 * @throws IndexOutOfBoundsException
	 *             If the row index is invalid.
	 */
	public Mat4 rowDiv(int r, double f) {
		// all indices valid if first one is, don't need explicit bounds checking
		int i = r * 4;
		elements[i++] /= f;
		elements[i++] /= f;
		elements[i++] /= f;
		elements[i++] /= f;
		return this;
	}

	/**
	 * Add two matrices. Can be done in-place.
	 * 
	 * @param target
	 *            Mat4 to store result in.
	 * @param lhs
	 *            Left-hand-side of the addition. Will not be directly modified.
	 * @param rhs
	 *            Right-hand-side of the addition. Will not be directly modified.
	 * @return <code>target</code>
	 */
	public static Mat4 add(Mat4 target, Mat4 lhs, Mat4 rhs) {
		double[] t = target.elements;
		double[] l = lhs.elements;
		double[] r = rhs.elements;
		for (int i = 0; i < 16; i++) {
			t[i] = l[i] + r[i];
		}
		return target;
	}

	/**
	 * Add this Mat4 to another Mat4.
	 * 
	 * @param rhs
	 *            Right-hand-side of the addition.
	 * @return The sum as a new Mat4.
	 */
	public Mat4 add(Mat4 rhs) {
		return add(new Mat4(), this, rhs);
	}

	/**
	 * Add this Mat4 to another Mat4, setting this Mat4 to their sum.
	 * 
	 * @param rhs
	 *            Right-hand-side of the addition.
	 * @return <code>this</code>
	 */
	public Mat4 setAdd(Mat4 rhs) {
		return add(this, this, rhs);
	}

	/**
	 * Negate a Mat4. Can be done in-place.
	 * 
	 * @param target
	 *            Mat4 to store result in.
	 * @param source
	 *            Mat4 to negate. Will not be directly modified.
	 * @return <code>target</code>
	 */
	public static Mat4 neg(Mat4 target, Mat4 source) {
		double[] t = target.elements;
		double[] s = source.elements;
		for (int i = 0; i < 16; i++) {
			t[i] = -s[i];
		}
		return target;
	}

	/**
	 * @return The negation of this Mat4 as a new Mat4.
	 */
	public Mat4 neg() {
		return neg(new Mat4(), this);
	}

	/**
	 * Set this Mat4 to its negation.
	 * 
	 * @return <code>this</code>
	 */
	public Mat4 setNeg() {
		return neg(this, this);
	}

	/**
	 * Subtract one Mat4 from another.
	 * 
	 * @param target
	 *            Mat4 to store result in.
	 * @param lhs
	 *            Left-hand-side of the subtraction. Will not be directly modified.
	 * @param rhs
	 *            Right-hand-side of the subtraction. Will not be directly modified.
	 * @return <code>target</code>
	 */
	public static Mat4 sub(Mat4 target, Mat4 lhs, Mat4 rhs) {
		double[] t = target.elements;
		double[] l = lhs.elements;
		double[] r = rhs.elements;
		for (int i = 0; i < 16; i++) {
			t[i] = l[i] - r[i];
		}
		return target;
	}

	/**
	 * Subtract a Mat4 from this Mat4.
	 * 
	 * @param rhs
	 *            Right-hand-side of the subtraction.
	 * @return The difference as a new Mat4.
	 */
	public Mat4 sub(Mat4 rhs) {
		return sub(new Mat4(), this, rhs);
	}

	/**
	 * Subtract a Mat4 from this Mat4, setting this Mat4 to their difference.
	 * 
	 * @param rhs
	 *            Right-hand-side of the subtraction.
	 * @return <code>this</code>
	 */
	public Mat4 setSub(Mat4 rhs) {
		return sub(this, this, rhs);
	}

	/**
	 * Multiply a Mat4 by a scalar. Can be done in-place.
	 * 
	 * @param target
	 *            Mat4 to store result in.
	 * @param lhs
	 *            Mat4 to multiply. Will not be directly modified.
	 * @param rhs
	 *            Scale factor.
	 * @return <code>target</code>
	 */
	public static Mat4 mul(Mat4 target, Mat4 lhs, double rhs) {
		double[] t = target.elements;
		double[] l = lhs.elements;
		for (int i = 0; i < 16; i++) {
			t[i] = l[i] * rhs;
		}
		return target;
	}

	/**
	 * Multiply this Mat4 by a scalar.
	 * 
	 * @param rhs
	 *            Scale factor.
	 * @return The product, as a new Mat4.
	 */
	public Mat4 mul(double rhs) {
		return mul(new Mat4(), this, rhs);
	}

	/**
	 * Multiply this Mat4 by a scalar, in-place.
	 * 
	 * @param rhs
	 *            Scale factor.
	 * @return <code>this</code>
	 */
	public Mat4 setMul(double rhs) {
		return mul(this, this, rhs);
	}

	/**
	 * Mutliply two Mat4s. Cannot be done in-place.
	 * 
	 * @param target
	 *            Mat4 to store result in.
	 * @param lhs
	 *            Left-hand-side of the multiplication. Will not be directly modified.
	 * @param rhs
	 *            Right-hand-side of the multiplication. Will not be directly modified.
	 * @return <code>target</code>
	 */
	public static Mat4 mul(Mat4 target, Mat4 lhs, Mat4 rhs) {
		double[] t = target.elements;
		double[] l = lhs.elements;
		double[] r = rhs.elements;
		// unrolling ftw!
		// target row 0
		t[0] = l[0] * r[0] + l[1] * r[4] + l[2] * r[8] + l[3] * r[12];
		t[1] = l[0] * r[1] + l[1] * r[5] + l[2] * r[9] + l[3] * r[13];
		t[2] = l[0] * r[2] + l[1] * r[6] + l[2] * r[10] + l[3] * r[14];
		t[3] = l[0] * r[3] + l[1] * r[7] + l[2] * r[11] + l[3] * r[15];
		// target row 1
		t[4] = l[4] * r[0] + l[5] * r[4] + l[6] * r[8] + l[7] * r[12];
		t[5] = l[4] * r[1] + l[5] * r[5] + l[6] * r[9] + l[7] * r[13];
		t[6] = l[4] * r[2] + l[5] * r[6] + l[6] * r[10] + l[7] * r[14];
		t[7] = l[4] * r[3] + l[5] * r[7] + l[6] * r[11] + l[7] * r[15];
		// target row 2
		t[8] = l[8] * r[0] + l[9] * r[4] + l[10] * r[8] + l[11] * r[12];
		t[9] = l[8] * r[1] + l[9] * r[5] + l[10] * r[9] + l[11] * r[13];
		t[10] = l[8] * r[2] + l[9] * r[6] + l[10] * r[10] + l[11] * r[14];
		t[11] = l[8] * r[3] + l[9] * r[7] + l[10] * r[11] + l[11] * r[15];
		// target row 3
		t[12] = l[12] * r[0] + l[13] * r[4] + l[14] * r[8] + l[15] * r[12];
		t[13] = l[12] * r[1] + l[13] * r[5] + l[14] * r[9] + l[15] * r[13];
		t[14] = l[12] * r[2] + l[13] * r[6] + l[14] * r[10] + l[15] * r[14];
		t[15] = l[12] * r[3] + l[13] * r[7] + l[14] * r[11] + l[15] * r[15];
		return target;
	}

	/**
	 * Multiply this Mat4 by another.
	 * 
	 * @param rhs
	 *            Right-hand-side of the multiplication.
	 * @return The matrix product, as a new Mat4.
	 */
	public Mat4 mul(Mat4 rhs) {
		return mul(new Mat4(), this, rhs);
	}

	/**
	 * Multiply this Mat4 by another, then set this Mat4 to the matrix product.
	 * 
	 * @param rhs
	 *            Right-hand-side of the multiplication.
	 * @return <code>this</code>
	 */
	public Mat4 setMul(Mat4 rhs) {
		return set(this.mul(rhs));
	}

	/**
	 * Left-multiply a (column) Vec4 by this Mat4. The result is not homogenised.
	 * 
	 * @param rhs
	 *            Right-hand-side of the multiplication.
	 * @return The matrix product.
	 */
	public Vec4 mul(Vec4 rhs) {
		double[] l = elements;
		double x = l[0] * rhs.x + l[1] * rhs.y + l[2] * rhs.z + l[3] * rhs.w;
		double y = l[4] * rhs.x + l[5] * rhs.y + l[6] * rhs.z + l[7] * rhs.w;
		double z = l[8] * rhs.x + l[9] * rhs.y + l[10] * rhs.z + l[11] * rhs.w;
		double w = l[12] * rhs.x + l[13] * rhs.y + l[14] * rhs.z + l[15] * rhs.w;
		return new Vec4(x, y, z, w);
	}

	/**
	 * Left-multiply a (column) Vec3 by this Mat4, as though the Vec3 had homogeneous component 1, and then homogenise
	 * the result.
	 * 
	 * @param rhs
	 *            Right-hand-side of the multiplication.
	 * @return The matrix product (without its homogeneous component, which can be assumed to be 1).
	 */
	public Vec3 mul(Vec3 rhs) {
		double[] l = elements;
		// assume rhs.w == 1
		double x = l[0] * rhs.x + l[1] * rhs.y + l[2] * rhs.z + l[3];
		double y = l[4] * rhs.x + l[5] * rhs.y + l[6] * rhs.z + l[7];
		double z = l[8] * rhs.x + l[9] * rhs.y + l[10] * rhs.z + l[11];
		double w = l[12] * rhs.x + l[13] * rhs.y + l[14] * rhs.z + l[15];
		// homogenise if necessary (the epsilon is kinda arbitrary...)
		if (Math.abs(w - 1) > 0.0000001) {
			double iw = 1 / w;
			x *= iw;
			y *= iw;
			z *= iw;
		}
		return new Vec3(x, y, z);
	}

	/**
	 * @return The determinant of this Mat4.
	 */
	public double det() {
		double d = 0;
		double[] e = elements;
		// expand about first row
		d += e[0] * det3x3(e[5], e[6], e[7], e[9], e[10], e[11], e[13], e[14], e[15]);
		d -= e[1] * det3x3(e[4], e[6], e[7], e[8], e[10], e[11], e[12], e[14], e[15]);
		d += e[2] * det3x3(e[4], e[5], e[7], e[8], e[9], e[11], e[12], e[13], e[15]);
		d -= e[3] * det3x3(e[4], e[5], e[6], e[8], e[9], e[10], e[12], e[13], e[14]);
		return d;
	}

	private static double det3x3(double e00, double e01, double e02, double e10, double e11, double e12, double e20,
			double e21, double e22) {
		double d = 0;
		d += e00 * e11 * e22;
		d += e01 * e12 * e20;
		d += e02 * e10 * e21;
		d -= e00 * e12 * e21;
		d -= e01 * e10 * e22;
		d -= e02 * e11 * e20;
		return d;
	}

	/**
	 * Transpose a Mat4. Can be done in-place.
	 * 
	 * @param target
	 *            Mat4 to store result in.
	 * @param source
	 *            Mat4 to compute transpose of. Will not be directly modified.
	 * @return <code>target</code>
	 */
	public static Mat4 xpose(Mat4 target, Mat4 source) {
		double[] t = target.elements;
		double[] s = source.elements;
		double x;
		t[0] = s[0];
		x = s[4];
		t[4] = s[1];
		t[1] = x;
		x = s[8];
		t[8] = s[2];
		t[2] = x;
		x = s[12];
		t[12] = s[3];
		t[3] = x;
		t[5] = s[5];
		x = s[9];
		t[9] = s[6];
		t[6] = x;
		x = s[13];
		t[13] = s[7];
		t[7] = x;
		t[10] = s[10];
		x = s[14];
		t[14] = s[11];
		t[11] = x;
		t[15] = s[15];
		return target;
	}

	/**
	 * @return The transpose of this Mat4, as a new Mat4.
	 */
	public Mat4 xpose() {
		return xpose(new Mat4(), this);
	}

	/**
	 * Transpose this Mat4 in-place.
	 * 
	 * @return <code>this</code>
	 */
	public Mat4 setXpose() {
		return xpose(this, this);
	}

	/**
	 * Calculate the inverse of a Mat4, with gauss-jordan elimination.<br>
	 * <br>
	 * This method is not used by this class, but is here so I don't lose this version of the algorithm - Ben.
	 * 
	 * @param temp
	 *            Temporary matrix. If null, a new one will be created.
	 * @param target
	 *            Mat4 to store inverse in. May be the same object as <code>source</code>.
	 * @param source
	 *            Mat4 to invert. Will not be directly modified.
	 * @return <code>target</code>
	 * @throws IllegalStateException
	 *             If an inverse for <code>source</code> cannot be found. In this case, the state of <code>target</code>
	 *             is not defined.
	 */
	public static Mat4 invGJ(Mat4 temp, Mat4 target, Mat4 source) {
		if (temp == null) temp = new Mat4();
		temp.set(source);
		target.setIden();
		// use gauss-jordan elim on temp, applying same row ops to target
		int row = 0;
		for (int i = 0; i < 4; i++) {
			// swap rows so (row, i) is as large (in magnitude) as possible
			int swrow = row;
			double swmax = Math.abs(temp.get(row, i));
			for (int j = row + 1; j < 4; j++) {
				double t = Math.abs(temp.get(j, i));
				if (t > swmax) {
					swmax = t;
					swrow = j;
				}
			}
			if (row != swrow) {
				// actually swap the rows
				target.rowSwap(row, swrow);
				temp.rowSwap(row, swrow);
			}
			if (swmax > 0) {
				// largest abs value in this column was > 0. continue!
				// zero out rest of column
				for (int j = 0; j < 4; j++) {
					if (j != row && Math.abs(temp.get(j, i)) > 0) {
						target.rowSub(j, row, temp.get(j, i) / temp.get(row, i));
						temp.rowSub(j, row, temp.get(j, i) / temp.get(row, i));
					}
				}
				// get leading 1
				target.rowMul(row, 1 / temp.get(row, i));
				temp.rowMul(row, 1 / temp.get(row, i));
				// pivot isolated, move to next row
				row++;
			}
		}
		// after the routine above, row == the rank of the matrix
		if (row != 4) throw new IllegalStateException("Non-invertible matrix.");
		return target;
	}

	/**
	 * <p>
	 * Calculate the inverse of a Mat4, via the adjugate. Cannot be done in-place.
	 * </p>
	 * <p>
	 * Note: this algorithm is faster than gauss-jordan elimination for a 4x4 matrix.
	 * </p>
	 * 
	 * @param target
	 *            Mat4 to store inverse in.
	 * @param source
	 *            Mat4 to invert. Will not be directly modified.
	 * @return <code>target</code>
	 * @throws IllegalStateException
	 *             If an inverse for <code>source</code> cannot be found. In this case, <code>target</code> will not be
	 *             modified.
	 */
	public static Mat4 inv(Mat4 target, Mat4 source) {
		double[] s = source.elements;
		double[] t = target.elements;
		// first row of cofactors, can use for determinant
		double c00 = det3x3(s[5], s[6], s[7], s[9], s[10], s[11], s[13], s[14], s[15]);
		double c01 = -det3x3(s[4], s[6], s[7], s[8], s[10], s[11], s[12], s[14], s[15]);
		double c02 = det3x3(s[4], s[5], s[7], s[8], s[9], s[11], s[12], s[13], s[15]);
		double c03 = -det3x3(s[4], s[5], s[6], s[8], s[9], s[10], s[12], s[13], s[14]);
		// get determinant by expanding about first row
		double invdet = 1 / (s[0] * c00 + s[1] * c01 + s[2] * c02 + s[3] * c03);
		// this should catch _most_ of them...
		if (Double.isInfinite(invdet) || Double.isNaN(invdet) || invdet == 0)
			throw new IllegalStateException("Non-invertible matrix.");
		// transpose of cofactor matrix * (1 / det)
		t[0] = c00 * invdet;
		t[4] = c01 * invdet;
		t[8] = c02 * invdet;
		t[12] = c03 * invdet;
		t[1] = -det3x3(s[1], s[2], s[3], s[9], s[10], s[11], s[13], s[14], s[15]) * invdet;
		t[5] = det3x3(s[0], s[2], s[3], s[8], s[10], s[11], s[12], s[14], s[15]) * invdet;
		t[9] = -det3x3(s[0], s[1], s[3], s[8], s[9], s[11], s[12], s[13], s[15]) * invdet;
		t[13] = det3x3(s[0], s[1], s[2], s[8], s[9], s[10], s[12], s[13], s[14]) * invdet;
		t[2] = det3x3(s[1], s[2], s[3], s[5], s[6], s[7], s[13], s[14], s[15]) * invdet;
		t[6] = -det3x3(s[0], s[2], s[3], s[4], s[6], s[7], s[12], s[14], s[15]) * invdet;
		t[10] = det3x3(s[0], s[1], s[3], s[4], s[5], s[7], s[12], s[13], s[15]) * invdet;
		t[14] = -det3x3(s[0], s[1], s[2], s[4], s[5], s[6], s[12], s[13], s[14]) * invdet;
		t[3] = -det3x3(s[1], s[2], s[3], s[5], s[6], s[7], s[9], s[10], s[11]) * invdet;
		t[7] = det3x3(s[0], s[2], s[3], s[4], s[6], s[7], s[8], s[10], s[11]) * invdet;
		t[11] = -det3x3(s[0], s[1], s[3], s[4], s[5], s[7], s[8], s[9], s[11]) * invdet;
		t[15] = det3x3(s[0], s[1], s[2], s[4], s[5], s[6], s[8], s[9], s[10]) * invdet;
		return target;
	}

	/**
	 * @return The inverse of this Mat4 as a new Mat4.
	 * @throws IllegalStateException
	 *             If an inverse for this Mat4 cannot be found.
	 */
	public Mat4 inv() {
		return inv(new Mat4(), this);
	}

	/**
	 * Set this Mat4 to its inverse.
	 * 
	 * @return <code>this</code>
	 * @throws IllegalStateException
	 *             If an inverse for this Mat4 cannot be found. In this case, this Mat4 will not be modified.
	 */
	public Mat4 setInv() {
		return set(this.inv());
	}

	@Override
	public String toString() {
		double[] e = elements;
		return String.format("[%10.4f, %10.4f, %10.4f, %10.4f]\n[%10.4f, %10.4f, %10.4f, %10.4f]\n"
				+ "[%10.4f, %10.4f, %10.4f, %10.4f]\n[%10.4f, %10.4f, %10.4f, %10.4f]", e[0], e[1], e[2], e[3], e[4],
				e[5], e[6], e[7], e[8], e[9], e[10], e[11], e[12], e[13], e[14], e[15]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(elements);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Mat4 other = (Mat4) obj;
		if (!Arrays.equals(elements, other.elements)) return false;
		return true;
	}

	/**
	 * Construct a perspective projection transformation matrix.
	 * 
	 * @param near
	 *            Distance from the origin to the near plane of the view frustum.
	 * @param far
	 *            Distance from the origin to the far plane of the view frustum.
	 * @param fov
	 *            Vertical field-of-view, in radians.
	 * @param ratio
	 *            View frustum aspect ratio, as width / height.
	 * @return The transform, as a new Mat4.
	 */
	public static Mat4 perspectiveFOV(double near, double far, double fov, double ratio) {
		Mat4 m = new Mat4(0);
		double fov_ = Math.cos(fov / 2d) / Math.sin(fov / 2d);
		m.set(0, 0, fov_ / ratio);
		m.set(1, 1, fov_);
		m.set(2, 2, (far + near) / (far - near));
		m.set(2, 3, (2 * near * far) / (near - far));
		m.set(3, 2, 1);
		return m;
	}

	/**
	 * Construct a transform that shears one dimension as a function of another.
	 * 
	 * @param t_dim
	 *            Dimension to shear.
	 * @param s_dim
	 *            Dimension to shear as a function of.
	 * @param f
	 *            Shear coefficient.
	 * @return The transform, as a new Mat4.
	 */
	public static Mat4 shear(int t_dim, int s_dim, double f) {
		Mat4 m = new Mat4();
		m.set(t_dim, s_dim, f);
		return m;
	}

	/**
	 * Construct a translation transform.
	 * 
	 * @param dx
	 *            Delta x.
	 * @param dy
	 *            Delta y.
	 * @param dz
	 *            Delta z.
	 * @return The transform, as a new Mat4.
	 */
	public static Mat4 translate(double dx, double dy, double dz) {
		Mat4 m = new Mat4();
		m.set(0, 3, dx);
		m.set(1, 3, dy);
		m.set(2, 3, dz);
		return m;
	}

	/**
	 * Construct a translation transform.
	 * 
	 * @param d
	 *            Vector representing the displacement to translate by.
	 * @return The transform, as a new Mat4.
	 */
	public static Mat4 translate(Vec3 d) {
		return translate(d.x, d.y, d.z);
	}

	/**
	 * Construct a non-uniform scaling transform.
	 * 
	 * @param fx
	 *            X scale factor.
	 * @param fy
	 *            Y scale factor.
	 * @param fz
	 *            Z scale factor.
	 * @return The transform, as a new Mat4.
	 */
	public static Mat4 scale(double fx, double fy, double fz) {
		Mat4 m = new Mat4();
		m.set(0, 0, fx);
		m.set(1, 1, fy);
		m.set(2, 2, fz);
		return m;
	}

	/**
	 * Construct a non-uniform scaling transform.
	 * 
	 * @param f
	 *            Vector of scale factors.
	 * @return The transform, as a new Mat4.
	 */
	public static Mat4 scale(Vec3 f) {
		return scale(f.x, f.y, f.z);
	}

	/**
	 * Construct a uniform scaling transform.
	 * 
	 * @param f
	 *            Scale factor.
	 * @return The transform, as a new Mat4.
	 */
	public static Mat4 scale(double f) {
		return scale(f, f, f);
	}

	/**
	 * Construct a rotation transform about the x-axis. In a right-handed coordinate system, viewing along the x-axis, a
	 * positive rotation is clockwise.
	 * 
	 * @param angle
	 *            Angle to rotate, in radians.
	 * @return The transform, as a new Mat4.
	 */
	public static Mat4 rotateX(double angle) {
		Mat4 m = new Mat4();
		m.set(1, 1, Math.cos(angle));
		m.set(1, 2, -Math.sin(angle));
		m.set(2, 1, Math.sin(angle));
		m.set(2, 2, Math.cos(angle));
		return m;
	}

	/**
	 * Construct a rotation transform about the y-axis. In a right-handed coordinate system, viewing along the y-axis, a
	 * positive rotation is clockwise.
	 * 
	 * @param angle
	 *            Angle to rotate, in radians.
	 * @return The transform, as a new Mat4.
	 */
	public static Mat4 rotateY(double angle) {
		Mat4 m = new Mat4();
		m.set(0, 0, Math.cos(angle));
		m.set(0, 2, Math.sin(angle));
		m.set(2, 0, -Math.sin(angle));
		m.set(2, 2, Math.cos(angle));
		return m;
	}

	/**
	 * Construct a rotation transform about the z-axis. In a right-handed coordinate system, viewing along the z-axis, a
	 * positive rotation is clockwise.
	 * 
	 * @param angle
	 *            Angle to rotate, in radians.
	 * @return The transform, as a new Mat4.
	 */
	public static Mat4 rotateZ(double angle) {
		Mat4 m = new Mat4();
		m.set(0, 0, Math.cos(angle));
		m.set(0, 1, -Math.sin(angle));
		m.set(1, 0, Math.sin(angle));
		m.set(1, 1, Math.cos(angle));
		return m;
	}

	/**
	 * Construct the rotation transform described by a quaternion.
	 * 
	 * @param q
	 *            Rotation quaternion.
	 * @return The transform, as a new Mat4.
	 */
	public static Mat4 rotate(Quat q) {
		double x = q.x, y = q.y, z = q.z, w = q.w;
		Mat4 m = new Mat4();
		m.set(0, 0, w * w + x * x - y * y - z * z);
		m.set(1, 0, 2 * x * y + 2 * w * z);
		m.set(2, 0, 2 * x * z - 2 * w * y);
		m.set(0, 1, 2 * x * y - 2 * w * z);
		m.set(1, 1, w * w - x * x + y * y - z * z);
		m.set(2, 1, 2 * y * z + 2 * w * x);
		m.set(0, 2, 2 * x * z + 2 * w * y);
		m.set(1, 2, 2 * y * z - 2 * w * x);
		m.set(2, 2, w * w - x * x - y * y + z * z);
		m.set(3, 3, w * w + x * x + y * y + z * z);
		return m;
	}

	/**
	 * Construct the rotation transform described by a vector, where the vector's direction is the axis of rotation and
	 * its magnitude is the rotation angle (in radians).
	 * 
	 * @param r
	 *            Rotation vector.
	 * @return The transform, as a new Mat4.
	 */
	public static Mat4 rotate(Vec3 r) {
		return rotate(new Quat(r));
	}

}
