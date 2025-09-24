package jswang.GoogleInterview.Intervals;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by willwjs on 10/13/15.
 */

public class RemoveInterval {
    public List<Interval> remove(List<Interval> intervals, Interval removeInterval) {
        Collections.sort(intervals, (i1, i2) -> i1.start > i2.start ? 1 : -1);
        List<Interval> res = new LinkedList<>();
        boolean needDo = true;
        int start = removeInterval.start, end = removeInterval.end;
        for (Interval i : intervals) {
            if (start >= i.end || !needDo) {
                res.add(i);
                continue;
            }
            if (start > i.start) {
                res.add(new Interval(i.start, start));
            }
            if (end >= i.start && end < i.end) {
                res.add(new Interval(end, i.end));
                continue;
            }
            if (end < i.start) {
                res.add(i);
                needDo = false;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<Interval> intervals = new LinkedList<>();
        intervals.add(new Interval(1, 2));
        intervals.add(new Interval(4, 5));
        intervals.add(new Interval(6, 7));
        intervals.add(new Interval(2, 6));
        intervals.add(new Interval(1, 5));
        intervals.add(new Interval(6, 8));
        intervals.add(new Interval(3, 9));
        Interval newInterval = new Interval(3, 6);
        List<Interval> res = new RemoveInterval().remove(intervals, newInterval);
        System.out.println(res);
    }
}
