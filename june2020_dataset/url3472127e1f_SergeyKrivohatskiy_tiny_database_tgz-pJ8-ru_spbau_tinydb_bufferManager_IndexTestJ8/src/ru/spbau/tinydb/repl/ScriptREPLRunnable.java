package ru.spbau.tinydb.repl;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.grammar.SQLGrammarParser;
import ru.spbau.tinydb.queries.IQuery;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author adkozlov
 */
public class ScriptREPLRunnable extends REPLRunnable<FileInputStream> {

    @NotNull
    private final FileInputStream fileInputStream;

    private ScriptREPLRunnable(@NotNull String dbFileName,
                               @NotNull FileInputStream fileInputStream,
                               @Nullable String outputFileName,
                               @Nullable String errorsFileName) throws FileNotFoundException {
        super(dbFileName, fileInputStream, outputFileName, errorsFileName);
        this.fileInputStream = fileInputStream;
    }

    public ScriptREPLRunnable(@NotNull String dbFileName,
                              @NotNull String inputFileName,
                              @Nullable String outputFileName,
                              @Nullable String errorsFileName) throws FileNotFoundException {
        this(dbFileName, new FileInputStream(inputFileName), outputFileName, errorsFileName);
    }

    @Override
    public void innerRun() {
        List<IQuery> queries = parseScript();

        if (queries != null) {
            for (IQuery query : queries) {
                executeAndPrintResult(query);

                getStdOut().println();
                getStdOut().flush();
            }
        }
    }

    @Nullable
    private List<IQuery> parseScript() {
        SQLGrammarParser parser = createParser(fileInputStream);

        if (parser != null) {
            try {
                return parser.script().result;
            } catch (DBException e) {
                handleException(e);
            }
        }

        return null;
    }

    @NotNull
    @Override
    protected ANTLRInputStream createANTLRInputStream(@NotNull FileInputStream query) throws IOException {
        return new ANTLRInputStream(query);
    }

    @Override
    public void close() throws Exception {
        fileInputStream.close();
        super.close();
    }
}
