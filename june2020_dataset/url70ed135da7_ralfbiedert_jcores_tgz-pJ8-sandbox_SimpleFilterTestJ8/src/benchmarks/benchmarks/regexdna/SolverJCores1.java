/*
   The Computer Language Benchmarks Game
   http://shootout.alioth.debian.org/
   contributed by Razii, idea taken from Elliott Hughes and Roger Millington
 */
package benchmarks.benchmarks.regexdna;

import static net.jcores.jre.CoreKeeper.$;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SolverJCores1 {

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

   static abstract class Rewriter {
      private Pattern pattern;
      private Matcher matcher;

      public Rewriter(String regularExpression) {

         this.pattern = Pattern.compile(regularExpression);
      }

      public String group(int i) {
         return matcher.group(i);
      }

      public abstract String replacement();

      public String rewrite(CharSequence original) {
         return rewrite(original, new StringBuffer(original.length())).toString();
      }

      public StringBuffer rewrite(CharSequence original, StringBuffer destination) {
         this.matcher = pattern.matcher(original);
         while (matcher.find()) {
            matcher.appendReplacement(destination, "");
            destination.append(replacement());
         }
         matcher.appendTail(destination);
         return destination;
      }
   }

   public static String main(InputStream inputStream) throws IOException {
      String s = $(inputStream).text().get(0);
      String sequence = $(s).replace(">.*\n|\n", "").get(0);
      
      int initialLength = s.length();
      int codeLength = sequence.length();

      String[] variants = { "agggtaaa|tttaccct" ,
                       "[cgt]gggtaaa|tttaccc[acg]",
                       "a[act]ggtaaa|tttacc[agt]t",
                       "ag[act]gtaaa|tttac[agt]ct",
                       "agg[act]taaa|ttta[agt]cct",
                       "aggg[acg]aaa|ttt[cgt]ccct",
                       "agggt[cgt]aa|tt[acg]accct",
                       "agggta[cgt]a|t[acg]taccct",
                       "agggtaa[cgt]|[acg]ttaccct"
                     };

      
      final StringBuilder ssb = new StringBuilder();
      for (String variant : variants) {

         int count = 0;
         Matcher m = Pattern.compile(variant).matcher(sequence);
         while (m.find())
            count++;
         ssb.append(variant + " " + count + "\n");
      }

      sequence = new Rewriter("[WYKMSRBDVHN]") {

         @Override
        public String replacement() {
            return replacements.get(group(0));
         }
      }.rewrite(sequence);

      ssb.append("\n");
      ssb.append(initialLength + "\n");
      ssb.append(codeLength+ "\n");
      ssb.append(sequence.length() + "\n");
      
      return ssb.toString();
   }
}