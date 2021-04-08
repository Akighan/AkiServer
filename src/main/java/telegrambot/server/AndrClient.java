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
    public static final String SEND_NOTES_MODIFICATION = "-N";
    public static final String SEND_SETTINGS_MODIFICATION = "-S";

    public AndrClient(Socket socket, TelegramBot telegramBot) throws IOException {
        this.telegramBot = telegramBot;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }

    @Override
    public void run() {
        String note;
        String clientId = null;
        ClientContainer clientContainer = ClientContainer.getInstance();
        List<String> notes = new ArrayList<>();
        try {
            if (in.ready()) clientId = in.readLine();
            String modification = in.readLine();

            switch (modification) {
                case SEND_NOTES_MODIFICATION: {
                    while (in.ready()) {
                        note = in.readLine();
                        notes.add(note);
                    }
                    Client client = clientContainer.getClientByClientId(clientId);
                    if (client != null) {
                        client.setListOfNotes(notes);
                    }
                    break;
                }
                case SEND_SETTINGS_MODIFICATION: {
                    boolean isTelegramChecked = false;
                    boolean isWeatherNotificationChecked = false;
                    boolean isNewsNotificationChecked = false;
                    int cityChosen = 0;
                    if (in.ready()) isTelegramChecked = Boolean.parseBoolean(in.readLine().trim());
                    if (in.ready()) isWeatherNotificationChecked = Boolean.parseBoolean(in.readLine().trim());
                    if (in.ready()) cityChosen = Integer.parseInt(in.readLine().trim());
                    if (in.ready()) isNewsNotificationChecked = Boolean.parseBoolean(in.readLine().trim());
                    Client client = clientContainer.getClientByClientId(clientId);
                    if (client != null) {
                        client.setTelegramChecked(isTelegramChecked);
                        client.setWeatherNotificationChecked(isWeatherNotificationChecked);
                        client.setNewsNotificationChecked(isNewsNotificationChecked);
                        client.setCityChosen(cityChosen);
                    }
                    break;
                }
            }
        } catch (IOException e) {
        }
    }
}
