package com.bow.demo.module.resteasy;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.jboss.resteasy.plugins.spring.SpringContextLoaderListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;

/**
 * jetty + rest easy
 * @author vv
 * @since 2018/3/5.
 */
public class RestSpringJettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestSpringJettyServer.class);

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
        context.addEventListener(new ResteasyBootstrap());
        context.addEventListener(new SpringContextLoaderListener());
        context.setInitParameter("contextConfigLocation", "classpath:root-context.xml");
        server.setHandler(context);
        try {
            server.start();
            LOGGER.info("----- Success to start jetty server -----");
        } catch (Exception e) {
            throw new ShineException(ShineExceptionCode.fail, "fail to start jetty server");
        }
    }

    public static void main(String[] args) {
        RestSpringJettyServer server = new RestSpringJettyServer();
        server.start();
    }
}
