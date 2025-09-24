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
package ch.thn.util.html;


/**
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class HtmlUtil {
	
	/**
	 * Puts the text between html-tags
	 * 
	 * @param text The text to put between the html-tags
	 * @return The text with html-tags
	 */
	public static String textHtml(String text) {
		return "<html><body>" + text + "</body></html>";
	}
	
	/**
	 * Puts the text between bold-tags
	 * 
	 * @param text The text to put between the bold-tags
	 * @return The text with bold-tags
	 */
	public static String textBold(String text) {
		return "<b>" + text + "</b>";
	}
	
	/**
	 * Puts the text between italic-tags
	 * 
	 * @param text The text to put between the italic-tags
	 * @return The text with italic-tags
	 */
	public static String textItalic(String text) {
		return "<i>" + text + "</i>";
	}
	
	/**
	 * Puts the text between underline-tags
	 * 
	 * @param text The text to put between the underline-tags
	 * @return The text with underline-tags
	 */
	public static String textUnderline(String text) {
		return "<u>" + text + "</u>";
	}

}
