package telegrambot.service.commands;

import telegrambot.command.Command;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.service.clients.Client;
import telegrambot.service.clients.ClientContainer;

public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final ClientContainer clientContainer;
    public final static String START_MESSAGE = "Привет! Я телеграм-бот. " +
            "Буду вашим личным помощником в ведении дел.";

    public final static String NO_NOTES_MESSAGE = "Заметок не обнаружено";
    public final static String NO_CLIENT_ID_MESSAGE = "В приложении заметок включите и выключите телеграм ассистента" +
            " для правильной привязки приложения к телеграм";



    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
        clientContainer = ClientContainer.getInstance();
    }

    @Override
    public void execute(Update update, String clientId) {
        Long chatId = update.getMessage().getChatId();
        if (clientId == null) {
            sendBotMessageService.sendMessage(chatId.toString(), NO_CLIENT_ID_MESSAGE);
            return;
        }

        System.out.println("i'm in execute method");
        Client client = clientContainer.getClient(clientId);
        System.out.println(client);
        if (client == null) {
            clientContainer.putClient(new Client(clientId,chatId.toString()));
        }

        sendBotMessageService.sendMessage(chatId.toString(), START_MESSAGE);
        try {
            sendBotMessageService.sendMessage(chatId.toString(), client.getListOfNotes().toString());
        }
        catch (NullPointerException e) {
            sendBotMessageService.sendMessage(chatId.toString(), NO_NOTES_MESSAGE);
        }
    }
}
