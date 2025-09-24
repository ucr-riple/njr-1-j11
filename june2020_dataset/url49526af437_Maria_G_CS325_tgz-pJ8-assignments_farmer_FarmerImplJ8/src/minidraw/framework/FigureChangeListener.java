package minidraw.framework;

import java.util.EventListener;

/**
 * Listener interested in Figure changes.
 *

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
public interface FigureChangeListener extends EventListener {

  /**
   *  Sent when an area is invalid
   */
  public void figureInvalidated(FigureChangeEvent e);
  
  /**
   * Sent when a figure changed
   */
  public void figureChanged(FigureChangeEvent e);
  
  /**
   * Sent when a figure was removed
   */
  public void figureRemoved(FigureChangeEvent e);
  
  /**
   * Sent when requesting to remove a figure.
   */
  public void figureRequestRemove(FigureChangeEvent e);
  
  /**
   * Sent when an update should happen.
   *
   */
  public void figureRequestUpdate(FigureChangeEvent e);
}
