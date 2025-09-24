package ru.spbau.tinydb.queries;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.common.DBException;
import ru.spbau.tinydb.engine.IDataBase;

/**
 * @author adkozlov
 */
public interface IQuery<R> {

    @NotNull
    R execute(@NotNull IDataBase instance) throws DBException;
}
