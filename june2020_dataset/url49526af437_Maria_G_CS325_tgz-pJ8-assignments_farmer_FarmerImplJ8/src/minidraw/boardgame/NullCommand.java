package minidraw.boardgame;

/** A null object implementation of Command. All
 * executions does nothing and return true.

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
public class NullCommand implements Command {
  public boolean execute() {
    return true;
  }
  public void setFromCoordinates(int fromX, int fromY) {
  }
  public void setToCoordinates(int toX, int toY) {
  }
}
