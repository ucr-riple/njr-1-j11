package jswang.GoogleInterview.RearrangeStringDistanceAway;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by willwjs on 9/14/15.
 * Had on 9/4 phone interview
 * http://www.geeksforgeeks.org/rearrange-a-string-so-that-all-same-characters-become-at-least-d-distance-away/
 * http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=141400&extra=page%3D1%26filter%3Dsortid%26sortid%3D311%26searchoption%5B3046%5D%5Bvalue%5D%3D1%26searchoption%5B3046%5D%5Btype%5D%3Dradio%26sortid%3D311
 */
public class Solution {
    class StructHelper {
        char c;
        int count;
        StructHelper(char c, int count) {
            this.c = c;
            this.count = count;
        }
    }

    public String rearrangeString(String str, int distance) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : str.toCharArray()) {
            if (map.containsKey(c)) {
                int count = map.get(c);
                map.put(c, ++count);
            } else {
                map.put(c, 1);
            }
        }
        PriorityQueue<StructHelper> maxHeap = new PriorityQueue<>(new Comparator<StructHelper>() {
            @Override
            public int compare(StructHelper o1, StructHelper o2) {
                if (o1.count > o2.count) {
                    return -1;
                } else if (o1.count < o2.count) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            maxHeap.add(new StructHelper(entry.getKey(), entry.getValue()));
        }

        char[] res = new char[str.length()];
        while (!maxHeap.isEmpty()) {
            StructHelper tmp = maxHeap.remove();
            int j = 0;
            while (res[j] != 0) ++j;
            if (j + (tmp.count - 1) * distance >= res.length) return null;
            for (int i = 0; i < tmp.count; ++i) {
                res[j + i * distance] = tmp.c;
            }
        }

        return new String(res);
    }

    public static void main(String[] args) {
        System.out.println(new Solution().rearrangeString("geeksforgeeks", 3));
    }
}