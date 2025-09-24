package ch2.msg;

public class Telegram {
    public final String sender;
    public final String receiver;
    public final MessageType type;
    public double dispatchTime;

    public Telegram(String sender, String receiver, MessageType type, double dispatchTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.dispatchTime = dispatchTime;
    }
}
