package fw.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fw.Log;

public class RegExpMatcher implements fw.Matcher<String> {

  private final Pattern _pattern;

  public RegExpMatcher(final String pattern) {
    _pattern = Pattern.compile(pattern);
  }

  @Override
  public boolean evaluate(final String value) {
    final Matcher matcher = _pattern.matcher(value);
    return matcher.find();
  }

  @Override
  public void printSuccess(final String value) {
    Log.success(patternFound(value));
  }

  @Override
  public void raiseFailure(final String value) {
    Log.error(patternNotFound(value));
  }

  @Override
  public void printNegationSuccess(final String value) {
    Log.success(patternNotFound(value));
  }

  @Override
  public void raiseNegationFailure(final String value) {
    Log.error(patternFound(value));
  }

  private String patternFound(final String value) {
    final StringBuilder details = new StringBuilder("Pattern /" + _pattern.pattern() + "/ found in \\" + value + "\\ [ ");

    final Matcher matcher = _pattern.matcher(value);
    if (matcher.find()) {
      do {
        details.append("\\" + matcher.group() + "\\ ");
      } while (matcher.find());
    }

    details.append("]");

    return details.toString();
  }

  private String patternNotFound(final String value) {
    return "Pattern /" + _pattern.pattern() + "/ not found in \\" + value + "\\";
  }

}
