package jswang.ValidSudoku;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by willwjs on 11/16/14.
 */
public class Solution {
    public boolean isValidSudoku(char[][] board) {
        Set<Character> set = new HashSet<Character>();
        final int SIZE = 9;

        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                if (board[i][j] == '.') continue;
                if (set.contains(board[i][j])) return false;
                set.add(board[i][j]);
            }
            set.clear();
        }

        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                if (board[j][i] == '.') continue;
                if (set.contains(board[j][i])) return false;
                set.add(board[j][i]);
            }
            set.clear();
        }

        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                for (int a = i; a < i + 3; ++a) {
                    for (int b = j; b < j + 3; ++b) {
                        if (board[a][b] == '.') continue;
                        if (set.contains(board[a][b])) return false;
                        set.add(board[a][b]);
                    }
                }
                set.clear();
            }
        }

        return true;
    }
}
