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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.thn.util.tree.printer.TreePrinterUtil;

/**
 * This is a abstract generic class which implements the functionality for printing
 * a tree in plain text form. By extending this class and implementing the
 * required methods, any form of tree can be printed in plain text.<br />
 * <br />
 * Example output:
 * <pre>
 * Key-Value test
 * ├─ Child 1
 * │  │ Test
 * │  ├─ Child 1.1
 * │  │    Test
 * │  ├─ Child 1.2
 * │  │  │ Test
 * │  │  └─ Child 1.2.1
 * │  └─ Child 1.3
 * ├─ Child 2
 * │  │  Test
 * │  ├─ Child 2.1
 * │  ├─ Child 2.2
 * │  └─ Child 2.3
 * │       Test
 * └─ Child 3
 *    ├─ Child 3.1
 *    ├─ Child 3.2
 *    └─ Child 3.3
 *       └─ Child 3.3.1
 * </pre>
 * 
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public abstract class PlainTextTreePrinter<N> extends GenericTreePrinter<N, String, StringBuilder, List<String>> {

	/** The line separator. Default is "\r\n" so that generated files also look
	 * good on windows and other systems which require the carriage return
	 */
	public static String LINE_SEPARATOR = "\r\n";


	/** In front of the head */
	protected String HEAD = null;
	/** Space to the left of the tree under the head node */
	protected String LEFT_SPACE = null;
	/** First child line */
	protected String FIRST_CHILD = "├─ ";
	/** Last child line or last sibling */
	protected String LAST_NODE = "└─ ";
	/** Connecting from one line to the next one, if there are child lines in between */
	protected String THROUGH = "│  ";
	/** After the END has been printed, this connector is printed instead */
	protected String AFTEREND = "   ";
	/** In front of an additional line, where there are following lines */
	protected String ADDITIONALLINE_THROUGH = "│  ";
	/** In front of an additional line, where there are no child lines */
	protected String ADDITIONALLINE_AFTEREND = "   ";
	/** Connection to first line if there are multiple lines for a node */
	protected String ADDITIONALLINE_CONNECTFIRST = "│ ";
	/** If there are no children, instead of connection to first line just some space for alignment */
	protected String ADDITIONALLINE_CONNECTFIRSTNOCHILD = "  ";


	@Override
	protected List<TreePrinterLine<String>> createPrinterLines(N node, List<String> prefix,
			int siblingIndex, int siblingCount, int childrenCount, int depth) {

		List<TreePrinterLine<String>> lines = new ArrayList<>();

		Collection<String> nodeValues = getNodeValues(node);

		if (nodeValues == null) {
			//Do not print anything for the current node
			return lines;
		}

		if (prefix == null) {
			prefix = new ArrayList<>();
		}

		boolean treeHead = siblingIndex == 0 && depth == 0;
		boolean hasNextSibling = siblingIndex < siblingCount - 1;
		boolean hasChild = childrenCount > 0;

		TreePrinterLine<String> line = new TreePrinterLine<>(siblingIndex, siblingCount, 0, nodeValues.size(), childrenCount, depth);
		lines.add(line);

		//Append the prefix which has been generated when its parent has been processed.
		//This prefix connects any parent nodes with their siblings.
		if (prefix.size() > 0) {
			line.addWithLabel(prefix.get(0), TreePrinterLine.LABEL_FIRST_PREFIX);

			for (int i = 1; i < prefix.size(); i++) {
				line.add(prefix.get(i));
			}
		}

		//Append the node specific prefix. This prefix connects the current node.
		if (treeHead) {
			//Tree head
			line.addWithLabel(HEAD, TreePrinterLine.LABEL_LAST_PREFIX);
		} else if (hasNextSibling) {
			//Not the head and not the last node (because it has a next sibling)
			line.addWithLabel(FIRST_CHILD, TreePrinterLine.LABEL_LAST_PREFIX);
		} else {
			line.addWithLabel(LAST_NODE, TreePrinterLine.LABEL_LAST_PREFIX);
		}

		int nodeValueIndex = 0;

		//A value can have one or more lines. Add them all
		for (String s : nodeValues) {

			if (nodeValueIndex != 0) {
				//Additional lines

				TreePrinterLine<String> additionalLine = new TreePrinterLine<>(siblingIndex, siblingCount, nodeValueIndex, nodeValues.size(), childrenCount, depth);
				lines.add(additionalLine);

				//Any following lines need the prefix and some additional prefixes added
				//Apply the parent prefixes first.
				if (prefix.size() > 0) {
					additionalLine.addWithLabel(prefix.get(0), TreePrinterLine.LABEL_FIRST_PREFIX);

					for (int i = 1; i < prefix.size(); i++) {
						additionalLine.add(prefix.get(i));
					}
				}

				//There is more than one value line. Adjust the additional lines
				//so that they line up on the previous lines.
				//Add with prefix. The last prefix index gets updated if
				//ADDITIONALLINE_CONNECTFIRST or ADDITIONALLINE_CONNECTFIRSTNOCHILD
				//is added after.
				if (!treeHead) {
					if (hasNextSibling) {
						additionalLine.addWithLabel(ADDITIONALLINE_THROUGH, TreePrinterLine.LABEL_LAST_PREFIX);
					} else {
						additionalLine.addWithLabel(ADDITIONALLINE_AFTEREND, TreePrinterLine.LABEL_LAST_PREFIX);
					}
				}

				//Align the additional lines, either with connection of the children
				//to the very first line or just with spaces if there are no
				//children after.
				if (hasChild) {
					additionalLine.addWithLabel(ADDITIONALLINE_CONNECTFIRST, TreePrinterLine.LABEL_LAST_PREFIX);
				} else {
					additionalLine.addWithLabel(ADDITIONALLINE_CONNECTFIRSTNOCHILD, TreePrinterLine.LABEL_LAST_PREFIX);
				}

				additionalLine.addWithLabel(s, TreePrinterLine.LABEL_FIRST_VALUE);
				//Set the last value index the same as the first value index, because only one value is added per line
				TreePrinterUtil.matchLabeledColumns(additionalLine, TreePrinterLine.LABEL_FIRST_VALUE, TreePrinterLine.LABEL_LAST_VALUE);
			} else {
				//Just add the first line
				line.addWithLabel(s, TreePrinterLine.LABEL_FIRST_VALUE);
				//Set the last value index the same as the first value index, because only one value is added per line
				TreePrinterUtil.matchLabeledColumns(line, TreePrinterLine.LABEL_FIRST_VALUE, TreePrinterLine.LABEL_LAST_VALUE);
			}

			nodeValueIndex++;
		}

		//Create a new prefix list so that each branch has its own list
		prefix = new ArrayList<>(prefix);

		//Prepare prefix for children
		if (treeHead) {
			//The very first prefix defines the left space
			prefix.add(LEFT_SPACE);
		} else {
			if (hasNextSibling) {
				prefix.add(THROUGH);
			} else {
				prefix.add(AFTEREND);
			}
		}

		List<List<TreePrinterLine<String>>> printedChildren = printChildren(node, prefix, depth);
		for (List<TreePrinterLine<String>> child : printedChildren) {
			lines.addAll(child);
		}

		return lines;
	}



}
