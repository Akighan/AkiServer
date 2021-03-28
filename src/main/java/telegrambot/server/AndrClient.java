package telegrambot.server;

import telegrambot.bot.TelegramBot;
import telegrambot.service.clients.Client;
import telegrambot.service.clients.ClientContainer;
import telegrambot.service.commands.SendBotMessageService;
import telegrambot.service.commands.SendBotMessageServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class AndrClient extends Thread {
    private Socket socket;
    private BufferedReader in;
    private TelegramBot telegramBot;

    public AndrClient(Socket socket, TelegramBot telegramBot) throws IOException {
        this.telegramBot = telegramBot;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }

    @Override
    public void run() {
        String note = null;
        String clientId = null;
        ClientContainer clientContainer = ClientContainer.getInstance();
        List<String> notes = new ArrayList<>();
        try {
            if (in.ready()) {
                clientId = in.readLine();

                while (in.ready()) {
                    note = in.readLine();
                    notes.add(note);
                }
                clientContainer.getClientByClientId(clientId).setListOfNotes(notes);
            }
        } catch (IOException e) {
        }
    }
}
