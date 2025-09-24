package minidraw.framework;

import java.awt.Rectangle;

/** A strategy for selecting figures when using the SelectAreaTracker.
 * You can implement a non-default strategy in order to control which
 * figures are selected when rubber band selecting. One example is in
 * board games where you may select a set of checkers but this strategy
 * can remove opponent checkers so a selection operation only select your own.

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

public interface RubberBandSelectionStrategy {

  /** Add figures in 'model' to the model's internal selection. The
      figures that are added to the selection is controlled by
      the 'rubberBandRectangle' (the rubber band the user has
      dragged in the view) and the toggle. PostCondition:
      model's selection is updated with the figures within
      the rubber rectangle (or what ever the strategy decides).
      @param model the drawing where figures should be selected in
      @param rubberBandRectangle the rectangle selected
      @param toggle if true then figures are not added but "toggled"
      in and out of the selection
  */
  public void selectGroup(Drawing model, 
                          Rectangle rubberBandRectangle,
                          boolean toggle);
}
