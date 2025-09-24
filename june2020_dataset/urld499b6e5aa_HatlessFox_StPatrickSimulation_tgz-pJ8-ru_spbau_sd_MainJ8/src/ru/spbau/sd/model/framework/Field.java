/**
  Copyright (c) 2013 Artur Huletski (hatless.fox@gmail.com)
  Permission is hereby granted, free of charge, to any person obtaining a copy 
  of this software and associated documentation files (the "Software"),
  to deal in the Software without restriction, including without limitation
  the rights to use, copy, modify, merge, publish, distribute, sublicense,
  and/or sell copies of the Software, and to permit persons
  to whom the Software is furnished to do so,
  subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included
  in all copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
  OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package ru.spbau.sd.model.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Instance of a game field.
 * 
 * @author Artur Huletski (hatless.fox@gmail.com)
 *
 */
public class Field {

    private static Field sInstance;
    public static Field getInstance() { return sInstance; }
    public static void init(int xBound, int yBound, FieldDescriptorsFactory fdf) {
        sInstance = new Field(xBound, yBound, fdf);
    }
    
    private final int mXBound;
    public int getXBound() { return mXBound; }
    private final int mYBound;
    public int getYBound() { return mYBound; }
    
    private FieldDescriptorsFactory mFDF;
    
    private Field(int xBound, int yBound, FieldDescriptorsFactory fdf) {
        mXBound = xBound;
        mYBound = yBound;
        mFDF = fdf;
    }
    
    //object that can move
    private List<MovableObject> mMovableObjects = new ArrayList<MovableObject>();
    //objects that can't move
    private List<FieldObject> mStationaryObjects = new ArrayList<FieldObject>();
    //end turn listeners
    private List<EndTurnListener> mEndTurnListenrs = new ArrayList<>();
    //outside objects
    private List<GameObject> mOutsideObjects = new ArrayList<>();
    
    
    public void addMovable(MovableObject movable) { mMovableObjects.add(movable); }
    public void removeMovable(MovableObject movable) { mMovableObjects.remove(movable); }
    public void addStationary(FieldObject stat) { mStationaryObjects.add(stat); }
    public void removeStationary(FieldObject stat) { mStationaryObjects.remove(stat); }
    public void addEndTurnListener(EndTurnListener etl) { mEndTurnListenrs.add(etl); }
    public void addOutside(GameObject etl) { mOutsideObjects.add(etl); }
    
    //checks if point lies on field
    public boolean isInsideField(Point2D point) {
        return isInsideField(point.x, point.y);
    }
    public boolean isInsideField(int x, int y) {
        return (0 <= x) && (x < getXBound()) &&
               (0 <= y) && (y < getYBound());
    }
    
    public static boolean arePointsNear(int x1, int y1, int x2, int y2) {
        return getInstance().mFDF.getFieldGeometry().arePointsNear(x1, y1, x2, y2);
    }
    
    
    public static boolean areNear(GameObject go1, GameObject go2) {
        return arePointsNear(go1.getX(), go1.getY(), go2.getX(), go2.getY());
    }
    
    public static boolean arePointsNear(Point2D p1, Point2D p2) {
        return arePointsNear(p1.x, p1.y, p2.x, p2.y);
    }
    
    public boolean isPosFree(Point2D pos) {
        return getObjectOnPos(pos) == null;
    }
    
    public FieldObject getObjectOnPos(Point2D pos) {
        for (FieldObject fo : getAllFieldObjects()) {
            if (fo.isOnSamePosition(pos)) { return fo; }
        }
        return null;
    }
    
    public FieldGeometry getGeometry() {
        return mFDF.getFieldGeometry();
    }
    
    /**
     * Performs 'time tick'
     */
    public void simulateRound() {
        List<MovableObject> movableSnapshot = new ArrayList<>(mMovableObjects);
        //iterate over snapshot since some interactions can modify movable list
        for (MovableObject movable : movableSnapshot) {
            Point2D nextPosition = movable.move();
            if (!isInsideField(nextPosition) || movable.isOnSamePosition(nextPosition)) {
                continue;
            }
            
            FieldObject fo = getObjectOnPos(nextPosition);
            
            if (fo == null) {
                movable.setNewPosition(nextPosition.x, nextPosition.y);
            } else {
                movable.interact(fo);
            }
            
        }
    }
    
    public void handleEndTurn() {
        for (EndTurnListener etl : mEndTurnListenrs) {
            etl.handleEndTurn();
        }
    }
    /**
     * Returns list of all object that are on field at a given time<br/>
     * 
     * @return
     */
    public List<FieldObject> getAllFieldObjects() {
        //caching if this is performance bottle neck
        List<FieldObject> result = new ArrayList<FieldObject>();
        result.addAll(mStationaryObjects);
        result.addAll(mMovableObjects);
        return result;
    }
    
    public List<GameObject> getOutsideObjects() {
        return Collections.unmodifiableList(mOutsideObjects);
    }
    
    public char[][] getTableView() {
        char filedProg[][] = new char[getXBound()+2][getYBound()+2]; 
        
        //round rectangle is for outer objects
        for (int i = -1; i < getXBound() + 1; i++) {
            for (int j = -1; j < getYBound() + 1; j ++) {
                filedProg[i+1][j+1] = isInsideField(i, j) ? '.' : ' ';
            }
        }
        
        
        for (FieldObject fo : getAllFieldObjects()) {
            filedProg[fo.getY()+1][fo.getX()+1] = fo.getSingleCharDescription();
        }
        for (GameObject go : getOutsideObjects()) {
            filedProg[go.getY()+1][go.getX()+1] = go.getSingleCharDescription();
        }
        
        return filedProg;
    }
    
    @Override
    public String toString() {
        return mFDF.getFieldStringSerializer().serializeField(this);
    }
    
}
