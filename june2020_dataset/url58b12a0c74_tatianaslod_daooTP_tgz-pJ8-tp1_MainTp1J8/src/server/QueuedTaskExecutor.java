package server;

import com.sun.istack.internal.NotNull;
import daoo.ioc.Task;
import daoo.ioc.TaskExecutor;

public class QueuedTaskExecutor implements TaskExecutor {
    @Override public void execute(@NotNull Task task) {
        throw new RuntimeException("To be implemented!");
    }
}
