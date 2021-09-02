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
import ro.fortsoft.pf4j.Extension;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Extension
public class HelloWorld implements SimpleRssDoc {

    public Rss getDocument(RssConfiguration rssConfiguration) {
        String url = "https://www.helloworld11111111.com";
        Rss rss = new Rss();
        rss.setTitle("Hello World News");
        rss.setLink(url);
        rss.setDescription("Hello World News");
        try {

            Document document = Jsoup.connect(url).timeout(Integer.valueOf(rssConfiguration.getConfigMap().get("rss_connect_timeout"))).get();
            Elements topStories = document.select("article");
            if (topStories != null && topStories.size() > 0) {
                List<Item> items = new ArrayList();
                Iterator<Element> elementIterator = topStories.iterator();
                while (elementIterator.hasNext()) {
                    Element elementArticle = elementIterator.next();
                    Element element = elementArticle.getElementsByTag("a").get(0);
                    String link = element.attr("href");
                    String text = element.getElementsByTag("h2").text();
                    Item item = new Item();
                    item.setLink(url + link);
                    String itemTitle = text;
                    item.setTitle(text);
                    ItemDescription itemDescription = new ItemDescription();
                    itemDescription.setValue(itemTitle);
                    itemDescription.setType("text/html");
                    item.setDescription(itemDescription);
                    String published = elementArticle.getElementsByTag("time").get(0).attr("datetime");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    item.setPublished(dateFormat.parse(published));
                    items.add(item);
                }
                rss.setItems(items);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rss;
    }

}
