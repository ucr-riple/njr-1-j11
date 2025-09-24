package leetcode.oj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static leetcode.oj.Solution.ListNode;
import static leetcode.oj.Solution.TreeNode;
import static leetcode.oj.Solution2.*;
import static leetcode.oj.TestUtils.*;

/**
 * Author: Sobercheg on 1/29/14.
 * Unit tests for LeetCode OJ solutions.
 */
public class Solution2Test {
    private final Solution2 solution = new Solution2();

    public static void main(String[] args) {
        Solution2Test solutionTest = new Solution2Test();
        solutionTest.testMinPathSum();
        solutionTest.testMergeTwoLists();
        solutionTest.testAddBinary();
        solutionTest.testPlusOne();
        solutionTest.testIsNumber();
        solutionTest.testFullJustify();
        solutionTest.testClimbStairs();
        solutionTest.testSimplifyPath();
        solutionTest.testMinDistance();
        solutionTest.testSetZeros();
        solutionTest.testMinWindow();
        solutionTest.testSearchMatrix();
        solutionTest.testSortColors();
        solutionTest.testExist();
        solutionTest.testCombine();
        solutionTest.testSubsets();
        solutionTest.testRemoveDuplicates();
        solutionTest.testSearch();
        solutionTest.testDeleteDuplicates();
        solutionTest.testDeleteDuplicatesII();
        solutionTest.testLargestRectangleArea();
        solutionTest.testInorderTraversal();
        solutionTest.testNumTrees();
        solutionTest.testGenerateTrees();
        solutionTest.testIsValidBST();
        solutionTest.testIsSameTree();
        solutionTest.testBuildTree();
        solutionTest.testIsSymmetric();
        solutionTest.testMaximalRectangle();
        solutionTest.testPartition();
        solutionTest.testIsInterleave();
        solutionTest.testReorderList();
        solutionTest.testLevelOrder();
        solutionTest.testIsScramble();
        solutionTest.testMerge();
        solutionTest.testGrayCode();
        solutionTest.testNumDecoings();
        solutionTest.testSubsetsWithDup();
        solutionTest.testRestoreIp();
        solutionTest.testSingleNumber();
        solutionTest.testSingleNumberII();
        solutionTest.testMaxDepth();
        solutionTest.testMinDepth();
        solutionTest.testLevelOrderBottom();
        solutionTest.testZigzagLevelOrder();
        solutionTest.testSortedArrayToBST();
        solutionTest.testIsBalanced();
        solutionTest.testHasCycle();
        solutionTest.testDetectCycle();
        solutionTest.testLongestConsecutive();
        solutionTest.testHasPathSum();
        solutionTest.testSortedListToBST();
        solutionTest.testReverseBetween();
        solutionTest.testPathSum();
        solutionTest.testBuildTreePost();
        solutionTest.testWordBreak();
        solutionTest.testWordBreakII();
        solutionTest.testFlatten();
        solutionTest.testMaxPathSum();
        solutionTest.testSumNumbers();
        solutionTest.testCopyRandomList();
        solutionTest.testRecoverTree();
        solutionTest.testMinimumTotal();
        solutionTest.testConnect();
        solutionTest.testConnectII();
        solutionTest.testGenerate();
        solutionTest.testGetRow();
        solutionTest.testIsPalindrome();
        solutionTest.testSolve();
        solutionTest.testCloneGraph();
    }

    public void testMinPathSum() {
        assertEquals(18, solution.minPathSum(new int[][]{
                {1, 2, 3},
                {4, 5, 5},
                {6, 8, 7},
        })); // 1 2 3 5 7

        assertEquals(1, solution.minPathSum(new int[][]{
                {1}
        }));
    }

    public void testMergeTwoLists() {
        ListNode list1 = ListNode.build(1, 3, 5);
        ListNode list2 = ListNode.build(2, 4, 6);
        ListNode actualMerged = solution.mergeTwoLists(list1, list2);
        ListNode expectedMerged = ListNode.build(1, 2, 3, 4, 5, 6);
        assertEquals(expectedMerged, actualMerged);

        list2 = ListNode.build(1);
        actualMerged = solution.mergeTwoLists(null, list2);
        expectedMerged = ListNode.build(1);
        assertEquals(expectedMerged, actualMerged);

        list1 = ListNode.build(2);
        list2 = ListNode.build(1);
        actualMerged = solution.mergeTwoLists(list1, list2);
        expectedMerged = ListNode.build(1, 2);
        assertEquals(expectedMerged, actualMerged);
    }

    public void testAddBinary() {
        assertEquals("100", solution.addBinary("11", "1"));
    }

    public void testPlusOne() {
        assertEquals(new int[]{1, 0, 0}, solution.plusOne(new int[]{9, 9}));
    }

    public void testIsNumber() {
        assertEquals(true, solution.isNumber("1"));
        assertEquals(true, solution.isNumber("1e1"));
        assertEquals(true, solution.isNumber("-1e1"));
        assertEquals(true, solution.isNumber("-1."));
        assertEquals(true, solution.isNumber("-1.2 "));
        assertEquals(true, solution.isNumber("-1.2e23"));
        assertEquals(true, solution.isNumber("-1.2e-23"));
        assertEquals(false, solution.isNumber("-1.2e-23e"));
        assertEquals(false, solution.isNumber("a"));
        assertEquals(false, solution.isNumber(" -."));
    }

