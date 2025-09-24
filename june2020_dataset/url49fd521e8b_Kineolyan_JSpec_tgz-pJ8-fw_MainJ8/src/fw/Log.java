package fw;

public final class Log {

  public static class AssertionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AssertionException(final String message) {
      super(message);
    }

  }

  public static void success(final String value, final Object... args) {
    System.out.printf("\033[32m" + value + "\033[0m%n", args);
  }

  public static void error(final String value, final Object... args) {
    System.out.printf("\033[31m" + value + "\031[0m%n", args);
    throw new AssertionException(String.format(value, args));
  }

}
