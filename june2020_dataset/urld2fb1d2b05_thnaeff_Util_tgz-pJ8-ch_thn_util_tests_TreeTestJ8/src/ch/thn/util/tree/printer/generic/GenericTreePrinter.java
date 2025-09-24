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

/**
 * This is the abstract base class for all tree printers. It only defines the
 * methods to implement and provides some basic functionality.<br />
 * <br />
 * This class has many generic types so that implementations can be tailored to their
 * use.
 * 
 * 
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 * @param <N> The node type (parameter type of methods like {@link #print(Object)} etc.)
 * @param <V> The node value type (return type of method {@link #getNodeValues(Object)})
 * @param <O> The printer output type (return type of {@link #print(Object)} etc.)
 * @param <R> A running value type. Often the printer implementations need a value
 * passed on from parent nodes to child nodes (e.g. a prefix for text printers). The
 * type of that value/object can be defined here.
 */
public abstract class GenericTreePrinter<N, V, O, R> {

	/**
	 * Prints the tree, starting at the given node. This method creates the output
	 * of one single node (one node can have multiple lines).
	 * 
	 * @param node The node to start printing from
	 * @return
	 */
	public O print(N node) {

		List<TreePrinterLine<V>> output = createPrinterLines(node, null, 0, 1, getChildren(node).size(), 0);

		return createPrintableForm(postProcessOutput(output));

	}

	/**
	 * Generates the printer output, starting at the give node.
	 * 
	 * 
	 * @param node The node to create the output for
	 * @param runningValue A value/object which is passed on downwards. Often the
	 * printer implementations need a value passed on from parent nodes to child
	 * nodes (e.g. a prefix for text printers).
	 * @param siblingIndex The index among its siblings of the current node
	 * @param siblingCount The number of siblings of the current node
	 * @param childrenCount The number of children of the current node
	 * @param depth The depth of the current node
	 * @return A list of lines to print for the current node
	 */
	protected abstract List<TreePrinterLine<V>> createPrinterLines(N node, R runningValue,
			int siblingIndex, int siblingCount, int childrenCount, int depth);

	/**
	 * A helper method which calls the {@link #createPrinterOutput(Object, int, int, boolean, boolean)}
	 * method on all children. This method returns a list of lists. The outer list
	 * is for each child, the inner list is for each value line that a node might
	 * have.
	 * 
	 * @param node The node from which the children should be printed
	 * @param runningValue A value/object which is passed on downwards. Often the
	 * printer implementations need a value passed on from parent nodes to child
	 * nodes (e.g. a prefix for text printers).
	 * @param depth The depth of the current node
	 * @return All children with all value lines
	 */
	protected List<List<TreePrinterLine<V>>> printChildren(N node, R runningValue, int depth) {
		ArrayList<List<TreePrinterLine<V>>> printed = new ArrayList<>();

		Collection<N> children = getChildren(node);

		int childIndex = 0;

		//Print all children
		for (N child : children) {
			printed.add(createPrinterLines(
					child,
					runningValue,
					childIndex,
					children.size(),
					getChildren(child).size(),
					depth + 1));
			childIndex++;
		}

		return printed;
	}

	/**
	 * The <code>lines</code> parameter contains all created lines.<br />
	 * The post processing can be used to perform any final modifications on the
	 * printer output. This method just returns the <code>lines</code> parameter
	 * by default, not performing any other actions. Overwrite this method
	 * to do post processing.
	 * 
	 * @param lines All the created lines which should be printed
	 * @return The (possibly) modified list of printer lines
	 */
	protected List<TreePrinterLine<V>> postProcessOutput(List<TreePrinterLine<V>> lines) {
		return lines;
	}

	/**
	 * Turns the generated lines into the printer output. This is the final step
	 * of the printing, the <code>lines</code> parameter contains all created
	 * (and possibly post processed) lines.
	 * 
	 * @param lines All the created lines which should be printed
	 * @return The final printer output
	 */
	protected abstract O createPrintableForm(List<TreePrinterLine<V>> lines);

	/**
	 * A list of node values of the give node. The printer implementation defines
	 * how multiple vales are printed. For example, in a text printer each value
	 * could be interpreted as a separate line.
	 * 
	 * @param node The node from which the values should be retrieved
	 * @return The node values as collection. A <code>null</code> value can be returned
	 * to skip the printing of the current node (printer specific).
	 */
	protected abstract Collection<V> getNodeValues(N node);

	/**
	 * Gets all the child nodes of the given node.
	 * 
	 * @param node The node from which the children should be retrieved
	 * @return
	 */
	protected abstract Collection<N> getChildren(N node);

}
