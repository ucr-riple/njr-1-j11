/*
 Copyright (C) 2012 William James Dyce

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package wjd.gui;

import wjd.amb.AmbitionEngine;
import wjd.math.V2;

/**
 * Launches the Graphical User Interface for our Multi-agent simulation.
 *
 * @author wdyce
 * @since Sep 27, 2012
 */
abstract class Main
{
  /* CONSTANTS */
  
  private static final String WIN_NAME = "Mult-agent System";
  private static final V2 WIN_SIZE = new V2(640, 480);
  
  /* PROGRAM ENTRANCE POINT */
  
  public static void main(String[] args)
  {
    /* NB - LWJGL uses native libraries, so this program will crash at run-time
     * unless you indicate to the JVM where to find them! As such the program
     * must be run with the following argument: 
     * -Djava.library.path=/a/path/to/lwjgl-2.8.4/native/your_operating_system
     */
    AmbitionEngine.launch(WIN_NAME, WIN_SIZE, new AgentScene());
  }
}
