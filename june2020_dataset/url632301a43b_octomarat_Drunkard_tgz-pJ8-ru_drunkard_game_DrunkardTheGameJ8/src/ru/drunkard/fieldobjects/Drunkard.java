package ru.drunkard.fieldobjects;

import ru.drunkard.field.GameField;
import ru.drunkard.gameprinters.GamePrinter;
import ru.drunkard.movestrategies.DrunkardMoveStrategy;
import ru.drunkard.utility.Point;

public class Drunkard extends UndirectedMovableObj {
    public boolean isSleeping = false;
    public boolean isFallen = false;
    private Bottle bottle = new Bottle();

    private final double BOTTLE_DROP_CHANCE = 1.0 / 30;

    public Drunkard(int x, int y) { super(new Point(x, y), new DrunkardMoveStrategy()); }

    public void doActions(GameField field) {
        if(!isFallen && !isSleeping) {
            int xBeforeMoveTry = pos.x;
            int yBeforeMoveTry = pos.y;
            Point nextPos = moveStrategy.nextPosition(pos, field);
            if(!nextPos.equals(pos)) {
                if(field.sectorIsEmpty(nextPos.x, nextPos.y)) {
                    moveInSector(nextPos, field);
                } else {
                    field.sendVisitorToSector(nextPos.x, nextPos.y, this);
                }
                if(xBeforeMoveTry != pos.x || yBeforeMoveTry != pos.y) {
                    tryDropBottle(xBeforeMoveTry, yBeforeMoveTry, field);
                }
            }
        }
    }

    public void visit(Drunkard otherDrunkard) {
        if(otherDrunkard.isSleeping) {
            this.isSleeping = true;
        }
    }
    public void visit(Column column) { isSleeping = true; }
    public void visit(Bottle bottle) { isFallen = true; }
    public void visit(LampPost lampPost) {}
    public void visit(Cop cop) { cop.visit(this); }
    public void visit(Hobo hobo) {}

    public void accept(IFieldObj visitor) { visitor.visit(this); }
    public void accept(GamePrinter printer) { printer.visit(this); }

    private void tryDropBottle(int xOld, int yOld, GameField field) {
        if(bottle != null) {
            if(Math.random() < BOTTLE_DROP_CHANCE) {
                Bottle bottleCopy = new Bottle();
                field.setObjectInSector(xOld, yOld, bottleCopy);
                bottle = null;
                field.addNewObject(bottleCopy);
            }
        }
    }
}