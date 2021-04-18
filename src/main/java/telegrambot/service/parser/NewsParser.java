package telegrambot.service.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsParser {
    Document doc;

    {
        try {
            doc = Jsoup.connect("https://newdaynews.ru/allnews/")
                    .referrer("http://www.google.com")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Elements listNews = doc.select("div.gbl-mh.shrtl-l");

    public List<String> getListNews() {
        Elements listNewsElements = listNews.select("a");
        List <String> news = new ArrayList<>();
        if (listNewsElements.size() > 3) {
            for (int i = 0; i<3; i++) {
                Element element = listNewsElements.get(i);
                String text = element.toggleClass("b").text();
                String urlAddress = "https://newdaynews.ru" + element.attr("href");
                news.add(String.format("<a href=\"%s\">%s</a>\n\n",urlAddress, text));
            }
        }
        return news;
    }
}
