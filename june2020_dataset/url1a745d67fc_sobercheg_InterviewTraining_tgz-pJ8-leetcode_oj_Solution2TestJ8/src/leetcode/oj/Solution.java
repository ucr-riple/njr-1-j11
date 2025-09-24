package leetcode.oj;

import java.util.*;

/**
 * Created by Sobercheg on 12/24/13.
 * http://oj.leetcode.com/problems/
 */
public class Solution {

    /************************** Data structures ****************************/

    /**
     * Definition for binary tree
     */
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        TreeNode(int x, TreeNode left, TreeNode right) {
            this(x);
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "[" + val + (left != null ? "-l>" + left.toString() : "") + (right != null ? "-r>" + right.toString() : "") + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TreeNode treeNode = (TreeNode) o;

            if (val != treeNode.val) return false;
            if (left != null ? !left.equals(treeNode.left) : treeNode.left != null) return false;
            if (right != null ? !right.equals(treeNode.right) : treeNode.right != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = val;
            result = 31 * result + (left != null ? left.hashCode() : 0);
            result = 31 * result + (right != null ? right.hashCode() : 0);
            return result;
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        static ListNode build(int... vals) {
            ListNode root = new ListNode(vals[0]);
            ListNode currentNode = root;
            for (int i = 1; i < vals.length; i++) {
                currentNode.next = new ListNode(vals[i]);
                currentNode = currentNode.next;
            }
            return root;
        }

        ListNode(int x) {
            val = x;
            next = null;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "" + val + (next != null ? "->" + next.toString() : "");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ListNode listNode = (ListNode) o;

            if (val != listNode.val) return false;
            if (next != null ? !next.equals(listNode.next) : listNode.next != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = val;
            result = 31 * result + (next != null ? next.hashCode() : 0);
            return result;
        }
    }

    /**
     * Definition for a point.
     */
    public static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /************************** Solutions ****************************/


    /**
     * <a href="http://oj.leetcode.com/problems/two-sum/">Two Sum</a>
     * Problem: Given an array of integers, find two numbers such that they add up to a specific target number.
     * <p/>
     * First idea:
     * A naive approach would be to compare all elements with each other and find the first pair matching target.
     * <p/>
     * Better idea:
     * A better solution is to have a map of numbers to their indices: if the map contains (target-number[i])
     * return the number index and the current index. Otherwise add a new element to the map.
     */
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> numToIndexMap = new HashMap<Integer, Integer>(numbers.length);
        for (int i = 0; i < numbers.length; i++) {
            int toTarget = target - numbers[i];
            if (numToIndexMap.containsKey(toTarget)) {
                return new int[]{numToIndexMap.get(toTarget) + 1, i + 1};
            }
            numToIndexMap.put(numbers[i], i);
        }

        // solution not found
        return new int[]{-1, -1};
    }


    /**
     * <a href="http://oj.leetcode.com/problems/evaluate-reverse-polish-notation/">Evaluate Reverse Polish Notation</a>
     * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
     * <p/>
     * Solution:
     * <pre>
     * Let's use a Stack of operands. Iterate over the array of tokens and do the following:
     *  - if the next token is an operand (number) put it to Stack
     *  - else
     *  - - pop last two Stack values
     *  - - apply the token operator
     *  - - push the result back to Stack
     *  At the end Stack should have only one element which is the result.
     *  If Stack has 0 or more than 1 element the input expression was wrong.
     * </pre>
     * Limitation: only integers are used. So division operations will produce integers.
     */
    public int evalRPN(String[] tokens) {
        LinkedList<Integer> stack = new LinkedList<Integer>();
        for (String token : tokens) {
            // Assume all tokens are valid: only numbers and the four operators are allowed (+, -, *, /)

            // token is an operator
            if ("+".equals(token)) {
                stack.push(stack.pop() + stack.pop());
            } else if ("-".equals(token)) {
                int subtrahend = stack.pop();
                stack.push(stack.pop() - subtrahend);
            } else if ("*".equals(token)) {
                stack.push(stack.pop() * stack.pop());
            } else if ("/".equals(token)) {
                int divisor = stack.pop();
                stack.push(stack.pop() / divisor);
            } else { // token is an operand
                stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/binary-tree-preorder-traversal/">Binary Tree Preorder Traversal</a>
     * Given a binary tree, return the preorder traversal of its nodes' values.
     * <p/>
     * Idea: use Stack
     * Init: push root
     * Iteration: be careful with the order of pushing element to Stack: right children should be placed first (not left)
     * so that left children could be popped first (LIFO).
     */
    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        if (root == null) return new ArrayList<Integer>();
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        ArrayList<Integer> preorder = new ArrayList<Integer>();
        TreeNode current;
        stack.push(root);
        while (!stack.isEmpty()) {
            current = stack.pop();
            preorder.add(current.val);
            if (current.right != null) {
                stack.push(current.right);
            }
            if (current.left != null) {
                stack.push(current.left);
            }
        }

        return preorder;
    }


    /**
     * <a href="http://oj.leetcode.com/problems/binary-tree-postorder-traversal/">Binary Tree Postorder Traversal</a>
     * Given a binary tree, return the postorder traversal of its nodes' values.
     * Note: Recursive solution is trivial, could you do it iteratively?
     * <p/>
     * Idea:
     * For an iterative solution we'll need a stack or two. Postorder means recursive visiting of left child,
     * right child and then the node element itself. So, the algorithm may look like this:
     * 1. Init: push the tree root to Stack
     * 2. Peek (not pop!) TreeNode from stack (if not empty)
     * 3. If has a left child AND it was not visited push it to stack and skip next steps (loop continue)
     * 4. If has a right child AND it was not visited push it to stack and skip next steps (loop continue)
     * 5. Pop TreeNode from stack and print
     * 6. Add TreeNode to visited
     * <p/>
     * Note: obviously, this is not the best solution since it uses O(n) space. I believe there exists an O(1) space
     * solution.
     */
    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        if (root == null) return new ArrayList<Integer>();
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        Set<TreeNode> visited = new HashSet<TreeNode>();
        ArrayList<Integer> postorder = new ArrayList<Integer>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if (node.left != null && !visited.contains(node.left)) {
                stack.push(node.left);
                continue;
            }
            if (node.right != null && !visited.contains(node.right)) {
                stack.push(node.right);
                continue;
            }
            TreeNode nextNode = stack.poll();
            postorder.add(nextNode.val);
            visited.add(nextNode);
        }

        return postorder;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/longest-substring-without-repeating-characters/">Longest Substring Without Repeating Characters</a>
     * Given a string, find the length of the longest substring without repeating characters.
     * For example, the longest substring without repeating letters for "abcabcbb" is "abc", which the length is 3.
     * For "bbbbb" the longest substring is "b", with the length of 1.
     * <p/>
     * Solution: remember character positions in a char->int HashMap.
     * If the next char IS in the map:
     * - remove all map entries for chars from startPosition to position of the encountered character
     * - set startPosition = currentPosition + 1
     * - update current character position in the map
     * Else:
     * - increment currentPosition
     * - check if currentPosition - startPosition > maxSubstring. If yes, update maxSubstring and remember positions.
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) return 0;
        int startPosition = 0;
        int maxSubstring = 0;
        Map<Character, Integer> positionMap = new HashMap<Character, Integer>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (positionMap.containsKey(ch)) {
                for (int j = startPosition; j < positionMap.get(ch); j++) {
                    positionMap.remove(s.charAt(j));
                }
                startPosition = positionMap.get(ch) + 1;
            } else {
                if (i - startPosition > maxSubstring) {
                    maxSubstring = i - startPosition;
                }
            }
            positionMap.put(ch, i);

        }

        return maxSubstring + 1;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/median-of-two-sorted-arrays/">Median of Two Sorted Arrays</a>
     * There are two sorted arrays A and B of size m and n respectively. Find the median of the two sorted arrays.
     * The overall run time complexity should be O(log (m+n)).
     * <p/>
     * Observations: if we could merge two arrays (O(m+n)) we would just pick the (m+n)/2 element (if m+n is odd).
     * TODO: the solution is wrong and does not work.
     */
    public double findMedianSortedArrays(int A[], int B[]) {
        int m = A.length;
        int n = B.length;
        if (n == 0) return (m % 2 == 0) ? (double) (A[(m - 1) / 2] + A[m / 2]) / 2 : A[m / 2];
        if (m == 0) return (n % 2 == 0) ? (double) (B[(n - 1) / 2] + B[n / 2]) / 2 : B[n / 2];
        if (m == 2 && n == 2) return (double) (Math.max(A[0], B[0]) + Math.min(A[1], B[1])) / 2;
        int jumpA = m / 2;
        int jumpB = n / 2;
        int kA = 0;
        int kB = 0;
        boolean lastA = false;
        while (jumpA > 0 && jumpB > 0) {
            if (kA + kB <= (m + n) / 2) {
                if (A[kA] < B[kB]) {
                    kA += jumpA;
                    jumpA /= 2;
                    lastA = true;
                } else {
                    kB += jumpB;
                    jumpB /= 2;
                    lastA = false;
                }
            } else {
                if (A[kA] < B[kB] && kB - jumpB >= 0) {
                    kB -= jumpB;
                    jumpB /= 2;
                    lastA = false;
                } else if (A[kA] <= B[kB] && kA - jumpA >= 0) {
                    kA -= jumpA;
                    jumpA /= 2;
                    lastA = true;
                } else {
                    break;
                }
            }
        }
        if ((m + n) % 2 == 0 && m == n) return (double) (A[kA] + B[kB]) / 2;
        if (lastA) return A[kA];
        return B[kB];
    }

    /**
     * <a href="oj.leetcode.com/problems/add-two-numbers/">Add Two Numbers</a>
     * You are given two linked lists representing two non-negative numbers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
     * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
     * Output: 7 -> 0 -> 8
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode result = new ListNode(0);
        ListNode currentNode = result;
        int carryover = 0;
        boolean isFirst = true;
        ListNode l1c = l1;
        ListNode l2c = l2;
        while (true) {
            if (l1c == null && l2c == null) {
                break;
            }
            int sum = 0;
            if (l1c != null) {
                sum = l1c.val;
                l1c = l1c.next;
            }
            if (l2c != null) {
                sum += l2c.val;
                l2c = l2c.next;
            }

            sum += carryover;
            if (sum >= 10) {
                carryover = 1;
                sum = sum - 10;
            } else {
                carryover = 0;
            }

            if (isFirst) {
                currentNode = new ListNode(sum);
                result = currentNode;
                isFirst = false;
            } else {
                currentNode.next = new ListNode(sum);
                currentNode = currentNode.next;
            }
        }
        if (carryover > 0) {
            currentNode.next = new ListNode(carryover);
        }
        return result;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/longest-palindromic-substring/">Longest Palindromic Substring</a> Given
     * a string S, find the longest palindromic substring in S. You may assume that the maximum length of S is 1000, and
     * there exists one unique longest palindromic substring.
     * <p/>
     * Solution 1: via dynamic programming.
     * A[i,j] = (A[i+1, j-1] AND Si == Sj)
     * Init: A[i,i] = true, A[i,i+1] = (Si == Si+1)
     */
    public String longestPalindrome(String s) {
//        return longestPalindromeDP(s);
        return longestPalindromeExpansion(s);
    }

