package jswang.CourseSchedule;

import java.util.*;

/**
 * Created by willwjs on 5/10/15.
 * Check whether there is a loop
 */
public class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> lists = new ArrayList<List<Integer>>(numCourses);
        for (int i = 0; i < numCourses; ++i) {
            List<Integer> tmp = new LinkedList<Integer>();
            lists.add(tmp);
        }
        for (int i = 0; i < prerequisites.length; ++i) {
            lists.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }

        boolean[] visited = new boolean[numCourses];
        int begin = 0;
        while (begin < numCourses) {
            if (isLoop(begin, lists, visited))
                return false;
            while (begin < numCourses && lists.get(begin++).isEmpty());
        }
        return true;
    }

    private boolean isLoop(int cur, List<List<Integer>> lists, boolean[] visited) {
        visited[cur] = true;
        while (!lists.get(cur).isEmpty()) {
            int i = lists.get(cur).get(0);
            lists.get(cur).remove(0);
            if (visited[i])
                return true;
            if (isLoop(i, lists, visited))
                return true;
        }
        visited[cur] = false;
        return false;
    }

    public static void main (String[] args) {
        System.out.println(new Solution().canFinish(3, new int[][]{{1, 0}, {2, 0}}));
    }
}