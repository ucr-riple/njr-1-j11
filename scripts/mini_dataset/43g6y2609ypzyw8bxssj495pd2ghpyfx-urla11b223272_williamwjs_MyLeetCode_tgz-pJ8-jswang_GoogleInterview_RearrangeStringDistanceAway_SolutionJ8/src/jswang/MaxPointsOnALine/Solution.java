package jswang.MaxPointsOnALine;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by willwjs on 10/12/15.
 * 1.考虑重复的点 sameP   =>   局部最大值需要加上sameP
 * 2.考虑斜率不存在 xCount
 * 3.考虑Double/Float类型中 -0.0 != 0.0   =>    因为用的是equals
 *   但是double/float基本类型中 -0.0 == 0.0
 */
class Point {
    int x;
    int y;
    Point() { x = 0; y = 0; }
    Point(int a, int b) { x = a; y = b; }
}

public class Solution {
    public int maxPoints(Point[] points) {
        if (points == null || points.length == 0) return 0;
        if (points.length == 1) return 1;
        int max = 0, xCount = 0, sameP = 1;
        Map<Double, Integer> countMap = new HashMap<>();
        for (int i = 0; i < points.length - 1; ++i) {
            countMap.clear();
            xCount = 0;
            sameP = 1;
            for (int j = i + 1; j < points.length; ++j) {
                if (points[i].x == points[j].x && points[i].y == points[j].y) {
                    sameP++;
                    continue;
                }
                if (points[j].x == points[i].x) {
                    xCount++;
                    continue;
                }
                double k = ((double)(points[j].y - points[i].y)) / (points[j].x - points[i].x);
                if (k == -0.0) k = 0.0;
                if (countMap.containsKey(k)) {
                    countMap.put(k, countMap.get(k) + 1);
                } else {
                    countMap.put(k, 1);
                }
            }
            for (int v : countMap.values()) {
                xCount = Math.max(xCount, v);
            }
            xCount += sameP;
            max = Math.max(max, xCount);
        }
        return max;
    }

    public static void main(String[] args) {
        Point[] points = new Point[] {new Point(2,3), new Point(3,3), new Point(-5,3)};
        System.out.println(new Solution().maxPoints(points));
    }
}