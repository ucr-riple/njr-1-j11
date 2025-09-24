package frs.hotgammon.framework;

/** This enum represents an enumeration of board locations (the
    "points") in the Backgammon board.

   Checkers that are born off are stored in Location.B_BEAR_OFF (black checkers)
   and Location.R_BEAR_OFF (red checkers).

   Checkers that are in the bar are either in B_BAR or R_BAR depending
   on the checker color: Red checkers are stored in R_BAR until they
   can move onto B1-B6; and Black checkers are stored in B_BAR.

   Two convenience methods are provided to calculate distance between
   two locations; and to find a location given a player color,
   starting location, and a distance.

   Remember that you can iterate through all values using the
   foreach construct:

       for( Location l : Location.values() ) {
        (process l)
       }

 
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
public enum Location {
  // enumerate all ordinary board locations
  B1 ("B1", 24),
  B2 ("B2", 23),
  B3 ("B3", 22),
  B4 ("B4", 21),
  B5 ("B5", 20),
  B6 ("B6", 19),
  B7 ("B7", 18),
  B8 ("B8", 17),
  B9 ("B9", 16),
  B10 ("B10", 15),
  B11 ("B11", 14),
  B12 ("B12", 13),
  
  R1 ("R1", 1),
  R2 ("R2", 2),
  R3 ("R3", 3),
  R4 ("R4", 4),
  R5 ("R5", 5),
  R6 ("R6", 6),
  R7 ("R7", 7),
  R8 ("R8", 8),
  R9 ("R9", 9),
  R10 ("R10", 10),
  R11 ("R11", 11),
  R12 ("R12", 12),
  
  // the bars 
  B_BAR ("B_BAR", 0),
  R_BAR ("R_BAR", 25),
  
  // the bearing off locations
  B_BEAR_OFF ("B_BEAR_OFF", 26),
  R_BEAR_OFF ("R_BEAR_OFF", 27);

  /** the name of a location for printing */
  private final String name;
  /** an index of the location to easy distance calculations */
  private final int index;

  /** a map to allow getting a location given an index */
  private final static Location[] indexToEnum = {
    B_BAR, 
    R1, R2, R3, R4, R5, R6, 
    R7, R8, R9, R10, R11, R12,
    B12, B11, B10, B9, B8, B7,
    B6, B5, B4, B3, B2, B1,
    R_BAR,
    B_BEAR_OFF,
    R_BEAR_OFF };

  /** create the location with the given name and index */
  private Location(String locationName, int index) {
    name = locationName;
    this.index = index;
  }
  
  public String toString() {
    return name;
  }

  /** calculate the distance between two locations. The distance
      function is signed to signal movement direction: it is POSITIVE
      when moving TOWARDS BLACK INNER TABLE and NEGATIVE in the
      opposite direction. 
      Ex. distance(B2,B1) == 1 while distance(B1,B2) == -1.
      The distance measure works correctly when moving a checker
      from the bar, for instance distance(R_BAR,B3) == -3.
      The distance to the bear off location is also calculated
      correctly when moving a checker off the board, ie.
      distance(B4,B_BEAR_OFF) == 4.

      PRECONDITION: Only plausible distances in a backgammon game are
      supported, all other distance calculations are not well
      defined. For instance moving TO the bar, FROM the bear off, or
      from the bar to a e.g. the outer table, is not well defined.
      
    @param from the location to move from
    @param to the location to move to
    @return the distance between from and to, the distance is signed.
  */
  public static int distance(Location from, Location to) {
    if (to == Location.R_BEAR_OFF) {
      // R1 == 1; R2 == 2, etc.
      // Thus the distance from bear off to R2 is -2
      // as red moves away from black inner table
      return -from.index;
    }
    if (to == Location.B_BEAR_OFF) {
      return 25 - from.index;
    }
    int _if = from.index, _it = to.index;
    return _it - _if;
  }

  /** calculate destination location given from location and a distance.
      PRECONDITION: distance is in range 1..6.
      PRECONDITION: from is not a bear off location.
      @param p the color of the player
      @param from the location to move from
      @param distance the distance to move
      @return location where a checker will land given color, from
      location, and a distance.
  */
  public static Location findLocation( Color p,
                                       Location from,
                                       int distance) {
    int _to;
    if (from == Location.B_BAR) {
      _to = distance;
    } else if (from == Location.R_BAR) {
      _to = 25 - distance;
    } else {
      _to = from.index + p.getSign() * distance;
    }

    if (_to <= 0) {
      _to = Location.R_BEAR_OFF.index;
    } else if (_to >= 25) {
      _to = Location.B_BEAR_OFF.index;
    }
    Location to = indexToEnum[_to];
    return to;
  }
}
