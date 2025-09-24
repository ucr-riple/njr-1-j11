package Color;

/** Class RGBColor describes a triplet of RGB values.
 * It is a standard representation, and makes no specific assumptions about spectral characteristics.
 * All methods happen in RGB space.
 */
public class RGBColor extends Object {
  public double r;

  public double g;

  public double b;

  /** Creates new RGBColor, default black (0,0,0)*/
  public RGBColor() {
    this.r = 0.0;
    this.g = 0.0;
    this.b = 0.0;
  }

  /** Creates new RGBColor from given parameter values
   * @param r value for red
   * @param g value for green
   * @param b value for blue
   */
  public RGBColor(double r,double g,double b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /** Add 2 RGBcolors together and create a new resulting RGBColor
   * @param c 1st color
   * @param d 2nd color
   * @return new RGBColor object, sum of two parameters (c+d)
   */
  public static RGBColor add(RGBColor c,RGBColor d) {
    RGBColor sum = new RGBColor();
    sum.r = d.r + c.r;
    sum.g = d.g + c.g;
    sum.b = d.b + c.b;
    return(sum);
  }

  /** Subtract 2 RGBcolors from each other
   * @param c 1st color
   * @param d 2nd color
   * @return new RGBColor object, difference of two parameters (c-d)
   */
  public static RGBColor subtract(RGBColor c,RGBColor d) {
    RGBColor diff = new RGBColor();
    diff.r = c.r - d.r;
    diff.g = c.g - d.g;
    diff.b = c.b - d.b;
    return(diff);
  }
  /** Multiply 2 RGBcolors together and create a new resulting RGBColor
   * The corresponding components are multiplied with each other
   * @param c 1st color
   * @param d 2nd color
   * @return new RGBColor object, sum of two parameters (c*d)
   */
  public static RGBColor multiply(RGBColor c,RGBColor d) {
    RGBColor mult = new RGBColor();
    mult.r = d.r * c.r;
    mult.g = d.g * c.g;
    mult.b = d.b * c.b;
    return(mult);
  }

  /** Multiply color with a scalar, results in a new RGBColor object
   * @param s scalar
   * @return new RGBColor = this color *s
   */
  public RGBColor scale(double s) {
    RGBColor mult = new RGBColor();
    mult.r = this.r * s;
    mult.g = this.g * s;
    mult.b = this.b * s;
    return(mult);
  }

  /** Multiply this color with a scalar
   * @param s scalar
   */
  public void scaleSelf(double s) {
    this.r *= s;
    this.g *= s;
    this.b *= s;

  }

  /** Divide color by a scalar, and create new RGBColor as a result
   * @param s scalar
   * @return new RGBCOlor = this color / s
   */
  public RGBColor scaleInverse(double s) {
    RGBColor div = new RGBColor();
    div.r = this.r / s;
    div.g = this.g / s;
    div.b = this.b / s;
    return(div);
  }

  /** Print out color values, followed by newline
   */
  public void printlnRGBColor() {
    System.out.println("Color comps: "+this.r+" -- "+this.g+" -- "+this.b);
  }

  /** Make a copy of the current color
   * @return new object, copy of current one
   */
  public RGBColor makeCopy() {
    RGBColor tmp = new RGBColor(this.r, this.g, this.b);
    return(tmp);
  }

  /** Make a linear combination of given scalars and colors.
   * Resulting color = a*color1 + b*color2
   * @param a weight for color1
   * @param color1 1st color
   * @param b weight for color 2
   * @param color2 2nd color
   * @return resulting color, new object
   */
  public static RGBColor linearCombination(double a,RGBColor color1,double b,RGBColor color2) {
    RGBColor tmp = new RGBColor();
    tmp.r = a*color1.r + b*color2.r;
    tmp.g = a*color1.g + b*color2.g;
    tmp.b = a*color1.b + b*color2.b;
    return tmp;
  }

  /** Make a linear combination of given scalars and colors.
   * Resulting color = a*color1 + b*color2 + c*color3
   * @param a weight for color1
   * @param color1 1st color
   * @param b weight for color 2
   * @param color2 2nd color
   * @param c weight for color 2
   * @param color3 3nd color
   * @return resulting color, new object
   */
  public static RGBColor linearCombination(double a,RGBColor color1,double b,RGBColor color2,double c,RGBColor color3) {
    RGBColor tmp = new RGBColor();
    tmp.r = a*color1.r + b*color2.r + c*color3.r;
    tmp.g = a*color1.g + b*color2.g + c*color3.g;
    tmp.b = a*color1.b + b*color2.b + c*color3.b;
    return tmp;
  }
  /** Make a linear combination of given scalars and colors.
   * Resulting color = color1 + b*color2
   * @param color1 1st color
   * @param b weight for color 2
   * @param color2 2nd color
   * @return resulting color, new object
   */
  public static RGBColor linearCombination(RGBColor color1,double b,RGBColor color2) {
    RGBColor tmp = new RGBColor();
    tmp.r = color1.r + b*color2.r;
    tmp.g = color1.g + b*color2.g;
    tmp.b = color1.b + b*color2.b;
    return tmp;
  }
  /** Make a linear combination of given scalars and colors.
   * Resulting color = a*color1 + color2
   * @param a weight for color1
   * @param color1 1st color
   * @param color2 2nd color
   * @return resulting color, new object
   */
  public static RGBColor linearCombination(double a,RGBColor color1,RGBColor color2) {
    RGBColor tmp = new RGBColor();
    tmp.r = a*color1.r + color2.r;
    tmp.g = a*color1.g + color2.g;
    tmp.b = a*color1.b + color2.b;
    return tmp;
  }
  
  public void clampZero() {
    if (this.r < 0.0) this.r = 0.0;
    if (this.g < 0.0) this.g = 0.0;
    if (this.b < 0.0) this.b = 0.0;
  }
  
  public void absoluteValue() {
    this.r = Math.abs(this.r);
    this.g = Math.abs(this.g);
    this.b = Math.abs(this.b);
  }
  
  public double sumOfComponents() {
    return(this.r+this.g+this.b);
  }


}