package hep.io.root.output.classes.hist;

import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.Title;

/**
 * Line Attributes class.
 * <a href="http://root.cern.ch/root/htmldoc/TAttLine.html">TAttLine</a>
 * @author tonyj
 */
@ClassDef(version = 1, checkSum = 1369587346)
@Title("Line Attributes")
public class TAttLine {
    @Title("line color")
    private short fLineColor = 1;
    @Title("line style")
    private short fLineStyle = 1;
    @Title("line width")
    private short fLineWidth = 1;  
}
