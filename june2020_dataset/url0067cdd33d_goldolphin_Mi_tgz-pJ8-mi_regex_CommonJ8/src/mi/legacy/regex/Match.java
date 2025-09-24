package mi.legacy.regex;

/**
 * User: goldolphin
 * Time: 2013-04-07 21:14
 */
public class Match {
    private String text;
    private int[] groupStart;
    private int[] groupEnd;
    private boolean succeed;

    // Temporary variables
    private int newOffset;

    Match(String text, int groupCount) {
        this.text = text;
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
        return text.substring(groupStart(n), groupEnd(n));
    }

    void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    void setNewOffset(int newOffset) {
        this.newOffset = newOffset;
    }

    int newOffset() {
        return newOffset;
    }

    char get(int offset) {
        return text.charAt(offset);
    }

    boolean begin(int offset) {
        return offset == 0;
    }

    boolean end(int offset) {
        return offset >= text.length();
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
        System.out.println(text);
        System.out.println(succeed);
        int groupCount = groupCount();
        for (int i = 0; i < groupCount; i++) {
            System.out.print("group " + i + ": ");
            System.out.println(group(i));
        }
    }
}
