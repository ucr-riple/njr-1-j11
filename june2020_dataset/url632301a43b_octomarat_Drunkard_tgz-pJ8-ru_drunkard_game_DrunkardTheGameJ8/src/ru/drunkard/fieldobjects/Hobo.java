package ru.drunkard.fieldobjects;

import ru.drunkard.field.GameField;
import ru.drunkard.gameprinters.GamePrinter;
import ru.drunkard.movestrategies.IDirectedMoveStrategy;
import ru.drunkard.states.movableobjstate.*;
import ru.drunkard.utility.Point;

import java.util.List;

public class Hobo extends DirectedMovableObj implements ISeekerWithState {
    private ISeekerState hState = new WaitingState();
    private final Point glassStationPos;
    private int timer = 30;

    public Hobo(Point startPos, IDirectedMoveStrategy ms, Point searchAreaStart, Point searchAreaEnd) {
        super(startPos, ms, searchAreaStart, searchAreaEnd);
        glassStationPos = new Point(startPos.x, startPos.y);
    }

    public void doActions(GameField field) { hState.initActions(this, field); }
    public void waitSome(GameField field) {
        if(timer == 0) {
            hState = new ExitStartPosState();
            hState.initActions(this, field);
        } else {
            timer -= 1;
        }
    }
    public void exitStartPos(GameField field) {
        List<Point> availableExits = field.getFreeNeighbours(glassStationPos, null);
        if(availableExits.size() != 0) {
            pos = availableExits.get(0);
            field.setObjectInSector(availableExits.get(0).x, availableExits.get(0).y, this);
            hState = new SeekingState();
        }
    }

    public void seekTarget(GameField field) {
        Point target = tryFindTarget(field);
        if(target != null) {
            hState = new TargetedState(target);
            hState.initActions(this, field);
        }
    }
    public void goToTarget(Point target, GameField field) {
        Point nextPos = moveStrategy.nextPosition(pos, target, field);
        if(nextPos.x == target.x && nextPos.y == target.y) {
            field.setObjectInSector(nextPos.x, nextPos.y, null);
            hState = new ReturnToStartState();
        }
        moveInSector(nextPos, field);
    }
    public void returnToStartPos(GameField field) {
        List<Point> availableExits = field.getFreeNeighbours(glassStationPos, pos);
        if(availableExits.size() != 0) {
            Point nextPos = moveStrategy.nextPosition(pos, availableExits.get(0), field);
            moveInSector(nextPos, field);
            if (isNeighbourToStartPos(glassStationPos, field)) {
                hState = new EnterStartPosState();
            }
        }
    }
    public void enterStartPos(GameField field) {
        field.setObjectInSector(pos.x, pos.y, null);
        hState = new WaitingState();
        hasTarget = false;
        timer = 30;
    }
    public void visit(Drunkard drunkard) {}
    public void visit(Column column) {}
    public void visit(Bottle bottle) { hasTarget = true; }
    public void visit(LampPost lampPost) {}
    public void visit(Cop cop) {}
    public void visit(Hobo hobo) {}

    public void accept(IFieldObj visitor) { visitor.visit(this); }
    public void accept(GamePrinter printer) { printer.visit(this); }
}
