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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import ch.thn.util.tree.core.CollectionTreeNodeInterface;
import ch.thn.util.tree.core.MapTreeNodeInterface;
import ch.thn.util.tree.printer.generic.PlainTextTreePrinter;
import ch.thn.util.tree.printer.generic.TreePrinterLine;

/**
 * Prints a tree in plain text. Prints additional information for debugging a tree.<br />
 * <br />
 * - In front of every line, the total node index (a), the node/sibling index (b) and
 * the depth (c) are shown in the format "a/b/c"<br />
 * - If the node to print is any node extending {@link MapTreeNodeInterface}, the
 * key (k) is shown on every first line in brackets "[k]"<br />
 * <br />
 * See {@link PlainTextTreePrinter} for additional information.
 *
 *
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class TreeNodeDebugPrinter<N extends CollectionTreeNodeInterface<?, N>>
extends PlainTextTreePrinter<N> {

	public static String VALUE_SPLIT = "\n";



	@Override
	protected Collection<String> getNodeValues(N node) {
		String[] values = null;

		if (node.getNodeValue() != null) {
			//Use the node toString method
			values = node.toString().split(VALUE_SPLIT);
		} else {
			values = new String[]{""};
		}


		return Arrays.asList(values);
	}

	@Override
	protected Collection<N> getChildren(N node) {
		return node.getChildNodes();
	}

	@Override
	protected StringBuilder createPrintableForm(List<TreePrinterLine<String>> output) {
		StringBuilder sb = new StringBuilder();

		int totalNodeIndex = 0;

		for (TreePrinterLine<String> line : output) {

			if (line.getNodeValueIndex() == 0) {
				//Only show this information for the fist node value lines

				sb.append(String.format("%02d", totalNodeIndex));
				totalNodeIndex++;

				sb.append("/");
				sb.append(String.format("%02d", line.getSiblingIndex()));

				sb.append("/");
				sb.append(String.format("%02d", line.getDepth()));
			} else {
				sb.append("  ");
				sb.append("   ");
				sb.append("   ");
			}


			sb.append(" ");

			for (String s : line) {
				sb.append(s);
			}

			sb.append(LINE_SEPARATOR);
		}

		return sb;
	}

}
