package ru.spbau.tinydb.expressions;

import org.jetbrains.annotations.NotNull;
import ru.spbau.tinydb.queries.SecondLevelId;

import java.util.List;
import java.util.Map;

/**
 * @author adkozlov
 */
public interface Expression<V> {

    @NotNull
    V execute(@NotNull Map<SecondLevelId, Object> values);

    @NotNull
    List<SecondLevelId> getIds();
}
