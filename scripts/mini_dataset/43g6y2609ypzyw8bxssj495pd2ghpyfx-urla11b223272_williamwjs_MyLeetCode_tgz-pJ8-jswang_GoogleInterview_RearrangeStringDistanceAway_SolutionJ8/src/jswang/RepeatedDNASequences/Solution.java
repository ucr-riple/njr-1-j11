package jswang.RepeatedDNASequences;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by willwjs on 5/12/15.
 * Use bit manipulation to save space
 */
public class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        Set<Integer> bitSet = new HashSet<Integer>();
        Set<String> resultSet = new HashSet<String>();
        if (s.length() < 10)
            return new LinkedList<String>(resultSet);
        int key = 0; //A-00; G-01; C-10; T-11
        for (int i = 0; i < 10; ++i) {
            switch (s.charAt(i)) {
                case 'A':
                    break;
                case 'G':
                    key |= (1 << (30 - 2 * i));
                    break;
                case 'C':
                    key |= (2 << (30 - 2 * i));
                    break;
                case 'T':
                    key |= (3 << (30 - 2 * i));
                    break;
            }
        }
        bitSet.add(key);
        for (int i = 10; i < s.length(); ++i) {
            switch (s.charAt(i)) {
                case 'A':
                    key = (key << 2);
                    break;
                case 'G':
                    key = (key << 2) | (1 << 12);
                    break;
                case 'C':
                    key = (key << 2) | (2 << 12);
                    break;
                case 'T':
                    key = (key << 2) | (3 << 12);
                    break;
            }
            if (bitSet.contains(key))
                resultSet.add(s.substring(i - 9, i + 1));
            else
                bitSet.add(key);
        }
        return new LinkedList<String>(resultSet);
    }

    public static void main(String[] args) {
        System.out.println(new Solution().findRepeatedDnaSequences("AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"));
    }
}