package org.logx;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Ethan Shea on 5/30/2017.
 */
public class LogXMessage {
    private Optional<LogXMessage> parent;
    private Map<String, Object> data;
    private String message;
    private LogXLevel level;

    public LogXMessage(LogXLevel level, String message, Map<String, Object> data, Optional<LogXMessage> parent) {
        this.level = level;
        this.parent = parent;
        this.data = data;
        this.message = message;
    }

    public LogXMessage(LogXLevel level, String message, Map<String, Object> data, LogXMessage parent) {
        this(level, message, data, Optional.of(parent));
    }

    public LogXMessage(LogXLevel level, String message, Map<String, Object> data) {
        this(level, message, data, Optional.empty());
    }

    public Optional<Object> getData(String key) {
        if (data.containsKey(key)){
            return Optional.of(data.get(key));
        } else {
            if (parent.isPresent()){
                return parent.get().getData(key);
            } else {
                return Optional.empty();
            }
        }
    }

    public Map<String, Object> getAllData(){
        Map<String, Object> result = new HashMap<>();
        if (parent.isPresent()) {
            result.putAll(parent.get().getAllData());
        }
        result.putAll(data);

        return result;
    }

    public Map<String, Object> getMyData(){
        // TODO defensive copy
        return data;
    }

    public Optional<LogXMessage> getParent() {
        return parent;
    }

    public LogXLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }
}
