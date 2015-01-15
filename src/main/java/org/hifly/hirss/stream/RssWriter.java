package org.hifly.hirss.stream;


import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.SyndFeedOutput;
import org.apache.commons.codec.digest.DigestUtils;
import org.hifly.hirss.RssConfiguration;
import org.hifly.hirss.model.Item;
import org.hifly.hirss.model.Rss;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class RssWriter {

    public static void generateFeed(Rss rssDoc, RssConfiguration config) throws Exception {
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0"); //TODO configure types

        feed.setTitle(rssDoc.getTitle());
        feed.setLink(rssDoc.getLink());
        feed.setDescription(rssDoc.getDescription());

        List entries = new ArrayList(rssDoc.getItems().size());
        for(Item item: rssDoc.getItems()) {
            SyndEntry entry;
            SyndContent description;
            entry = new SyndEntryImpl();
            entry.setTitle(item.getTitle());
            entry.setLink(item.getLink());
            entry.setPublishedDate(item.getPublished());
            description = new SyndContentImpl();
            description.setType(item.getDescription().getType());
            description.setValue(item.getDescription().getValue());
            entry.setDescription(description);
            entries.add(entry);
        }


        feed.setEntries(entries);

        Writer writer = new FileWriter(System.getProperty("user.home") + File.separator + config.getConfigMap().get("rss_folder") + File.separator + DigestUtils.md5Hex(rssDoc.getTitle()));
        SyndFeedOutput output = new SyndFeedOutput();
        output.output(feed,writer);
        writer.close();

    }
}
