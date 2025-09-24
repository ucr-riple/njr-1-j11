/*   Skandium: A Java(TM) based parallel skeleton library.
 *   
 *   Copyright (C) 2009 NIC Labs, Universidad de Chile.
 * 
 *   Skandium is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Skandium is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.

 *   You should have received a copy of the GNU General Public License
 *   along with Skandium.  If not, see <http://www.gnu.org/licenses/>.
 */
package concurrent.example;

import cl.niclabs.skandium.muscles.Execute;

/**
 * <p>
 * This class computes Pi decimals for a given interval, using
 * Bailey–Borwein–Plouffe (BPP) formula, using custom data types (BigDecimal).
 * </p>
 * 
 * <p>
 * Note that the bigger the scale the slower operations are for BigDecimal
 * types.
 * </p>
 * 
 * <p>
 * TODO: This class should be re-implemented without BigDecimal to compute
 * hexadecimal values of Pi's "decimals".
 * </p>
 * 
 * @author mleyton
 * 
 */
public class ExecuteIntegralCalculation implements Execute<Integer, Double> {

	int parts;
	int size;

	public ExecuteIntegralCalculation(int parts, int size) {
		this.parts = parts;
		this.size = size;
	}

	@Override
	public Double execute(Integer rank) {
		return new Double(trapezoid(this.size, rank.intValue(), this.parts));
	}

	public static double f(double x) {
		return 4.0 / (1.0 + x * x);
	}

	public static double trapezoid(int intervalle, int rank, int sub_trapezoide) {
		double left_bound = 0.0;
		double right_bound = 1.0;
		double width = (right_bound - left_bound)
				/ (sub_trapezoide * intervalle);
		double start = left_bound + rank * sub_trapezoide * width;
		double finish = start + sub_trapezoide * width;
		double integral = (f(start) + f(finish)) / 2.0;

		double x = start;
		for (int i = 1; i < sub_trapezoide; i++) {
			x += width;
			integral += f(x);
		}
		return integral * width;
	}
}