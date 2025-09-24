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

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import uk.ac.leeds.ccg.andyt.generic.visualisation.charts.Abstract_Generic_Plot;

public class Generic_Canvas extends Canvas {

    public Generic_Canvas() {
    }
    
    public Abstract_Generic_Plot _Generic_Plot;
    public Graphics2D _Graphics2D;
    public BufferedImage _BufferedImage;
    public Rectangle _Rectangle;

    @Override
    public Graphics getGraphics() {
        return this._Graphics2D;
    }

    public BufferedImage getBufferedImage() {
        return _BufferedImage;
    }

    //public int paintedCounter; 
    /**
     * This is a bit strange as the input Graphics g are ignored!
     * @param g 
     */
    @Override
    public void paint(Graphics g) {
        
        //Rectangle _Rectangle = getBounds();
        _BufferedImage = new BufferedImage(
                _Rectangle.width,
                _Rectangle.height,
                BufferedImage.TYPE_INT_ARGB);
        _Graphics2D = (Graphics2D) _BufferedImage.getGraphics();
        
//        _Graphics2D = (Graphics2D) g;
        
        // draw all components
        Dimension d = _Generic_Plot.draw(_Graphics2D);
        setDimension(d);
        
//        paintedCounter ++;
//        if (paintedCounter ==2) {
//            int debug = 1;
//             Generic_Visualisation.saveImage(
//                    this,
//                    _BufferedImage,
//                    10000,
//                    _Generic_Plot.getFormat(),
//                    _Generic_Plot.getFile());
//        }
        //_Generic_Plot.output();
    }
    
//    @Override
//    public void repaint() {
//        
//    }
    
//    public Dimension paintAndGetNewDimensions(Graphics g) {
//        //Rectangle _Rectangle = getBounds();
//        _BufferedImage = new BufferedImage(
//                _Rectangle.width,
//                _Rectangle.height,
//                BufferedImage.TYPE_INT_ARGB);
//        _Graphics2D = (Graphics2D) _BufferedImage.getGraphics();
//        // draw all components
//        return _Generic_Plot.draw();
//    }
    

    // Override this to do custom drawing
    public void draw() {
        drawOutline();
        _Graphics2D.setFont(new Font("Arial", Font.ITALIC, 12));
        _Graphics2D.drawString("Test", 32, 8);
    }

    public void drawOutline() {
        //Color color = _Graphics2D.getColor();
        _Graphics2D.setPaint(Color.WHITE);
        Rectangle2D rect = new Rectangle2D.Double(
                getX(), 
                getY(), 
                getWidth() - 1, 
                getHeight() - 1);
//        _Graphics2D.fillRect(
//                getX(), 
//                getY(), 
//                (2 * getWidth()) - 1, 
//                (2 * getHeight()) - 1);
        _Graphics2D.fillRect(
                getX(), 
                getY(), 
                getWidth() - 1, 
                getHeight() - 1);
        _Graphics2D.setPaint(Color.DARK_GRAY);
        _Graphics2D.draw(rect);
        //setPaint(color);
    }
    
    public void setDimension(Dimension d) {
        _Rectangle = new Rectangle(d);
    }
}
