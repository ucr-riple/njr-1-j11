/**
 *    Copyright 2013 Thomas Naeff (github.com/thnaeff)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ch.thn.util.valuerange;

import java.util.ArrayList;

import ch.thn.util.NumberUtil;

/**
 * This value range class can be used to calculate the values between a <code>start</code>
 * and an <code>end</code> value, using the number of <code>steps</code> to adjust
 * the granularity of the range. In addition, the number of start and end values
 * before or after the range can be given with <code>startShiftSteps</code> or
 * <code>endShiftSteps</code>.<br>
 * <br>
 * <b>Example:</b><br>
 * Range from 0-10, with 6 steps: ValueRange(0, 10, 6, 0, 0) -> [0, 2, 4, 6, 8, 10]<br>
 * Range from 0-10, with 6 steps and 3 start and end values: ValueRange(0, 10, 6, 3, 3) -> [0, 0, 0, 0, 2, 4, 6, 8, 10, 10, 10, 10]
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ValueRange {

	public static final int TYPE_LINEAR = 0;

	private Number valueStart = 0;
	private Number valueEnd = 0;
	private Number distance = 0;
	private Number stepSize = 0;

	private ArrayList<Number> values = null;

	private int steps = 0;
	private int startShiftSteps = 0;
	private int endShiftSteps = 0;
	private int stepPos = 0;
	private final int type = TYPE_LINEAR;

	/**
	 * Range of number values.<br>
	 * The type of the generated number values depends on the start or end value,
	 * whichever has the higher "precision" (as it works with calculations. A float
	 * value multiplied by an integer value gives a result with the type float for
	 * example).
	 * 
	 * @param start The starting value
	 * @param end The final value
	 * @param steps The number of steps from start to end (including start and end).
	 */
	public ValueRange(Number start, Number end, int steps) {
		this(start, end, steps, 0, 0);
	}

	/**
	 * Range of number values.<br>
	 * The type of the generated number values depends on the start or end value,
	 * whichever has the higher "precision" (as it works with calculations. A float
	 * value multiplied by an integer value gives a result with the type float for
	 * example).
	 * 
	 * @param start The starting value
	 * @param end The final value
	 * @param steps The number of steps from start to end (including start and end).
	 * @param startShiftSteps A number of steps where only the start value
	 * is used at the beginning of the range. This creates a delay for the range
	 * to start.
	 * @param endShiftSteps A number of steps where only the end value
	 * is used at the end of the range. This creates a delay for the end of the range.
	 */
	public ValueRange(Number start, Number end, int steps, int startShiftSteps, int endShiftSteps) {
		this.valueStart = start;
		this.valueEnd = end;
		this.steps = steps;
		this.startShiftSteps = startShiftSteps;
		this.endShiftSteps = endShiftSteps;

		values = new ArrayList<Number>();

		calculate();
	}

	/**
	 * Returns all the calculated values as {@link ArrayList}
	 * 
	 * @return
	 */
	public ArrayList<Number> getValues() {
		return values;
	}

	/**
	 * Returns the calculated step size between the values
	 * 
	 * @return
	 */
	public Number getStepSize() {
		return stepSize;
	}

	/**
	 * Resets the retrieval of the values
	 */
	public void reset() {
		stepPos = 0;
	}

	/**
	 * Returns the next double value. If <code>null</code> is returned it means
	 * that the end of the gradient has been reached. Use {@link #reset()} to
	 * start at the beginning.
	 * 
	 * @return
	 */
	public Number getNext() {
		if (stepPos > values.size()) {
			return null;
		}

		return values.get(stepPos++);
	}

	/**
	 * Returns true if there is another gradient value
	 * 
	 * @return
	 */
	public boolean hasNext() {
		if (stepPos >= values.size()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Returns the number of values which have been calculated for this gradient
	 * 
	 * @return
	 */
	public int getNumberOfValues() {
		return values.size();
	}

	/**
	 * Does the calculation according to the gradient type
	 */
	private void calculate() {
		if (steps < 0) {
			//The steps have to be positive. The start and end values define the direction.
			throw new NumberFormatException("Invalid number " + steps + ". Has to be >= 0");
		}

		distance = NumberUtil.subtract(valueEnd, valueStart);
		stepSize = NumberUtil.divide(distance, steps - 1);

		values.clear();

		switch (type) {
		case TYPE_LINEAR:
			calcLinear();
			break;

		default:
			break;
		}

	}

	/**
	 * Does a linear calculation of the gradient
	 */
	private void calcLinear() {

		//Starting "delay"
		for (int i = 0; i < startShiftSteps; i++) {
			values.add(valueStart);
		}

		//The fading. One step less so that the exact end value can be added after.
		for (int i = 0; i < steps - 1; i++) {
			//Using multiplication to avoid propagating rounding errors
			values.add(NumberUtil.add(valueStart, NumberUtil.multiply(i, stepSize)));
		}

		//Makes sure the last one is exactly the end value. Rounding errors could
		//result in a slightly different end value.
		values.add(valueEnd);

		//Ending "delay"
		for (int i = 0; i < endShiftSteps; i++) {
			values.add(valueEnd);
		}

	}


	@Override
	public String toString() {
		return values.toString();
	}


}
