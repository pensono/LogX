package org.logx;

import java.util.*;

/**
 * Created by Ethan Shea on 5/30/2017.
 */
public class LogXLogger {
    private LogXHandler handler;
    Optional<LogXMessage> parent = Optional.empty();

    public LogXLogger(LogXHandler handler){
        this.handler = handler;
    }

    public void begin(String message, Object... data){
        LogXMessage record = makeMessage(LogXLevel.INFO, message, data);
        parent = Optional.of(record);
        log(record);
    }

    public void end(String message, Object... data){
        if (parent.isPresent()){
            parent = parent.get().getParent();
            log(LogXLevel.INFO, message, data);
        } else {
            throw new RuntimeException("End called without a matching begin.");
        }
    }

    public void log(LogXLevel level, String message, Object... data){
        log(makeMessage(level, message, data));
    }

    public void log(LogXMessage record){
        handler.handle(record);
    }

    private LogXMessage makeMessage(LogXLevel level, String message, Object... data){
        return new LogXMessage(level, message, parseData(data), parent);
    }

    private Map<String, Object> parseData(Object... data){
        Map<String, Object> logData = new HashMap<>();
        for (int i = 0; i < data.length; i++){
            if (data[i] instanceof String) {
                if (i + 1 >= data.length){
                    throw new RuntimeException("No data for parameter " + data[i]);
                }
                logData.put((String) data[i], data[i + 1]);
                i++;
            } else {
                logData.put(data[i].getClass().getSimpleName(), data[i]);
            }
        }
        return logData;
    }

    public void debug(String message, Object... data){
        log(LogXLevel.DEBUG, message, data);
    }

    public void info(String message, Object... data){
        log(LogXLevel.INFO, message, data);
    }

    public void audit(String message, Object... data){
        log(LogXLevel.AUDIT, message, data);
    }

    public void fixSoon(String message, Object... data){
        log(LogXLevel.FIX_SOON, message, data);
    }

    public void fixEventually(String message, Object... data){
        log(LogXLevel.FIX_EVENTUALLY, message, data);
    }

    public void fixImmediately(String message, Object... data){
        log(LogXLevel.FIX_IMMEDIATELY, message, data);
    }
}
