package careercup.linkedin;

import java.util.*;

/**
 * Created by Sobercheg on 12/12/13.
 * There are 2 sorted sets.Find the common elements of those sets
 * e.g.
 * A={1,2,3,4,5,6}
 * B={5,6,7,8,9}
 * o/p C={5,6}
 * <p/>
 * Complexity should ne 0(n+m) where n and m is the size of the first and second set respectively
 * <p/>
 * Which data structure should be used to store the output
 */
public class SortedSetsCommonElement {

    List<Integer> getCommonElements(Set<Integer> sortedSet1, Set<Integer> sortedSet2) {
        if (sortedSet1.isEmpty() || sortedSet2.isEmpty()) return new ArrayList<Integer>();
        List<Integer> commonElements = new ArrayList<Integer>();
        Iterator<Integer> set1Iterator = sortedSet1.iterator();
        Iterator<Integer> set2Iterator = sortedSet2.iterator();

        int set1Peek = set1Iterator.next();
        int set2Peek = set2Iterator.next();

        while (set1Iterator.hasNext() && set2Iterator.hasNext()) {
            if (set1Peek == set2Peek) {
                commonElements.add(set1Peek);
                set1Peek = set1Iterator.next();
                set2Peek = set2Iterator.next();
            } else if (set1Peek < set2Peek) {
                set1Peek = set1Iterator.next();
            } else {
                set2Peek = set2Iterator.next();
            }
        }
        if (set1Peek == set2Peek) commonElements.add(set1Peek);
        return commonElements;
    }

    public static void main(String[] args) {
        SortedSetsCommonElement sortedSetsCommonElement = new SortedSetsCommonElement();
        System.out.println(sortedSetsCommonElement.getCommonElements(
                new TreeSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6)),
                new TreeSet<Integer>(Arrays.asList(5, 6, 7, 8, 9))));
    }
}
