package org.logx;

import java.util.*;

/**
 * Created by Ethan Shea on 5/30/2017.
 */
public class LogXLogger {
    List<LogXRecord> messages = new ArrayList<>();
    Optional<LogXRecord> parent = Optional.empty();

    public void begin(String what, Object... data){
        parent = Optional.of(makeMessage(LogLevel.INFO, what, data));
        messages.add(parent.get());
    }

    public void end(String what, Object... data){
        if (parent.isPresent()){
            parent = parent.get().getParent();
            messages.add(makeMessage(LogLevel.INFO, what, data));
        } else {
            throw new RuntimeException("End called without a matching begin.");
        }
    }

    public void log(LogLevel level, String what, Object... data){
        messages.add(makeMessage(level, what, data));
    }

    private LogXRecord makeMessage(LogLevel level, String what, Object... data){
        return new LogXRecord(level, what, parseData(data), parent);
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

    public void debug(String what, Object... data){
        log(LogLevel.DEBUG, what, data);
    }

    public void info(String what, Object... data){
        log(LogLevel.INFO, what, data);
    }

    public void audit(String what, Object... data){
        log(LogLevel.AUDIT, what, data);
    }

    public void fixSoon(String what, Object... data){
        log(LogLevel.FIX_SOON, what, data);
    }

    public void fixEventually(String what, Object... data){
        log(LogLevel.FIX_EVENTUALLY, what, data);
    }

    public void fixImmediately(String what, Object... data){
        log(LogLevel.FIX_IMMEDIATELY, what, data);
    }

    public List<LogXRecord> getMessages(){
        // TODO defensive copy
        return messages;
    }
}
