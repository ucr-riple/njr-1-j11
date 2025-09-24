package com.github.users.schlabberdog.blocks.mccs;

public class Rect {
    public final Coord origin;
    public final Size size;

    public Rect(Coord origin, Size size) {
        this.origin = origin;
        this.size = size;
    }

	public Rect(Coord origin, int w, int h) {
		this(origin,new Size(w,h));
	}

	public Rect(int x, int y, Size size) {
		this(new Coord(x,y),size);
	}

    public Rect(int x,int y,int w,int h) {
        this(new Coord(x,y),new Size(w,h));
    }

    /**
     * Bestimmt die Schnittmenge zwischen zwei Rechtecken
     * @param a
     * @param b
     * @return Die Schnittmenge oder null, wenn sie leer ist
     */
    public static Rect intersection(Rect a,Rect b) {
        //im einfachsten fall sind sie identisch, dann ist die schnittmenge auch identisch
        if(a.equals(b))
            return a.copy();
        //der linke rand der schnittmenge ist der linke rand von A und B, der weiter rechts sitzt
        int isX = (a.origin.x >= b.origin.x)? a.origin.x : b.origin.x;
        //der obere rand der schmittmenge ist der obere rand von A und B, der weiter unten sitzt
        int isY = (a.origin.y >= b.origin.y)? a.origin.y : b.origin.y;
        //der rechte rand der schnittmenge ist der rechte rand von A und B, der weiter links sitzt
        int isEX = ((a.origin.x+a.size.width) <= (b.origin.x+b.size.width))? (a.origin.x+a.size.width) : (b.origin.x+b.size.width);
        //der untere rand der schnittmenge ist der untere rand von A und B, der weiter oben sitzt
        int isEY = ((a.origin.y+a.size.height) <= (b.origin.y+b.size.height))? (a.origin.y+a.size.height) : (b.origin.y+b.size.height);
        //aus den koordinaten können wir jetzt breite und höhe ableiten
        int isW = isEX - isX;
        int isH = isEY - isY;
        //eine schnittmenge gibt es nur dann, wenn beide > 0 sind (==0 entspricht kante an kante)
        if(isW > 0 && isH > 0)
            return new Rect(isX,isY,isW,isH);
        else
            return null;
    }

    public int getX() {
        return origin.x;
    }

    public int getY() {
        return origin.y;
    }

    public int getWidth() {
        return size.width;
    }

    public int getHeight() {
        return size.height;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Rect) {
            Rect o = (Rect) obj;
            return (origin.equals(o.origin) && size.equals(o.size));
        }
        return super.equals(obj);
    }

    public Rect copy() {
        return new Rect(getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public String toString() {
        return "R<("+getX()+"|"+getY()+"),["+getWidth()+"|"+getHeight()+"]>";
    }

    public Rect intersect(Rect b) {
        return intersection(this, b);
    }
}
