package leetcode.oj;

import java.util.ArrayList;
import java.util.Arrays;

import static leetcode.oj.Solution.*;
import static leetcode.oj.TestUtils.*;

/**
 * Author: Sobercheg on 12/24/13.
 * Unit tests for LeetCode OJ solutions.
 */
public class SolutionTest {
    private final Solution solution = new Solution();

    public static void main(String[] args) {
        SolutionTest solutionTest = new SolutionTest();
        solutionTest.testTwoSum();
        solutionTest.testEvalRPN();
        solutionTest.testPostorderTraversal();
        solutionTest.testPreorderTraversal();
        solutionTest.testLengthOfLongestSubstring();
//        solutionTest.testFindMedianSortedArrays();
        solutionTest.testAddTwoNumbers();
        solutionTest.testLongestPalindrome();
        solutionTest.testZigzagConvert();
        solutionTest.testReverse();
        solutionTest.testAtoi();
        solutionTest.testIsPalindrome();
        solutionTest.testIsMatch();
        solutionTest.testMaxArea();
        solutionTest.testIntToRoman();
        solutionTest.testRomanToInt();
        solutionTest.testLongestCommonPrefix();
        solutionTest.testThreeSum();
        solutionTest.testThreeSumClosest();
        solutionTest.testFourSum();
        solutionTest.testKSum();
        solutionTest.testLetterCombinations();
        solutionTest.testRemoveNthFromEnd();
        solutionTest.testIsValid();
        solutionTest.testGenerateParenthesis();
        solutionTest.testMaxPoints();
        solutionTest.testMaxProfitI();
        solutionTest.testMaxProfitII();
        solutionTest.testMaxProfitIII();
        solutionTest.testMergeKLists();
        solutionTest.testSwapPairs();
        solutionTest.testReverseKGroup();
        solutionTest.testRemoveDuplicates();
        solutionTest.testRemoveElement();
        solutionTest.testInsertionSortList();
        solutionTest.testStrStr();
        solutionTest.testDivide();
        solutionTest.testFindSubstring();
        solutionTest.testNextPermutation();
        solutionTest.testLongestValidParentheses();
        solutionTest.testSearch();
        solutionTest.testSearchRange();
        solutionTest.testSearchInsert();
        solutionTest.testIsValidSudoku();
        solutionTest.testSolveSudoku();
        solutionTest.testCountAndSay();
        solutionTest.testLRUCache();
        solutionTest.testSortList();
        solutionTest.testCombinationSum();
        solutionTest.testCombinationSum2();
        solutionTest.testFirstMissingPositive();
        solutionTest.testTrap();
        solutionTest.testMultiply();
        solutionTest.testIsWildcardMatch();
        solutionTest.testCanJump();
        solutionTest.testJump();
        solutionTest.testPermute();
        solutionTest.testPermuteUnique();
        solutionTest.testRotate();
        solutionTest.testAnagrams();
        solutionTest.testPow();
        solutionTest.testSolveNQueens();
        solutionTest.testTotalNQueens();
        solutionTest.testMaxSubArray();
        solutionTest.testSpiralOrder();
        solutionTest.testMerge();
        solutionTest.testInsert();
        solutionTest.testLengthOfLastWord();
        solutionTest.testGenerateMatrix();
        solutionTest.testGetPermutation();
        solutionTest.testRotateRight();
        solutionTest.testUniquePaths();
        solutionTest.testUniquePathsWithObstacles();
    }

    public void testTwoSum() {
        assertEquals(new int[]{2, 3}, solution.twoSum(new int[]{3, 1, 5, 8}, 6));
        assertEquals(new int[]{-1, -1}, solution.twoSum(new int[]{3, 1, 5, 8}, 7));
    }

    public void testEvalRPN() {
        assertEquals(9, solution.evalRPN(new String[]{"2", "1", "+", "3", "*"}));
        assertEquals(6, solution.evalRPN(new String[]{"4", "13", "5", "/", "+"}));
    }

    public void testPostorderTraversal() {
        assertEquals(Arrays.asList(), solution.postorderTraversal(null));
        assertEquals(Arrays.asList(1), solution.postorderTraversal(new TreeNode(1)));

        TreeNode root = new TreeNode(1,
                null, new TreeNode(2,
                new TreeNode(3), null));
        assertEquals(Arrays.asList(3, 2, 1), solution.postorderTraversal(root));

    }

