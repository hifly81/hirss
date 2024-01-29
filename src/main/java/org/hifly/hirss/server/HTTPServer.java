package org.hifly.hirss.server;

import org.hifly.hirss.RssConfiguration;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerList;
import org.mortbay.jetty.handler.ResourceHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;

import java.io.File;

public class HTTPServer {

    public HTTPServer(RssConfiguration config) throws Exception {
        Server server = new Server();
        Connector connector = new SelectChannelConnector();
        connector.setHost((config.getConfigMap().get("binding_address")));
        connector.setPort(Integer.parseInt(config.getConfigMap().get("port")));
        server.addConnector(connector);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setWelcomeFiles(new String[]{"index.html"});

        resource_handler.setResourceBase(System.getProperty("user.home") + File.separator + config.getConfigMap().get("rss_folder"));

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, new DefaultHandler()});
        server.setHandler(handlers);

        server.start();
        server.join();
    }


}
