package jswang.CombinationSum3;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by willwjs on 9/1/15.
 * 1.Use "new LinkedList<>(list)" to avoid reference-to-the-same-space problem
 * 2.Notice that List could not be cast to LinkedList. Have to use explicit case
 *   But subList can't support this cast even if it is explicit cast
 */
public class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> lists = new LinkedList<>();
        List<Integer> list = new LinkedList<>();
        combinationSum3Helper(lists, list, k, n);
        return lists;
    }

    private List<Integer> combinationSum3Helper(List<List<Integer>> lists, List<Integer> list, int k, int n) {
        if (list.size() == k) {
            if (sumList(list) == n) {
                lists.add(list);
                return new LinkedList<>(list);
            } else
                return list;
        }
        int i = 1;
        if (list.size() != 0) {
            i = list.get(list.size() - 1) + 1;
        }
        for (; i <= 9; ++i) {
            list.add(i);
            list = combinationSum3Helper(lists, list, k, n);
            if (list.size() == k && sumList(list) >= n) {
                list.remove(list.size() - 1);
                break;
            }
            list.remove(list.size() - 1);
        }
        return list;
    }

    private int sumList(List<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(new Solution().combinationSum3(3, 9));
    }
}