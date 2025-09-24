package ru.drunkard.gameprinters;

import ru.drunkard.fieldobjects.*;
import ru.drunkard.utility.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicGamePrinter implements GamePrinter {
    protected List<Character> frameUpperPart;
    protected List<Character> frameBottomPart;
    protected List<Character> frameRightPart;
    protected List<Character> frameLeftPart;

    protected final char FRAME_EMPTY_ELEM = ' ';
    protected final char EMPTY_SECTOR = '.';
    protected final char LIGHTEN_SECTOR = ';';
    protected final char TAVERN = 'T';
    protected final char POLICE_STATION = 'S';
    protected final char GLASS_STATION = 'G';
    protected final String HORIZONTAL_DELIMITER = "   ";

    protected Point lightAreaStart;
    protected Point lightAreaEnd;

    protected BasicGamePrinter(int fieldWidth, int fieldHeight) {
        frameUpperPart = new ArrayList<>();
        initFramePart(frameUpperPart, fieldWidth + 2);
        frameBottomPart = new ArrayList<>();
        initFramePart(frameBottomPart, fieldWidth + 2);
        frameRightPart = new ArrayList<>();
        initFramePart(frameRightPart, fieldHeight);
        frameLeftPart = new ArrayList<>();
        initFramePart(frameLeftPart, fieldHeight);
    }

    public void setTavern(Point pos) { setFrameObject(pos, TAVERN); }
    public void setPoliceStation(Point pos) { setFrameObject(pos, POLICE_STATION); }
    public void setGlassStation(Point pos) { setFrameObject(pos, GLASS_STATION); }
    public void setLampPost(Point lightAreaStart, Point lightAreaEnd) {
        this.lightAreaStart = lightAreaStart;
        this.lightAreaEnd = lightAreaEnd;
    }

    protected void initFramePart(List<Character> framePart, int size) {
        for(int i = 0; i < size; i++) {
            framePart.add(FRAME_EMPTY_ELEM);
        }
    }

    protected void setFrameObject(Point pos, char symbol) {
        if(pos.y == -1) {
            frameUpperPart.add(pos.x + 1, symbol);
        } else if(pos.y == frameLeftPart.size()) {
            frameBottomPart.add(pos.x + 1, symbol);
        } else if(pos.x == -1) {
            frameLeftPart.add(pos.y, symbol);
        } else {
            frameRightPart.add(pos.y, symbol);
        }
    }
    protected void printChar(char symbol) {
        System.out.print(symbol);
        System.out.print(HORIZONTAL_DELIMITER);
    }

    public void visit(Drunkard drunkard) {
        if(drunkard.isSleeping) {
            printChar('Z');
        } else if(drunkard.isFallen) {
            printChar('&');
        } else {
            printChar('D');
        }
    }
    public void visit(Bottle bottle) { printChar('b' ); }
    public void visit(Column column) { printChar('C'); }
    public void visit(LampPost lampPost) { printChar('L'); }
    public void visit(Cop cop) { printChar('P'); }
    public void visit(Hobo hobo) { printChar('H'); }
}
