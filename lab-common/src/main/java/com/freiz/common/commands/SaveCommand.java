package com.freiz.common.commands;

import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.FileManager;
import com.freiz.common.util.HistoryManager;
import com.freiz.common.util.JsonParser;

import java.io.IOException;

public class SaveCommand {
    private final FileManager fileManager;
    public SaveCommand(FileManager fileManager) {
        this.fileManager = fileManager;
    }

/*     @Override
    public Request packageRequest(String[] args) {
        Request request = new Request("save", new String[] {});
        return request;
    }

    @Override
    public CommandResultDto execute(Request request) {
        try {
            fileManager.save(JsonParser.toJson(collectionManager.getSpaceMarinesCollection()));
        } catch (IOException e) {
            return new CommandResultDto("There was a problem saving a file. Please restart the program with another one");
        }
        return new CommandResultDto("The data was saved successfully");
    } */
    public CommandResultDto execute(CollectionManager collectionManager, HistoryManager historyManager) {
        try {
            fileManager.save(JsonParser.serialize(collectionManager.getSpaceMarinesCollection()));
        } catch (IOException e) {
            return new CommandResultDto("There was a problem saving a file. Please restart the program with another one");
        }
        return new CommandResultDto("The data was saved successfully");
    }

}
