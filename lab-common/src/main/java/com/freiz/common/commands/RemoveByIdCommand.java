package com.freiz.common.commands;

import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.exception.NotMinException;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.HistoryManager;

public class RemoveByIdCommand extends AbstractCommand {

    public RemoveByIdCommand() {
        super("remove_by_id");
    }


/*     @Override
    public Request packageRequest(String[] args) {
        Request request = new Request("remove_by_id", new String[] {arg});
        return request;
    }

    @Override
    public CommandResultDto execute(Request request) {
        Long id;
        try {
            id = Long.parseLong(request.getArg(0));
        } catch (NumberFormatException e) {
            return new CommandResultDto("Your argument was incorrect. The command was not executed.");
        }
        if (collectionManager.removeByID(id)) {
            return new CommandResultDto("remove success");
        }
        return new CommandResultDto("not SpaceMarine with id");
    }
 */

    @Override
    public Request packageRequest(Object[] args) throws InvalidRequestException {
        Object arg = new Object();
        Request request = new Request("remove_by_id", new Object[] {arg});
        return request;
    }


    @Override
    public CommandResultDto execute(Request request, CollectionManager collectionManager, HistoryManager historyManager)
            throws NotMaxException, NotMinException {
                historyManager.addNote(this.getName());
                String castArg = (String) request.getArg(0);
                Long id;
                try {
                    id = Long.parseLong(castArg);
                } catch (NumberFormatException e) {
                    return new CommandResultDto("Your argument was incorrect. The command was not executed.");
                }
                if (collectionManager.getSpaceMarinesCollection().removeIf(x -> x.getId() == id)) {
                    return new CommandResultDto("The element was deleted successfully.");
                } else {
                    return new CommandResultDto("Could not find written id. The command was not executed");
                }
    }
}
