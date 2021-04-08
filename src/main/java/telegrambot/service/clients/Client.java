package telegrambot.service.clients;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private String clientId;
    private String chatId;
    private List<String> listOfNotes;
    private List <String> listOfTimes;
    private boolean isTelegramChecked = false;
    private boolean isWeatherNotificationChecked = false;
    private boolean isNewsNotificationChecked = false;
    private int cityChosen;

    public Client(String clientId, String chatId) {
        this.clientId = clientId;
        this.chatId = chatId;
        listOfNotes = new ArrayList<>();
        listOfTimes = new ArrayList<>();
    }

    public Client(String clientId) {
        this.clientId = clientId;
        this.chatId = null;
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

    public String notesToString () {
        StringBuilder stringBuilder = new StringBuilder();
        for (String note:listOfNotes) {
            stringBuilder.append(note).append("\n\n");
        }
        return stringBuilder.toString();
    }

    public List<String> getListOfTimes() {
        return listOfTimes;
    }

    public void setListOfTimes(List<String> listOfTimes) {
        this.listOfTimes = listOfTimes;
    }

    public boolean isTelegramChecked() {
        return isTelegramChecked;
    }

    public void setTelegramChecked(boolean telegramChecked) {
        isTelegramChecked = telegramChecked;
    }

    public boolean isWeatherNotificationChecked() {
        return isWeatherNotificationChecked;
    }

    public void setWeatherNotificationChecked(boolean weatherNotificationChecked) {
        isWeatherNotificationChecked = weatherNotificationChecked;
    }

    public boolean isNewsNotificationChecked() {
        return isNewsNotificationChecked;
    }

    public void setNewsNotificationChecked(boolean newsNotificationChecked) {
        isNewsNotificationChecked = newsNotificationChecked;
    }

    public int getCityChosen() {
        return cityChosen;
    }

    public void setCityChosen(int cityChosen) {
        this.cityChosen = cityChosen;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
