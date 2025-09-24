package fw;

public class Main {

  /**
   * @param args
   */
  public static void main(final String[] args) {
    try {
      new Test().run();
    } catch (final Log.AssertionException e) {
      e.printStackTrace();
    }
  }
}
