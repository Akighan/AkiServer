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

    public TimerCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
        clientContainer = ClientContainer.getInstance();
    }

    @Override
    public void execute(Update update) {
        String message = update.getMessage().getText().trim();
        String [] timers = message.replaceAll("/timer","").trim().split(",");
        Long chatId = update.getMessage().getChatId();
        Client client = clientContainer.getClientByChatId(chatId.toString());
        client.setListOfTimes(Arrays.asList(timers));
        for (int i = 0; i<timers.length; i++) {
            new TimerMessageSetter().setTimer(timers[i].trim(),client);
        }
    }

    public class TimerMessageSetter {
        private final ClientContainer clientContainer;

        public TimerMessageSetter() {
            clientContainer = ClientContainer.getInstance();
        }

        public void initTimers() {
            for (Map.Entry<String, Client> entry : clientContainer.getClientMap().entrySet()) {
                Client client = entry.getValue();
                List<String> timeToSend = client.getListOfTimes();
                for (String time : timeToSend) {
                    setTimer(time, client);
                }
            }
        }

        public void setTimer (String time, Client client) {
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
