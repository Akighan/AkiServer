package telegrambot.server;

import telegrambot.bot.TelegramBot;
import telegrambot.service.SendBotMessageService;
import telegrambot.service.SendBotMessageServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class AndrClient extends Thread {
    private Socket socket;
    private BufferedReader in;
    private TelegramBot telegramBot;
    private SendBotMessageService sendBotMessageService;

    public AndrClient (Socket socket, TelegramBot telegramBot) throws IOException {
        this.telegramBot = telegramBot;
        this.socket = socket;
        SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(telegramBot);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }

    @Override
    public void run() {
        String word = null;
        String id = null;

        try {
//            if (in.ready()) {
//                id = in.readLine();
//                System.out.println(id);
//            }
            while (in.ready()) {
                word = in.readLine();
                if (id != null && word != null) {
                    sendBotMessageService.sendMessage("825476859", word);
                }
            }
        } catch (IOException e) {
        }
    }
}
