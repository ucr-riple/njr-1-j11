package hep.io.root.output.classes;

import hep.io.root.output.annotations.ClassDef;
import hep.io.root.output.annotations.Title;

/**
 * The TNamed class is the base class for all named ROOT classes.
 *
 * @see <a href="http://root.cern.ch/root/htmldoc/TNamed.html">TNamed</a>
 * @author tonyj
 */
@ClassDef(version = 1, checkSum = -68599943)
@Title("The basis for a named object (name, title)")
public class TNamed extends TObject {

    @Title("object identifier")
    String name;
    @Title("object title")
    String title;

    public TNamed(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }
}
