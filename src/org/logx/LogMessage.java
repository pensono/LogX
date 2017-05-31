package org.logx;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Ethan Shea on 5/30/2017.
 */
public class LogMessage {
    private Optional<LogMessage> parent;
    private Map<String, Object> data;
    private String what;

    public LogMessage(String what, Map<String, Object> data, Optional<LogMessage> parent) {
        this.parent = parent;
        this.data = data;
    }

    public LogMessage(String what, Map<String, Object> data) {
        this(what, data, Optional.empty());
    }

    public LogMessage(String what, Map<String, Object> data, LogMessage parent) {
        this(what, data, Optional.of(parent));
    }

    public Optional<LogMessage> getParent() {
        return parent;
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
}
