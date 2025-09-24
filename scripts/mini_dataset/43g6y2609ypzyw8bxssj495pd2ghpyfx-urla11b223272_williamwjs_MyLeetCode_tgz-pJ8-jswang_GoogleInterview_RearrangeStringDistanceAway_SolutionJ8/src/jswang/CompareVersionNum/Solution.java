package jswang.CompareVersionNum;

/**
 * Created by willwjs on 2/22/15.
 * 1  There may be multiple "."
 * 2  There may be no ".", where we can't use split
 * 3  There may exist the situation like "1" and "1.0"
 */
public class Solution {
    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        if (v1.length == 0 && v2.length == 0) {
            if (Integer.parseInt(version1) < Integer.parseInt(version2))
                return -1;
            else if (Integer.parseInt(version1) > Integer.parseInt(version2))
                return 1;
            else
                return 0;
        }
        if (v1.length == 0) {
            if (Integer.parseInt(version1) < Integer.parseInt(v2[0]))
                return -1;
            else if (Integer.parseInt(version1) > Integer.parseInt(v2[0]))
                return 1;
            else
                return 0;
        }
        if (v2.length == 0) {
            if (Integer.parseInt(v1[0]) < Integer.parseInt(version2))
                return -1;
            else if (Integer.parseInt(v1[0]) > Integer.parseInt(version2))
                return 1;
            else
                return 0;
        }
        if (v2.length == 0)
            v2[0] = version2;
        int i = 0;
        for (; i < v1.length && i < v2.length; ++i) {
            if (Integer.parseInt(v1[i]) < Integer.parseInt(v2[i]))
                return -1;
            else if (Integer.parseInt(v1[i]) > Integer.parseInt(v2[i]))
                return 1;
        }
        if (i == v1.length && i == v2.length)
            return 0;
        if (i == v1.length) {
            for (; i < v2.length; ++i)
                if (Integer.parseInt(v2[i]) != 0)
                    return -1;
            return 0;
        }
        if (i == v2.length) {
            for (; i < v1.length; ++i)
                if (Integer.parseInt(v1[i]) != 0)
                    return 1;
            return 0;
        }
        return 0;
    }

    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.compareVersion("1.0", "1.1"));
    }
}