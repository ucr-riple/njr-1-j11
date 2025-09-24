// Spatial Index Library
//
// Copyright (C) 2002  Navel Ltd.
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

package spatialindex.io;

import java.util.Iterator;
import java.util.Random;

/**
 * implements the add/remove method of Buffer.class
 * 
 * 
 */
public class RandomEvictionsBuffer extends Buffer
{
	Random m_random = new Random();

	public RandomEvictionsBuffer(IStorageManager sm, int capacity, boolean bWriteThrough)
	{
		super(sm, capacity, bWriteThrough);
	}

	@Override
	void addEntry(int id, Entry e)
	{
		assert m_buffer.size() <= m_capacity;

		if (m_buffer.size() == m_capacity) removeEntry();
		m_buffer.put(new Integer(id), e);
	}

	@Override
	void removeEntry()
	{
		if (m_buffer.size() == 0) return;

		int entry = m_random.nextInt(m_buffer.size());

		Iterator<java.util.Map.Entry<Integer, Entry>> it = m_buffer.entrySet()
				.iterator();
		for (int cIndex = 0; cIndex < entry - 1; cIndex++) it.next();

		java.util.Map.Entry<Integer, Entry> me = it.next();
		Entry e = me.getValue();
		int id = (me.getKey()).intValue();

		if (e.m_bDirty)
		{
			m_storageManager.storeByteArray(id, e.m_data);
		}

		m_buffer.remove(new Integer(id));
	}
} // RandomEvictionsBuffer
