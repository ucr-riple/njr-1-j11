package Mathtools;

/** Class Interval defines the standard mathematical closed interval in one dimension.
 */
public class Interval extends Object {
  
  /** lowerBound and upperBound both belong to the interval
  * empty indicates whether or not the interval is empty
  */
  private double lowerBound;
  private double upperBound;
  private boolean empty;

  /** Creates new Interval [0,1]
   */
  public Interval() {
    lowerBound = 0.0;
    upperBound = 1.0;
    empty = false;
  }

  /** Creates new Interval with given lower and upper bound.
   * If the lowerbound is larger than the upperbound, the interval is empty.
   * @param lower lowerbound of interval
   * @param upper upperbound of interval
   */
  public Interval(double lower,double upper) {
    this.lowerBound = lower;
    this.upperBound = upper;
    if (upper < lower) empty = true;
    else empty = false;
  }

  /** Checks whether value t is within interval bounds.
   * Boolean value is returned.
   * @param t value to check for inclusion in interval
   * @return true if t belongs to interval, false otherwise
   */
  public boolean contains(double t) {
    boolean tmp;

    if (this.empty) tmp = false;
    else tmp = (t>=this.lowerBound)&&(t<=this.upperBound);
    return(tmp);
  }

  /** Check whether 2 intervals overlap.
   * @param i interval to test against current interval
   * @return true if the 2 intervals overlap, false otherwise.
   */
  public boolean overlaps(Interval i) {
    boolean tmp;

    if ((this.empty)||(i.empty)) return (false);
    tmp = (i.lowerBound >= this.lowerBound)&&(i.lowerBound<=this.upperBound);
    tmp = tmp || ((i.upperBound <= this.upperBound)&&(i.upperBound >= this.lowerBound));

    return(tmp);
  }

  /** 2 intervals are intersected with each other, and the resulting interval is returned.
   * @param i Interval to be intersected with current interval
   * @return New interval, which is the intersection of 2 given intervals.
   * If there is no intersection, resulting interval is empty.
   */
  public Interval intersect(Interval i) {
    Interval resultingInterval = new Interval();

    if ((this.empty)||(i.empty)) {
      resultingInterval.empty = true;
      return(resultingInterval);
    }

    if (this.contains(i.lowerBound)) {
      resultingInterval.lowerBound = this.lowerBound;
      resultingInterval.upperBound = Math.max(this.upperBound,i.upperBound);
      resultingInterval.empty = false;
    }
    else if (this.contains(i.upperBound)) {
      resultingInterval.lowerBound = Math.min(this.lowerBound, i.lowerBound);
      resultingInterval.upperBound = this.upperBound;
      resultingInterval.empty = false;
    }
    else {
      resultingInterval.empty = true;
    }

    return(resultingInterval);
  }

  /** Getter for property lowerBound.
   * @return Value of property lowerBound.
   */
  public double getLowerBound() {
    return(lowerBound);
  }

  /** Setter for property lowerBound.
   * @param lowerBound New value of property lowerBound.
   */
  public void setLowerBound(double lowerBound) {
    this.lowerBound = lowerBound;
  }

  /** Getter for property upperBound.
   * @return Value of property upperBound.
   */
  public double getUpperBound() {
    return(upperBound);
  }

  /** Setter for property upperBound.
   * @param upperBound New value of property upperBound.
   */
  public void setUpperBound(double upperBound) {
    this.upperBound = upperBound;
  }

  /** Copy current interval
   * @return copy of object
   */
  public Interval makeCopy() {
    Interval tmp = new Interval(this.getLowerBound(),this.getUpperBound());
    return(tmp);
  }

  /** Print out values of interval, followed by newline
   */
  public void printlnInterval() {
    System.out.println("Interval: [ " + this.getLowerBound() + " , " + this.getUpperBound()+" ]");
  }


}