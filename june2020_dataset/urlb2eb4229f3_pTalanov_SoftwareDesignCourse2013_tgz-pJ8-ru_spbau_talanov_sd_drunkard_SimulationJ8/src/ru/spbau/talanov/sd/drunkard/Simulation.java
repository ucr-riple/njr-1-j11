package ru.spbau.talanov.sd.drunkard;

import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Pavel Talanov
 */
public final class Simulation {

    @NotNull
    private final PrintStream resultOut;
    @NotNull
    private final PrintStream debugOut;

    private static final List<Integer> movesToReport = Arrays.asList(200, 300, 500);

    private static final int MAX_MOVES = Collections.max(movesToReport);

    private static final boolean USE_HEXAGONAL_GRID = true;

    @NotNull
    private final SimulationState state;


    public Simulation(@NotNull PrintStream resultOut, @NotNull PrintStream debugOut) {
        this.resultOut = resultOut;
        this.debugOut = debugOut;
        @SuppressWarnings("ConstantConditions")
        Board board = new Board(15, USE_HEXAGONAL_GRID ? new HexagonalTopology() : new RectangularTopology());
        board.addObject(new Column(Position.at(7, 7)));
        Lantern lantern = new Lantern(Position.at(10, 3));
        board.addObject(lantern);
        Inn theInn = new Inn(Position.at(9, -1), Position.at(9, 0));
        board.addSpecialObject(theInn);
        PoliceStation policeStation = new PoliceStation(Position.at(15, 3));
        board.addSpecialObject(policeStation);
        Policeman policeman = new Policeman(Position.at(14, 3), policeStation.getPosition(),
                lantern.getPosition(), board.getTopology());
        RecyclingPoint recyclingPoint = new RecyclingPoint(Position.at(-1, 4));
        board.addSpecialObject(recyclingPoint);
        Beggar beggar = new Beggar(Position.at(0, 4), recyclingPoint.getPosition());
        this.state = SimulationState.initialState(board, Arrays.<Actor>asList(theInn, policeman, beggar));
    }

    public void simulate() {
        for (int i = 0; i <= MAX_MOVES; ++i) {
            output(i, debugOut);
            makeMove();
            if (movesToReport.contains(i)) {
                output(i, resultOut);
            }
        }
    }

    private void output(int i, PrintStream out) {
        out.println(i);
        out.println("-------------------------------------");
        out.print(state.getBoard().representation());
        out.println("-------------------------------------");
    }

    private void makeMove() {
        for (Actor actor : state.getActors()) {
            actor.performMove(state);
        }
    }


    public static void main(String[] args) {
        try (FileOutputStream debugFileStream = new FileOutputStream("debug.out")) {
            try (PrintStream debugPrintStream = new PrintStream(debugFileStream)) {
                Simulation simulation = new Simulation(System.out, debugPrintStream);
                simulation.simulate();
            }
        } catch (IOException e) {
            System.out.println("Error while writing debug file.");
        }
    }
}
