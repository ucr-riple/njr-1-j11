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
import java.util.Vector;
import edu.luc.nmerge.exception.*;
/**
 * Define a match between the graph and the special arc. A match is defined 
 * by a start node, an offset in the path following that node, a length and 
 * a version to follow to get to the offset and to follow throughout the match. 
 * The start node is usually the one immediately preceding the arc in which 
 * the match occurs, although this cannot be guaranteed because it may be split 
 * subsequently by some other alignment. This formulation of a match 
 * insulates it from the addition of nodes to the graph and hence avoids 
 * expensive MUM recalculation.
 * @author Desmond Schmidt 12/1/09
 */
public class Match
{
	/** the start offset in the first element of path */
	int graphOffset;
	/** offset within arc where the alignment starts */
	int dataOffset;
	/** the length of the alignment in bytes */
	int length;
	/** the data of the match */
	char[] data;
	/** the version to follow to locate the match*/
	short version;
	/** The Node to start the match from */
	Node start;
	/** the match path as an array of successive arcs */
	Arc[] path;
	/** the frequency of the match */
	int freq;
	/** the Adler32 modulus */
	static int MOD_ADLER = 65521;
	/**
	 * Construct a Match, which is ignorant about how to interpret this data. 
	 * It just stores it for future use in context.
	 * @param start the node from which to count
	 * @param graphOffset the first offset in the first path component
	 * @param version the version to follow to locate the match
	 * @param dataOffset the offset in the special arc
	 * @param length the length of the match
	 * @param data all the data of the special arc
	 */
	Match( Node start, int graphOffset, short version, int dataOffset, 
		int length, char[] data )
	{
		this.graphOffset = graphOffset;
		this.dataOffset = dataOffset;
		this.version = version;
		assert version >= 0;
		this.length = length;
		this.data = new char[length];
		this.start = start;
		this.start.addMatch( this );
		this.freq = 1;
		for ( int i=0,j=dataOffset;i<length;i++,j++ )
			this.data[i] = data[j];
	}
	/**
	 * Set start to a value. Needed when we merge two nodes 
	 * during alignMerge.
	 * @param start the new value of start
	 */
	public void setStart( Node start )
	{
		this.start = start;
	}
	/**
	 * Do we represent the same data?
	 * @return true if we do
	 */
	public boolean equals( Object other )
	{
		Match m = ((Match)other);
		if ( m.length == this.length 
			&& this.data != null 
			&& ((Match)other).data != null )
		{
			for ( int i=0;i<length;i++ )
			{
				if ( m.data[i] != this.data[i] )
					return false;
			}
			return true;
		}
		return false;
	}
	/**
	 * Add the given version to each arc in the path
	 * @param version the version to OR with the path
	 */
	void addVersion( int version ) throws MVDException
	{
		Arc[] path = getMatchPath();
		for ( int i=0;i<path.length;i++ )
			path[i].addVersion( version );
	}
	/**
	 * We need this for debugging
	 * (different from MergeTester but only used for debugging)
	 */
	public String toString()
	{
		String dataStr = new String( data );
		int offset = graphOffset+dataOffset;
		return "Version: "+version+" offset="+offset+" length="+length+" data="+dataStr;
	}
	/**
	 * This is required by the HashMap to test for equality of keys.
	 * Match objects with the same data are equal
	 * @return the hashcode value
	 */
	public int hashCode()
	{
		int a = 1, b = 0, i = 0;
		int len = data.length;
	    while ( len > 0 ) 
	    {
	        int tlen = len > 5552 ? 5552 : len;
	        len -= tlen;
	        do 
	        {
	            a += data[i++];
	            b += a;
	        } while (--tlen > 0 );
	        a %= MOD_ADLER;
	        b %= MOD_ADLER;
		 }
		 return (b << 16) | a;
	}
	/** 
	 * Separate out the path from the graph by adding new nodes 
	 * as required. If already split off just returns the path.
	 * Note that we first split all the arcs as required, then 
	 * retraverse the path to put the split arcs into the array. 
	 * This way we don't accidentally split an arc already in the 
	 * array of arcs we are building.
	 * @return an array of arcs belonging to the match path
	 */
	Arc[] getMatchPath() throws MVDException
	{
		if ( path == null || !isPathValid() )
		{
			Vector<Arc> parents = new Vector<Arc>();
			Arc[] splits;
			Arc a = start.pickOutgoingArc( version );
			assert a != null;
			int distance = 0;
			int splitStart = 0;
			int splitEnd = 0;
			// move forward to graphOffset
			// NB leading arcs may be empty
			while ( distance <= graphOffset )
			{
				if ( distance+a.dataLen() <= graphOffset )
				{
					distance += a.dataLen();
					a = a.to.pickOutgoingArc( version );
					assert a != null&&a.versions.nextSetBit(version)==version;
				}
				else
				{
					splitStart = graphOffset - distance;
					distance = graphOffset;
					break;
				}
			}
			// if the split-point is not 0, split
			if ( splitStart > 0 )
			{
				assert a.versions.nextSetBit(version)==version;
				splits = a.split( splitStart );
				a = splits[1];
				assert a != null&&a.versions.nextSetBit(version)==version;
			}
			// now advance distance to the end of the match
			// a is the first arc of the path
			while ( distance < graphOffset+length )
			{
				assert a != null;
				if ( distance+a.dataLen() <= graphOffset+length )
				{
					distance += a.dataLen();
					//parents.add( a );
					Arc b = a.to.pickOutgoingArc( version );
					if ( b == null )	// if a.to is the end-node
					{
						assert a.to.outdegree()==0;
						break;
					}
					else
					{
						assert a.versions.nextSetBit(version)==version;
						a = b;
					}
				}
				else
				{
					splitEnd = graphOffset+length - distance;
					distance = graphOffset+length;
				}
			}
			if ( splitEnd > 0 )
			{
				splits = a.split( splitEnd );
				//a = splits[0];
				//parents.add( a );
			}
			// now retraverse the path (which should now be 
			// correctly split) and build the path array
			a = start.pickOutgoingArc( version );
			distance = 0;
			// move back to start of path - should fit exactly
			while ( distance < graphOffset )
			{
				distance += a.dataLen();
				a = a.to.pickOutgoingArc( version );
			}
			assert distance == graphOffset;
			distance = 0;
			while ( distance < length )
			{
				parents.add( a );
				distance += a.dataLen();
				a = a.to.pickOutgoingArc( version );
			}
			assert distance == length;
			splits = new Arc[parents.size()];
			path = parents.toArray( splits );
		}
		return path;
	}
	/**
	 * Find the parent arc in a vector of arcs. We want to replace 
	 * it but we can't while iterating through the list. So first 
	 * just find it.
	 * @param parents the vector of arcs to search through
	 * @param a the arc to search for
	 * @return -1 if not found, otherwise its index
	 */
	int findParentArc( Vector<Arc> parents, Arc a )
	{
		int index = -1;
		for ( int i=0;i<parents.size();i++ )
		{
			if ( parents.get(i) == a )
			{
				index = i;
				break;
			}
		}
		return index;
	}
	/**
	 * It is possible that the path as previously calculated is 
	 * now invalid. This can happen if part of the path is split 
	 * by a transposition that overlaps with the desired alignment. 
	 * In this case we check that each component of the calculated 
	 * path joins up.
	 * @return true if the path is usable
	 */
	private boolean isPathValid()
	{
		boolean valid = true;
		for ( int i=0;i<path.length-1;i++ )
		{
			if ( path[i].to == null 
				|| path[i+1].from == null 
				|| path[i].to.nodeId != path[i+1].from.nodeId )
			{
				valid = false;
				break;
			}
		}
		return valid;
	}
	/**
	 * Get the node at the end of the left subgraph
	 * @return the node that will become the end of the left 
	 * subgraph
	 */
	Node getLeftNode() throws MVDException
	{
		Arc[] path = getMatchPath();
		if ( path[0].from== null )
			System.out.println("null!");
		return path[0].from;
	}
	/**
	 * Get the node at the start of the right subgraph
	 * @return the node that will become the start of the right 
	 * subgraph
	 */
	Node getRightNode() throws MVDException
	{
		Arc[] path = getMatchPath();
		assert path != null && path[path.length-1] != null;
		return path[path.length-1].to;
	}
	/**
	 * Verify that the match is what it is supposed to be
	 * @param end don't go beyond this node
	 */
	void verify( Node end )
	{
		Node temp = start;
		int pos = graphOffset;
		int compared = 0;
		while ( temp != null && compared < length )
		{
			assert temp != end;
			Arc a = temp.pickOutgoingArc( version );
			if ( a.dataLen() < pos )
			{
				pos -= a.dataLen();
			}
			else if ( a.dataLen() == pos )
			{
				pos = 0;
				temp = a.to;
			}
			else if ( a.dataLen() == 0 )
			{
				temp = a.to;
				continue;
			}
			else 
			{
				char[] arcData = a.getData();
				while ( compared < length && pos < a.dataLen() )
				{
					assert arcData[pos++] == data[compared++];
					if ( pos == a.dataLen() )
					{
						pos = 0;
						break;
					}
				}
			}
			temp = a.to;
		}
	}
	/**
	 * Check that no element of the path contains the new version or 
	 * is a parent whose child contains that version.
	 * @param newVersion the new version that the arc can't traverse
	 * @return true if the conditions hold, false otherwise
	 */
	boolean checkPath( short newVersion )
	{
		Arc a = start.pickOutgoingArc( version );
		int posWithinArc = 0;
		int distTravelled = 0;
		// advance to graphOffset
		while ( distTravelled < graphOffset )
		{
			if ( distTravelled+a.dataLen() < graphOffset )
			{
				distTravelled += a.dataLen();
				a = a.to.pickOutgoingArc( version );
				posWithinArc = 0;
			}
			else 
			{
				posWithinArc = graphOffset - distTravelled;
				break;
			}
		}
		// now traverse the path
		if ( a.versions.nextSetBit(newVersion) == newVersion 
			|| (a.isParent()&&a.hasChildInVersion(newVersion)) )
			return false;
		else
		{
			distTravelled = 0;
			while ( distTravelled < length )
			{
				// move to next arc
				while ( posWithinArc == a.dataLen() )
				{
					a = a.to.pickOutgoingArc( version );
					if ( a.versions.nextSetBit(newVersion) == newVersion 
						|| (a.isParent()&&a.hasChildInVersion(newVersion)) )
						return false;
					posWithinArc = 0;
				}
				posWithinArc++;
				distTravelled++;
			}
			return true;
		}
	}
	// extra routines for nmerge
	/** 
	 * Get the length of the match
	 * @return the length in bytes
	 */
	public int getLength()
	{
		return length;
	}
	/**
	 * Do we use any part of the path in the graph that is also 
	 * used by another match?
	 * @param b the other Match
	 * @return true if the b match uses any arc from our path
	 */
	public boolean overlaps( Match b )
	{
		if ( version <= 0 )
			System.out.println("0");
		Arc aArc = start.pickOutgoingArc( version );
		Arc bArc = b.start.pickOutgoingArc( b.version );
		int i = 0;
		int aCurrArcIndex = 0;
		int bCurrArcIndex = 0;
		// advance to match-start in a
		while ( i < graphOffset )
		{
			if ( i+aArc.dataLen() < graphOffset )
			{
				i += aArc.dataLen();
				aArc = aArc.to.pickOutgoingArc( version );
				aCurrArcIndex = 0;
			}
			else 
			{
				aCurrArcIndex = graphOffset - i;
				i = graphOffset;
			}
		}
		i = 0;
		// advance to match-start in b
		while ( i < b.graphOffset )
		{
			if ( i+bArc.dataLen() < b.graphOffset )
			{
				i += bArc.dataLen();
				bArc = bArc.to.pickOutgoingArc( b.version );
				bCurrArcIndex = 0;
			}
			else 
			{
				bCurrArcIndex = b.graphOffset - i;
				i = b.graphOffset;
			}
		}
		// advance byte by byte to match-end
		i = 0;
		while ( i < length )
		{
			if ( aArc == bArc && aCurrArcIndex == bCurrArcIndex ) 
				return true;
			else
			{
				// move to next a-arc
				while ( aCurrArcIndex == aArc.dataLen() )
				{
					aArc = aArc.to.pickOutgoingArc( version );
					aCurrArcIndex = 0;
				}
				aCurrArcIndex++;
				// move to next b-arc
				while ( bCurrArcIndex == bArc.dataLen() )
				{
					bArc = bArc.to.pickOutgoingArc( b.version );
					bCurrArcIndex = 0;
				}
				bCurrArcIndex++;
				i++;
			}
		}
		return false;
	}
}
