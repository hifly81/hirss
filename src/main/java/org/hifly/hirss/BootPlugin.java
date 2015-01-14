package org.hifly.hirss;


import org.hifly.hirss.extensionpoint.SimpleRssDoc;
import org.hifly.hirss.model.Rss;
import org.hifly.hirss.persistence.RssDocStore;
import org.hifly.hirss.server.HTTPServer;
import org.hifly.hirss.stream.RssWriter;
import ro.fortsoft.pf4j.DefaultPluginManager;
import ro.fortsoft.pf4j.PluginManager;
import ro.fortsoft.pf4j.PluginWrapper;

import java.io.File;
import java.util.List;
import java.util.Set;

public class BootPlugin {

    public static void main(String [] args) throws Exception {
        final PluginManager pluginManager = new DefaultPluginManager();
        pluginManager.loadPlugins();
        pluginManager.startPlugins();

        List<SimpleRssDoc> rssDocs = pluginManager.getExtensions(SimpleRssDoc.class);
        if(rssDocs !=null) {
            for (SimpleRssDoc rssDoc : rssDocs) {
                RssDocStore.getRssDocs().add(rssDoc.getDocument());
            }
        }

        Set<String> extensionClassNames = pluginManager.getExtensionClassNames(null);
        for (String extension : extensionClassNames) {
            SimpleRssDoc rssDoc = (SimpleRssDoc)Class.forName(extension).newInstance();
            RssDocStore.getRssDocs().add(rssDoc.getDocument());
        }

        List<PluginWrapper> startedPlugins = pluginManager.getStartedPlugins();
        for (PluginWrapper plugin : startedPlugins) {
            String pluginId = plugin.getDescriptor().getPluginId();
            extensionClassNames = pluginManager.getExtensionClassNames(pluginId);
            for (String extension : extensionClassNames) {
                SimpleRssDoc rssDoc = (SimpleRssDoc)Class.forName(extension).newInstance();
                RssDocStore.getRssDocs().add(rssDoc.getDocument());
            }
        }


        Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				pluginManager.stopPlugins();
			}
        });

        RssConfiguration conf = new RssConfiguration(new File("/home/hifly/projects/hirss/src/main/resources/test.properties"));
        conf.configure();

        if(!RssDocStore.getRssDocs().isEmpty()) {
            for(Rss rss: RssDocStore.getRssDocs())  {
                RssWriter.generateFeed(rss, conf);
            }
        }

        HTTPServer server = new HTTPServer(conf);




    }
}
