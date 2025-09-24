package ru.drunkard.game;

public class DrunkardTheGame {

    public static void main(String[] args) {
        GameController gameController = new GameController(true);
        gameController.startGame(2000, 400, 0);
    }
}
