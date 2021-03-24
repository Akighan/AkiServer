package telegrambot.server;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegrambot.bot.TelegramBot;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

    public static final int PORT = 8080;
    public static LinkedList <AndrClient> andrClients = new LinkedList<AndrClient>();

    public static void main(String[] args) throws IOException {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        ServerSocket server = new ServerSocket(PORT);
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    andrClients.add (new AndrClient(socket));
                } catch (IOException e) {
                    socket.close();
                    e.printStackTrace();
                }
            }
        } finally {
            server.close();
        }
    }
}
