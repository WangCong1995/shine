package com.bow.remoting.hessian;

import com.bow.common.exception.ShineException;
import com.bow.common.exception.ShineExceptionCode;
import com.bow.common.utils.NetUtil;
import com.bow.config.ShineConfig;
import com.bow.remoting.ShineServer;
import com.bow.rpc.RequestHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author vv
 * @since 2016/8/20.
 */
public class HessianServer implements ShineServer {

    private static final Logger logger = LoggerFactory.getLogger(HessianServer.class);

    private RequestHandler requestHandler;

    public void start() {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setHost(NetUtil.getLocalHostAddress());
        connector.setPort(ShineConfig.getServicePort());
        server.addConnector(connector);

        // 添加servlet
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        if(requestHandler == null){
            throw new ShineException(ShineExceptionCode.fail,"requestHandler must not be null in jetty server");
        }
        ServletHolder servletHolder = new ServletHolder(new HessianDispatcherServlet(requestHandler));
        context.addServlet(servletHolder,"/*");


        try {
            server.start();
            logger.info("----- success to start jetty server -----");
        } catch (Exception e) {
            throw new ShineException(ShineExceptionCode.fail,"fail to start jetty server");
        }
    }

    public void stop() {
        //no-op
    }


    @Override
    public void setRequestHandler(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }
}
