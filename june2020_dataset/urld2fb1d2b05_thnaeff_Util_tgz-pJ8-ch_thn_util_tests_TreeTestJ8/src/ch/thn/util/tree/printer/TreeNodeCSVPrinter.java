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
import ch.thn.util.tree.printer.generic.CSVTreePrinter;
import ch.thn.util.tree.printer.generic.TreePrinterLine;

/**
 * Prints a tree as CSV text.<br />
 * See {@link CSVTreePrinter} for additional information.
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class TreeNodeCSVPrinter<N extends CollectionTreeNodeInterface<?, N>>
extends CSVTreePrinter<N> {


	public static String VALUE_SPLIT = "\n";
	public static String CSV_SEPARATOR = ";";


	/**
	 * @param alignValuesRight
	 */
	public TreeNodeCSVPrinter(boolean alignValuesRight) {
		super(alignValuesRight);


	}

	@Override
	protected Collection<String> getNodeValues(N node) {
		String[] values = null;

		if (node.getNodeValue() != null) {
			values = node.getNodeValue().toString().split(VALUE_SPLIT);
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
	protected StringBuilder createPrintableForm(List<TreePrinterLine<String>> lines) {
		/*
		 * A very simple implementation of CSV data generation. It only adds
		 * the delimiter between cells, without quoting/escaping/...
		 * 
		 */


		StringBuilder sb = new StringBuilder();

		for (TreePrinterLine<String> line : lines) {
			for (String s : line) {
				sb.append(s);
				sb.append(CSV_SEPARATOR);
			}

			sb.append(LINE_SEPARATOR);
		}


		return sb;
	}

}
