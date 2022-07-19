package com.freiz.common.commands;

import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.exception.NotMinException;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.HistoryManager;

public class HistoryCommand extends AbstractCommand {

    public HistoryCommand() {
        super("history");
    }

/*     @Override
    public Request packageRequest(String[] args) {
        Request request = new Request("history", new String[] {});
        return request;
    }

    @Override
    public CommandResultDto execute(Request request) {
        return new CommandResultDto(historyManager.niceToString());
    } */

    @Override
    public Request packageRequest(Object[] args) throws InvalidRequestException {
        Request request = new Request("history", new Object[] {});
        return request;
    }

    @Override
    public CommandResultDto execute(Request request, CollectionManager collectionManager, HistoryManager historyManager)
            throws NotMaxException, NotMinException {
        historyManager.addNote(this.getName());
        return new CommandResultDto(historyManager.niceToString());
    }

}
