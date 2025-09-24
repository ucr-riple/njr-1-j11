/*
   The Computer Language Benchmarks Game
   http://shootout.alioth.debian.org/
   contributed by Razii, idea taken from Elliott Hughes and Roger Millington
 */
package benchmarks.benchmarks.regexdna;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SolverVanillaMT {
    private static final Map<String, String> replacements = new HashMap<String, String>();

    static {

        replacements.put("W", "(a|t)");
        replacements.put("Y", "(c|t)");
        replacements.put("K", "(g|t)");
        replacements.put("M", "(a|c)");
        replacements.put("S", "(c|g)");
        replacements.put("R", "(a|g)");
        replacements.put("B", "(c|g|t)");
        replacements.put("D", "(a|g|t)");
        replacements.put("V", "(a|c|g)");
        replacements.put("H", "(a|c|t)");
        replacements.put("N", "(a|c|g|t)");
    }

    public static String main(InputStream inputStream) throws IOException {
        final StringBuilder ssb = new StringBuilder();

        Reader r = new InputStreamReader(inputStream, "ISO-8859-1");
        StringBuilder sb = new StringBuilder(5100000);
        char[] cbuf = new char[16384];
        int charsRead;
        while ((charsRead = r.read(cbuf)) != -1)
           sb.append(cbuf, 0, charsRead);

        int initialLength = sb.length();

        final String sequence = sb.toString().replaceAll(">.*\n|\n", "");

        int codeLength = sequence.length();

        String[] variants = { "agggtaaa|tttaccct", "[cgt]gggtaaa|tttaccc[acg]",
                "a[act]ggtaaa|tttacc[agt]t", "ag[act]gtaaa|tttac[agt]ct",
                "agg[act]taaa|ttta[agt]cct", "aggg[acg]aaa|ttt[cgt]ccct",
                "agggt[cgt]aa|tt[acg]accct", "agggta[cgt]a|t[acg]taccct",
                "agggtaa[cgt]|[acg]ttaccct" };

        final Map<String, Integer> results = new HashMap<String, Integer>();
        ThreadGroup tg = new ThreadGroup("regexWork");
        for (String v : variants) {
           final String variant = v;
           new Thread(tg, v) {
              @Override
              public void run() {
                 int count = 0;
                 Matcher m = Pattern.compile(variant).matcher(sequence);
                 while (m.find()) {
                    count++;
                 }
                 results.put(variant, count);
              }
           }.start();
        }
        Thread[] threads = new Thread[variants.length];
        tg.enumerate(threads);
        for (Thread t : threads) {
           try {
              if (t != null) {
                 t.join();
              }
           } catch (InterruptedException e) {
              // noop
           }
        }
        tg.destroy();
        for (String variant : variants) {
            ssb.append(variant + " " + results.get(variant) +"\n");
        }
        StringBuffer buf = new StringBuffer();
        Matcher m = Pattern.compile("[WYKMSRBDVHN]").matcher(sequence);
        while (m.find()) {
           m.appendReplacement(buf, "");
           buf.append(replacements.get(m.group()));
        }
        m.appendTail(buf);
        
        ssb.append("\n");
        ssb.append(initialLength + "\n");
        ssb.append(codeLength + "\n");
        ssb.append(buf.length() + "\n");

        return ssb.toString();
    }
}