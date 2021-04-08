package telegrambot.service.clients;

import java.util.*;

public class Client {
    private String clientId;
    private String chatId;
    private List<String> listOfNotes;
    private Map<String, Timer> timesMap;
    private boolean isTelegramChecked = false;
    private boolean isWeatherNotificationChecked = false;
    private boolean isNewsNotificationChecked = false;
    private int cityChosen;

    public Client(String clientId, String chatId) {
        this.clientId = clientId;
        this.chatId = chatId;
        listOfNotes = new ArrayList<>();
        timesMap = new HashMap<>();
    }

    public Client(String clientId) {
        this.clientId = clientId;
        this.chatId = null;
        listOfNotes = new ArrayList<>();
        timesMap = new HashMap<>();
    }

    public String getClientId() {
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

    public String notesToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String note : listOfNotes) {
            stringBuilder.append(note).append("\n\n");
        }
        return stringBuilder.toString();
    }

    public Map<String, Timer> getTimesMap() {
        return timesMap;
    }

    public List <String> getSortedTimesMapKeys () {
        List <String> listOfTimes = new ArrayList<>();
        for (Map.Entry<String, Timer> timer:timesMap.entrySet()) {
            listOfTimes.add(timer.getKey());
        }
        listOfTimes.sort((o1, o2) -> {
            int firstTime = Integer.parseInt(o1.replace(":", ""));
            int secondTime = Integer.parseInt(o2.replace(":", ""));
            return Integer.compare(firstTime, secondTime);
        });

        return listOfTimes;
    }

    public void setTimesMap(Map<String, Timer> timesMap) {
        this.timesMap = timesMap;
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
