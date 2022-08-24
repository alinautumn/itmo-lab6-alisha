package com.alisha.common.commands;

import com.alisha.common.data.Route;
import com.alisha.common.dto.CommandResultDto;
import com.alisha.common.utilities.CollectionManager;
import com.alisha.common.utilities.HistoryManager;

public class AddIfMinCommand extends Command {

    public AddIfMinCommand(Route arg) {
        super(arg, "add_if_min");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());

        Route route = (Route) arg;
        route.setId(collectionManager.getMaxId() + 1);

        // stream api would be worse in this case
        if (collectionManager.getMainData().isEmpty() || route.compareTo(collectionManager.getMainData().first()) < 0) {
            collectionManager.getMainData().add(route);
            return new CommandResultDto("The element was added successfully");
        } else {
            return new CommandResultDto("The element was not min, so it was not added");
        }
    }
}
