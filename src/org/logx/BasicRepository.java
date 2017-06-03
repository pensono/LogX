package org.logx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class BasicRepository extends LogXRepository {
    private List<LogXMessage> records = new ArrayList<>();
    private Collection<LogXHandler> handlers = new HashSet<>();

    @Override
    public void addHandler(LogXHandler handler) {
        handlers.add(handler);
    }

    @Override
    public void handle(LogXMessage record) {
        records.add(record);
        handlers.forEach(h -> h.handle(record));
    }

    @Override
    public List<LogXMessage> allMessages() {
        // TODO defensive copy
        return records;
    }

    @Override
    public List<LogXMessage> recordsWithMessageContaining(String message) {
        return records.stream()
                .filter(m -> m.getMessage().contains(message))
                .collect(Collectors.toList());
    }
}
