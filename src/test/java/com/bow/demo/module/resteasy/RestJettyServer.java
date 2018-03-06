package com.bow.demo.module.resteasy;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;

/**
 * jetty + rest easy
 * @author vv
 * @since 2018/3/5.
 */
public class RestJettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestJettyServer.class);

    public void start() {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        // 创建context
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        // 添加servlet
        ServletHolder servletHolder = new ServletHolder(RestServletDispatcher.class);
        context.addServlet(servletHolder, "/*");

        server.setHandler(context);
        try {
            server.start();
            LOGGER.info("----- Success to start jetty server -----");
        } catch (Exception e) {
            throw new ShineException(ShineExceptionCode.fail, "fail to start jetty server");
        }
    }

    public static void main(String[] args) {
        RestJettyServer server = new RestJettyServer();
        server.start();
    }
}
