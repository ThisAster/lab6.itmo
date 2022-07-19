package com.freiz.common.commands;

import java.util.Set;
import java.util.stream.Collectors;

import com.freiz.common.data.SpaceMarine;
import com.freiz.common.data.Weapon;
import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.exception.NotMinException;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.HistoryManager;

public class FilterByWeaponTypeCommand extends AbstractCommand {

    public FilterByWeaponTypeCommand() {
        super("filter_by_weapon_type");
    }

/*     @Override
    public Request packageRequest(String[] args) throws InvalidRequestException {
        Request request = new Request("filter_by_weapon_type", new String[] {arg});
        return request;
    }

    @Override
    public CommandResultDto execute(Request request) throws InvalidRequestException, NotMaxException, NotMinException {
        Weapon inpEnum;
        try {
            inpEnum = Weapon.valueOf(request.getArg(0));
        } catch (IllegalArgumentException e) {
            return new CommandResultDto("Your argument was incorrect");
        }
        String output = String.valueOf(collectionManager.filterByWeaponType(inpEnum));
        return new CommandResultDto(output);
    } */

    @Override
    public Request packageRequest(Object[] args) throws InvalidRequestException {
        Request request = new Request("filter_by_weapon_type", args);
        return request;
    }

    @Override
    public CommandResultDto execute(Request request, CollectionManager collectionManager, HistoryManager historyManager) throws NotMaxException, NotMinException {
        historyManager.addNote(this.getName());
        Weapon inpEnum;
        try {
            if (request.getArgs().length != 2) {
                return new CommandResultDto("Incorrect format command");
            }
            inpEnum = Weapon.valueOf(((String) request.getArg(1)).toUpperCase());
        } catch (IllegalArgumentException e) {
            return new CommandResultDto("Your argument was incorrect");
        }
        Set<SpaceMarine> output = collectionManager.getSpaceMarinesCollection().stream().filter(it -> it.getWeaponType().equals(inpEnum)).collect(Collectors.toSet());
        String castOutput = String.valueOf(output);
        return new CommandResultDto(castOutput);
    }
}


