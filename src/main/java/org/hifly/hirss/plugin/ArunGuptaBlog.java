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
public class ArunGuptaBlog implements SimpleRssDoc {

    public Rss getDocument(RssConfiguration rssConfiguration) {
        String url = "http://blog.arungupta.me"; //TODO config url
        Rss rss = new Rss();
        rss.setTitle("Arun Gupta Blog");
        rss.setLink(url);
        rss.setDescription("Arun Gupta Blog");
        try {

            Document document = Jsoup.connect(url).timeout(Integer.valueOf(rssConfiguration.getConfigMap().get("rss_connect_timeout"))).get();
            Elements topStories = document.select("h1.entry-title");
            if(topStories != null && topStories.size() > 0) {
                List<Item> items = new ArrayList();
                Iterator<Element> elementIterator = topStories.iterator();
                while(elementIterator.hasNext()) {
                    Element element = elementIterator.next();
                    element = element.getElementsByTag("a").get(0);
                    String link = element.attr("href");
                    String text = element.text();
                    Item item = new Item();
                    item.setLink(link);
                    String itemTitle = text;
                    item.setTitle(text);
                    ItemDescription itemDescription = new ItemDescription();
                    itemDescription.setValue(itemTitle);
                    itemDescription.setType("text/html");
                    item.setDescription(itemDescription);
                    String published = element.getElementsByTag("time").get(0).attr("datetime");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    item.setPublished(dateFormat.parse(published));
                    items.add(item);
                }
                rss.setItems(items);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return rss;
    }

}
