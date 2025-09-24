package jswang.LowestCommonAncestor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by willwjs on 10/13/15.
 */
public class LowestCommonAncestorWithParentPointer {
    public TreeNodeWithParent lowestCommonAncestor(TreeNodeWithParent root, TreeNodeWithParent p, TreeNodeWithParent q) {
        Set<TreeNodeWithParent> routep = new HashSet<>();
        TreeNodeWithParent cur = p;
        while (cur != null) {
            if (cur == q) return q;
            routep.add(cur);
            cur = cur.parent;
        }

        cur = q;
        while (cur != null) {
            if (routep.contains(cur)) return cur;
            cur = cur.parent;
        }

        throw new RuntimeException();
    }
}