package challenges;

import java.io.Console;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class IntermediateScheduleEdit {

    record Event(String name, int hour) {}

    static final Console CON = System.console();
    static final List<Event> EVENTS = new ArrayList<>();

    static boolean isExiting;

    public static void main(String[] args) {
        while (!isExiting) {
            try {
                processCommands();
            } catch (RuntimeException e) {
                CON.format("%s%n", e.getMessage());
            }
        }
        CON.format("exiting...%n");
    }

    static void processCommands() {
        var in = CON.readLine(" > ");
        if (in == null) {
            isExiting = true;
            return;
        }

        var command = in.split("\\s+");
        if (command.length <= 0 || command[0].isBlank()) {
            throw new RuntimeException("No command found");
        }

        switch (command[0]) {
            case "help" ->
                CON.format("""
                        Command list:
                        \t- help -- output current message
                        \t- list -- ordered list of all events
                        \t- create {NAME} {HOUR} -- add new event
                        \t- update {INDEX} -- edit specific event
                        \t- delete {INDEX} -- remove specific event
                        \t- exit (CTRL+C) -- quit application
                        """);
            case "list" ->
                list();
            case "create" ->
                create(command);
            case "update" ->
                update(command);
            case "delete" ->
                delete(command);
            case "exit" ->
                isExiting = true;
            default ->
                throw new RuntimeException("Unknown command " + command[0]);
        }
    }

    static void list() {
        if (EVENTS.isEmpty()) {
            CON.format("No events added yet%n");
            return;
        }
        for (var i = 0; i < EVENTS.size(); i++) {
            CON.format("%d : %s%n", i, EVENTS.get(i));
        }
    }

    static void create(String[] command) {
        var name = "";
        try {
            name = command[1];
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("CREATE command requires {NAME} parameter", e);
        }

        var hour = 0;
        try {
            hour = Integer.parseInt(command[2]);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("CREATE command requires {HOUR} parameter", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("{HOUR} parameter must be an integer", e);
        }

        if (hour < 0 || hour > 23) {
            throw new RuntimeException("{HOUR} parameter must be in [0, 23]");
        }

        EVENTS.add(new Event(name, hour));
        EVENTS.sort(Comparator.comparingInt(Event::hour));
    }

    static void update(String[] command) {
        var index = 0;
        try {
            index = Integer.parseInt(command[1]);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("DELETE command requires {INDEX} parameter", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("{INDEX} parameter must be an integer", e);
        }

        Event event = null;
        try {
            event = EVENTS.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("There is no event with number " + index, e);
        }

        var in = CON.readLine("Input new {NAME} (%s) > ", event.name());
        if (in == null) {
            isExiting = true;
            return;
        }

        var name = in.isBlank() ? event.name() : in;

        in = CON.readLine("Input new {HOUR} (%d) > ", event.hour());
        if (in == null) {
            isExiting = true;
            return;
        }

        var hour = 0;
        try {
            hour = in.isBlank() ? event.hour() : Integer.parseInt(in);
        } catch (NumberFormatException e) {
            throw new RuntimeException("{HOUR} parameter must be an integer", e);
        }

        if (hour < 0 || hour > 23) {
            throw new RuntimeException("{HOUR} parameter must be in [0, 23]");
        }

        EVENTS.remove(index);
        EVENTS.add(new Event(name, hour));
        EVENTS.sort(Comparator.comparingInt(Event::hour));
    }

    static void delete(String[] command) {
        var index = 0;

        try {
            index = Integer.parseInt(command[1]);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("DELETE command requires {INDEX} parameter", e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("{INDEX} parameter must be an integer", e);
        }

        try {
            EVENTS.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("There is no event with number " + index, e);
        }
    }

}
