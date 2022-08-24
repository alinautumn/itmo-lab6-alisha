package com.alisha.client.commands;

import com.alisha.client.utilities.InputManager;
import com.alisha.common.dto.CommandResultDto;

import java.io.File;
import java.io.IOException;

public class ExecuteScriptCommand {
    private final String arg;

    public ExecuteScriptCommand(String arg) {
        this.arg = arg;
    }

    public void execute(InputManager inputManager) {
        try {
            inputManager.connectToFile(new File(arg));
            new CommandResultDto("Starting to execute script...");
        } catch (IOException e) {
            new CommandResultDto("There was a problem opening the file. Check if it is available and you have written it in the command arg correctly");
        } catch (UnsupportedOperationException e) {
            new CommandResultDto(e.getMessage());
        }
    }
}
