package com.alisha.common.commands;

import com.alisha.common.dto.CommandResultDto;
import com.alisha.common.utilities.CollectionManager;
import com.alisha.common.utilities.HistoryManager;

public class HistoryCommand extends Command {

    public HistoryCommand() {
        super("", "history");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        return new CommandResultDto(historyManager.niceToString());
    }
}
