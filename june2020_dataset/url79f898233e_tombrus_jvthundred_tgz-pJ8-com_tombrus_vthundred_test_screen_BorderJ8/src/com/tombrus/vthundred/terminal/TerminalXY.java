
package com.tombrus.vthundred.terminal;

public class TerminalXY {
    private final int x;
    private final int y;

    public TerminalXY (int x, int y) {
        if (x <0) {
            throw new IllegalArgumentException("TerminalXY.x cannot be less than 0");
        }
        if (y <0) {
            throw new IllegalArgumentException("TerminalXY.y cannot be less than 0");
        }
        this.x = x;
        this.y = y;
    }

    public TerminalXY (TerminalXY terminalSize) {
        this(terminalSize.getX(), terminalSize.getY());
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    @Override
    public String toString () {
        return "{x="+x +",y=" +y +"}";
    }
    
    public int compare(TerminalXY o) {
        final int oy = o.getY();
        if (y<oy) {
            return -1;
        } else if (y!=oy) {
            return 1;
        } else {
            final int ox = o.getX();
            return x<ox ? -1 : x==ox ? 0 : 1;
        }
    }

    @Override
    public boolean equals (Object obj) {
        if (!(obj instanceof TerminalXY)) {
            return false;
        }
        TerminalXY other = (TerminalXY) obj;
        return x ==other.x &&
               y ==other.y;
    }

    @Override
    public int hashCode () {
        int hash = 5;
        hash = 53 *hash +this.x;
        hash = 53 *hash +this.y;
        return hash;
    }
}
