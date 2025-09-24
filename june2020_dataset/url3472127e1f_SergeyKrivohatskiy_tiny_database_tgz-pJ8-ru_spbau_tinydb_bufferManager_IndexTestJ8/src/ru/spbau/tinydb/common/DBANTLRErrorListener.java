package ru.spbau.tinydb.common;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.jetbrains.annotations.NotNull;

/**
 * @author adkozlov
 */
public class DBANTLRErrorListener extends BaseErrorListener {

    @NotNull
    private static final DBANTLRErrorListener INSTANCE = new DBANTLRErrorListener();

    private DBANTLRErrorListener() {
        super();
    }

    @NotNull
    public static DBANTLRErrorListener getInstance() {
        return INSTANCE;
    }

    @Override
    public void syntaxError(@NotNull Recognizer<?, ?> recognizer, Object o, int i, int i1, @NotNull String s, RecognitionException e) {
        if (e != null) {
            throw new DBException(s, e);
        } else {
            throw new DBException(s);
        }
    }
}
