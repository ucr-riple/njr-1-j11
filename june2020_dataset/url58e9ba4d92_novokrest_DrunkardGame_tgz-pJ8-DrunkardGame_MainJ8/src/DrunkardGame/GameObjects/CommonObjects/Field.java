package DrunkardGame.GameObjects.CommonObjects;

import DrunkardGame.GameObjects.StaticObjects.*;

/**
 * Created by novokrest on 3/3/14.
 */
public class Field {
    int rowCount;
    int columnCount;
    GameObject[][] tableObjects;

    Column column;
    Lamppost lamppost;
    Pub pub;
    PoliceStation policeStation;
    GlassPoint glassPoint;

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        initialize();
        registerStaticObjects();

    }

    private void initialize() {
        tableObjects = new GameObject[this.rowCount][this.columnCount];
        for (int i = 0; i < this.rowCount; i++) {
            for (int j = 0; j < this.columnCount; j++) {
                if ((i == 0) || (i == this.rowCount - 1) || (j == 0) || (j == this.columnCount - 1)) {
                    tableObjects[i][j] = new Border(i, j);
                } else {
                    tableObjects[i][j] = new GameObject(i, j);
                }
            }
        }
    }

    private void registerStaticObjects() {
        pub = new Pub(10, 0);
        policeStation = new PoliceStation(16, 4);
        glassPoint = new GlassPoint(0, 5);
        column = new Column(8, 8);
        lamppost = new Lamppost(11, 4);
        register(pub);
        register(policeStation);
        register(glassPoint);
        register(column);
        register(lamppost);
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Lamppost getLamppost() {
        return lamppost;
    }

    public GameObject getObject(int coordinateX, int coordinateY) {
        return tableObjects[coordinateX][coordinateY];
    }

    public GameObject getObject(Coordinates coordinates) {
        return getObject(coordinates.getX(), coordinates.getY());
    }

    public void register(GameObject gameObject) {
        tableObjects[gameObject.getX()][gameObject.getY()] = gameObject;
    }

    public void unregister(Coordinates coordinates) {
        tableObjects[coordinates.getX()][coordinates.getY()] = new GameObject(coordinates.getX(), coordinates.getY());
    }

    public void swapGameObject(Coordinates coordinates1, Coordinates coordinates2) {
        GameObject tmp = getObject(coordinates1);
        tableObjects[coordinates1.getX()][coordinates1.getY()] = tableObjects[coordinates2.getX()][coordinates2.getY()];
        tableObjects[coordinates2.getX()][coordinates2.getY()] = tmp;
        getObject(coordinates1).setCoordinates(coordinates1);
        getObject(coordinates2).setCoordinates(coordinates2);
    }

    public void print() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tableObjects[j][i].print();
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
