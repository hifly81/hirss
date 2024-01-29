package org.hifly.hirss.extensionpoint;

import org.hifly.hirss.RssConfiguration;
import org.hifly.hirss.model.Rss;

public interface SimpleRssDoc {

    Rss getDocument(RssConfiguration rssConfiguration);
}
