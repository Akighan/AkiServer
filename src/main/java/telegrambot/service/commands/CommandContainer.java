package telegrambot.service.commands;

import telegrambot.command.Command;
import com.google.common.collect.ImmutableMap;
import static telegrambot.command.CommandName.*;

public class CommandContainer {

    private final ImmutableMap commandMap;
    private final Command unknownCommand;

    public CommandContainer (SendBotMessageService sendBotMessageService) {
        TimerCommand timerCommand = new TimerCommand(sendBotMessageService);
        timerCommand.new TimerMessageSetter ().initTimers();

        commandMap = ImmutableMap.builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put (TIMER.getCommandName(), timerCommand)
                .put(DELETE.getCommandName(), new DeleteCommand(sendBotMessageService))
                .build();
        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand (String commandIdentifier) {
        return (Command) commandMap.getOrDefault(commandIdentifier,unknownCommand);
    }
}
