package com.freiz.common.commands;

import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.exception.NotMinException;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.HistoryManager;

public class HelpCommand extends AbstractCommand {
    public HelpCommand() {
        super("help");
    }

/*     @Override
    public Request packageRequest(String[] args) {
        Request request = new Request("help", new String[] {});
        return request;
    }

    @Override
    public CommandResultDto execute(Request request) {
        return new CommandResultDto(
            "help : output help for the available commands\n"
            + "info : output to the standard output stream information about the collection (type, initialization date, number of elements, etc.)\n"
            + "show : output to the standard output stream all the elements of the collection in the string representation \n"
            + "add {element} : add a new element to the collection\n"
            + "update id {element} : update the value of a collection element whose id is equal to the specified \n"
            + "remove_by_id id : remove an item from the collection by its id\n"
            + "clear : clear collection\n"
            + "save : save the collection to a file \n"
            + "execute_script file_name : read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode.\n"
            + "exit : finish the program");
    } */

    @Override
    public Request packageRequest(Object[] args) throws InvalidRequestException {
        Request request = new Request("help", new Object[] {});
        return request;
    }

    @Override
    public CommandResultDto execute(Request request, CollectionManager collectionManager, HistoryManager historyManager)
            throws NotMaxException, NotMinException {
                historyManager.addNote(this.getName());
                return new CommandResultDto(
                    "help : output help for the available commands\n"
                    + "info : output to the standard output stream information about the collection (type, initialization date, number of elements, etc.)\n"
                    + "show : output to the standard output stream all the elements of the collection in the string representation \n"
                    + "add {element} : add a new element to the collection\n"
                    + "update id {element} : update the value of a collection element whose id is equal to the specified \n"
                    + "remove_by_id id : remove an item from the collection by its id\n"
                    + "clear : clear collection\n"
                    + "save : save the collection to a file \n"
                    + "execute_script file_name : read and execute the script from the specified file. The script contains commands in the same form in which they are entered by the user in interactive mode.\n"
                    + "exit : finish the program");
    }

}

