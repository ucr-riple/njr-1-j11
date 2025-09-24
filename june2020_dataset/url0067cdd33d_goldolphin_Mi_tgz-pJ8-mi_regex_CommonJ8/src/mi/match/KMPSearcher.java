package mi.match;

import mi.common.Utils;
import mi.stream.ICharStream;

import java.util.Arrays;

/**
 * User: goldolphin
 * Time: 2013-06-12 11:30
 */
public class KMPSearcher {
    private final char[] pattern;
    private final int[] fail;

    public KMPSearcher(String pattern) {
        this.pattern = pattern.toCharArray();
        fail = new int[this.pattern.length-1];
        for (int i = 0; i < fail.length; i ++) {
            if (i == 0) {
                fail[i] = -1;
                continue;
            }
            char c = this.pattern[i];
            for (int f = fail[i-1]; ; ) {
                if (c == this.pattern[f+1]) {
                    fail[i] = f+1;
                    break;
                } else if (f == -1) {
                    fail[i] = -1;
                    break;
                } else {
                    f = fail[f];
                }
            }
        }
    }

    public boolean search(ICharStream stream) {
        int i = 0;
        while (true) {
            if (i >= pattern.length) {
                return true;
            }
            char c = stream.peek();
            if (c == ICharStream.EOF) {
                return false;
            }
            if (c == pattern[i]) {
                i ++;
                stream.poll();
            } else if (i == 0) {
                stream.poll();
            } else {
                i = fail[i-1]+1;
            }
        }
    }

    public boolean search(String source) {
        int i = 0;
        for (int pos = 0;;) {
            if (i >= pattern.length) {
                return true;
            }
            if (pos >= source.length()) {
                return false;
            }
            char c = source.charAt(pos);
            if (c == pattern[i]) {
                i ++;
                pos ++;
            } else if (i == 0) {
                pos ++;
            } else {
                i = fail[i-1]+1;
            }
        }
    }

    public static void main(String[] args) {
        String p = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        KMPSearcher kmp = new KMPSearcher(p);
        System.out.println(kmp.search(s));
        System.out.println(kmp.search("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));

        long begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i ++) {
            Utils.verify(kmp.search(s));
        }
        System.out.println(System.currentTimeMillis() - begin);

        begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i ++) {
            Utils.verify(s.indexOf(p) > 0);
        }
        System.out.println(System.currentTimeMillis() - begin);
    }
}
