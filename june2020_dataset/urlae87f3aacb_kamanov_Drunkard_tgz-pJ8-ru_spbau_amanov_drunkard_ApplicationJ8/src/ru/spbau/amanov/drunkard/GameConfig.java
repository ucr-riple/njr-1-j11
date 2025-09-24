package ru.spbau.amanov.drunkard;

/**
 * Class provides game configuration.
 *
 * @author  Karim Amanov
 */
public class GameConfig {
    public static final Position COLUMN_POS = new Position(7, 7);
    public static final Position PUB_POS = new Position(0, 9);
    public static final Position LANTERN_POS = new Position(3, 10);
    public static final Position POLICE_STATION_POS = new Position(3, 14);
    public static final int FIELD_WIDTH = 15;
    public static final int FIELD_HEIGHT = 15;
    public static final int BOTTLE_LOOSE_PROBABILITY = 30;
    public static final int DRUNKARD_FREQ = 20;
    public static final int LANTERN_RADIUS = 3;
    public static final Position BOTTLE_STORE_POS = new Position(4, 0);
    public static final int BEGGAR_PERIOD = 30;
    public static final int MAX_GAME_STEPS = 500;
}
