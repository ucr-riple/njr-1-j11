package frs.hotgammon.framework;

/** This class represents an enumeration of colours used in
 * Backgammon.  We need to represent the red or the black colour - but
 * also in some casese neither of the colours, for instance if we ask
 * what colour the checkers have on an empty location on the board.
 * This is represented by the NONE colour.
 
   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Computer Science Department
     Aarhus University
   
   This source code is provided WITHOUT ANY WARRANTY either 
   expressed or implied. You may study, use, modify, and 
   distribute it for non-commercial purposes. For any 
   commercial use, see http://www.baerbak.com/
  */
public enum Color {

  RED(-1),
  NONE(0),
  BLACK(+1);
  
  // we represent color by an underlying numerical value,
  // thus negative value represent red, 0 no color, and
  // positive values represent black.
  private int sign;

  /** private constructor means that no one else but this scope is
   *  allowed to define instances. 
  */
  private Color(int sign) {
    this.sign = sign;
  }

  /** return the numerical value that this colour is defined by
   * @return the numerical value that this colour is defined by
   */
  public final int getSign() {
    return sign;
  }

  /** return the color that a numerical a value represents
   * @param value a numerical value that represents a color
   * @return the color the value represents
   */
  public static Color getColorFromNumerical(int value) {
    if (value < 0)
      return RED;
    if (value > 0)
      return BLACK;
    return NONE;
  }

  /** return the string representation of this color.
      @return string representation
  */
  public String toString() {
    switch (sign) {
      case -1 :
        return "RED";
      case +1 :
        return "BLACK";
      default :
        return "NONE";
    }
  }

}
