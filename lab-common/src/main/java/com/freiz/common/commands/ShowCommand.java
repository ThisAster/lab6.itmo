package com.freiz.common.commands;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.freiz.common.data.SpaceMarine;
import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.exception.NotMinException;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.HistoryManager;

public class ShowCommand extends AbstractCommand {

    public ShowCommand() {
        super("show");
    }
    @Override
    public Request packageRequest(Object[] args) throws InvalidRequestException {
        Request request = new Request("show", new Object[] {});
        return request;
    }

    @Override
    public CommandResultDto execute(Request request, CollectionManager collectionManager, HistoryManager historyManager) throws NotMaxException, NotMinException {
        historyManager.addNote(this.getName());
        List<SpaceMarine> output = collectionManager.getSpaceMarinesCollection().stream().sorted(Comparator.comparing(SpaceMarine::getName)).collect(Collectors.toList());
        String castOutput = output.toString();
        return new CommandResultDto(castOutput);
    }
}




