package edu.utils;

import org.junit.Test;

/**
 * Created by yurii.pyvovarenko on 22.03.14.
 */
public class HierarchyTreeNodeTest {

    @Test
    public void shouldHaveAtLeastOneElement() {
        //given
        HierarchyTreeNode tree;

        //when
        tree = new HierarchyTreeNode("TTT", null);

        //then
        org.junit.Assert.assertFalse(tree.size() == 0);

    }

    @Test
    public void shouldAddNode() {
        //given
        HierarchyTreeNode tree = new HierarchyTreeNode("TTT", null);
        int size = tree.size();

        //when
        tree.addNode("QQQ");

        //then
        org.junit.Assert.assertEquals(tree.size(), size + 1);

    }

    @Test
    public void shouldGetProperSizeAfterAddingFiveNode() {
        //given
        HierarchyTreeNode tree = new HierarchyTreeNode("TTT", null);
        tree.addNode("QQQ_1");
        tree.addNode("QQQ_2");
        int size = tree.size();

        //when
        for (int i = 0; i < 5; i++)
            tree.addNode("QQQ" + i);

        //then
        org.junit.Assert.assertEquals(tree.size(), size + 5);
    }

    @Test
    public void shouldGetTreeNodeHeight() {
        //given
        HierarchyTreeNode tree = new HierarchyTreeNode("TTT", null);
        tree.addNode("QQQ");

        //when

        //then
        org.junit.Assert.assertTrue(tree.getHeight() > 1);

    }

    @Test
    public void shouldGetProperTreeNodeHeight() {
        //given
        HierarchyTreeNode binaryTreeNode = new HierarchyTreeNode("TTT", null);
        int level = 5;
        double levelNodesCount = 1;
        for (double i = level; i > 0; i--)  {
            levelNodesCount += Math.pow(binaryTreeNode.getMaxChildrenCount(), i);
        }

        //when
        for (int i = 0;
             i < levelNodesCount;
             i++)
            binaryTreeNode.addNode("QQQ" + i);

        //then
        //TODO org.junit.Assert.assertEquals("levelNodesCount = " + levelNodesCount, 1 + level, binaryTreeNode.getHeight());
    }

    @Test
    public void shouldHaveProperNumOfChildren() {
        //given
        HierarchyTreeNode binaryTreeNode = new HierarchyTreeNode("TTT", null);

        //when
        binaryTreeNode.addNode("QQQ");
        binaryTreeNode.addNode("QQQ1");
        binaryTreeNode.addNode("QQQ2");

        //then
        org.junit.Assert.assertTrue(binaryTreeNode.getMaxChildrenCount() >= binaryTreeNode.getChildrenCount());
    }

    @Test
    public void shouldRiseHeightWhenAddedMoreThanMaxChildrenPerNode() {
        //given
        HierarchyTreeNode binaryTreeNode = new HierarchyTreeNode("TTT", null);
        int initialHeight = binaryTreeNode.getHeight();

        //when
        for (int i = 0; i < binaryTreeNode.getMaxChildrenCount(); i++)
            binaryTreeNode.addNode("QQQ" + i);
        binaryTreeNode.addNode("QQQ_!");

        //then
        org.junit.Assert.assertTrue(initialHeight + 1 < binaryTreeNode.getHeight());
    }

    @Test
    public void shouldHaveSameHeightWhenAddedLessThanMaxChildrenPerNode() {
        //given
        HierarchyTreeNode binaryTreeNode = new HierarchyTreeNode("TTT", null);
        int initialHeight = binaryTreeNode.getHeight();

        //when
        for (int i = 0; i < binaryTreeNode.getMaxChildrenCount(); i++)
            binaryTreeNode.addNode("QQQ" + i);

        //then
        org.junit.Assert.assertEquals(initialHeight + 1, binaryTreeNode.getHeight());
    }
}
