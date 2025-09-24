/**
 *    Copyright 2014 Thomas Naeff (github.com/thnaeff)
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
package ch.thn.util.tree.printer.generic;

import java.util.List;

import ch.thn.util.tree.printer.TreePrinterUtil;

/**
 * This is a abstract generic class which implements the functionality for printing
 * a tree as HTML code. By extending this class and implementing the
 * required methods, any form of tree can be printed as HTML code. The returned
 * output contains only the HTML table rows and data (without the table tags).<br />
 * <br />
 * Example output:
 * <pre>
 * 
 * </pre>
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public abstract class HTMLTreePrinter<N> extends PlainTextTreePrinter<N> {

	public static final String CLASS_LINE = "printer_line";
	public static final String CLASS_VALUE = "printer_value";

	/** HTML Colors */
	private final String colors[] = {
			//bordeaux, light green, purple, orange, magenta
			"#8A084B", "#088A08", "#4C0B5F", "#B45F04", "#086A87",
			//olive green, dark red, blue green, blue, green
			"#868A08", "#8A0808", "#0B6138", "#210B61", "#38610B"};


	private boolean useColors = true;
	private boolean alignValuesRight = false;


	/**
	 * 
	 * 
	 */
	public HTMLTreePrinter() {
		this(false, false);

	}

	/**
	 * 
	 * 
	 * @param alignValuesRight
	 * @param useColors
	 */
	public HTMLTreePrinter(boolean alignValuesRight, boolean useColors) {
		this.alignValuesRight = alignValuesRight;
		this.useColors = useColors;

	}


	/**
	 * Returns the colors to use for the tree connector
	 * 
	 * @param columnIndex
	 * @return
	 */
	private String getHTMLColor(int columnIndex) {
		return colors[columnIndex%colors.length];
	}

	@Override
	protected StringBuilder createPrintableForm(List<TreePrinterLine<String>> lines) {
		StringBuilder sb = new StringBuilder();

		if (alignValuesRight) {
			TreePrinterUtil.alignValuesRight(lines);
		}

		int longestLine = 0;
		for (TreePrinterLine<String> line : lines) {
			if (line.size() > longestLine) {
				longestLine = line.size();
			}
		}


		for (TreePrinterLine<String> line : lines) {
			//The indexes. +1 because a count is used for the loop
			int firstLineIndex = line.getColumnIndex(TreePrinterLine.LABEL_FIRST_PREFIX) + 1;
			int lastLineIndex = line.getColumnIndex(TreePrinterLine.LABEL_LAST_PREFIX) + 1;
			int firstValueIndex = line.getColumnIndex(TreePrinterLine.LABEL_FIRST_VALUE) + 1;
			int lastValueIndex = line.getColumnIndex(TreePrinterLine.LABEL_LAST_VALUE) + 1;

			sb.append("<tr>");

			String lastColor = null;
			int count = 0;
			for (String s : line) {
				count++;

				sb.append("<td");

				if (count >= firstLineIndex && count <= lastLineIndex) {
					sb.append(" class=" + CLASS_LINE);
					//Make sure all lines are aligned the same
					sb.append(" style='text-align:left;");

					if (useColors) {
						//Do not change color if there is a connector for the value alignment
						if (!s.equals(TreePrinterUtil.RIGHT_ALIGN_CONNECTOR)) {
							lastColor = getHTMLColor(count - 1);
						}

						sb.append(" color:" + lastColor + ";");
					}

					sb.append("'");
				} else if (count >= firstValueIndex && count <= lastValueIndex) {
					sb.append(" class=" + CLASS_VALUE);
				}

				if (count == line.size() && line.size() < longestLine) {
					sb.append(" colspan=");
					sb.append(longestLine - line.size() + 1);
				}

				sb.append(">");

				sb.append(s);
				sb.append("</td>");

			}

			sb.append("</tr>");
			sb.append(LINE_SEPARATOR);

		}


		return sb;
	}





	/**
	 * This just writes the style-tags and some default styles for the table, line and
	 * value class.
	 * 
	 * @param sb
	 */
	protected void appendHeaderData(StringBuilder sb) {

		sb.append("<style>" + LINE_SEPARATOR);
		//		sb.append("td {border:1px dotted black}");
		//		sb.append("td:last-child {width:100%}");	//last-child does not work in < IE8
		sb.append("." + CLASS_LINE + " {width:20px}" + LINE_SEPARATOR);
		sb.append("." + CLASS_VALUE + " {}" + LINE_SEPARATOR);
		sb.append("</style>" + LINE_SEPARATOR);

	}


	/**
	 * 
	 * 
	 * @param sb
	 * @param treeTitle
	 * @param additionalHeaders
	 */
	public void appendSimpleHeader(StringBuilder sb, String treeTitle, String... additionalHeaders) {
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">" + LINE_SEPARATOR);
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">" + LINE_SEPARATOR);
		sb.append("<head>" + LINE_SEPARATOR);
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />" + LINE_SEPARATOR);
		sb.append("<title>" + treeTitle + "</title>" + LINE_SEPARATOR);

		appendHeaderData(sb);

		for (int i = 0; i < additionalHeaders.length; i++) {
			sb.append(additionalHeaders[i]);
		}

		sb.append("</head>" + LINE_SEPARATOR);
		sb.append("<body>" + LINE_SEPARATOR);
	}

	/**
	 * 
	 * 
	 * @param sb
	 */
	public void appendSimpleFooter(StringBuilder sb) {
		sb.append("</body>" + LINE_SEPARATOR);
		sb.append("</html>" + LINE_SEPARATOR);
	}

}
