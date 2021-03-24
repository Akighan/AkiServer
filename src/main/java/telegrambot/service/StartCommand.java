package telegrambot.service;

import telegrambot.command.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    public final static String START_MESSAGE = "Привет! Я телеграм-бот. Буду вашим личным помощником в ведении дел. Пожалуйста, скопируйте следующий ключ" +
            " в поле программы заметок.";

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        sendBotMessageService.sendMessage(chatId.toString(), START_MESSAGE);
        sendBotMessageService.sendMessage(chatId.toString(), chatId.toString());
    }
}
