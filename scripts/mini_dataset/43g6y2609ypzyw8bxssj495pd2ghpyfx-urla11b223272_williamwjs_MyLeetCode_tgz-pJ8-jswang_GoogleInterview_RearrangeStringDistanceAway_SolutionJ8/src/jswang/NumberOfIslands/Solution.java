package jswang.NumberOfIslands;

/**
 * Created by willwjs on 5/11/15.
 * You can't create an array like visited[0][0]
 */
public class Solution {
    public int numIslands(char[][] grid) {
        if (grid.length == 0 || grid[0].length == 0)
            return 0;
        int res = 0;
        for (int i = 0; i < grid.length; ++i) {
            for (int j = 0; j < grid[0].length; ++j) {
                if (grid[i][j] == '0')
                    continue;
                res++;
                findIsland(i, j, grid);
            }
        }
        return res;
    }

    private void findIsland(int i, int j, char[][] grid) {
        grid[i][j] = '0';
        if (i - 1 >= 0 && grid[i - 1][j] == '1')
                findIsland(i - 1, j, grid);
        if (j - 1 >= 0 && grid[i][j - 1] == '1')
                findIsland(i, j - 1, grid);
        if (j + 1 < grid[0].length && grid[i][j + 1] == '1')
                findIsland(i, j + 1, grid);
        if (i + 1 < grid.length && grid[i + 1][j] == '1')
                findIsland(i + 1, j, grid);
    }

    public static void main(String[] args) {
        System.out.println(new Solution().numIslands(new char[][]{
                {'1','1','1'},
                {'0','1','0'},
                {'1','1','1'}}));
    }
}