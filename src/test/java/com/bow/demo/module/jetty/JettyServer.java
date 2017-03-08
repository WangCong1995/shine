package com.bow.demo.module.jetty;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vv
 * @since 2017/3/8.
 */
public class JettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JettyServer.class);

    public void start() {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9000);
        server.addConnector(connector);

        // 添加servlet
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder servletHolder = new ServletHolder(new DemoServlet());
        context.addServlet(servletHolder, "/*");

        try {
            server.start();
            LOGGER.info("----- success to start jetty server -----");
        } catch (Exception e) {
            throw new ShineException(ShineExceptionCode.fail, "fail to start jetty server");
        }
    }

    public static void main(String[] args) {
        JettyServer server = new JettyServer();
        server.start();
    }
}
