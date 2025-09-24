/**
 * Copyright 2012 Andy Turner, The University of Leeds, UK
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.ac.leeds.ccg.andyt.generic.visualisation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

/**
 * Holds a reference to a Generic_Canvas for graphics rendering.
 */
public class Generic_Printable implements Printable {

    public Generic_Canvas c;
    
    public Generic_Printable (Generic_Canvas c) {
        this.c = c;
    }
    
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        if (pageIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }

        ((Graphics2D) g).translate(pf.getImageableX(),
                pf.getImageableY());

        // Paint canvas.
        c.paint(g);

        return Printable.PAGE_EXISTS;
    }
}