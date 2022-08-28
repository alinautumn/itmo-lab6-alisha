package com.alisha.client.utilities;

import com.alisha.common.data.Coordinates;
import com.alisha.common.data.LocationFrom;
import com.alisha.common.data.LocationTo;
import com.alisha.common.data.Route;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

public class RouteMaker {
    private static final String ERROR_MESSAGE = "Your enter was not correct type. Try again";
    private final OutputManager outputManager;
    private final Asker asker;

    public RouteMaker(InputManager inputManager, OutputManager outputManager) {
        this.outputManager = outputManager;
        this.asker = new Asker(inputManager, outputManager);
    }

    public Route makeRoute() throws IOException {
        return askForRoute();
    }

    @NotNull
    @Contract(" -> new")
    private Route askForRoute() throws IOException {
        outputManager.println("Enter route data");
        String name = asker.ask(arg -> (arg).length() > 0, "Enter name (String)",
                ERROR_MESSAGE, "The string must not be empty", x -> x, false);
        Coordinates coordinates = askForCoordinates(); //not null
        LocationFrom from = askForLocationFrom(); //not null
        LocationTo to = askForLocationTo(); //not null
        long distance = asker.ask(arg -> true, "Enter distance (long)", ERROR_MESSAGE,
                ERROR_MESSAGE, Long::parseLong, false);
        return new Route(name, coordinates, from, to, distance);
    }

    private LocationTo askForLocationTo() throws IOException {
        outputManager.println("Enter LocationTo data");
        String name = asker.ask(arg -> (arg).length() > 0, "Enter name (String) (can be null)",
                ERROR_MESSAGE, "The string must not be empty. Try again", x -> x, true);
        double x = asker.ask(arg -> true, "Enter x (double)", ERROR_MESSAGE,
                ERROR_MESSAGE, Double::parseDouble, false);
        Integer y = asker.ask(arg -> true, "Enter y (Double)", ERROR_MESSAGE,
                ERROR_MESSAGE, Integer::parseInt, false);

        return new LocationTo(x, y, name);
    }

    @Contract(" -> new")
    private @NotNull Coordinates askForCoordinates() throws IOException {
        outputManager.println("Enter coordinates data");
        Double x = asker.ask(arg -> true, "Enter x (Double)", ERROR_MESSAGE,
                ERROR_MESSAGE, Double::parseDouble,false);
        Float y = asker.ask(arg -> true, "Enter y (Float)", ERROR_MESSAGE,
                ERROR_MESSAGE, Float::parseFloat,false);
        return new Coordinates(x, y);
    }

    private LocationFrom askForLocationFrom() throws IOException {
        outputManager.println("Enter locationFrom data");
        String name = asker.ask(arg -> (arg).length() > 0, "Enter name (String) (can be null)",
                ERROR_MESSAGE, "The string must not be empty. Try again", x -> x, true);
        Double x = asker.ask(arg -> true, "Enter x (Double)", ERROR_MESSAGE,
                ERROR_MESSAGE, Double::parseDouble, false);
        Double y = asker.ask(arg -> true, "Enter y (Double)", ERROR_MESSAGE,
                ERROR_MESSAGE, Double::parseDouble, false);

        return new LocationFrom(x, y, name);
    }

    public static class Asker {
        private final InputManager inputManager;
        private final OutputManager outputManager;


        public Asker(InputManager inputManager, OutputManager outputManager) {
            this.inputManager = inputManager;
            this.outputManager = outputManager;
        }

        public <T> T ask(Predicate<T> predicate,
                         String askMessage,
                         String errorMessage,
                         String wrongValueMessage,
                         Function<String, T> converter,
                         boolean nullable) {
            outputManager.println(askMessage);
            String input;
            T value;
            do {
                try {
                    input = inputManager.nextLine();
                    if ("".equals(input) && nullable) {
                        return null;
                    }

                    value = converter.apply(input);

                } catch (IllegalArgumentException | IOException e) {
                    outputManager.println(errorMessage);
                    continue;
                }
                if (predicate.test(value)) {
                    return value;
                } else {
                    outputManager.println(wrongValueMessage);
                }
            } while (true);
        }
    }
}
