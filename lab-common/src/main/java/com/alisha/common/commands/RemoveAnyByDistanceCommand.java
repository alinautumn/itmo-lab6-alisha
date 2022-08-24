package com.alisha.common.commands;

import com.alisha.common.dto.CommandResultDto;
import com.alisha.common.utilities.CollectionManager;
import com.alisha.common.utilities.HistoryManager;

public class RemoveAnyByDistanceCommand extends Command {

    public RemoveAnyByDistanceCommand(String arg) {
        super(arg,"remove_any_by_distance");
    }

    @Override
    public CommandResultDto execute(CollectionManager collectionManager,
                                    HistoryManager historyManager)
    {
        historyManager.addNote(this.getName());
        long longArg;
        try {
            longArg = Long.parseLong((String) arg);
        } catch (NumberFormatException e) {
            return new CommandResultDto("Your argument was incorrect. The command was not executed");
        }
        if (collectionManager.getMainData().removeIf(x -> x.getDistance() == longArg)) {
            return new CommandResultDto("The element was deleted successfully");
        } else {
            return new CommandResultDto("Could not find written id. The command was not executed");
        }
    }
}
