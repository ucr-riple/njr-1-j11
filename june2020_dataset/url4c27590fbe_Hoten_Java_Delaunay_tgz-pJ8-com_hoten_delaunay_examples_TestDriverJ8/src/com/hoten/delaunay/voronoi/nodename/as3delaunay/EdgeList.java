package com.hoten.delaunay.voronoi.nodename.as3delaunay;

import com.hoten.delaunay.geom.Point;
import java.util.ArrayList;

public final class EdgeList implements IDisposable {

    private double _deltax;
    private double _xmin;
    private int _hashsize;
    private ArrayList<Halfedge> _hash;
    public Halfedge leftEnd;
    public Halfedge rightEnd;

    @Override
    public void dispose() {
        Halfedge halfEdge = leftEnd;
        Halfedge prevHe;
        while (halfEdge != rightEnd) {
            prevHe = halfEdge;
            halfEdge = halfEdge.edgeListRightNeighbor;
            prevHe.dispose();
        }
        leftEnd = null;
        rightEnd.dispose();
        rightEnd = null;

        _hash.clear();
        _hash = null;
    }

    public EdgeList(double xmin, double deltax, int sqrt_nsites) {
        _xmin = xmin;
        _deltax = deltax;
        _hashsize = 2 * sqrt_nsites;

        _hash = new ArrayList(_hashsize);

        // two dummy Halfedges:
        leftEnd = Halfedge.createDummy();
        rightEnd = Halfedge.createDummy();
        leftEnd.edgeListLeftNeighbor = null;
        leftEnd.edgeListRightNeighbor = rightEnd;
        rightEnd.edgeListLeftNeighbor = leftEnd;
        rightEnd.edgeListRightNeighbor = null;
        
        for(int i = 0; i < _hashsize; i++){
            _hash.add(null);
        }
        
        _hash.set(0, leftEnd);
        _hash.set(_hashsize - 1, rightEnd);
    }

    /**
     * Insert newHalfedge to the right of lb
     *
     * @param lb
     * @param newHalfedge
     *
     */
    public void insert(Halfedge lb, Halfedge newHalfedge) {
        newHalfedge.edgeListLeftNeighbor = lb;
        newHalfedge.edgeListRightNeighbor = lb.edgeListRightNeighbor;
        lb.edgeListRightNeighbor.edgeListLeftNeighbor = newHalfedge;
        lb.edgeListRightNeighbor = newHalfedge;
    }

    /**
     * This function only removes the Halfedge from the left-right list. We
     * cannot dispose it yet because we are still using it.
     *
     * @param halfEdge
     *
     */
    public void remove(Halfedge halfEdge) {
        halfEdge.edgeListLeftNeighbor.edgeListRightNeighbor = halfEdge.edgeListRightNeighbor;
        halfEdge.edgeListRightNeighbor.edgeListLeftNeighbor = halfEdge.edgeListLeftNeighbor;
        halfEdge.edge = Edge.DELETED;
        halfEdge.edgeListLeftNeighbor = halfEdge.edgeListRightNeighbor = null;
    }

    /**
     * Find the rightmost Halfedge that is still left of p
     *
     * @param p
     * @return
     *
     */
    public Halfedge edgeListLeftNeighbor(Point p) {
        int i, bucket;
        Halfedge halfEdge;

        /* Use hash table to get close to desired halfedge */
        bucket = (int) ((p.x - _xmin) / _deltax * _hashsize);
        if (bucket < 0) {
            bucket = 0;
        }
        if (bucket >= _hashsize) {
            bucket = _hashsize - 1;
        }
        halfEdge = getHash(bucket);
        if (halfEdge == null) {
            for (i = 1; true; ++i) {
                if ((halfEdge = getHash(bucket - i)) != null) {
                    break;
                }
                if ((halfEdge = getHash(bucket + i)) != null) {
                    break;
                }
            }
        }
        /* Now search linear list of halfedges for the correct one */
        if (halfEdge == leftEnd || (halfEdge != rightEnd && halfEdge.isLeftOf(p))) {
            do {
                halfEdge = halfEdge.edgeListRightNeighbor;
            } while (halfEdge != rightEnd && halfEdge.isLeftOf(p));
            halfEdge = halfEdge.edgeListLeftNeighbor;
        } else {
            do {
                halfEdge = halfEdge.edgeListLeftNeighbor;
            } while (halfEdge != leftEnd && !halfEdge.isLeftOf(p));
        }

        /* Update hash table and reference counts */
        if (bucket > 0 && bucket < _hashsize - 1) {
            _hash.set(bucket, halfEdge);
        }
        return halfEdge;
    }

    /* Get entry from hash table, pruning any deleted nodes */
    private Halfedge getHash(int b) {
        Halfedge halfEdge;

        if (b < 0 || b >= _hashsize) {
            return null;
        }
        halfEdge = _hash.get(b);
        if (halfEdge != null && halfEdge.edge == Edge.DELETED) {
            /* Hash table points to deleted halfedge.  Patch as necessary. */
            _hash.set(b, null);
            // still can't dispose halfEdge yet!
            return null;
        } else {
            return halfEdge;
        }
    }
}