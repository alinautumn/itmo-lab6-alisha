package com.alisha.server.commands;

import com.alisha.common.commands.Command;
import com.alisha.common.dto.CommandResultDto;
import com.alisha.common.utilities.CollectionManager;
import com.alisha.common.utilities.HistoryManager;
import com.alisha.server.utilities.FileManager;
import com.alisha.server.utilities.JsonParser;

import java.io.FileNotFoundException;

public class SaveCommand extends Command {

    private final FileManager fileManager;

    public SaveCommand(FileManager fileManager) {
        super("", "save");
        this.fileManager = fileManager;
    }

    @Override
    public CommandResultDto execute(CollectionManager collectionManager, HistoryManager historyManager) {
        try {
            fileManager.save(new JsonParser().serialize(collectionManager.getMainData()));
        } catch (FileNotFoundException e) {
            return new CommandResultDto("There was a problem saving a file. Please restart the program with another one");
        }
        return new CommandResultDto("The data was saved successfully");
    }
}
