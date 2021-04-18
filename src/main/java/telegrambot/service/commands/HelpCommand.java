package telegrambot.service.commands;

import telegrambot.command.Command;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.service.clients.Client;
import telegrambot.service.clients.ClientContainer;

import static telegrambot.command.CommandName.*;

public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("✨Доступные команды✨\n\n"

                    + "%s - начать работу со мной\n"
                    + "%s - получить помощь в работе со мной\n" +
                    "%s - задать ежедневные оповещения в формате %3$s чч:мм\n" +
                    "%s - в формате %4$s чч:мм удаляет оповещение на заданное время.\n" +
                    "Вы так же можете удалить все оповещения командой %4$s all\n" +
                    "%s - узнать какие таймеры уже установлены\n",
            START.getCommandName(), HELP.getCommandName(), TIMER.getCommandName(), DELETE.getCommandName(), TIMERS.getCommandName());

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
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
