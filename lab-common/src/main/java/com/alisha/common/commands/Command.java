package com.alisha.common.commands;

import com.alisha.common.dto.CommandResultDto;
import com.alisha.common.utilities.CollectionManager;
import com.alisha.common.utilities.HistoryManager;

import java.io.Serializable;

public abstract class Command implements Serializable {
    protected final Serializable arg;
    private final String name;

    protected Command(Serializable arg, String name) {
        this.arg = arg;
        this.name = name;
    }

    public abstract CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    );

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Command{"
                + "name='" + name + '\''
                + ", arg=" + arg
                + '}';
    }
}
