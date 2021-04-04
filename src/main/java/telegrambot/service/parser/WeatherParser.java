package telegrambot.service.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WeatherParser {
    Document doc = null;
    public String getListNews(String url) {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        Elements spans = doc.select("span [class=js_value tab-weather__value_l]");
        Element span = spans.get(0);
        System.out.println(span.text());
        return span.text();
    }
}
