package ru.spbau.amanov.drunkard;

import java.util.*;
import ru.spbau.amanov.drunkard.GameObjects.Empty;
import ru.spbau.amanov.drunkard.GameObjects.GameObject;

/**
 *  Class provides square field of the game.
 *
 *  @author  Karim Amanov
 */
public class RectangleField extends AbstractField {

    @Override
    public int getWidth(int row) {
        return WIDTH;
    }

    @Override
    public boolean isShift(int row) {
        return false;
    }
}
