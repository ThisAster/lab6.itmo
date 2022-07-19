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

public class AddIfMinCommand extends AbstractCommand {
    private final OutputManager outputManager;
    private final InputManager inputManager;

    public AddIfMinCommand(InputManager inputManager, OutputManager outputManager) {
        super("add_if_min");
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }

    @Override
    public Request packageRequest(Object[] args) throws InvalidRequestException {
        SpaceMarine spaceMarine = AddElem.add(inputManager, outputManager);
        Request request = new Request("add", new Object[] {spaceMarine});
        return request;
    }

    @Override
    public CommandResultDto execute(Request request, CollectionManager collectionManager, HistoryManager historyManager)
            throws NotMaxException, NotMinException {
                historyManager.addNote(this.getName());
                SpaceMarine willAdedElem = (SpaceMarine) request.getArg(0);
                long countLessThan = collectionManager.getSpaceMarinesCollection().stream().filter(x -> willAdedElem.compareTo(x) >= 0).count();
                if (countLessThan > 0) {
                    System.out.println();
                    throw new NotMinException();
                }
                collectionManager.getSpaceMarinesCollection().add(willAdedElem);
                return new CommandResultDto("succes added");
    }

}
