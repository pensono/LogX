package org.logx;

import java.util.List;

/**
 * @author Ethan
 */
public abstract class LogXRepository implements LogXHandler {
    public abstract void addHandler(LogXHandler handler);

    public abstract void handle(LogXMessage record);

    public abstract List<LogXMessage> allMessages();

    public abstract List<LogXMessage> recordsWithMessageContaining(String message);
}
