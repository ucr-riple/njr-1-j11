package ru.spbau.tinydb.repl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.tinydb.engine.DataBaseEngine;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 * @author adkozlov
 */
public class REPL {

    private static final String USAGE_MESSAGE_FORMAT = "Usage: <db_filename> [ %s <input_filename> ]" +
            "[ %s <output_filename> ] [ %s <errors_filename> ]";
    private static final String INPUT_FILENAME_KEY = "-i";
    private static final String OUTPUT_FILENAME_KEY = "-o";
    private static final String ERRORS_FILENAME_KEY = "-e";

    public static void main(@NotNull String[] args) throws FileNotFoundException {
        if (!assertArgumentsListLength(args.length)) {
            System.out.printf(USAGE_MESSAGE_FORMAT, INPUT_FILENAME_KEY, OUTPUT_FILENAME_KEY, ERRORS_FILENAME_KEY);
        }

        createRunnable(Arrays.asList(args)).run();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    DataBaseEngine.getInstance().close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        });
    }

    private static boolean assertArgumentsListLength(int length) {
        return length == 1 || length == 3 || length == 5 || length == 7;
    }

    private static REPLRunnable createRunnable(@NotNull List<String> args) throws FileNotFoundException {
        String dbFileName = args.get(0);
        String inputFileName = getValue(args, INPUT_FILENAME_KEY);
        String outputFileName = getValue(args, OUTPUT_FILENAME_KEY);
        String errorsFileName = getValue(args, ERRORS_FILENAME_KEY);

        return inputFileName != null ? new ScriptREPLRunnable(dbFileName, inputFileName, outputFileName, errorsFileName) :
                new ConsoleREPLRunnable(dbFileName, outputFileName, errorsFileName);
    }

    @Nullable
    private static String getValue(@NotNull List<String> args, @NotNull String key) {
        int index = args.indexOf(key) + 1;
        return index != 0 ? args.get(index) : null;
    }
}
