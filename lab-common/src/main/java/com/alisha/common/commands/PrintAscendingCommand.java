package com.alisha.common.commands;

import com.alisha.common.dto.CommandResultDto;
import com.alisha.common.utilities.CollectionManager;
import com.alisha.common.utilities.HistoryManager;

public class PrintAscendingCommand extends Command {

    public PrintAscendingCommand() {
        super("", "print_ascending");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        return new CommandResultDto(collectionManager.getMainData());
    }
}
