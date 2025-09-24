/*
 *  NMerge is Copyright 2009 Desmond Schmidt
 * 
 *  This file is part of NMerge. NMerge is a Java library for merging 
 *  multiple versions into multi-version documents (MVDs), and for 
 *  reading, searching and comparing them.
 *
 *  NMerge is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  NMerge is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.luc.nmerge.graph;

import java.util.ListIterator;
import edu.luc.nmerge.graph.suffixtree.SuffixTree;

/**
 * This version of MatchThread searches the arcs to the left 
 * of the subgraph using a different technique. We don't look
 * for graph-end but only traverse arcs forwards if they are 
 * already 'printed'. We also don't go beyond the `forbidden' 
 * start node of the subgraph.
 * @author Desmond Schmidt 31/1/09
 */
public class MatchThreadTransposeLeft extends MatchThreadDirect
{
	/**
	 * Constructor for thread to search for matches
	 * @param mum the mum we have to update
	 * @param st the suffix tree representing the new version 
	 * @param a the arc to start searching from
	 * @param first the offset into a at which to start
	 * @param prevChars an array of characters preceding a[first]
	 * @param travelled the distance from the special arc on our right
	 * @param forbidden the forbidden node we mustn't cross
	 */
	MatchThreadTransposeLeft( MUM mum, SuffixTree st, Arc a, 
		int first, PrevChar[] prevChars, int travelled, Node forbidden )
	{
		super( mum, null, st, a, a.from, first, prevChars, forbidden );
		this.travelled = travelled;
	}
	/**
	 * Copy constructor for recursion
	 * @param mttl the MatchThreadTransposeLeft object to clone
	 */
	protected MatchThreadTransposeLeft( MatchThreadTransposeLeft mttl )
	{
		super( mttl );
		this.forbidden = mttl.forbidden;
	}
	/** 
	 * Move on to the next arc(s) - if you can - by recursion.
	 * This is an override of the direct alignment method.
	 */
	protected void updateArc()
	{
		// arc was fully matched - save it
		addToPath( arc );
		boolean extended = false;
		if ( arc.to != forbidden )
		{
			ListIterator<Arc> iter = arc.to.outgoingArcs();
			while ( iter.hasNext() )
			{
				Arc a = iter.next();
				if ( a.versions.intersects(versions)
					&&a.to.isPrintedOutgoing(a.versions)
					&&a.versions.nextSetBit(mum.version)!=mum.version
					&&(!a.isParent()||!a.hasChildInVersion(mum.version)) )
				{
					this.arc = a;
					this.first = 0;
					MatchThreadTransposeLeft mttl = 
						new MatchThreadTransposeLeft( this );
					mttl.run();
					extended |= mttl.first > 0;
				}
			}
		}
		if ( !extended )
			mismatch();
	}
}
