package telegrambot.service.sendontime;

import telegrambot.service.clients.Client;
import telegrambot.service.commands.SendBotMessageService;
import telegrambot.service.parser.CityContainer;
import telegrambot.service.parser.NewsParser;
import telegrambot.service.parser.WeatherParser;

import java.util.*;

public class OnTimeMessageSender extends TimerTask {
    private final Client client;
    private final SendBotMessageService sendBotMessageService;
    private final NewsParser newsParser;
    private final WeatherParser weatherParser;
    public static final String EVERYDAY_NOTIFICATION = "И снова здравствуйте! Подготовил для Вас " +
            "пару новостей.";
    public static final String NOTES_NOTIFICATION = "Так же не забудьте:";
    public static final String NO_NOTES = "Заметок не обнаружено";


    public OnTimeMessageSender(Client client, SendBotMessageService sendBotMessageService) {
        this.client = client;
        this.sendBotMessageService = sendBotMessageService;
        newsParser = new NewsParser();
        weatherParser = new WeatherParser();
    }

    @Override
    public void run() {
        if (client.isTelegramChecked()) {
            String listNews = newsParser.getListNews();

            sendBotMessageService.sendMessage(client.getChatId(), EVERYDAY_NOTIFICATION);

            sendBotMessageService.sendMessage(client.getChatId(), listNews);

            if (client.isWeatherNotificationChecked()) {
                CityContainer cityContainer = CityContainer.getInstance();
                String weather = weatherParser.getListNews(cityContainer.getCity(client.getCityChosen()));
                sendBotMessageService.sendMessage(client.getChatId(), "Погода в Вашем городе \n");
                sendBotMessageService.sendMessage(client.getChatId(), weather);
            }

            if (client.getListOfNotes().size() != 0) {
                sendBotMessageService.sendMessage(client.getChatId(), NOTES_NOTIFICATION);

                sendBotMessageService.sendMessage(client.getChatId(), client.notesToString());
            } else {
                sendBotMessageService.sendMessage(client.getChatId(), NO_NOTES);
            }
        }
    }
}