package telegrambot.service.clients;

import java.util.HashMap;

public class ClientContainer {

    private static HashMap<String, Client> clientIdMap;
    private static HashMap<String, Client> chatIdMap;
    private static ClientContainer clientContainer;

    private ClientContainer() {
    }

    public static ClientContainer getInstance() {
        if (clientContainer == null) {
            clientContainer = new ClientContainer();
            clientIdMap = new HashMap<>();
            chatIdMap = new HashMap<>();
        }
        return clientContainer;
    }

    public HashMap<String, Client> getClientIdMap() {
        return clientIdMap;
    }

    public HashMap<String, Client> getChatIdMap() {
        return chatIdMap;
    }

    public Client getClientByClientId(String clientId) {
        return clientIdMap.get(clientId);
    }

    public Client getClientByChatId(String chatId) {
        return chatIdMap.get(chatId);
    }

    public void putClient(Client client) {
            clientIdMap.putIfAbsent(client.getClientId(), client);

            chatIdMap.putIfAbsent(client.getChatId(), client);
    }
}

