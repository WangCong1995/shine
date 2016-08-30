package com.bow.remoting;

import com.bow.common.utils.NetUtil;
import com.bow.config.ShineConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by vv on 2016/8/20.
 */
public class HessianServer extends AbstractShineServer {


    public boolean start() {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setHost(NetUtil.getLocalHostAddress());
        connector.setPort(ShineConfig.getservicePort());
        server.addConnector(connector);

        // 添加servlet
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHandler servletHandler = new ServletHandler();
        ServletHolder servletHolder = servletHandler.addServletWithMapping(HessianDispatcherServlet.class, "/*");
        servletHolder.setInitOrder(2);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean stop() {
        return false;
    }


}
