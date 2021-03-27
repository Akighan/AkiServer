package telegrambot.service.clients;

import com.google.common.collect.ImmutableMap;
import telegrambot.command.Command;
import telegrambot.service.commands.*;

import java.util.HashMap;

import static telegrambot.command.CommandName.*;

public class ClientContainer {

    private static HashMap <String, Client> clientMap;
    private static ClientContainer clientContainer;

    private ClientContainer() {
    }

    public static ClientContainer getInstance () {
        if (clientContainer == null) {
            clientContainer = new ClientContainer();
            clientMap = new HashMap<>();
        }
        return clientContainer;
    }

    public Client getClient (String clientId) {
        return clientMap.get(clientId);
    }

    public void putClient (Client client) {
        clientMap.put(client.getClientId(),client);
    }
}

