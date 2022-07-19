package com.freiz.common.commands;

import java.util.stream.Collectors;

import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.exception.NotMinException;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.HistoryManager;

public class CountGreaterThanHeartCountCommand extends AbstractCommand {

    public CountGreaterThanHeartCountCommand() {
        super("count_greater_than_heart_count");
    }

    @Override
    public Request packageRequest(Object[] args) throws InvalidRequestException {
        Request request = new Request("count_greater_than_heart_count", args);
        return request;
    }

    @Override
    public CommandResultDto execute(Request request, CollectionManager collectionManager, HistoryManager historyManager)
            throws NotMaxException, NotMinException {
                historyManager.addNote(this.getName());
                if (request.getArgs().length != 2) {
                    return new CommandResultDto("Incorrect format command");
                }
                int heartCount;
                try {
                    String arg = (String) request.getArg(1);
                    heartCount = Integer.parseInt(arg);
                    return new CommandResultDto(collectionManager.getSpaceMarinesCollection().stream().filter(spaceMarine -> spaceMarine.getHeartCount() > heartCount).collect(Collectors.counting()).toString());
                } catch (NumberFormatException e) {
                    return new CommandResultDto(e.getMessage());
                }
    }
}
