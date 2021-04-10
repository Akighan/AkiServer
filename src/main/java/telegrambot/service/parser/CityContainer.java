package telegrambot.service.parser;

import java.util.ArrayList;
import java.util.List;

public class CityContainer {
    private List<String> cities;
    private static CityContainer cityContainer;


    private CityContainer() {
        cities = new ArrayList<>();
        cities.add("Город не выбран");
        cities.add("https://www.gismeteo.ru/weather-nizhny-novgorod-4355/");
        cities.add("https://www.gismeteo.ru/weather-moscow-4368/");
        cities.add("https://www.gismeteo.ru/weather-astrakhan-5130/");
        cities.add("https://www.gismeteo.ru/weather-kazan-4364/");
        cities.add("https://www.gismeteo.ru/weather-volgograd-5089/");
        cities.add("https://www.gismeteo.ru/weather-sankt-peterburg-4079/");

    }

    public String getCity(int cityNumber) {
        return cities.get(cityNumber);
    }

    public static CityContainer getInstance() {
        if (cityContainer == null) {
            cityContainer = new CityContainer();
        }
        return cityContainer;
    }
}
