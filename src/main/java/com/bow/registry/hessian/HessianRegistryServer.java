package com.bow.registry.hessian;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.common.utils.NetUtil;
import com.bow.config.ShineConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 开一个jetty,用hessian servlet做注册中心
 * 
 * @author vv
 * @since 2016/12/11.
 */
public class HessianRegistryServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(HessianRegistryServer.class);

    public void start() {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        InetSocketAddress address = NetUtil.toSocketAddress(ShineConfig.getRegistryUrl());
        String host = "127.0.0.1";
        int port = address.getPort();
        connector.setHost(host);
        connector.setPort(port);
        server.addConnector(connector);

        // 添加servlet
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder servletHolder = new ServletHolder(new HessianRegistryServlet());
        context.addServlet(servletHolder, "/registry");

        try {
            server.start();
            LOGGER.info("----- success to start hessian registry server -----");
            LOGGER.debug("address " + host + ":" + port);
        } catch (Exception e) {
            throw new ShineException(ShineExceptionCode.fail, "fail to start hessian registry server");
        }
    }

    public static void main(String[] args) {
        HessianRegistryServer server = new HessianRegistryServer();
        server.start();
    }
}
