package edu.utils;

import org.junit.Test;

import java.security.InvalidParameterException;

/**
 * Created by yurii.pyvovarenko on 22.03.14.
 */
public class BinaryTreeNodeTest {

    @Test
    public void shouldHaveAtLeastOneElement() {
        //given
        BinaryTreeNode tree;

        //when
        tree = new BinaryTreeNode("TTT");

        //then
        org.junit.Assert.assertFalse(tree.size() == 0);
    }

    @Test (expected = InvalidParameterException.class)
    public void shouldExceptionWhenNoDataGivenForConstructor() {
        //given

        //when
        BinaryTreeNode tree = new BinaryTreeNode(null); //InvalidParameterException

        //then

    }

    @Test
    public void shouldHaveSizeOfOneWhenNoChildren() {
        //given
        BinaryTreeNode tree = new BinaryTreeNode("TTT");

        //when

        //then
        org.junit.Assert.assertEquals(tree.size(), 1);
    }

    @Test
    public void shouldAddNodeAndRizeSizeByOne() {
        //given
        BinaryTreeNode tree = new BinaryTreeNode("TTT");

        //when
        tree.addNode("QQQ");

        //then
        org.junit.Assert.assertEquals(tree.size(), 2);
    }

    @Test
    public void shouldHaveProperSizeWhenSeveralChildrenAdded() {
        //given
        BinaryTreeNode binaryTreeNode = new BinaryTreeNode("TTT");
        binaryTreeNode.addNode("QQQ_1");
        binaryTreeNode.addNode("QQQ_2");
        int size = binaryTreeNode.size();

        //when
        int childrenToAdd = 5;
        for (int i = 0; i < childrenToAdd; i++)
            binaryTreeNode.addNode("ZZZ" + i);

        //then
        org.junit.Assert.assertEquals("added children = " + childrenToAdd, size + childrenToAdd, binaryTreeNode.size());
    }

    @Test
    public void shouldGetTreeNodeHeightOfOneWhenNoChildren() {
        //given
        BinaryTreeNode tree = new BinaryTreeNode("TTT");

        //when

        //then
        org.junit.Assert.assertEquals(1, tree.getHeight());
    }

    @Test
    public void shouldGetTreeNodeHeightOfTwoWhenFirstChildAdded() {
        //given
        BinaryTreeNode binaryTreeNode = new BinaryTreeNode("TTT");

        //when
        binaryTreeNode.addNode("ZZZ");

        //then
        org.junit.Assert.assertEquals(2, binaryTreeNode.getHeight());
    }

    @Test
    public void shouldIncrementHeightWhenAddedLessThanTwoChildren() {
        //given
        BinaryTreeNode binaryTreeNode = new BinaryTreeNode("TTT");
        binaryTreeNode.addNode("ZZZ");
        int initialHeight = binaryTreeNode.getHeight();

        //when
        binaryTreeNode.addNode("ZZZ");

        //then
        org.junit.Assert.assertEquals(initialHeight + 1, binaryTreeNode.getHeight());
    }

    @Test
    public void shouldGetProperTreeNodeHeightWhenSeveralChildrenAddedToOneSide() {
        //given
        BinaryTreeNode binaryTreeNode = new BinaryTreeNode("TTT");
        int level = 5;

        //when
        for (int i = 0; i < level; i++)
            binaryTreeNode.addNode("ZZZ" + i);

        //then
        org.junit.Assert.assertEquals("added levels = " + level, 1 + level, binaryTreeNode.getHeight());
    }

    @Test
    public void shouldRiseHeightByTwoWhenAddedMoreThanTwoNodesPerChildrenNode() {
        //given
        BinaryTreeNode binaryTreeNode = new BinaryTreeNode("TTT");
        int initialHeight = binaryTreeNode.getHeight();

        //when
        binaryTreeNode.addNode("QQ5");
        binaryTreeNode.addNode("ZZZ_5");

        binaryTreeNode.addNode("QQ1");
        binaryTreeNode.addNode("QQ7");

        binaryTreeNode.addNode("ZZZ_1");
        binaryTreeNode.addNode("ZZZ_7");

        //then
        org.junit.Assert.assertTrue("\n" + binaryTreeNode.toString(), initialHeight + 2 == binaryTreeNode.getHeight()); //TODO must be EQUAL
    }

    @Test
    public void shouldGetString() {
        //given
        BinaryTreeNode binaryTreeNode = new BinaryTreeNode("TTT");

        //when

        //then
        org.junit.Assert.assertEquals("binaryTreeNode.toString() = " + binaryTreeNode.toString(), "TTT", binaryTreeNode.toString());
    }

    @Test
    public void shouldAddLeftNode() {
        //given
        BinaryTreeNode binaryTreeNode = new BinaryTreeNode("TTT");

        //when
        binaryTreeNode.addNode("QQQ");

        //then
        org.junit.Assert.assertEquals(" QQQ", binaryTreeNode.getChildNodeLeft().toString());
    }

    @Test
    public void shouldAddRightNode() {
        //given
        BinaryTreeNode binaryTreeNode = new BinaryTreeNode("TTT");

        //when
        binaryTreeNode.addNode("ZZZ");

        //then
        org.junit.Assert.assertEquals(" ZZZ", binaryTreeNode.getChildNodeRight().toString());
    }
}
