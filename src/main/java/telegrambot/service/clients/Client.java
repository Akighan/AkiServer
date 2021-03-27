package telegrambot.service.clients;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private final String clientId;
    private final String chatId;
    List<String> listOfNotes;

    public Client(String clientId, String chatId) {
        this.clientId = clientId;
        this.chatId = chatId;
        listOfNotes = new ArrayList<>();
    }

    public String getClientId () {
        return clientId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setListOfNotes(List<String> listOfNotes) {
        this.listOfNotes = listOfNotes;
    }

    public List<String> getListOfNotes() {
        return listOfNotes;
    }
}
