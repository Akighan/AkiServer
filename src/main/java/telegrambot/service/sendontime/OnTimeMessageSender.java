package telegrambot.service.sendontime;

import telegrambot.bot.TelegramBot;
import telegrambot.service.clients.Client;
import telegrambot.service.clients.ClientContainer;
import telegrambot.service.commands.SendBotMessageService;
import telegrambot.service.commands.TimerCommand;
import telegrambot.service.parser.NewsParser;

import java.util.*;

public class OnTimeMessageSender extends TimerTask {
    private final Client client;
    private final SendBotMessageService sendBotMessageService;
    private final NewsParser newsParser;
    public static final String EVERYDAY_NOTIFICATION = "И снова здравствуйте! Подготовил для Вас " +
            "пару новостей.";
    public static final String NOTES_NOTIFICATION = "Так же не забудьте:";
    public static final String NO_NOTES = "Заметок не обнаружено";


    public OnTimeMessageSender(Client client, SendBotMessageService sendBotMessageService) {
        this.client = client;
        this.sendBotMessageService = sendBotMessageService;
        newsParser = new NewsParser();
    }

    @Override
    public void run() {
        String listNews = newsParser.getListNews();

        sendBotMessageService.sendMessage(client.getChatId(), EVERYDAY_NOTIFICATION);

        sendBotMessageService.sendMessage(client.getChatId(), listNews);


        if (client.getListOfNotes().size() != 0) {
            sendBotMessageService.sendMessage(client.getChatId(), NOTES_NOTIFICATION);

            sendBotMessageService.sendMessage(client.getChatId(), client.notesToString());
        } else {
            sendBotMessageService.sendMessage(client.getChatId(), NO_NOTES);
        }
    }

}
