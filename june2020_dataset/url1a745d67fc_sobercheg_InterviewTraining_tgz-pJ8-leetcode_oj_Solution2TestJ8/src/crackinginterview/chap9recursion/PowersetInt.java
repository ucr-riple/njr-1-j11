package crackinginterview.chap9recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sobercheg on 11/10/13.
 */
public class PowersetInt {

    public List<List<String>> powerset(List<String> set) {
        int num = 1 << set.size();
        List<List<String>> powerset = new ArrayList<List<String>>();
        for (int i = 0; i < num; i++) {
            powerset.add(convertIntToSubset(set, i));
        }
        return powerset;
    }

    private List<String> convertIntToSubset(List<String> set, int num) {
        List<String> subset = new ArrayList<String>();
        for (int i = 0; num > 0; num >>= 1, i++) {
            if ((num & 1) == 1) subset.add(set.get(i));
        }

        return subset;
    }

    public static void main(String[] args) {
        PowersetInt powerset = new PowersetInt();
        System.out.println(powerset.powerset(Arrays.asList("a", "b", "c", "d")));
    }
}
