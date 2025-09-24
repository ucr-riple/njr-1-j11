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
package ch.thn.util;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class StringUtil {

	private static final int CLIP_LEFT = 0;
	private static final int CLIP_RIGHT = 1;
	private static final int CLIP_CENTER = 2;

	private static final int CLIP_CENTER_LEFT = 0;
	private static final int CLIP_CENTER_RIGHT = 1;
	private static final int CLIP_CENTER_CENTER = 3;

	/**
	 * Special characters, from ASCII #33 to #126, without minus
	 * and underline: !"#$%&'()*+,./:;<=>?@[\]^`{|}~
	 **/
	public static String specialChars = "!\"#$%&\'()*+,./:;<=>?@[\\]^`{|}~";

	//Matches single string ranges, like a-z and A-Z in "a-zA-z"
	private static Pattern stringRangePattern = Pattern.compile(".-.");
	//Matches a range group with one or more ranges, like "[a-z]" or "[a-zA-z]"
	private static Pattern stringRangePatternGroup = Pattern.compile("\\[(.-.)+\\]");
	//Matches a whole string only if there are only pattern groups, like "[a-zA-z][0-9]" but not "[a-zA-z]abc[0-999]"
	private static Pattern stringRangePatternGroupsOnly = Pattern.compile("(\\[(.-.)+\\])*");
	//Matches the beginning or a range group with multiple ranges, like "[a-xA" in "[a-xA-X1-9]"
	private static Pattern stringRangePatternGroupStart = Pattern.compile("(\\[.-.)([^\\]])");

	/**
	 * Null-save equal check for two strings. Returns true if both are <code>null</code>,
	 * <code>false</code> if one of them is <code>null</code> and compares them otherwise
	 * using the equals method.
	 * 
	 * @param string1
	 * @param string2
	 * @return
	 */
	public static boolean equals(String string1, String string2) {
		//return (str1 == null ? str2 == null : str1.equals(str2));
		//Now uses Objects.equals from Java7
		return Objects.equals(string1, string2);
	}

	/**
	 * This method clips the string s on the right side, replacing
	 * excessive characters with "...". The length of the string can be defined
	 * by number of characters.<br>
	 * 
	 * @param s
	 * @param maxCharacterLength
	 * @return
	 */
	public static String clipStringRight(String s, int maxCharacterLength) {
		return clipString(s, maxCharacterLength, CLIP_RIGHT);
	}

	/**
	 * This method clips the string s on the left side, replacing
	 * excessive characters with "...". The length of the string can be defined
	 * by number of characters.<br>
	 * 
	 * @param s
	 * @param maxCharacterLength
	 * @return
	 */
	public static String clipStringLeft(String s, int maxCharacterLength) {
		return clipString(s, maxCharacterLength, CLIP_LEFT);
	}

	/**
	 * The core method for clipping strings with a defined character length.<br>
	 * This method clips the string s on the left or the right side, replacing
	 * excessive characters with "...". The length of the string can be defined
	 * by number of characters.<br>
	 * 
	 * @param s
	 * @param maxCharacterLength
	 * @param clipMode
	 * @return
	 */
	private static String clipString(String s, int maxCharacterLength, int clipMode) {
		if (s == null || s.length() <= maxCharacterLength) {
			return s;
		}

		if (maxCharacterLength < 0) {
			return "...";
		}

		String substitute = "...";

		if (s.length() > maxCharacterLength) {

			switch (clipMode) {
			case CLIP_LEFT:
				int start = s.length() - maxCharacterLength + substitute.length();

				if (start > s.length()) {
					start = s.length();
				}

				return substitute + s.substring(start, s.length());
			case CLIP_RIGHT:
				int end = maxCharacterLength - substitute.length();

				if (end < 0) {
					end = 0;
				}

				return s.substring(0, end) + substitute;
			default:
				throw new StringUtilError("Unknown clip mode " + clipMode);
			}
		}

		return s;

	}

	/**
	 * This method clips the string s in the center of the string, replacing
	 * excessive characters with "...". The length of the string can be defined
	 * by number of characters.<br>
	 * 
	 * @param s
	 * @param maxCharacterLength
	 * @return
	 */
	public static String clipStringCenter(String s, int maxCharacterLength) {
		return clipStringCenter(s, maxCharacterLength, CLIP_CENTER_CENTER, 0, 0, 0, 0);
	}

	/**
	 * This method clips the string s within the string, replacing excessive characters
	 * with "...". The length of the string can be defined by number of characters.<br>
	 * The clipping is shifted to the left, leaving at least leftMin
	 * and maximum leftMax characters on the left side. leftMax can be set to 0
	 * for no maximum which results in a centered clipping.
	 * 
	 * @param s
	 * @param maxCharacterLength
	 * @return
	 */
	public static String clipStringCenterLeft(String s, int maxCharacterLength, int leftMin, int leftMax) {
		if (leftMin > leftMax) {
			throw new StringUtilError("leftMin > leftMax");
		}

		if (leftMin < 0 || leftMax < 0) {
			throw new StringUtilError("leftMin < 0 or leftMax < 0");
		}

		return clipStringCenter(s, maxCharacterLength, CLIP_CENTER_LEFT, leftMin, leftMax, 0, 0);
	}

	/**
	 * This method clips the string s within the string, replacing excessive characters
	 * with "...". The length of the string can be defined by number of characters.<br>
	 * The clipping is shifted to the right, leaving at least rightMin
	 * and maximum rightMax characters on the right side. rightMax can be set to 0
	 * for no maximum which results in a centered clipping.
	 * 
	 * @param s
	 * @param maxCharacterLength
	 * @return
	 */
	public static String clipStringCenterRight(String s, int maxCharacterLength, int rightMin, int rightMax) {
		if (rightMin > rightMax) {
			throw new StringUtilError("rightMin > rightMax");
		}

		if (rightMin < 0 || rightMax < 0) {
			throw new StringUtilError("rightMin < 0 or rightMax < 0");
		}

		return clipStringCenter(s, maxCharacterLength, CLIP_CENTER_RIGHT, 0, 0, rightMin, rightMax);
	}

	/**
	 * The core method for clipping strings with a defined character length.<br>
	 * This method clips the string s within the string, replacing excessive characters
	 * with "...". The length of the string can be defined by the number of characters.<br>
	 * There are 3 modes which can be used in order to have the clipping
	 * in the center of the string (CLIP_CENTER_CENTER), shifted to the right
	 * (CLIP_CENTER_RIGHT) or shifted to the left (CLIP_CENTER_LEFT).
	 * 
	 * @param s The string to clip
	 * @param maxCharacterLength The maximum characters (including the "...")
	 * @param clipCenterMode The mode for clipping in the center
	 * @param leftMin The minimum characters on the left side, if the clipping mode
	 * is CLIP_CENTER_LEFT
	 * @param leftMax The maximum characters on the left side, if the clipping mode
	 * is CLIP_CENTER_LEFT. If set to 0, the maximum value is not used.
	 * @param rightMin The minimum characters on the right side, if the clipping mode
	 * is CLIP_CENTER_RIGHT
	 * @param rightMax The maximum characters on the right side, if the clipping mode
	 * is CLIP_CENTER_RIGHT. If set to 0, the maximum value is not used.
	 * @return The clipped string
	 */
	public static String clipStringCenter(String s, int maxCharacterLength, int clipCenterMode,
			int leftMin, int leftMax, int rightMin, int rightMax) {

		if (s == null || s.length() <= maxCharacterLength) {
			return s;
		}

		if (maxCharacterLength <= 3) {
			return "...";
		}

		String substitute = "...";

		//Get the center
		int left = (int)Math.ceil(maxCharacterLength / 2.0) - (int)Math.ceil(substitute.length() / 2.0);
		int right = s.length() - (int)Math.floor(maxCharacterLength / 2.0) + (int)Math.floor(substitute.length() / 2.0);

		//Adjust the left and right min/max bounds if necessary
		switch (clipCenterMode) {
		case CLIP_CENTER_LEFT:
			if (left < leftMin) {
				//It is too far to the left -> shift it to the right
				right += leftMin - left;
				left = leftMin;
			} else if (left > leftMax && leftMax != 0) {
				//It is too far to the right -> shift it to the left
				right -= left - leftMax;
				left = leftMax;
			}
			break;
		case CLIP_CENTER_RIGHT:
			if (s.length() - right < rightMin) {
				left -= s.length() - right - rightMin;
				right = s.length() - rightMin;
			} else if (s.length() - right > rightMax && rightMax != 0) {
				left += s.length() - right - rightMax;
				right = s.length() - rightMax;
			}
			break;
		case CLIP_CENTER_CENTER:
			//Do nothing
			break;
		default:
			throw new StringUtilError("Unknown clip center mode " + clipCenterMode);
		}

		//Adjust left and right if they are out of bounds

		if (left < 0) {
			left = 0;
		}

		if (right > s.length()) {
			right = s.length();
		}

		//Get the substrings
		return s.substring(0, left) + substitute + s.subSequence(right, s.length());

	}



	/**
	 * This method clips the string s in the center of the string, replacing
	 * excessive characters with "...". The length of the string can be defined
	 * by the actual width of the string (which depends on the used font and the
	 * graphics context).<br>
	 * 
	 * @param s
	 * @param fontMetrics
	 * @param maxWidth
	 * @return
	 */
	public static String clipStringCenter(String s, FontMetrics fontMetrics, int maxWidth) {
		return clipString(s, fontMetrics, maxWidth, CLIP_CENTER, CLIP_CENTER_CENTER, 0, 0, 0, 0);
	}

	/**
	 * This method clips the string s within the string, replacing excessive characters
	 * with "...". The length of the string can be defined
	 * by the actual width of the string (which depends on the used font and the
	 * graphics context).<br>
	 * The clipping is shifted to the left, leaving at least leftMin
	 * and maximum leftMax characters on the left side. leftMax can be set to 0
	 * for no maximum which results in a centered clipping.
	 * 
	 * @param s
	 * @param fontMetrics
	 * @param maxWidth
	 * @param leftMin
	 * @param leftMax
	 * @return
	 */
	public static String clipStringCenterLeft(String s, FontMetrics fontMetrics,
			int maxWidth, int leftMin, int leftMax) {
		if (leftMin > leftMax) {
			throw new StringUtilError("leftMin > leftMax");
		}

		if (leftMin < 0 || leftMax < 0) {
			throw new StringUtilError("leftMin < 0 or leftMax < 0");
		}

		return clipString(s, fontMetrics, maxWidth, CLIP_CENTER, CLIP_CENTER_LEFT, leftMin, leftMax, 0, 0);
	}

	/**
	 * This method clips the string s within the string, replacing excessive characters
	 * with "...". The length of the string can be defined
	 * by the actual width of the string (which depends on the used font and the
	 * graphics context).<br>
	 * The clipping is shifted to the right, leaving at least rightMin
	 * and maximum rightMax characters on the right side. rightMax can be set to 0
	 * for no maximum which results in a centered clipping.
	 * 
	 * @param s
	 * @param fontMetrics
	 * @param maxWidth
	 * @param rightMin
	 * @param rightMax
	 * @return
	 */
	public static String clipStringCenterRight(String s, FontMetrics fontMetrics,
			int maxWidth, int rightMin, int rightMax) {
		if (rightMin > rightMax) {
			throw new StringUtilError("rightMin > rightMax");
		}

		if (rightMin < 0 || rightMax < 0) {
			throw new StringUtilError("rightMin < 0 or rightMax < 0");
		}

		return clipString(s, fontMetrics, maxWidth, CLIP_CENTER, CLIP_CENTER_RIGHT, 0, 0, rightMin, rightMax);
	}

	/**
	 * This method clips the string s on the right side, replacing
	 * excessive characters with "...". The length of the string can be defined
	 * by the actual width of the string (which depends on the used font and the
	 * graphics context).<br>
	 * 
	 * @param s
	 * @param maxCharacterLength
	 * @return
	 */
	public static String clipStringRight(String s, FontMetrics fontMetrics, int maxWidth) {
		return clipString(s, fontMetrics, maxWidth, CLIP_RIGHT, 0, 0, 0, 0, 0);
	}

	/**
	 * This method clips the string s on the left side, replacing
	 * excessive characters with "...". The length of the string can be defined
	 * by the actual width of the string (which depends on the used font and the
	 * graphics context).<br>
	 * 
	 * @param s
	 * @param maxCharacterLength
	 * @return
	 */
	public static String clipStringLeft(String s, FontMetrics fontMetrics, int maxWidth) {
		return clipString(s, fontMetrics, maxWidth, CLIP_LEFT, 0, 0, 0, 0, 0);
	}

	/**
	 * The core method for clipping strings with a defined width.<br>
	 * 
	 * @param s
	 * @param fontMetrics
	 * @param maxWidth
	 * @param clipCenterMode
	 * @param leftMin
	 * @param leftMax
	 * @param rightMin
	 * @param rightMax
	 * @return
	 */
	private static String clipString(String s, FontMetrics fontMetrics, int maxWidth,
			int clipMode, int clipCenterMode, int leftMin, int leftMax, int rightMin, int rightMax) {

		int averageCharWidth = fontMetrics.stringWidth(s) / s.length();
		int numOfChars = (int)Math.floor(maxWidth / averageCharWidth);

		if (numOfChars <= 3) {
			return "...";
		}

		String str = chooseClipStringMode(s, numOfChars, clipMode,
				clipCenterMode, leftMin, leftMax, rightMin, rightMax);
		int lengthDiff = fontMetrics.stringWidth(str) - maxWidth;

		if (lengthDiff > 0) {
			//The string is too long -> characters have to be removed
			while(lengthDiff > 0) {
				numOfChars -= (int)Math.ceil((double)lengthDiff / (double)averageCharWidth);
				str = chooseClipStringMode(s, numOfChars, clipMode,
						clipCenterMode, leftMin, leftMax, rightMin, rightMax);
				lengthDiff = fontMetrics.stringWidth(str) - maxWidth;
			}
		} else if (lengthDiff + averageCharWidth < 0) {
			//The string is too short -> characters can be added (+averageCharWidth
			//to make sure adding one character does not exceed the length already)
			while(lengthDiff + averageCharWidth < 0) {
				//lengthDiff is negative -> for ceil the result needs to be positive
				numOfChars += (int)Math.ceil(-(double)(lengthDiff + averageCharWidth) / averageCharWidth);
				str = chooseClipStringMode(s, numOfChars, clipMode,
						clipCenterMode, leftMin, leftMax, rightMin, rightMax);
				lengthDiff = fontMetrics.stringWidth(str) - maxWidth;
			}
		}

		return str;
	}

	/**
	 * Just a helper method to pick the right method for clipping
	 * 
	 * @param s
	 * @param maxCharacterLength
	 * @param clipMode
	 * @param clipCenterMode
	 * @param leftMin
	 * @param leftMax
	 * @param rightMin
	 * @param rightMax
	 * @return
	 */
	private static String chooseClipStringMode(String s, int maxCharacterLength,
			int clipMode, int clipCenterMode, int leftMin, int leftMax, int rightMin, int rightMax) {

		if (clipMode == CLIP_CENTER) {
			return clipStringCenter(s, maxCharacterLength, clipCenterMode, leftMin, leftMax, rightMin, rightMax);
		} else {
			return clipString(s, maxCharacterLength, clipMode);
		}

	}

	/**
	 * 
	 * @param text
	 * @param font
	 * @param frc
	 * @return
	 */
	public static int getTextWidth(String text, Font font, FontRenderContext frc) {
		TextLayout tl = new TextLayout(text, font, frc);
		return (int)tl.getBounds().getWidth();
	}

	/**
	 * 
	 * @param text
	 * @param font
	 * @param frc
	 * @return
	 */
	public static int getTextHeight(String text, Font font, FontRenderContext frc) {
		TextLayout tl = new TextLayout(text, font, frc);
		return (int)tl.getBounds().getHeight();
	}


	/**
	 * Checks if the pattern is present in the given input string.<br>
	 * <br>
	 * This method compiles the pattern every time the
	 * method is used. To reuse a pattern for more efficiency use the
	 * method {@link #contains(Pattern, String)}
	 * 
	 * @param pattern
	 * @param input
	 * @return <code>true</code> if the pattern matches the input string at least once
	 */
	public static boolean contains(String pattern, String input) {
		return contains(Pattern.compile(pattern), input);
	}

	/**
	 * Checks if the pattern is present in the given input string.
	 * 
	 * @param pattern
	 * @param input
	 * @return <code>true</code> if the pattern is found the input string at least once
	 */
	public static boolean contains(Pattern pattern, String input) {
		return pattern.matcher(input).find();
	}

	/**
	 * Checks if the pattern is present in the given input string the given number
	 * of times<br>
	 * <br>
	 * This method compiles the pattern every time the
	 * method is used. To reuse a pattern for more efficiency use the
	 * method {@link #contains(Pattern, String)}
	 * 
	 * @param pattern
	 * @param input
	 * @param numberOfMatches
	 * @return
	 */
	public static boolean contains(String pattern, String input, int numberOfMatches) {
		return contains(Pattern.compile(pattern), input, numberOfMatches);
	}

	/**
	 * Checks if the pattern is present in the given input string the given number
	 * of times
	 * 
	 * @param pattern
	 * @param input
	 * @param numberOfMatches
	 * @return
	 */
	public static boolean contains(Pattern pattern, String input, int numberOfMatches) {
		if (numberOfMatches <= 0) {
			throw new StringUtilError("Invalid value range for numberOfMatches. Only values > 0 allowed.");
		}

		Matcher m = pattern.matcher(input);
		int count = 0;
		while (m.find()) {
			count++;

			if (count == numberOfMatches) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if the pattern matches the entire input string. It is equivalent to
	 * String.matches and it is only here to provide an easier method for
	 * {@link #matches(Pattern, String)}<br>
	 * <br>
	 * This method compiles the pattern every time the
	 * method is used. To reuse a pattern for more efficiency use the
	 * method {@link #matches(Pattern, String)}
	 * 
	 * @param pattern
	 * @param input
	 * @return
	 */
	public static boolean matches(String pattern, String input) {
		return Pattern.matches(pattern, input);
	}

	/**
	 * Checks if the pattern matches the entire input string. It is equivalent to
	 * String.matches but gives the possibility to use a precompiled pattern.
	 * 
	 * @param pattern
	 * @param input
	 * @return
	 */
	public static boolean matches(Pattern pattern, String input) {
		return pattern.matcher(input).matches();
	}

	/**
	 * Counts the number of matches in the input string.<br>
	 * <br>
	 * This method compiles the pattern every time the
	 * method is used. To reuse a pattern for more efficiency use the
	 * method {@link #matches(Pattern, String)}
	 * 
	 * @param pattern
	 * @param input
	 * @return
	 */
	public static int matchesCount(String pattern, String input) {
		return matchesCount(Pattern.compile(pattern), input);
	}

	/**
	 * Counts the number of matches in the input string.<br>
	 * <br>
	 * If you want to know if a pattern is present in a string a certain number
	 * of times, use {@link #contains(Pattern, String, int)} for better efficiency.
	 * 
	 * @param pattern
	 * @param input
	 * @return
	 */
	public static int matchesCount(Pattern pattern, String input) {
		Matcher m = pattern.matcher(input);
		int count = 0;
		while (m.find()) {
			count++;
		}
		return count;
	}

	/**
	 * Removes all occurrences of the matching pattern in the input string.<br>
	 * <br>
	 * This method compiles the pattern every time the
	 * method is used. To reuse a pattern for more efficiency use the
	 * method {@link #removeAll(Pattern, String)}
	 * 
	 * 
	 * @param pattern
	 * @param input
	 */
	public static String removeAll(String pattern, String input) {
		return removeAll(Pattern.compile(pattern), input);
	}

	/**
	 * Removes all occurrences of the matching pattern in the input string
	 * 
	 * @param pattern
	 * @param input
	 * @return The input string without any parts which matched the pattern
	 */
	public static String removeAll(Pattern pattern, String input) {
		return replaceAll(pattern, input, "", false);
	}


	/**
	 * Calls {@link #replaceAll(String, String, String, boolean)} with
	 * <code>replaceReplaced=false</code>.
	 * <br>
	 * This method compiles the pattern every time the
	 * method is used. To reuse a pattern for more efficiency use the
	 * method {@link #replaceAll(Pattern, String, String)}
	 * 
	 * @param pattern
	 * @param input
	 * @param replaceWith
	 * @return
	 */
	public static String replaceAll(String pattern, String input, String replaceWith) {
		return replaceAll(Pattern.compile(pattern), input, replaceWith, false);
	}


	/**
	 * Calls {@link #replaceAll(Pattern, String, String, boolean)} with
	 * <code>replaceReplaced=false</code>.
	 * 
	 * @param pattern
	 * @param input
	 * @param replaceWith
	 * @return
	 */
	public static String replaceAll(Pattern pattern, String input, String replaceWith) {
		return replaceAll(pattern, input, replaceWith, false);
	}

	/**
	 * Replaces all occurrences of the pattern in the input string with the given
	 * replaceWith string. It is equivalent to String.replaceAll, but gives the
	 * possibility to also look through the result of the replacement result
	 * and replace any patterns which might have been created through a previous
	 * replacement.<br>
	 * <br>
	 * This method compiles the pattern every time the
	 * method is used. To reuse a pattern for more efficiency use the
	 * method {@link #replaceAll(Pattern, String, String, boolean)}
	 * 
	 * @param pattern
	 * @param input
	 * @param replaceWith
	 * @param replaceReplaced If set to <code>true</code>, also matches which are created by the
	 * replacement are replaced, until there are no matches any more (e.g. replacing
	 * "abc" with "cba" in "abcbcxyz" needs two steps: "abcbcxyz" -> has one match
	 * -> "cbabcxyz" -> this created anothermatch -> "cbcbaxyz"). <i>Warning:</i> If
	 * this is set to <code>true</code>, an error is thrown if the pattern matches
	 * the replacement string because this would result in an infinite loop.
	 * @return The input string whose pattern matches are replaced with the replaceWith
	 * string
	 * @throws StringUtilError If <code>replaceReplaced=true</code> and the given
	 * pattern matches the replacement string (becuase an infinite loop would occur).
	 */
	public static String replaceAll(String pattern, String input, String replaceWith,
			boolean replaceReplaced) {
		return replaceAll(Pattern.compile(pattern), input, replaceWith, replaceReplaced);
	}

	/**
	 * Replaces all occurrences of the pattern in the input string with the given
	 * replaceWith string. It is equivalent to String.replaceAll, but gives the
	 * possibility to also look through the result of the replacement result
	 * and replace any patterns which might have been created through a previous
	 * replacement.<br>
	 * 
	 * @param pattern
	 * @param input
	 * @param replaceWith
	 * @param replaceReplaced If set to <code>true</code>, also matches which are created by the
	 * replacement are replaced, until there are no matches any more (e.g. replacing
	 * "abc" with "cba" in "abcbcxyz" needs two steps: "abcbcxyz" -> has one match
	 * -> "cbabcxyz" -> this created anothermatch -> "cbcbaxyz"). <i>Warning:</i> If
	 * this is set to <code>true</code>, an error is thrown if the pattern matches
	 * the replacement string because this would result in an infinite loop.
	 * @return The input string whose pattern matches are replaced with the replaceWith
	 * string
	 */
	public static String replaceAll(Pattern pattern, String input, String replaceWith,
			boolean replaceReplaced) {
		if (replaceReplaced) {
			Matcher m = pattern.matcher(input);
			String ret = input;
			while (m.find()) {
				ret = m.replaceAll(replaceWith);
				m = pattern.matcher(ret);

				//If the pattern matches the replacement string, an infinite loop
				//would be created -> show error
				if (contains(pattern, replaceWith)) {
					throw new StringUtilError("Pattern '" + pattern.toString() + "' matches the replacement string '" + replaceWith + "' which would create an infinite loop when using replaceReplaced=true.");
				}
			}
			return ret;
		} else {
			Matcher m = pattern.matcher(input);
			if (m.find()) {
				return m.replaceAll(replaceWith);
			} else {
				return input;
			}
		}
	}

	/**
	 * Returns the string which matches the given pattern. If there are multiple
	 * matches in the input string, multiple matches are given in the returned
	 * list.<br>
	 * <br>
	 * This method compiles the pattern every time the
	 * method is used. To reuse a pattern for more efficiency use the
	 * method {@link #getMatching(Pattern, String)}
	 * 
	 * @param pattern
	 * @param input
	 * @return
	 */
	public static LinkedList<String> getMatching(String pattern, String input) {
		return getMatching(Pattern.compile(pattern), input);
	}

	/**
	 * Returns all the strings in the input which match the given pattern.
	 * 
	 * @param pattern
	 * @param input
	 * @return
	 */
	public static LinkedList<String> getMatching(Pattern pattern, String input) {
		return getMatching(pattern, input, 0);
	}

	/**
	 * Returns the first string in the input which match the given pattern.
	 * <br>
	 * This method compiles the pattern every time the
	 * method is used. To reuse a pattern for more efficiency use the
	 * method {@link #getMatchingFirst(Pattern, String)}
	 * 
	 * @param pattern
	 * @param input
	 * @return
	 */
	public static String getMatchingFirst(String pattern, String input) {
		return getMatchingFirst(Pattern.compile(pattern), input);
	}

	/**
	 * Returns the first string in the input which match the given pattern.
	 * 
	 * @param pattern
	 * @param input
	 * @return
	 */
	public static String getMatchingFirst(Pattern pattern, String input) {
		LinkedList<String> l = getMatching(pattern, input, 1);
		if (l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Returns all the strings in the input which match the given pattern, but
	 * stops looking for more matches when <code>numberOfMatching</code> is reached.
	 * If <code>numberOfMatching=0</code>, it returns all the matches.
	 * 
	 * @param pattern
	 * @param input
	 * @param numberOfMatching
	 * @return
	 */
	private static LinkedList<String> getMatching(Pattern pattern, String input, int numberOfMatching) {
		LinkedList<String> l = new LinkedList<String>();
		Matcher m = pattern.matcher(input);

		if (numberOfMatching == 0) {
			//Return all the matches

			while (m.find()) {
				if (m.group().length() == 0) {
					continue;
				}

				l.add(m.group());
			}
		} else {
			while (m.find() && l.size() < numberOfMatching) {
				if (m.group().length() == 0) {
					continue;
				}

				l.add(m.group());
			}
		}

		return l;
	}

	/**
	 * Extracts all the "ranges" out of the given string. A range has a start and
	 * an end character, enclosed in [] and separated by "-" (e.g. "abc[a-z]def"
	 * has one range "a-z"). There can also be range groups like "[a-zA-Z0-9]".
	 * 
	 * @param rangesString An input string like "a-z" or "a-zA-Z0-9#-/"
	 * @return
	 */
	public static LinkedList<String> extractRanges(String rangesString) {
		LinkedList<String> groups = getMatching(stringRangePatternGroup, rangesString);
		LinkedList<String> ranges = new LinkedList<String>();
		for (String rangeGroup : groups) {
			ranges.addAll(getMatching(stringRangePattern, rangeGroup));
		}
		return ranges;
	}

	/**
	 * Builds a string out of one single range. A range can be increasing or decreasing (reverse),
	 * thus [a-z] will go from a to z and [z-a] will go from z to a.<br>
	 * <br>
	 * For multiple ranges, use {@link #buildRangesString(String)}.<br>
	 * For one or multiple ranges within a string, use {@link #replaceStringRanges(String)}.
	 * 
	 * @param rangesString An input string like "a-z" or "[#-/]", with or without
	 * the enclosing brackets []
	 * @return
	 */
	public static String buildRangeString(String rangeString) {
		//Get rid of the enclosing brackets if there are any
		if (rangeString != null && rangeString.length() == 5) {
			rangeString = rangeString.substring(1);
			rangeString = rangeString.substring(0, 3);
		}

		if (rangeString == null || !matches(stringRangePattern, rangeString)) {
			throw new StringUtilError("Invalid range string '" + rangeString +
					"'. A range string can only have 3 characters (plus the opening " +
					"and closing brackets [ and ]), e.g. a-z, [A-Z] etc.");
		}

		int start = rangeString.charAt(0);
		int end = rangeString.charAt(2);
		boolean reverse = start > end;

		//The output string will have the size of all the including start
		//and end characters
		StringBuilder sb = null;

		if (reverse) {
			sb = new StringBuilder(start - end + 1);
			for (int i = start; i >= end; i--) {
				sb.append((char)i);
			}
		} else {
			sb = new StringBuilder(end - start + 1);
			for (int i = start; i <= end; i++) {
				sb.append((char)i);
			}
		}

		return sb.toString();
	}

	/**
	 * Builds the string with all the given ranges. The given string can only
	 * contain ranges (single ranges or groups), like [a-zA-z][0-9] etc. and
	 * must include the opening and closing brackets []
	 * 
	 * @return
	 */
	public static String buildRangesString(String rangesString) {
		if (rangesString == null || !matches(stringRangePatternGroupsOnly, rangesString)) {
			throw new StringUtilError("Ranges string does not only contain ranges. A range needs to be between opening and closing brackets [].");
		}

		LinkedList<String> ranges = extractRanges(rangesString);

		//<range, built range>
		HashMap<String, String> builtRanges = new HashMap<String, String>();

		//Build all ranges
		for (String range : ranges) {
			//Do not build it again if already built
			if (!builtRanges.containsKey(range)) {
				builtRanges.put(range, buildRangeString(range));
			}
		}

		StringBuilder sb = new StringBuilder(ranges.size() * 2);

		for (String range : ranges) {
			sb.append(builtRanges.get(range));
		}

		return sb.toString();
	}

	/**
	 * Builds a map with all the ranges which are within the rangesString
	 * 
	 * @param rangesString
	 * @return
	 */
	public static HashMap<String, String> buildStringRanges(String rangesString) {
		LinkedList<String> ranges = extractRanges(rangesString);

		//<range, built range>
		HashMap<String, String> builtRanges = new HashMap<String, String>();

		//Build all ranges
		for (String range : ranges) {
			//Do not build it again if already built
			if (!builtRanges.containsKey(range)) {
				builtRanges.put(range, buildRangeString(range));
			}
		}

		return builtRanges;
	}

	/**
	 * Looks for all the ranges within the given string, builds the ranges and
	 * replaces the range-strings [a-z] etc. with the built range.<br>
	 * See {@link #buildRangeString(String)} for more information about allowed
	 * ranges and their format.
	 * 
	 * @param rangesString
	 * @return
	 */
	public static String replaceStringRanges(String rangesString) {
		HashMap<String, String> builtRanges = buildStringRanges(rangesString);

		//Convert all the group ranges to single ranges. This is necessary for
		//the later replacement of the ranges.
		rangesString = replaceAll(stringRangePatternGroupStart, rangesString, "$1\\]\\[$2", true);

		String output = rangesString;

		Iterator<String> iterator = builtRanges.keySet().iterator();

		//Replace all ranges (a-z, A-Z, etc.) with their built ranges
		while (iterator.hasNext()) {
			String range = iterator.next();
			output = output.replaceAll(Pattern.quote("[" + range + "]"),
					Matcher.quoteReplacement(builtRanges.get(range)));
		}

		return output;
	}

	/**
	 * Generates a random string with the given length, the numbers 0-9 and the
	 * letters A-Z and a-z. If needed, special characters can be included.
	 * 
	 * @param len
	 * @param includeSpecialCharacters
	 * @return
	 */
	public static String randomString(int len, boolean includeSpecialCharacters) {
		return randomString(len, true, true, true, false,
				includeSpecialCharacters, includeSpecialCharacters, includeSpecialCharacters);
	}

	/**
	 * Generates a random string with the given length and the selected characters
	 * 
	 * @param len The character length of the random string
	 * @param numbers All numbers 0-9
	 * @param uppercase All uppercase Characters A-Z
	 * @param lowercase All lowercase characters a-z
	 * @param space The space character
	 * @param minus The minus character: -
	 * @param underline The underline character: _
	 * @param specialChars Special characters, from ASCII #33 to #126, without minus
	 * and underline: !"#$%&'()*+,./:;<=>?@[\]^`{|}~
	 * @return
	 */
	public static String randomString(int len, boolean numbers,
			boolean uppercase, boolean lowercase, boolean space,
			boolean minus, boolean underline, boolean specialChars) {

		StringBuilder sb = new StringBuilder();

		if (numbers) {
			sb.append("0123456789");
		}

		if (uppercase) {
			sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		}

		if (lowercase) {
			sb.append("abcdefghijklmnopqrstuvwxyz");
		}

		if (space) {
			sb.append(" ");
		}

		if (minus) {
			sb.append("-");
		}

		if (underline) {
			sb.append("_");
		}

		return randomString(sb.toString(), len, specialChars);
	}

	/**
	 * Generates a random string with the given length and the characters
	 * given in the characterString. If needed, the special characters from
	 * ASCII #33 to #126 can be included (defined in {@link #specialChars}).
	 * 
	 * @param characterString
	 * @param len
	 * @param includeSpecialCharacters
	 * @return
	 */
	public static String randomString(String characterString, int len, boolean includeSpecialCharacters) {
		if (characterString == null || characterString.length() == 0 || len < 0) {
			return null;
		}

		Random random = new Random();
		int nextInt = 0;
		StringBuilder sb = new StringBuilder(len);

		if (includeSpecialCharacters) {
			characterString = characterString + StringUtil.specialChars;
		}

		for(int i = 0; i < len; i++) {
			nextInt = random.nextInt(characterString.length());
			sb.append(characterString.substring(nextInt, nextInt + 1));
		}

		return sb.toString();

	}


	/**
	 * Adds the given leadingTrailing string to the beginning or the end of the
	 * given text, as many times as specified with leading/trailing.
	 * 
	 * @param text
	 * @param leadingTrailing
	 * @param leading
	 * @param trailing
	 * @return
	 */
	public static String leadingTrailing(String text, String leadingTrailing, int leading, int trailing) {
		if (leading <= 0 || trailing <= 0 || leadingTrailing == null) {
			return text;
		}

		int size = leading + trailing + (text != null ? text.length() : 0);

		if (size < 0) {
			//Integer value overflow
			size = leading > trailing ? leading : trailing;
		}

		StringBuilder sb = new StringBuilder(size);

		for (int i = 0; i < leading; i++) {
			sb.append(leadingTrailing);
		}

		sb.append(text);

		for (int i = 0; i < trailing; i++) {
			sb.append(leadingTrailing);
		}

		return sb.toString();
	}


	/**
	 * Adds the string s to itself repeatedly as many times as specified with count.<br>
	 * s = "x", count = 5 -> result = "xxxxx"
	 * 
	 * @param s
	 * @param count
	 * @return
	 */
	public static String stringString(String s, int count) {
		if (s == null || count < 0) {
			return null;
		}

		StringBuilder sb = new StringBuilder(count);

		for (int i = 0; i < count; i++) {
			sb.append(s);
		}

		return sb.toString();
	}

	/**
	 * Simple search and replace in a string builder.<br>
	 * Replaces all occurrences of <code>search</code> in <code>sb</code>
	 * with <code>replace</code>
	 * 
	 * @param sb
	 * @param search
	 * @param replace
	 */
	public static void replaceAll(StringBuilder sb, String search, String replace) {
		replaceAll(sb, search, replace, false);
	}

	/**
	 * Simple search and replace in a string builder.<br>
	 * Replaces all occurrences of <code>search</code> in <code>sb</code>
	 * with <code>replace</code><br>
	 * Choose to start at the front or at the tail with the search.
	 * 
	 * @param sb
	 * @param search
	 * @param replace
	 * @param startWithTail
	 */
	public static void replaceAll(StringBuilder sb, String search, String replace,
			boolean startWithTail) {

		int index = 0;
		int start = 0;
		int end = 0;

		while (index < sb.length()) {

			if (startWithTail) {
				start = sb.lastIndexOf(search);
			} else {
				start = sb.indexOf(search, index);
			}

			if (start == -1) {
				//No more occurrences
				break;
			}

			end = start + search.length();

			sb.replace(start, end, replace);
			index = start + replace.length();
		}


		//		//Could also be done like this. Faster? Probably depends...
		//		Pattern p = Pattern.compile("cat");
		//		Matcher m = p.matcher("one cat two cats in the yard");
		//		StringBuffer sb = new StringBuffer();
		//		while (m.find()) {
		//			m.appendReplacement(sb, "dog");
		//		}
		//		m.appendTail(sb);
		//		System.out.println(sb.toString());

	}


	/**
	 * Returns the stack trace as string of the given throwable
	 * 
	 * @param e
	 * @return
	 */
	public static String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

}
