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

package ru.spbau.sd.model.game;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import ru.spbau.sd.model.framework.Field;
import ru.spbau.sd.model.framework.FieldObject;
import ru.spbau.sd.model.framework.Point2D;

final public class GameUtils {
    private GameUtils() {}
    
    
    private static int [][] getMap(Class<?>... ignoredObstacles) {
        int x_dem = Field.getInstance().getXBound() + 2;
        int y_dem = Field.getInstance().getYBound() + 2;
        
        int[][] map = new int[x_dem][y_dem];
        Set<Class<?>> ignoredObst = new HashSet<Class<?>>();
        for (Class<?> obstClass : ignoredObstacles) { ignoredObst.add(obstClass); }
        
        for (FieldObject fo : Field.getInstance().getAllFieldObjects()) {
            if (ignoredObst.contains(fo.getClass())) { continue; }
            map[fo.getX()+1][fo.getY()+1] = Integer.MAX_VALUE;
        }
        return map;
    }

    private static abstract class NeighborHandler {
        public int[][] map;
        public int x;
        public int y;
        
        public NeighborHandler(int[][] pMap) {
            map = pMap;
        }
        abstract void handleNeighbor(int i, int y);
    }
    
    private static class CommonBfsNeighborHandler extends NeighborHandler {
        public Queue<Integer> q;
        public CommonBfsNeighborHandler(Queue<Integer> pQ, int[][] pMap) {
          super(pMap);
          q = pQ;
        }
        
        public void setCurrentCoord(int pX, int pY) {
            x = pX;
            y = pY;
        }
        @Override
        void handleNeighbor(int i, int j) {
            if (map[i][j] != 0) { return; }
             
            map[i][j] = map[x][y] + 1;
            q.add(i);
            q.add(j);
        }
        
    }
    
    private static class DestinationNeighborHandler extends NeighborHandler {
        public Point2D next_step = null;
        public DestinationNeighborHandler(int[][] pMap, Point2D point) {
            super(pMap);
            x = point.x + 1;
            y = point.y + 1;
        }
        
        @Override
        void handleNeighbor(int i, int j) {
            if (map[i][j] + 1 == map[x][y]) {
                next_step =  new Point2D(i-1, j-1);
            }
        }
        
    }
    
    private static void processNeighbors(NeighborHandler nh) {
        for (int i = nh.x - 1; i < nh.x + 2; i++) {
            for (int j = nh.y - 1; j < nh.y + 2; j++) {
                if (!Field.arePointsNear(i, j, nh.x, nh.y)) { continue; }
                if (!Field.getInstance().isInsideField(i-1, j-1)) { continue; }
                nh.handleNeighbor(i, j);
            }
        }
    }
        
    public static Point2D lookUpNextStep(Point2D start, Point2D end, Class<?>... ignoredObstacles) {
        if (Field.arePointsNear(start, end)) { return end; }
        
        int[][] map = getMap(ignoredObstacles);
        map[start.x+1][start.y+1] = 0; 
        map[end.x+1][end.y+1] = 0;
        
        Queue<Integer> points_to_go = new LinkedList<>();
        points_to_go.add(end.x+1);
        points_to_go.add(end.y+1);
        
        CommonBfsNeighborHandler bfsH = new CommonBfsNeighborHandler(points_to_go, map);
        DestinationNeighborHandler destH = new DestinationNeighborHandler(map, start);
        
        while (!points_to_go.isEmpty()) {
            int curr_x = points_to_go.poll();
            int curr_y = points_to_go.poll();
            
            if (curr_x == start.x+1 && curr_y == start.y+1) {
                processNeighbors(destH);
                break;
            }
            
            bfsH.setCurrentCoord(curr_x, curr_y);
            processNeighbors(bfsH);
        }
        
        return destH.next_step == null ? start : destH.next_step;
    }
}
