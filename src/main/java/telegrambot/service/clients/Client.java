package telegrambot.service.clients;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private final String clientId;
    private final String chatId;
    private List<String> listOfNotes;
    private List <String> listOfTimes;

    public Client(String clientId, String chatId) {
        this.clientId = clientId;
        this.chatId = chatId;
        listOfNotes = new ArrayList<>();
        listOfTimes = new ArrayList<>();
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

    public List<String> getListOfTimes() {
        return listOfTimes;
    }

    public void setListOfTimes(List<String> listOfTimes) {
        this.listOfTimes = listOfTimes;
    }
}
