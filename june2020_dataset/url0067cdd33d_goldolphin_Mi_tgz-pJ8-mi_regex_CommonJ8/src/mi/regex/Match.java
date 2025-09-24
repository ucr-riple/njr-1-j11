package mi.regex;

/**
 * User: goldolphin
 * Time: 2013-04-07 21:14
 */
public class Match {
    private StringBuilder buffer;
    private int[] groupStart;
    private int[] groupEnd;
    private boolean succeed;

    Match(int groupCount) {
        buffer = new StringBuilder();
        groupStart = new int[groupCount];
        groupEnd = new int[groupCount];
        succeed = false;
    }

    public boolean succeed() {
        return succeed;
    }

    public int groupCount() {
        return groupStart.length;
    }

    public String group(int n) {
        if (succeed()) {
            return buffer.substring(groupStart[n], groupEnd[n]);
        }
        return null;
    }

    void reset() {
        buffer.setLength(0);
        succeed = false;
    }

    void append(char c) {
        buffer.append(c);
    }

    int length() {
        return buffer.length();
    }

    void setLength(int length) {
        buffer.setLength(length);
    }

    char get(int offset) {
        return buffer.charAt(offset);
    }

    void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    void setGroupStart(int n, int start) {
        groupStart[n] = start;
    }

    void setGroupEnd(int n, int end) {
        groupEnd[n] = end;
    }

    int groupStart(int n) {
        return groupStart[n];
    }

    int groupEnd(int n) {
        return groupEnd[n];
    }

    void dump() {
        System.out.println(succeed);
        System.out.println(buffer.toString());
        int groupCount = groupCount();
        for (int i = 0; i < groupCount; i++) {
            System.out.print("group " + i + ": ");
            System.out.println(group(i));
        }
    }
}
