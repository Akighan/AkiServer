package telegrambot.service.commands;

import telegrambot.command.Command;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.service.clients.Client;
import telegrambot.service.clients.ClientContainer;

import static telegrambot.command.CommandName.*;

public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("✨Доступные команды✨\n\n"

                    + "Начать\\закончить работу с ботом\n"
                    + "%s - начать работу со мной\n"
                    + "%s - приостановить работу со мной\n\n"
                    + "%s - получить помощь в работе со мной\n" +
                    "%s - задать ежедневные оповещения в формате %4$s чч:мм",
            START.getCommandName(), STOP.getCommandName(), HELP.getCommandName(), TIMER.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        Client client = ClientContainer.getInstance().getClientByChatId(chatId.toString());
        if (client == null) {
            sendBotMessageService.sendMessage(chatId.toString(), StartCommand.NO_CLIENT_ID_MESSAGE);
            return;
        }
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(),HELP_MESSAGE);
    }
}
