package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Pavel Talanov
 */
public final class Beggar extends MovableObject implements Actor {

    private static final int TIME_TO_WASTE_ALL_THE_MONEY = 30;
    private int turnsTillRunsOutOfMoney = 1;
    @NotNull
    private final Position spawnLocation;
    @NotNull
    private final Position recyclingPointLocation;
    private boolean hasABottle = false;
    boolean isAtRecyclePoint = true;

    public Beggar(@NotNull Position spawnLocation, @NotNull Position recyclingPointPosition) {
        super(recyclingPointPosition);
        this.spawnLocation = spawnLocation;
        this.recyclingPointLocation = recyclingPointPosition;
    }

    @Override
    public void performMove(@NotNull SimulationState simulationState) {
        if (isSpendingMoney()) {
            assert isAtRecyclePoint;
            spendSomeMore();
            return;
        }
        final Board board = simulationState.getBoard();
        if (isAtRecyclePoint) {
            exitRecyclePoint(board);
            return;
        }
        List<Position> path = hasABottle ? pathToRecyclePoint(board) : pathToNearestBottle(board);
        if (path != null) {
            follow(path, board);
        } else {
            wanderRandomly(board);
        }
    }

    private void follow(@NotNull List<Position> path, @NotNull Board board) {
        assert path.size() > 1;
        assert path.get(0).equals(getPosition());
        assert !isAtRecyclePoint;
        Position nextStep = path.get(1);
        boolean aboutToReachGoal = path.size() == 2;
        if (aboutToReachGoal) {
            if (hasABottle) {
                moveToRecyclingPoint(nextStep, board);
            } else {
                pickUpBottle(nextStep, board);
            }
            return;
        }

        board.move(this, nextStep);
    }

    private void pickUpBottle(@NotNull Position nextStep, @NotNull Board board) {
        assert !board.isEmpty(nextStep);
        assert board.getObject(nextStep) instanceof Bottle;
        board.setEmpty(nextStep);
        board.move(this, nextStep);
        hasABottle = true;
    }

    private void moveToRecyclingPoint(@NotNull Position nextStep, @NotNull Board board) {
        assert nextStep.equals(recyclingPointLocation);
        assert hasABottle;
        assert getPosition().equals(spawnLocation);
        assert !isAtRecyclePoint;
        board.setEmpty(spawnLocation);
        isAtRecyclePoint = true;
        turnsTillRunsOutOfMoney = TIME_TO_WASTE_ALL_THE_MONEY;
        hasABottle = false;
    }

    private void exitRecyclePoint(@NotNull Board board) {
        assert board.isValid(spawnLocation);
        if (!board.isEmpty(spawnLocation)) {
            return;
        }
        isAtRecyclePoint = false;
        setPosition(spawnLocation);
        board.addObject(this);
    }

    private void wanderRandomly(@NotNull Board board) {
        Position randomMove = board.getTopology().getRandomAdjacentPosition(getPosition());
        if (board.isEmpty(randomMove) && board.isValid(randomMove)) {
            board.move(this, randomMove);
        }
    }

    @Nullable
    private List<Position> pathToRecyclePoint(@NotNull Board board) {
        return FindPath.findPath(getPosition(), FindPath.emptyValidPosition(board), new FindPath.PositionPredicate() {
            @Override
            public boolean accepts(@NotNull Position position) {
                return recyclingPointLocation.equals(position);
            }
        }, board.getTopology());
    }

    @Nullable
    private List<Position> pathToNearestBottle(@NotNull final Board board) {
        return FindPath.findPath(getPosition(), FindPath.emptyValidPosition(board), new FindPath.PositionPredicate() {
            @Override
            public boolean accepts(@NotNull Position position) {
                return !board.isEmpty(position) && board.getObject(position) instanceof Bottle;
            }
        }, board.getTopology());
    }

    @NotNull
    @Override
    public Position getPosition() {
        if (isAtRecyclePoint) {
            return recyclingPointLocation;
        }
        return super.getPosition();
    }

    private void spendSomeMore() {
        turnsTillRunsOutOfMoney--;
    }

    @Override
    public char representation() {
        return 'z';
    }

    public boolean isSpendingMoney() {
        return turnsTillRunsOutOfMoney > 0;
    }
}
