package com.github.users.schlabberdog.blocks.mccs;

public class Coord {
    public final int x;
    public final int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coord) {
            Coord o = (Coord) obj;
            return (x == o.x && y == o.y);
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "C("+x+"|"+y+")";
    }
}
