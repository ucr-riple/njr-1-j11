package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * @author Pavel Talanov
 */
public final class Policeman extends MovableObject implements Actor {

    private static final int LANTERN_LIGHT_RADIUS = 3;

    @NotNull
    private final Position spawnLocation;
    @NotNull
    private final Position policeStationLocation;
    @NotNull
    private final Collection<Position> observableLocations;
    private boolean isAtPoliceStation = true;
    private boolean isCarryingADrunkard = false;

    public Policeman(@NotNull Position spawnLocation,
                     @NotNull Position policeStationLocation,
                     @NotNull Position lanternLocation,
                     @NotNull BoardTopology topology) {
        super(policeStationLocation);
        this.spawnLocation = spawnLocation;
        this.policeStationLocation = policeStationLocation;
        this.observableLocations = topology.allPositionsInRadius(lanternLocation, LANTERN_LIGHT_RADIUS);
        assert !observableLocations.contains(spawnLocation);
    }

    @Override
    public void performMove(@NotNull SimulationState simulationState) {
        final Board board = simulationState.getBoard();
        boolean headingToPoliceStation = isCarryingADrunkard;
        if (!headingToPoliceStation) {
            List<Position> pathToNearestDrunkardInObservablePositions = pathToNearestDrunkard(board);
            if (pathToNearestDrunkardInObservablePositions != null) {
                follow(pathToNearestDrunkardInObservablePositions, board, headingToPoliceStation);
            } else {
                headingToPoliceStation = true;
            }
        }
        if (headingToPoliceStation) {
            List<Position> pathToPoliceStation = pathToPoliceStation(board);
            if (pathToPoliceStation != null) {
                follow(pathToPoliceStation, board, headingToPoliceStation);
            } else {
                //IT'S A TRAP
                //do nothing
            }
        }
    }

    private void follow(@NotNull List<Position> path, @NotNull Board board, boolean headingToPoliceStation) {
        assert path.size() > 1;
        assert path.get(0).equals(getPosition());
        Position nextStep = path.get(1);
        boolean aboutToReachGoal = path.size() == 2;
        if (aboutToReachGoal) {
            assert !isAtPoliceStation : "This explicitly asserts that we can't pick drunkards from spawn location.";
            if (headingToPoliceStation) {
                moveToPoliceStation(nextStep, board);
            } else {
                pickUpDrunkard(nextStep, board);
            }
            return;
        }
        if (isAtPoliceStation) {
            exitPoliceStation(nextStep, board);
            return;
        }
        board.move(this, nextStep);
    }

    private void exitPoliceStation(@NotNull Position nextStep, @NotNull Board board) {
        assert nextStep.equals(spawnLocation);
        isAtPoliceStation = false;
        setPosition(spawnLocation);
        board.addObject(this);
    }

    private void moveToPoliceStation(@NotNull Position nextStep, @NotNull Board board) {
        assert nextStep.equals(policeStationLocation);
        assert getPosition().equals(spawnLocation);
        assert !isAtPoliceStation;
        board.setEmpty(spawnLocation);
        isAtPoliceStation = true;
        isCarryingADrunkard = false;
    }

    private void pickUpDrunkard(@NotNull Position nextStep, @NotNull Board board) {
        assert board.getObject(nextStep) instanceof Drunkard &&
                ((Drunkard) board.getObject(nextStep)).shouldBePickedByPoliceman();
        assert !isCarryingADrunkard;
        board.setEmpty(nextStep);
        board.move(this, nextStep);
        isCarryingADrunkard = true;
    }

    @Nullable
    private List<Position> pathToPoliceStation(@NotNull Board board) {
        return FindPath.findPath(getPosition(), FindPath.emptyValidPosition(board), new FindPath.PositionPredicate() {
            @Override
            public boolean accepts(@NotNull Position position) {
                return position.equals(policeStationLocation);
            }
        }, board.getTopology());
    }

    @NotNull
    @Override
    public Position getPosition() {
        if (isAtPoliceStation) {
            return policeStationLocation;
        } else {
            return super.getPosition();
        }
    }

    @Nullable
    private List<Position> pathToNearestDrunkard(@NotNull final Board board) {
        return FindPath.findPath(getPosition(), FindPath.emptyValidPosition(board),
                new FindPath.PositionPredicate() {
                    @SuppressWarnings("SimplifiableIfStatement")
                    @Override
                    public boolean accepts(@NotNull Position position) {
                        if (board.isEmpty(position)) {
                            return false;
                        }
                        BoardObject object = board.getObject(position);
                        if (!(object instanceof Drunkard)) {
                            return false;
                        }
                        if (!((Drunkard) object).shouldBePickedByPoliceman()) {
                            return false;
                        }
                        return observableLocations.contains(position);
                    }
                }, board.getTopology()
        );
    }

    @Override
    public char representation() {
        return 'P';
    }
}
