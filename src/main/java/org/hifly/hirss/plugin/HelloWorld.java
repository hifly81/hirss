package org.hifly.hirss.plugin;

import org.hifly.hirss.RssConfiguration;
import org.hifly.hirss.extensionpoint.SimpleRssDoc;
import org.hifly.hirss.model.Item;
import org.hifly.hirss.model.ItemDescription;
import org.hifly.hirss.model.Rss;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.fortsoft.pf4j.Extension;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Extension
public class HelloWorld implements SimpleRssDoc {

    private static Logger log = LoggerFactory.getLogger(HelloWorld.class);

    public Rss getDocument(RssConfiguration rssConfiguration) {
        String url = "https://xxxxx";
        Rss rss = new Rss();
        rss.setTitle("Hello World News");
        rss.setLink(url);
        rss.setDescription("Hello World News");
        try {

            Document document = Jsoup.connect(url).timeout(Integer.parseInt(rssConfiguration.getConfigMap().get("rss_connect_timeout"))).get();
            Elements topStories = document.select("div.xxxx");
            if (topStories != null && !topStories.isEmpty()) {
                List<Item> items = new ArrayList<>();
                for (Element elementArticle : topStories) {
                    Element element = elementArticle.getElementsByTag("a").get(0);
                    String link = element.attr("href");
                    String text  = document.select("div.xxx").text();
                    Item item = new Item();
                    item.setLink(url + link);
                    item.setTitle(text);
                    ItemDescription itemDescription = new ItemDescription();
                    itemDescription.setValue(text);
                    itemDescription.setType("text/html");
                    item.setDescription(itemDescription);
                    String published;

                    try {
                        published = elementArticle.getElementsByTag("time").get(0).attr("datetime");
                    } catch (Exception ex) {
                        log.warn("Published date not found, set now.");
                        published = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date());
                    }

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    item.setPublished(dateFormat.parse(published));
                    items.add(item);
                }
                rss.setItems(items);
            } else {
                log.warn("No elements found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rss;
    }

}
