package com.alisha.common.commands;

import com.alisha.common.data.Route;
import com.alisha.common.dto.CommandResultDto;
import com.alisha.common.utilities.CollectionManager;
import com.alisha.common.utilities.HistoryManager;

public class RemoveGreaterCommand extends Command {

    public RemoveGreaterCommand(Route arg) {
        super(arg, "remove_greater");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        Route route = (Route) arg;
        collectionManager.getMainData().removeIf(x -> x.compareTo(route) > 0);
        return new CommandResultDto("Greater elements were removed successfully");
    }
}
