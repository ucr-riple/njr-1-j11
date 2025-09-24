package jswang.GoogleInterview.Intervals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by willwjs on 10/13/15.
 */
public class AddInterval implements Intervals {
    private List<Interval> intervals;

    AddInterval() {
        intervals = new LinkedList<>();
    }

    @Override
    public void addInterval(int from, int to) {
        intervals.add(new Interval(from, to));
    }

    @Override
    public int getTotalCoveredLength() {
        Collections.sort(intervals, (i1, i2) -> i1.start > i2.start ? 1 : -1);
        int start = -1, end = -1, cover = 0;
        for (Interval i : intervals) {
            if (start == -1) {
                start = i.start;
                end = i.end;
                continue;
            }
            if (i.start <= end) {
                end = Math.max(i.end, end);
            } else {
                cover += (end - start);
                start = i.start;
                end = i.end;
            }
        }
        if (start != -1) {
            cover += (end - start);
        }
        return cover;
    }

    public static void main(String[] args) {
        AddInterval addInterval = new AddInterval();
        addInterval.addInterval(1, 5);
        addInterval.addInterval(2, 3);
        addInterval.addInterval(4, 7);
        addInterval.addInterval(8, 9);
        System.out.println(addInterval.getTotalCoveredLength());
    }
}
