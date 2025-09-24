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
import java.util.HashMap;

/**
 * This is a single printer line. A line contains all the data which should be printed
 * and it also contains additional information about the node or data being printed,
 * for example the sibling index, children count, depth, etc.<br />
 * <br />
 * A single line is divided into columns with all prefixes and values in separate
 * columns. This makes it easier to modify the printer output before printing because
 * the data is clearly separated.<br />
 * Columns can be labeled when they are added so that their content and index
 * can be retrieved later through that label.
 * 
 * <pre>
 * #Key-Value test#	-> one column: #LABEL_FIRST_VALUE#
 * #├─ #Child 1#	-> two columns: #LABEL_FIRST_PREFIX#LABEL_FIRST_VALUE#
 * #│  #│ #Test#	-> three columns: #LABEL_FIRST_PREFIX#LABEL_LAST_PREFIX#LABEL_FIRST_VALUE#
 * #│  #├─ #Child 1.1#	-> three columns: #LABEL_FIRST_PREFIX#LABEL_LAST_PREFIX#LABEL_FIRST_VALUE#
 * </pre>
 *
 *<br />
 * <code>null</code> values are ignored when adding them. add-methods which return
 * a boolean value return <code>false</code> when passing <code>null</code> as element.
 * <br />
 * <br />
 * 
 * 
 * 
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class TreePrinterLine<V> extends ArrayList<V> {
	private static final long serialVersionUID = 3889113250460759197L;

	/** The first value column */
	public static final String LABEL_FIRST_VALUE = "_first_value_";
	/** The last value column (should be set as the same as the first value column if there is only one value) */
	public static final String LABEL_LAST_VALUE = "_last_value_";
	/** The first prefix column */
	public static final String LABEL_FIRST_PREFIX = "_first_prefix_";
	/** The last prefix column (should be set as the same as the prefix column if there is only one prefix) */
	public static final String LABEL_LAST_PREFIX = "_last_prefix_";

	private int siblingIndex = 0;
	private int siblingCount = 0;
	private int nodeValueIndex = 0;
	private int nodeValueCount = 0;
	private int childrenCount = 0;
	private int depth = 0;


	/**
	 * Maps the label to its column index. The label can be anything, a String or
	 * any object.<br />
	 * <br />
	 * &lt;label, column index&gt;
	 */
	private HashMap<Object, Integer> columnLabels = null;


	/**
	 * 
	 * 
	 * @param siblingIndex
	 * @param siblingCount
	 * @param nodeValueIndex
	 * @param nodeValueCount
	 * @param childrenCount
	 * @param depth
	 */
	public TreePrinterLine(int siblingIndex, int siblingCount, int nodeValueIndex, int nodeValueCount,
			int childrenCount, int depth) {

		super();

		this.siblingIndex = siblingIndex;
		this.siblingCount = siblingCount;
		this.nodeValueIndex = nodeValueIndex;
		this.nodeValueCount = nodeValueCount;
		this.childrenCount = childrenCount;
		this.depth = depth;

		columnLabels = new HashMap<>();

	}

	/**
	 * The index of this node among its siblings
	 * 
	 * @return
	 */
	public int getSiblingIndex() {
		return siblingIndex;
	}

	/**
	 * The number of siblings under this nodes parent
	 * 
	 * @return
	 */
	public int getSiblingCount() {
		return siblingCount;
	}

	/**
	 * For a node which has multiple node values, this is the index of the value
	 * 
	 * @return
	 */
	public int getNodeValueIndex() {
		return nodeValueIndex;
	}

	/**
	 * The number of node values (value lines under one node)
	 * 
	 * @return
	 */
	public int getNodeValueCount() {
		return nodeValueCount;
	}

	/**
	 * The number of children under this node
	 * 
	 * @return
	 */
	public int getChildrenCount() {
		return childrenCount;
	}

	/**
	 * The depth of this node, relative to the first printed node (head node)
	 * 
	 * @return
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Adds a new value at the end of the columns. A label can be defined to
	 * later retrieve that column value with the given label.<br />
	 * If the given label already exists, the column index is updated to the
	 * column of the newly added element.
	 * 
	 * @param element The column element to add
	 * @param label A label for the added column value
	 * @return <code>true</code> if the columns changed by adding a new item
	 */
	public boolean addWithLabel(V element, Object label) {
		if (element == null) {
			return false;
		}

		boolean ret = super.add(element);

		columnLabels.put(label, size()-1);

		return ret;
	}

	/**
	 * Adds a new value at the given index of the columns. A label can be defined to
	 * later retrieve that column value with the given label.<br />
	 * If the given label already exists, the column index is updated to the
	 * column of the newly added element.
	 * 
	 * @param index The index where the new element should be added
	 * @param element The column element to add
	 * @param label A label for the added column value
	 */
	public void addWithLabel(int index, V element, Object label) {
		if (element == null) {
			return;
		}

		//Increase any label indexes which are after the inserted element
		increaseLabelIndex(index);

		super.add(index, element);

		columnLabels.put(label, index);

	}

	@Override
	public void add(int index, V element) {
		if (element == null) {
			return;
		}

		//Increase any label indexes which are after the inserted element
		increaseLabelIndex(index);

		super.add(index, element);
	}

	@Override
	public boolean add(V e) {
		if (e == null) {
			return false;
		}

		return super.add(e);
	}

	/**
	 * Increases all label indexes which are greater/equal than the given parameter
	 * <code>afterIndex</code>.
	 * 
	 * @param afterIndex
	 */
	private void increaseLabelIndex(int afterIndex) {

		for (Object o : columnLabels.keySet()) {
			if (columnLabels.get(o) >= afterIndex) {
				columnLabels.put(o, columnLabels.get(o) + 1);
			}

		}

	}

	/**
	 * Retrieves the column element which has been added with the given label.
	 * 
	 * @param label
	 * @return The column element with the given label, or <code>null</code> if
	 * the given label does not exist of if the column does not exist any more.
	 */
	public V getWithLabel(Object label) {
		if (!columnLabels.containsKey(label)) {
			return null;
		}
		return super.get(columnLabels.get(label));
	}

	/**
	 * Returns the column index for the given label (if the given label has
	 * been defined when adding a column element).
	 * 
	 * @param label
	 * @return The column index for the given label, or -1 if the given label has
	 * not been defined.
	 */
	public int getColumnIndex(Object label) {
		if (!columnLabels.containsKey(label)) {
			return -1;
		}

		return columnLabels.get(label);
	}

	/**
	 * Sets a label for the given column index.<br />
	 * If the given label already exists, the column index is updated..
	 * 
	 * @param columnIndex
	 * @return
	 */
	public void setColumnLabel(Object label, int columnIndex) {
		columnLabels.put(label, columnIndex);
	}

}
