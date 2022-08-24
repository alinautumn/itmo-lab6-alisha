package com.alisha.common.commands;

import com.alisha.common.data.Route;
import com.alisha.common.dto.CommandResultDto;
import com.alisha.common.utilities.CollectionManager;
import com.alisha.common.utilities.HistoryManager;

public class UpdateCommand extends Command {

    private final String idArg;

    public UpdateCommand(Route route, String id) {
        super(route, "update");
        this.idArg = id;
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        int intArg;
        try {
            intArg = Integer.parseInt(idArg);
        } catch (NumberFormatException e) {
            return new CommandResultDto("Your argument was incorrect. The command was not executed.");
        }

        if (collectionManager.getMainData().removeIf(x -> x.getId() == intArg)) {
            Route route = (Route) arg;
            route.setId(intArg);
            collectionManager.getMainData().add(route);
            return new CommandResultDto("The element was updated successfully");
        } else {
            return new CommandResultDto("Written id was not found. The command was not executed");
        }
    }
}
