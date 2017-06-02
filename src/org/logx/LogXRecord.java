package org.logx;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Ethan Shea on 5/30/2017.
 */
public class LogXRecord {
    private Optional<LogXRecord> parent;
    private Map<String, Object> data;
    private String what;
    private LogLevel level;

    public LogXRecord(LogLevel level, String what, Map<String, Object> data, Optional<LogXRecord> parent) {
        this.level = level;
        this.parent = parent;
        this.data = data;
        this.what = what;
    }

    public LogXRecord(LogLevel level, String what, Map<String, Object> data, LogXRecord parent) {
        this(level, what, data, Optional.of(parent));
    }

    public LogXRecord(LogLevel level, String what, Map<String, Object> data) {
        this(level, what, data, Optional.empty());
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

    public Optional<LogXRecord> getParent() {
        return parent;
    }

    public LogLevel getLevel() {
        return level;
    }
}
