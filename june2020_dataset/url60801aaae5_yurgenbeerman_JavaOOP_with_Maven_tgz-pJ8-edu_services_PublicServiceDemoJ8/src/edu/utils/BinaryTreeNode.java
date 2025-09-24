package edu.utils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yurii.pyvovarenko on 22.03.14.
 */
public class BinaryTreeNode {
    String data;
    BinaryTreeNode childNodeRight;
    BinaryTreeNode childNodeLeft;
    int parentsCount = 0;

    public BinaryTreeNode(String data) {
        if (data != null) {
            this.data = new String(data);
            childNodeRight = null;
            childNodeLeft = null;
        }
        else {
            throw new InvalidParameterException("Parameter cannot be null");
        }
    }

    public void addNode(String data) {
        if (data != null) {
            if (data.hashCode() < this.data.hashCode()) {
                if (getChildNodeLeft() == null) {
                    this.childNodeLeft = new BinaryTreeNode(data);
                    this.childNodeLeft.setParentsCount(this.parentsCount + 1);
                } else
                    this.childNodeLeft.addNode(data);
            } else {
                if (getChildNodeRight() == null) {
                    this.childNodeRight = new BinaryTreeNode(data);
                    this.childNodeRight.setParentsCount(this.parentsCount + 1);
                } else
                    this.childNodeRight.addNode(data);
            }
        }
    }

    public int getHeight() {
        int leftHeight = (childNodeLeft != null) ? childNodeLeft.getHeight() : 0;
        int maxHeight = (childNodeRight != null) ? childNodeRight.getHeight() : 0;
        if ( leftHeight > maxHeight)
            maxHeight = leftHeight;
        return 1 + maxHeight;
    }

    public int size() {
        return 1 +
                ((childNodeLeft != null) ? childNodeLeft.size() : 0) +
                ((childNodeRight != null) ? childNodeRight.size() : 0);
    }

    public String toString() {
        String indent = "";
        for (int i = 0; i < this.parentsCount; i++)
            indent = indent + " ";
        return indent + this.data +
               ((childNodeLeft != null) ? "\n" + childNodeLeft.toString() : "") +
               ((childNodeRight != null) ? "\n" + childNodeRight.toString() : "");
    }

    public BinaryTreeNode getChildNodeRight() {
        return childNodeRight;
    }

    public BinaryTreeNode getChildNodeLeft() {
        return childNodeLeft;
    }

    public void setParentsCount(int parentsCount) {
        this.parentsCount = parentsCount;
    }
}
