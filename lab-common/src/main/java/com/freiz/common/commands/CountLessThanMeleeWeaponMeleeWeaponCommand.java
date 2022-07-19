package com.freiz.common.commands;

import java.util.stream.Collectors;

import com.freiz.common.data.MeleeWeapon;
import com.freiz.common.dto.CommandResultDto;
import com.freiz.common.exception.InvalidRequestException;
import com.freiz.common.exception.NotMaxException;
import com.freiz.common.exception.NotMinException;
import com.freiz.common.network.Request;
import com.freiz.common.util.CollectionManager;
import com.freiz.common.util.HistoryManager;

public class CountLessThanMeleeWeaponMeleeWeaponCommand extends AbstractCommand {

    public CountLessThanMeleeWeaponMeleeWeaponCommand() {
        super("count_less_than_melee_weapon");
    }

/*     @Override
    public Request packageRequest(String[] args) {
        Request request = new Request("count_less_than_melee_weapon", new String[] {arg});
        return request;
    }

    @Override
    public CommandResultDto execute(Request request) {
        MeleeWeapon inpEnum;
        try {
            inpEnum = MeleeWeapon.valueOf(request.getArg(0));
        } catch (IllegalArgumentException e) {
            return new CommandResultDto("Your argument was incorrect");
        }
        String output = String.valueOf(collectionManager.countLessThanMeleeWeapon(inpEnum));
        return new CommandResultDto(output);
    } */

    @Override
    public Request packageRequest(Object[] args) throws InvalidRequestException {
        Request request = new Request("count_less_than_melee_weapon", args);
        return request;
    }

    @Override
    public CommandResultDto execute(Request request, CollectionManager collectionManager, HistoryManager historyManager)
            throws NotMaxException, NotMinException {
                historyManager.addNote(this.getName());
                if (request.getArgs().length != 2) {
                    return new CommandResultDto("Incorrect format command");
                }
                MeleeWeapon inpEnum;
                try {
                    inpEnum = MeleeWeapon.valueOf(((String) request.getArg(1)).toUpperCase());
                } catch (IllegalArgumentException e) {
                    return new CommandResultDto("Your argument was incorrect");
                }
                String output = String.valueOf(collectionManager.getSpaceMarinesCollection().stream().filter(e -> e.getMeleeWeapon().compareTo(inpEnum) < 0).collect(Collectors.counting()).toString());
                return new CommandResultDto(output);
    }
}
