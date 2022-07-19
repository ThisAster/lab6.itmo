package com.freiz.common.commands;

import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.exception.NotMinException;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.HistoryManager;

public abstract class AbstractCommand {

    private final String name;

    protected AbstractCommand(String name) {
        this.name = name;
    }

    public abstract Request packageRequest(Object[] args) throws InvalidRequestException;
    public abstract CommandResultDto execute(Request request, CollectionManager collectionManager, HistoryManager historyManager) throws NotMaxException, NotMinException;
    public String getName() {
        return name;
    }
}