    private String longestPalindromeDP(String s) {
        int n = s.length();
        if (n == 0 || n == 1) return s;
        boolean A[][] = new boolean[n][n];

        // init
        for (int i = 0; i < n; i++) {
            A[i][i] = true;
        }

        int longestLen = 0;
        String longest = "" + s.charAt(n - 1);

        for (int i = 0; i < n - 1; i++) {
            A[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
            if (A[i][i + 1]) longest = s.substring(i, i + 2);
        }

        if (n < 3) return longest;

        // iteration
        for (int len = 2; len < n; len++) {
            for (int i = 0; i < n - len; i++) {
                A[i][i + len] = A[i + 1][i + len - 1] && s.charAt(i) == s.charAt(i + len);
                if (A[i][i + len] && len > longestLen) {
                    longestLen = len;
                    longest = s.substring(i, i + len + 1);
                }
            }
        }
        return longest;
    }

    private String longestPalindromeExpansion(String s) {
        int n = s.length();
        if (n == 0 || n == 1) return s;
        int from = 0;
        int to = 0;
        int maxLength = -1;

        for (int i = 0; i < n; i++) {
            // expand around a char (aBa)
            for (int expansion = 1; expansion < n; expansion++) {
                if (i - expansion < 0 || i + expansion >= n) break;
                if (s.charAt(i - expansion) != s.charAt(i + expansion)) break;
                if ((expansion * 2 + 1) > maxLength) {
                    maxLength = expansion * 2 + 1;
                    from = i - expansion;
                    to = i + expansion;
                }
            }
            // expand around two chars (aBBa)
            for (int expansion = 0; expansion < n - 1; expansion++) {
                if (i - expansion < 0 || i + expansion + 1 >= n) break;
                if (s.charAt(i - expansion) != s.charAt(i + expansion + 1)) break;
                if ((expansion + 1) * 2 >= maxLength) {
                    maxLength = (expansion + 1) * 2;
                    from = i - expansion;
                    to = i + expansion + 1;
                }
            }
        }

        return s.substring(from, to + 1);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/zigzag-conversion/">ZigZag Conversion</a>
     * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this:
     * (you may want to display this pattern in a fixed font for better legibility):
     * P   A   H   N
     * A P L S I I G
     * Y   I   R
     * And then read line by line: "PAHNAPLSIIGYIR"
     * <p/>
     * Write the code that will take a string and make this conversion given a number of rows.
     * <p/>
     * 0      10      20       30       40      50
     * 1    9 11    19 21    29 31    39 41    49
     * 2   8  12   18  22   28  32   38  42   48
     * 3  7   13  17   23  27   33  37   43  47
     * 4 6    14 16    24 26    34 36    44 46
     * 5      15       25       35       45
     * <p/>
     * 0   4   8
     * 1 3 5 7 9
     * 2   6   10
     * <p/>
     * Solution: just look at the examples and implement what you see.
     */
    public String convert(String s, int n) {
        if (n <= 1) return s;
        int l = s.length();
        if (l <= n) return s;
        StringBuilder out = new StringBuilder();

        for (int row = 0; row < n; row++) {
            for (int i = 0; i <= l / ((n - 1) * 2) + 1; i++) {
                int at = i * (n - 1) * 2;
                if (row == 0 && at < l) {
                    out.append(s.charAt(at));
                } else if (row == n - 1 && at + n - 1 < l) {
                    out.append(s.charAt(at + n - 1));
                } else if (row > 0 && row < n - 1) {
                    if (at - row >= 0 && at - row < l) {
                        out.append(s.charAt(at - row));
                    }
                    if (at + row < l && at + row >= 0) {
                        out.append(s.charAt(at + row));
                    }
                }
            }
        }

        return out.toString();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/reverse-integer/">Reverse Integer</a>
     * Reverse digits of an integer.
     * <p/>
     * Example1: x = 123, return 321
     * Example2: x = -123, return -321
     * <p/>
     * Solution: keep dividing the number by 10 and append num%10 to the result left side as result = result * 10 + right
     */
    public int reverse(int x) {
        int num = x < 0 ? -x : x;
        int reversed = 0;

        while (num > 0) {
            int right = num % 10;
            num /= 10;
            reversed = reversed * 10 + right;
        }

        return x < 0 ? -reversed : reversed;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/string-to-integer-atoi/">String to Integer (atoi)</a>
     * Implement atoi to convert a string to an integer.
     * <p/>
     * Hint: Carefully consider all possible input cases. If you want a challenge, please do not see below and ask yourself what are the possible input cases.
     * <p/>
     * Notes: It is intended for this problem to be specified vaguely (ie, no given input specs). You are responsible to gather all the input requirements up front.
     * <p/>
     * My initial requirements:
     * 1. Whitespaces in front are allowed: "    -42" -> -42
     * 2. Trailing non-digits are allowed: "44www" -> 44
     * 3. Leading non-digits (except whitespaces) are NOT allowed: "ww34" -> 0
     * 4. Whitespaces in the middle are NOT allowed: " 4 3" -> 0
     * 5. Fractional digits are ignored: "45.943" -> 45
     * 6. Only one negative sign is allowed.
     * 7. If there is an overflow return Integer.MAX_VALUE for positive or Integer.MIN_VALUE for negative numbers
     */
    public int atoi(String str) {
        boolean isNegative = false;
        boolean isPositive = false;
        boolean isBeginning = true;
        int num = 0;

        for (char ch : str.toCharArray()) {

            // R1. Ignore leading whitespaces
            // R4. Whitespaces in the middle are NOT allowed
            if (ch == ' ' && isBeginning) continue;

            // R6. Only one negative sign is allowed
            if (isNegative && ch == '-') {
                break;
            }
            if (isPositive && ch == '+') {
                break;
            }

            if (ch == '-') {
                isBeginning = false;
                isNegative = true;
                continue;
            }

            if (ch == '+') {
                isBeginning = false;
                isPositive = true;
                continue;
            }

            if (ch >= '0' && ch <= '9') {
                isBeginning = false;
                int newNum = num * 10 + (ch - '0');

                // R7. Preventing overflow
                if (!isNegative && (newNum) / 10 != num) {
                    return Integer.MAX_VALUE;
                }
                if (isNegative && (newNum) / 10 != num) {
                    return Integer.MIN_VALUE;
                }
                num = newNum;
                continue;
            }

            // R2. Ignore trailing non-digits
            // R5. Fractional digits are ignored
            // R3. Leadning non-digits are NOT allowed
            if (ch < '0' || ch > '9') {
                break;
            }
        }

        return isNegative ? -num : num;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/palindrome-number/">http://oj.leetcode.com/problems/palindrome-number/</a>
     * Determine whether an integer is a palindrome. Do this without extra space.
     */
    public boolean isPalindrome(int x) {
        if (x < 0) return false;
        if (x < 10) return true;

        int copy = x;
        int len = 0;
        while (copy > 0) {
            len++;
            copy /= 10;
        }

        while (len > 1) {
            int pow = (int) (Math.pow(10, len - 1));
            if (x % 10 != (x / pow)) return false;
            x = x - ((x / pow) * pow);
            x = x / 10;
            len -= 2;
        }

        return true;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/regular-expression-matching/">Regular Expression Matching</a>
     * Implement regular expression matching with support for '.' and '*'.
     * <p/>
     * '.' Matches any single character.
     * '*' Matches zero or more of the preceding element.
     * <p/>
     * The matching should cover the entire input string (not partial).
     * <p/>
     * The function prototype should be:
     * bool isMatch(const char *s, const char *p)
     * <p/>
     * Some examples:
     * isMatch("aa","a") → false
     * isMatch("aa","aa") → true
     * isMatch("aaa","aa") → false
     * isMatch("aa", "a*") → true
     * isMatch("aa", ".*") → true
     * isMatch("ab", ".*") → true
     * isMatch("aab", "c*a*b") → true
     */
    public boolean isMatch(String s, String p) {
        log("----- Start matching [%s] by [%s] -----", s, p);
        // compress pattern: a*a*a* -> a*
        StringBuilder compressedPattern = new StringBuilder();
        char prev = '!';
        int i = 0;
        while (i < p.length()) {
            if (i + 1 < p.length() && p.charAt(i + 1) == '*') {
                if (p.charAt(i) == prev) {
                    i += 2;
                } else {
                    prev = p.charAt(i);
                    compressedPattern.append(prev);
                    i++;
                }
            } else {
                compressedPattern.append(p.charAt(i));
                if (p.charAt(i) != '*') prev = '!';
                i++;
            }
        }
        log("Compressed pattern: " + compressedPattern.toString());
        return isMatch(s, compressedPattern.toString(), 0, 0, 0);
    }

    private boolean isMatch(String s, String p, int sfrom, int pfrom, int level) {
        log("L[%d]: matching [%s] by [%s]", level, s.substring(sfrom), p.substring(pfrom));
        // base cases
        if (sfrom >= s.length()) {
            if (pfrom >= p.length()) return true; // [] vs []

            if (pfrom + 1 < p.length() && p.charAt(pfrom + 1) == '*')
                return isMatch(s, p, sfrom, pfrom + 2, level + 1); // [] vs [a*...] -> [] vs [...]

            if (pfrom == p.length() - 2 && p.charAt(p.length() - 1) == '*')
                return true; // match [] against [a*]

            return false; // [] vs [a], [] vs [aa*] (all other cases)
        }


        if (sfrom < s.length() && pfrom >= p.length()) {
            return false; // all pattern chars are exhausted but the string is not matched ([abc] vs [])
        }

        boolean result = false;

        if (s.charAt(sfrom) == p.charAt(pfrom) || p.charAt(pfrom) == '.') {
            // aa:a* or aa:.*
            log("L[%d]: First symbol matches (equals or .)", level);
            if (pfrom + 1 < p.length() && p.charAt(pfrom + 1) == '*') {
                log("L[%d]: Next pattern symbol is *, so try matching [%s] against [%s] or [%s], or [%s] against [%s]", level, s.substring(sfrom + 1), p.substring(pfrom), p.substring(pfrom + 2), s.substring(sfrom), p.substring(pfrom + 2));
                result = isMatch(s, p, sfrom + 1, pfrom, level + 1) // try to match next input char with the same regex (a* or .*)
                        || isMatch(s, p, sfrom, pfrom + 2, level + 1) // or skip this regex part (a* or .*) and try to match same string
                        || isMatch(s, p, sfrom + 1, pfrom + 2, level + 1); // or go further, skip this regex part (a* or .*)
                log("L[%d]: Result of matching [%s] against [%s] or [%s], or [%s] against [%s] is [%s]", level, s.substring(sfrom + 1), p.substring(pfrom), p.substring(pfrom + 2), s.substring(sfrom), p.substring(pfrom + 2), result);

            } else {
                log("L[%d]: Trying regular char-char or char-. match [%s] [%s]", level, s.substring(sfrom + 1), p.substring(pfrom + 1));
                result = isMatch(s, p, sfrom + 1, pfrom + 1, level + 1); // regular char-char or char-. match
                log("L[%d]: Result of regular char-char or char-. match [%s] [%s] is [%s]", level, s.substring(sfrom + 1), p.substring(pfrom + 1), result);

            }
        } else if (s.charAt(sfrom) != p.charAt(pfrom) && pfrom + 1 < p.length() && p.charAt(pfrom + 1) == '*') {
            // aa:b*
            log("L[%d]: skipping non-matching star piece: [%s] against [%s]", level, s.substring(sfrom), p.substring(pfrom));
            result = isMatch(s, p, sfrom, pfrom + 2, level + 1); // skip this b* regex
            log("L[%d]: Result of skipping non-matching star piece: [%s] against [%s] is [%s]", level, s.substring(sfrom), p.substring(pfrom), result);

        }
        log("L[%d]: Returning result for [%s] against [%s] as [%s]", level, s.substring(sfrom), p.substring(pfrom), result);
        return result;
    }

    private void log(String msg, Object... args) {
        // System.out.println(String.format(msg, args));
    }

    /**
     * <a href="http://oj.leetcode.com/problems/container-with-most-water/">Container With Most Water</a>
     * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). n vertical
     * lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
     * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
     * <p/>
     * Note: You may not slant the container.
     * <p/>
     * Solution: let's do three passes:
     * 1. From left to right raising the watermark as line height becomes higher
     * <p/>
     * 2. From right to left lowering the watermark to the current line height
     * <p/>
     * 3. Calculate maxArea during Pass 3
     */
    public int maxArea(int[] height) {
        if (height.length < 2) return 0;

        int[] watermark = new int[height.length];

        // Pass 1
        int currentWatermark = height[0];
        for (int i = 0; i < height.length; i++) {
            if (height[i] > currentWatermark) {
                currentWatermark = height[i];
            }
            watermark[i] = currentWatermark;
        }

        // Pass 2
        currentWatermark = height[height.length - 1];
        for (int i = height.length - 1; i > 0; i--) {

            if (height[i] > currentWatermark) {
                currentWatermark = height[i];
            }
            watermark[i - 1] = Math.min(currentWatermark, watermark[i - 1]);
        }

        // Pass 3
        int i = 0;
        int j = watermark.length - 2;
        int maxVolume = (j - i + 1) * Math.min(watermark[0], watermark[watermark.length - 2]);

        int prevWatermark;
        while (i < j) {
            prevWatermark = Math.min(watermark[i], watermark[j]);
            while (watermark[i] <= prevWatermark && i < j) i++;
            while (watermark[j] <= prevWatermark && i < j) j--;

            int currentMax = (j - i + 1) * Math.min(watermark[i], watermark[j]);
            if (currentMax > maxVolume) {
                maxVolume = currentMax;
            }
        }

        return maxVolume;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/integer-to-roman/">Integer to Roman</a>
     * Given an integer, convert it to a roman numeral.
     * <p/>
     * Input is guaranteed to be within the range from 1 to 3999.
     */
    public String intToRoman(int num) {
        int[] nums = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romans = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int i = 0;
            // find the largest
            while (nums[i] > num) {
                i++;
            }
            sb.append(romans[i]);
            num -= nums[i];
        }
        return sb.toString();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/roman-to-integer/">Roman to Integer</a>
     * Given a roman numeral, convert it to an integer.
     * <p/>
     * Input is guaranteed to be within the range from 1 to 3999.
     */
    public int romanToInt(String s) {
        // the order matters here: we want to match CM before M, CD before D and so on
        String[] romans = new String[]{"CM", "M", "CD", "D", "XC", "C", "XL", "L", "IX", "X", "IV", "V", "I"};
        int[] nums = new int[]{900, 1000, 400, 500, 90, 100, 40, 50, 9, 10, 4, 5, 1};
        int num = 0;
        int offset = 0;
        while (offset < s.length()) {
            for (int i = 0; i < romans.length; i++) {
                if (s.startsWith(romans[i], offset)) {
                    num += nums[i];
                    offset += romans[i].length();
                    break;
                }
            }
        }

        return num;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/longest-common-prefix/">Longest Common Prefix</a>
     * Write a function to find the longest common prefix string amongst an array of strings.
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) return "";
        int rightMost = strs[0].length() - 1;
        int pos = 0;
        for (; pos <= rightMost; pos++) {
            for (String str : strs) {
                if (str.length() < pos + 1 || str.charAt(pos) != strs[0].charAt(pos))
                    return strs[0].substring(0, pos);
            }
        }
        return strs[0];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/3sum/">3Sum</a>
     * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0?
     * Find all unique triplets in the array which gives the sum of zero.
     * <p/>
     * Note:
     * <p/>
     * Elements in a triplet (a,b,c) must be in non-descending order. (ie, a ≤ b ≤ c)
     * The solution set must not contain duplicate triplets.
     * <p/>
     * For example, given array S = {-1 0 1 2 -1 -4},
     * <p/>
     * A solution set is:
     * (-1, 0, 1)
     * (-1, -1, 2)
     * <p/>
     * Solution: the idea is to extend the k=a+b (two sum) problem on a sorted list: for every k find a and b by moving
     * left and right pointers toward each other until a+b+k=0.
     */
    public ArrayList<ArrayList<Integer>> threeSum(int[] num) {
        ArrayList<ArrayList<Integer>> sums = new ArrayList<ArrayList<Integer>>();
        Arrays.sort(num);
        if (num.length < 3) return sums;
        int n = num.length;
        for (int k = 2; k < n; k++) {
            // prevent duplicates
            if (k < n - 1 && num[k] == num[k + 1]) continue;
            int a = 0;
            int b = k - 1;
            while (a < b) {
                // prevent duplicates
                if (a > 0 && num[a] == num[a - 1]) {
                    a++;
                    continue;
                }
                // prevent duplicates
                if (b < k - 1 && num[b] == num[b + 1]) {
                    b--;
                    continue;
                }
                int sum = num[a] + num[k] + num[b];
                if (sum > 0) b--;
                else if (sum < 0) a++;
                else {
                    sums.add(new ArrayList<Integer>(Arrays.asList(num[a], num[b], num[k])));
                    a++;
                    b--;
                }
            }
        }

        return sums;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/3sum-closest/">3Sum Closest</a>
     * Given an array S of n integers, find three integers in S such that the sum is closest to a given number, target.
     * Return the sum of the three integers. You may assume that each input would have exactly one solution.
     * <p/>
     * For example, given array S = {-1 2 1 -4}, and target = 1.
     * <p/>
     * The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
     * <p/>
     * Idea: the problem seems similar to the 3sum problem. We need to keep only one 3set and the closest sum to target so far.
     */
    public int threeSumClosest(int[] num, int target) {
        if (num.length == 0) return 0;
        if (num.length == 1) return num[0];
        if (num.length == 2) return num[0] + num[1];
        if (num.length == 3) return num[0] + num[1] + num[2];

        Arrays.sort(num);
        int n = num.length;
        int bestDiff = Integer.MAX_VALUE;
        int bestSum = Integer.MAX_VALUE;
        for (int k = 2; k < n; k++) {
            // skip duplicates
            if (k < n - 1 && num[k] == num[k + 1]) continue;
            for (int a = 0, b = k - 1; a < b; ) {
                if (a > 0 && num[a] == num[a - 1]) {
                    a++;
                    continue;
                }
                if (b < k - 1 && num[b] == num[b + 1]) {
                    b--;
                    continue;
                }
                int sum = num[a] + num[k] + num[b];
                if (Math.abs(sum - target) < bestDiff) {
                    bestSum = sum;
                    bestDiff = Math.abs(sum - target);
                }

                if (sum < target) {
                    a++;
                } else {
                    b--;
                }
            }

        }

        return bestSum;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/4sum/">4Sum</a>
     * Given an array S of n integers, are there elements a, b, c, and d in S such that a + b + c + d = target?
     * Find all unique quadruplets in the array which gives the sum of target.
     * <p/>
     * Note:
     * <p/>
     * Elements in a quadruplet (a,b,c,d) must be in non-descending order. (ie, a ≤ b ≤ c ≤ d)
     * The solution set must not contain duplicate quadruplets.
     * <p/>
     * For example, given array S = {1 0 -1 0 -2 2}, and target = 0.
     * <p/>
     * A solution set is:
     * (-1,  0, 0, 1)
     * (-2, -1, 1, 2)
     * (-2,  0, 0, 2)
     */
    public List<List<Integer>> fourSum(int[] num, int target) {
        if (num.length < 4) return new ArrayList<List<Integer>>();
        List<List<Integer>> kSums = kSum(num, target, 4);
        return new ArrayList<List<Integer>>(kSums);
    }

    /**
     * <a href="http://cs.stackexchange.com/questions/2973/generalised-3sum-k-sum-problem">k-SUM problem</a>
     * <p/>
     * For even k: Compute a sorted list S of all sums of k/2 input elements.
     * Check whether S contains both some number x and its negation −x. The algorithm runs in O(nk/2logn) time.
     * <p/>
     * For odd k: Compute the sorted list S of all sums of (k−1)/2 input elements.
     * For each input element a, check whether S contains both x and a−x, for some number x.
     * (The second step is essentially the O(n2)-time algorithm for 3SUM.) The algorithm runs in O(n(k+1)/2) time.
     */
    public List<List<Integer>> kSum(int[] num, int target, int k) {
        if (num.length < k) return new ArrayList<List<Integer>>();
        // Step 1. Compute all sums of k/2 input elements
        // Probably this step brings most complexity and running time
        // Probably the algo can be redesigned to use n choose k rather than n choose k/2. It'll greatly simplify
        // the code avoiding combination steps
        List<List<Integer>> nChooseKIndices = choose(num.length, k / 2);
        Map<Integer, List<List<Integer>>> sumToCombinations = new HashMap<Integer, List<List<Integer>>>();
        for (List<Integer> indices : nChooseKIndices) {
            int sum = 0;
            for (int index : indices) {
                sum += num[index];
            }
            if (!sumToCombinations.containsKey(sum)) {
                sumToCombinations.put(sum, new ArrayList<List<Integer>>());
            }
            sumToCombinations.get(sum).add(indices);
        }

        Set<List<Integer>> kSums = new HashSet<List<Integer>>();

        // Step 2. Check whether S contains both some number x and its sibling target−x
        if (k % 2 == 0) {
            for (int sum : sumToCombinations.keySet()) {
                int antiSum = target - sum;
                if (sumToCombinations.containsKey(antiSum)) {
                    List<List<Integer>> finalCombos = new ArrayList<List<Integer>>();
                    for (int i = 0; i < sumToCombinations.get(sum).size(); i++) {
                        for (int j = sum == antiSum ? i + 1 : 0; j < sumToCombinations.get(antiSum).size(); j++) {
                            List<Integer> finalCombo = new ArrayList<Integer>();
                            finalCombo.addAll(sumToCombinations.get(sum).get(i));
                            finalCombo.addAll(sumToCombinations.get(antiSum).get(j));
                            if (new HashSet<Integer>(finalCombo).size() < finalCombo.size()) continue;
                            finalCombos.add(finalCombo);
                        }
                    }

                    for (List<Integer> indices : finalCombos) {
                        List<Integer> finalValues = new ArrayList<Integer>();
                        for (int index : indices) {
                            finalValues.add(num[index]);
                        }
                        Collections.sort(finalValues);
                        kSums.add(finalValues);
                    }
//                    break;
                }
            }

        } else {
            //  For each input element z, check whether S contains both x and target+z−x, for some number x.
            for (int z = 0; z < num.length; z++) {
                for (int sum : sumToCombinations.keySet()) {
                    int antiSum = target - sum - num[z];
                    if (sumToCombinations.containsKey(antiSum)) {
                        List<List<Integer>> finalCombos = new ArrayList<List<Integer>>();
                        for (int i = 0; i < sumToCombinations.get(sum).size(); i++) {
                            for (int j = (sum == antiSum) ? i + 1 : 0; j < sumToCombinations.get(antiSum).size(); j++) {
                                List<Integer> finalCombo = new ArrayList<Integer>();
                                finalCombo.addAll(sumToCombinations.get(sum).get(i));
                                finalCombo.addAll(sumToCombinations.get(antiSum).get(j));
                                if (finalCombo.contains(z)) continue;
                                if (new HashSet<Integer>(finalCombo).size() < finalCombo.size()) continue;
                                finalCombo.add(z);
                                finalCombos.add(finalCombo);
                            }
                        }

                        for (List<Integer> indices : finalCombos) {
                            List<Integer> finalValues = new ArrayList<Integer>();
                            for (int index : indices) {
                                finalValues.add(num[index]);
                            }
                            Collections.sort(finalValues);
                            kSums.add(finalValues);
                        }
//                        break;
                    }
                }
            }

        }

        return new ArrayList<List<Integer>>(kSums);
    }

    private static List<List<Integer>> choose(int n, int k) {
        List<List<Integer>> combos = new ArrayList<List<Integer>>();
        boolean[] visited = new boolean[n];
        choose(combos, n, visited, 0, k);
        return combos;
    }

    private static void choose(List<List<Integer>> combinations, int n, boolean[] visited, int from, int limit) {
        // base case: all indices are visited
        if (limit == 0) {
            List<Integer> combo = new ArrayList<Integer>();
            for (int i = 0; i < visited.length; i++) {
                if (visited[i]) combo.add(i);
            }
            combinations.add(combo);
            return;
        }

        for (int i = from; i < n; i++) {
            if (visited[i]) continue;
            visited[i] = true;
            choose(combinations, n, visited, i, limit - 1);
            visited[i] = false;
        }
    }


    /**
     * <a href="http://oj.leetcode.com/problems/letter-combinations-of-a-phone-number/">Letter Combinations of a Phone Number</a>
     * Given a digit string, return all possible letter combinations that the number could represent.
     * <p/>
     * A mapping of digit to letters (just like on the telephone buttons) is given below.
     * <p/>
     * Input: Digit string "23"
     * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
     * <p/>
     * Solution: Think of letters as digits of a 3 or 4 based numerical system. "Increment" the number until overflow
     */
    public ArrayList<String> letterCombinations(String digits) {
        if (digits == null || digits.isEmpty()) return new ArrayList<String>(Arrays.asList(""));
        // map of digit to letters
        Map<Character, String> digitMap = new HashMap<Character, String>();
        digitMap.put('1', "1");
        digitMap.put('2', "abc");
        digitMap.put('3', "def");
        digitMap.put('4', "ghi");
        digitMap.put('5', "jkl");
        digitMap.put('6', "mno");
        digitMap.put('7', "pqrs");
        digitMap.put('8', "tuv");
        digitMap.put('9', "wxyz");

        int[] counters = new int[digits.length()];
        boolean overflow = false;
        ArrayList<String> combinations = new ArrayList<String>();
        // keep incrementing counters while there is no overflow
        int n = digits.length() - 1;
        while (!overflow) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < counters.length; i++) {
                String chars = digitMap.get(digits.charAt(i));
                sb.append(chars.charAt(counters[i]));
            }
            combinations.add(sb.toString());
            int position = n;
            counters[position]++;
            while (counters[position] >= digitMap.get(digits.charAt(position)).length()) {

                counters[position] = 0;
                position--;
                if (position < 0) {
                    overflow = true;
                    break;
                }
                counters[position]++;
            }

        }
        return combinations;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/remove-nth-node-from-end-of-list/">Remove Nth Node From End of List</a>
     * Given a linked list, remove the nth node from the end of list and return its head.
     * <p/>
     * For example,
     * <p/>
     * Given linked list: 1->2->3->4->5, and n = 2.
     * <p/>
     * After removing the second node from the end, the linked list becomes 1->2->3->5.
     * <p/>
     * Note:
     * Given n will always be valid.
     * Try to do this in one pass.
     * <p/>
     * Solution: use two pointers: one starts immediately, another after n moves. As soon as the first pointer reaches
     * the end of the list (node.next == null) the second will point to the element right before the element to delete.
     * Now just rewire a couple of pointers.
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fastPointer = head;
        ListNode slowPointer = head;
        for (int i = 0; i < n; i++)
            fastPointer = fastPointer.next;

        // need to remove the first element, head.
        if (fastPointer == null) {
            return head.next;
        }

        while (fastPointer.next != null) {
            fastPointer = fastPointer.next;
            slowPointer = slowPointer.next;
        }

        ListNode toDelete = slowPointer.next;
        slowPointer.next = toDelete.next;
        return head;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/valid-parentheses/">Valid Parentheses</a>
     * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * <p/>
     * The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
     */
    public boolean isValid(String s) {
        Map<Character, Character> par = new HashMap<Character, Character>();
        par.put('(', ')');
        par.put('{', '}');
        par.put('[', ']');

        LinkedList<Character> stack = new LinkedList<Character>();

        for (char ch : s.toCharArray()) {
            // opening paren: add the corresponding closing paren to the stack
            if (par.containsKey(ch)) {
                stack.push(par.get(ch));
            }
            // should be a closing paren: make sure the top stack element is exactly this paren.
            else if (!stack.isEmpty() && stack.peek() == ch) {
                stack.pop();
            }
            // invalid paren or character
            else return false;
        }

        // there missing closing parens if the stack is not empty
        return stack.isEmpty();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/generate-parentheses/">Generate Parentheses</a>
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     * <p/>
     * For example, given n = 3, a solution set is:
     * <p/>
     * "((()))", "(()())", "(())()", "()(())", "()()()"
     */
    public ArrayList<String> generateParenthesis(int n) {
        char[] parens = new char[n * 2];
        return new ArrayList<String>(generateParenthesis(parens, n, 0, 0, 0));
    }

    private List<String> generateParenthesis(char[] parens, int n, int opened, int closed, int len) {
        if (len == n * 2) {
            return Arrays.asList(new String(parens));
        }

        List<String> allParens = new ArrayList<String>();
        if (opened < n) {
            parens[len] = '(';
            allParens.addAll(generateParenthesis(parens, n, opened + 1, closed, len + 1));
        }
        if (closed < opened) {
            parens[len] = ')';
            allParens.addAll(generateParenthesis(parens, n, opened, closed + 1, len + 1));
        }

        return allParens;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/max-points-on-a-line/">Max Points on a Line</a>
     * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
     * <p/>
     * Solution: calculate all slopes for all points, put Points with the same slope to a hashmap.
     * Note: process equal points separately
     */
    public int maxPoints(Point[] points) {
        if (points.length < 3) return points.length;

        long precision = 1000000;
        Map<Long, Integer> slopeToNumOfPointsMap;
        int maxPoints;
        int totalMaxPoints = 2;
        long slope;
        for (Point point1 : points) {
            slopeToNumOfPointsMap = new HashMap<Long, Integer>();
            int equal = 0;
            maxPoints = 1;
            for (Point point2 : points) {
                if (point1 == point2) continue;
                if (point1.x == point2.x && point1.y == point2.y) {
                    equal++;
                    continue;
                } else if (point1.x == point2.x)
                    slope = Long.MAX_VALUE;
                else slope = (long) (precision * ((double) (point1.y - point2.y) / (point1.x - point2.x)));
                if (!slopeToNumOfPointsMap.containsKey(slope)) {
                    slopeToNumOfPointsMap.put(slope, 1);
                }
                int numOfPoints = slopeToNumOfPointsMap.get(slope) + 1;
                slopeToNumOfPointsMap.put(slope, numOfPoints);
                if (numOfPoints > maxPoints) maxPoints = numOfPoints;
            }
            if (maxPoints + equal > totalMaxPoints) {
                totalMaxPoints = maxPoints + equal;
            }
        }

        return totalMaxPoints;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/best-time-to-buy-and-sell-stock/">Best Time to Buy and Sell Stock</a>
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * <p/>
     * If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock),
     * design an algorithm to find the maximum profit.
     */
    public int maxProfitI(int[] prices) {
        int buy = 0;
        int maxDiff = 0;

        for (int i = 0; i < prices.length; i++) {
            if (prices[i] - prices[buy] > maxDiff) {
                maxDiff = prices[i] - prices[buy];
            }
            if (prices[i] < prices[buy]) {
                buy = i;
            }

        }
        return maxDiff;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/">Best Time to Buy and Sell Stock II</a>
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * <p/>
     * Design an algorithm to find the maximum profit. You may complete as many transactions as you like
     * (ie, buy one and sell one share of the stock multiple times).
     * However, you may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     * <p/>
     * Solution: just track when the price curve changes direction: sell on previous day, buy on this day
     */
    public int maxProfitII(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i - 1] > prices[i]) continue;
            profit += prices[i] - prices[i - 1];
        }
        return profit;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/">Best Time to Buy and Sell Stock III</a>
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * <p/>
     * Design an algorithm to find the maximum profit. You may complete at most two transactions.
     * <p/>
     * Note:
     * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
     * <p/>
     * Solution: divide prices into two regions around 0, 1, ..., n-1 and calculate the sum of maxProfits of each
     * region (keep the best profit).
     * Hint: we need to reuse n-1 solutions to calculate n solutions to avoid Time Limit Exceeded errors, so it is
     * infeasible to reuse maxProfitI(). For the right part we should go from the rightmost price to left. In this case
     * we need to maintain the highest sell prices rather than lowest buy price.
     */
    public int maxProfitIII(int[] prices) {
        int maxProfit = 0;
        int n = prices.length;
        int prevLeftBuy = 0;
        int[] leftMax = new int[n];
        int prevRightSell = n - 1;
        int[] rightMax = new int[n];

        for (int i = 1; i < n; i++) {
            if (prices[i] - prices[prevLeftBuy] > leftMax[i - 1]) {
                leftMax[i] = prices[i] - prices[prevLeftBuy];
            } else leftMax[i] = leftMax[i - 1];
            if (prices[i] < prices[prevLeftBuy]) {
                prevLeftBuy = i;
            }

            // right region: [n-2, n-1], [n-3,n-1], ... [0, n-1]
            int r = n - i - 1;
            if (prices[prevRightSell] - prices[r] > rightMax[r + 1]) {
                rightMax[r] = prices[prevRightSell] - prices[r];
            } else rightMax[r] = rightMax[r + 1];
            if (prices[r] > prices[prevRightSell]) {
                prevRightSell = r;
            }
        }

        for (int i = 0; i < leftMax.length; i++) {
            if (leftMax[i] + rightMax[i] > maxProfit) {
                maxProfit = leftMax[i] + rightMax[i];
            }
        }

        return maxProfit;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/merge-k-sorted-lists/">Merge k Sorted Lists</a>
     * Merge k sorted linked lists and return it as one sorted list. Analyze and describe its complexity.
     * <p/>
     * Solution: use a TreeMap for storing list heads sorted by their value. Get first map entry, advance the corresponding
     * list position (if available) and put ths value-list pair to the map.
     */
    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        if (lists == null || lists.isEmpty()) return null;

        PriorityQueue<ListNode> heap = new PriorityQueue<ListNode>(lists.size(), new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return Integer.compare(o1.val, o2.val);
            }
        });

        // initial population
        for (ListNode list : lists) {
            if (list == null) continue;
            heap.add(list);
        }
        ListNode sortedHead = null;
        ListNode sortedNext = null;

        while (!heap.isEmpty()) {
            ListNode smallestHead = heap.poll();
            // init sorted list head if necessary
            if (sortedHead == null) {
                sortedHead = new ListNode(smallestHead.val);
                sortedNext = sortedHead;
            } else {
                // create new sorted list node
                ListNode newNode = new ListNode(smallestHead.val);
                sortedNext.next = newNode;
                sortedNext = newNode;
            }

            // push next pointer if possible
            if (smallestHead.next != null) {
                heap.offer(smallestHead.next);
            }
        }
        return sortedHead;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/swap-nodes-in-pairs/">Swap Nodes in Pairs</a>
     * Given a linked list, swap every two adjacent nodes and return its head.
     * <p/>
     * For example,
     * Given 1->2->3->4, you should return the list as 2->1->4->3.
     * <p/>
     * Your algorithm should use only constant space. You may not modify the values in the list, only nodes itself can be changed.
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode current = head;
        ListNode prev = null;
        ListNode newHead = null;
        while (current != null && current.next != null) {
            ListNode left = current;
            ListNode right = current.next;
            ListNode next = current.next.next;
            if (prev != null) prev.next = right;
            right.next = left;
            left.next = next;
            current = next;
            prev = left;
            if (newHead == null) newHead = right;
        }
        return newHead;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/reverse-nodes-in-k-group/">Reverse Nodes in k-Group</a>
     * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
     * <p/>
     * If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.
     * <p/>
     * You may not alter the values in the nodes, only nodes itself may be changed.
     * <p/>
     * Only constant memory is allowed.
     * <p/>
     * For example,
     * Given this linked list: 1->2->3->4->5
     * <p/>
     * For k = 2, you should return: 2->1->4->3->5
     * <p/>
     * For k = 3, you should return: 3->2->1->4->5
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null) return head;
        if (k < 2) return head;
        ListNode newHead = null;
        ListNode start, end; // group K start and end nodes
        ListNode leftStart = null, leftEnd; // group K nodes before start and end
        ListNode nextGroupStart = head;
        ListNode prevGroupEnd = null;

        while (true) {
            // find leftEnd and end nodes (where a group K ends)
            start = end = nextGroupStart;
            if (prevGroupEnd != null) leftStart = prevGroupEnd;
            int layer = k;
            // init end, leftEnd
            int toEnd = layer - 1;
            boolean reachedEnd = false;
            while (toEnd > 0) {
                // reached the end of the list, no need to reverse the last incomplete group
                if (end == null) {
                    reachedEnd = true;
                    break;
                }
                end = end.next;
                toEnd--;
            }

            if (reachedEnd || end == null) break;

            nextGroupStart = end.next;

            boolean firstLayer = true;
            while (layer > 1) {
                toEnd = layer - 1;
                reachedEnd = false;
                leftEnd = start;
                while (toEnd > 1) {
                    // reached the end of the list, no need to reverse the last incomplete group
                    if (end == null) {
                        reachedEnd = true;
                        break;
                    }
                    leftEnd = leftEnd.next;
                    toEnd--;
                }

                if (reachedEnd || end == null) break;

                // swap start and end nodes
                ListNode nextStart = start.next;
                if (leftStart != null) leftStart.next = end;
                leftEnd.next = start;
                ListNode startNext = start.next;
                start.next = end.next;
                end.next = startNext;
                if (firstLayer) {
                    prevGroupEnd = start;
                    firstLayer = false;
                }
                if (newHead == null) {
                    newHead = end;
                }

                start = nextStart;
                leftStart = end;
                end = leftEnd;

                layer -= 2;
            }

        }
        // newHead is null when k > list size
        return newHead != null ? newHead : head;

    }

    /**
     * <a href="http://oj.leetcode.com/problems/remove-duplicates-from-sorted-array/">Remove Duplicates from Sorted Array</a>
     * Given a sorted array, remove the duplicates in place such that each element appear only once and return the new length.
     * <p/>
     * Do not allocate extra space for another array, you must do this in place with constant memory.
     * <p/>
     * For example,
     * Given input array A = [1,1,2],
     * <p/>
     * Your function should return length = 2, and A is now [1,2].
     */
    public int removeDuplicates(int[] A) {
        if (A == null) return 0;
        if (A.length < 2) return A.length;
        int insert = 1;
        int read = 1;
        while (read < A.length) {
            if (A[read] == A[read - 1]) read++;
            else {
                A[insert++] = A[read++];
            }
        }
        return insert;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/remove-element/">Remove Element</a>
     * Given an array and a value, remove all instances of that value in place and return the new length.
     * <p/>
     * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
     */
    public int removeElement(int[] A, int elem) {
        int insert = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] != elem) {
                A[insert++] = A[i];
            }
        }
        return insert;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/insertion-sort-list/">Insertion Sort List</a>
     * Sort a linked list using insertion sort.
     */
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) return head;

        int sorted = 0;

        ListNode newHead = head;
        ListNode read = head.next;
        ListNode prevRead = head;
        while (read != null) {
            // find appropriate position in the sorted area
            ListNode insert = newHead;
            ListNode prevInsert = null;
            int sortedIndex = 0;
            while (insert.val < read.val && sortedIndex <= sorted) {
                prevInsert = insert;
                insert = insert.next;
                sortedIndex++;
            }
            if (insert != read) {
                // insert the read node between prevInsert and insert nodes
                prevRead.next = read.next;
                if (prevInsert != null) prevInsert.next = read;
                else newHead = read;

                read.next = insert;
                read = prevRead.next;

            } else {
                prevRead = read;
                read = read.next;
            }
            sorted++;
        }

        return newHead;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/implement-strstr/">Implement strStr()</a>
     * Implement strStr().
     * <p/>
     * Returns a pointer to the first occurrence of needle in haystack, or null if needle is not part of haystack.
     * <p/>
     * Solutions: brute-force, Boyer-Moore, Rabin-Karp (no KMP, sorry).
     */
    public String strStr(String haystack, String needle) {
//        return strStrBruteforce(haystack, needle);
        return strStrBoyerMoore(haystack, needle);
    }

    private String strStrBruteforce(String haystack, String needle) {
        if (needle == null || needle.isEmpty()) return haystack;
        if (haystack.length() < needle.length()) return null;

        for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            int j;
            for (j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) break;
            }
            if (j == needle.length()) return haystack.substring(i);

        }
        return null;
    }

    public String strStrBoyerMoore(String haystack, String needle) {
        if (needle == null || needle.isEmpty()) return haystack;
        if (haystack.length() < needle.length()) return null;

        int R = 256;
        int[] right = new int[R];
        for (int i = 0; i < R; i++) {
            right[i] = -1;
        }
        for (int c = 0; c < needle.length(); c++) {
            right[needle.charAt(c)] = c;
        }

        int skip;
        int M = needle.length();
        for (int i = 0; i <= haystack.length() - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >= 0; j--) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    skip = j - right[haystack.charAt(i + j)];
                    if (skip < 1) skip = 1;
                    break;
                }
            }
            if (skip == 0) return haystack.substring(i);
        }

