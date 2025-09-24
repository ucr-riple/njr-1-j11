/*
   (jCores modification)
  
   The Computer Language Benchmarks Game
   http://shootout.alioth.debian.org/
   contributed by Razii, idea taken from Elliott Hughes and Roger Millington
 */
package benchmarks.benchmarks.regexdna;

import static net.jcores.jre.CoreKeeper.$;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.jcores.jre.interfaces.functions.F1;

public final class SolverJCores2 {

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

    public static String main(InputStream inputStream) {
        final StringBuffer console = new StringBuffer();
        final String s = $(inputStream).text().get(0);
        final String sequence = $(s).replace(">.*\n|\n", "").get(0);

        int initialLength = s.length();
        int codeLength = sequence.length();

        // Little trick so we can print our results in an ordered way
        final Map<String, Integer> result = new ConcurrentHashMap<String, Integer>();
        
        // Produce counts
        $("agggtaaa|tttaccct", 
          "[cgt]gggtaaa|tttaccc[acg]", 
          "a[act]ggtaaa|tttacc[agt]t",
          "ag[act]gtaaa|tttac[agt]ct", 
          "agg[act]taaa|ttta[agt]cct", 
          "aggg[acg]aaa|ttt[cgt]ccct",
          "agggt[cgt]aa|tt[acg]accct", 
          "agggta[cgt]a|t[acg]taccct", 
          "agggtaa[cgt]|[acg]ttaccct")
          .map(new F1<String, String>() {
            public String f(String x) {
                int count = 0;
                Matcher m = Pattern.compile(x).matcher(sequence);
                while (m.find())
                    count++;
                result.put(x, count);
                return x;
            }
        }).forEach(new F1<String, Void>() {
            public Void f(String x) {
                console.append(x + " " + result.get(x) + "\n");
                return null;
            }
        });

        // Rewrite sequence
        final StringBuffer dst = new StringBuffer();
        final Pattern pattern = Pattern.compile("[WYKMSRBDVHN]");
        final Matcher matcher = pattern.matcher(sequence);
        while (matcher.find()) {
            matcher.appendReplacement(dst, "");
            dst.append(replacements.get(matcher.group(0)));
        }
        matcher.appendTail(dst);

        // Output results
        console.append("\n");
        console.append(initialLength + "\n");
        console.append(codeLength + "\n");
        console.append(dst.length() + "\n");

        return console.toString();
    }
}