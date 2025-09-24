package DrunkardGame.GameObjects.CommonObjects;

import DrunkardGame.GameInterfaces.*;

/**
 * Created by novokrest on 3/3/14.
 */
public class GameObject implements IGameVisitable, IGamePrintable {
    Coordinates coordinates;

    public GameObject(int coordinateX, int coordinateY) {
        coordinates = new Coordinates(coordinateX, coordinateY);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
    public int getX() { return coordinates.getX(); }
    public int getY() { return coordinates.getY(); }

    public void setCoordinates (Coordinates newCoordinates) {
        coordinates = new Coordinates(newCoordinates);
    }

    @Override
    public void accept(IGameVisitor visitor, Field field) {
        visitor.visit(this, field);
    }

    @Override
    public void print() {
        System.out.print('.');
    }
}
