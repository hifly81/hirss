package org.hifly.hirss.persistence;


import org.hifly.hirss.model.Rss;

import java.util.ArrayList;
import java.util.List;

public class RssDocStore {

    private final static List<Rss> rssDocs = new ArrayList();

    public static List<Rss> getRssDocs() {
        return rssDocs;
    }
}