    public void testPreorderTraversal() {
        assertEquals(Arrays.asList(), solution.postorderTraversal(null));
        assertEquals(Arrays.asList(1), solution.postorderTraversal(new TreeNode(1)));

        TreeNode root = new TreeNode(1,
                null, new TreeNode(2,
                new TreeNode(3), null));
        assertEquals(Arrays.asList(1, 2, 3), solution.preorderTraversal(root));

        TreeNode root2 = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(3,
                                new TreeNode(4), new TreeNode(5)),
                        new TreeNode(6)), new TreeNode(7));

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), solution.preorderTraversal(root2));

    }

    public void testLengthOfLongestSubstring() {
        assertEquals(0, solution.lengthOfLongestSubstring(null));
        assertEquals(0, solution.lengthOfLongestSubstring(""));
        assertEquals(3, solution.lengthOfLongestSubstring("abc"));
        assertEquals(3, solution.lengthOfLongestSubstring("abca"));
        assertEquals(3, solution.lengthOfLongestSubstring("abcabcbb"));
        assertEquals(12, solution.lengthOfLongestSubstring("wlrbbmqbhcdarzowkkyhiddqscdxrjmowfrxsjybldbefsarcbynecdyggxxpklorellnmpapqfwkhopkmco"));
    }

    // TODO: fix solution
    public void testFindMedianSortedArrays() {
        assertEquals(60.0, solution.findMedianSortedArrays(new int[]{1, 2, 100, 101}, new int[]{50, 60, 70}));
        assertEquals(26.0, solution.findMedianSortedArrays(new int[]{1, 2}, new int[]{50, 60}));
        assertEquals(3.0, solution.findMedianSortedArrays(new int[]{1, 3, 5}, new int[]{2, 4}));
        assertEquals(3.0, solution.findMedianSortedArrays(new int[]{1, 3, 5}, new int[]{2, 4}));
        assertEquals(1.0, solution.findMedianSortedArrays(new int[]{1}, new int[]{}));
        assertEquals(2.5, solution.findMedianSortedArrays(new int[]{2, 3}, new int[]{}));
        assertEquals(1.5, solution.findMedianSortedArrays(new int[]{1, 2}, new int[]{1, 2}));
        assertEquals(1.0, solution.findMedianSortedArrays(new int[]{1, 1}, new int[]{1, 2}));
        assertEquals(7.0, solution.findMedianSortedArrays(new int[]{8, 9}, new int[]{1, 2, 3, 4, 5, 6, 7, 10}));
        assertEquals(2.0, solution.findMedianSortedArrays(new int[]{1, 2, 2}, new int[]{1, 2, 3}));
    }

    public void testAddTwoNumbers() {
        ListNode val1 = ListNode.build(2, 4, 3);
        ListNode val2 = ListNode.build(5, 6, 4);
        ListNode actualSum = solution.addTwoNumbers(val1, val2);
        ListNode expectedSum = ListNode.build(7, 0, 8);
        assertEquals(expectedSum, actualSum);

        val1 = ListNode.build(1);
        val2 = ListNode.build(9, 9);
        actualSum = solution.addTwoNumbers(val1, val2);
        expectedSum = ListNode.build(0, 0, 1);
        assertEquals(expectedSum, actualSum);

    }

    public void testLongestPalindrome() {
        assertEquals("aba", solution.longestPalindrome("cabaq"));
        assertEquals("abba", solution.longestPalindrome("abba"));
//      unspecified behavior
//      assertEquals("d", solution.longestPalindrome("abcd"));
        assertEquals("bb", solution.longestPalindrome("bb"));
        assertEquals("aba", solution.longestPalindrome("abaa"));
        assertEquals("aaabaaa", solution.longestPalindrome("aaabaaaa"));
    }

    public void testZigzagConvert() {
        assertEquals("0481357926", solution.convert("0123456789", 3));
        assertEquals("PAHNAPLSIIGYIR", solution.convert("PAYPALISHIRING", 3));
        assertEquals("AB", solution.convert("AB", 3));
        assertEquals("ABC", solution.convert("ACB", 2));
        assertEquals("ABCED", solution.convert("ABCDE", 4));
    }

    public void testReverse() {
        assertEquals(123, solution.reverse(321));
        assertEquals(-321, solution.reverse(-123));
        assertEquals(1, solution.reverse(1));
    }

    public void testAtoi() {
        assertEquals(123, solution.atoi("123"));
        assertEquals(123, solution.atoi("  123"));
        assertEquals(1, solution.atoi("  1 2 3 "));
        assertEquals(123, solution.atoi("123www"));
        assertEquals(0, solution.atoi(" - 123www"));
        assertEquals(0, solution.atoi(" - 123.5"));
        assertEquals(0, solution.atoi(".5"));
        assertEquals(0, solution.atoi("w"));
        assertEquals(0, solution.atoi("w5"));
        assertEquals(0, solution.atoi("--1"));
        assertEquals(1, solution.atoi("+1"));
        assertEquals(0, solution.atoi("++1"));
        assertEquals(0, solution.atoi(" +0 123"));
        assertEquals(2147483647, solution.atoi("2147483648"));
        assertEquals(2147483647, solution.atoi(" 10522545459"));
    }

    public void testIsPalindrome() {
        assertEquals(true, solution.isPalindrome(1));
        assertEquals(true, solution.isPalindrome(11));
        assertEquals(true, solution.isPalindrome(121));
        assertEquals(true, solution.isPalindrome(1221));
        assertEquals(false, solution.isPalindrome(1223));
        assertEquals(false, solution.isPalindrome(122322));
        assertEquals(true, solution.isPalindrome(9898989));
    }

    public void testIsMatch() {
        assertEquals(false, solution.isMatch("bbab", "b*a*"));
        assertEquals(true, solution.isMatch("", "a*"));
        assertEquals(false, solution.isMatch("aa", "a"));
        assertEquals(true, solution.isMatch("a", "a*a"));
        assertEquals(true, solution.isMatch("bac", "c*.*b*aa*.a*"));
        assertEquals(true, solution.isMatch("bbbba", ".*a*a"));
        assertEquals(true, solution.isMatch("aa", "aa"));
        assertEquals(false, solution.isMatch("aaa", "aa"));
        assertEquals(true, solution.isMatch("aa", "a*"));
        assertEquals(true, solution.isMatch("aa", ".*"));
        assertEquals(true, solution.isMatch("ab", ".*"));
        assertEquals(true, solution.isMatch("aab", "c*a*b"));
        assertEquals(true, solution.isMatch("aab", "a.b"));
        assertEquals(false, solution.isMatch("aacacbcbabbaaaccb", "a*b.c*c*aa*c*b*c*c"));
        assertEquals(true, solution.isMatch("baccbbcbcacacbbc", "c*.*b*c*ba*b*b*.a*"));
        assertEquals(false, solution.isMatch("aaaaaaaaaaaaab", "a*a*a*a*a*a*a*a*a*a*c"));
        assertEquals(true, solution.isMatch("cbaacacaaccbaabcb", "c*b*b*.*ac*.*bc*a*"));
    }

    public void testMaxArea() {
        assertEquals(2, solution.maxArea(new int[]{1, 2, 3}));
        assertEquals(2, solution.maxArea(new int[]{3, 2, 1}));
        assertEquals(0, solution.maxArea(new int[]{1}));
        assertEquals(4, solution.maxArea(new int[]{2, 1, 2}));
        assertEquals(2, solution.maxArea(new int[]{1, 2, 1}));
        assertEquals(4, solution.maxArea(new int[]{1, 2, 4, 3}));
    }

    public void testIntToRoman() {
        assertEquals("MCMLIV", solution.intToRoman(1954));
    }

    public void testRomanToInt() {
        assertEquals(1954, solution.romanToInt("MCMLIV"));
    }

    public void testLongestCommonPrefix() {
        assertEquals("a", solution.longestCommonPrefix(new String[]{"aa", "a"}));
        assertEquals("ab", solution.longestCommonPrefix(new String[]{"abc", "abde"}));
        assertEquals("ab", solution.longestCommonPrefix(new String[]{"ab", "abde"}));
        assertEquals("", solution.longestCommonPrefix(new String[]{"ab", "bbde"}));
    }

    public void testThreeSum() {
        assertEquals(arrayListOf(arrayListOf(-1, 0, 1), arrayListOf(-1, -1, 2)), solution.threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        assertEquals(arrayListOf(arrayListOf(0, 0, 0)), solution.threeSum(new int[]{0, 0, 0}));
        assertEquals(arrayListOf(arrayListOf(-1, 0, 1)), solution.threeSum(new int[]{1, -1, -1, 0}));
        assertEquals(arrayListOf(arrayListOf(-2, 1, 1), arrayListOf(-2, 0, 2)), solution.threeSum(new int[]{-2, 0, 1, 1, 2}));
    }

    public void testThreeSumClosest() {
        assertEquals(2, solution.threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
        assertEquals(3, solution.threeSumClosest(new int[]{1, 1, 1, 1}, 3));
        assertEquals(1, solution.threeSumClosest(new int[]{1, 1, -1, -1, 3}, 1));
    }

    public void testFourSum() {
        testKSum();
    }

    public void testKSum() {
        assertEquals(arrayListOf(arrayListOf(-1, 0, 1), arrayListOf(-1, -1, 2)), solution.kSum(new int[]{-1, 0, 1, 2, -1, -4}, 0, 3));
        assertEquals(arrayListOf(arrayListOf(0, 0, 0)), solution.kSum(new int[]{0, 0, 0}, 0, 3));
        assertEquals(arrayListOf(arrayListOf(-1, 0, 1)), solution.kSum(new int[]{1, -1, -1, 0}, 0, 3));
        assertEquals(arrayListOf(arrayListOf(-2, 1, 1), arrayListOf(-2, 0, 2)), solution.kSum(new int[]{-2, 0, 1, 1, 2}, 0, 3));
        assertEquals(arrayListOf(arrayListOf(-1, 0, 0, 1), arrayListOf(-2, -1, 1, 2), arrayListOf(-2, 0, 0, 2)),
                solution.fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
        assertEquals(arrayListOf(arrayListOf(-3, -1, 0, 4)), solution.kSum(new int[]{-3, -1, 0, 2, 4, 5}, 0, 4));
        assertEquals(arrayListOf(arrayListOf(-4, 0, 1, 2), arrayListOf(-1, -1, 0, 1)), solution.kSum(new int[]{-1, 0, 1, 2, -1, -4}, -1, 4));
    }

    public void testLetterCombinations() {
        assertEquals(arrayListOf("ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"), solution.letterCombinations("23"));
        assertEquals(arrayListOf(""), solution.letterCombinations(""));
    }

    private void testRemoveNthFromEnd() {
        // remove head
        ListNode head = ListNode.build(1, 2);
        ListNode removed = solution.removeNthFromEnd(head, 2);
        ListNode expected = ListNode.build(2);
        assertEquals(expected, removed);

        // one element, n = 1
        head = ListNode.build(1);
        removed = solution.removeNthFromEnd(head, 1);
        assertEquals(null, removed);

        // normal list
        head = ListNode.build(1, 2, 3, 4, 5);
        removed = solution.removeNthFromEnd(head, 2);
        expected = ListNode.build(1, 2, 3, 5);
        assertEquals(expected, removed);
    }

    public void testIsValid() {
        assertEquals(false, solution.isValid("["));
        assertEquals(false, solution.isValid("]"));
        assertEquals(false, solution.isValid("()]"));
        assertEquals(false, solution.isValid("[()"));
        assertEquals(true, solution.isValid("()"));
        assertEquals(true, solution.isValid("({})[]"));
    }

    public void testGenerateParenthesis() {
        assertEquals(arrayListOf(""), solution.generateParenthesis(0));
        assertEquals(arrayListOf("()"), solution.generateParenthesis(1));
        assertEquals(arrayListOf("(())", "()()"), solution.generateParenthesis(2));
        assertEquals(arrayListOf("((()))", "(()())", "(())()", "()(())", "()()()"), solution.generateParenthesis(3));
    }

    public void testMaxPoints() {
        assertEquals(3, solution.maxPoints(new Point[]{new Point(0, 0), new Point(0, 4), new Point(2, 3), new Point(4, 2)}));
        assertEquals(4, solution.maxPoints(new Point[]{new Point(0, 0), new Point(0, 0), new Point(2, 2), new Point(2, 2)}));
        assertEquals(3, solution.maxPoints(new Point[]{new Point(0, 0), new Point(1, 1), new Point(0, 0)}));
        assertEquals(3, solution.maxPoints(new Point[]{new Point(1, 1), new Point(1, 1), new Point(1, 1)}));
    }

    public void testMaxProfitI() {
        assertEquals(6, solution.maxProfitI(new int[]{2, 1, 3, 2, 7, 0, 1}));
    }

    public void testMaxProfitII() {
        assertEquals(1, solution.maxProfitII(new int[]{1, 2}));
    }

    public void testMaxProfitIII() {
        assertEquals(7, solution.maxProfitIII(new int[]{2, 1, 3, 2, 7, 0, 1}));
        assertEquals(0, solution.maxProfitIII(new int[]{10, 9}));
        assertEquals(1, solution.maxProfitIII(new int[]{1, 2}));
        assertEquals(7, solution.maxProfitIII(new int[]{6, 1, 3, 2, 4, 7}));
    }

    public void testMergeKLists() {
        // just one element
        ListNode merged = solution.mergeKLists(arrayListOf(ListNode.build(1)));
        assertEquals(1, merged.val);
        assertEquals(null, merged.next);

        ListNode list1 = ListNode.build(1, 4, 5);
        ListNode list2 = ListNode.build(1, 3, 8);
        merged = solution.mergeKLists(arrayListOf(list1, list2));
        ListNode expected = ListNode.build(1, 1, 3, 4, 5, 8);
        assertEquals(expected, merged);

    }

    public void testSwapPairs() {
        // even number of nodes
        ListNode swapped = solution.swapPairs(ListNode.build(1, 2, 3, 4));
        ListNode expected = ListNode.build(2, 1, 4, 3);
        assertEquals(expected, swapped);

        // odd number of nodes
        swapped = solution.swapPairs(ListNode.build(1, 2, 3));
        expected = ListNode.build(2, 1, 3);
        assertEquals(expected, swapped);

        // one node
        swapped = solution.swapPairs(ListNode.build(1));
        expected = ListNode.build(1);
        assertEquals(expected, swapped);

        // no nodes
        swapped = solution.swapPairs(null);
        assertEquals(null, swapped);
    }

    public void testReverseKGroup() {
        // k=1
        ListNode reversed = solution.reverseKGroup(ListNode.build(1, 2, 3, 4, 5), 1);
        ListNode expected = ListNode.build(1, 2, 3, 4, 5);
        assertEquals(expected, reversed);

        // k=2, 2 groups
        reversed = solution.reverseKGroup(ListNode.build(1, 2, 3, 4, 5), 2);
        expected = ListNode.build(2, 1, 4, 3, 5);
        assertEquals(expected, reversed);

        // k=2, 2 groups, no trailing nodes
        reversed = solution.reverseKGroup(ListNode.build(1, 2, 3, 4, 5, 6), 2);
        expected = ListNode.build(2, 1, 4, 3, 6, 5);
        assertEquals(expected, reversed);

        // k=3, 1 group
        reversed = solution.reverseKGroup(ListNode.build(1, 2, 3, 4, 5), 3);
        expected = ListNode.build(3, 2, 1, 4, 5);
        assertEquals(expected, reversed);

        // k=4, 1 group
        reversed = solution.reverseKGroup(ListNode.build(1, 2, 3, 4, 5, 6), 4);
        expected = ListNode.build(4, 3, 2, 1, 5, 6);
        assertEquals(expected, reversed);

        // k=4, 2 groups
        reversed = solution.reverseKGroup(ListNode.build(1, 2, 3, 4, 5, 6, 7, 8, 9), 4);
        assertEquals(4, reversed.val);
        assertEquals(3, reversed.next.val);
        assertEquals(2, reversed.next.next.val);
        assertEquals(1, reversed.next.next.next.val);
        assertEquals(8, reversed.next.next.next.next.val);
        assertEquals(7, reversed.next.next.next.next.next.val);
        assertEquals(6, reversed.next.next.next.next.next.next.val);
        assertEquals(5, reversed.next.next.next.next.next.next.next.val);
        assertEquals(9, reversed.next.next.next.next.next.next.next.next.val);
        assertEquals(null, reversed.next.next.next.next.next.next.next.next.next);

        // k=30, 0 groups
        reversed = solution.reverseKGroup(ListNode.build(1, 2, 3, 4, 5), 30);
        assertEquals(1, reversed.val);
        assertEquals(2, reversed.next.val);
        assertEquals(3, reversed.next.next.val);
        assertEquals(4, reversed.next.next.next.val);
        assertEquals(5, reversed.next.next.next.next.val);
        assertEquals(null, reversed.next.next.next.next.next);

        // one node
        reversed = solution.reverseKGroup(ListNode.build(1), 1);
        assertEquals(1, reversed.val);

        // no nodes
        reversed = solution.swapPairs(null);
        assertEquals(null, reversed);
    }

    public void testRemoveDuplicates() {
        assertEquals(1, solution.removeDuplicates(new int[]{2}));
        assertEquals(2, solution.removeDuplicates(new int[]{1, 2}));
        assertEquals(1, solution.removeDuplicates(new int[]{1, 1}));
        assertEquals(2, solution.removeDuplicates(new int[]{1, 1, 2}));
        assertEquals(2, solution.removeDuplicates(new int[]{1, 1, 2, 2}));
    }

    public void testRemoveElement() {
        assertEquals(1, solution.removeElement(new int[]{2}, 1));
        assertEquals(0, solution.removeElement(new int[]{1}, 1));
        assertEquals(3, solution.removeElement(new int[]{1, 3, 4, 5, 4}, 4));
    }

    public void testInsertionSortList() {
        ListNode sorted = solution.insertionSortList(ListNode.build(3, 1, 7, 5, 0));
        assertEquals(0, sorted.val);
        assertEquals(1, sorted.next.val);
        assertEquals(3, sorted.next.next.val);
        assertEquals(5, sorted.next.next.next.val);
        assertEquals(7, sorted.next.next.next.next.val);
        assertEquals(null, sorted.next.next.next.next.next);

        sorted = solution.insertionSortList(ListNode.build(1));
        assertEquals(1, sorted.val);
        assertEquals(null, sorted.next);
    }

    public void testStrStr() {
        assertEquals("needle somewhere", solution.strStr("haystack need has a needle somewhere", "needle"));
        assertEquals("needle somewhere", solution.strStr("needle somewhere", ""));
        assertEquals("aaaaaaaaaaaaaaaba", solution.strStr("aaaaaaaaaaaaaaaaaaaaaaaba", "aaaaaaaaaaaaaaab"));
        assertEquals(null, solution.strStr("mississippi", "issipi"));
        assertEquals("a", solution.strStr("a", "a"));
    }

    public void testDivide() {
        assertEquals(5, solution.divide(29, 5));
        assertEquals(5, solution.divide(25, 5));
        assertEquals(4, solution.divide(24, 5));
        assertEquals(24, solution.divide(24, 1));
        assertEquals(0, solution.divide(0, 1));
        assertEquals(-1, solution.divide(1, -1));
        assertEquals(-1, solution.divide(-1, 1));
        assertEquals(1, solution.divide(-1, -1));
        assertEquals(0, solution.divide(-1006986286, -2145851451));
        assertEquals(0, solution.divide(-1010369383, -2147483648));
        assertEquals(-1073741824, solution.divide(-2147483648, 2));
    }

    public void testFindSubstring() {
        assertEquals(arrayListOf(0, 9), solution.findSubstring("barfoothefoobarman", new String[]{"foo", "bar"}));
        assertEquals(arrayListOf(), solution.findSubstring("baarfotothefotobrarman", new String[]{"foo", "bar"}));
        assertEquals(arrayListOf(), solution.findSubstring("abababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababab", new String[]{"ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba"}));
        assertEquals(arrayListOf(), solution.findSubstring("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", new String[]{"a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"}));
        assertEquals(arrayListOf(), solution.findSubstring("abababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababab", new String[]{"ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba", "ab", "ba"}));
        assertEquals(arrayListOf(0), solution.findSubstring("a", new String[]{"a"}));
        assertEquals(arrayListOf(0, 1), solution.findSubstring("aaa", new String[]{"a", "a"}));
        assertEquals(arrayListOf(), solution.findSubstring("aaa", new String[]{"a", "b"}));
        assertEquals(arrayListOf(0, 2, 4), solution.findSubstring("abababab", new String[]{"a", "b", "a"}));
        assertEquals(arrayListOf(1), solution.findSubstring("acaacc", new String[]{"ca", "ac"}));
    }

    public void testNextPermutation() {
        int[] array = new int[]{1, 2, 3};
        solution.nextPermutation(array);
        assertEquals(new int[]{1, 3, 2}, array);

        array = new int[]{1, 1, 5};
        solution.nextPermutation(array);
        assertEquals(new int[]{1, 5, 1}, array);

        array = new int[]{1, 3, 2};
        solution.nextPermutation(array);
        assertEquals(new int[]{2, 1, 3}, array);

        array = new int[]{3, 2, 1};
        solution.nextPermutation(array);
        assertEquals(new int[]{1, 2, 3}, array);

    }

    public void testLongestValidParentheses() {
        assertEquals(6, solution.longestValidParentheses("((()))"));
        assertEquals(0, solution.longestValidParentheses(")"));
        assertEquals(2, solution.longestValidParentheses("()"));
        assertEquals(2, solution.longestValidParentheses("())"));
        assertEquals(2, solution.longestValidParentheses("(()"));
        assertEquals(4, solution.longestValidParentheses(")()()("));
        assertEquals(4, solution.longestValidParentheses("(()()"));
        assertEquals(2, solution.longestValidParentheses("))()(("));
        assertEquals(0, solution.longestValidParentheses("("));
        assertEquals(2, solution.longestValidParentheses("())"));
        assertEquals(2, solution.longestValidParentheses("((((((()"));
        assertEquals(6, solution.longestValidParentheses("()(())"));
        assertEquals(4, solution.longestValidParentheses(")()())()()("));
        assertEquals(16, solution.longestValidParentheses("()(()((()()((()))))"));
        assertEquals(4, solution.longestValidParentheses("))))())()()(()"));
        assertEquals(2684, solution.longestValidParentheses("(((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((()())))(())()())((((()())((())))((()))()())))()(()()()(()((()))()()()))()()()(()()((((())()(((()(((())((()))()((()(()))()())))))))))())()())(()()))((()()()()())))((()()((((()()))))(())())()()))))(())()(()))((((((()))(()()()()(())(()((()))(()(())(((()()))(()((((()((((()((((())(())))()(())))()))(()((((((((())()()((())((()())()))))())())()(((((()()(((((())()((()(((()))(()(()))(()(()())))())(()((((()((()(((((()()))((()(()((())()))))(()(()())((()((()((((())))(()())()))()())())()))))(())))(())()((())(()(()))))()())(((()(()(((((((((()(()()())))((()((()())())())(((((()(()))))()))()))))()())()(()(())))(()))))(()))(((()))))())))))(((())((())((((()((()))((())))()))(((()))())()))()()()((()()(()())(()))()()((()())))))())(()())(((())))))())(())()))()())())(()(()((())((()(()((())(()()()(()((()(((()(())()(((())))))()())))))(()((((()(()()))(((())(()))(()()))))(())()((()))()))()()))()((())(()())())())(()))(()()(())()(()((((()())(((())(()()())())(()()))())))(()((())(()()))))(()))((()()((((()())(()()))()())()())))()(()((((())())()(())()))()()(()(()))))))(((()()((()))(()((((()()((())))())())))()())))())))((())()()()))()((()((()))()()())))(())())(()(()(()(()))())()))(())((())()())(((()()(((())(()()))(()())(())))()))(((()()()())))())))(((()))())())())))(((()))()())())())))))()()()()(())))(()())))(()()())))()((((()()()((((()))()())))(()))()))))(()())()))(((((())()((())()))(()())()()()())()(((()(()(())))))(()(((()()))((((()()))()))(((())(()(()))()(())))()()(()))))()))))()())))()))((((((((()()())((()(()()()(((())())())))()()(())(())))()())()())))((()))((((())()()))(())(((())(()()(((((()()((()()(((()(()()(((())()))))()(()())(()((((()()())(((()))(())((())()))))())))))(()()()())))()))(())((()())()())()()))(())))((()))()()((()())()()))(()()(())()())(())))((()(((())))()))))((((()))((())())())()(())(()))((((((())()()(((((()))()())(((()(()(())()((()())))(((())(()(())))))(()(()(((()))(())((((())))((())((((((((()(((((()(())))((((((())(()((((()(())()()((())())())((((((((()))))(((())()))()()))(())(())()()())(()()((())(()))())(((())(()((())(())(())))))(()(()(()()(((()()()))())(()))(())())()(((()((())((()())()(((((()()(()))))(((())()()))(()(()(()(()((())))))))(())())()))()(()(()))))()()((((())()())(((())(()))((()())(()((())()()(())((((())))))(())())())(())(()()(()()))(((()((((())(((())))))(()()()()(((()((((())(()))((())()))()(((((((()(()())))((()()(()()((())()))()(())))((()()((((()()()))((())()))((())(((()(()()()(((()((())((())()())())))((()))))))))))(())()()(((()()())))(((()))(()))))(((()(()())(()))(())((()))(((()(()()(((((((()())((((()))((((()(()())())()(((()(()((()))))))))))()()(((()()((((((((((())))))((((())())((()(((()())()))()()(((((())(()())())(((()((())((((((())(((())(((()(()(((((((()(())()())(()))))(()(((()))))))()))(((())))(()(()())()))(()()(()(()((()())()(())((()()((()()()(()(()()))(((((())()(()())()((()())()))(((((()((())()((()((((()(((())())(()()(())()(())(()(())))))(()())((()((()()()())(()))(()))))))(()((())(())((())()())()()))(()((()))(()()))()())(())(()()(()))((())()((())((((((())()(()()(((((())(()())())())()()(()())))))()))()((())((((((()())((()))))))((()(()()(((((((())))))))((()))(())(((()(()(())()()()()(()(())()))))))())()))()(((((()(())(((()))((()))()))()()(()(()((())(()))))()())((()())))))))(()()(()()))()((()(())()((())(()()))())((()())())()()))))((((()()()))())(())()())))()))()))))()))((()(()())()))()))(((()()()()())))())()))((()()())((()())))(((()((()()())(())))()(())(()(()(())(()(((((()()()(((())()())(()((()())(()(((()(())((((()())()(())))(((((((()))))())())))(()))()()(((()())(()))()())(())()))()((())()((())((()((())()())(()()))(((((()()()((((((((()(()((()()((((((()())))((((((())))())(()(()((((()(()())())()()))()((())())(()((((()(((()())((())))))(()())(()()()(()))()())()()))((()((()())(())()()()((())()()))))())()))())))(()))(()))()))((())()((()((()))))))())(((()))))))()(((()((())))((()())())()))((()(()(()(()))((()()))())))(()())))())(()))(())(())))))()(())(()()))()))((())))(()))(()))))(())()())(()(()))())(()(())(())))(()))())(()())))())(()())((()))()()((()(()()()(((((()((()((())(()())(())))()))))))(((())())))()((((()))()((()))())()))()))(()(()((()()())()()(((()))())))))()((((()()))))()))())))()())))(((((()(())))())(((()))((()))(((()(())())()((()(((()))()())))))((((()))()(()((((((()(()()()())(())((()))()(()()))))))()(((())))(())()())))))((()))(())()))))(()(((()()((())(()))))(((((()))))())))()(())(()(()))()))()))(()((())(()((()())()(((()))))())(())()(())))((())(()(((()))(((((()))(()))())))(()((((((())()((((())())()))((())))))())(()(())())))))()()(((())()())))))()))()())))()(())())(())()()()(((())))(())(((()))(()(((()()))())((()))(((()()()()())()()))(()))))()()))))(((()()))))()()(()()))()()()())())()((())(((()())(((())(()((()(((()(()())()()()(()((())(()()(()()()))))))()((()))))()(()))()))(())()()())))()()(((()))((()()(((()())))((()()())((())))))()())()((())))())(()())()()()()((())((()()())((()()))())(())())()(()(((()))())(()))))(()()))(())))))))()())()((()())()()))()())))((()()(()())()(()))((())()))(((())))())))(((()()())())("));
    }

    public void testSearch() {
        assertEquals(2, solution.search(new int[]{1, 3, 5}, 5));
        assertEquals(5, solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 1));
        assertEquals(0, solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 4));
        assertEquals(-1, solution.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 8));
    }

    public void testSearchRange() {
        assertEquals(new int[]{3, 4}, solution.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8));
        assertEquals(new int[]{-1, -1}, solution.searchRange(new int[]{1}, 0));
        assertEquals(new int[]{-1, -1}, solution.searchRange(new int[]{1, 1}, 0));
        assertEquals(new int[]{0, 1}, solution.searchRange(new int[]{2, 2}, 2));
    }

    public void testSearchInsert() {
        assertEquals(0, solution.searchInsert(new int[]{0}, 0));
        assertEquals(1, solution.searchInsert(new int[]{0}, 4));
        assertEquals(1, solution.searchInsert(new int[]{1, 3}, 2));
        assertEquals(0, solution.searchInsert(new int[]{1, 3, 5}, 1));
        assertEquals(2, solution.searchInsert(new int[]{1, 3, 5, 6}, 5));
        assertEquals(1, solution.searchInsert(new int[]{1, 3, 5, 6}, 2));
        assertEquals(4, solution.searchInsert(new int[]{1, 3, 5, 6}, 7));
        assertEquals(0, solution.searchInsert(new int[]{1, 3, 5, 6}, 0));
        assertEquals(2, solution.searchInsert(new int[]{1, 4, 6, 7, 8, 9}, 6));
    }

    public void testIsValidSudoku() {
        char[][] board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'},
        };
        assertEquals(true, solution.isValidSudoku(board));

        board[0][0] = '9';
        assertEquals(false, solution.isValidSudoku(board));
    }

    public void testSolveSudoku() {
        char[][] board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'},
        };

        char[][] solved = new char[][]{
                {'5', '3', '4', '6', '7', '8', '9', '1', '2'},
                {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
                {'1', '9', '8', '3', '4', '2', '5', '6', '7'},
                {'8', '5', '9', '7', '6', '1', '4', '2', '3'},
                {'4', '2', '6', '8', '5', '3', '7', '9', '1'},
                {'7', '1', '3', '9', '2', '4', '8', '5', '6'},
                {'9', '6', '1', '5', '3', '7', '2', '8', '4'},
                {'2', '8', '7', '4', '1', '9', '6', '3', '5'},
                {'3', '4', '5', '2', '8', '6', '1', '7', '9'},
        };

        solution.solveSudoku(board);
        assertArrayEquals(solved, board);

    }

    public void testCountAndSay() {
        assertEquals("1", solution.countAndSay(1));
        assertEquals("11", solution.countAndSay(2));
        assertEquals("21", solution.countAndSay(3));
        assertEquals("1211", solution.countAndSay(4));
        assertEquals("111221", solution.countAndSay(5));
    }

    public void testLRUCache() {
        LRUCache cache = new LRUCache(1);
        assertEquals(-1, cache.get(1));
        cache.set(1, 100);
        assertEquals(100, cache.get(1));
        cache.set(1, 200);
        assertEquals(200, cache.get(1));
        cache.set(2, 300);
        assertEquals(-1, cache.get(1));
        assertEquals(300, cache.get(2));

        cache = new LRUCache(2);
        cache.set(2, 1);
        cache.set(1, 1);
        assertEquals(1, cache.get(2));
        cache.set(4, 1);
        assertEquals(-1, cache.get(1));
        assertEquals(1, cache.get(2));

        cache = new LRUCache(3);
        cache.set(1, 1);
        cache.set(2, 2);
        cache.set(3, 3);
        cache.set(2, 4);
        cache.set(4, 5);
        assertEquals(5, cache.get(4));
        assertEquals(4, cache.get(2));
        assertEquals(3, cache.get(3));
        assertEquals(-1, cache.get(1));
    }

    public void testSortList() {
        ListNode head = ListNode.build(1);
        ListNode newHead = solution.sortList(head);
        assertEquals(1, newHead.val);
        assertEquals(null, head.next);

        head = ListNode.build(2, 1);
        newHead = solution.sortList(head);
        assertEquals(1, newHead.val);
        assertEquals(2, newHead.next.val);
        assertEquals(null, newHead.next.next);

        head = ListNode.build(2, 1, 3);
        newHead = solution.sortList(head);
        assertEquals(1, newHead.val);
        assertEquals(2, newHead.next.val);
        assertEquals(3, newHead.next.next.val);
        assertEquals(null, newHead.next.next.next);

        head = ListNode.build(2, 1, 5, 3, 1, 10, 2);
        newHead = solution.sortList(head);
        assertEquals(1, newHead.val);
        assertEquals(1, newHead.next.val);
        assertEquals(2, newHead.next.next.val);
        assertEquals(2, newHead.next.next.next.val);
        assertEquals(3, newHead.next.next.next.next.val);
        assertEquals(5, newHead.next.next.next.next.next.val);
        assertEquals(10, newHead.next.next.next.next.next.next.val);
        assertEquals(null, newHead.next.next.next.next.next.next.next);
    }

    public void testCombinationSum() {
        int[] candidates = new int[]{2, 3, 6, 7};
        ArrayList<ArrayList<Integer>> expectedCombos = arrayListOf(
                arrayListOf(2, 2, 3),
                arrayListOf(7)
        );
        assertEquals(expectedCombos, solution.combinationSum(candidates, 7));
    }

    public void testCombinationSum2() {
        int[] candidates = new int[]{10, 1, 2, 7, 6, 1, 5};
        ArrayList<ArrayList<Integer>> expectedCombos = arrayListOf(
                arrayListOf(1, 2, 5),
                arrayListOf(1, 7),
                arrayListOf(1, 1, 6),
                arrayListOf(2, 6)
        );
        assertEquals(expectedCombos, solution.combinationSum2(candidates, 8));

        candidates = new int[]{1};
        expectedCombos = arrayListOf(
                arrayListOf(1)
        );
        assertEquals(expectedCombos, solution.combinationSum2(candidates, 1));
    }

    public void testFirstMissingPositive() {
        assertEquals(3, solution.firstMissingPositive(new int[]{1, 2, 0}));
        assertEquals(2, solution.firstMissingPositive(new int[]{3, 4, -1, 1}));
        assertEquals(2, solution.firstMissingPositive(new int[]{3, 4, -1, 1, 1, 1, 1, 1}));
        assertEquals(6, solution.firstMissingPositive(new int[]{-3, 9, 16, 4, 5, 16, -4, 9, 26, 2, 1, 19, -1, 25, 7, 22, 2, -7, 14, 2, 5, -6, 1, 17, 3, 24, -4, 17, 15}));
        assertEquals(2, solution.firstMissingPositive(new int[]{3, 4, -1, 3, 3, -3, -1, 1, 1, 1, 1}));
    }

    public void testTrap() {
        assertEquals(6, solution.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        assertEquals(0, solution.trap(new int[]{}));
    }

    public void testMultiply() {
        assertEquals("987", solution.multiply("987", "1"));
        assertEquals("987", solution.multiply("987", "001"));
        assertEquals("0", solution.multiply("987", "000"));
        assertEquals("0", solution.multiply("2", "0"));
        assertEquals("4", solution.multiply("2", "2"));
        assertEquals("408", solution.multiply("12", "34"));
        assertEquals("121481442", solution.multiply("987654", "123"));
        assertEquals("43948012904063856759044120137240872866229860081261532279707206564606727572550", solution.multiply("9589786154075", "4582793838983465951968751293058626896181199565052151246575992754"));
        assertEquals("30501687172287445993560048081057096686019986405658336826483685740920318317486606305094807387278589614", solution.multiply("60974249908865105026646412538664653190280198809433017", "500238825698990292381312765074025160144624723742"));
        assertEquals("67500649338953014190022427720009974152122888684303491539825680400401937831201855940252", solution.multiply("9369162965141127216164882458728854782080715827760307787224298083754", "7204554941577564438"));
        assertEquals("1584586728493525893642541066405904799198536104293994395887321573366110169031498781815747478010725266431858021835056717082517535189123595786349457873248", solution.multiply("581852037460725882246068583352420736139988952640866685633288423526139", "2723349969536684936041476639043426870967112972397011150925040382981287990380531232"));
        assertEquals("681344793223740657242403970125810525392681110604025091311764298548399357610192132687865941776993697029677098827798229059122389135701735092750089068639470461443672046293149640058727618291254104325945998811392274887032839061818500689444177927957742349680103792957450334154097251265748729189362320754544559409008954281592028906601652250209483417753147780454262099529876311249553954236029853931047783719339767442657316986744840626481382649734696411571490356995343288134793227547214793813508620947728760675834671048850253285575858036767525783268999760079879704717174035767186420033458097211236067500998571795926828205899442550426202616945433017572600151183112007566301052632823055936869155751839359210486440469754913156779208008947144791634631073557429982520641293200252", solution.multiply("93693691629651411272161648824587288547820807158277603077872242980837549162993691629651411272161648824587288547820807158277603077872242980837546514112721616936916296514112721616488245872885478208071582776030778722429808375448824587288547829369162965141127216164882458728854782080715827760307787224298083754080715827760307787224298083754", "7272045549415775649369162965141127216164882458728854782080715827760307787224298083754438720455494157756493691629651411272161648824587288547820807158277603077872242980837547204554941577564936916296514112721616488245872885478208071582776030778722429808375720455494157756493691629651411272161648824587288547820807158277603077872242980837544384438438045549415775649369162965141127216164882458728854782080715827760307787224298083754438"));
    }

    public void testIsWildcardMatch() {
        assertEquals(false, solution.isWildcardMatch("a", ""));
        assertEquals(false, solution.isWildcardMatch("a", "aa"));
        assertEquals(false, solution.isWildcardMatch("aa", "a"));
        assertEquals(true, solution.isWildcardMatch("aa", "aa"));
        assertEquals(false, solution.isWildcardMatch("aaa", "aa"));
        assertEquals(true, solution.isWildcardMatch("aa", "*"));
        assertEquals(true, solution.isWildcardMatch("aa", "a*"));
        assertEquals(true, solution.isWildcardMatch("ab", "?*"));
        assertEquals(false, solution.isWildcardMatch("aab", "c*a*b"));
        assertEquals(true, solution.isWildcardMatch("bab", "b*a*b"));
        assertEquals(true, solution.isWildcardMatch("bab", "*a*"));
        assertEquals(true, solution.isWildcardMatch("abbabbbaabaaabbbbbabbabbabbbabbaaabbbababbabaaabbab", "*aabb***aa**a******aa*"));
        assertEquals(true, solution.isWildcardMatch("abbaabbbbababaababababbabbbaaaabbbbaaabbbabaabbbbbabbbbabbabbaaabaaaabbbbbbaaabbabbbbababbbaaabbabbabb", "***b**a*a*b***b*a*b*bbb**baa*bba**b**bb***b*a*aab*a**"));
        assertEquals(false, solution.isWildcardMatch("abbabaaabbabbaababbabbbbbabbbabbbabaaaaababababbbabababaabbababaabbbbbbaaaabababbbaabbbbaabbbbababababbaabbaababaabbbababababbbbaaabbbbbabaaaabbababbbbaababaabbababbbbbababbbabaaaaaaaabbbbbaabaaababaaaabb", "**aa*****ba*a*bb**aa*ab****a*aaaaaa***a*aaaa**bbabb*b*b**aaaaaaaaa*a********ba*bbb***a*ba*bb*bb**a*b*bb"));
    }

    public void testCanJump() {
        assertEquals(true, solution.canJump(new int[]{2, 3, 1, 1, 4}));
        assertEquals(false, solution.canJump(new int[]{3, 2, 1, 0, 4}));
        assertEquals(false, solution.canJump(new int[]{1, 0, 1, 0}));
        assertEquals(true, solution.canJump(new int[]{3, 0, 0, 0}));
        assertEquals(false, solution.canJump(new int[]{1, 0, 0, 1, 1, 2, 2, 0, 2, 2}));
    }

    public void testJump() {
        assertEquals(2, solution.jump(new int[]{2, 3, 1, 1, 4}));
    }

    public void testPermute() {
        ArrayList<ArrayList<Integer>> expected = arrayListOf(
                arrayListOf(1, 5, 10),
                arrayListOf(1, 10, 5),
                arrayListOf(5, 1, 10),
                arrayListOf(5, 10, 1),
                arrayListOf(10, 5, 1),
                arrayListOf(10, 1, 5)
        );
        assertEquals(expected, solution.permute(new int[]{1, 5, 10}));
    }

    public void testPermuteUnique() {
        ArrayList<ArrayList<Integer>> expected = arrayListOf(
                arrayListOf(1, 1, 2),
                arrayListOf(1, 2, 1),
                arrayListOf(2, 1, 1)
        );
        assertEquals(expected, solution.permuteUnique(new int[]{1, 1, 2}));

        expected = arrayListOf(
                arrayListOf(-1, -1, 3, -1),
                arrayListOf(-1, -1, -1, 3),
                arrayListOf(-1, 3, -1, -1),
                arrayListOf(3, -1, -1, -1)
        );
        assertEquals(expected, solution.permuteUnique(new int[]{-1, -1, 3, -1}));

        expected = arrayListOf(
                arrayListOf(2, 1, 1),
                arrayListOf(1, 2, 1),
                arrayListOf(1, 1, 2)
        );
        assertEquals(expected, solution.permuteUnique(new int[]{2, 1, 1}));

    }

    public void testRotate() {
        int[][] image = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] rotated = new int[][]{
                {7, 4, 1},
                {8, 5, 2},
                {9, 6, 3}
        };

        solution.rotate(image);
        assertArrayEquals(rotated, image);

        image = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16},
        };

        rotated = new int[][]{
                {13, 9, 5, 1},
                {14, 10, 6, 2},
                {15, 11, 7, 3},
                {16, 12, 8, 4}
        };

        solution.rotate(image);
        assertArrayEquals(rotated, image);
    }

    public void testAnagrams() {
        assertEquals(arrayListOf("ab", "ba", "ca", "ac"), solution.anagrams(new String[]{"ab", "bb", "ba", "ca", "cc", "ac", "dd"}));
    }

    public void testPow() {
        assertEquals(4.0d, solution.pow(2, 2));
        assertEquals(1 / 4.0d, solution.pow(2, -2));
    }

    public void testSolveNQueens() {
        ArrayList<String[]> expected = new ArrayList<String[]>();
        expected.add(new String[]{
                ".Q..",
                "...Q",
                "Q...",
                "..Q.",
        });

        expected.add(new String[]{
                "..Q.",
                "Q...",
                "...Q",
                ".Q..",
        });


        ArrayList<String[]> solved = solution.solveNQueens(4);
        for (int i = 0; i < solved.size(); i++) {
            assertEquals(expected.get(i), solved.get(i));
        }

        expected = new ArrayList<String[]>();
        expected.add(new String[]{"Q"});

        solved = solution.solveNQueens(1);
        for (int i = 0; i < solved.size(); i++) {
            assertEquals(expected.get(i), solved.get(i));
        }
    }

    public void testTotalNQueens() {
        assertEquals(92, solution.totalNQueens(8));
        assertEquals(1, solution.totalNQueens(1));
    }

    public void testMaxSubArray() {
        assertEquals(6, solution.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        assertEquals(-1, solution.maxSubArray(new int[]{-1}));
    }

    public void testSpiralOrder() {
        assertEquals(arrayListOf(1), solution.spiralOrder(new int[][]{
                {1}
        }));

        assertEquals(arrayListOf(1, 2, 4, 3), solution.spiralOrder(new int[][]{
                {1, 2},
                {3, 4}
        }));

        assertEquals(arrayListOf(1, 2, 3, 6, 9, 8, 7, 4, 5), solution.spiralOrder(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        }));

        assertEquals(arrayListOf(1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10), solution.spiralOrder(new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        }));

        assertEquals(arrayListOf(1, 2, 4, 6, 8, 7, 5, 3), solution.spiralOrder(new int[][]{
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8}
        }));

        assertEquals(arrayListOf(2, 3), solution.spiralOrder(new int[][]{{2, 3}}));

        assertEquals(arrayListOf(7, 9, 6), solution.spiralOrder(new int[][]{
                {7},
                {9},
                {6}
        }));
    }

    public void testMerge() {

        ArrayList<Interval> input = arrayListOf(
                new Interval(1, 3), new Interval(2, 6), new Interval(8, 10), new Interval(15, 18)
        );
        ArrayList<Interval> merged = arrayListOf(
                new Interval(1, 6), new Interval(8, 10), new Interval(15, 18)
        );
        assertEquals(merged, solution.merge(input));


        input = new ArrayList<Interval>();
        merged = new ArrayList<Interval>();
        assertEquals(merged, solution.merge(input));


        input = arrayListOf(
                new Interval(1, 3)
        );
        merged = arrayListOf(
                new Interval(1, 3)
        );
        assertEquals(merged, solution.merge(input));


        input = arrayListOf(
                new Interval(1, 3), new Interval(4, 6), new Interval(7, 9), new Interval(10, 12), new Interval(5, 11), new Interval(13, 14)
        );
        merged = arrayListOf(
                new Interval(1, 3), new Interval(4, 12), new Interval(13, 14)
        );
        assertEquals(merged, solution.merge(input));


        input = arrayListOf(
                new Interval(2, 3), new Interval(5, 5), new Interval(2, 2), new Interval(3, 4), new Interval(3, 4)
        );
        merged = arrayListOf(
                new Interval(2, 4), new Interval(5, 5)
        );
        assertEquals(merged, solution.merge(input));


        input = arrayListOf(
                new Interval(1, 4), new Interval(1, 5)
        );
        merged = arrayListOf(
                new Interval(1, 5)
        );
        assertEquals(merged, solution.merge(input));

    }

    public void testInsert() {
        ArrayList<Interval> input = arrayListOf(
                new Interval(1, 3), new Interval(6, 9)
        );
        ArrayList<Interval> inserted = arrayListOf(
                new Interval(1, 5), new Interval(6, 9)
        );
        assertEquals(inserted, solution.insert(input, new Interval(2, 5)));


        input = arrayListOf(
                new Interval(1, 2), new Interval(3, 5), new Interval(6, 7), new Interval(8, 10), new Interval(12, 16)
        );
        inserted = arrayListOf(
                new Interval(1, 2), new Interval(3, 10), new Interval(12, 16)
        );
        assertEquals(inserted, solution.insert(input, new Interval(4, 9)));


        input = arrayListOf(
                new Interval(1, 5)
        );
        inserted = arrayListOf(
                new Interval(1, 7)
        );
        assertEquals(inserted, solution.insert(input, new Interval(2, 7)));
    }

    public void testLengthOfLastWord() {
        assertEquals(5, solution.lengthOfLastWord("Hello World"));
        assertEquals(0, solution.lengthOfLastWord("   "));
        assertEquals(3, solution.lengthOfLastWord("Nice day"));
        assertEquals(1, solution.lengthOfLastWord("a"));
    }

    public void testGenerateMatrix() {
        assertArrayEquals(new int[][]{
                {1, 2, 3, 4},
                {12, 13, 14, 5},
                {11, 16, 15, 6},
                {10, 9, 8, 7}
        }, solution.generateMatrix(4));

        assertArrayEquals(new int[][]{
                {1}
        }, solution.generateMatrix(1));
    }

    public void testGetPermutation() {
        assertEquals("231", solution.getPermutation(3, 4));
        assertEquals("1", solution.getPermutation(1, 1));
    }

    public void testRotateRight() {
        ListNode input = ListNode.build(1, 2, 3, 4, 5);
        ListNode rotated = solution.rotateRight(input, 2);
        assertEquals(4, rotated.val);
        assertEquals(5, rotated.next.val);
        assertEquals(1, rotated.next.next.val);
        assertEquals(2, rotated.next.next.next.val);
        assertEquals(3, rotated.next.next.next.next.val);
        assertEquals(null, rotated.next.next.next.next.next);

        input = ListNode.build(1, 2);
        rotated = solution.rotateRight(input, 2);
        assertEquals(1, rotated.val);
        assertEquals(2, rotated.next.val);
        assertEquals(null, rotated.next.next);
    }

    public void testUniquePaths() {
        assertEquals(1, solution.uniquePaths(0, 0));
        assertEquals(3, solution.uniquePaths(2, 3));
        assertEquals(1, solution.uniquePaths(1, 2));
    }

    public void testUniquePathsWithObstacles() {
        assertEquals(1, solution.uniquePathsWithObstacles(new int[][]{
                {0, 0},
                {1, 0},
                {0, 0}
        }));

        assertEquals(3, solution.uniquePathsWithObstacles(new int[][]{
                {0, 0},
                {0, 0},
                {0, 0}
        }));

        assertEquals(0, solution.uniquePathsWithObstacles(new int[][]{
                {1}
        }));
    }

}
