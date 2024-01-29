package org.hifly.hirss.plugin;

import org.hifly.hirss.RssConfiguration;
import org.hifly.hirss.extensionpoint.SimpleRssDoc;
import org.hifly.hirss.model.Rss;
import org.hifly.hirss.persistence.RssDocStore;
import org.hifly.hirss.stream.RssWriter;
import org.hifly.hirss.timer.SimpleTask;
import ro.fortsoft.pf4j.DefaultPluginManager;
import ro.fortsoft.pf4j.PluginManager;
import ro.fortsoft.pf4j.PluginWrapper;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.Timer;

public class PluginLoader {

    private final PluginManager pluginManager;
    private final RssConfiguration rssConfiguration;

    public PluginLoader(RssConfiguration rssConfiguration) {
        pluginManager = new DefaultPluginManager();
        //default load and start plugins
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        this.rssConfiguration = rssConfiguration;
    }

    public void stopPlugins() {
        pluginManager.stopPlugins();
    }

    public void loadExtensions() throws Exception {
        List<SimpleRssDoc> rssDocs = pluginManager.getExtensions(SimpleRssDoc.class);
        if(rssDocs !=null) {
            for (SimpleRssDoc rssDoc : rssDocs) {
                RssDocStore.getRssDocs().add(rssDoc.getDocument(rssConfiguration));
            }
        }

        Set<String> extensionClassNames = pluginManager.getExtensionClassNames(null);
        for (String extension : extensionClassNames) {
            SimpleRssDoc rssDoc = (SimpleRssDoc)Class.forName(extension).newInstance();
            RssDocStore.getRssDocs().add(rssDoc.getDocument(rssConfiguration));
        }

        List<PluginWrapper> startedPlugins = pluginManager.getStartedPlugins();
        for (PluginWrapper plugin : startedPlugins) {
            String pluginId = plugin.getDescriptor().getPluginId();
            extensionClassNames = pluginManager.getExtensionClassNames(pluginId);
            for (String extension : extensionClassNames) {
                SimpleRssDoc rssDoc = (SimpleRssDoc)Class.forName(extension).newInstance();
                RssDocStore.getRssDocs().add(rssDoc.getDocument(rssConfiguration));
            }
        }
    }

    public void runExtensions() throws Exception {
        if(!RssDocStore.getRssDocs().isEmpty()) {
            for(Rss rss: RssDocStore.getRssDocs())  {
                Method method = RssWriter.class.getMethod("generateFeed",
                        new Class[] { Rss.class, RssConfiguration.class});
                Object [] args = { rss, rssConfiguration};
                SimpleTask task = new SimpleTask(method, args, rss.getLink());
                Timer time = new Timer();
                int interval = Integer.parseInt(rssConfiguration.getConfigMap().get("rss_update_period"));
                time.schedule(task, 0, 50L * interval);

            }
        }

    }
}
