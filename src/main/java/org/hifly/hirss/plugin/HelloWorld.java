package org.hifly.hirss.plugin;


import org.hifly.hirss.RssConfiguration;
import org.hifly.hirss.extensionpoint.SimpleRssDoc;
import org.hifly.hirss.model.Item;
import org.hifly.hirss.model.ItemDescription;
import org.hifly.hirss.model.Rss;
import ro.fortsoft.pf4j.Extension;

import java.util.ArrayList;
import java.util.List;

@Extension
public class HelloWorld implements SimpleRssDoc {

    public Rss getDocument(RssConfiguration rssConfiguration) {
        String url = "http://www.google.com"; //TODO config url
        Rss rss = new Rss();
        rss.setTitle("Hello World");
        rss.setLink(url);
        rss.setDescription("News From the world");
        try {

            //rss geenric elements
            List<Item> items = new ArrayList(1);

            Item item = new Item();
            item.setLink("http://www.google.com");
            String itemTitle = "Title";
            item.setTitle(itemTitle);
            ItemDescription itemDescription = new ItemDescription();
            itemDescription.setValue(itemTitle);
            itemDescription.setType("text/html");
            item.setDescription(itemDescription);
            items.add(item);
            //TODO define a published date

            rss.setItems(items);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return rss;
    }
}
