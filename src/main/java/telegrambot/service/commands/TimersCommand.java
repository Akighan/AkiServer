package telegrambot.service.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.command.Command;
import telegrambot.service.clients.Client;
import telegrambot.service.clients.ClientContainer;

import java.util.List;

public class TimersCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final ClientContainer clientContainer;

    public TimersCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
        clientContainer = ClientContainer.getInstance();
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        Client client = clientContainer.getClientByChatId(chatId);

        if (client.isTelegramChecked() && client.getTimesMap() != null && client.getTimesMap().size() > 0) {
            List<String> sortedTimes = client.getSortedTimesMapKeys();

            sendBotMessageService.sendMessage(chatId, "Ежедневные оповещения установлены на следующее время:\n" + sortedTimes);
        }
        else sendBotMessageService.sendMessage(chatId, "Ежедневные оповещения не установлены");
    }
}
