package telegrambot.command;

public enum CommandName {
    START ("/start"),
    HELP ("/help"),
    TIMER ("/timer"),
    DELETE ("/delete"),
    TIMERS ("/timers"),
    NO ("");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
