
public class Timer extends Object {

  static long timeMilliseconds;
  
  static void startTime () {
      timeMilliseconds = System.currentTimeMillis();
  }
  
  static double endTime () {
    long executionTime;
    executionTime = System.currentTimeMillis() - timeMilliseconds;
    return((double)executionTime/1000.0);
  }
  
}