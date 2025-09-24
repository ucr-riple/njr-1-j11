package ru.drunkard.gameprinters;

import ru.drunkard.field.GameField;
import ru.drunkard.fieldobjects.*;
import ru.drunkard.utility.Point;

public interface GamePrinter {
    public void print(Integer gameStepNo, GameField gameField);
    public void setTavern(Point pos);
    public void setPoliceStation(Point pos);
    public void setGlassStation(Point pos);
    public void setLampPost(Point lightAreaStart, Point lightAreaEnd);

    public void visit(Drunkard drunkard);
    public void visit(Bottle bottle);
    public void visit(Column column);
    public void visit(LampPost lampPost);
    public void visit(Cop cop);
    public void visit(Hobo hobo);
}
