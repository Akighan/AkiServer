package telegrambot.service.commands;

import telegrambot.command.Command;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.service.clients.Client;
import telegrambot.service.clients.ClientContainer;

import java.util.Comparator;
import java.util.List;

public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final ClientContainer clientContainer;
    public final static String START_MESSAGE_SHORT = "Привет! Я телеграм-бот. " +
            "Буду вашим личным помощником в ведении дел. Помощь по командам: /help \n\n";
    public final static String START_MESSAGE = START_MESSAGE_SHORT + "Установить время получения оповещений Вы" +
            " можете командой \n/timer чч:мм \n(например: /timer 08:49)\n Так же доступна установка сразу " +
            "нескольких таймеров через запятую \n(например: /timer 07:00, 16:50)";

    public final static String NO_CLIENT_ID_MESSAGE = "В приложении заметок выключите и включите телеграм ассистента" +
            " для правильной привязки приложения к телеграм.";


    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
        clientContainer = ClientContainer.getInstance();
    }

    @Override
    public void execute(Update update) {
        String clientId = null;
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText().trim();
        Client client = null;
        if (message.split(" ").length > 1) {
            clientId = message.split(" ")[1];
        }

        if (clientId == null) {
            client = clientContainer.getClientByChatId(chatId.toString());
            if (client == null) {
                sendBotMessageService.sendMessage(chatId.toString(), NO_CLIENT_ID_MESSAGE);
                return;
            }

        } else {
            do {
            client = clientContainer.getClientByClientId(clientId);}
            while (client == null);
        }
        if (client.getChatId() == null) {
            client.setChatId(chatId.toString());
            clientContainer.putClient(client);
            sendBotMessageService.sendMessage(chatId.toString(), START_MESSAGE);
        } else {
            sendBotMessageService.sendMessage(chatId.toString(), START_MESSAGE_SHORT);
            if (client.isTelegramChecked() && client.getListOfTimes() != null && client.getListOfTimes().size() > 0) {
                List<String> sortedTimes = client.getListOfTimes();
                sortedTimes.sort((o1, o2) -> {
                    int firstTime = Integer.parseInt(o1.replace(":", ""));
                    int secondTime = Integer.parseInt(o2.replace(":", ""));
                    return Integer.compare(firstTime, secondTime);
                });

                sendBotMessageService.sendMessage(chatId.toString(), "Ежедневные оповещения установлены на следующее время:\n" + sortedTimes);
            }
        }
    }
}
