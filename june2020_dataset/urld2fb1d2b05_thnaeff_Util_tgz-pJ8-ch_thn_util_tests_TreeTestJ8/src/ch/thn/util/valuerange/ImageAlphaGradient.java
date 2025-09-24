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

/**
 * A class extending {@link ValueRange} and providing constructors which only allow
 * values which are within the range of image alpha values.
 * 
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class ImageAlphaGradient extends ValueRange {

	public static final int FADE_OUT = 0;
	public static final int FADE_IN = 1;

	public static final int ALPHA_MAX = 1;
	public static final int ALPHA_MIN = 0;

	/**
	 * A gradient prepared for the alpha values of images which range from 0 to 1.<br>
	 * This constructor can be used with the modes {@link #FADE_OUT} or {@link #FADE_IN}
	 * to create a predefined gradient which goes from a completely visible image
	 * to a completely invisible image (or from invisible to visible).
	 * 
	 * @param mode {@link #FADE_IN} or {@link #FADE_OUT}
	 * @param steps The number of gradient steps from start to end (including start and end).
	 * @param startShiftSteps A number of steps where only the start gradient value
	 * is used at the beginning of the gradient. This creates a delay for the fading
	 * to start.
	 * @param endShiftSteps A number of steps where only the start gradient value
	 * is used at the end of the gradient. This creates a delay for the end of the fading
	 * (e.g. before the next fading starts).
	 */
	public ImageAlphaGradient(int mode, int steps, int startShiftSteps, int endShiftSteps) {
		this(mode == FADE_OUT ? 1 : 0, mode == FADE_OUT ? 0 : 1, steps, startShiftSteps, endShiftSteps);
	}

	/**
	 * A gradient prepared for the alpha values of images which range from 0 to 1.<br>
	 * This constructor can be used with the modes {@link #FADE_OUT} or {@link #FADE_IN}
	 * to create a predefined gradient which goes from a completely visible image
	 * to a completely invisible image (or from invisible to visible).
	 * 
	 * @param mode {@link #FADE_IN} or {@link #FADE_OUT}
	 * @param steps The number of gradient steps from start to end (including start and end).
	 */
	public ImageAlphaGradient(int mode, int steps) {
		this(mode == FADE_OUT ? 1 : 0, mode == FADE_OUT ? 0 : 1, steps, 0, 0);
	}

	/**
	 * A gradient prepared for the alpha values of images which range from 0 to 1.<br>
	 * This constructor can be used to create a custom start and end value for the
	 * gradient. The start and end value have to be within (or equal) 0 and 1.<br>
	 * A {@link ValueRangeError} is thrown if the start or end value is out of bounds.<br />
	 * {@link #ALPHA_MAX} and {@link #ALPHA_MIN} contain the maximal and minimal
	 * possible alpha values.
	 * 
	 * @param start The starting alpha value
	 * @param end The final alpha value
	 * @param steps The number of gradient steps from start to end (including start and end).
	 * @param startShiftSteps A number of steps where only the start gradient value
	 * is used at the beginning of the gradient. This creates a delay for the fading
	 * to start.
	 * @param endShiftSteps A number of steps where only the end gradient value
	 * is used at the end of the gradient. This creates a delay for the end of the fading
	 * (e.g. before the next fading starts).
	 */
	public ImageAlphaGradient(float start, float end, int steps, int startShiftSteps, int endShiftSteps) {
		super(start, end, steps, startShiftSteps, endShiftSteps);

		if (start > 1 || start < 0) {
			throw new ValueRangeError("Start value " + start + " out of range! (must be 0<=start<=1)");
		}

		if (end > 1 || end < 0) {
			throw new ValueRangeError("End value " + end + " out of range! (must be 0<=end<=1)");
		}

	}

}
