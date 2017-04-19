package org.hifly.hirss;


import org.hifly.hirss.plugin.PluginLoader;
import org.hifly.hirss.server.HTTPServer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Boot {

    public static void main(String[] args) throws Exception {
        RssConfiguration conf = new RssConfiguration();
        conf.configure();
        final PluginLoader loader = new PluginLoader(conf);
        loader.loadExtensions();
        loader.runExtensions();

        new File(System.getProperty("user.home") + File.separator + conf.getConfigMap().get("rss_folder")).mkdirs();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                loader.stopPlugins();
            }
        });

        new HTTPServer(conf);


    }
}
