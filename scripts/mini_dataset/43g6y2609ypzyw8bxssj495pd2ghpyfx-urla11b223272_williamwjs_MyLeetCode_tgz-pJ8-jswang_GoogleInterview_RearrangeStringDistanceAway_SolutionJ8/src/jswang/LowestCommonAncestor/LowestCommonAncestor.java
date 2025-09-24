package jswang.LowestCommonAncestor;

/**
 * Created by willwjs on 10/13/15.
 */
public class LowestCommonAncestor {

    // ========================= 未优化递归版
    // 递归求公共祖先，Time:O(N)
    public static TreeNode commonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 错误判断，如果有一个点根本不在树内，则报错！
        if(!isIn(root.left, p) || !isIn(root.right, q)) {
            return null;
        }
        return commonAncestorRec(root, p, q);
    }

    public static TreeNode commonAncestorRec(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) {
            return null;
        }
        if(root==p || root==q) {
            return root;
        }

        boolean isPInLeft = isIn(root.left, p);		// 判断p是否在树的左侧
        boolean isQInLeft = isIn(root.left, q);		// 判断q是否在树的左侧

        if(isPInLeft != isQInLeft){			// 如果一个在左侧一个在右侧，则公共祖先必然是root
            return root;
        }

        if(isPInLeft) {		// p，q都在左侧
            return commonAncestorRec(root.left, p, q);
        } else{				// p，q都在右侧
            return commonAncestorRec(root.right, p, q);
        }
    }

    // 判断节点p是否在root树中
    private static boolean isIn(TreeNode root, TreeNode node) {
        if(root == null) {
            return false;
        }
        if(root == node) {
            return true;
        }
        return isIn(root.left, node) || isIn(root.right, node);
    }



    // ========================= 优化递归版，待改动
    public static TreeNode commonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        Result res = commonAncestorRec2(root, p, q);
        if(res.isAncestor) {
            return res.node;
        }
        return null;
    }

    public static Result commonAncestorRec2(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) {
            return new Result(null, false);
        }
        if(root==p && root==q) {
            return new Result(root, true);
        }

        Result leftRes = commonAncestorRec2(root.left, p, q);
        if(leftRes.isAncestor) {		// 在左子树找到公共节点
            return leftRes;
        }

        Result rightRes = commonAncestorRec2(root.right, p, q);
        if(rightRes.isAncestor) {	// 在右子树找到公共节点
            return rightRes;
        }

        if(leftRes.node!=null && rightRes.node!=null) {
            return new Result(root, true);		// root为公共节点
        }
        else if(root==p || root==q) {
            boolean isAncestor = (leftRes.node != null || rightRes.node != null) ? true : false;
            return new Result(root, isAncestor);
        }
        else {
            return new Result(leftRes!=null ? leftRes.node : rightRes.node, false);
        }
    }

    static class Result {
        public TreeNode node;
        public boolean isAncestor;
        public Result(TreeNode n, boolean isAnc) {
            node = n;
            isAncestor = isAnc;
        }
    }
}