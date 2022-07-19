package com.freiz.common.commands;

import com.freiz.common.commands.subcommand.AddElem;
import com.freiz.common.data.SpaceMarine;
import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.exception.NotMinException;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.HistoryManager;
import com.freiz.common.util.InputManager;
import com.freiz.common.util.OutputManager;

public class UpdateCommand extends AbstractCommand {
    private final OutputManager outputManager;
    private final InputManager inputManager;


    public UpdateCommand(InputManager inputManager, OutputManager outputManager) {
        super("update");
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }
    @Override
    public Request packageRequest(Object[] args) throws InvalidRequestException {
        SpaceMarine spaceMarine = AddElem.add(inputManager, outputManager);
        Request request = new Request("update", new Object[] {args[0], args[1], spaceMarine});

        return request;
    }

    @Override
    public CommandResultDto execute(Request request, CollectionManager collectionManager, HistoryManager historyManager)
            throws NotMaxException, NotMinException {
                historyManager.addNote(this.getName());
                String castArg = (String) request.getArg(1);
                Long id;
                try {
                    id = Long.parseLong(castArg);
                } catch (NumberFormatException e) {
                    return new CommandResultDto("Your argument was incorrect. The command was not executed.");
                }
                if (!collectionManager.getSpaceMarinesCollection().stream().anyMatch(x -> x.getId().equals(id))) {
                    return new CommandResultDto("have not this id");
                }
                SpaceMarine spaceMarine = (SpaceMarine) request.getArg(2);
                spaceMarine.setId(id);
                if (collectionManager.getSpaceMarinesCollection().stream().anyMatch(x -> x.getId().equals(id))) {
                    SpaceMarine thisSpaceMarine = collectionManager.getSpaceMarinesCollection().stream().filter(x -> x.getId().equals(id)).findAny().get();
                    collectionManager.getHashSetId().remove(thisSpaceMarine.getId());
                    collectionManager.getSpaceMarinesCollection().remove(thisSpaceMarine);
                    collectionManager.getHashSetId().remove(thisSpaceMarine.getId());
                } else {
                    return new CommandResultDto("not succes");
                }
                collectionManager.getSpaceMarinesCollection().add(spaceMarine);
                return new CommandResultDto("succes added");
    }
}
