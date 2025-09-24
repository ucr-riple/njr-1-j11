package DrunkardGame.GameObjects.CommonObjects;

/**
 * Created by novokrest on 4/12/14.
 */
public class Coordinates {
    int X;
    int Y;

    public Coordinates(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public Coordinates(Coordinates coordinates) {
        this.X = coordinates.X;
        this.Y = coordinates.Y;
    }

    public int getX() { return X; }
    public int getY() { return Y; }

    void setX(int X) { this.X = X; }
    void setY(int Y) { this.Y = Y; }
}
