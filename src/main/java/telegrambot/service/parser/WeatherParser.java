package telegrambot.service.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WeatherParser {
    Document doc = null;

    public String getWeather(String url) {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Elements cityInfo = doc.select ("div [class=pageinfo_title index-h1]");
        String city = cityInfo.select("h1").text();
        Elements spans = doc.select("span [class=js_value tab-weather__value_l]");
        Element temperature = spans.get(0);
        Elements clouds = doc.select("div [class=tab  tooltip]");
        String cloudName = clouds.attr("data-text");
        return city + "::" + temperature.text() + "::" + cloudName.toLowerCase();
    }
}