        return null;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/divide-two-integers/">Divide Two Integers</a>
     * Divide two integers without using multiplication, division and mod operator.
     */
    public int divide(int dividend, int divisor) {
        boolean isNegative = (dividend < 0 && divisor > 0 || dividend > 0 && divisor < 0);
        if (dividend == 0) return 0;
        if (divisor == 1) return dividend;
        if (divisor == dividend) return 1;

        int result = divideLongBinary(Math.abs((long) dividend), Math.abs((long) divisor));

        return isNegative ? -result : result;
    }

    // trivial ineffective implementation
    private int divideSubtract(long dividend, long divisor) {
        long toDividend = 0;
        long result = 0;
        if (divisor == Integer.MIN_VALUE) return (dividend > 0) ? -1 : 1;

        while (toDividend <= dividend) {
            toDividend += divisor;
            result++;
        }
        return (int) result - 1;
    }

    /**
     * <a href="http://stackoverflow.com/a/5387432/959852">Division Without using /</a>
     * The typical way is to shift and subtract. This is basically pretty similar to long division as we learned it in school.
     * The big difference is that in decimal division you need to estimate the next digit of the result.
     * In binary, that's trivial. The next digit is always either 0 or 1. If the (left-shifted) divisor is less than
     * or equal to the current dividend value, you subtract it, and the current bit of the result is a 1. If it's greater,
     * then the current bit of the result is a 0.
     */
    private int divideLongBinary(long dividend, long divisor) {

        long answer = 0;
        long current = 1;
        long denom = divisor;

        while (denom <= dividend) {
            denom <<= 1;
            current <<= 1;
        }
        denom >>= 1;
        current >>= 1;

        while (current != 0) {
            if (dividend >= denom) {
                dividend -= denom;
                answer |= current;
            }
            current >>= 1;
            denom >>= 1;
        }
        return (int) answer;

    }

