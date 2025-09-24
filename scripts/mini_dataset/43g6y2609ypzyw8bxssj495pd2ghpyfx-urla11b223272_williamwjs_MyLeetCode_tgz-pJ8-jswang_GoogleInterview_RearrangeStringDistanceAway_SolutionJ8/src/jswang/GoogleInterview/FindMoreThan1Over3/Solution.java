package jswang.GoogleInterview.FindMoreThan1Over3;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by willwjs on 10/11/15.
 */
public class Solution {
    public int findMoreThan1OverN(int[] arr, int N) {
        Map<Integer, Integer> map = new HashMap<>(N - 1);
        for (int i : arr) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                if (map.size() == N - 1) {
                    for (Map.Entry<Integer, Integer> e : map.entrySet()) {
                        if (e.getValue() == 1) {
                            map.remove(e.getKey());
                        } else {
                            map.put(e.getKey(), e.getValue() - 1);
                        }
                    }
                } else {
                    map.put(i, 1);
                }
            }
            /*if (map.size() == N - 1) {
                List<Integer> removeList = new LinkedList<>();
                for (Map.Entry<Integer, Integer> e : map.entrySet()) {
                    if (e.getValue() == 1) {
                        removeList.add(e.getKey());
                    } else {
                        map.put(e.getKey(), e.getValue() - 1);
                    }
                }
                for (int r : removeList) {
                    map.remove(r);
                }
            } else {
                if (map.containsKey(i)) {
                    map.put(i, map.get(i) + 1);
                } else {
                    map.put(i, 1);
                }
            }*/
        }

        int res = 0;
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            res = e.getKey();
            break;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().findMoreThan1OverN(new int[] {1, 1, 2, 2, 2, 3}, 3));
    }
}
