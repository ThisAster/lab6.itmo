package com.freiz.common.util;

import java.util.Queue;
import java.util.StringJoiner;
import java.util.concurrent.ArrayBlockingQueue;

public class HistoryManagerImpl implements HistoryManager {
    private static final int CAPACITY = 6;
    private final Queue<String> history = new ArrayBlockingQueue<>(CAPACITY);

    public void addNote(String note) {
        if (history.size() == CAPACITY) {
            history.remove();
        }
        history.add(note);
    }

    public String niceToString() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("The last 6 commands were:");
        for (String commandName : history) {
            stringJoiner.add(commandName);
        }
        return stringJoiner.toString();
    }
}
