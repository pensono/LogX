package org.logx;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Ethan Shea on 5/30/2017.
 */
public class Logger {
    List<LogMessage> messages = new ArrayList<>();
    Optional<LogMessage> parent = Optional.empty();

    public void begin(String what, Object... data){
        parent = Optional.of(makeMessage(what, data));
        messages.add(parent.get());
    }

    public void end(String what, Object... data){
        if (parent.isPresent()){
            parent = parent.get().getParent();
            messages.add(makeMessage(what, data));
        } else {
            throw new RuntimeException("End called without a matching begin.");
        }
    }

    public void log(String what, Object... data){
        messages.add(makeMessage(what, data));
    }

    private LogMessage makeMessage(String what, Object... data){
        return new LogMessage(what, parseData(data), parent);
    }

    private Map<String, Object> parseData(Object... data){
        if (data.length % 2 != 0){
            throw new RuntimeException("There must be a piece of data for each parameter");
        }

        Map<String, Object> logData = new HashMap<>();
        for (int i = 0; i < data.length / 2; i++){
            logData.put((String) data[i * 2], data[i * 2 + 1]);
        }
        return logData;
    }

    public List<LogMessage> getMessages(){
        // TODO defensive copy
        return messages;
    }
}
