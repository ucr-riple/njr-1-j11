package daoo.ioc;

import com.sun.istack.internal.NotNull;

public interface TaskServer {

    /** Start server with specified executor on specified port. */
    void start(@NotNull final TaskExecutor executor, int port);
}
