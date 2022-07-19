package com.freiz.common.commands;


import com.freiz.common.commands.subcommand.AddElem;
import com.freiz.common.data.SpaceMarine;
import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.HistoryManager;
import com.freiz.common.util.InputManager;
import com.freiz.common.util.OutputManager;

public class AddIfMaxCommand extends AbstractCommand {
    private final OutputManager outputManager;
    private final InputManager inputManager;

    public AddIfMaxCommand(InputManager inputManager, OutputManager outputManager) {
        super("add_if_max");
        this.outputManager = outputManager;
        this.inputManager = inputManager;
    }

    @Override
    public Request packageRequest(Object[] args) throws InvalidRequestException {
        SpaceMarine spaceMarine = AddElem.add(inputManager, outputManager);
        Request request = new Request("add", new Object[] {spaceMarine});
        return request;
    }


    @Override
    public CommandResultDto execute(Request request, CollectionManager collectionManager,
            HistoryManager historyManager) throws NotMaxException {
                historyManager.addNote(this.getName());
                SpaceMarine willAdedElem = (SpaceMarine) request.getArg(0);
                long countGreaterThan = collectionManager.getSpaceMarinesCollection().stream().filter(x -> willAdedElem.compareTo(x) <= 0).count();
                if (countGreaterThan > 0) {
                    System.out.println();
                    throw new NotMaxException();
                }
                collectionManager.getSpaceMarinesCollection().add(willAdedElem);
                return new CommandResultDto("Succes added");
    }

}
