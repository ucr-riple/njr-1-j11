package jswang.ValidNumber;

/**
 * Created by willwjs on 1/15/15.
 */
public class Solution {
    public boolean isNumber(String s) {
        if (s.matches(" *\\+{0,1}-{0,1}[0-9]*\\.{0,1}[0-9]*e{0,1}\\+{0,1}-{0,1}[0-9]* *")
                && !s.matches(" *\\+{0,1}-{0,1}\\. *")
                && !s.matches(" *\\+{0,1}-{0,1}[0-9]*\\.{2,}.*")
                && !s.matches(" *\\+{0,1}-{0,1}[0-9]*\\.[0-9]+\\..*")
                && !s.matches(" *\\+{0,1}-{0,1}e.*")
                && !s.matches(" *\\+{0,1}-{0,1}\\.e.*")
                && !s.matches(" *")
                && !s.matches(" *\\+-.*")
                && !s.matches(" *-\\+.*")
                && !s.matches(" *\\+{0,1}-{0,1}[0-9]*\\.{0,1}[0-9]*e{0,1}\\+-.*")
                && !s.matches(" *\\+{0,1}-{0,1}[0-9]*\\.{0,1}[0-9]*e{0,1}-\\+.*")
                && !s.matches(" *\\+{0,1}-{0,1}[0-9]*\\.[0-9]*\\+.*")
                && !s.matches(" *\\+{0,1}-{0,1}[0-9]*\\.[0-9]*-.*")
                && !s.matches(" *\\+{0,1}-{0,1}[0-9]+\\+.*")
                && !s.matches(" *\\+{0,1}-{0,1}[0-9]+-.*")
                && !s.matches(" *\\+{0,1}-{0,1}[0-9]*\\.{0,1}[0-9]*e\\.{0,1} *")
                && !s.matches(" *\\+{0,1}-{0,1}[0-9]*\\.{0,1}[0-9]*e\\+ *")
                && !s.matches(" *\\+{0,1}-{0,1}[0-9]*\\.{0,1}[0-9]*e- *"))
            return true;
        return false;
    }

    public static void main (String[] args) {
        Solution sol = new Solution();
        System.out.print(sol.isNumber("-1."));
    }
}