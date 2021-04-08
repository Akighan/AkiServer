package telegrambot.command;

public enum CommandName {
    START ("/start"),
    STOP ("/stop"),
    HELP ("/help"),
    TIMER ("/timer"),
    DELETE ("/delete"),
    NO ("");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
