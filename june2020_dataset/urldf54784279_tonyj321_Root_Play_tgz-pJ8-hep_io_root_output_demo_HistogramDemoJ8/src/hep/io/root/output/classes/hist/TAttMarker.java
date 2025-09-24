package hep.io.root.output.classes.hist;

import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.Title;

/**
 * Marker Attributes class.
 * <a href="http://root.cern.ch/root/htmldoc/TAttMarker.html">TAttMarker</a>
 * @author tonyj
 */
@ClassDef(version = 2, checkSum = -87219836)
@Title("Marker Attributes")
public class TAttMarker {
    @Title("Marker color index")
    private short fMarkerColor = 1;
    @Title("Marker style")
    private short fMarkerStyle = 1;
    @Title("Marker size")
    private float fMarkerSize = 1;   
}
