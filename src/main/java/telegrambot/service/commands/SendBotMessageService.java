package telegrambot.service.commands;

public interface SendBotMessageService {
    void sendMessage (String chatId, String message);
}
