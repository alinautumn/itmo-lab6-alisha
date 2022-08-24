package com.alisha.client.utilities;

import com.alisha.client.ClientApp;
import com.alisha.client.commands.ExecuteScriptCommand;
import com.alisha.common.commands.*;
import com.alisha.common.data.Route;
import com.alisha.common.dto.CommandFromClientDto;
import com.alisha.common.exceptions.DataCantBeSentException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.NoSuchElementException;

public class Console {
    private final OutputManager outputManager;
    private final InputManager inputManager;
    private final ClientApp clientApp;
    private final RouteMaker routeMaker;
    private final Collection<String> listOfCommands;


    public Console(OutputManager outputManager, InputManager inputManager, ClientApp clientApp,
                   Collection<String> listOfCommands) {
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.clientApp = clientApp;
        this.listOfCommands = listOfCommands;
        this.routeMaker = new RouteMaker(inputManager, outputManager);
    }

    public void start() throws ClassNotFoundException, IOException {
        String input;
        do {
            input = readNextCommand();
            final String[] parsedInp = parseToNameAndArg(input);
            final String commandName = parsedInp[0];
            Serializable commandArg = parsedInp[1];
            String commandArg2 = ""; // only for update command in this case
            if (listOfCommands.contains(commandName)) {
                if ("add".equals(commandName) || "add_if_min".equals(commandName) || "add_if_max".equals(commandName)) {
                    commandArg = routeMaker.makeRoute();
                }
                if ("update".equals(commandName)) {
                    commandArg2 = (String) commandArg;
                    commandArg = routeMaker.makeRoute();
                }
                if ("execute_script".equals(commandName)) {
                    new ExecuteScriptCommand((String) commandArg).execute(inputManager);
                } else {
                    try {
                        outputManager.println(
                                clientApp.sendCommand(new CommandFromClientDto(getCommandObjectByName(commandName, commandArg, commandArg2)))
                                        .getOutput().toString()
                        );
                    } catch (DataCantBeSentException e) {
                        outputManager.println("Could not send a command");
                    }
                }

            } else {
                outputManager.println("The command was not found. Please use \"help\" to know about commands.");
            }
        } while (!"exit".equals(input));
    }

    private String[] parseToNameAndArg(String input) {
        String[] arrayInput = input.split(" ");
        String inputCommand = arrayInput[0];
        String inputArg = "";

        if (arrayInput.length >= 2) {
            inputArg = arrayInput[1];
        }

        return new String[]{inputCommand, inputArg};
    }

    private String readNextCommand() throws IOException {
        outputManager.print(">>>");
        try {
            return inputManager.nextLine();
        } catch (NoSuchElementException e) {
            return "exit";
        }
    }

    private Command getCommandObjectByName(String commandName, Serializable arg, String arg2) {
        Command command;
        switch (commandName) {
            case "add": command = new AddCommand((Route) arg);
                break;
            case "add_if_min": command = new AddIfMinCommand((Route) arg);
                break;
            case "add_if_max": command = new AddIfMaxCommand((Route) arg);
                break;
            case "clear": command = new ClearCommand();
                break;
            case "history": command = new HistoryCommand();
                break;
            case "info": command = new InfoCommand();
                break;
            case "print_ascending": command = new PrintAscendingCommand();
                break;
            case "remove_any_by_distance": command = new RemoveAnyByDistanceCommand((String) arg);
                break;
            case "remove_by_id": command = new RemoveByIdCommand((String) arg);
                break;
            case "remove_greater": command = new RemoveGreaterCommand((Route) arg);
                break;
            case "show": command = new ShowCommand();
                break;
            case "update": command = new UpdateCommand((Route) arg, arg2);
                break;
            default: command = new HelpCommand();
                break;
        }
        return command;
    }
}
