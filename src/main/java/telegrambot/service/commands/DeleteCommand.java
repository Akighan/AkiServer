package telegrambot.service.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import telegrambot.command.Command;
import telegrambot.command.CommandName;
import telegrambot.service.clients.Client;
import telegrambot.service.clients.ClientContainer;

import java.util.Map;
import java.util.Timer;

public class DeleteCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final ClientContainer clientContainer;
    private Map<String, Timer> timerMap;
    private final static String INCORRECT_TIME_MESSAGE = "Некоректно заданное время";

    public DeleteCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
        clientContainer = ClientContainer.getInstance();
    }

    @Override
    public void execute(Update update) {
        String message = update.getMessage().getText().trim();
        String[] timers = message.replaceAll("/delete", "").trim().split(",");
        Long chatId = update.getMessage().getChatId();
        Client client = clientContainer.getClientByChatId(chatId.toString());

        if (client == null) {
            sendBotMessageService.sendMessage(chatId.toString(), StartCommand.NO_CLIENT_ID_MESSAGE);
            return;
        }

        timerMap = client.getTimesMap();

        System.out.println(timers[0].trim());
        System.out.println(!(timers[0].trim().equalsIgnoreCase("all")));
        System.out.println(!(timers[0].trim().matches("\\d{2}:\\d{2}")));
        System.out.println(!(timers[0].trim().equalsIgnoreCase("all")) ^ !(timers[0].trim().matches("\\d{2}:\\d{2}")));

        if (!(timers[0].trim().equalsIgnoreCase("all")) && !(timers[0].trim().matches("\\d{2}:\\d{2}"))) {
            sendBotMessageService.sendMessage(chatId.toString(), "Задайте время командой /delete чч:мм\nВремя можно установить через" +
                    " запятую: /delete чч:мм, чч:мм \nТак же доступно удаление всех таймеров /delete all");
            return;
        }
        for (String time : timers) {
            if (time.equalsIgnoreCase("all")) {
                for (Map.Entry<String, Timer> timer : timerMap.entrySet()) {
                    timer.getValue().cancel();
                }
                timerMap.clear();
                sendBotMessageService.sendMessage(chatId.toString(), "Все таймеры успешно удалены");
                return;
            }

            if (checkTimeIsCorrect(time.trim())) {
                if (isTimerAlreadyHas(time, client)) {
                    timerMap.get(time).cancel();
                    timerMap.remove(time);
                    sendBotMessageService.sendMessage(chatId.toString(), "Оповещение на данное время успешно удалено.\n"
                            + CommandName.TIMERS.getCommandName() + " - Список доступных таймеров.");
                    continue;
                } else {
                    sendBotMessageService.sendMessage(client.getChatId(), String.format("Не нашёл таймера на %s", time));
                }
            } else
                sendBotMessageService.sendMessage(client.getChatId(), String.format(("%s: %s. Удаление таймера невозможно."), INCORRECT_TIME_MESSAGE, time));
        }
    }

    private boolean isTimerAlreadyHas(String time, Client client) {
        for (Map.Entry<String, Timer> clientTime : timerMap.entrySet()) {
            if (clientTime.getKey().equals(time.trim())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTimeIsCorrect(String time) {
        return time.matches("[0-2][0-9]:[0-5][0-9]");
    }
}

