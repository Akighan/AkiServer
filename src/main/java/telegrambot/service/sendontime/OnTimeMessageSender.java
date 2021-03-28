package telegrambot.service.sendontime;

import telegrambot.bot.TelegramBot;
import telegrambot.service.clients.Client;
import telegrambot.service.clients.ClientContainer;
import telegrambot.service.commands.SendBotMessageService;
import telegrambot.service.commands.TimerCommand;

import java.util.*;

public class OnTimeMessageSender extends TimerTask {
    private final Client client;
    private final SendBotMessageService sendBotMessageService;

    public OnTimeMessageSender(Client client, SendBotMessageService sendBotMessageService) {
        this.client = client;
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void run() {
        sendBotMessageService.sendMessage(client.getChatId(), client.getListOfNotes().toString());
    }

}
