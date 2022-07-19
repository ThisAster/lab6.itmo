package com.freiz.common.commands;

import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.exception.NotMinException;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.HistoryManager;

public class InfoCommand extends AbstractCommand {

    public InfoCommand() {
        super("info");
    }
    @Override
    public Request packageRequest(Object[] args) throws InvalidRequestException {
        Request request = new Request("info", new Object[] {});
        return request;
    }

    @Override
    public CommandResultDto execute(Request request, CollectionManager collectionManager, HistoryManager historyManager) throws NotMaxException, NotMinException {
        historyManager.addNote(this.getName());
        return new CommandResultDto("Collection type: " + collectionManager.getSpaceMarinesCollection().getClass().toString() + "\n"
        + "Count elem: " + collectionManager.getSpaceMarinesCollection().size() + "\n"
        + "Creation date: " + collectionManager.getCreationDate());
    }
}