    public void testFullJustify() {
        assertEquals(Arrays.asList(
                        "This    is    an",
                        "example  of text",
                        "justification.  "),
                solution.fullJustify(
                        new String[]{"This", "is", "an", "example", "of", "text", "justification."}, 16)
        );

        assertEquals(Arrays.asList(
                        "                "),
                solution.fullJustify(
                        new String[]{""}, 16)
        );

        assertEquals(Arrays.asList("a"), solution.fullJustify(new String[]{"a"}, 1));

        assertEquals(Arrays.asList(
                        "Listen",
                        "to    ",
                        "many, ",
                        "speak ",
                        "to   a",
                        "few.  "),
                solution.fullJustify(
                        new String[]{"Listen", "to", "many,", "speak", "to", "a", "few."}, 6)
        );

        assertEquals(Arrays.asList(
                        "world  owes  you a living; the",
                        "world                         "),
                solution.fullJustify(
                        new String[]{"world", "owes", "you", "a", "living;", "the", "world"}, 30)
        );

    }

    public void testClimbStairs() {
        assertEquals(3, solution.climbStairs(3));
        assertEquals(5, solution.climbStairs(4));
    }

    public void testSimplifyPath() {
        assertEquals("/home", solution.simplifyPath("/home/"));
        assertEquals("/c", solution.simplifyPath("/a/./b/../../c/"));
    }

    public void testMinDistance() {
        assertEquals(3, solution.minDistance("bugaga", "bgzgz"));
        assertEquals(1, solution.minDistance("a", ""));
    }

    public void testSetZeros() {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {1, 0, 4},
                {1, 1, 1},
        };
        solution.setZeroes(matrix);
        assertArrayEquals(matrix, new int[][]{
                {1, 0, 3},
                {0, 0, 0},
                {1, 0, 1},
        });

        matrix = new int[][]{
                {1, 2},
                {1, 0},
        };
        solution.setZeroes(matrix);
        assertArrayEquals(matrix, new int[][]{
                {1, 0},
                {0, 0},
        });

