package com.alisha.common.commands;

import com.alisha.common.dto.CommandResultDto;
import com.alisha.common.utilities.CollectionManager;
import com.alisha.common.utilities.HistoryManager;

public class HelpCommand extends Command {

    public HelpCommand() {
        super("", "help");
    }

    @Override
    public CommandResultDto execute(
            CollectionManager collectionManager,
            HistoryManager historyManager
    ) {
        historyManager.addNote(this.getName());
        // stream api would not help
        return new CommandResultDto(
                "help : gives information about available commands\n"
                        + "info : gives information about collection\n"
                        + "show : shows every element in collection with string\n"
                        + "add {element} : adds new element to collection\n"
                        + "update id {element} : update element info by it's id\n"
                        + "remove_by_id id : delete element by it's id\n"
                        + "clear : clears collection\n"
                        + "save : saves collection to a file\n"
                        + "execute_script file_name : executes script entered in a file\n"
                        + "exit : exits the program (!!!does not save data!!!)\n"
                        + "add_if_min {element} : adds new element to the collection if it's value less than min element's value\n"
                        + "add_if_max {element} : adds new element to the collection if it's value less than that of the smallest item in this collection\n"
                        + "history : shows last 14 command usages\n"
                        + "remove_any_by_distance : deletes one element from the collection, the value of the distance field of which is equivalent to the specified\n"
                        + "remove_greater {element} : deletes every element in the collection with value more than entered element's value\n"
                        + "print_ascending : prints every element in ascending order");
    }
}
