package ru.drunkard.gameprinters;

import ru.drunkard.field.GameField;
import ru.drunkard.utility.Point;

import java.util.Iterator;

public class RectGamePrinter extends BasicGamePrinter {

    public RectGamePrinter(int fieldWidth, int fieldHeight) {
        super(fieldWidth, fieldHeight);
    }

    public void print(Integer gameStepNo, GameField gameField) {
        System.out.println();
        System.out.println("Game step : " + gameStepNo);
        for(Character ch : frameUpperPart) {
            printChar(ch);
        }
        System.out.println();
        Iterator<Point> fieldIter = gameField.getIterator();
        int rowNumber = 0;
        while (fieldIter.hasNext()) {
            Point current = fieldIter.next();
            if(current.x == 0) {
                if(rowNumber != 0) {
                    System.out.println(frameRightPart.get(rowNumber - 1));
                }
                printChar(frameLeftPart.get(rowNumber));
                ++rowNumber;
            }
            if(gameField.sectorIsEmpty(current.x, current.y)) {
                if(Point.rectCoversPoint(lightAreaStart, lightAreaEnd, current)) {
                    printChar(LIGHTEN_SECTOR);
                } else {
                    printChar(EMPTY_SECTOR);
                }
            } else {
                gameField.sendVisitorToSector(current.x, current.y, this);
            }
        }
        for(Character ch : frameBottomPart) {
            printChar(ch);
        }
        System.out.println();
    }
}
