package com.alisha.common.commands;

import com.alisha.common.data.Route;
import com.alisha.common.dto.CommandResultDto;
import com.alisha.common.utilities.CollectionManager;
import com.alisha.common.utilities.HistoryManager;

public class AddCommand extends Command {

    public AddCommand(Route arg) {
        super(arg, "add");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        // stream api would not help
        Route route = (Route) arg;
        route.setId(collectionManager.getMaxId() + 1);
        collectionManager.getMainData().add(route);
        return new CommandResultDto("The element was added successfully");
    }
}
