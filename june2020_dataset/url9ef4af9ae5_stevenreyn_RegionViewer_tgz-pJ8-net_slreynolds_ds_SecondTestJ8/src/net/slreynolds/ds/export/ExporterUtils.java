package net.slreynolds.ds.export;

import net.slreynolds.ds.model.GraphPoint;
import net.slreynolds.ds.model.Named;
import net.slreynolds.ds.model.Node;
import net.slreynolds.ds.model.NodeArray;

class ExporterUtils {

    static String name(String path) {
        int dotIdx = path.lastIndexOf('.');
        int slashIdx = Math.max(path.lastIndexOf('/'),path.lastIndexOf('\\'));
        if (dotIdx < 0) {
            if (slashIdx < 0) {
                return path;
            }
            return path.substring(slashIdx+1,path.length());
        }
        assert(dotIdx >= 0);
        if (slashIdx < 0) {
            return path.substring(0,dotIdx);
        }
        assert(slashIdx >= 0);
        if (slashIdx < dotIdx) {
            return path.substring(slashIdx+1,dotIdx);
        }
        return path.substring(slashIdx+1,path.length());
    }
    
    static String encloseInQuotes(String arg) {
        return '"' + arg + '"';
    }
    
    static String id(GraphPoint gp) {
    	return String.format("%d", gp.getID());
    }

    static String getIndent(int level) {
        final String indent_1 = "   ";
        switch (level) {
            case 0:
                return "";
            case 1:
                return indent_1;
            default:
                return indent_1 + getIndent(level-1); // TODO kinda slow
        }
    }
}
