package jswang.Candy;

/**
 * Created by willwjs on 11/17/14.
 * 两次扫描，算法设计的很好
 */
public class Solution {
    public int candy(int[] ratings) {
        int[] eachCandy = new int[ratings.length];

        for (int i = 1, num = 1; i < ratings.length; ++i) {
            if (ratings[i] > ratings[i - 1])
                eachCandy[i] = num > eachCandy[i] ? num++ : eachCandy[i];
            else
                num = 1;
        }

        for (int i = ratings.length - 2, num = 1; i >= 0; --i) {
            if (ratings[i] > ratings[i + 1])
                eachCandy[i] = num > eachCandy[i] ? num++ : eachCandy[i];
            else
                num = 1;
        }

        int res = ratings.length;
        for (int i : eachCandy)
            res += i;

        return res;

        /*与上面同样道理，但本方法难理解
        int[] eachCandy = new int[ratings.length];
        //eachCandy[0] = eachCandy[ratings.length - 1] = 1;

        for (int i = 1, num = 1; i < ratings.length; ++i) {
            if (ratings[i] > ratings[i - 1])
                if (num > eachCandy[i])
                    eachCandy[i] = num++;
            else if (ratings[i] == ratings[i - 1]) //
                if (num - 1 > eachCandy[i])
                    eachCandy[i] = num - 1;
            else
                num = 1;
        }

        for (int i = ratings.length - 2, num = 1; i >= 0; --i) {
            if (ratings[i] > ratings[i + 1])
                if (num > eachCandy[i])
                    eachCandy[i] = num++;
            else if (ratings[i] == ratings[i + 1]) //
                if (num - 1 == eachCandy[i])
                    eachCandy[i] = num - 1;
            else
                num = 1;
        }

        int total = ratings.length;
        for (int i : eachCandy)
            total += i;
        return total;*/

        /*//超时
        int totalNum = 1, each[] = new int[ratings.length];
        each[0] = 1;
        for (int i = 1; i < ratings.length; ++i) {
            if (ratings[i] > ratings[i - 1]) {
                each[i] = each[i - 1] + 1;
            }
            else {
                each[i] = 1;
                for (int j = i; j > 0 && each[j - 1] == each[j]; --j) {
                    ++each[j - 1];
                    ++totalNum;
                }
            }
            totalNum += each[i];
        }
        return totalNum;*/
    }

    public  static void main (String[] args) {
        Solution sol = new Solution();
        int[] ratings = {1, 2, 3, 3, 3, 3, 1};
        System.out.println(sol.candy(ratings));
    }
}
