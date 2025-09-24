package crackinginterview.chap9recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sobercheg on 11/10/13.
 */
public class PowersetRecursive {

    public List<List<String>> powerset(List<String> set, int index) {
        if (index == -1) {
            List<List<String>> powerset = new ArrayList<List<String>>();
            powerset.add(new ArrayList<String>());
            return powerset;
        }

        List<List<String>> newSubsets = new ArrayList<List<String>>();
        List<List<String>> prevPowerset = powerset(set, index - 1);

        for (List<String> subset : prevPowerset) {
            List<String> newSubset = new ArrayList<String>();
            newSubset.addAll(subset);
            newSubset.add(set.get(index));
            newSubsets.add(newSubset);
        }
        prevPowerset.addAll(newSubsets);
        return prevPowerset;
    }

    public static void main(String[] args) {
        PowersetRecursive powerset = new PowersetRecursive();
        List<String> set = Arrays.asList(new String[]{"a", "b", "c"});
        System.out.println(powerset.powerset(set, set.size() - 1));
    }
}
