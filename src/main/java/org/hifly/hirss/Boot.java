package org.hifly.hirss;


import org.hifly.hirss.plugin.PluginLoader;
import org.hifly.hirss.server.HTTPServer;

public class Boot {

    public static void main(String[] args) throws Exception {
        RssConfiguration conf = new RssConfiguration();
        conf.configure();
        final PluginLoader loader = new PluginLoader();
        loader.loadExtensions();
        loader.runExtensions(conf);


        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                loader.stopPlugins();
            }
        });

        HTTPServer server = new HTTPServer(conf);


    }
}
