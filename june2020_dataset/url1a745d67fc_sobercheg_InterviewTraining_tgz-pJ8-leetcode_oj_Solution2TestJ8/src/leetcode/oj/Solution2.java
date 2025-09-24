package leetcode.oj;

import java.util.*;

import static leetcode.oj.Solution.ListNode;
import static leetcode.oj.Solution.TreeNode;

/**
 * Created by Sobercheg on 1/29/14.
 * http://oj.leetcode.com/problems/
 */
public class Solution2 {

    /**
     * <a href="http://oj.leetcode.com/problems/minimum-path-sum/">Minimum Path Sum</a>
     * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right
     * which minimizes the sum of all numbers along its path.
     * <p/>
     * Note: You can only move either down or right at any point in time.
     */
    public int minPathSum(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 && j == 0) continue;
                grid[i][j] += Math.min(
                        i == 0 ? Integer.MAX_VALUE : grid[i - 1][j],
                        j == 0 ? Integer.MAX_VALUE : grid[i][j - 1]);
            }
        }
        return grid[n - 1][m - 1];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/merge-two-sorted-lists/">Merge Two Sorted Lists</a>
     * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing
     * together the nodes of the first two lists.
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode prevNode = null;
        ListNode newHead = null;

        while (l1 != null || l2 != null) {
            if (l1 != null && l2 != null && l1.val < l2.val || l2 == null) {
                if (prevNode == null)
                    prevNode = l1;
                else {
                    prevNode.next = l1;
                    prevNode = prevNode.next;
                }
                l1 = l1.next;
            } else {
                if (prevNode == null)
                    prevNode = l2;
                else {
                    prevNode.next = l2;
                    prevNode = prevNode.next;
                }
                l2 = l2.next;
            }

            if (newHead == null)
                newHead = prevNode;
        }
        return newHead;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/add-binary/">Add Binary</a>
     * Given two binary strings, return their sum (also a binary string).
     * <p/>
     * For example,
     * a = "11"
     * b = "1"
     * Return "100".
     */
    public String addBinary(String a, String b) {
        // pad with zeros: "11", "1" -> "11", "01"
        char[] zeros = new char[Math.abs(a.length() - b.length())];
        Arrays.fill(zeros, '0');
        if (a.length() < b.length())
            a = new String(zeros) + a;
        else
            b = new String(zeros) + b;

        StringBuilder result = new StringBuilder();
        int carryover = 0;
        for (int i = a.length() - 1; i >= 0; i--) {
            int sum = (a.charAt(i) - '0') + (b.charAt(i) - '0') + carryover;
            carryover = sum > 1 ? 1 : 0;
            result.insert(0, (char) ('0' + sum % 2));
        }
        if (carryover > 0) result.insert(0, '1');
        return result.toString();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/valid-number/">Valid Number</a>
     * Validate if a given string is numeric.
     * <p/>
     * Some examples:
     * "0" => true
     * " 0.1 " => true
     * "abc" => false
     * "1 a" => false
     * "2e10" => true
     * <p/>
     * Note: It is intended for the problem statement to be ambiguous. You should gather all requirements up front before implementing one.
     * <p/>
     * The solution is based on automata-based programming http://en.wikipedia.org/wiki/Automata-based_programming,
     * http://discuss.leetcode.com/questions/241/valid-number
     */
    static enum State {
        START_SPACE(0),
        START_DIGIT(1),
        SIGN_READ(2),
        DOT_READ(3),
        DIGIT_AFTER_DOT(4),
        EXPONENT(5),
        EXPONENT_SIGN(6),
        EXPONENT_DIGIT(7),
        END_SPACE(8);

        State(int value) {
            this.value = value;
        }

        int value;
    }

    static enum Input {
        INVALID(0),
        SPACE(1),
        SIGN(2),
        EXPONENT(3),
        DIGIT(4),
        DOT(5);

        Input(int value) {
            this.value = value;
        }

        int value;

        public static Input of(char ch) {
            if (ch == ' ') return SPACE;
            if (ch == '+' || ch == '-') return SIGN;
            if (ch == 'e' || ch == 'E') return EXPONENT;
            if (ch >= '0' && ch <= '9') return DIGIT;
            if (ch == '.') return DOT;
            return INVALID;

        }
    }

    static class Transition {
        State state;
        Input input;

        Transition(State state, Input input) {
            this.state = state;
            this.input = input;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Transition that = (Transition) o;

            return input == that.input && state == that.state;

        }

        @Override
        public int hashCode() {
            int result = state != null ? state.hashCode() : 0;
            result = 31 * result + (input != null ? input.hashCode() : 0);
            return result;
        }
    }

    public boolean isNumber(String s) {
        Map<Transition, State> transitions = new HashMap<Transition, State>();
        transitions.put(new Transition(State.START_SPACE, Input.SPACE), State.START_SPACE);
        transitions.put(new Transition(State.START_SPACE, Input.DIGIT), State.START_DIGIT);
        transitions.put(new Transition(State.START_SPACE, Input.SIGN), State.SIGN_READ);
        transitions.put(new Transition(State.START_SPACE, Input.DOT), State.DOT_READ);

        transitions.put(new Transition(State.START_DIGIT, Input.DIGIT), State.START_DIGIT);
        transitions.put(new Transition(State.START_DIGIT, Input.DOT), State.DIGIT_AFTER_DOT);
        transitions.put(new Transition(State.START_DIGIT, Input.EXPONENT), State.EXPONENT);
        transitions.put(new Transition(State.START_DIGIT, Input.SPACE), State.END_SPACE);

        transitions.put(new Transition(State.DOT_READ, Input.DIGIT), State.DIGIT_AFTER_DOT);

        transitions.put(new Transition(State.SIGN_READ, Input.DIGIT), State.START_DIGIT);
        transitions.put(new Transition(State.SIGN_READ, Input.DOT), State.DOT_READ);

        transitions.put(new Transition(State.DIGIT_AFTER_DOT, Input.DIGIT), State.DIGIT_AFTER_DOT);
        transitions.put(new Transition(State.DIGIT_AFTER_DOT, Input.SPACE), State.END_SPACE);
        transitions.put(new Transition(State.DIGIT_AFTER_DOT, Input.EXPONENT), State.EXPONENT);

        transitions.put(new Transition(State.EXPONENT, Input.DIGIT), State.EXPONENT_DIGIT);
        transitions.put(new Transition(State.EXPONENT, Input.SIGN), State.EXPONENT_SIGN);

        transitions.put(new Transition(State.EXPONENT_SIGN, Input.DIGIT), State.EXPONENT_DIGIT);

        transitions.put(new Transition(State.EXPONENT_DIGIT, Input.DIGIT), State.EXPONENT_DIGIT);
        transitions.put(new Transition(State.EXPONENT_DIGIT, Input.SPACE), State.END_SPACE);

        transitions.put(new Transition(State.END_SPACE, Input.SPACE), State.END_SPACE);

        State currentState = State.START_SPACE;
        for (int i = 0; i < s.length(); i++) {
            Input input = Input.of(s.charAt(i));
            currentState = transitions.get(new Transition(currentState, input));
            if (currentState == null) return false;
        }

        return currentState == State.START_DIGIT
                || currentState == State.DIGIT_AFTER_DOT
                || currentState == State.END_SPACE
                || currentState == State.EXPONENT_DIGIT;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/plus-one/">Plus One</a>
     * Given a number represented as an array of digits, plus one to the number.
     */
    public int[] plusOne(int[] digits) {
        int carryover = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int sum = digits[i] + carryover;
            if (sum > 9) {
                digits[i] = sum % 10;
                carryover = 1;
            } else {
                digits[i] += carryover;
                carryover = 0;
            }

        }
        if (carryover == 0) return digits;
        int[] newArray = new int[digits.length + 1];
        System.arraycopy(digits, 0, newArray, 1, digits.length);
        newArray[0] = carryover;
        return newArray;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/text-justification/">Text Justification</a>
     * Given an array of words and a length L, format the text such that each line has exactly L characters and
     * is fully (left and right) justified.
     * <p/>
     * You should pack your words in a greedy approach; that is, pack as many words as you can in each line.
     * Pad extra spaces ' ' when necessary so that each line has exactly L characters.
     * <p/>
     * Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line
     * do not divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.
     * <p/>
     * For the last line of text, it should be left justified and no extra space is inserted between words.
     * <p/>
     * For example,
     * words: ["This", "is", "an", "example", "of", "text", "justification."]
     * L: 16.
     * <p/>
     * Return the formatted lines as:
     * <p/>
     * [
     * "This    is    an",
     * "example  of text",
     * "justification.  "
     * ]
     * <p/>
     * Note: Each word is guaranteed not to exceed L in length.
     */
    public ArrayList<String> fullJustify(String[] words, int L) {
        ArrayList<String> justified = new ArrayList<String>();

        if (L == 0) {
            Collections.addAll(justified, words);
            return justified;
        }

        int stringLength = 0;
        int numOfWordsInLine = 0;
        int lineStartWordIndex = 0;
        for (int i = 0; i < words.length; i++) {
            stringLength += words[i].length() + 1;
            numOfWordsInLine++;
            if (stringLength + (i < words.length - 1 ? words[i + 1].length() : 0) > L) {
                int spacesToInsert = L - (stringLength - 1);
                int baseSpaces = numOfWordsInLine > 1 ? spacesToInsert / (numOfWordsInLine - 1) : 0;
                int additionalSpaces = numOfWordsInLine > 1 ? spacesToInsert % (numOfWordsInLine - 1) : L - stringLength;
                StringBuilder line = new StringBuilder();
                for (int j = lineStartWordIndex; j < i; j++) {
                    line.append(words[j]);
                    line.append(' ');
                    for (int s = 0; s < baseSpaces + (additionalSpaces > 0 ? 1 : 0); s++)
                        line.append(' ');
                    if (additionalSpaces > 0)
                        additionalSpaces--;
                }
                line.append(words[i]);
                if (numOfWordsInLine == 1 && stringLength - 1 < L) {
                    line.append(' ');
                    for (int s = 0; s < baseSpaces + additionalSpaces; s++)
                        line.append(' ');
                }
                justified.add(line.toString());
                lineStartWordIndex = i + 1;
                numOfWordsInLine = 0;
                stringLength = 0;
            }

        }

        if (lineStartWordIndex == words.length) return justified;

        StringBuilder lastLine = new StringBuilder();

        for (int i = lineStartWordIndex; i < words.length; i++) {
            lastLine.append(words[i]);
            if (lastLine.length() < L) lastLine.append(' ');
        }

        if (lastLine.length() < L) {
            int len = lastLine.length();
            for (int i = 0; i < L - len; i++) lastLine.append(' ');
        }

        if (lastLine.length() > 0)
            justified.add(lastLine.toString());
        return justified;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/climbing-stairs/">Climbing Stairs</a>
     * You are climbing a stair case. It takes n steps to reach to the top.
     * <p/>
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
     */
    public int climbStairs(int n) {
        return climbStairs(n, new int[n + 1]);
    }

    private int climbStairs(int n, int[] memo) {
        if (n <= 2) return n;
        if (memo[n] > 0)
            return memo[n];
        memo[n] = climbStairs(n - 2, memo) + climbStairs(n - 1, memo);
        return memo[n];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/simplify-path/">Simplify Path</a>
     * Given an absolute path for a file (Unix-style), simplify it.
     * <p/>
     * For example,
     * path = "/home/", => "/home"
     * path = "/a/./b/../../c/", => "/c"
     */
    public String simplifyPath(String path) {
        LinkedList<String> elements = new LinkedList<String>();
        for (String element : path.split("/")) {
            if (element.isEmpty()) continue;
            if (element.equals(".")) continue;
            if (element.equals(".."))
                elements.poll();
            else
                elements.push(element);
        }
        if (elements.isEmpty()) return "/";
        Collections.reverse(elements);
        StringBuilder simplifiedPath = new StringBuilder();
        for (String element : elements) {
            simplifiedPath.append("/");
            simplifiedPath.append(element);
        }
        return simplifiedPath.toString();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/edit-distance/">Edit Distance</a>
     * Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)
     * <p/>
     * You have the following 3 operations permitted on a word:
     * <p/>
     * a) Insert a character
     * b) Delete a character
     * c) Replace a character
     */
    public int minDistance(String word1, String word2) {

        int m = word1.length();
        int n = word2.length();
        int[][] distance = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            distance[i + 1][0] = i + 1;
        }
        for (int j = 0; j < n; j++) {
            distance[0][j + 1] = j + 1;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (word1.charAt(i) == word2.charAt(j))
                    distance[i + 1][j + 1] = distance[i][j];
                else distance[i + 1][j + 1] = 1 +
                        Math.min(Math.min(
                                        distance[i][j + 1],
                                        distance[i + 1][j]),
                                distance[i][j]
                        );
            }
        }

        return distance[m][n];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/set-matrix-zeroes/">Set Matrix Zeros</a>
     * Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in place.
     */
    public void setZeroes(int[][] matrix) {
        int height = matrix.length;
        int width = matrix[0].length;

        boolean upperEdgeHasZeros = false;
        for (int i = 0; i < width; i++) {
            if (matrix[0][i] == 0) {
                upperEdgeHasZeros = true;
                break;
            }
        }

        boolean leftEdgeHasZeros = false;
        for (int i = 0; i < height; i++) {
            if (matrix[i][0] == 0) {
                leftEdgeHasZeros = true;
                break;
            }
        }

        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    break;
                }
            }
        }

        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                if (matrix[j][i] == 0) {
                    matrix[0][i] = 0;
                    break;
                }
            }
        }

        for (int i = 1; i < height; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 0; j < width; j++)
                    matrix[i][j] = 0;
            }
        }

        for (int i = 1; i < width; i++) {
            if (matrix[0][i] == 0) {
                for (int j = 0; j < height; j++)
                    matrix[j][i] = 0;
            }
        }

        if (upperEdgeHasZeros) {
            for (int i = 0; i < width; i++)
                matrix[0][i] = 0;
        }

        if (leftEdgeHasZeros) {
            for (int i = 0; i < height; i++)
                matrix[i][0] = 0;
        }

    }

    /**
     * <a href="http://oj.leetcode.com/problems/minimum-window-substring/">Minimum Window Substring</a>
     * Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).
     * <p/>
     * For example,
     * S = "ADOBECODEBANC"
     * T = "ABC"
     * <p/>
     * Minimum window is "BANC".
     * <p/>
     * Note:
     * If there is no such window in S that covers all characters in T, return the emtpy string "".
     * <p/>
     * If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.
     * <p/>
     * Solution: http://oj.leetcode.com/problems/minimum-window-substring/
     */
    public String minWindow(String S, String T) {

        int[] needToFind = new int[256];
        for (char ch : T.toCharArray()) {
            needToFind[ch]++;
        }
        int[] hasFound = new int[256];

        int minSubstringStart = 0;
        int minSubstringEnd = -1;
        int minLen = Integer.MAX_VALUE;

        int count = 0;
        int begin = 0;
        for (int i = 0; i < S.length(); i++) {
            char ch = S.charAt(i);

            hasFound[ch]++;

            if (hasFound[ch] <= needToFind[ch])
                count++;

            if (count == T.length()) {
                for (int j = begin; j <= i; j++) {
                    char sch = S.charAt(j);
                    if (hasFound[sch] > needToFind[sch])
                        hasFound[sch]--;
                    else {
                        begin = j;
                        break;
                    }
                }

                if (i - begin < minLen) {
                    minSubstringStart = begin;
                    minSubstringEnd = i;
                    minLen = minSubstringEnd - minSubstringStart;
                }
            }
        }

        return S.substring(minSubstringStart, minSubstringEnd + 1);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/search-a-2d-matrix/">Search a 2D Matrix</a>
     * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
     * <p/>
     * Integers in each row are sorted from left to right.
     * The first integer of each row is greater than the last integer of the previous row.
     * <p/>
     * For example,
     * <p/>
     * Consider the following matrix:
     * <p/>
     * [
     * [1,   3,  5,  7],
     * [10, 11, 16, 20],
     * [23, 30, 34, 50]
     * ]
     * <p/>
     * Given target = 3, return true.
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int start = 0;
        int c = matrix[0].length;

        int end = matrix.length * matrix[0].length - 1;
        int mid = 0;
        while (start <= end) {
            mid = (end + start) / 2;
            int val = matrix[mid / c][mid % c];
            if (val > target)
                end = mid - 1;
            else if (val < target)
                start = mid + 1;
            else break;
        }

        return matrix[mid / c][mid % c] == target;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/sort-colors/">Sort Colors</a>
     * Given an array with n objects colored red, white or blue, sort them so that objects of the same color are
     * adjacent, with the colors in the order red, white and blue.
     * <p/>
     * Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
     * <p/>
     * Note:
     * You are not suppose to use the library's sort function for this problem.
     * <p/>
     * click to show follow up.
     * <p/>
     * Follow up:
     * A rather straight forward solution is a two-pass algorithm using counting sort.
     * First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's,
     * then 1's and followed by 2's.
     * <p/>
     * Could you come up with an one-pass algorithm using only constant space?
     * <p/>
     * Solution: 3-way partitioning (see Sedgewick)
     */
    public void sortColors(int[] A) {
        int n = A.length - 1;
        int start = 0;
        int end = n;
        int i = 0;

        while (i <= end) {
            if (A[i] == 0) {
                swap(A, start, i);
                start++;
                i++;
            } else if (A[i] == 2) {
                swap(A, i, end);
                end--;
            } else i++;
        }
    }

    private void swap(int[] A, int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/word-search/">Word Search</a>
     * Given a 2D board and a word, find if the word exists in the grid.
     * <p/>
     * The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are
     * those horizontally or vertically neighboring. The same letter cell may not be used more than once.
     * <p/>
     * For example,
     * Given board =
     * <p/>
     * [
     * ["ABCE"],
     * ["SFCS"],
     * ["ADEE"]
     * ]
     * <p/>
     * word = "ABCCED", -> returns true,
     * word = "SEE", -> returns true,
     * word = "ABCB", -> returns false.
     */
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (exist(board, word, 0, visited, i, j))
                    return true;
            }
        }
        return false;
    }

    private boolean exist(char[][] board, String word, int letter, boolean[][] visited, int y, int x) {
        if (letter == word.length())
            return true;

        if (y < 0 || x < 0 || y >= board.length || x >= board[0].length)
            return false;

        if (visited[y][x] || board[y][x] != word.charAt(letter))
            return false;

        visited[y][x] = true;

        boolean exists = exist(board, word, letter + 1, visited, y + 1, x)
                || exist(board, word, letter + 1, visited, y - 1, x)
                || exist(board, word, letter + 1, visited, y, x + 1)
                || exist(board, word, letter + 1, visited, y, x - 1);
        visited[y][x] = false;

        return exists;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/combinations/">Combinations</a>
     * Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
     * <p/>
     * For example,
     * If n = 4 and k = 2, a solution is:
     * <p/>
     * [
     * [2,4],
     * [3,4],
     * [2,3],
     * [1,2],
     * [1,3],
     * [1,4],
     * ]
     */
    public ArrayList<ArrayList<Integer>> combine(int n, int k) {
        return combine(n, k, 0, 0, new ArrayList<Integer>());
    }

    private ArrayList<ArrayList<Integer>> combine(int n, int k, int level, int start, ArrayList<Integer> combo) {
        if (level == k) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
            result.add(new ArrayList<Integer>(combo));
            return result;
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        for (int i = start; i < n; i++) {
            combo.add(i + 1);
            result.addAll(combine(n, k, level + 1, i + 1, combo));
            combo.remove((Integer) (i + 1));
        }
        return result;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/subsets/">Subsets</a>
     * Given a set of distinct integers, S, return all possible subsets.
     * <p/>
     * Note:
     * <p/>
     * Elements in a subset must be in non-descending order.
     * The solution set must not contain duplicate subsets.
     * <p/>
     * For example,
     * If S = [1,2,3], a solution is:
     * <p/>
     * [
     * [3],
     * [1],
     * [2],
     * [1,2,3],
     * [1,3],
     * [2,3],
     * [1,2],
     * []
     * ]
     */
    public ArrayList<ArrayList<Integer>> subsets(int[] S) {
        /*
        ArrayList<ArrayList<Integer>> combos = new ArrayList<ArrayList<Integer>>();
        for (int k = 0; k <= S.length; k++)
            subsetsNofK(S, 0, 0, k, new ArrayList<Integer>(), combos);
        return combos;
        */
        return subsetsBits(S);
    }

    private void subsetsNofK(int[] S, int level, int start, int maxLevel, ArrayList<Integer> combo, ArrayList<ArrayList<Integer>> combos) {
        if (level == maxLevel) {
            ArrayList<Integer> result = new ArrayList<Integer>(combo);
            Collections.sort(result);
            combos.add(result);
            return;
        }

        for (int i = start; i < S.length; i++) {
            combo.add(S[i]);
            subsetsNofK(S, level + 1, i + 1, maxLevel, combo, combos);
            combo.remove((Integer) S[i]);
        }
    }

    private ArrayList<ArrayList<Integer>> subsetsBits(int[] S) {
        long num = 1 << S.length;
        ArrayList<ArrayList<Integer>> combos = new ArrayList<ArrayList<Integer>>();

        for (long i = 0; i < num; i++) {
            ArrayList<Integer> combo = new ArrayList<Integer>();
            for (long n = i, index = 0; n > 0; n >>= 1, index++) {
                if ((n & 1) == 1) combo.add(S[(int) index]);
            }
            Collections.sort(combo);
            combos.add(combo);
        }

        return combos;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/remove-duplicates-from-sorted-array-ii/">Remove Duplicates from Sorted Array II</a>
     * Follow up for "Remove Duplicates":
     * What if duplicates are allowed at most twice?
     * <p/>
     * For example,
     * Given sorted array A = [1,1,1,2,2,3],
     * <p/>
     * Your function should return length = 5, and A is now [1,1,2,2,3].
     */
    public int removeDuplicates(int[] A) {
        LinkedList<Integer> buf = new LinkedList<Integer>();
        int storePointer = 0;
        for (int i = 0; i < A.length; i++) {
            boolean shouldAddIthNumber =
                    i - 1 < 0 ||
                            ((A[i] == A[i - 1] && (i - 2 < 0 || A[i - 1] != A[i - 2]))) ||
                            (A[i] != A[i - 1]);
            if (shouldAddIthNumber) {
                if (buf.size() == 3) {
                    A[storePointer++] = buf.removeFirst();
                }
                buf.add(A[i]);
            }
        }
        for (Integer b : buf) {
            A[storePointer++] = b;
        }
        return storePointer;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/search-in-rotated-sorted-array-ii/">Search in Rotated Sorted Array II</a>
     * Follow up for "Search in Rotated Sorted Array":
     * What if duplicates are allowed?
     * <p/>
     * Would this affect the run-time complexity? How and why?
     * <p/>
     * Write a function to determine if a given target is in the array.
     * <p/>
     * Solution is based on http://oj.leetcode.com/discuss/223/when-there-are-duplicates-the-worst-case-is-could-we-do-better
     */
    public boolean search(int[] A, int target) {
        int l = 0;
        int r = A.length - 1;
        int mid;
        while (l <= r) {
            mid = (l + r) / 2;
            if (A[mid] == target) return true;
            if (A[l] < A[mid]) { // left half is sorted
                if (A[l] <= target && A[mid] > target) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else if (A[l] > A[mid]) { // right half is sorted
                if (A[r] >= target && A[mid] < target) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            } else l++;
        }
        return false;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/remove-duplicates-from-sorted-list/">Remove Duplicates from Sorted List</a>
     * Given a sorted linked list, delete all duplicates such that each element appear only once.
     * <p/>
     * For example,
     * Given 1->1->2, return 1->2.
     * Given 1->1->2->3->3, return 1->2->3.
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode readNode = head.next;
        ListNode writeNode = head;
        writeNode.next = null;
        while (readNode != null) {
            if (writeNode.val != readNode.val) {
                writeNode.next = readNode;
                writeNode = writeNode.next;
            }
            readNode = readNode.next;
        }
        writeNode.next = null;
        return head;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/remove-duplicates-from-sorted-list-ii/">Remove Duplicates from Sorted List II</a>
     * Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.
     * <p/>
     * For example,
     * Given 1->2->3->3->4->4->5, return 1->2->5.
     * Given 1->1->1->2->3, return 2->3.
     */
    public ListNode deleteDuplicatesII(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode readNode = head;
        ListNode writeNode = null;
        ListNode newHead = null;
        ListNode prevNode = null;

        while (readNode != null) {
            boolean shouldWriteNode =
                    (prevNode == null && readNode.next == null) ||
                            ((prevNode == null || prevNode.val != readNode.val)
                                    && (readNode.next == null || readNode.next.val != readNode.val));
            if (shouldWriteNode) {
                if (writeNode == null) {
                    writeNode = readNode;
                } else {
                    writeNode.next = readNode;
                    writeNode = writeNode.next;
                }
                if (newHead == null) {
                    newHead = writeNode;
                }

            }
            if (prevNode == null) {
                prevNode = readNode;
            } else {
                prevNode = prevNode.next;
            }
            readNode = readNode.next;

        }
        if (writeNode != null)
            writeNode.next = null;
        return newHead;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/largest-rectangle-in-histogram/">Largest Rectangle in Histogram</a>
     * Given n non-negative integers representing the histogram's bar height where the width of each bar is 1,
     * find the area of largest rectangle in the histogram.
     * <p/>
     * Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].
     * <p/>
     * The largest rectangle is shown in the shaded area, which has area = 10 unit.
     * <p/>
     * For example,
     * Given height = [2,1,5,6,2,3],
     * return 10.
     * </p>
     * Solution: divide and conquer (pretty much like the maximum subarray problem)
     */
    public int largestRectangleArea(int[] height) {
        if (height == null || height.length == 0) return 0;
        return largestRectangleArea(height, 0, height.length - 1);
    }

    private int largestRectangleArea(int[] A, int l, int r) {
        if (l == r) return A[l];
        int mid = (l + r) / 2;
        int leftArea = largestRectangleArea(A, l, mid);
        int rightArea = largestRectangleArea(A, mid + 1, r);
        int crossingArea = crossingArea(A, l, r, mid);
        return Math.max(leftArea, Math.max(rightArea, crossingArea));
    }

    private int crossingArea(int[] A, int l, int r, int mid) {
        int left = mid;
        int right = mid;
        int leftMin = A[left];
        int rightMin = A[right];
        int bestArea = 0;

        while (left >= l && right <= r) {
            leftMin = Math.min(A[left], leftMin);
            rightMin = Math.min(A[right], rightMin);

            bestArea = Math.max(bestArea,
                    Math.min(leftMin, rightMin) * (right - left + 1));
            if (left > l && (right == r || A[left - 1] > A[right + 1])) {
                left--;
            } else {
                right++;
            }
        }
        return bestArea;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/binary-tree-inorder-traversal/">Binary Tree Inorder Traversal</a>
     * Given a binary tree, return the inorder traversal of its nodes' values.
     * <p/>
     * For example:
     * Given binary tree {1,#,2,3},
     * <p/>
     * 1
     * \
     * 2
     * /
     * 3
     * <p/>
     * return [1,3,2].
     * <p/>
     * Note: Recursive solution is trivial, could you do it iteratively?
     * <p/>
     * confused what "{1,#,2,3}" means? > read more on how binary tree is serialized on OJ.
     */
    public ArrayList<Integer> inorderTraversal(TreeNode root) {
        if (root == null) return new ArrayList<Integer>();
        TreeNode node = root;
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        ArrayList<Integer> inorder = new ArrayList<Integer>();
        boolean done = false;
        while (!done) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                if (stack.isEmpty()) {
                    done = true;
                } else {
                    node = stack.peek();
                    stack.pop();
                    inorder.add(node.val);
                    node = node.right;
                }
            }
        }
        return inorder;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/unique-binary-search-trees/">Unique Binary Search Trees</a>
     * Given n, how many structurally unique BST's (binary search trees) that store values 1...n?
     * <p/>
     * For example,
     * Given n = 3, there are a total of 5 unique BST's.
     * <p/>
     * 1         3     3      2      1
     * \       /     /      / \      \
     * 3     2     1      1   3      2
     * /     /       \                 \
     * 2     1         2                 3
     */
    public int numTrees(int n) {
        if (n < 2) return 1;
        int total = 0;
        for (int i = 0; i < n; i++) {
            total += numTrees(i) * numTrees(n - i - 1);
        }
        return total;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/unique-binary-search-trees-ii/">Unique Binary Search Trees II</a>
     * Given n, generate all structurally unique BST's (binary search trees) that store values 1...n.
     * <p/>
     * For example,
     * Given n = 3, your program should return all 5 unique BST's shown below.
     * <p/>
     * 1         3     3      2      1
     * \       /     /      / \      \
     * 3     2     1      1   3      2
     * /     /       \                 \
     * 2     1         2                 3
     * <p/>
     * confused what "{1,#,2,3}" means? > read more on how binary tree is serialized on OJ.
     */
    public ArrayList<TreeNode> generateTrees(int n) {
        return buildTrees(0, n - 1);
    }

    private ArrayList<TreeNode> buildTrees(int from, int to) {
        if (from > to) {
            ArrayList<TreeNode> list = new ArrayList<TreeNode>();
            list.add(null);
            return list;
        }

        ArrayList<TreeNode> allTrees = new ArrayList<TreeNode>();
        for (int i = from; i <= to; i++) {
            ArrayList<TreeNode> leftSubtrees = buildTrees(from, i - 1);
            ArrayList<TreeNode> rightSubtrees = buildTrees(i + 1, to);
            for (TreeNode leftSubtree : leftSubtrees) {
                for (TreeNode rightSubtree : rightSubtrees) {
                    TreeNode newTree = new TreeNode(i + 1);
                    newTree.left = leftSubtree;
                    newTree.right = rightSubtree;
                    allTrees.add(newTree);
                }
            }
        }

        return allTrees;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/validate-binary-search-tree/">Validate Binary Search Tree</a>
     * Given a binary tree, determine if it is a valid binary search tree (BST).
     * <p/>
     * Assume a BST is defined as follows:
     * <p/>
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     * <p/>
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isValidBST(TreeNode root, int left, int right) {
        if (root == null) return true;
        if (root.val <= left || root.val >= right) return false;
        return isValidBST(root.left, left, root.val) && isValidBST(root.right, root.val, right);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/same-tree/">Same Tree</a>
     * Given two binary trees, write a function to check if they are equal or not.
     * <p/>
     * Two binary trees are considered equal if they are structurally identical and the nodes have the same value.
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null) return false;
        if (q == null) return false;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/">Construct Binary Tree from Preorder and Inorder Traversal</a>
     * Given preorder and inorder traversal of a tree, construct the binary tree.
     * <p/>
     * Note:
     * You may assume that duplicates do not exist in the tree.
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int[] pi = new int[1];
        return buildInPreTree(preorder, inorder, pi, 0, inorder.length - 1);
    }

    private TreeNode buildInPreTree(int[] p, int[] i, int[] pi, int leftIn, int rightIn) {
        if (leftIn > rightIn) return null;

        int val = p[pi[0]];
        TreeNode root = new TreeNode(val);
        pi[0]++;
        int foundIn = -1;
        for (int k = leftIn; k <= rightIn; k++) {
            if (i[k] == val) {
                foundIn = k;
                break;
            }
        }
        root.left = buildInPreTree(p, i, pi, leftIn, foundIn - 1);
        root.right = buildInPreTree(p, i, pi, foundIn + 1, rightIn);

        return root;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/symmetric-tree/">Symmetric Tree</a>
     * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
     * <p/>
     * For example, this binary tree is symmetric:
     * <p/>
     * 1
     * / \
     * 2   2
     * / \ / \
     * 3  4 4  3
     * <p/>
     * But the following is not:
     * <p/>
     * 1
     * / \
     * 2   2
     * \   \
     * 3    3
     * <p/>
     * Note:
     * Bonus points if you could solve it both recursively and iteratively.
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        // return isSymmetricRecursive(root.left, root.right);
        return isSymmetricIterative(root.left, root.right);
    }

    private boolean isSymmetricRecursive(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        if (left.val != right.val) return false;
        return isSymmetricRecursive(left.left, right.right) && isSymmetricRecursive(left.right, right.left);
    }

    private boolean isSymmetricIterative(TreeNode l, TreeNode r) {
        if (l == null && r == null) return true;
        if (l == null || r == null) return false;
        LinkedList<TreeNode> q1 = new LinkedList<TreeNode>();
        q1.add(l);
        LinkedList<TreeNode> q2 = new LinkedList<TreeNode>();
        q2.add(r);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            TreeNode left = q1.removeFirst();
            TreeNode right = q2.removeFirst();
            if (left.val != right.val) return false;
            if (left.left != null && right.right != null) {
                q1.addLast(left.left);
                q2.addLast(right.right);
            } else if (left.left != null || right.right != null) {
                return false;
            }

            if (left.right != null && right.left != null) {
                q1.addLast(left.right);
                q2.addLast(right.left);
            } else if (left.right != null || right.left != null) {
                return false;
            }
        }

        return q1.isEmpty() && q2.isEmpty();

    }

    /**
     * <a href="http://oj.leetcode.com/problems/maximal-rectangle/">Maximal Rectangle</a>
     * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing all ones and return its area.
     */
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return 0;
        int maxRect = 0;
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] intMatrix = new int[m][n];
        for (int row = 0; row < m; row++) {
            int[] histogramRow = new int[n];
            for (int i = 0; i < n; i++) {
                intMatrix[row][i] = matrix[row][i] - '0';
                if (row > 0 && intMatrix[row][i] > 0)
                    intMatrix[row][i] += intMatrix[row - 1][i];
                histogramRow[i] = intMatrix[row][i];
            }
            int maxArea = largestRectangleArea(histogramRow);
            maxRect = Math.max(maxRect, maxArea);
        }
        return maxRect;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/partition-list/">Partition List</a>
     * Given a linked list and a value x, partition it such that all nodes less than x come before nodes greater than or equal to x.
     * <p/>
     * You should preserve the original relative order of the nodes in each of the two partitions.
     * <p/>
     * For example,
     * Given 1->4->3->2->5->2 and x = 3,
     * return 1->2->2->4->3->5.
     */
    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) return head;
        ListNode readNode = head;
        ListNode lessWrite = null;
        ListNode greaterWrite = null;
        ListNode lessWriteHead = null;
        ListNode greaterWriteHead = null;

        while (readNode != null) {
            if (readNode.val < x) {
                if (lessWrite == null) lessWrite = readNode;
                else {
                    lessWrite.next = readNode;
                    lessWrite = lessWrite.next;
                }
                if (lessWriteHead == null) lessWriteHead = readNode;
            } else {
                if (greaterWrite == null) greaterWrite = readNode;
                else {
                    greaterWrite.next = readNode;
                    greaterWrite = greaterWrite.next;
                }
                if (greaterWriteHead == null) greaterWriteHead = readNode;
            }

            readNode = readNode.next;
        }
        if (greaterWrite != null) greaterWrite.next = null;
        if (lessWrite != null) lessWrite.next = null;
        if (greaterWriteHead == null) return lessWriteHead;
        if (lessWriteHead == null) return greaterWriteHead;
        if (lessWrite != null) lessWrite.next = greaterWriteHead;
        return lessWriteHead;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/interleaving-string/">Interleaving String</a>
     * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
     * <p/>
     * For example,
     * Given:
     * s1 = "aabcc",
     * s2 = "dbbca",
     * <p/>
     * When s3 = "aadbbcbcac", return true.
     * When s3 = "aadbbbaccc", return false.
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s1.isEmpty()) return s2.equals(s3);
        if (s2 == null || s2.isEmpty()) return s1.equals(s3);
        int m = s1.length();
        int n = s2.length();
        if (s3.length() != m + n) return false;

        boolean[][] A = new boolean[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 && j == 0) {
                    A[i][j] = true;
                    continue;
                }

                char s3ch = s3.charAt(i + j - 1);
                if (i == 0)
                    A[i][j] = s2.charAt(j - 1) == s3ch && A[i][j - 1];
                else if (j == 0)
                    A[i][j] = s1.charAt(i - 1) == s3ch && A[i - 1][j];
                else if (s1.charAt(i - 1) == s3ch && s2.charAt(j - 1) != s3ch)
                    A[i][j] = A[i - 1][j];
                else if (s1.charAt(i - 1) != s3ch && s2.charAt(j - 1) == s3ch)
                    A[i][j] = A[i][j - 1];
                else if (s1.charAt(i - 1) == s3ch && s2.charAt(j - 1) == s3ch)
                    A[i][j] = A[i][j - 1] || A[i - 1][j];

            }
        }

        return A[m][n];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/reorder-list/">Reorder List</a>
     * Given a singly linked list L: L0→L1→…→Ln-1→Ln,
     * reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
     * <p/>
     * You must do this in-place without altering the nodes' values.
     * <p/>
     * For example,
     * Given {1,2,3,4}, reorder it to {1,4,2,3}.
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        reorderListNoStack(head);
    }

    private void reorderListStack(ListNode head) {
        ListNode slowPointer = head;
        LinkedList<ListNode> stack = new LinkedList<ListNode>();
        int totalNodes = 0;
        while (slowPointer != null) {
            stack.push(slowPointer);
            slowPointer = slowPointer.next;
            totalNodes++;
        }

        while (totalNodes > 0) {
            ListNode lastNode = stack.poll();
            lastNode.next = null;
            ListNode nextNode = head.next;
            if (head.next != lastNode) {
                head.next = lastNode;
                lastNode.next = nextNode;
                head = nextNode;
            }
            totalNodes--;
            totalNodes--;
        }
    }

    // Based on http://oj.leetcode.com/discuss/236/does-this-problem-solution-time-complexity-space-comlexity
    private void reorderListNoStack(ListNode head) {

        ListNode start = head;

        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // slow now points to the second half
        ListNode reverseHead = slow.next;
        ListNode prev = null;
        while (reverseHead != null) {
            ListNode nextRead = reverseHead.next;
            reverseHead.next = prev;
            prev = reverseHead;
            reverseHead = nextRead;
        }

        slow.next = null;

        ListNode secondHalfHead = prev;
        while (secondHalfHead != null) {
            ListNode firstHalfNext = start.next;
            ListNode secondHalfNext = secondHalfHead.next;
            secondHalfHead.next = firstHalfNext;
            start.next = secondHalfHead;
            secondHalfHead = secondHalfNext;
            start = firstHalfNext;
        }

    }

    /**
     * <a href="http://oj.leetcode.com/problems/binary-tree-level-order-traversal/">Binary Tree Level Order Traversal</a>
     * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
     * <p/>
     * For example:
     * Given binary tree {3,9,20,#,#,15,7},
     * <p/>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * <p/>
     * return its level order traversal as:
     * <p/>
     * [
     * [3],
     * [9,20],
     * [15,7]
     * ]
     */
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        if (root == null) return new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        LinkedList<TreeNode> q = new LinkedList<TreeNode>();
        q.add(root);
        int currentLevelNodes = 1;
        int nextLevelNodes = 0;
        ArrayList<Integer> levelTraveral = new ArrayList<Integer>();
        while (!q.isEmpty()) {
            TreeNode nextInQueue = q.removeFirst();
            levelTraveral.add(nextInQueue.val);
            currentLevelNodes--;
            if (nextInQueue.left != null) {
                q.add(nextInQueue.left);
                nextLevelNodes++;
            }
            if (nextInQueue.right != null) {
                q.add(nextInQueue.right);
                nextLevelNodes++;
            }
            if (currentLevelNodes == 0) {
                result.add(levelTraveral);
                levelTraveral = new ArrayList<Integer>();
                currentLevelNodes = nextLevelNodes;
                nextLevelNodes = 0;
            }
        }
        return result;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/scramble-string/">Scramble String</a>
     * Given a string s1, we may represent it as a binary tree by partitioning it to two non-empty substrings recursively.
     * <p/>
     * Below is one possible representation of s1 = "great":
     * <p/>
     * great
     * /    \
     * gr    eat
     * / \    /  \
     * g   r  e   at
     * / \
     * a   t
     * <p/>
     * To scramble the string, we may choose any non-leaf node and swap its two children.
     * <p/>
     * For example, if we choose the node "gr" and swap its two children, it produces a scrambled string "rgeat".
     * <p/>
     * rgeat
     * /    \
     * rg    eat
     * / \    /  \
     * r   g  e   at
     * / \
     * a   t
     * <p/>
     * We say that "rgeat" is a scrambled string of "great".
     * <p/>
     * Similarly, if we continue to swap the children of nodes "eat" and "at", it produces a scrambled string "rgtae".
     * <p/>
     * rgtae
     * /    \
     * rg    tae
     * / \    /  \
     * r   g  ta  e
     * / \
     * t   a
     * <p/>
     * We say that "rgtae" is a scrambled string of "great".
     * <p/>
     * Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1.
     * <p/>
     * Some ideas are based on http://discuss.leetcode.com/questions/262/scramble-string
     */
    public boolean isScramble(String s1, String s2) {
        int l1 = s1.length();
        int l2 = s2.length();

        if (l1 != l2) return false;
        if (l1 < 2) return s1.equals(s2);

        boolean isScramble = false;
        for (int i = 1; i < l1; i++) {
            if (isEqualSet(s1.substring(0, i), s2.substring(0, i))) {
                if (isScramble(s1.substring(0, i), s2.substring(0, i))
                        && isScramble(s1.substring(i), s2.substring(i))) {
                    return true;
                }
            }

            if (isEqualSet(s1.substring(0, i), s2.substring(l1 - i)))
                isScramble = isScramble(s1.substring(0, i), s2.substring(l1 - i))
                        && isScramble(s1.substring(i), s2.substring(0, l1 - i));
            if (isScramble) return true;
        }
        return false;
    }

    private boolean isEqualSet(String s1, String s2) {
        int[] set = new int[256];
        for (char c : s1.toCharArray()) {
            set[c]++;
        }
        for (char c : s2.toCharArray()) {
            if (set[c]-- == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/merge-sorted-array/">Merge Sorted Array</a>
     * Given two sorted integer arrays A and B, merge B into A as one sorted array.
     * <p/>
     * Note:
     * You may assume that A has enough space to hold additional elements from B. The number of elements initialized
     * in A and B are m and n respectively.
     */
    public void merge(int A[], int m, int B[], int n) {
        if (n == 0) return;
        if (m == 0) {
            System.arraycopy(B, 0, A, 0, n);
            return;
        }
        int aIndex = m - 1;
        int bIndex = n - 1;
        int writeIndex = m + n - 1;
        while (aIndex >= 0 || bIndex >= 0) {
            if (aIndex < 0) {
                A[writeIndex--] = B[bIndex--];
            } else if (bIndex < 0) {
                A[writeIndex--] = A[aIndex--];
            } else if (B[bIndex] >= A[aIndex]) {
                A[writeIndex--] = B[bIndex--];
            } else {
                A[writeIndex--] = A[aIndex--];
            }
        }
    }

    /**
     * <a href="http://oj.leetcode.com/problems/gray-code/">Gray Code</a>
     * The gray code is a binary numeral system where two successive values differ in only one bit.
     * <p/>
     * Given a non-negative integer n representing the total number of bits in the code, print the sequence of gray code.
     * A gray code sequence must begin with 0.
     * <p/>
     * For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:
     * <p/>
     * 00 - 0
     * 01 - 1
     * 11 - 3
     * 10 - 2
     * <p/>
     * Note:
     * For a given n, a gray code sequence is not uniquely defined.
     * <p/>
     * For example, [0,2,3,1] is also a valid gray code sequence according to the above definition.
     * <p/>
     * For now, the judge is able to judge based on one instance of gray code sequence. Sorry about that.
     */
    public ArrayList<Integer> grayCode(int n) {
        if (n == 0) return new ArrayList<Integer>();
        if (n == 1) return new ArrayList<Integer>(Arrays.asList(0, 1));
        ArrayList<Integer> previousCodes = grayCode(n - 1);
        ArrayList<Integer> reflectedCodes = new ArrayList<Integer>();
        reflectedCodes.addAll(previousCodes);
        Collections.reverse(previousCodes);
        for (int prevCode : previousCodes) {
            reflectedCodes.add((1 << (n - 1)) + prevCode);
        }
        return reflectedCodes;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/decode-ways/">Decode Ways</a>
     * A message containing letters from A-Z is being encoded to numbers using the following mapping:
     * <p/>
     * 'A' -> 1
     * 'B' -> 2
     * ...
     * 'Z' -> 26
     * <p/>
     * Given an encoded message containing digits, determine the total number of ways to decode it.
     * <p/>
     * For example,
     * Given encoded message "12", it could be decoded as "AB" (1 2) or "L" (12).
     * <p/>
     * The number of ways decoding "12" is 2.
     */
    public int numDecodings(String s) {
        if (s.isEmpty()) return 0;
        Map<String, Integer> memo = new HashMap<String, Integer>();
        return numDecodings(s, memo);
    }

    public int numDecodings(String s, Map<String, Integer> memo) {
        if (memo.containsKey(s)) return memo.get(s);
        if (s.length() == 0) return 1;
        if (s.charAt(0) == '0') return 0;
        if (s.length() == 1) return 1;
        int num = numDecodings(s.substring(1), memo);
        if (s.length() > 1) {
            if (s.charAt(0) < '2') num += numDecodings(s.substring(2), memo);
            else if (s.charAt(0) == '2' && s.charAt(1) <= '6') num += numDecodings(s.substring(2), memo);
        }
        memo.put(s, num);
        return num;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/subsets-ii/">Subsets II</a>
     * Given a collection of integers that might contain duplicates, S, return all possible subsets.
     * <p/>
     * Note:
     * <p/>
     * Elements in a subset must be in non-descending order.
     * The solution set must not contain duplicate subsets.
     * <p/>
     * For example,
     * If S = [1,2,2], a solution is:a
     * <p/>
     * [
     * [2],
     * [1],
     * [1,2,2],
     * [2,2],
     * [1,2],
     * []
     * ]
     * <p/>
     * Solution is based on the following idea: http://xiaotong-blog.herokuapp.com/posts/44
     */
    public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] num) {
        if (num.length == 0) return new ArrayList<ArrayList<Integer>>();
        Arrays.sort(num);

        ArrayList<ArrayList<Integer>> prev = new ArrayList<ArrayList<Integer>>();
        prev.add(new ArrayList<Integer>(Arrays.asList(num[0])));
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        result.add(new ArrayList<Integer>());
        result.addAll(prev);

        int prevNum = num[0];

        for (int i = 1; i < num.length; i++) {
            if (num[i] == prevNum) {
                ArrayList<ArrayList<Integer>> newPowerset = new ArrayList<ArrayList<Integer>>();
                for (ArrayList<Integer> prevSet : prev) {
                    ArrayList<Integer> newSet = new ArrayList<Integer>(prevSet);
                    newSet.add(num[i]);
                    newPowerset.add(newSet);
                }
                prev = newPowerset;
            } else {
                prev = new ArrayList<ArrayList<Integer>>();
                ArrayList<ArrayList<Integer>> resultCopy = new ArrayList<ArrayList<Integer>>(result);

                for (ArrayList<Integer> set : resultCopy) {
                    ArrayList<Integer> newSet = new ArrayList<Integer>(set);
                    newSet.add(num[i]);
                    prev.add(newSet);
                }
            }
            result.addAll(prev);
            prevNum = num[i];
        }

        return result;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/restore-ip-addresses/">Restore IP Addresses</a>
     * Given a string containing only digits, restore it by returning all possible valid IP address combinations.
     * <p/>
     * For example:
     * Given "25525511135",
     * <p/>
     * return ["255.255.11.135", "255.255.111.35"]. (Order does not matter)
     */
    public ArrayList<String> restoreIpAddresses(String s) {
        ArrayList<String> list = new ArrayList<String>();
        restoreIp(s, list, new ArrayList<String>(), 0);
        return list;
    }

    private void restoreIp(String s, ArrayList<String> combos, ArrayList<String> parts, int level) {
        if (level == 4) {
            if (s.length() == 0)
                combos.add(parts.get(0) + "." + parts.get(1) + "." + parts.get(2) + "." + parts.get(3));
            return;
        }

        if (s.length() == 0) return;

        // try only one digit
        backtrackRestore(s, 1, combos, parts, level);

        if (s.charAt(0) == '1') {
            // try 1*
            if (s.length() > 1) {
                backtrackRestore(s, 2, combos, parts, level);
                // try 1**
                if (s.length() > 2) {
                    backtrackRestore(s, 3, combos, parts, level);
                }
            }
        } else if (s.charAt(0) == '2') {
            // try 2*
            if (s.length() > 1) {
                backtrackRestore(s, 2, combos, parts, level);
                // try up to 255
                if (s.length() > 2 && (s.charAt(1) < '5' || (s.charAt(1) == '5' && s.charAt(2) < '6'))) {
                    backtrackRestore(s, 3, combos, parts, level);
                }
            }
        } else if (s.charAt(0) != '0') { // there can be only one leading zero (already covered)
            // try [3-9][0-9] (2 digits)
            if (s.length() > 1) {
                backtrackRestore(s, 2, combos, parts, level);
            }
        }
    }

    private void backtrackRestore(String s, int len, ArrayList<String> combos, ArrayList<String> parts, int level) {
        parts.add(s.substring(0, len));
        restoreIp(s.substring(len), combos, parts, level + 1);
        parts.remove(parts.size() - 1);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/single-number/">Single Number</a>
     * Given an array of integers, every element appears twice except for one. Find that single one.
     * <p/>
     * Note:
     * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
     */
    public int singleNumber(int[] A) {
        int singleNum = 0;
        for (int a : A) singleNum ^= a;
        return singleNum;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/single-number-ii/">Single Number II</a>
     * Given an array of integers, every element appears three times except for one. Find that single one.
     * <p/>
     * Note:
     * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
     * <p/>
     * Solution is based on http://oj.leetcode.com/discuss/857/constant-space-solution
     */
    public int singleNumberII(int[] A) {
        int[] count = new int[32];
        int result = 0;
        for (int i = 0; i < 32; i++) {
            for (int a : A) {
                count[i] += ((a >> i) & 1);
            }
            result |= ((count[i] % 3) << i);
        }
        return result;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/maximum-depth-of-binary-tree/">Maximum Depth of Binary Tree</a>
     * Given a binary tree, find its maximum depth.
     * <p/>
     * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * <a href="http://oj.leetcode.com/problems/minimum-depth-of-binary-tree/">Minimum Depth of Binary Tree</a>
     * Given a binary tree, find its minimum depth.
     * <p/>
     * The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
     */
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        return minDepth(root, 1);
    }

    private int minDepth(TreeNode root, int level) {
        if (root == null) return level - 1;
        int leftDepth = minDepth(root.left, level + 1);
        int rightDepth = minDepth(root.right, level + 1);

        if (leftDepth == level) return rightDepth;
        if (rightDepth == level) return leftDepth;
        return Math.min(leftDepth, rightDepth);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/binary-tree-level-order-traversal-ii/">Binary Tree Level Order Traversal II</a>
     * Given a binary tree, return the bottom-up level order traversal of its nodes' values. (ie, from left to right, level by level from leaf to root).
     * For example:
     * Given binary tree {3,9,20,#,#,15,7},
     * <p/>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * <p/>
     * return its bottom-up level order traversal as:
     * <p/>
     * [
     * [15,7]
     * [9,20],
     * [3],
     * ]
     */
    public ArrayList<ArrayList<Integer>> levelOrderBottom(TreeNode root) {
        if (root == null) return new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        ArrayList<TreeNode> queue = new ArrayList<TreeNode>();
        ArrayList<Integer> levelPrint = new ArrayList<Integer>();
        int currCount = 1;
        int nextCount = 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove(0);
            levelPrint.add(node.val);
            if (node.left != null) {
                queue.add(node.left);
                nextCount++;
            }
            if (node.right != null) {
                queue.add(node.right);
                nextCount++;
            }

            currCount--;
            if (currCount == 0) {
                result.add(0, new ArrayList<Integer>(levelPrint));
                levelPrint = new ArrayList<Integer>();
                currCount = nextCount;
                nextCount = 0;
            }
        }

        return result;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/binary-tree-zigzag-level-order-traversal/">Binary Tree Zigzag Level Order Traversal</a>
     * Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).
     * <p/>
     * For example:
     * Given binary tree {3,9,20,#,#,15,7},
     * <p/>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * <p/>
     * return its zigzag level order traversal as:
     * <p/>
     * [
     * [3],
     * [20,9],
     * [15,7]
     * ]
     */
    public ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) return new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        ArrayList<TreeNode> queue = new ArrayList<TreeNode>();
        ArrayList<Integer> levelPrint = new ArrayList<Integer>();
        int currCount = 1;
        int nextCount = 0;
        queue.add(root);
        boolean isLeftOriented = false;
        while (!queue.isEmpty()) {
            TreeNode node = queue.remove(0);
            if (isLeftOriented)
                levelPrint.add(0, node.val);
            else
                levelPrint.add(node.val);
            if (node.left != null) {
                queue.add(node.left);
                nextCount++;
            }
            if (node.right != null) {
                queue.add(node.right);
                nextCount++;
            }

            currCount--;
            if (currCount == 0) {
                isLeftOriented = !isLeftOriented;
                result.add(new ArrayList<Integer>(levelPrint));
                levelPrint = new ArrayList<Integer>();
                currCount = nextCount;
                nextCount = 0;
            }
        }

        return result;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/convert-sorted-array-to-binary-search-tree/">Convert Sorted Array to Binary Search Tree</a>
     * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
     */
    public TreeNode sortedArrayToBST(int[] num) {
        return sortedArrayToBST(num, 0, num.length - 1);
    }

    private TreeNode sortedArrayToBST(int[] num, int from, int to) {
        if (from > to) return null;
        int mid = (to + from) / 2;
        TreeNode root = new TreeNode(num[mid]);
        root.left = sortedArrayToBST(num, from, mid - 1);
        root.right = sortedArrayToBST(num, mid + 1, to);
        return root;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/balanced-binary-tree/">Balanced Binary Tree</a>
     * Given a binary tree, determine if it is height-balanced.
     * <p/>
     * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two
     * subtrees of every node never differ by more than 1.
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        if (Math.abs(leftDepth - rightDepth) <= 1
                && isBalanced(root.left)
                && isBalanced(root.right)) return true;
        return false;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/linked-list-cycle/">Linked List Cycle</a>
     * Given a linked list, determine if it has a cycle in it.
     * <p/>
     * Follow up:
     * Can you solve it without using extra space?
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode slowPointer = head;
        ListNode fastPointer = head.next;

        while (slowPointer != null && fastPointer != null && fastPointer.next != null) {
            if (slowPointer == fastPointer) return true;
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }
        return false;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/linked-list-cycle-ii/">Linked List Cycle II</a>
     * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
     * <p/>
     * Follow up:
     * Can you solve it without using extra space?
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == head) return head;
        if (head.next == null) return null;
        ListNode slow = head;
        ListNode fast = head;
        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }
        if (fast == null || slow != fast) return null;

        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/longest-consecutive-sequence/">Longest Consecutive Sequence</a>
     * Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
     * <p/>
     * For example,
     * Given [100, 4, 200, 1, 3, 2],
     * The longest consecutive elements sequence is [1, 2, 3, 4]. Return its length: 4.
     * <p/>
     * Your algorithm should run in O(n) complexity.
     * <p/>
     * Solution idea: introduce a map with key=v and value=[min to max interval for this value]. As new entries are inserted
     * we need only update min/max of v-1 and/or v+1 entries keeping calculating max-min.
     */
    public int longestConsecutive(int[] num) {
        if (num.length == 0) return 0;
        int longestSequence = 1;
        Map<Integer, int[]> intervalMap = new HashMap<Integer, int[]>();

        for (int v : num) {
            if (intervalMap.containsKey(v)) continue;
            int begin = v;
            int end = v;
            if (intervalMap.containsKey(v - 1)) {
                int[] prevInterval = intervalMap.get(v - 1);
                intervalMap.put(v, new int[]{prevInterval[0], v});
                begin = prevInterval[0];
            }

            if (intervalMap.containsKey(v + 1)) {
                int[] nextInterval = intervalMap.get(v + 1);
                intervalMap.put(v, new int[]{begin, nextInterval[1]});
                end = nextInterval[1];
            }

            if (intervalMap.containsKey(begin)) {
                intervalMap.get(begin)[1] = end;
            }

            if (intervalMap.containsKey(end)) {
                intervalMap.get(end)[0] = begin;
            }

            if (end - begin + 1 > longestSequence)
                longestSequence = end - begin + 1;

            if (!intervalMap.containsKey(v - 1) && !intervalMap.containsKey(v + 1))
                intervalMap.put(v, new int[]{v, v});
        }

        return longestSequence;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/path-sum/">Path Sum</a>
     * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values
     * along the path equals the given sum.
     * For example:
     * Given the below binary tree and sum = 22,
     * <p/>
     * 5
     * / \
     * 4   8
     * /   / \
     * 11  13  4
     * /  \      \
     * 7    2      1
     * <p/>
     * return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        return hasPathSumRecursive(root, sum);
    }

    public boolean hasPathSumRecursive(TreeNode root, int sum) {
        if (root == null)
            return sum == 0;
        if (root.left == null)
            return hasPathSumRecursive(root.right, sum - root.val);
        if (root.right == null)
            return hasPathSumRecursive(root.left, sum - root.val);
        return hasPathSumRecursive(root.left, sum - root.val) || hasPathSumRecursive(root.right, sum - root.val);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/convert-sorted-list-to-binary-search-tree/">Convert Sorted List to Binary Search Tree</a>
     * Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        int len = 0;
        ListNode current = head;
        while (current != null) {
            len++;
            current = current.next;
        }
        return sortedListToBST(new ListNode[]{head}, 0, len);
    }

    private TreeNode sortedListToBST(ListNode[] head, int from, int to) {
        if (from > to || head[0] == null) return null;
        int mid = (from + to) / 2;
        TreeNode left = sortedListToBST(head, from, mid - 1);
        TreeNode node = new TreeNode(head[0].val);
        head[0] = head[0].next;
        TreeNode right = sortedListToBST(head, mid + 1, to);
        node.left = left;
        node.right = right;
        return node;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/reverse-linked-list-ii/">Reverse Linked List II</a>
     * Reverse a linked list from position m to n. Do it in-place and in one-pass.
     * <p/>
     * For example:
     * Given 1->2->3->4->5->NULL, m = 2 and n = 4,
     * <p/>
     * return 1->4->3->2->5->NULL.
     * <p/>
     * Note:
     * Given m, n satisfy the following condition:
     * 1 ≤ m ≤ n ≤ length of list.
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head.next == null) return head;
        if (m == n) return head;
        ListNode current = head;
        int startReverse = m;
        ListNode prev = null;
        while (startReverse > 1) {
            prev = current;
            current = current.next;
            startReverse--;
        }

        ListNode reverseHead = current;
        for (; m < n; m++) {
            ListNode next = current.next;
            current.next = next.next;
            next.next = reverseHead;
            reverseHead = next;
        }

        if (prev == null) {
            return reverseHead;
        } else {
            prev.next = reverseHead;
            return head;
        }
    }

    /**
     * <a href="http://oj.leetcode.com/problems/path-sum-ii/">Path Sum II</a>
     * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
     * For example:
     * Given the below binary tree and sum = 22,
     * <p/>
     * 5
     * / \
     * 4   8
     * /   / \
     * 11  13  4
     * /  \    / \
     * 7    2  5   1
     * <p/>
     * return
     * <p/>
     * [
     * [5,4,11,2],
     * [5,8,4,5]
     * ]
     */
    public ArrayList<ArrayList<Integer>> pathSum(TreeNode root, int sum) {
        if (root == null) return new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
        pathSum(root, sum, new ArrayList<Integer>(), paths);
        return paths;
    }

    private void pathSum(TreeNode root, int sum, ArrayList<Integer> path, ArrayList<ArrayList<Integer>> paths) {
        if (root == null) {
            if (sum == 0)
                paths.add(new ArrayList<Integer>(path));
            return;
        }

        path.add(root.val);
        if (root.left != null)
            pathSum(root.left, sum - root.val, path, paths);
        if (root.right != null)
            pathSum(root.right, sum - root.val, path, paths);
        if (root.right == null && root.left == null)
            pathSum(null, sum - root.val, path, paths);
        path.remove(path.size() - 1);

    }

    /**
     * <a href="http://oj.leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/">Construct Binary Tree from Inorder and Postorder Traversal</a>
     * Given inorder and postorder traversal of a tree, construct the binary tree.
     * <p/>
     * Note:
     * You may assume that duplicates do not exist in the tree.
     * <p/>
     * Solution: process postorder from right to left. The rightmost is the root. Then find the number of elements
     * in the right subtree by searching for the value in inorder and getting the number of elements from this index to
     * the "to" index in inorder.
     */
    public TreeNode buildTreePost(int[] inorder, int[] postorder) {
        int n = inorder.length - 1;
        return buildTreePost(inorder, 0, n, postorder, new int[]{n});
    }

    public TreeNode buildTreePost(int[] inorder, int inFrom, int inTo, int[] postorder, int[] postIndex) {
        if (inFrom > inTo || postIndex[0] < 0) return null;
        TreeNode root = new TreeNode(postorder[postIndex[0]]);
        int inIndex = getIndex(inorder, postorder[postIndex[0]]);
        postIndex[0]--;
        // the order here matters: process right first
        TreeNode right = buildTreePost(inorder, inIndex + 1, inTo, postorder, postIndex);
        TreeNode left = buildTreePost(inorder, inFrom, inIndex - 1, postorder, postIndex);
        root.right = right;
        root.left = left;
        return root;
    }

    private int getIndex(int[] in, int value) {
        for (int i = 0; i < in.length; i++) {
            if (in[i] == value) return i;
        }
        return -1;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/word-break/">Word Break</a>
     * Given a string s and a dictionary of words dict, determine if s can be segmented into a space-separated
     * sequence of one or more dictionary words.
     * <p/>
     * For example, given
     * s = "leetcode",
     * dict = ["leet", "code"].
     * <p/>
     * Return true because "leetcode" can be segmented as "leet code".
     */
    public boolean wordBreak(String s, Set<String> dict) {
        if (s == null) return false;
        if (s.isEmpty() && dict.isEmpty()) return true;
        if (dict.isEmpty()) return false;

        boolean[] A = new boolean[s.length() + 1];
        A[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (A[j] && dict.contains(s.substring(j, i))) {
                    A[i] = true;
                    break;
                }
            }

        }
        return A[s.length()];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/word-break-ii/">Word Break II</a>
     * Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where each word is a valid dictionary word.
     * <p/>
     * Return all such possible sentences.
     * <p/>
     * For example, given
     * s = "catsanddog",
     * dict = ["cat", "cats", "and", "sand", "dog"].
     * <p/>
     * A solution is ["cats and dog", "cat sand dog"].
     */
    public ArrayList<String> wordBreakII(String s, Set<String> dict) {
        if (s == null || s.isEmpty() || dict.isEmpty()) return new ArrayList<String>();
        if (!wordBreak(s, dict)) return new ArrayList<String>();

        ArrayList<String>[] solutions = (ArrayList<String>[]) new ArrayList[s.length() + 1];
        for (int i = 0; i < solutions.length; i++) solutions[i] = new ArrayList<String>();
        boolean[] A = new boolean[s.length() + 1];
        A[0] = true;

        for (int i = 1; i <= s.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (A[j] && dict.contains(s.substring(j, i))) {
                    if (solutions[j].isEmpty()) {
                        solutions[i].add(s.substring(j, i));
                    } else {
                        for (int k = 0; k < solutions[j].size(); k++)
                            solutions[i].add(solutions[j].get(k) + " " + s.substring(j, i));
                    }
                    A[i] = true;
                }
            }

        }
        return solutions[s.length()];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/flatten-binary-tree-to-linked-list/">Flatten Binary Tree to Linked List</a>
     * Given a binary tree, flatten it to a linked list in-place.
     * <p/>
     * For example,
     * Given
     * <p/>
     * 1
     * / \
     * 2   5
     * / \   \
     * 3   4   6
     * <p/>
     * The flattened tree should look like:
     * <p/>
     * 1
     * \
     * 2
     * \
     * 3
     * \
     * 4
     * \
     * 5
     * \
     * 6
     */
    public void flatten(TreeNode root) {
        if (root == null) return;

        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        stack.push(root);
        TreeNode prevNode = null;
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
            if (prevNode != null) {
                prevNode.left = null;
                prevNode.right = node;
            }
            prevNode = node;
        }

    }

    /**
     * <a href="http://oj.leetcode.com/problems/binary-tree-maximum-path-sum/">Binary Tree Maximum Path Sum</a>
     * Given a binary tree, find the maximum path sum.
     * <p/>
     * The path may start and end at any node in the tree.
     * <p/>
     * For example:
     * Given the below binary tree,
     * <p/>
     * 1
     * / \
     * 2   3
     * <p/>
     * Return 6.
     * <p/>
     * Solution is based on http://discuss.leetcode.com/questions/288/binary-tree-maximum-path-sum/783
     */
    public int maxPathSum(TreeNode root) {
        int max[] = new int[]{Integer.MIN_VALUE};
        maxPathSum(root, max);
        return max[0];
    }

    private int maxPathSum(TreeNode root, int[] max) {
        if (root == null) return 0;
        int leftSum = Math.max(0, maxPathSum(root.left, max));
        int rightSum = Math.max(0, maxPathSum(root.right, max));
        max[0] = Math.max(max[0], root.val + leftSum + rightSum);
        return Math.max(root.val + leftSum, root.val + rightSum);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/sum-root-to-leaf-numbers/">Sum Root to Leaf Numbers</a>
     * Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
     * <p/>
     * An example is the root-to-leaf path 1->2->3 which represents the number 123.
     * <p/>
     * Find the total sum of all root-to-leaf numbers.
     * <p/>
     * For example,
     * <p/>
     * 1
     * / \
     * 2   3
     * <p/>
     * The root-to-leaf path 1->2 represents the number 12.
     * The root-to-leaf path 1->3 represents the number 13.
     * <p/>
     * Return the sum = 12 + 13 = 25.
     */
    public int sumNumbers(TreeNode root) {
        if (root == null) return 0;
        int soFar = 0;
        int[] totalSum = new int[]{0};
        sumNumbersInternal(root, soFar, totalSum);
        return totalSum[0];
    }

    private void sumNumbersInternal(TreeNode root, int sumSoFar, int[] totalSum) {
        if (root.left == null && root.right == null) {
            totalSum[0] += sumSoFar * 10 + root.val;
            return;
        }
        sumSoFar = sumSoFar * 10 + root.val;
        if (root.left != null) sumNumbersInternal(root.left, sumSoFar, totalSum);
        if (root.right != null) sumNumbersInternal(root.right, sumSoFar, totalSum);

    }

    /**
     * Definition for singly-linked list with a random pointer.
     */
    static class RandomListNode {
        int label;
        RandomListNode next, random;

        RandomListNode(int x) {
            this.label = x;
        }
    }

    /**
     * <a href="http://oj.leetcode.com/problems/copy-list-with-random-pointer/">Copy List with Random Pointer</a>
     * A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.
     * <p/>
     * Return a deep copy of the list.
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        return copyRandomListGood(head);
    }

    private RandomListNode copyRandomListBad(RandomListNode head) {
        if (head == null) return null;

        RandomListNode newList = new RandomListNode(head.label);
        RandomListNode current = head.next;
        RandomListNode newCurrent = newList;

        while (current != null) {
            newCurrent.next = new RandomListNode(current.label);
            newCurrent = newCurrent.next;
            current = current.next;
        }

        current = head;
        newCurrent = newList;
        while (current != null) {
            if (current.random != null) {
                RandomListNode currentCounter = head;
                RandomListNode newCounter = newList;
                while (currentCounter != current.random) {
                    currentCounter = currentCounter.next;
                    newCounter = newCounter.next;
                }
                newCurrent.random = newCounter;
            }

            current = current.next;
            newCurrent = newCurrent.next;
        }

        return newList;
    }

    private RandomListNode copyRandomListGood(RandomListNode head) {
        if (head == null) return null;

        // insert copies between original nodes
        RandomListNode current = head;
        while (current != null) {
            RandomListNode copy = new RandomListNode(current.label);
            copy.next = current.next;
            current.next = copy;
            current = copy.next;
        }

        // add random links to copies
        current = head;
        RandomListNode newHead = head.next;
        while (current != null) {
            if (current.random != null)
                current.next.random = current.random.next;
            current = current.next.next;
        }

        // separate the merged list into two
        current = head;
        while (current != null) {
            RandomListNode nextOrig = current.next.next;
            if (nextOrig != null) current.next.next = nextOrig.next;
            current.next = nextOrig;
            current = current.next;
        }

        return newHead;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/recover-binary-search-tree/">Recover Binary Search Tree</a>
     * Two elements of a binary search tree (BST) are swapped by mistake.
     * <p/>
     * Recover the tree without changing its structure.
     * Note:
     * A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?
     */
    public void recoverTree(TreeNode root) {
        if (root == null) return;
        TreeNode[] pre = new TreeNode[1];
        TreeNode[] wrongOne = new TreeNode[1];
        TreeNode[] wrongTwo = new TreeNode[1];
        inOrder(root, pre, wrongOne, wrongTwo);
        if (wrongTwo[0] == null) wrongTwo[0] = pre[0];
        int tmp = wrongOne[0].val;
        wrongOne[0].val = wrongTwo[0].val;
        wrongTwo[0].val = tmp;
    }

    private void inOrder(TreeNode root, TreeNode[] pre, TreeNode[] wrongOne, TreeNode[] wrongTwo) {
        if (root == null) return;
        inOrder(root.left, pre, wrongOne, wrongTwo);

        if (pre[0] != null && root.val < pre[0].val && wrongOne[0] == null)
            wrongOne[0] = pre[0];
        else if (wrongOne[0] != null && root.val > wrongOne[0].val) {
            wrongTwo[0] = pre[0];
            return;
        }
        pre[0] = root;

        inOrder(root.right, pre, wrongOne, wrongTwo);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/triangle/">Triangle</a>
     * Given a triangle, find the minimum path sum from top to bottom. Each step you may move to adjacent numbers on the row below.
     * <p/>
     * For example, given the following triangle
     * <p/>
     * [
     * [2],
     * [3,4],
     * [6,5,7],
     * [4,1,8,3]
     * ]
     * <p/>
     * The minimum path sum from top to bottom is 11 (i.e., 2 + 3 + 5 + 1 = 11).
     * <p/>
     * Note:
     * Bonus point if you are able to do this using only O(n) extra space, where n is the total number of rows in the triangle.
     */
    public int minimumTotal(ArrayList<ArrayList<Integer>> triangle) {
/*
        int minTotal = Integer.MAX_VALUE;
        int maxLevel = triangle.size() - 1;
        for (int index = 0; index < triangle.get(maxLevel).size(); index++) {
            minTotal = Math.min(minTotal, minTotalRecursive(triangle, maxLevel, index, new int[maxLevel + 1]));
        }
        return minTotal;
*/
        return minimumTotalBottomUp(triangle);
    }

    private int minTotalRecursive(ArrayList<ArrayList<Integer>> triangle, int level, int index) {
        if (level == 0) return triangle.get(level).get(0);
        int min = triangle.get(level).get(index);

        if (index > 0 && index < level) min += Math.min(
                minTotalRecursive(triangle, level - 1, index - 1),
                minTotalRecursive(triangle, level - 1, index));
        else if (index == 0)
            min += minTotalRecursive(triangle, level - 1, index);
        else
            min += minTotalRecursive(triangle, level - 1, index - 1);
        return min;
    }

    /**
     * Based on http://www.programcreek.com/2013/01/leetcode-triangle-java/
     */
    private int minimumTotalBottomUp(ArrayList<ArrayList<Integer>> triangle) {
        int s = triangle.size();
        int[] total = new int[s];
        for (int i = 0; i < s; i++) total[i] = triangle.get(s - 1).get(i);
        for (int l = s - 2; l >= 0; l--) {
            for (int i = 0; i <= l; i++) {
                total[i] = triangle.get(l).get(i) + Math.min(total[i], total[i + 1]);
            }
        }

        return total[0];
    }

    /**
     * Definition for binary tree with next pointer.
     */
    static class TreeLinkNode {
        int val;
        TreeLinkNode left, right, next;

        TreeLinkNode(int x) {
            val = x;
        }

        TreeLinkNode(int x, TreeLinkNode left, TreeLinkNode right) {
            val = x;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "" + val;
        }
    }

    /**
     * <a href="http://oj.leetcode.com/problems/populating-next-right-pointers-in-each-node/">Populating Next Right Pointers in Each Node</a>
     * Given a binary tree
     * <p/>
     * struct TreeLinkNode {
     * TreeLinkNode *left;
     * TreeLinkNode *right;
     * TreeLinkNode *next;
     * }
     * <p/>
     * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
     * <p/>
     * Initially, all next pointers are set to NULL.
     * <p/>
     * Note:
     * <p/>
     * You may only use constant extra space.
     * You may assume that it is a perfect binary tree (ie, all leaves are at the same level, and every parent has two children).
     * <p/>
     * For example,
     * Given the following perfect binary tree,
     * <p/>
     * 1
     * /  \
     * 2    3
     * / \  / \
     * 4  5  6  7
     * <p/>
     * After calling your function, the tree should look like:
     * <p/>
     * 1 -> NULL
     * /  \
     * 2 -> 3 -> NULL
     * / \  / \
     * 4->5->6->7 -> NULL
     */
    public void connect(TreeLinkNode root) {
        if (root == null) return;
        connect(root.left, root.right);
    }

    private void connect(TreeLinkNode left, TreeLinkNode right) {
        if (left == null) return;
        left.next = right;
        connect(left.left, left.right);
        connect(left.right, right.left);
        connect(right.left, right.right);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/">Populating Next Right Pointers in Each Node II</a>
     * Follow up for problem "Populating Next Right Pointers in Each Node".
     * <p/>
     * What if the given tree could be any binary tree? Would your previous solution still work?
     * <p/>
     * Note:
     * <p/>
     * You may only use constant extra space.
     * <p/>
     * For example,
     * Given the following binary tree,
     * <p/>
     * 1
     * /  \
     * 2    3
     * / \    \
     * 4   5    7
     * <p/>
     * After calling your function, the tree should look like:
     * <p/>
     * 1 -> NULL
     * /  \
     * 2 -> 3 -> NULL
     * / \    \
     * 4-> 5 -> 7 -> NULL
     */
    public void connectII(TreeLinkNode root) {
        if (root == null) return;
        TreeLinkNode left = null;
        TreeLinkNode current = root;
        TreeLinkNode nextSibling = null;
        TreeLinkNode prevSibling;
        while (current != null) {
            if (current.left != null) {
                prevSibling = nextSibling;
                nextSibling = current.left;
                if (prevSibling != null) prevSibling.next = nextSibling;
                if (left == null) left = current.left;
            }
            if (current.right != null) {
                prevSibling = nextSibling;
                nextSibling = current.right;
                if (prevSibling != null) prevSibling.next = nextSibling;
                if (left == null) left = current.right;
            }
            current = current.next;
        }

        connectII(left);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/pascals-triangle/">Pascal's Triangle</a>
     * Given numRows, generate the first numRows of Pascal's triangle.
     * <p/>
     * For example, given numRows = 5,
     * Return
     * <p/>
     * [
     * [1],
     * [1,1],
     * [1,2,1],
     * [1,3,3,1],
     * [1,4,6,4,1]
     * ]
     */
    public ArrayList<ArrayList<Integer>> generate(int numRows) {
        if (numRows == 0) return new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> prevRow = new ArrayList<Integer>();
        prevRow.add(1);
        result.add(prevRow);
        for (int i = 1; i < numRows; i++) {
            ArrayList<Integer> newRow = new ArrayList<Integer>();
            newRow.add(1);
            for (int j = 1; j < i; j++) {
                newRow.add(j, prevRow.get(j - 1) + prevRow.get(j));
            }
            newRow.add(1);
            result.add(newRow);
            prevRow = newRow;
        }

        return result;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/pascals-triangle-ii/">Pascal's Triangle II</a>
     * Given an index k, return the kth row of the Pascal's triangle.
     * <p/>
     * For example, given k = 3,
     * Return [1,3,3,1].
     * <p/>
     * Note:
     * Could you optimize your algorithm to use only O(k) extra space?
     */
    public ArrayList<Integer> getRow(int rowIndex) {
        int[] column = new int[rowIndex + 1];
        int[] prevColumn = new int[rowIndex + 1];
        for (int i = 0; i < column.length; i++) prevColumn[i] = 1;
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(1);
        for (int col = 1; col <= rowIndex / 2; col++) {
            column[col] = 1;
            for (int row = col + 1; row <= rowIndex; row++) {
                column[row] = column[row - 1] + prevColumn[row - 1];
            }
            result.add(column[rowIndex]);
            System.arraycopy(column, 0, prevColumn, 0, column.length);
        }
        ArrayList<Integer> reverse = new ArrayList<Integer>(result);
        Collections.reverse(reverse);
        if (rowIndex % 2 == 0) reverse.remove(0);
        result.addAll(reverse);
        return result;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/valid-palindrome/">Valid Palindrome</a>
     * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
     * <p/>
     * For example,
     * "A man, a plan, a canal: Panama" is a palindrome.
     * "race a car" is not a palindrome.
     * <p/>
     * Note:
     * Have you consider that the string might be empty? This is a good question to ask during an interview.
     * <p/>
     * For the purpose of this problem, we define empty string as valid palindrome.
     */
    public boolean isPalindrome(String s) {
        int l = 0;
        int r = s.length() - 1;
        while (l < r) {
            if (!isAlphanumeric(s.charAt(l))) {
                l++;
                continue;
            }
            if (!isAlphanumeric(s.charAt(r))) {
                r--;
                continue;
            }
            if (s.substring(l, l + 1).equalsIgnoreCase(s.substring(r, r + 1))) {
                l++;
                r--;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean isAlphanumeric(char ch) {
        return (ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }

    /**
     * <a href="http://oj.leetcode.com/problems/surrounded-regions/">Surrounded Regions</a>
     * Given a 2D board containing 'X' and 'O', capture all regions surrounded by 'X'.
     * <p/>
     * A region is captured by flipping all 'O's into 'X's in that surrounded region .
     * <p/>
     * For example,
     * <p/>
     * X X X X
     * X O O X
     * X X O X
     * X O X X
     * <p/>
     * After running your function, the board should be:
     * <p/>
     * X X X X
     * X X X X
     * X X X X
     * X O X X
     * </p>
     * Solution: think opposite: how to find all regions NOT surrounded by 'X'? Those are regions having at least one 'O'
     * adjacent to a border. So, first pass: find all 'O' adjacent to borders, do DFS/BFS, mark as 'A'. Then mark the rest
     * ('X' and 'O') as 'X', then mark 'A' back to 'O'.
     */
    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;

        // Pass 1. Find all adjacent 'O' to borders, do BFS and and mark as 'A'
        for (int i = 0; i < board[0].length; i++)
            if (board[0][i] == 'O')
                dfs(board, 0, i);
        for (int i = 0; i < board[0].length; i++)
            if (board[board.length - 1][i] == 'O')
                dfs(board, board.length - 1, i);
        for (int i = 0; i < board.length; i++)
            if (board[i][0] == 'O')
                dfs(board, i, 0);
        for (int i = 0; i < board.length; i++)
            if (board[i][board[0].length - 1] == 'O')
                dfs(board, i, board[0].length - 1);

        // Pass 2. Mark all oather 'O' as 'X' (not adjacent to borders)
        // Pass 3. Mark all 'A' as 'O' (adjacent to borders)
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                if (board[i][j] == 'A') board[i][j] = 'O';
            }
    }

    private void dfs(char[][] board, int i, int j) {
        LinkedList<int[]> queue = new LinkedList<int[]>();
        queue.add(new int[]{i, j});
        while (!queue.isEmpty()) {
            int[] coord = queue.removeLast();
            int y = coord[0];
            int x = coord[1];
            board[y][x] = 'A';
            if (y > 0 && board[y - 1][x] == 'O')
                queue.add(new int[]{y - 1, x});
            if (x > 0 && board[y][x - 1] == 'O')
                queue.add(new int[]{y, x - 1});
            if (y < board.length - 1 && board[y + 1][x] == 'O')
                queue.add(new int[]{y + 1, x});
            if (x < board[0].length - 1 && board[y][x + 1] == 'O')
                queue.add(new int[]{y, x + 1});
        }
    }

    /**
     * Definition for undirected graph.
     */
    static class UndirectedGraphNode {
        int label;
        ArrayList<UndirectedGraphNode> neighbors;

        UndirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<UndirectedGraphNode>();
        }
    }

    /**
     * <a href="http://oj.leetcode.com/problems/clone-graph/">Clone Graph</a>
     * Clone an undirected graph. Each node in the graph contains a label and a list of its neighbors.
     * <p/>
     * OJ's undirected graph serialization:
     * <p/>
     * Nodes are labeled uniquely.
     * We use # as a separator for each node, and , as a separator for node label and each neighbor of the node.
     * <p/>
     * As an example, consider the serialized graph {0,1,2#1,2#2,2}.
     * <p/>
     * The graph has a total of three nodes, and therefore contains three parts as separated by #.
     * <p/>
     * First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
     * Second node is labeled as 1. Connect node 1 to node 2.
     * Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle.
     * <p/>
     * Visually, the graph looks like the following:
     * <p/>
     * 1
     * / \
     * /   \
     * 0 --- 2
     * / \
     * \_/
     */
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) return null;
        Map<UndirectedGraphNode, UndirectedGraphNode> visited = new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
        UndirectedGraphNode copy = new UndirectedGraphNode(node.label);
        Queue<UndirectedGraphNode> queue = new ArrayDeque<UndirectedGraphNode>();
        Queue<UndirectedGraphNode> copyQueue = new ArrayDeque<UndirectedGraphNode>();
        queue.add(node);
        copyQueue.add(copy);
        visited.put(node, copy);
        while (!queue.isEmpty()) {
            UndirectedGraphNode current = queue.remove();
            UndirectedGraphNode copyCurrent = copyQueue.remove();
            if (current.neighbors != null) {
                for (UndirectedGraphNode adj : current.neighbors) {
                    if (copyCurrent.neighbors == null)
                        copyCurrent.neighbors = new ArrayList<UndirectedGraphNode>();
                    if (!visited.containsKey(adj)) {
                        UndirectedGraphNode newNode = new UndirectedGraphNode(adj.label);
                        copyCurrent.neighbors.add(newNode);
                        visited.put(adj, newNode);
                        queue.add(adj);
                        copyQueue.add(newNode);
                    } else {
                        copyCurrent.neighbors.add(visited.get(adj));
                    }
                }
            }
        }
        return copy;
    }

}