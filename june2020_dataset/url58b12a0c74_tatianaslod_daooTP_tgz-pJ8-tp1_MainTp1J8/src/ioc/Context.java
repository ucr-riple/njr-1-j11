package ioc;

import daoo.ioc.MsgEncoder;
import daoo.ioc.TaskExecutor;
import encoder.MsgEncoderProvider;
import server.TaskExecutorProvider;

/**
 * Created with IntelliJ IDEA.
 * User: Tatiana
 * Date: 14/06/13
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
public class Context {
    public static MsgEncoder getMsgEncoder() throws Exception {
        return MsgEncoderProvider.getMsgEncoder();
    }

    public static TaskExecutor getTaskExecutor() throws Exception {
        return TaskExecutorProvider.getExecutor();
    }
}
