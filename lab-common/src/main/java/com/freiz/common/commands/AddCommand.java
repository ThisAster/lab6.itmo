package com.freiz.common.commands;
import com.freiz.common.commands.subcommand.AddElem;
import com.freiz.common.data.SpaceMarine;
import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.HistoryManager;
import com.freiz.common.util.InputManager;
import com.freiz.common.util.OutputManager;

public class AddCommand extends AbstractCommand {

    private final InputManager inputManager;
    private final OutputManager outputManager;

    public AddCommand(OutputManager outputManager, InputManager inputManager) {
        super("add");
        this.outputManager = outputManager;
        this.inputManager = inputManager;
    }


    @Override
    public Request packageRequest(Object[] args) {
        SpaceMarine spaceMarine = AddElem.add(inputManager, outputManager);
        Request request = new Request("add", new Object[] {spaceMarine});
        //historyManager.addNote(this.getName());
        //spaceMarine.setId(collectionManager.getNewID());
        //collectionManager.getHashSetId().add(spaceMarine.getId());
        //collectionManager.getSpaceMarinesCollection().add(spaceMarine);
        return request;
    }

    @Override
    public CommandResultDto execute(Request request, CollectionManager collectionManager,
            HistoryManager historyManager) {
                historyManager.addNote(this.getName());
                SpaceMarine willAddedElement = (SpaceMarine) request.getArg(0);
                willAddedElement.setId(collectionManager.getNewID());
                collectionManager.getHashSetId().add(willAddedElement.getId());
                collectionManager.getSpaceMarinesCollection().add(willAddedElement);
                return new CommandResultDto("succes added");
    }

}
