package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pavel Talanov
 */
public final class SimulationState {

    @NotNull
    public static SimulationState initialState(@NotNull Board board, @NotNull List<Actor> actors) {
        return new SimulationState(board, actors);
    }

    @NotNull
    private final Board board;

    @NotNull
    private final List<Actor> actors;

    private SimulationState(@NotNull Board board, @NotNull List<Actor> actors) {
        this.board = board;
        this.actors = new ArrayList<>(actors);
    }

    @NotNull
    public Board getBoard() {
        return board;
    }

    @NotNull
    public List<Actor> getActors() {
        return new ArrayList<>(actors);
    }

    public void addActor(@NotNull Actor actor) {
        if (actors.contains(actor)) {
            throw new IllegalStateException("Actor already registered!");
        }
        actors.add(actor);
    }

    public void removeActor(@NotNull Actor actor) {
        boolean removed = actors.remove(actor);
        if (!removed) {
            throw new IllegalStateException("No such actor!");
        }
    }
}
