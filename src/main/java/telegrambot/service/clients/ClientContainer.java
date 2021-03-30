package telegrambot.service.clients;

import com.google.common.collect.ImmutableMap;
import telegrambot.command.Command;
import telegrambot.service.commands.*;

import java.util.HashMap;

import static telegrambot.command.CommandName.*;

public class ClientContainer {

    private static HashMap <String, Client> clientIdMap;
    private static HashMap <String, Client> chatIdMap;
    private static ClientContainer clientContainer;

    private ClientContainer() {
    }

    public static ClientContainer getInstance () {
        if (clientContainer == null) {
            clientContainer = new ClientContainer();
            clientIdMap = new HashMap<>();
            chatIdMap = new HashMap<>();
        }
        return clientContainer;
    }

    public HashMap<String, Client> getClientMap() {
        return clientIdMap;
    }

    public HashMap<String, Client> getChatIdMap() {
        return chatIdMap;
    }

    public Client getClientByClientId (String clientId) {
        return clientIdMap.get(clientId);
    }

    public Client getClientByChatId (String chatId) {
        return chatIdMap.get(chatId);
    }

    public void putClient (Client client) {
        clientIdMap.put(client.getClientId(),client);
        chatIdMap.put(client.getChatId(),client);
    }
}

