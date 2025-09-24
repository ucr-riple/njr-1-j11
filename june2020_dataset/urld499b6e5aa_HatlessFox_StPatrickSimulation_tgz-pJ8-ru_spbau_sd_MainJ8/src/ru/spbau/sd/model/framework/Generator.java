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
import java.util.List;

public abstract class Generator extends GameObject {

    private List<Point2D> entryPoints = new ArrayList<>();
    protected List<Point2D> getEntryPoints() { return entryPoints; }
    
    public Generator(int x, int y) {
        super(x, y);
        
        FieldGeometry fg = Field.getInstance().getGeometry();
        for (int i = 0; i < fg.getNeighborCnt(); i++) {
            Point2D entryPoint = fg.getNeighborByDir(i, x, y);
            if (Field.getInstance().isInsideField(entryPoint)) {
                entryPoints.add(entryPoint);
            }
        }
    }
    
    protected boolean tryAddMovable(MovableObject movable) {
        for (Point2D ep : entryPoints) {
            if (Field.getInstance().isPosFree(ep)) {
                movable.setNewPosition(ep);
                Field.getInstance().addMovable(movable);
                return true;
            }
        }
        return false;
    }
    
    protected boolean isBlocked() {
        for (Point2D ep : entryPoints) {
            if (Field.getInstance().isPosFree(ep)) { return false; }
        }
        return true;
    }

}
