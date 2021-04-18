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
    public static final String NOTES_NOTIFICATION = "Ваши дела и планы:";
    public static final String NO_NOTES = "Заметок не обнаружено";


    public OnTimeMessageSender(Client client, SendBotMessageService sendBotMessageService) {
        this.client = client;
        this.sendBotMessageService = sendBotMessageService;
        newsParser = new NewsParser();
        weatherParser = new WeatherParser();
    }

    @Override
    public void run() {
        StringBuilder stringBuilder = new StringBuilder();
        if (client.isTelegramChecked()) {
            if (client.isNewsNotificationChecked()) {
                stringBuilder.append(EVERYDAY_NOTIFICATION).append("\n").append("\n");
            }

            if (client.isNewsNotificationChecked()) {
                List<String> listNews = newsParser.getListNews();
                for (String news : listNews) {
                    stringBuilder.append("\uD83D\uDCF0 ").append(news);
                }
                stringBuilder.append("\n");

            }
            if (client.isWeatherNotificationChecked() && client.getCityChosen() != 0) {
                CityContainer cityContainer = CityContainer.getInstance();
                String weather = weatherParser.getWeather(cityContainer.getCity(client.getCityChosen()));
                String[] temperatureAndCloud = weather.split("::");
                stringBuilder.append(String.format("\uD83C\uDF02 " +
                        "%s:\n\n%s°C, %s", temperatureAndCloud[0], temperatureAndCloud[1], temperatureAndCloud[2])).append("\n").append("\n");
            }

            if (client.getListOfNotes().size() != 0) {
                stringBuilder.append("\uD83D\uDCD2 ")
                        .append(NOTES_NOTIFICATION).append("\n\n");

                stringBuilder.append(client.notesToString());
            } else {
                stringBuilder.append("\uD83D\uDCD2 ")
                        .append(NO_NOTES);
            }
            if (stringBuilder.length() > 0) {
                sendBotMessageService.sendMessage(client.getChatId(), stringBuilder.toString());
            }
        }
    }
}