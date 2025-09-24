package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

/**
 * @author Pavel Talanov
 */
public final class Inn extends ImmobileObject implements Actor {

    private static final int DRUNKARD_SPAWN_PERIOD = 20;

    private int moveCount = DRUNKARD_SPAWN_PERIOD;
    private boolean shouldSpawnDrunkard = false;
    @NotNull
    private final Position drunkardSpawnPosition;

    public Inn(@NotNull Position innPosition, @NotNull Position drunkardSpawnPosition) {
        super(innPosition, 'T');
        this.drunkardSpawnPosition = drunkardSpawnPosition;
    }

    @Override
    public void performMove(@NotNull SimulationState simulationState) {
        decrementCounter();
        mayBeSpawnDrunkard(simulationState);
    }

    private void mayBeSpawnDrunkard(@NotNull SimulationState simulationState) {
        if (!shouldSpawnDrunkard) {
            return;
        }
        Board board = simulationState.getBoard();
        if (board.isEmpty(drunkardSpawnPosition)) {
            Drunkard spawnedDrunkard = new Drunkard(drunkardSpawnPosition);
            board.addObject(spawnedDrunkard);
            simulationState.addActor(spawnedDrunkard);
            shouldSpawnDrunkard = false;
        }
    }

    private void decrementCounter() {
        moveCount--;
        if (moveCount == 0) {
            moveCount = DRUNKARD_SPAWN_PERIOD;
            shouldSpawnDrunkard = true;
        }
    }
}
