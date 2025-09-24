package ping.pong.net.connection.messaging;

/**
 *
 * @author mfullen
 */
public final class ConnectionIdMessage implements AbstractMessage
{
    private static final long serialVersionUID = -5530776801634240701L;

    /**
     *
     */
    public static class RequestMessage implements AbstractMessage
    {
        private static final long serialVersionUID = -8137880902144409573L;
    }

    /**
     *
     */
    public static class ResponseMessage implements AbstractMessage
    {
        private static final long serialVersionUID = -6001062890618431909L;
        private int id = -1;

        /**
         *
         */
        public ResponseMessage()
        {
        }

        /**
         *
         * @param id
         */
        public ResponseMessage(int id)
        {
            this.id = id;
        }

        /**
         *
         * @return
         */
        public int getId()
        {
            return id;
        }
    }
}
