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

package ru.spbau.sd.field.hex;

import ru.spbau.sd.model.framework.FieldGeometry;
import ru.spbau.sd.model.framework.FieldObject;
import ru.spbau.sd.model.framework.Point2D;

public class HexFieldGeometry implements FieldGeometry {

    @Override
    public boolean arePointsNear(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        if (dx * dy == 0 && (dx == 1 || dy == 1)) { return true; }
        if (dx  > 1 || dy > 1) { 
            return false; 
        }
        
        //Top-Left & Bottom-Right are also near
        return (x1 - x2) * (y1 - y2) < 0;
    }

    @Override
    public int getNeighborCnt() { return 6; }

    @Override
    public Point2D getNeighborByDir(int dir, int baseX, int baseY) {
        dir = dir % getNeighborCnt();
        int newX = baseX, newY = baseY;
        switch (dir) {
        case 0: newX += 1; break;
        case 1: newY += 1; break;
        case 2: newX -= 1; break;
        case 3: newY -= 1; break;
        case 4: newX += 1; newY -= 1; break;
        case 5: newX -= 1; newY += 1; break;
        }
        return new Point2D(newX, newY);
    }

    
    
    /*
     * 
     *     - - - - - - -
     *    - - X X X - - -
     *   - - X X X X - - -
     *  - - X X С X X - - -
     *   - - X X X X - - -
     *    - - X X X - - -
     *     - - - - - - -
     * Or 
     * 
     * ---------
     * ----XXXX-
     * ---XXXXX-
     * --XXXXXX-
     * -XXXCXXX-
     * -XXXXXX--
     * -XXXXX---
     * -XXXX----
     * ---------
     * 
     */
    @Override
    public boolean isInsideCircle(int cX, int cY, int rad, FieldObject fo) {
        int dx = cX - fo.getX();
        int dy = cY - fo.getY();
        
        boolean insSq = Math.abs(dx) <= rad && Math.abs(dy) <= rad;
        if (!insSq) { return false; }
        if (dx * dy < 0) { return true; }
        
        int stX = Math.max(cX - rad, 0);
        int stY = Math.max(cY - rad, 0);
        
        int diagX = fo.getX() - stX;
        int diagY = fo.getY() - stY;
        return (rad <= diagX + diagY) && (diagX + diagY <= 3*rad);
    }
    
}
