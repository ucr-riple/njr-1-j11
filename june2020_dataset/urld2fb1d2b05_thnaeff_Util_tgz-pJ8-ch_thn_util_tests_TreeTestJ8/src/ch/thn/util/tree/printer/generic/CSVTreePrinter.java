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
 * a tree as CSV text form. By extending this class and implementing the
 * required methods, any form of tree can be printed as CSV text.<br />
 * <br />
 * Example output:
 * <pre>
 * ──;──;──;──;Key-Value test;
 * ├─ ;──;──;──;Child 1;
 * │  ;│ ;  ;  ;Test;
 * │  ;├─ ;──;──;Child 1.1;
 * │  ;│  ;  ;  ;Test1;
 * │  ;│  ;  ;  ;Test2;
 * │  ;│  ;  ;  ;Test3;
 * │  ;├─ ;──;──;Child 1.2;
 * ...
 * </pre>
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public abstract class CSVTreePrinter<N> extends PlainTextTreePrinter<N> {


	private boolean alignValuesRight = false;

	/**
	 * 
	 * 
	 * 
	 * @param alignValuesRight Adds cells in front of the values so that they
	 * are aligned by starting at the same column.
	 */
	public CSVTreePrinter(boolean alignValuesRight) {
		this.alignValuesRight = alignValuesRight;

		if (!alignValuesRight) {
			//No "space" (which is a column when generating CSV) in front of additional lines
			ADDITIONALLINE_CONNECTFIRST = null;
			ADDITIONALLINE_CONNECTFIRSTNOCHILD = null;
		}

	}


	@Override
	protected List<TreePrinterLine<String>> postProcessOutput(List<TreePrinterLine<String>> lines) {

		if (alignValuesRight) {
			TreePrinterUtil.alignValuesRight(lines);
		}

		return lines;
	}


	/**
	 * Turns the generated lines into the printer output. This is the final step
	 * of the printing, the lines parameter contains all created lines.<br />
	 * This method has to be implemented so that it generates a well formatted
	 * CSV output, separating each {@link TreePrinterLine} column in its own cell.
	 * 
	 * @return The CSV formatted output
	 */
	@Override
	protected abstract StringBuilder createPrintableForm(List<TreePrinterLine<String>> lines);


}
