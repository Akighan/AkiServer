package telegrambot.service.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.bot.TelegramBot;
import telegrambot.command.Command;
import telegrambot.service.clients.Client;
import telegrambot.service.clients.ClientContainer;
import telegrambot.service.sendontime.OnTimeMessageSender;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class TimerCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final ClientContainer clientContainer;
    private final static String INCORRECT_TIME_MESSAGE = "Некоректно заданное время";

    public TimerCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
        clientContainer = ClientContainer.getInstance();
    }

    @Override
    public void execute(Update update) {
        String message = update.getMessage().getText().trim();
        String[] timers = message.replaceAll("/timer", "").trim().split(",");
        Long chatId = update.getMessage().getChatId();
        Client client = clientContainer.getClientByChatId(chatId.toString());

        if (client == null) {
            sendBotMessageService.sendMessage(chatId.toString(), StartCommand.NO_CLIENT_ID_MESSAGE);
            return;
        }

        if (!timers[0].trim().matches("\\d{2}:\\d{2}")) {
            sendBotMessageService.sendMessage(chatId.toString(), "Задайте время командой /timer чч:мм\nВремя можно установить через" +
                    " запятую: /timer чч:мм, чч:мм");
            return;
        }
        for (String time : timers) {
            if (checkTimeIsCorrect(time.trim())) {
                if (isTimerAlreadyHas(time, client)) {
                    sendBotMessageService.sendMessage(chatId.toString(), "Оповещение на данное время уже установлено");
                    return;
                } else {
                    client.getListOfTimes().add(time.trim());
                    new TimerMessageSetter().setTimer(time.trim(), client);
                    sendBotMessageService.sendMessage(client.getChatId(), String.format("Таймер ежедневных оповещений на %s успешно установлен", time));
                }
            } else
                sendBotMessageService.sendMessage(client.getChatId(), String.format(("%s: %s. Время не задано."), INCORRECT_TIME_MESSAGE, time));
        }
        if (!client.isTelegramChecked()) {
            sendBotMessageService.sendMessage(client.getChatId(), "Таймеры заданы, но уведомления не будут получены. Включите телеграм-бота в настройках" +
                    " приложения.");
        }
    }

    private boolean isTimerAlreadyHas(String time, Client client) {
        for (String clientTime : client.getListOfTimes()) {
            if (clientTime.equals(time.trim())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTimeIsCorrect(String time) {
        return time.matches("[0-2][0-9]:[0-5][0-9]");
    }


    public class TimerMessageSetter {

        public void initTimers() {
            for (Map.Entry<String, Client> entry : clientContainer.getClientMap().entrySet()) {
                Client client = entry.getValue();
                List<String> timeToSend = client.getListOfTimes();
                for (String time : timeToSend) {
                    setTimer(time, client);
                }
            }
        }


        public void setTimer(String time, Client client) {
            int hour = Integer.parseInt(time.split(":")[0]);
            int minute = Integer.parseInt(time.split(":")[1]);

            Timer timer = new Timer();

            Calendar date = Calendar.getInstance();

            date.set(Calendar.HOUR_OF_DAY, hour);
            date.set(Calendar.MINUTE, minute);
            date.set(Calendar.SECOND, 0);
            date.set(Calendar.MILLISECOND, 0);

            System.out.println(date.getTime());
            timer.schedule(
                    new OnTimeMessageSender(client, sendBotMessageService),
                    date.getTime(),
                    1000 * 60 * 60 * 24);
        }
    }
}
