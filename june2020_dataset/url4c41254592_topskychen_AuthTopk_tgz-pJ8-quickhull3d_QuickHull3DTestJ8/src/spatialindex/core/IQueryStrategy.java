//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// Contact information:
//  Mailing address:
//    Marios Hadjieleftheriou
//    University of California, Riverside
//    Department of Computer Science
//    Surge Building, Room 310
//    Riverside, CA 92521
//
//  Email:
//    marioh@cs.ucr.edu

package spatialindex.core;


public interface IQueryStrategy
{
	public void getNextEntry(IEntry e, int[] nextEntry, boolean[] hasNext);
}
// below are example of IQueryStrategy

/*
	Just traverses the tree by level. Example of a Strategy pattern.
	
	class MyQueryStrategy implements IQueryStrategy
	{
	private final ArrayList<Integer> ids = new ArrayList<Integer>();
	
	@Override
	public void getNextEntry(IEntry entry, int[] nextEntry, boolean[] hasNext)
	{
		Region r = entry.getShape().getMBR();
	
		System.out.println(r.m_pLow[0] + " " + r.m_pLow[1]);
		System.out.println(r.m_pHigh[0] + " " + r.m_pLow[1]);
		System.out.println(r.m_pHigh[0] + " " + r.m_pHigh[1]);
		System.out.println(r.m_pLow[0] + " " + r.m_pHigh[1]);
		System.out.println(r.m_pLow[0] + " " + r.m_pLow[1]);
		System.out.println();
		System.out.println();
			// print node MBRs gnuplot style!
	
		// traverse only index nodes at levels 2 and higher.
		// need optimized this query step, unnecessary to load all of ids
		if (entry instanceof INode && ((INode) entry).getLevel() > 1)
		{
			for (int cChild = 0; cChild < ((INode) entry).getChildrenCount(); cChild++)
			{
				ids.add(new Integer(((INode) entry).getChildIdentifier(cChild)));
			}
		}
	
		if (! ids.isEmpty())
		{
			nextEntry[0] = (ids.remove(0)).intValue();
			hasNext[0] = true;
		}
		else
		{
			hasNext[0] = false;
		}
	}
	};
	
	// example of a Strategy pattern.
	// find the total indexed space managed by the index (the MBR of the root).
	class MyQueryStrategy2 implements IQueryStrategy
	{
	public Region m_indexedSpace;
	
	@Override
	public void getNextEntry(IEntry entry, int[] nextEntry, boolean[] hasNext)
	{
		// the first time we are called, entry points to the root.
		IShape s = entry.getShape();
		m_indexedSpace = s.getMBR();
	
		// stop after the root.
		hasNext[0] = false;
	}
	}
*/