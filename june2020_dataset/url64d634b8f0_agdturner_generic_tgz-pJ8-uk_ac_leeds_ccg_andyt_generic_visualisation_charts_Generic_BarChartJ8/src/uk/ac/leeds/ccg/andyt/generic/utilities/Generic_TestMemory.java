/**
 * A component of a library for
 * <a href="http://www.geog.leeds.ac.uk/people/a.turner/projects/MoSeS">MoSeS</a>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
package uk.ac.leeds.ccg.andyt.generic.utilities;

/**
 * A class with methods that help in testing computer memory.
 */
public class Generic_TestMemory {

	protected transient Runtime _Runtime;

	/** Creates a new instance of TestMemory */
	public Generic_TestMemory() {
		init_Runtime();
	}

	/**
	 * Initialises _Runtime
	 */
	protected final void init_Runtime() {
		this._Runtime = Runtime.getRuntime();
	}

	/**
	 * For returning the total free memory
	 * <ul>
	 * <li>( _Runtime.freeMemory() + ( _Runtime.maxMemory() -
	 * _Runtime.totalMemory() ) )</li>
	 * </ul>
	 * Even if this is zero then the JVM may be able to allocate memory in
	 * reserves?
	 * 
	 * @return The TotalFreeMemory available as calculated from _Runtime. If the
	 *         returned value is lower than a minimum required to call a method
	 *         then probably best to swap some data.
	 */
	public long getTotalFreeMemory() {
		long result;
		try {
			long maxMemory = _Runtime.maxMemory();
			long allocatedMemory = _Runtime.totalMemory();
			long freeMemory = _Runtime.freeMemory();
			result = freeMemory + (maxMemory - allocatedMemory);
			return result;
		} catch (NullPointerException _NullPointerException) {
			if (_Runtime == null) {
				init_Runtime();
			}
			return getTotalFreeMemory();
		}
	}

}