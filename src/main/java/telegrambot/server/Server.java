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

    public static final int PORT = 6789;
    public static LinkedList <AndrClient> andrClients = new LinkedList<AndrClient>();

    public static void main(String[] args) throws IOException {
        TelegramBot telegramBot = new TelegramBot();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        ServerSocket server = new ServerSocket(PORT);
        try {
            while (true) {
                System.out.println("Ищу подключения");
                Socket socket = server.accept();
                System.out.println("устанавливаю соединение");
                try {
                    andrClients.add (new AndrClient(socket, telegramBot));
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
