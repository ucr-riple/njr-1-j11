package ru.spbau.amanov.drunkard;

import ru.spbau.amanov.drunkard.GameObjects.*;

/**
 * Class provides game field printing.
 *
 * @author  Karim Amanov
 */
public class Printer implements ICollisionVisitor {

    /**
     *  Class constructor.
     * @param field field to print.
     * @param vpubCoord pub coordinate.
     */
    public Printer(AbstractField field) {
        gameField = field;
    }

    /**
     *  Field reprinting.
     */
    public void reprintField() {
        printPub();

        for (int i = 0; i < gameField.getHeight(); i++) {
            makeShift(i);
            if (i == GameConfig.BOTTLE_STORE_POS.getRow()) {
                System.out.print(BOTTLE_STORE_SYMBOL);
            } else {
                System.out.print("  ");
            }

            for (int j = 0; j < gameField.getWidth(i); j++) {
                gameField.getObject(i, j).accept(this);
            }

            if (i == GameConfig.POLICE_STATION_POS.getRow()) {
                System.out.print(POLICE_STATION_SYMBOL);
            }
            System.out.println();
        }
    }

    @Override
    public void collisionWith(Drunkard obj) {
        switch(obj.getState()) {
            case ACTIVE : System.out.print(DRUNKARD_ACTIVE_SYMBOL);
                break;
            case SLEEP : System.out.print(DRUNKARD_SLEEP_SYMBOL);
                break;
            case LYING : System.out.print(DRUNKARD_LYING_SYMBOL);
                break;
        }
    }

    @Override
    public void collisionWith(Column obj) {
        System.out.print(COLUMN_SYMBOL);
    }

    @Override
    public void collisionWith(Bottle obj) {
        System.out.print(BOTTLE_SYMBOL);
    }

    @Override
    public void collisionWith(Empty obj) {
        System.out.print(EMPTY_SYMBOL);
    }

    @Override
    public void collisionWith(Lantern obj) {
        System.out.print(LANTERN_SYMBOL);
    }

    @Override
    public void collisionWith(Policeman obj) {
        System.out.print(POLICEMAN_SYMBOL);
    }

    @Override
    public void collisionWith(Beggar obj) {
        System.out.print(BEGGAR_SYMBOL);
    }

    private void printPub() {
        StringBuilder sbuild = new StringBuilder();
        sbuild.append(" ");
        for (int i = 0; i <= GameConfig.PUB_POS.getColumn(); i++) {
            sbuild.append("  ");
        }
        sbuild.append(PUB_SYMBOL);
        System.out.println(sbuild.toString());
    }

    private void makeShift(int row) {
        if (gameField.isShift(row)) {
            System.out.print(" ");
        }
    }

    private final String DRUNKARD_SLEEP_SYMBOL = "Z ";
    private final String DRUNKARD_ACTIVE_SYMBOL = "D ";
    private final String DRUNKARD_LYING_SYMBOL = "& ";
    private final String BOTTLE_SYMBOL = "B ";
    private final String COLUMN_SYMBOL = "C ";
    private final String EMPTY_SYMBOL = ". ";
    private final String PUB_SYMBOL = "T";
    private final String POLICE_STATION_SYMBOL = "S";
    private final String LANTERN_SYMBOL = "L ";
    private final String POLICEMAN_SYMBOL = "P ";
    private final String BOTTLE_STORE_SYMBOL = "R ";
    private final String BEGGAR_SYMBOL = "z ";
    private AbstractField gameField;
}
