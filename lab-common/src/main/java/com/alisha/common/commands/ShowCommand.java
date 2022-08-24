package com.alisha.common.commands;

import com.alisha.common.data.Route;
import com.alisha.common.dto.CommandResultDto;
import com.alisha.common.utilities.CollectionManager;
import com.alisha.common.utilities.HistoryManager;

import java.io.Serializable;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ShowCommand extends Command {

    public ShowCommand() {
        super("", "show");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        // sorting by name
        return new CommandResultDto((Serializable) collectionManager.getMainData().stream().sorted(Comparator.comparing(Route::getName)).collect(Collectors.toList()));
    }
}