    /**
     * <a href="http://oj.leetcode.com/problems/substring-with-concatenation-of-all-words/">Substring with Concatenation of All Words</a>
     * You are given a string, S, and a list of words, L, that are all of the same length.
     * Find all starting indices of substring(s) in S that is a concatenation of each word in L exactly once and without any intervening characters.
     * <p/>
     * For example, given:
     * S: "barfoothefoobarman"
     * L: ["foo", "bar"]
     * <p/>
     * You should return the indices: [0,9].
     * (order does not matter).
     */
    public List<Integer> findSubstring(String S, String[] L) {
        return findSubstringHashmap(S, L);
    }

    private List<Integer> findSubstringHashmap(String S, String[] L) {
        List<Integer> result = new ArrayList<Integer>();
        int wordLen = L[0].length();

        // init maps
        Map<String, List<Integer>> wordToStringIndexMap = new HashMap<String, List<Integer>>();
        Map<String, Integer> wordToCounterMap = new HashMap<String, Integer>();
        for (String word : L) {
            wordToStringIndexMap.put(word, new ArrayList<Integer>());
            if (wordToCounterMap.get(word) == null) {
                wordToCounterMap.put(word, 1);
            } else
                wordToCounterMap.put(word, wordToCounterMap.get(word) + 1);
        }

        // populate index map
        for (int i = 0; i < S.length(); i++) {
            for (String word : wordToCounterMap.keySet()) {
                if (S.startsWith(word, i)) {
                    wordToStringIndexMap.get(word).add(i);
                }
            }
        }

        for (int k = 0; k < wordLen; k++) {
            Map<String, Integer> currentWordToCounterMap = new HashMap<String, Integer>();

            int left;
            int right;
            for (right = k; right < S.length(); right += wordLen) {
                left = right - wordLen * L.length;
                if (right + wordLen > S.length()) break;

                if (currentWordToCounterMap.equals(wordToCounterMap)) {
                    result.add(left);
                }

                if (left >= 0) {
                    String leftToken = S.substring(left, left + wordLen);
                    if (wordToCounterMap.containsKey(leftToken)) {
                        decrement(currentWordToCounterMap, leftToken);
                    }
                }

                String token = S.substring(right, right + wordLen);
                if (!wordToCounterMap.containsKey(token)) continue;

                increment(currentWordToCounterMap, token);
            }
            if (currentWordToCounterMap.equals(wordToCounterMap)) {
                result.add(right - wordLen * L.length);
            }
        }

        return result;
    }

