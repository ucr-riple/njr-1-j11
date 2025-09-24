package patterns.gof.behavioral.chainOfResponsibility;

public abstract class Logger {
	private int logMask;
	private Logger next;

    public Logger(int mask) {
        this.logMask = mask;
    }

    public void setNext(Logger nextlogger) {
        next = nextlogger;
    }

    public void message(String msg, LoggingLevel severity) {
        if ((severity.getCode() & logMask) != 0) { //True only if all logMask bits are set in severity
            writeMessage(msg);
        }
        if (next != null) {
            next.message(msg, severity); 
        }
    }

    abstract protected void writeMessage(String msg);
}