        matrix = new int[][]{
                {0, 0, 0, 5},
                {4, 3, 1, 4},
                {0, 1, 1, 4},
                {1, 2, 1, 3},
                {0, 0, 1, 1}
        };
        solution.setZeroes(matrix);
        assertArrayEquals(matrix, new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 4},
                {0, 0, 0, 0},
                {0, 0, 0, 3},
                {0, 0, 0, 0}
        });
    }

    public void testMinWindow() {
        assertEquals("ba", solution.minWindow("bba", "ab"));
        assertEquals("BANC", solution.minWindow("ADOBECODEBANC", "ABC"));
        assertEquals("", solution.minWindow("ADOBECODEBANC", "ABQ"));
        assertEquals("a", solution.minWindow("a", "a"));
        assertEquals("b", solution.minWindow("ab", "b"));
        assertEquals("aa", solution.minWindow("aa", "aa"));
        assertEquals("aec", solution.minWindow("cabefgecdaecf", "cae"));
        assertEquals("cwae", solution.minWindow("cabwefgewcwaefgcf", "cae"));
    }

    public void testSearchMatrix() {
        int[][] matrix = new int[][]{
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 50},
        };
        assertEquals(true, solution.searchMatrix(matrix, 3));
        assertEquals(false, solution.searchMatrix(matrix, 4));
        assertEquals(false, solution.searchMatrix(matrix, 0));
        assertEquals(false, solution.searchMatrix(matrix, 70));
        assertEquals(true, solution.searchMatrix(matrix, 11));

        matrix = new int[][]{
                {1, 3},
        };
        assertEquals(true, solution.searchMatrix(matrix, 1));
        assertEquals(true, solution.searchMatrix(matrix, 3));
        assertEquals(false, solution.searchMatrix(matrix, 2));
    }

    public void testSortColors() {
        int[] colors = new int[]{1, 0, 2, 0, 1, 1, 0, 2, 0};
        solution.sortColors(colors);
        assertEquals(new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2}, colors);

        colors = new int[]{2, 1};
        solution.sortColors(colors);
        assertEquals(new int[]{1, 2}, colors);
    }

    public void testExist() {
        char[][] board = new char[][]{
                "abce".toCharArray(),
                "sfcs".toCharArray(),
                "adee".toCharArray(),
        };

        assertEquals(true, solution.exist(board, "abcced"));
        assertEquals(true, solution.exist(board, "see"));
        assertEquals(false, solution.exist(board, "abcb"));

        board = new char[][]{
                {'a'}
        };

        assertEquals(true, solution.exist(board, "a"));
    }

    public void testCombine() {
        ArrayList<ArrayList<Integer>> expected = new ArrayList<ArrayList<Integer>>();
        expected.add(arrayListOf(1, 2));
        expected.add(arrayListOf(1, 3));
        expected.add(arrayListOf(1, 4));
        expected.add(arrayListOf(2, 3));
        expected.add(arrayListOf(2, 4));
        expected.add(arrayListOf(3, 4));

        assertEquals(expected, solution.combine(4, 2));
    }

    public void testSubsets() {
        ArrayList<ArrayList<Integer>> expected = new ArrayList<ArrayList<Integer>>();
        expected.add(new ArrayList<Integer>());
        expected.add(arrayListOf(1));
        expected.add(arrayListOf(2));
        expected.add(arrayListOf(1, 2));
        expected.add(arrayListOf(3));
        expected.add(arrayListOf(1, 3));
        expected.add(arrayListOf(2, 3));
        expected.add(arrayListOf(1, 2, 3));

        assertEquals(expected, solution.subsets(new int[]{1, 2, 3}));

        expected = new ArrayList<ArrayList<Integer>>();
        expected.add(new ArrayList<Integer>());
        expected.add(new ArrayList<Integer>(Arrays.asList(0)));

        assertEquals(expected, solution.subsets(new int[]{0}));
    }

    public void testRemoveDuplicates() {
        int[] a = new int[]{1, 1, 1, 2, 2, 3};
        assertEquals(5, solution.removeDuplicates(a));
        assertEquals(new int[]{1, 1, 2, 2, 3}, a, 5);

        a = new int[]{1, 2, 3};
        assertEquals(3, solution.removeDuplicates(a));
        assertEquals(new int[]{1, 2, 3}, a, 3);

        a = new int[]{1};
        assertEquals(1, solution.removeDuplicates(a));
        assertEquals(new int[]{1}, a, 1);

        a = new int[]{1, 1, 1, 1, 1};
        assertEquals(2, solution.removeDuplicates(a));
        assertEquals(new int[]{1, 1}, a, 2);

        a = new int[]{1, 1, 1, 1, 2};
        assertEquals(3, solution.removeDuplicates(a));
        assertEquals(new int[]{1, 1, 2}, a, 3);
    }

    public void testSearch() {
        assertEquals(true, solution.search(new int[]{1, 2, 1, 1, 1}, 1));
        assertEquals(true, solution.search(new int[]{1, 2, 1, 1, 1}, 2));
        assertEquals(false, solution.search(new int[]{1, 2, 1, 1, 1}, 3));
        assertEquals(false, solution.search(new int[]{1, 2, 1, 1, 1}, 0));
        assertEquals(true, solution.search(new int[]{1, 2, 3, 4, 1}, 4));
        assertEquals(false, solution.search(new int[]{1}, 0));
    }

    public void testDeleteDuplicates() {
        ListNode root = ListNode.build(1);
        ListNode noDuplicates = solution.deleteDuplicates(root);
        ListNode expected = ListNode.build(1);
        assertEquals(expected, noDuplicates);

        root = ListNode.build(1, 2, 3);
        noDuplicates = solution.deleteDuplicates(root);
        expected = ListNode.build(1, 2, 3);
        assertEquals(expected, noDuplicates);

        root = ListNode.build(1, 1, 1, 2);
        noDuplicates = solution.deleteDuplicates(root);
        expected = ListNode.build(1, 2);
        assertEquals(expected, noDuplicates);
    }

    public void testDeleteDuplicatesII() {
        ListNode root = ListNode.build(1);
        ListNode noDuplicatesActual = solution.deleteDuplicatesII(root);
        ListNode noDuplicatesExpected = ListNode.build(1);
        assertEquals(noDuplicatesExpected, noDuplicatesActual);

        root = ListNode.build(1, 2, 3);
        noDuplicatesActual = solution.deleteDuplicatesII(root);
        noDuplicatesExpected = ListNode.build(1, 2, 3);
        assertEquals(noDuplicatesExpected, noDuplicatesActual);

        root = ListNode.build(1, 1, 1, 2, 3);
        noDuplicatesActual = solution.deleteDuplicatesII(root);
        noDuplicatesExpected = ListNode.build(2, 3);
        assertEquals(noDuplicatesExpected, noDuplicatesActual);

        root = ListNode.build(1, 2, 3, 3, 4, 4, 5);
        noDuplicatesActual = solution.deleteDuplicatesII(root);
        noDuplicatesExpected = ListNode.build(1, 2, 5);
        assertEquals(noDuplicatesExpected, noDuplicatesActual);

    }

    public void testLargestRectangleArea() {
        assertEquals(10, solution.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
        assertEquals(10, solution.largestRectangleArea(new int[]{10}));
        assertEquals(20, solution.largestRectangleArea(new int[]{10, 10}));
        assertEquals(20, solution.largestRectangleArea(new int[]{10, 11}));
        assertEquals(3, solution.largestRectangleArea(new int[]{2, 1, 2}));
        assertEquals(10, solution.largestRectangleArea(new int[]{4, 2, 0, 3, 2, 4, 3, 4}));
    }

    public void testInorderTraversal() {
        Solution.TreeNode root = new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null));
        assertEquals(Arrays.asList(1, 3, 2), solution.inorderTraversal(root));

        root = new TreeNode(1, new TreeNode(2, new TreeNode(3, new TreeNode(4), null), new TreeNode(5, new TreeNode(6), null)), null);
        assertEquals(Arrays.asList(4, 3, 2, 6, 5, 1), solution.inorderTraversal(root));
    }

    public void testNumTrees() {
        assertEquals(5, solution.numTrees(3));
        assertEquals(1, solution.numTrees(1));
    }

    public void testGenerateTrees() {
        ArrayList<TreeNode> expectedTrees = new ArrayList<TreeNode>();
        expectedTrees.add(new TreeNode(1, null, new TreeNode(2, null, new TreeNode(3))));
        expectedTrees.add(new TreeNode(1, null, new TreeNode(3, new TreeNode(2), null)));
        expectedTrees.add(new TreeNode(2, new TreeNode(1), new TreeNode(3)));
        expectedTrees.add(new TreeNode(3, new TreeNode(1, null, new TreeNode(2)), null));
        expectedTrees.add(new TreeNode(3, new TreeNode(2, new TreeNode(1), null), null));
        ArrayList<TreeNode> generatedTrees = solution.generateTrees(3);
        assertEquals(expectedTrees, generatedTrees);

        expectedTrees = new ArrayList<TreeNode>();
        expectedTrees.add(new TreeNode(1));
        generatedTrees = solution.generateTrees(1);
        assertEquals(expectedTrees, generatedTrees);
    }

    public void testIsValidBST() {
        assertEquals(true, solution.isValidBST(new TreeNode(2, new TreeNode(1), new TreeNode(3))));
        assertEquals(false, solution.isValidBST(new TreeNode(2, new TreeNode(2), new TreeNode(3))));
    }

    public void testIsSameTree() {
        TreeNode tree1 = new TreeNode(1, new TreeNode(2), null);
        TreeNode tree2 = new TreeNode(1, new TreeNode(2), null);
        assertEquals(true, solution.isSameTree(tree1, tree2));

        tree1 = new TreeNode(1, new TreeNode(2), null);
        tree2 = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        assertEquals(false, solution.isSameTree(tree1, tree2));
    }

    public void testBuildTree() {
        TreeNode root = solution.buildTree(new int[]{1, 2, 3, 5, 6, 7, 4}, new int[]{3, 2, 5, 7, 6, 1, 4});
        assertEquals(1, root.val);
        assertEquals(2, root.left.val);
        assertEquals(4, root.right.val);
        assertEquals(null, root.right.right);
        assertEquals(null, root.right.left);
        assertEquals(3, root.left.left.val);
        assertEquals(5, root.left.right.val);
        assertEquals(6, root.left.right.right.val);
        assertEquals(7, root.left.right.right.left.val);
    }

    public void testIsSymmetric() {
        TreeNode root = new TreeNode(1, new TreeNode(2, null, new TreeNode(3)), new TreeNode(2, new TreeNode(3), null));
        assertEquals(true, solution.isSymmetric(root));

        root = new TreeNode(1, new TreeNode(2, null, new TreeNode(3)), new TreeNode(2, null, new TreeNode(3)));
        assertEquals(false, solution.isSymmetric(root));

        assertEquals(true, solution.isSymmetric(null));

        assertEquals(true, solution.isSymmetric(new TreeNode(1)));
    }

    public void testMaximalRectangle() {
        char[][] matrix = new char[][]{
                "000000011".toCharArray(),
                "000011100".toCharArray(),
                "000011100".toCharArray(),
                "111100011".toCharArray(),
                "111000001".toCharArray(),
        };

        assertEquals(6, solution.maximalRectangle(matrix));
    }

    public void testPartition() {
        ListNode list = ListNode.build(1, 4, 3, 2, 5, 2);
        ListNode partitioned = solution.partition(list, 3);
        assertEquals(ListNode.build(1, 2, 2, 4, 3, 5), partitioned);

        list = ListNode.build(1, 4, 3, 2, 5, 2);
        partitioned = solution.partition(list, 6);
        assertEquals(ListNode.build(1, 4, 3, 2, 5, 2), partitioned);

        list = ListNode.build(1, 4, 3, 2, 5, 2);
        partitioned = solution.partition(list, 0);
        assertEquals(ListNode.build(1, 4, 3, 2, 5, 2), partitioned);
    }

    public void testIsInterleave() {
        assertEquals(true, solution.isInterleave("abc", "cde", "abcdec"));
        assertEquals(true, solution.isInterleave("abc", "cde", "acbdec"));
        assertEquals(false, solution.isInterleave("abc", "cde", "cbadec"));
        assertEquals(true, solution.isInterleave("aabcc", "dbbca", "aadbbcbcac"));
        assertEquals(false, solution.isInterleave("aabcc", "dbbca", "aadbbbaccc"));
        assertEquals(false, solution.isInterleave("abc", "def", "zzzzzz"));
    }

    public void testReorderList() {
        ListNode list = ListNode.build(1, 2, 3, 4, 5, 6, 7, 8, 9);
        ListNode reorderedList = ListNode.build(1, 9, 2, 8, 3, 7, 4, 6, 5);
        solution.reorderList(list);
        assertEquals(reorderedList, list);

        list = ListNode.build(1, 2);
        reorderedList = ListNode.build(1, 2);
        solution.reorderList(list);
        assertEquals(reorderedList, list);
    }

    public void testLevelOrder() {
        TreeNode root = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        ArrayList<ArrayList<Integer>> expectedLevelOrder = arrayListOf(
                arrayListOf(3),
                arrayListOf(9, 20),
                arrayListOf(15, 7)
        );
        ArrayList<ArrayList<Integer>> actualLevelOrder = solution.levelOrder(root);
        assertEquals(expectedLevelOrder, actualLevelOrder);
    }

    public void testIsScramble() {
        assertEquals(true, solution.isScramble("rgeat", "great"));
        assertEquals(true, solution.isScramble("rgtae", "great"));
        assertEquals(true, solution.isScramble("abb", "bab"));
        assertEquals(false, solution.isScramble("abcdefghijklmnopq", "efghijklmnopqcadb"));
        assertEquals(false, solution.isScramble("pcighfdjnbwfkohtklrecxnooxyipj", "npodkfchrfpxliocgtnykhxwjbojie"));
        assertEquals(true, solution.isScramble("abc", "bca"));
    }

    public void testMerge() {
        int[] A = new int[]{1, 0};
        int[] B = new int[]{2};
        solution.merge(A, 1, B, 1);
        assertEquals(new int[]{1, 2}, A);

        A = new int[]{2, 0};
        B = new int[]{1};
        solution.merge(A, 1, B, 1);
        assertEquals(new int[]{1, 2}, A);
    }

    public void testGrayCode() {
        assertEquals(arrayListOf(0, 1, 3, 2), solution.grayCode(2));
        assertEquals(arrayListOf(0, 1), solution.grayCode(1));
    }

    public void testNumDecoings() {
        assertEquals(2, solution.numDecodings("12"));
        assertEquals(0, solution.numDecodings("0"));
        assertEquals(0, solution.numDecodings("01"));
        assertEquals(0, solution.numDecodings("100"));
        assertEquals(1, solution.numDecodings("3102"));
        assertEquals(3981312, solution.numDecodings("9371597631128776948387197132267188677349946742344217846154932859125134924241649584251978418763151253"));
    }

    public void testSubsetsWithDup() {
        assertEquals(arrayListOf(
                arrayListOf(),
                arrayListOf(1),
                arrayListOf(2),
                arrayListOf(1, 2),
                arrayListOf(2, 2),
                arrayListOf(1, 2, 2)
        ), solution.subsetsWithDup(new int[]{1, 2, 2}));

        assertEquals(arrayListOf(
                arrayListOf(),
                arrayListOf(1),
                arrayListOf(1, 1)
        ), solution.subsetsWithDup(new int[]{1, 1}));

    }

    public void testRestoreIp() {
        assertEquals(arrayListOf("255.255.11.135", "255.255.111.35"), solution.restoreIpAddresses("25525511135"));
        assertEquals(arrayListOf("0.0.0.0"), solution.restoreIpAddresses("0000"));
    }

    public void testSingleNumber() {
        assertEquals(2, solution.singleNumber(new int[]{2}));
        assertEquals(2, solution.singleNumber(new int[]{2, 1, 1}));
        assertEquals(2, solution.singleNumber(new int[]{1, 3, 2, 3, 1}));
    }

    public void testSingleNumberII() {
        assertEquals(2, solution.singleNumberII(new int[]{2}));
        assertEquals(2, solution.singleNumberII(new int[]{1, 2, 1, 1, 4, 4, 4}));
        assertEquals(2, solution.singleNumberII(new int[]{1, 3, 3, 1, 2, 3, 1}));
    }

    public void testMaxDepth() {
        TreeNode root = new TreeNode(1, new TreeNode(2, new TreeNode(3), null), new TreeNode(4));
        assertEquals(3, solution.maxDepth(root));
        root = new TreeNode(1, null, new TreeNode(2));
        assertEquals(2, solution.maxDepth(root));
    }

    public void testMinDepth() {
        TreeNode root = new TreeNode(1, new TreeNode(2, new TreeNode(3), null), new TreeNode(4));
        assertEquals(2, solution.minDepth(root));
        root = new TreeNode(1, null, new TreeNode(2));
        assertEquals(2, solution.minDepth(root));
    }

    public void testLevelOrderBottom() {
        TreeNode root = new TreeNode(1, new TreeNode(2, new TreeNode(3), null), new TreeNode(4));
        ArrayList<ArrayList<Integer>> expectedTraversal = arrayListOf(
                arrayListOf(3),
                arrayListOf(2, 4),
                arrayListOf(1)
        );
        assertEquals(expectedTraversal, solution.levelOrderBottom(root));
    }

    public void testZigzagLevelOrder() {
        TreeNode root = new TreeNode(1, new TreeNode(2, new TreeNode(3), new TreeNode(5)), new TreeNode(4));
        ArrayList<ArrayList<Integer>> expectedTraversal = arrayListOf(
                arrayListOf(1),
                arrayListOf(4, 2),
                arrayListOf(3, 5)
        );
        assertEquals(expectedTraversal, solution.zigzagLevelOrder(root));

    }

    public void testSortedArrayToBST() {
        TreeNode actualTree = solution.sortedArrayToBST(new int[]{1, 2, 3, 4, 5});
        TreeNode expectedTree = new TreeNode(3, new TreeNode(1, null, new TreeNode(2)), new TreeNode(4, null, new TreeNode(5)));
        assertEquals(expectedTree.val, actualTree.val);
        assertEquals(expectedTree.left.val, actualTree.left.val);
        assertEquals(expectedTree.left.right.val, actualTree.left.right.val);

        assertEquals(expectedTree.right.val, actualTree.right.val);
        assertEquals(expectedTree.right.left, actualTree.right.left);
        assertEquals(expectedTree.right.right.val, actualTree.right.right.val);
    }

    public void testIsBalanced() {
        TreeNode balanced = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        assertEquals(true, solution.isBalanced(balanced));

        balanced = new TreeNode(1, new TreeNode(2), null);
        assertEquals(true, solution.isBalanced(balanced));

        balanced = new TreeNode(1, new TreeNode(2, new TreeNode(3), null), null);
        assertEquals(false, solution.isBalanced(balanced));
    }

    public void testHasCycle() {
        assertEquals(false, solution.hasCycle(ListNode.build(1, 2, 3, 4, 5)));
        ListNode cycledList = new ListNode(1);
        cycledList.next = new ListNode(2);
        cycledList.next.next = cycledList;
        assertEquals(true, solution.hasCycle(cycledList));
    }

    public void testDetectCycle() {
        assertEquals(null, solution.detectCycle(ListNode.build(1, 2, 3, 4, 5)));
        ListNode cycleList = new ListNode(1, new ListNode(2));
        ListNode cycleNode = new ListNode(3, new ListNode(4, new ListNode(5)));
        cycleNode.next.next.next = cycleNode;
        cycleList.next.next = cycleNode;
        assertEquals(3, solution.detectCycle(cycleList).val);
    }

    public void testLongestConsecutive() {
        assertEquals(1, solution.longestConsecutive(new int[]{0, 3, 7}));
        assertEquals(9, solution.longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
        assertEquals(5, solution.longestConsecutive(new int[]{1, 3, 5, 2, 4}));
        assertEquals(4, solution.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
        assertEquals(13, solution.longestConsecutive(new int[]{-1, 9, -3, -6, 7, -8, -6, 2, 9, 2, 3, -2, 4, -1, 0, 6, 1, -9, 6, 8, 6, 5, 2}));
    }

    public void testHasPathSum() {
        assertEquals(true, solution.hasPathSum(new TreeNode(5,
                new TreeNode(4,
                        new TreeNode(11, new TreeNode(7), new TreeNode(2)), null),
                new TreeNode(8, new TreeNode(13), new TreeNode(4, null, new TreeNode(1)))
        ), 22));

        assertEquals(false, solution.hasPathSum(new TreeNode(5, null, new TreeNode(4)), 11));
        assertEquals(false, solution.hasPathSum(new TreeNode(5, null, new TreeNode(4)), 1));
        assertEquals(true, solution.hasPathSum(new TreeNode(5, null, new TreeNode(4)), 9));
        assertEquals(false, solution.hasPathSum(new TreeNode(1, new TreeNode(2), null), 1));
    }

    public void testSortedListToBST() {
        ListNode list = ListNode.build(1, 2, 3);
        TreeNode expectedTree = new TreeNode(2, new TreeNode(1), new TreeNode(3));
        assertEquals(expectedTree, solution.sortedListToBST(list));

        assertEquals(new TreeNode(1), solution.sortedListToBST(new ListNode(1)));

        list = ListNode.build(1, 2, 3, 4, 5);
        expectedTree = new TreeNode(3, new TreeNode(1, null, new TreeNode(2)), new TreeNode(5, new TreeNode(4), null));
        assertEquals(expectedTree, solution.sortedListToBST(list));
    }

    public void testReverseBetween() {
        ListNode reversedActual = solution.reverseBetween(ListNode.build(1, 2, 3, 4, 5), 2, 4);
        assertEquals(ListNode.build(1, 4, 3, 2, 5), reversedActual);

        reversedActual = solution.reverseBetween(ListNode.build(1, 2, 3, 4, 5), 2, 5);
        assertEquals(ListNode.build(1, 5, 4, 3, 2), reversedActual);

        reversedActual = solution.reverseBetween(ListNode.build(1, 2, 3, 4, 5), 1, 5);
        assertEquals(ListNode.build(5, 4, 3, 2, 1), reversedActual);

        reversedActual = solution.reverseBetween(ListNode.build(1, 2, 3, 4, 5), 3, 4);
        assertEquals(ListNode.build(1, 2, 4, 3, 5), reversedActual);

    }

    public void testPathSum() {
        TreeNode root = new TreeNode(5,
                new TreeNode(4,
                        new TreeNode(11,
                                new TreeNode(7), new TreeNode(2)), null
                ),
                new TreeNode(8,
                        new TreeNode(13),
                        new TreeNode(4,
                                new TreeNode(5), new TreeNode(1))
                )
        );

        ArrayList<ArrayList<Integer>> expectedPaths = arrayListOf(
                arrayListOf(5, 4, 11, 2),
                arrayListOf(5, 8, 4, 5)
        );
        assertEquals(expectedPaths, solution.pathSum(root, 22));
    }

    public void testBuildTreePost() {
        TreeNode expectedTree = new TreeNode(1,
                new TreeNode(2,
                        new TreeNode(3, new TreeNode(4), new TreeNode(5)),
                        new TreeNode(6)),
                new TreeNode(7, new TreeNode(8), null)
        );
        assertEquals(expectedTree, solution.buildTreePost(new int[]{4, 3, 5, 2, 6, 1, 8, 7}, new int[]{4, 5, 3, 6, 2, 8, 7, 1}));

        expectedTree = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        assertEquals(expectedTree, solution.buildTreePost(new int[]{2, 1, 3}, new int[]{2, 3, 1}));
    }

    public void testWordBreak() {
        assertEquals(true, solution.wordBreak("leetcode", new HashSet<String>(Arrays.asList("code", "leet"))));
        assertEquals(false, solution.wordBreak("buka", new HashSet<String>(Arrays.asList("buk", "ka"))));
    }

    public void testWordBreakII() {
        ArrayList<String> expectedWords = arrayListOf("cats and dog", "cat sand dog");
        assertEquals(expectedWords, solution.wordBreakII("catsanddog",
                new HashSet<String>(Arrays.asList("cat", "cats", "and", "sand", "dog"))));

        expectedWords = arrayListOf();
        assertEquals(expectedWords, solution.wordBreakII("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab",
                new HashSet<String>(Arrays.asList("a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa", "aaaaaaaaaa"))));

        expectedWords = arrayListOf("a");
        assertEquals(expectedWords, solution.wordBreakII("a", new HashSet<String>(Arrays.asList("a"))));
    }

    public void testFlatten() {
        TreeNode tree = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), new TreeNode(4)),
                new TreeNode(5, null, new TreeNode(6)));
        TreeNode expectedFlattened = new TreeNode(1, null, new TreeNode(2, null, new TreeNode(3, null, new TreeNode(4, null, new TreeNode(5, null, new TreeNode(6))))));
        solution.flatten(tree);
        assertEquals(expectedFlattened, tree);
    }

    public void testMaxPathSum() {
        assertEquals(-3, solution.maxPathSum(new TreeNode(-3)));
        assertEquals(6, solution.maxPathSum(new TreeNode(1, new TreeNode(2), new TreeNode(3))));
        assertEquals(1, solution.maxPathSum(new TreeNode(-2, new TreeNode(1), null)));
    }

    public void testSumNumbers() {
        assertEquals(25, solution.sumNumbers(new TreeNode(1, new TreeNode(2), new TreeNode(3))));
    }

    public void testCopyRandomList() {
        RandomListNode origHead = new RandomListNode(1);
        RandomListNode origNext = new RandomListNode(2);
        RandomListNode origNextNext = new RandomListNode(3);
        origHead.next = origNext;
        origNext.next = origNextNext;
        origNextNext.random = origNext;
        origHead.random = origHead;

        RandomListNode copyHead = solution.copyRandomList(origHead);
        assertEquals(1, copyHead.label);
        assertEquals(2, copyHead.next.label);
        assertEquals(3, copyHead.next.next.label);
        assertEquals(1, copyHead.random.label);
        assertEquals(2, copyHead.next.next.random.label);

        origHead = new RandomListNode(-1);
        origHead.next = new RandomListNode(1);
        copyHead = solution.copyRandomList(origHead);
        assertEquals(false, origHead == copyHead);
        assertEquals(false, origHead.next == copyHead.next);
    }

    public void testRecoverTree() {
        TreeNode root = new TreeNode(2, new TreeNode(3), new TreeNode(1));
        solution.recoverTree(root);
        assertEquals(2, root.val);
        assertEquals(1, root.left.val);
        assertEquals(3, root.right.val);

        root = new TreeNode(0, new TreeNode(1), null);
        solution.recoverTree(root);
        assertEquals(1, root.val);
        assertEquals(0, root.left.val);

        root = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        solution.recoverTree(root);
        assertEquals(2, root.val);
        assertEquals(1, root.left.val);
        assertEquals(3, root.right.val);

        root = new TreeNode(68,
                new TreeNode(41, new TreeNode(-85,
                        new TreeNode(-73,
                                new TreeNode(-98, new TreeNode(-124), null), null), new TreeNode(-49)
                ), null), null
        );

        solution.recoverTree(root);
        assertEquals(-73, root.left.left.val);
        assertEquals(-85, root.left.left.left.val);
    }

    public void testMinimumTotal() {
        assertEquals(1, solution.minimumTotal(arrayListOf(
                arrayListOf(-1),
                arrayListOf(2, 3)
        )));

        assertEquals(-1, solution.minimumTotal(arrayListOf(
                arrayListOf(-1),
                arrayListOf(2, 3),
                arrayListOf(1, -1, -3)
        )));

        assertEquals(11, solution.minimumTotal(arrayListOf(
                arrayListOf(2),
                arrayListOf(3, 4),
                arrayListOf(6, 5, 7),
                arrayListOf(4, 1, 8, 3)
        )));

    }

    public void testConnect() {
        TreeLinkNode root = new TreeLinkNode(1,
                new TreeLinkNode(2, new TreeLinkNode(4), new TreeLinkNode(5)),
                new TreeLinkNode(3, new TreeLinkNode(6), new TreeLinkNode(7)));

        solution.connect(root);
        assertEquals(3, root.left.next.val);
        assertEquals(5, root.left.left.next.val);
        assertEquals(6, root.left.right.next.val);
        assertEquals(7, root.right.left.next.val);
    }

    public void testConnectII() {
        TreeLinkNode root = new TreeLinkNode(1,
                new TreeLinkNode(2, new TreeLinkNode(4), new TreeLinkNode(5)),
                new TreeLinkNode(3, null, new TreeLinkNode(7)));

        solution.connectII(root);
        assertEquals(3, root.left.next.val);
        assertEquals(5, root.left.left.next.val);
        assertEquals(7, root.left.right.next.val);

        root = new TreeLinkNode(1,
                new TreeLinkNode(2, new TreeLinkNode(3, new TreeLinkNode(13), null), null),
                new TreeLinkNode(4, null, new TreeLinkNode(5, null, new TreeLinkNode(15))));
        solution.connectII(root);
        assertEquals(4, root.left.next.val);
        assertEquals(5, root.left.left.next.val);
        assertEquals(15, root.left.left.left.next.val);

        root = new TreeLinkNode(1,
                new TreeLinkNode(2,
                        new TreeLinkNode(4, new TreeLinkNode(7), null), new TreeLinkNode(5)),
                new TreeLinkNode(3, null, new TreeLinkNode(6, null, new TreeLinkNode(8)))
        );
        solution.connectII(root);
        assertEquals(3, root.left.next.val);
        assertEquals(5, root.left.left.next.val);
        assertEquals(6, root.left.right.next.val);
        assertEquals(8, root.left.left.left.next.val);

        root = new TreeLinkNode(-9,
                new TreeLinkNode(-3, null, new TreeLinkNode(4, new TreeLinkNode(-6), null)),
                new TreeLinkNode(-2, new TreeLinkNode(4, new TreeLinkNode(-5), null), new TreeLinkNode(0)));
        solution.connectII(root);
        assertEquals(-2, root.left.next.val);
        assertEquals(4, root.left.right.next.val);
        assertEquals(0, root.right.left.next.val);
        assertEquals(-5, root.left.right.left.next.val);
    }

    public void testGenerate() {
        assertEquals(arrayListOf(
                arrayListOf(1),
                arrayListOf(1, 1),
                arrayListOf(1, 2, 1),
                arrayListOf(1, 3, 3, 1),
                arrayListOf(1, 4, 6, 4, 1)
        ), solution.generate(5));
    }

    public void testGetRow() {
        assertEquals(arrayListOf(1, 1), solution.getRow(1));
        assertEquals(arrayListOf(1, 2, 1), solution.getRow(2));
        assertEquals(arrayListOf(1, 3, 3, 1), solution.getRow(3));
        assertEquals(arrayListOf(1, 4, 6, 4, 1), solution.getRow(4));
    }

    public void testIsPalindrome() {
        assertEquals(true, solution.isPalindrome("A man, a plan, a canal: Panama"));
        assertEquals(false, solution.isPalindrome("race a car"));
        assertEquals(true, solution.isPalindrome(""));
        assertEquals(true, solution.isPalindrome(" "));
    }

    public void testSolve() {
        char[][] board = new char[][]{
                "XXXX".toCharArray(),
                "XOOX".toCharArray(),
                "XXOX".toCharArray(),
                "XOXX".toCharArray(),
        };
        solution.solve(board);
        assertArrayEquals(new char[][]{
                "XXXX".toCharArray(),
                "XXXX".toCharArray(),
                "XXXX".toCharArray(),
                "XOXX".toCharArray(),
        }, board);

        board = new char[][]{
                "XOOOOOOOOOOOOOOOOOOO".toCharArray(),
                "OXOOOOXOOOOOOOOOOOXX".toCharArray(),
                "OOOOOOOOXOOOOOOOOOOX".toCharArray(),
                "OOXOOOOOOOOOOOOOOOXO".toCharArray(),
                "OOOOOXOOOOXOOOOOXOOX".toCharArray(),
                "XOOOXOOOOOXOXOXOXOXO".toCharArray(),
                "OOOOXOOXOOOOOXOOXOOO".toCharArray(),
                "XOOOXXXOXOOOOXXOXOOO".toCharArray(),
                "OOOOOXXXXOOOOXOOXOOO".toCharArray(),
                "XOOOOXOOOOOOXXOOXOOX".toCharArray(),
                "OOOOOOOOOOXOOXOOOXOX".toCharArray(),
                "OOOOXOXOOXXOOOOOXOOO".toCharArray(),
                "XXOOOOOXOOOOOOOOOOOO".toCharArray(),
                "OXOXOOOXOXOOOXOXOXOO".toCharArray(),
                "OOXOOOOOOOXOOOOOXOXO".toCharArray(),
        };
        solution.solve(board);
        assertArrayEquals(new char[][]{
                "XOOOOOOOOOOOOOOOOOOO".toCharArray(),
                "OXOOOOXOOOOOOOOOOOXX".toCharArray(),
                "OOOOOOOOXOOOOOOOOOOX".toCharArray(),
                "OOXOOOOOOOOOOOOOOOXO".toCharArray(),
                "OOOOOXOOOOXOOOOOXOOX".toCharArray(),
                "XOOOXOOOOOXOXOXOXOXO".toCharArray(),
                "OOOOXOOXOOOOOXOOXOOO".toCharArray(),
                "XOOOXXXXXOOOOXXOXOOO".toCharArray(),
                "OOOOOXXXXOOOOXOOXOOO".toCharArray(),
                "XOOOOXOOOOOOXXOOXOOX".toCharArray(),
                "OOOOOOOOOOXOOXOOOXOX".toCharArray(),
                "OOOOXOXOOXXOOOOOXOOO".toCharArray(),
                "XXOOOOOXOOOOOOOOOOOO".toCharArray(),
                "OXOXOOOXOXOOOXOXOXOO".toCharArray(),
                "OOXOOOOOOOXOOOOOXOXO".toCharArray(),
        }, board);
    }

    public void testCloneGraph() {
        UndirectedGraphNode graph = new UndirectedGraphNode(0);
        UndirectedGraphNode node1 = new UndirectedGraphNode(1);
        UndirectedGraphNode node2 = new UndirectedGraphNode(2);
        graph.neighbors = arrayListOf(node1, node2);
        node1.neighbors = arrayListOf(node2);
        node2.neighbors = arrayListOf(node2);
        UndirectedGraphNode copy = solution.cloneGraph(graph);
        assertEquals(0, copy.label);
        assertEquals(1, copy.neighbors.get(0).label);
        assertEquals(2, copy.neighbors.get(1).label);
        assertEquals(2, copy.neighbors.get(0).neighbors.get(0).label);
        assertEquals(2, copy.neighbors.get(1).neighbors.get(0).label);

        graph = new UndirectedGraphNode(0);
        graph.neighbors = new ArrayList<UndirectedGraphNode>();
        graph.neighbors.add(graph);
        graph.neighbors.add(graph);
        copy = solution.cloneGraph(graph);
        assertEquals(0, copy.label);
        assertEquals(2, copy.neighbors.size());
        assertEquals(0, copy.neighbors.get(0).label);
        assertEquals(0, copy.neighbors.get(1).label);
    }
}