    private void increment(Map<String, Integer> map, String key) {
        if (!map.containsKey(key)) {
            map.put(key, 1);
        } else {
            map.put(key, map.get(key) + 1);
        }
    }

    private void decrement(Map<String, Integer> map, String key) {
        map.put(key, map.get(key) - 1);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/next-permutation/">Next Permutation</a>
     * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
     * <p/>
     * If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
     * <p/>
     * The replacement must be in-place, do not allocate extra memory.
     * <p/>
     * Here are some examples. Inputs are in the left-hand column and its corresponding outputs are in the right-hand column.
     * 1,2,3 → 1,3,2
     * 3,2,1 → 1,2,3
     * 1,1,5 → 1,5,1
     * <a href="http://en.wikipedia.org/wiki/Permutation#Generation_in_lexicographic_order">Solution</a>
     */
    public void nextPermutation(int[] num) {
        int n = num.length;
        // 1. Find the largest index k such that a[k] < a[k + 1].
        // If no such index exists, the permutation is the last permutation.
        int k = -1;
        for (int i = n - 2; i >= 0; i--) {
            if (num[i] < num[i + 1]) {
                k = i;
                break;
            }
        }

        if (k == -1) {
            for (int i = 0, j = n - 1; i < j; i++, j--) {
                int tmp = num[i];
                num[i] = num[j];
                num[j] = tmp;
            }
            return;
        }

        // 2. Find the largest index l such that a[k] < a[l].
        int l = 0;
        for (int i = 0; i < n; i++) {
            if (num[k] < num[i]) {
                l = i;
            }
        }

        // 3. Swap the value of a[k] with that of a[l].
        int tmp = num[k];
        num[k] = num[l];
        num[l] = tmp;

        // 4. Reverse the sequence from a[k + 1] up to and including the final element a[n].
        for (int i = k + 1, j = n - 1; i < j; i++, j--) {
            tmp = num[i];
            num[i] = num[j];
            num[j] = tmp;
        }
    }

    /**
     * <a href="http://oj.leetcode.com/problems/longest-valid-parentheses/">Longest Valid Parentheses</a>
     * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed)
     * parentheses substring.
     * <p/>
     * For "(()", the longest valid parentheses substring is "()", which has length = 2.
     * <p/>
     * Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.
     * <p/>
     * <a href="http://zhihengli.blogspot.com/2013/08/leetcode-longest-valid-parentheses.html">Solution</a>
     */
    public int longestValidParentheses(String s) {
        int longest = 0;
        int lastClosing = -1;
        LinkedList<Integer> stack = new LinkedList<Integer>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                if (stack.isEmpty()) {
                    lastClosing = i;
                } else {
                    stack.pop();
                    if (stack.isEmpty()) {
                        longest = Math.max(longest, i - lastClosing);
                    } else {
                        longest = Math.max(longest, i - stack.peek());
                    }
                }
            }

        }
        return longest;
    }

    private int longestValidParenthesisExpansion(String s) {
        int n = s.length();
        int[] A = new int[n + 1];
        int longest = 0;

        int start;
        int j;
        for (int i = 0; i < n / 2; i++) {
            // find
            j = 0;
            start = 0;
            while (j < n) {
                if (A[j] != 0) {
                    j += A[j];
                } else if (s.charAt(j) == '(' && j + 1 + A[j + 1] < s.length() && s.charAt(j + 1 + A[j + 1]) == ')') {
                    A[j] = A[j + 1] + 2;
                    j += A[j];
                } else {
                    if (j - start > longest) {
                        longest = j - start;
                    }
                    j++;
                    start = j;
                }
            }
            if (j - start > longest) {
                longest = j - start;
            }

            // merge
            j = 0;
            while (j < n) {
                start = j;
                while (j < n && A[j] != 0) {
                    j += A[j];
                }
                A[start] = j - start;
                if (A[start] > longest) {
                    longest = A[start];
                }
                j++;
            }

        }

        return longest;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/search-in-rotated-sorted-array/">Search in Rotated Sorted Array</a>
     * Suppose a sorted array is rotated at some pivot unknown to you beforehand.
     * <p/>
     * (i.e., 0 1 2 4 5 6 7 might become 4 5 6 7 0 1 2).
     * <p/>
     * You are given a target value to search. If found in the array return its index, otherwise return -1.
     * <p/>
     * You may assume no duplicate exists in the array.
     */
    public int search(int[] A, int target) {

        int l = 0;
        int r = A.length - 1;

        while (l <= r) {
            int mid = (l + r) / 2;
            if (A[mid] == target) return mid;

            if (A[l] <= A[mid]) {
                if (target >= A[l] && target < A[mid])
                    r = mid - 1;
                else l = mid + 1;
            } else {
                if (target > A[mid] && target <= A[r])
                    l = mid + 1;
                else r = mid - 1;
            }
        }

        return -1;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/search-for-a-range/">Search for a Range</a>
     * Given a sorted array of integers, find the starting and ending position of a given target value.
     * <p/>
     * Your algorithm's runtime complexity must be in the order of O(log n).
     * <p/>
     * If the target is not found in the array, return [-1, -1].
     * <p/>
     * For example,
     * Given [5, 7, 7, 8, 8, 10] and target value 8,
     * return [3, 4].
     * <p/>
     * Idea: do a binary search for the target value. If not found return [-1, -1]. Otherwise do two more binary searches for
     * the value range boundaries
     */
    public int[] searchRange(int[] A, int target) {

        int l = 0;
        int r = A.length - 1;
        if (l == r) {
            if (A[0] == target) return new int[]{0, 0};
            else return new int[]{-1, -1};
        }
        // Step 1. Find target
        int targetIndex = -1;

        while (l <= r) {
            int mid = (l + r) / 2;
            if (A[mid] == target) {
                targetIndex = mid;
                break;
            }
            if (target < A[mid]) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        if (targetIndex == -1) return new int[]{-1, -1};

        // Step 2.1. Find left boundary
        l = 0;
        r = targetIndex;
        int left = -1;
        while (l <= r) {
            if (l == r || A[l] == target) {
                left = l;
                break;
            }
            int mid = (l + r) / 2;
            if (A[mid] != target)
                l = mid + 1;
            else r = mid;
        }

        // Step 2.2. Find right boundary
        l = targetIndex;
        r = A.length - 1;
        int right = -1;
        while (l <= r) {
            if (l == r || A[r] == target) {
                right = r;
                break;
            }
            int mid = (l + r + 1) / 2;
            if (A[mid] != target)
                r = mid - 1;
            else l = mid;
        }

        return new int[]{left, right};

    }

    /**
     * <a href="http://oj.leetcode.com/problems/search-insert-position/">Search Insert Position</a>
     * Given a sorted array and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.
     * <p/>
     * You may assume no duplicates in the array.
     * <p/>
     * Here are few examples.
     * [1,3,5,6], 5 → 2
     * [1,3,5,6], 2 → 1
     * [1,3,5,6], 7 → 4
     * [1,3,5,6], 0 → 0
     */
    public int searchInsert(int[] A, int target) {
        int n = A.length - 1;
        int l = 0;
        int r = n;
        int mid = 0;
        int position;

        while (l < r) {
            mid = (l + r) / 2;
            if (A[mid] == target) {
                break;
            }
            if (target < A[mid]) r = mid - 1;
            else l = mid + 1;
        }
        mid = (l + r) / 2;

        if (l == n && target > A[n]) {
            position = n + 1;
        } else if (l == 0 && target < A[0])
            position = 0;
        else if (target > A[mid]) {
            position = mid + 1;
        } else
            position = mid;

        return position;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/valid-sudoku/">Valid Sudoku</a>
     * Determine if a Sudoku is valid, according to: Sudoku Puzzles - The Rules.
     * <p/>
     * The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
     */
    public boolean isValidSudoku(char[][] board) {
        // check 3x3 cells
        boolean[] table;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                table = new boolean[9];
                for (int k = 0; k < 9; k++) {
                    int x = j * 3 + k % 3;
                    int y = i * 3 + k / 3;
                    char num = board[x][y];
                    if (num == '.') continue;
                    if (table[num - '1'])
                        return false;
                    table[num - '1'] = true;
                }
            }
        }

        // check rows
        for (int i = 0; i < 9; i++) {
            table = new boolean[9];
            for (int k = 0; k < 9; k++) {
                char num = board[i][k];
                if (num == '.') continue;
                if (table[num - '1'])
                    return false;
                table[num - '1'] = true;
            }
        }

        // check columns
        for (int i = 0; i < 9; i++) {
            table = new boolean[9];
            for (int k = 0; k < 9; k++) {
                char num = board[k][i];
                if (num == '.') continue;
                if (table[num - '1'])
                    return false;
                table[num - '1'] = true;
            }
        }

        return true;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/sudoku-solver/">Sudoku Solver</a>
     * Write a program to solve a Sudoku puzzle by filling the empty cells.
     * <p/>
     * Empty cells are indicated by the character '.'.
     * <p/>
     * You may assume that there will be only one unique solution.
     */
    public void solveSudoku(char[][] board) {
        boolean[][] cellNotAvail = new boolean[9][9];
        boolean[][] rowNotAvail = new boolean[9][9];
        boolean[][] colNotAvail = new boolean[9][9];
        for (int cell = 0; cell < 9 * 9; cell++) {
            int x = cell % 9;
            int y = cell / 9;
            int cellX = x / 3;
            int cellY = y / 3;
            int cellNum = cellY * 3 + cellX;

            if (board[y][x] == '.') continue;
            int num = board[y][x] - '1';
            cellNotAvail[cellNum][num] = true;
            rowNotAvail[y][num] = true;
            colNotAvail[x][num] = true;
        }
        solveSudoku(board, cellNotAvail, rowNotAvail, colNotAvail, 0);

    }

    private boolean solveSudoku(char[][] board, boolean[][] cellNotAvail, boolean[][] rowNotAvail, boolean[][] colNotAvail, int cell) {
        if (cell == 9 * 9) {
            return true;
        }

        int x = cell % 9;
        int y = cell / 9;
        int cellX = x / 3;
        int cellY = y / 3;
        int cellNum = cellY * 3 + cellX;

        if (board[y][x] != '.') {
            return solveSudoku(board, cellNotAvail, rowNotAvail, colNotAvail, cell + 1);
        }

        for (char num = 0; num < 9; num++) {
            boolean isAvailable = !cellNotAvail[cellNum][num] && !rowNotAvail[y][num] && !colNotAvail[x][num];
            boolean isCellValid;
            if (isAvailable) {
                cellNotAvail[cellNum][num] = true;
                rowNotAvail[y][num] = true;
                colNotAvail[x][num] = true;
                board[y][x] = (char) ('1' + num);

                isCellValid = solveSudoku(board, cellNotAvail, rowNotAvail, colNotAvail, cell + 1);

                if (!isCellValid) {
                    board[y][x] = '.';
                }
                cellNotAvail[cellNum][num] = false;
                rowNotAvail[y][num] = false;
                colNotAvail[x][num] = false;

                if (isCellValid) return true;

            }
        }
        return false;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/count-and-say/">Count and Say</a>
     * The count-and-say sequence is the sequence of integers beginning as follows:
     * 1, 11, 21, 1211, 111221, ...
     * <p/>
     * 1 is read off as "one 1" or 11.
     * 11 is read off as "two 1s" or 21.
     * 21 is read off as "one 2, then one 1" or 1211.
     * <p/>
     * Given an integer n, generate the nth sequence.
     * <p/>
     * Note: The sequence of integers will be represented as a string.
     * <p/>
     * Solution: no magic. Count from 1 to n
     */
    public String countAndSay(int n) {
        StringBuilder sb = new StringBuilder("1");
        while (n > 1) {
            StringBuilder newItem = new StringBuilder();
            char ch = sb.charAt(0);
            int count = 0;
            int index = 0;
            while (index < sb.length()) {
                while (index < sb.length() && sb.charAt(index) == ch) {
                    count++;
                    index++;
                }
                newItem.append(count);
                newItem.append(ch);
                count = 0;
                if (index < sb.length())
                    ch = sb.charAt(index);
            }
            n--;
            sb = newItem;
        }

        return sb.toString();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/lru-cache/">LRU Cache</a>
     * Design and implement a data structure for Least Recently Used (LRU) cache.
     * It should support the following operations: get and set.
     * </p>
     * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
     * <p/>
     * set(key, value) - Set or insert the value if the key is not already present.
     * When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
     * <p/>
     * Idea: use two data structures:
     * 1. HashMap for storing key to value mapping
     * 2. Linked list for storing cached keys. Keep head and tail pointers.
     */
    public static class LRUCache {
        private final int capacity;
        private final Map<Integer, Integer> map;
        private final Map<Integer, Node> keyToNodeMap;
        private int size;
        private Node head;
        private Node tail;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<Integer, Integer>(capacity);
            this.keyToNodeMap = new HashMap<Integer, Node>();
            this.head = null;
            this.tail = null;
            size = 0;
        }

        public int get(int key) {
            // Case 1: there is not such key in the map. Do nothing
            if (!map.containsKey(key))
                return -1;

            // Case 2: there is such key in the map. Move the key in the list to the head

            /*Node keyNode = head;
            while (keyNode.data != key) {
                keyNode = keyNode.next;
            }*/
            Node keyNode = keyToNodeMap.get(key);
            if (keyNode == null) return -1;

            if (head != tail && keyNode.prev != null) {
                keyNode.prev.next = keyNode.next;
                if (keyNode.next != null)
                    keyNode.next.prev = keyNode.prev;
                if (head != null)
                    head.prev = keyNode;
                keyNode.next = head;
                if (tail == keyNode) {
                    tail = keyNode.prev;
                }
                keyNode.prev = null;
                head = keyNode;
                keyToNodeMap.put(key, head);
            }

            return map.get(key);
        }

        public void set(int key, int value) {
            // case 1: the map contains key: renew value, move the key node to the head
            if (map.containsKey(key)) {
                /*Node keyNode = head;
                while (keyNode.data != key) {
                    keyNode = keyNode.next;
                }*/
                Node keyNode = keyToNodeMap.get(key);
                if (tail == head) { // there is exactly one element
                    head.data = key;
                } else { // more than 1 element
                    if (keyNode.prev == null) { // this is the head
                        keyNode.data = key;
                    } else { // the key element is somewhere in the middle
                        keyNode.prev.next = keyNode.next;
                        if (keyNode.next != null) {
                            keyNode.next.prev = keyNode.prev;
                        }
                        if (tail == keyNode) {
                            tail = keyNode.prev;
                        }

                        head.prev = keyNode;
                        keyNode.next = head;
                        head = keyNode;
                        head.prev = null;
                        keyToNodeMap.put(key, head);
                    }
                }
                map.put(key, value);
            } else { // this is a brand new element
                if (size == capacity) { // reached capacity
                    map.remove(tail.data);
                    keyToNodeMap.remove(tail.data);
                    tail = tail.prev;
                    if (tail != null) tail.next = null;
                    size--;
                }
                Node newNode = new Node(key, null, head);
                if (head != null) head.prev = newNode;
                if (tail == null) {
                    tail = newNode;
                }
                head = newNode;
                size++;
                map.put(key, value);
                keyToNodeMap.put(key, head);
            }
        }

        class Node {
            int data;
            Node prev;
            Node next;

            Node(int data, Node prev, Node next) {
                this.data = data;
                this.prev = prev;
                this.next = next;
            }
        }
    }

    /**
     * <a href="http://oj.leetcode.com/problems/sort-list/">Sort List</a>
     * Sort a linked list in O(n log n) time using constant space complexity.
     */
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        int chunkLength = 1;
        ListNode prevFrom1;
        ListNode from1;
        ListNode from2;
        ListNode newHead = head;
        boolean isMergeNecessary = true;

        ListNode currentNode;
        while (true) { // chunk size loop: 2, 4, 8, 16...
            boolean isFirstChunk = true;
            currentNode = newHead;
            ListNode[] mergedHeadTail = new ListNode[2];

            while (currentNode != null) { // list loop
                int elementsInChunk = 0;

                from1 = currentNode;
                while (elementsInChunk < chunkLength) { // 1st chunk
                    currentNode = currentNode.next;
                    if (currentNode == null) {
                        if (isFirstChunk)
                            isMergeNecessary = false;
                        break;
                    }
                    elementsInChunk++;
                }

                if (!isMergeNecessary) return newHead;

                from2 = currentNode;

                ///
                if (from2 == null) break;

                ListNode prevTail = mergedHeadTail[1];
                merge(from1, from2, chunkLength, mergedHeadTail);

                prevFrom1 = mergedHeadTail[1];
                currentNode = prevFrom1.next;
                if (prevTail != null) {
                    prevTail.next = mergedHeadTail[0];
                }

                if (isFirstChunk) {
                    newHead = mergedHeadTail[0];
                }

                isFirstChunk = false;
            }

            chunkLength *= 2;
        }
    }

    private void merge(ListNode from1, ListNode from2, int numOfNodes, ListNode[] headTail) {

        int counter1 = 0;
        int counter2 = 0;

        ListNode mergedHead;
        ListNode mergedTail = from1;
        ListNode currentNode1 = from1;
        ListNode currentNode2 = from2;
        ListNode mergedCurrent;

        if (currentNode2 == null || currentNode1.val <= currentNode2.val) {
            mergedHead = from1;
            currentNode1 = currentNode1.next;
            counter1++;
        } else {
            mergedHead = from2;
            currentNode2 = currentNode2.next;
            counter2++;
        }
        mergedCurrent = mergedHead;

        while (true) {
            // reached the end of the first chunk. Copy the rest of the second chunk
            if (counter1 == numOfNodes) {
                while (counter2 < numOfNodes && currentNode2 != null) {
                    mergedCurrent.next = currentNode2;
                    mergedCurrent = mergedCurrent.next;
                    currentNode2 = currentNode2.next;
                    mergedTail = mergedCurrent;
                    counter2++;
                }
                break;
            }

            // reached the end of the second chunk. Copy the rest of the first chunk
            else if (counter2 == numOfNodes) {
                while (counter1 < numOfNodes && currentNode1 != null) {
                    mergedCurrent.next = currentNode1;
                    mergedCurrent = mergedCurrent.next;
                    currentNode1 = currentNode1.next;
                    mergedTail = mergedCurrent;
                    counter1++;
                }
                break;
            }

            // need to advance one counter
            else {
                if (currentNode2 == null || currentNode1.val <= currentNode2.val) {
                    counter1++;
                    mergedCurrent.next = currentNode1;
                    mergedCurrent = mergedCurrent.next;
                    currentNode1 = currentNode1.next;
                    mergedTail = mergedCurrent;
                } else {
                    counter2++;
                    mergedCurrent.next = currentNode2;
                    mergedCurrent = mergedCurrent.next;
                    currentNode2 = currentNode2.next;
                    mergedTail = mergedCurrent;
                }

            }

        }
        mergedTail.next = currentNode2;
        headTail[0] = mergedHead;
        headTail[1] = mergedTail;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/combination-sum/">Combination Sum</a>
     * Given a set of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.
     * <p/>
     * The same repeated number may be chosen from C unlimited number of times.
     * <p/>
     * Note:
     * <p/>
     * All numbers (including target) will be positive integers.
     * Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
     * The solution set must not contain duplicate combinations.
     * <p/>
     * For example, given candidate set 2,3,6,7 and target 7,
     * A solution set is:
     * [7]
     * [2, 2, 3]
     */
    public ArrayList<ArrayList<Integer>> combinationSum(int[] candidates, int target) {
        ArrayList<ArrayList<Integer>> allCombos = new ArrayList<ArrayList<Integer>>();
        combinationSum(candidates, target, 0, new ArrayList<Integer>(), allCombos, true);
        return allCombos;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/combination-sum-ii/">Combination Sum II</a>
     * Given a collection of candidate numbers (C) and a target number (T), find all unique combinations in C where the candidate numbers sums to T.
     * <p/>
     * Each number in C may only be used once in the combination.
     * <p/>
     * Note:
     * <p/>
     * All numbers (including target) will be positive integers.
     * Elements in a combination (a1, a2, … , ak) must be in non-descending order. (ie, a1 ≤ a2 ≤ … ≤ ak).
     * The solution set must not contain duplicate combinations.
     * <p/>
     * For example, given candidate set 10,1,2,7,6,1,5 and target 8,
     * A solution set is:
     * [1, 7]
     * [1, 2, 5]
     * [2, 6]
     * [1, 1, 6]
     */
    public ArrayList<ArrayList<Integer>> combinationSum2(int[] candidates, int target) {
        ArrayList<ArrayList<Integer>> allCombos = new ArrayList<ArrayList<Integer>>();
        combinationSum(candidates, target, -1, new ArrayList<Integer>(), allCombos, false);
        return allCombos;
    }

    private void combinationSum(int[] candidates, int left, int level, ArrayList<Integer> numbers, ArrayList<ArrayList<Integer>> combinations, boolean reuseCandidates) {
        if (left == 0) {
            ArrayList<Integer> goodCombo = new ArrayList<Integer>(numbers);
            Collections.sort(goodCombo);
            if (!combinations.contains(goodCombo))
                combinations.add(goodCombo);
            return;
        }
        if (left < 0) {
            return;
        }

        for (int i = level + (reuseCandidates ? 0 : 1); i < candidates.length; i++) {
            numbers.add(candidates[i]);
            combinationSum(candidates, left - candidates[i], i, numbers, combinations, reuseCandidates);
            numbers.remove((Integer) candidates[i]);
        }
    }

    /**
     * <a href="http://oj.leetcode.com/problems/first-missing-positive/">First Missing Positive</a>
     * Given an unsorted integer array, find the first missing positive integer.
     * <p/>
     * For example,
     * Given [1,2,0] return 3,
     * and [3,4,-1,1] return 2.
     * <p/>
     * Your algorithm should run in O(n) time and uses constant space.
     * <p/>
     * Solution: try to match array indices and array values.
     * http://tianrunhe.wordpress.com/2012/07/15/finding-the-1st-missing-positive-int-in-an-array-first-missing-positive/
     */
    public int firstMissingPositive(int[] A) {
        int i = 0;
        while (i < A.length) {
            int v = A[i];
            while (v - 1 != i) {
                if (v <= 0 || v > A.length || v == A[v - 1])
                    break;
                int tmp = A[v - 1];
                A[v - 1] = A[i];
                A[i] = tmp;
                v = A[i];
            }
            i++;
        }

        for (i = 0; i < A.length; i++) {
            if (A[i] - 1 != i) return i + 1;
        }
        return A.length + 1;

    }

    /**
     * <a href="http://oj.leetcode.com/problems/trapping-rain-water/">Trapping Rain Water</a>
     * Given n non-negative integers representing an elevation map where the width of each bar is 1,
     * compute how much water it is able to trap after raining.
     * <p/>
     * For example,
     * Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.
     * <p/>
     * Solution: do two passes: from left to right and from right to left:
     * Pass 1: keep watermark level raising with bar height
     * Pass 2: keep watermark level lowering with bar height
     */
    public int trap(int[] A) {
        if (A.length == 0) return 0;

        int[] W = new int[A.length];
        int watermark = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] > watermark) {
                watermark = A[i];
            }
            W[i] = watermark;
        }

        int volume = 0;
        watermark = A[A.length - 1];
        for (int i = A.length - 1; i >= 0; i--) {
            if (A[i] > watermark) {
                watermark = A[i];
            }
            W[i] = Math.min(watermark, W[i]);
            volume += W[i] - A[i];
        }

        return volume;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/multiply-strings/">Multiply Strings</a>
     * Given two numbers represented as strings, return multiplication of the numbers as a string.
     * <p/>
     * Note: The numbers can be arbitrarily large and are non-negative.
     * <p/>
     * Solution: <a href="https://class.coursera.org/algo-004/lecture/167">Karatsuba algorithm</a>
     */
    public String multiply(String num1, String num2) {
        int maxLen = 9;
        long limit = (long) Math.pow(10, maxLen);
        return mult(num1, num2, maxLen, limit, 0);
    }

    private String mult(String num1, String num2, int maxLen, long limit, int level) {
        if (num2 == null || num2.isEmpty()) return num1;
        if (num1 == null || num1.isEmpty()) return num2;
        // base case
        int l1 = num1.length();
        int l2 = num2.length();
        if (l1 <= maxLen && l2 <= maxLen) {
            return Long.toString(Long.parseLong(num1) * Long.parseLong(num2));
        }
        // pad with zeros
        if (l1 != l2) {
            char[] zeros = new char[Math.abs(l1 - l2)];
            Arrays.fill(zeros, '0');
            if (l1 < l2) {
                num1 = new String(zeros) + num1;
            } else if (l1 > l2) {
                num2 = new String(zeros) + num2;
            }
        }

        int m = Math.max(l1, l2);
        int m2 = m / 2 + m % 2;

        String a = num1.substring(0, m - m2);
        String b = num1.substring(m - m2);
        String c = num2.substring(0, m - m2);
        String d = num2.substring(m - m2);

        String ac = mult(a, c, maxLen, limit, level + 1);
        String bd = mult(b, d, maxLen, limit, level + 1);
        String a_b__c_d = mult(sum(a, b, maxLen, limit), sum(c, d, maxLen, limit), maxLen, limit, level + 1);
        String ad_bc = subtr(subtr(a_b__c_d, ac, maxLen, limit), bd, maxLen, limit);

        StringBuilder part1 = new StringBuilder(ac);
        for (int i = 0; i < m2; i++) part1.append("00");
        StringBuilder part2 = new StringBuilder(ad_bc);
        for (int i = 0; i < m2; i++) part2.append("0");

        String part3 = bd;

        String result = sum(sum(part1.toString(), part2.toString(), maxLen, limit), part3, maxLen, limit);
        return result;
    }

    private String sum(String num1, String num2, int maxLen, long limit) {
        if (num1 == null || num1.isEmpty()) return num2;
        if (num2 == null || num2.isEmpty()) return num1;

        int carryover = 0;
        int i1 = num1.length();
        int i2 = num2.length();
        StringBuilder sum = new StringBuilder();
        while (i1 > 0 || i2 > 0) {
            long num1Part = i1 > 0 ? Long.parseLong(num1.substring(Math.max(i1 - maxLen, 0), i1)) : 0;
            long num2Part = i2 > 0 ? Long.parseLong(num2.substring(Math.max(i2 - maxLen, 0), i2)) : 0;
            long partSum = num1Part + num2Part + carryover;
            StringBuilder partSumStr = new StringBuilder(Long.toString(partSum));
            // pad with leading zeros
            while (partSumStr.length() < maxLen) partSumStr.insert(0, '0');

            if (partSum >= limit) {
                carryover = 1;
                partSumStr.deleteCharAt(0);
            } else
                carryover = 0;
            sum.insert(0, partSumStr);
            i1 -= maxLen;
            i2 -= maxLen;
        }
        if (carryover > 0) sum.insert(0, carryover);
        while (sum.charAt(0) == '0' && sum.length() > 1) sum.deleteCharAt(0);
        return sum.toString();

    }

    private String subtr(String num1, String num2, int maxLen, long limit) {
        if (num2 == null || num2.isEmpty()) return num1;
        int carryover = 0;
        int i1 = num1.length();
        int i2 = num2.length();
        StringBuilder sum = new StringBuilder();
        while (i1 > 0 || i2 > 0) {
            long num1Part = i1 > 0 ? Long.parseLong(num1.substring(Math.max(i1 - maxLen, 0), i1)) : 0;
            long num2Part = i2 > 0 ? Long.parseLong(num2.substring(Math.max(i2 - maxLen, 0), i2)) : 0;
            long partSubstr = num1Part - num2Part + carryover;

            StringBuilder partSumStr = new StringBuilder(Long.toString(partSubstr >= 0 ? partSubstr : limit + partSubstr));
            // pad with leading zeros
            while (partSumStr.length() < maxLen) partSumStr.insert(0, '0');

            if (partSubstr < 0) {
                carryover = -1;
            } else
                carryover = 0;

            sum.insert(0, partSumStr);
            i1 -= maxLen;
            i2 -= maxLen;
        }
        while (sum.charAt(0) == '0' && sum.length() > 1) sum.deleteCharAt(0);
        return sum.toString();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/wildcard-matching/">Wildcard Matching</a>
     * Implement wildcard pattern matching with support for '?' and '*'.
     * <p/>
     * '?' Matches any single character.
     * '*' Matches any sequence of characters (including the empty sequence).
     * <p/>
     * The matching should cover the entire input string (not partial).
     * <p/>
     * The function prototype should be:
     * bool isMatch(const char *s, const char *p)
     * <p/>
     * Some examples:
     * isMatch("aa","a") → false
     * isMatch("aa","aa") → true
     * isMatch("aaa","aa") → false
     * isMatch("aa", "*") → true
     * isMatch("aa", "a*") → true
     * isMatch("ab", "?*") → true
     * isMatch("aab", "c*a*b") → false
     */
    public boolean isWildcardMatch(String s, String p) {
        // compress **** -> *
        StringBuilder psb = new StringBuilder(p);
        int i = 0;
        while (i < psb.length() - 1) {
            if (psb.charAt(i) == '*' && psb.charAt(i + 1) == '*')
                psb.deleteCharAt(i);
            else
                i++;
        }

//        return isWildcardMatchRecursive(s, 0, psb.toString(), 0);
        return isWildcardMatchDPOptimal(s, psb.toString());
//        return isWildcardMatchDP(s, psb.toString());
    }

    private boolean isWildcardMatchRecursive(String s, int sFrom, String p, int pFrom) {
        if (sFrom >= s.length() && pFrom >= p.length()) return true;
        if (s.substring(sFrom).equals(p.substring(pFrom))) return true;
        if (pFrom == p.length() - 1 && p.charAt(pFrom) == '*') return true;

        if (sFrom >= s.length() && pFrom < p.length()) return false;

        if (sFrom < s.length() && pFrom >= p.length()) return false;

        if (s.charAt(sFrom) == p.charAt(pFrom) || p.charAt(pFrom) == '?')
            return isWildcardMatchRecursive(s, sFrom + 1, p, pFrom + 1);

        if (p.charAt(pFrom) == '*')
            return isWildcardMatchRecursive(s, sFrom + 1, p, pFrom) || isWildcardMatchRecursive(s, sFrom, p, pFrom + 1);

        return false;
    }

    /**
     * Solution (I was really-really close to exactly this one):
     * <a href="http://vialgorithms.blogspot.com/2013/11/wildcard-matching.html>Wildcard matching</a>
     */
    private boolean isWildcardMatchDP(String s, String p) {
        boolean[][] A = new boolean[p.length() + 1][s.length() + 1];
        A[0][0] = true;
        for (int i = 1; i < p.length(); i++) {
            if (p.charAt(i - 1) == '*')
                A[i][0] = A[i - 1][0];
        }

        for (int i = 0; i < p.length(); i++) {
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == p.charAt(i) || p.charAt(i) == '?') {
                    A[i + 1][j + 1] = A[i][j];
                } else if (p.charAt(i) == '*') {
                    A[i + 1][j + 1] = A[i][j] || A[i + 1][j] || A[i][j + 1];
                } else {
                    A[i + 1][j + 1] = false;
                }
            }
        }

        return A[p.length()][s.length()];
    }

    private boolean isWildcardMatchDPOptimal(String s, String p) {
        boolean[][] A = new boolean[2][s.length() + 1];
        A[0][0] = true;
        A[1][0] = false;

        int curr = 1;
        int prev = 0;
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*')
                A[curr][0] = A[prev][0];
            else
                A[curr][0] = false;

            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == p.charAt(i) || p.charAt(i) == '?') {
                    A[curr][j + 1] = A[prev][j];
                } else if (p.charAt(i) == '*') {
                    A[curr][j + 1] = A[prev][j] || A[curr][j] || A[prev][j + 1];
                } else {
                    A[curr][j + 1] = false;
                }
            }
//            System.arraycopy(A[1], 0, A[0], 0, s.length()+1);
//            for (int j = 0; j <= s.length(); j++) {
//                A[prev][j] = A[curr][j];
//            }
            int tmp = prev;
            prev = curr;
            curr = tmp;
        }

        return A[prev][s.length()];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/jump-game/">Jump Game</a>
     * Given an array of non-negative integers, you are initially positioned at the first index of the array.
     * <p/>
     * Each element in the array represents your maximum jump length at that position.
     * <p/>
     * Determine if you are able to reach the last index.
     * <p/>
     * For example:
     * A = [2,3,1,1,4], return true.
     * <p/>
     * A = [3,2,1,0,4], return false.
     */
    public boolean canJump(int[] A) {
        int n = A.length;
        if (n == 0 || n == 1) return true;

        int maxRightPos = 0;

        for (int i = 0; i < n; i++) {
            if (i > maxRightPos) return false;
            maxRightPos = Math.max(maxRightPos, A[i] + i);
        }
        return true;

    }

    /**
     * <a href="http://oj.leetcode.com/problems/jump-game-ii/">Jump Game II</a>
     * Given an array of non-negative integers, you are initially positioned at the first index of the array.
     * <p/>
     * Each element in the array represents your maximum jump length at that position.
     * <p/>
     * Your goal is to reach the last index in the minimum number of jumps.
     * <p/>
     * For example:
     * Given array A = [2,3,1,1,4]
     * <p/>
     * The minimum number of jumps to reach the last index is 2. (Jump 1 step from index 0 to 1, then 3 steps to the last index.)
     */
    public int jump(int[] A) {
        if (A.length == 0 || A.length == 1) return 0;
        int maxLast = 0;
        int currentLast = 0;
        int numOfJumps = 0;

        for (int i = 0; i < A.length; i++) {
            if (i > maxLast) {
                maxLast = currentLast;
                numOfJumps++;
            }

            currentLast = Math.max(currentLast, A[i] + i);
        }
        return numOfJumps;

    }

    /**
     * <a href="http://oj.leetcode.com/problems/permutations/">Permutations</a>
     * Given a collection of numbers, return all possible permutations.
     * <p/>
     * For example,
     * [1,2,3] have the following permutations:
     * [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], and [3,2,1].
     */
    public ArrayList<ArrayList<Integer>> permute(int[] num) {
        return permuteBacktracking(num, 0, true);
//        return permuteHeap(num, 0, true);
    }

    /**
     * <a href="http://oj.leetcode.com/problems/permutations-ii/">Permutations II </a>
     * Given a collection of numbers that might contain duplicates, return all possible unique permutations.
     * <p/>
     * For example,
     * [1,1,2] have the following unique permutations:
     * [1,1,2], [1,2,1], and [2,1,1].
     */
    public ArrayList<ArrayList<Integer>> permuteUnique(int[] num) {
        return permuteBacktracking(num, 0, false);
    }


    /**
     * <a href="http://www.cse.ohio-state.edu/~gurari/course/cis680/cis680Ch19.html#QQ1-51-133">Generating Permutations</a>
     * At each stage of the permutation process, the given set of elements consists of two parts: a subset of values
     * that already have been processed, and a subset that still needs to be processed. This logical separation can be
     * physically realized by exchanging, in the i'th step, the i'th value with the value being chosen at that stage.
     * That approaches leaves the first subset in the first i locations of the outcome.
     * permute(i)
     * if i == N  output A[N]
     * else
     * for j = i to N do
     * swap(A[i], A[j])
     * permute(i+1)
     * swap(A[i], A[j])
     */
    private ArrayList<ArrayList<Integer>> permuteBacktracking(int[] num, int level, boolean keepUniqueIfSorted) {
        if (level == num.length) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
            ArrayList<Integer> permut = new ArrayList<Integer>();
            for (int n : num) permut.add(n);
            result.add(permut);
            return result;
        }

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        Set<Integer> used = new HashSet<Integer>();
        for (int i = level; i < num.length; i++) {
            if (!keepUniqueIfSorted && used.contains(num[i])) continue;
            used.add(num[i]);
            exch(num, level, i);
            result.addAll(permuteBacktracking(num, level + 1, keepUniqueIfSorted));
            exch(num, level, i);
        }

        return result;

    }

    private void exch(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * <a href="http://en.wikipedia.org/wiki/Heap%27s_algorithm">Heap's algorithm</a>
     * Suppose we have a sequence of different characters with a length of N.
     * Heap found that we can simply interchange the positions of two elements to get a new permutation output.
     * Let us describe it in a recursive way. If we have got (N - 1)! permutation outputs, fixing the last element.
     * Then if N is odd, we can switch the first element and the last one, while N is even we can switch the ith (i
     * is the step number of the cycle, and now it is 1) element and the last one, then we will continue outputting
     * the (N - 1)! permutation outputs and switching step for another N − 1 times(N times int total).
     * The following pseudocode outputs permutations of a data array of length N.
     * <p/>
     * procedure generate(N : integer, data : array of any):
     * if N = 1 then
     * output(data)
     * else
     * for c := 1; c <= N; c += 1 do
     * generate(N - 1, data)
     * swap(data[if N is odd then 1 else c], data[N])
     */
    private ArrayList<ArrayList<Integer>> permuteHeap(int[] num, int level) {
        if (level == num.length) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
            ArrayList<Integer> permut = new ArrayList<Integer>();
            for (int n : num) permut.add(n);
            result.add(permut);
            return result;
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i <= level; i++) {
            result.addAll(permuteHeap(num, level + 1));
            exch(num, level, level % 2 == 0 ? i : 0);
        }

        return result;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/rotate-image/">Rotate Image</a>
     * You are given an n x n 2D matrix representing an image.
     * <p/>
     * Rotate the image by 90 degrees (clockwise).
     * <p/>
     * Follow up:
     * Could you do this in-place?
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        if (n < 2) return;

        for (int layer = 0; layer < n / 2; layer++) {
            int end = n - layer - 1;
            for (int i = 0; i < end - layer; i++) {
                int tmp = matrix[layer][layer + i];
                matrix[layer][layer + i] = matrix[end - i][layer];
                matrix[end - i][layer] = matrix[end][end - i];
                matrix[end][end - i] = matrix[layer + i][end];
                matrix[layer + i][end] = tmp;
            }
        }
    }

    /**
     * <a href="http://oj.leetcode.com/problems/anagrams/">Anagrams</a>
     * Given an array of strings, return all groups of strings that are anagrams.
     * <p/>
     * Note: All inputs will be in lower-case.
     */
    public ArrayList<String> anagrams(String[] strs) {
        Map<Map<Character, Integer>, Integer> frequencyMap = new HashMap<Map<Character, Integer>, Integer>();
        Map<Map<Character, Integer>, String> frequencyToLastString = new HashMap<Map<Character, Integer>, String>();

        ArrayList<String> anagrams = new ArrayList<String>();
        for (String str : strs) {
            Map<Character, Integer> freq = new HashMap<Character, Integer>();
            for (char ch : str.toCharArray()) {
                if (!freq.containsKey(ch))
                    freq.put(ch, 1);
                else freq.put(ch, freq.get(ch) + 1);
            }

            if (!frequencyMap.containsKey(freq))
                frequencyMap.put(freq, 1);
            else {
                if (frequencyMap.get(freq) == 1) {
                    anagrams.add(frequencyToLastString.get(freq));
                }
                anagrams.add(str);
                frequencyMap.put(freq, frequencyMap.get(freq) + 1);
            }

            frequencyToLastString.put(freq, str);

        }

        return anagrams;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/powx-n/">Pow(x, n)</a>
     * Implement pow(x, n).
     */
    public double pow(double x, int n) {
        boolean isExponentNegative = n < 0;
//        double result = powRecurse(x, Math.abs(n));
        double result = powBits(x, Math.abs(n));

        return isExponentNegative ? 1 / result : result;

    }

    private double powRecurse(double x, int n) {
        if (n == 0) return 1;
        if (n == 1) return x;

        double n2pow = powRecurse(x, n / 2);
        return n % 2 == 0 ? n2pow * n2pow : x * n2pow * n2pow;
    }

    /**
     * <a href="http://discuss.leetcode.com/questions/228/powx-n">Approach</a>
     */
    private double powBits(double x, int n) {
        if (n == 0) return 1;
        if (n == 1) return x;

        double result = 1;
        while (n > 0) {
            if ((n & 1) > 0) {
                result *= x;
            }
            x *= x;
            n /= 2;
        }

        return result;
    }


    /**
     * <a href="http://oj.leetcode.com/problems/n-queens/">N-Queens</a>
     * The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each other.
     * <p/>
     * Given an integer n, return all distinct solutions to the n-queens puzzle.
     * <p/>
     * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both
     * indicate a queen and an empty space respectively.
     * <p/>
     * For example,
     * There exist two distinct solutions to the 4-queens puzzle:
     * <p/>
     * [
     * [".Q..",  // Solution 1
     * "...Q",
     * "Q...",
     * "..Q."],
     * <p/>
     * ["..Q.",  // Solution 2
     * "Q...",
     * "...Q",
     * ".Q.."]
     * ]
     */
    public ArrayList<String[]> solveNQueens(int n) {
        ArrayList<String[]> solutions = new ArrayList<String[]>();
        solveQueens(n, new boolean[n][n], new boolean[n], new boolean[n], new boolean[n * 2], new boolean[n * 2], 0, solutions);
        return solutions;
    }

    private void solveQueens(int n, boolean[][] board, boolean[] y, boolean[] x, boolean[] diag1, boolean[] diag2,
                             int queens, ArrayList<String[]> solutions) {
        if (queens == n) {
            String[] solution = new String[n];
            for (int i = 0; i < board.length; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < board[i].length; j++) {
                    sb.append(board[i][j] ? 'Q' : '.');
                }
                solution[i] = sb.toString();
            }
            solutions.add(solution);
            return;
        }
        int i = queens;
        for (int j = 0; j < n; j++) {
            if (!y[i] && !x[j] && !diag1[i + j] && !diag2[i - j + n]) {
                board[i][j] = true;
                y[i] = true;
                x[j] = true;
                diag1[i + j] = true;
                diag2[i - j + n] = true;

                solveQueens(n, board, y, x, diag1, diag2, queens + 1, solutions);

                board[i][j] = false;
                y[i] = false;
                x[j] = false;
                diag1[i + j] = false;
                diag2[i - j + n] = false;
            }
        }
    }

    /**
     * <a href="http://oj.leetcode.com/problems/n-queens-ii/">N-Queens II</a>
     * Follow up for N-Queens problem.
     * <p/>
     * Now, instead outputting board configurations, return the total number of distinct solutions.
     */
    public int totalNQueens(int n) {
        return solveQueensNum(n, new boolean[n][n], new boolean[n], new boolean[n], new boolean[n * 2], new boolean[n * 2], 0);
    }

    private int solveQueensNum(int n, boolean[][] board, boolean[] y, boolean[] x, boolean[] diag1, boolean[] diag2,
                               int queens) {
        if (queens == n) {
            return 1;
        }
        int i = queens;
        int total = 0;
        for (int j = 0; j < n; j++) {
            if (!y[i] && !x[j] && !diag1[i + j] && !diag2[i - j + n]) {
                board[i][j] = true;
                y[i] = true;
                x[j] = true;
                diag1[i + j] = true;
                diag2[i - j + n] = true;

                total += solveQueensNum(n, board, y, x, diag1, diag2, queens + 1);

                board[i][j] = false;
                y[i] = false;
                x[j] = false;
                diag1[i + j] = false;
                diag2[i - j + n] = false;
            }
        }
        return total;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/maximum-subarray/">Maximum Subarray</a>
     * Find the contiguous subarray within an array (containing at least one number) which has the largest sum.
     * <p/>
     * For example, given the array [−2,1,−3,4,−1,2,1,−5,4],
     * the contiguous subarray [4,−1,2,1] has the largest sum = 6.
     * <p/>
     * More practice:
     * <p/>
     * If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
     */
    public int maxSubArray(int[] A) {
        int currentSum = 0;
        int max = Integer.MIN_VALUE;

        for (int a : A) {
            currentSum += a;
            max = Math.max(max, currentSum);
            if (currentSum < 0)
                currentSum = 0;
        }

        return max;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/spiral-matrix/">Spiral Matrix</a>
     * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
     * <p/>
     * For example,
     * Given the following matrix:
     * <p/>
     * [
     * [ 1, 2, 3 ],
     * [ 4, 5, 6 ],
     * [ 7, 8, 9 ]
     * ]
     * <p/>
     * You should return [1,2,3,6,9,8,7,4,5].
     */
    public ArrayList<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length;
        if (m == 0) return new ArrayList<Integer>();
        int n = matrix[0].length;
        int maxLayer = (Math.min(m, n) + 1) / 2;

        ArrayList<Integer> spiral = new ArrayList<Integer>();
        for (int layer = 0; layer < maxLayer; layer++) {
            int xMax = n - layer - 1;
            int yMax = m - layer - 1;
            for (int i = layer; i <= xMax; i++)
                spiral.add(matrix[layer][i]);
            for (int i = layer + 1; i <= yMax; i++)
                spiral.add(matrix[i][xMax]);
            if (yMax != layer)
                for (int i = xMax - 1; i >= layer; i--)
                    spiral.add(matrix[yMax][i]);
            if (xMax != layer)
                for (int i = yMax - 1; i > layer; i--)
                    spiral.add(matrix[i][layer]);
        }

        return spiral;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/merge-intervals/">Merge Intervals</a>
     * Given a collection of intervals, merge all overlapping intervals.
     * <p/>
     * For example,
     * Given [1,3],[2,6],[8,10],[15,18],
     * return [1,6],[8,10],[15,18].
     * <p/>
     * Solution: somewhat a Kadane algorithm
     */
    public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        if (intervals == null || intervals.isEmpty()) return intervals;
        TreeSet<Interval> intervalSet = new TreeSet<Interval>(new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                return o1.start == o2.start ? Integer.compare(o1.end, o2.end) : Integer.compare(o1.start, o2.start);
            }
        });

        for (Interval interval : intervals) {
            intervalSet.add(interval);
        }

        ArrayList<Interval> merged = new ArrayList<Interval>();

        int startInterval = intervalSet.iterator().next().start;
        int maxEnd = intervalSet.iterator().next().end;

        for (Interval interval : intervalSet) {
            if (interval.start > maxEnd) {
                merged.add(new Interval(startInterval, maxEnd));
                startInterval = interval.start;
            }
            maxEnd = Math.max(maxEnd, interval.end);
        }
        merged.add(new Interval(startInterval, maxEnd));
        return merged;
    }

    static class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Interval interval = (Interval) o;

            if (end != interval.end) return false;
            if (start != interval.start) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = start;
            result = 31 * result + end;
            return result;
        }

        @Override
        public String toString() {
            return String.format("[%d, %d]", start, end);
        }
    }

    /**
     * <a href="http://oj.leetcode.com/problems/insert-interval/">Insert Interval</a>
     * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
     * <p/>
     * You may assume that the intervals were initially sorted according to their start times.
     * <p/>
     * Example 1:
     * Given intervals [1,3],[6,9], insert and merge [2,5] in as [1,5],[6,9].
     * <p/>
     * Example 2:
     * Given [1,2],[3,5],[6,7],[8,10],[12,16], insert and merge [4,9] in as [1,2],[3,10],[12,16].
     * <p/>
     * This is because the new interval [4,9] overlaps with [3,5],[6,7],[8,10].
     */
    public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
        if (intervals.isEmpty()) {
            return new ArrayList<Interval>(Arrays.asList(newInterval));
        }

        int prev = Integer.MIN_VALUE;
        for (int i = 0; i < intervals.size(); i++) {
            if (prev <= newInterval.start && intervals.get(i).start >= newInterval.start) {
                intervals.add(i, newInterval);
                i++;
            }
            prev = intervals.get(i).start;
        }
        if (newInterval.start > prev) intervals.add(newInterval);

        ArrayList<Interval> inserted = new ArrayList<Interval>();

        int start = intervals.get(0).start;
        int maxEnd = intervals.get(0).end;

        for (Interval interval : intervals) {

            if (interval.start > maxEnd) {
                inserted.add(new Interval(start, maxEnd));
                start = interval.start;
            }
            maxEnd = Math.max(maxEnd, interval.end);
        }
        inserted.add(new Interval(start, maxEnd));

        return inserted;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/length-of-last-word/">Length of Last Word</a>
     * Given a string s consists of upper/lower-case alphabets and empty space characters ' ',
     * return the length of last word in the string.
     * <p/>
     * If the last word does not exist, return 0.
     * <p/>
     * Note: A word is defined as a character sequence consists of non-space characters only.
     * <p/>
     * For example,
     * Given s = "Hello World",
     * return 5.
     */
    public int lengthOfLastWord(String s) {
        int wordStart = s.length() - 1;
        int maxLen = 0;

        boolean wordStarted = false;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                if (wordStarted) {
                    maxLen = wordStart - i;
                    wordStarted = false;
                    break;
                }
                wordStart = i - 1;
            } else wordStarted = true;
        }
        if (wordStarted) return wordStart + 1;
        return maxLen;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/spiral-matrix-ii/">Spiral Matrix II</a>
     * Given an integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
     * <p/>
     * For example,
     * Given n = 3,
     * You should return the following matrix:
     * <p/>
     * [
     * [ 1, 2, 3 ],
     * [ 8, 9, 4 ],
     * [ 7, 6, 5 ]
     * ]
     */
    public int[][] generateMatrix(int n) {
        if (n == 0) return new int[0][0];
        int[][] m = new int[n][n];
        int c = 1;
        for (int layer = 0; layer < (n + 1) / 2; layer++) {
            int end = n - layer - 1;
            for (int i = layer; i <= end; i++) m[layer][i] = c++;
            for (int i = layer + 1; i <= end; i++) m[i][end] = c++;
            for (int i = end - 1; i >= layer; i--) m[end][i] = c++;
            for (int i = end - 1; i > layer; i--) m[i][layer] = c++;
        }
        return m;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/permutation-sequence">Permutation Sequence</a>
     * The set [1,2,3,…,n] contains a total of n! unique permutations.
     * <p/>
     * By listing and labeling all of the permutations in order,
     * We get the following sequence (ie, for n = 3):
     * <p/>
     * "123"
     * "132"
     * "213"
     * "231"
     * "312"
     * "321"
     * <p/>
     * Given n and k, return the kth permutation sequence.
     * <p/>
     * Note: Given n will be between 1 and 9 inclusive.
     * <p/>
     * The solution is based on factoradic numbers (http://en.wikipedia.org/wiki/Factorial_number_system)
     */
    public String getPermutation(int n, int k) {
        int f = 14;
        int[] factorials = new int[f];
        factorials[0] = 0;
        factorials[1] = 1;

        for (int i = 2; i < f; i++) {
            factorials[i] = i * factorials[i - 1];
        }

        k--;
        int[] factoradic = new int[f];
        for (int i = f - 1; i > 0; i--) {
            factoradic[i] = (k / factorials[i]);
            if (k >= factorials[i])
                k = k % factorials[i];
        }

        factoradic[0] = 0;
        List<Character> chars = new ArrayList<Character>();
        for (char i = 1; i <= n; i++) {
            chars.add((char) ('0' + i));
        }

        StringBuilder sb = new StringBuilder();
        for (int i = n - 1; i >= 0; i--) {
            sb.append(chars.get(factoradic[i]));
            chars.remove(factoradic[i]);
        }
        return sb.toString();
    }

    /**
     * <a href="http://oj.leetcode.com/problems/rotate-list/">Rotate List</a>
     * Given a list, rotate the list to the right by k places, where k is non-negative.
     * <p/>
     * For example:
     * Given 1->2->3->4->5->NULL and k = 2,
     * return 4->5->1->2->3->NULL.
     */
    public ListNode rotateRight(ListNode head, int n) {
        if (head == null || head.next == null) return head;
        if (n == 0) return head;
        ListNode slowPointer = head;
        int countDown = n;
        ListNode fastPointer = head;
        int listLength = 0;
        while (fastPointer.next != null) {
            if (countDown == 0) slowPointer = slowPointer.next;
            else countDown--;
            fastPointer = fastPointer.next;
            listLength++;
        }

        listLength++;
        if (countDown > 0) {
            n = n % listLength;
            if (n == 0) return head;
            countDown = n;
            slowPointer = head;
            fastPointer = head;
            while (fastPointer.next != null) {
                if (countDown == 0) slowPointer = slowPointer.next;
                else countDown--;
                fastPointer = fastPointer.next;
            }

        }

        ListNode newHead = slowPointer.next;
        slowPointer.next = null;
        fastPointer.next = head;
        return newHead;
    }

    /**
     * <a href="http://oj.leetcode.com/problems/unique-paths/">Unique Paths</a>
     * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
     * <p/>
     * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
     * <p/>
     * How many possible unique paths are there?
     * <p/>
     * Note: m and n will be at most 100.
     */
    public int uniquePaths(int m, int n) {
        if (m == 0 && n == 0) return 1;
//        return uniquePathsBacktrack(m, n);
//        return uniquePathsMemoization(m-1, n-1, new int[m][n]);
        return uniquePathsDP(m, n);
    }

    private int uniquePathsBacktrack(int m, int n) {
        if (n == 0 && m == 0) return 1;
        return (m > 0 ? uniquePaths(m - 1, n) : 0) + (n > 0 ? uniquePaths(m, n - 1) : 0);
    }

    private int uniquePathsMemoization(int m, int n, int[][] memo) {
        if (n == 0 && m == 0) return 1;
        if (m < 0 || n < 0) return 0;
        if (memo[m][n] == 0)
            memo[m][n] = uniquePathsMemoization(m - 1, n, memo) + uniquePathsMemoization(m, n - 1, memo);

        return memo[m][n];
    }

    private int uniquePathsDP(int m, int n) {
        int[][] board = new int[m + 1][n + 1];
        board[1][0] = 1;
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++) {
                board[i][j] = board[i - 1][j] + board[i][j - 1];
            }
        return board[m][n];
    }

    /**
     * <a href="http://oj.leetcode.com/problems/unique-paths-ii/">Unique Paths II</a>
     * Follow up for "Unique Paths":
     * <p/>
     * Now consider if some obstacles are added to the grids. How many unique paths would there be?
     * <p/>
     * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
     * <p/>
     * For example,
     * <p/>
     * There is one obstacle in the middle of a 3x3 grid as illustrated below.
     * <p/>
     * [
     * [0,0,0],
     * [0,1,0],
     * [0,0,0]
     * ]
     * <p/>
     * The total number of unique paths is 2.
     * <p/>
     * Note: m and n will be at most 100.
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        if (obstacleGrid[m - 1][n - 1] == 1 || obstacleGrid[0][0] == 1) return 0;
        if (n == 0 && m == 0) return 1;
        return uniquePathsMemoizationObstacles(m - 1, n - 1, new int[m][n], obstacleGrid);
    }

    private int uniquePathsMemoizationObstacles(int m, int n, int[][] memo, int[][] obstacleGrid) {
        if (n == 0 && m == 0) return 1;
        if (m < 0 || n < 0 || obstacleGrid[m][n] == 1) return 0;
        if (memo[m][n] == 0)
            memo[m][n] = uniquePathsMemoizationObstacles(m - 1, n, memo, obstacleGrid)
                    + uniquePathsMemoizationObstacles(m, n - 1, memo, obstacleGrid);

        return memo[m][n];
    }
}
