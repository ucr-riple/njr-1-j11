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
package ch.thn.util.tree.printer;

import java.util.List;

import ch.thn.util.tree.printer.generic.TreePrinterLine;

/**
 * Some useful methods for the tree printer
 * 
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class TreePrinterUtil {

	public static final String RIGHT_ALIGN_CONNECTOR = "──";
	public static final String RIGHT_ALIGN_SPACE = "  ";


	/**
	 * Looks through all the lines to find the count of the most prefixes.<br />
	 * 
	 * <pre>
	 * Key-Value test	-> 0 prefixes
	 * ├─ Child 1		-> 1 prefix
	 * │  │ Test		-> 2 prefixes
	 * │  ├─ Child 1.1	-> 3 prefixes
	 * │  │    Test		-> 4 prefixes
	 * </pre>
	 * 
	 * @param lines
	 * @return
	 */
	public static int getMaxPrefixCount(List<? extends TreePrinterLine<?>> lines) {

		int maxPrefixCount = 0;
		for (TreePrinterLine<?> line : lines) {
			int prefixesCount = line.getColumnIndex(TreePrinterLine.LABEL_LAST_PREFIX) - line.getColumnIndex(TreePrinterLine.LABEL_FIRST_PREFIX);
			if (prefixesCount > maxPrefixCount) {
				maxPrefixCount = prefixesCount;
			}
		}

		return maxPrefixCount;
	}

	/**
	 * Looks through all the lines to find the count of the most values.<br />
	 * 
	 * As example, a plain text printer which prints each value in brackets []
	 * <pre>
	 * [Key-Value test]		-> 1 value
	 * ├─ [Child 1][abc]	-> 2 values
	 * │  │ [Test][def]		-> 2 values
	 * │  ├─ [Child 1.1]	-> 1 value
	 * │  │    [Test]		-> 1 value
	 * </pre>
	 * 
	 * @param lines
	 * @return
	 */
	public static int getMaxValueCount(List<? extends TreePrinterLine<?>> lines) {

		int maxValueCount = 0;
		for (TreePrinterLine<?> line : lines) {
			int valueCount = line.getColumnIndex(TreePrinterLine.LABEL_LAST_VALUE) - line.getColumnIndex(TreePrinterLine.LABEL_FIRST_VALUE);
			if (valueCount > maxValueCount) {
				maxValueCount = valueCount;
			}
		}

		return maxValueCount;
	}


	/**
	 * Looks through all the lines to find the maximal node depth.<br />
	 * 
	 * <pre>
	 * Key-Value test	-> depth 0
	 * ├─ Child 1		-> depth 1
	 * │  │ Test		-> depth 1
	 * │  ├─ Child 1.1	-> depth 2
	 * │  │    Test		-> depth 2
	 * </pre>
	 * 
	 * @param lines
	 * @return
	 */
	public static int getMaxDepth(List<? extends TreePrinterLine<?>> lines) {

		int depth = 0;
		for (TreePrinterLine<?> line : lines) {
			if (line.getDepth() > depth) {
				depth = line.getDepth();
			}
		}

		return depth;
	}


	/**
	 * Sets the index of the first label as the index of the second label. This
	 * is useful if only one index is set, but both should be present. For example
	 * the fist prefix and the last prefix in a {@link TreePrinterLine}. If only
	 * one single prefix is added to the line (with the label {@link TreePrinterLine#LABEL_FIRST_PREFIX}),
	 * there should also be the label {@link TreePrinterLine#LABEL_LAST_PREFIX}
	 * to close the prefix range.
	 * 
	 * @param line
	 * @param takeIndexOfThisLabel
	 * @param matchWithThisLabel
	 */
	public static void matchLabeledColumns(TreePrinterLine<?> line, Object takeIndexOfThisLabel, Object matchWithThisLabel) {
		line.setColumnLabel(matchWithThisLabel, line.getColumnIndex(takeIndexOfThisLabel));
	}



	/**
	 * Adds additional connectors so that all values are aligned
	 * 
	 * @param lines
	 */
	public static void alignValuesRight(List<TreePrinterLine<String>> lines) {
		//Find the maximal prefix count
		int maxPrefixCount = TreePrinterUtil.getMaxPrefixCount(lines);

		//Align so that every line has the same number of prefixes as the maximal prefix count
		for (TreePrinterLine<String> line : lines) {
			// +1 will result in an index of 0 for the tree head
			int lastPrefixIndex = line.getColumnIndex(TreePrinterLine.LABEL_LAST_PREFIX);

			int firstPrefixIndex = line.getColumnIndex(TreePrinterLine.LABEL_FIRST_PREFIX);
			int prefixCount = lastPrefixIndex - firstPrefixIndex;


			if (line.getNodeValueIndex() == 0) {
				//Add lines to connect
				for (int i = 0; i < maxPrefixCount - prefixCount; i++) {
					//Add after the last prefix
					//Since the newly added prefix is now the last prefix, increase the lastPrefixIndex
					line.addWithLabel(++lastPrefixIndex, RIGHT_ALIGN_CONNECTOR, TreePrinterLine.LABEL_LAST_PREFIX);
				}
			} else {
				//Add spaces for any additional node value lines
				for (int i = 0; i < maxPrefixCount - prefixCount; i++) {
					//Add after the last prefix
					//Since the newly added prefix is now the last prefix, increase the lastPrefixIndex
					line.addWithLabel(++lastPrefixIndex, RIGHT_ALIGN_SPACE, TreePrinterLine.LABEL_LAST_PREFIX);
				}
			}

		}
	}

}
