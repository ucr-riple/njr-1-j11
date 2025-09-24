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

package ru.spbau.sd.field.sq;

import ru.spbau.sd.model.framework.FieldGeometry;
import ru.spbau.sd.model.framework.FieldObject;
import ru.spbau.sd.model.framework.Point2D;

public class SquareFieldGeometry implements FieldGeometry{

    @Override
    public boolean arePointsNear(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        return dx * dy == 0 && (dx == 1 || dy == 1);
    }

    @Override
    public int getNeighborCnt() { return 4; }

    @Override
    public Point2D getNeighborByDir(int dir, int baseX, int baseY) {
        dir = dir % getNeighborCnt();
        int newX = baseX + (dir < 2 ? 1 : -1) * (dir % 2);
        int newY = baseY + (dir < 2 ? 1 : -1) * ((dir + 1)% 2);
        return new Point2D(newX, newY);
    }

    
    /*
     * 
     * ---X---
     * -XXXXX-
     * -XXXXX-
     * XXXCXXX
     * -XXXXX-
     * -XXXXX-
     * ---X---
     * 
     */
    @Override
    public boolean isInsideCircle(int cX, int cY, int rad, FieldObject fo) {
        if (Math.abs(cX - fo.getX()) <= rad-1 && Math.abs(cY - fo.getY()) <= rad-1) {
            return true;
        }
               
        return fo.isOnSamePosition(new Point2D(cX, cY - 3)) ||
               fo.isOnSamePosition(new Point2D(cX, cY + 3)) ||
               fo.isOnSamePosition(new Point2D(cX - 3, cY)) ||
               fo.isOnSamePosition(new Point2D(cX + 3, cY));
    }
    
}
