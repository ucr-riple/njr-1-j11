package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * @author Pavel Talanov
 */
public final class Drunkard extends MovableObject implements Actor {

    public static final Random RANDOM = new Random();

    private enum State {
        SLEEPING('Z'),
        WALKING('D'),
        LYING('&');

        private final char representation;

        private State(char representation) {
            this.representation = representation;
        }
    }

    @NotNull
    private State state = State.WALKING;

    public Drunkard(@NotNull Position position) {
        super(position);
    }

    @Override
    public char representation() {
        return state.representation;
    }

    @Override
    public String toString() {
        return String.valueOf(representation());
    }

    public boolean shouldBePickedByPoliceman() {
        return state == State.LYING || state == State.SLEEPING;
    }

    @Override
    public void performMove(@NotNull SimulationState simulationState) {
        assert state == State.WALKING;
        Board board = simulationState.getBoard();
        Position randomMove = board.getTopology().getRandomAdjacentPosition(getPosition());
        if (!board.isValid(randomMove)) {
            return;
        }
        if (!board.isEmpty(randomMove)) {
            mayBeChangeStatus(board, randomMove);
            if (state != State.WALKING) {
                simulationState.removeActor(this);
            }
            return;
        }
        doMove(board, randomMove);
    }

    private void mayBeChangeStatus(@NotNull Board board, @NotNull Position desiredDirection) {
        BoardObject obstacle = board.getObject(desiredDirection);
        if (obstacle instanceof Column ||
                (obstacle instanceof Drunkard && ((Drunkard) obstacle).state == State.SLEEPING)) {
            state = State.SLEEPING;
        } else if (obstacle instanceof Bottle) {
            board.setEmpty(desiredDirection);
            board.move(this, desiredDirection);
            state = State.LYING;
        }
    }

    private void doMove(@NotNull Board board, @NotNull Position randomMove) {
        Position oldPosition = getPosition();
        board.move(this, randomMove);
        assert board.isEmpty(oldPosition);
        if (drunkardDropsBottle()) {
            board.addObject(new Bottle(oldPosition));
        }
    }

    private boolean drunkardDropsBottle() {
        return RANDOM.nextInt(30) == 0;
    }
}
